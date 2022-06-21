package com.trainingapps.playerapp.serviceTest;


import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.function.Executable;

import com.trainingapps.playerapp.dao.IFavoritedPlayerRepository;
import com.trainingapps.playerapp.dto.AddToFavorite;
import com.trainingapps.playerapp.dto.FavouritedPlayerDetails;
import com.trainingapps.playerapp.dto.RemoveFavorite;
import com.trainingapps.playerapp.entity.FavouritedPlayer;
import com.trainingapps.playerapp.exceptions.FavoritePlayerListEmptyException;
import com.trainingapps.playerapp.exceptions.PlayerAlreadyExistsException;
import com.trainingapps.playerapp.exceptions.PlayerNotFoundException;
import com.trainingapps.playerapp.service.FavoritedPlayerServiceImpl;
import com.trainingapps.playerapp.util.FavoritePlayerUtil;

@ExtendWith(MockitoExtension.class)
public class FavoritedPlayerServiceImplTest {
	
	@Mock
	IFavoritedPlayerRepository  dao;

	@Mock
	FavoritePlayerUtil utility;
	
	@InjectMocks
	@Spy
	FavoritedPlayerServiceImpl service;
	/*
	 * Scenario: when list is successfully fetched
	 * Input: Prajwal
	 * Expectation:list successfully fetched
	 */
	@Test
	public void testListFavoritePlayersByUserName_1() throws Exception{
		String username="Prajwal";
		List<FavouritedPlayerDetails> detailsList=mock(List.class);
		List<FavouritedPlayer>favourite=mock(List.class);
		//when(favourite.isEmpty()).thenReturn(false);
		when(dao.findByUserName(username)).thenReturn(favourite);
		when(utility.toFavoritePlayerDetailsList(favourite)).thenReturn(detailsList);
		List<FavouritedPlayerDetails>result=service.listFavoritePlayersByUserName(username);
		assertSame(detailsList,result);
		verify(dao).findByUserName(username);
		verify(utility).toFavoritePlayerDetailsList(favourite);
	}
	
	 /*
    Scenario: When list is empty, FavouritePlayerListEmptyException is thrown
    input: username : harsh
    expectation : FavoritePlayerListEmptyException is thrown
    verifying IFavouritedPlayerRepository#findByUserName(username) is called once

     */
    @Test
    public void testListFavoritePlayersByUserName_2() throws Exception {
        String username="Harsh";
        List<FavouritedPlayer> favourites = mock(List.class);
        lenient().when(favourites.isEmpty()).thenReturn(true);
        when(dao.findByUserName(username)).thenReturn(favourites);
        Executable executable = () -> {
            service.listFavoritePlayersByUserName(username);
        };
     
        assertThrows(FavoritePlayerListEmptyException.class, executable);
        verify(dao).findByUserName(username);

    }
    /*
    Scenario: Player is added successfully
    Input: AddToFavourite data
    expectation : Player is added, favouriteTrackDetails is returned
    verify repository#save(player) is called once
    String id=generateNewId(requestData.getUsername(), requestData.getExternalServerId());

     */
    @Test
    public void testAddToFavourite_1() throws Exception {
        AddToFavorite request=new AddToFavorite();
        request.setName("Sachi Tendulkar");
        request.setCountry("India");
        request.setPlaceOfBirth("Mumbai");
        request.setBattingStyle("Right handed batsman");
        request.setBowlingStyle("right handed bowler");
        request.setUserName("Ramya");
        String id="123";
        Optional<FavouritedPlayer> optional=Optional.empty();
        when(dao.findByUserNameAndExternalServerId(request.getUserName(),request.getExternalServerId())).thenReturn(optional);
        FavouritedPlayer favouritePlayer=mock(FavouritedPlayer.class);
        when(utility.toFavouritedPlayer(request)).thenReturn(favouritePlayer);
        doReturn(id).when(service).generateNewID(request.getUserName(),request.getExternalServerId());
        FavouritedPlayer savedPlayer=mock(FavouritedPlayer.class);
        when(dao.save(favouritePlayer)).thenReturn(savedPlayer);
        FavouritedPlayerDetails details=mock(FavouritedPlayerDetails.class);
        when(utility.convertToFavoritePlayerDetails(savedPlayer)).thenReturn(details);
        FavouritedPlayerDetails result=service.addToFavorite(request);
        assertSame(details,result);
        verify(dao).save(favouritePlayer);

    }
    
   
    /*
    Scenario: when player already exist
    Input: AddToFavourite data
    expectation : PlayerAlreadyExistsException is thrown
    verify repository#save(player) is never called


     */
    @Test
    public void testAddToFavourite_2() throws Exception {
        AddToFavorite request=new AddToFavorite();
        request.setName("Sachi Tendulkar");
        request.setCountry("India");
        request.setPlaceOfBirth("Mumbai");
        request.setBattingStyle("Right handed batsman");
        request.setBowlingStyle("right handed bowler");
        request.setUserName("Ramya");
        FavouritedPlayer favouritePlayer=mock(FavouritedPlayer.class);
        Optional<FavouritedPlayer>optional=Optional.of(favouritePlayer);
        when(dao.findByUserNameAndExternalServerId(request.getUserName(), request.getExternalServerId()))
                .thenReturn(optional);
        Executable executable=()->{
          service.addToFavorite(request);
        };
        assertThrows(PlayerAlreadyExistsException.class,executable);
        verify(dao,never()).save(favouritePlayer);
    }
    
    
    /*
    Scenario: Player is deleted successfully
    input: RemoveFavourite requestData
    expectation : Player is successfully deleted
    verifying IFavouritedPlayerkRepository#delete(player) is called once

     */
     @Test
     public void testRemoveFavourite_1() throws Exception {
         RemoveFavorite request=new RemoveFavorite();
         request.setExternalServerId("abcd1234");
         request.setUserName("Ramya");
         FavouritedPlayer favouritePlayer=mock(FavouritedPlayer.class);
         Optional<FavouritedPlayer> optional=Optional.of(favouritePlayer);
         when(dao.findByUserNameAndExternalServerId(request.getUserName(),request.getExternalServerId()))
                 .thenReturn(optional);
        service.removeFavorite(request);
         verify(dao).delete(favouritePlayer);

     }
     
     /*
     Scenario: When player is not available, PlayerNotFoundException is thrown
     input: RemoveFavourite requestData
     expectation : PlayerNotFoundException is thrown
     verifying IFavouritedPlayerRepository#delete(player) is never called

      */
     @Test
     public void testRemoveFavourite_2() throws Exception {
    	 RemoveFavorite request=new RemoveFavorite();
         request.setExternalServerId("abcd1234");
         request.setUserName("Ramya");
         FavouritedPlayer favouritePlayer=mock(FavouritedPlayer.class);
         Optional<FavouritedPlayer> optional=Optional.empty();
         when(dao.findByUserNameAndExternalServerId(request.getUserName(),request.getExternalServerId()))
         .thenReturn(optional);
         Executable executable=()->{
             service.removeFavorite(request);
         };
         assertThrows(PlayerNotFoundException.class,executable);
         verify(dao,never()).delete(favouritePlayer);

     }




}
