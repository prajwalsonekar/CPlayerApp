package com.trainingapps.playerapp;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.http.MediaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trainingapps.playerapp.controller.FavoritePlayerController;
import com.trainingapps.playerapp.dto.AddToFavorite;
import com.trainingapps.playerapp.dto.FavouritedPlayerDetails;
import com.trainingapps.playerapp.dto.RemoveFavorite;
import com.trainingapps.playerapp.exceptions.FavoritePlayerListEmptyException;
import com.trainingapps.playerapp.exceptions.PlayerAlreadyExistsException;
import com.trainingapps.playerapp.exceptions.PlayerNotFoundException;
import com.trainingapps.playerapp.service.IFavoritedPlayerService;

@WebMvcTest(FavoritePlayerController.class)
class FavouritePlayerControllerTest {


    private FavouritedPlayerDetails response;

    @MockBean
    IFavoritedPlayerService service;

    @Autowired
    MockMvc mvc;


    @BeforeEach
    public void setUp() {
        response = new FavouritedPlayerDetails();
        response.setName("Kapil Dev");
        response.setCountry("India");
        response.setDateOfBirth("6 January 1959");
        response .setPlaceOfBirth("Chandigarh");
        response.setBattingStyle("Right handed");
        response.setBowlingStyle("right handed");
        
    }

    @AfterEach
    public void tearDown() {
        response = null;
    }

    /**
     * scenario: When list is fetched successfully
     * input : userName=Prajwal
     * expectation: List is fetched successfully. status 200/OK
     */
    @Test
    public void testFindAll_1() throws Exception {
        String userName = "Prajwal";
        List<FavouritedPlayerDetails> players = new ArrayList<>();
        players.add(response);
        when(service.listFavoritePlayersByUserName(userName)).thenReturn(players);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(players);
        System.out.println("**created json=" + json);
        String url = "/favouritePlayers/allFavoritedPlayer/" + userName;
        mvc.perform(get(url))
        		.andExpect(status().isOk())
                .andExpect(content().json(json));


    }
    /**
     * scenario: No players is found in the favourite list
     * input : userName: Ramya
     * expectation: FavoritePlayerListEmptyException is thrown. status 404/NOT_FOUND
     */
    @Test
    public void testFindAll_2() throws Exception {
        String userName = "Ramya";
        String msg = "No player found";
        FavoritePlayerListEmptyException e = new FavoritePlayerListEmptyException(msg);
        when(service.listFavoritePlayersByUserName(userName)).thenThrow(e);
        String url = "/favouritePlayers/allFavoritedPlayer/" + userName;
        mvc.perform(get(url))
                .andExpect(status().isNotFound())
                .andExpect(content().string(msg));

    }
    
    /**
     * scenario: When player is added successfully
     * input : AddToFavorite
     * expectation:  Player is added successfully. status 200/OK
     */
    @Test
    public void testAdd_1() throws Exception {

        AddToFavorite request = new AddToFavorite();
        request.setUserName("Harsh");
        request.setName("Rishab pant");
        request.setCountry("India");
        request.setPlaceOfBirth("Roorkee");
        request.setBattingStyle("Left handed");
        request.setBowlingStyle("Fast-bowling");
        when(service.addToFavorite(request)).thenReturn(response);
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonRequest = objectMapper.writeValueAsString(request);
        String jsonResponse = objectMapper.writeValueAsString(response);
        String url = "/favouritePlayers/add";
        mvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk());
            

    }
    
    /**
     * scenario: When player already exists
     * input : AddToFavorite
     * expectation:  PlayerAlreadyExistsException is thrown. status 409/CONFLICT
     */
    @Test
    public void testAdd_2() throws Exception {

        AddToFavorite request = new AddToFavorite();
        request.setUserName("Harsh");
        request.setName("Rishab pant");
        request.setCountry("India");
        request.setPlaceOfBirth("Roorkee");
        request.setBattingStyle("Left handed");
        request.setBowlingStyle("Fast-bowling");
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonRequest = objectMapper.writeValueAsString(request);
        String msg = "Track is already in the favourite list";
        PlayerAlreadyExistsException e = new PlayerAlreadyExistsException(msg);
        when(service.addToFavorite(any(AddToFavorite.class))).thenThrow(e);
        String url = "/favouritePlayers/add";
        mvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isConflict())
                .andExpect(content().string(msg));

    }
    /**
     * scenario: When player is removed successfully
     * input : RemovedFavorite
     * expectation:  Player is removed successfully. status 200/OK
     */
    @Test
    public void testRemove_1() throws Exception {
        RemoveFavorite request = new RemoveFavorite();
        request.setUserName("Harsh");
       
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonRequest = objectMapper.writeValueAsString(request);
        String url = "/favouritePlayers/delete";
        mvc.perform(delete(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk());
       
    }
    /**
     * scenario: When player is not found
     * input : RemovedFavourite
     * expectation:  PlayerNotFoundException. status 404/NOT_FOUND
     */
    @Test
    public void testRemove_2() throws Exception {
        RemoveFavorite request = new RemoveFavorite();
        request.setUserName("Harikrishna");
        request.setExternalServerId("abc");
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonRequest = objectMapper.writeValueAsString(request);
        String url = "/favouritePlayers/delete";
        String msg = "Player not found";
        PlayerNotFoundException e = new PlayerNotFoundException(msg);
        doThrow(e).when(service).removeFavorite(request);
        mvc.perform(delete(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
               .andExpect(status().isOk())
                .andExpect(content().string(""));
       
    }

}
