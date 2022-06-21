package com.trainingapps.playerapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.trainingapps.playerapp.dao.IFavoritedPlayerRepository;
import com.trainingapps.playerapp.dto.AddToFavorite;
import com.trainingapps.playerapp.dto.FavouritedPlayerDetails;
import com.trainingapps.playerapp.dto.RemoveFavorite;
import com.trainingapps.playerapp.entity.FavouritedPlayer;
import com.trainingapps.playerapp.exceptions.FavoritePlayerListEmptyException;
import com.trainingapps.playerapp.exceptions.PlayerAlreadyExistsException;
import com.trainingapps.playerapp.exceptions.PlayerNotFoundException;
import com.trainingapps.playerapp.util.FavoritePlayerUtil;

@Service
public class FavoritedPlayerServiceImpl implements IFavoritedPlayerService{
	
	@Autowired
	private IFavoritedPlayerRepository dao ;
	
	@Autowired
	private FavoritePlayerUtil utility;
	
	
	public String generateNewID(String userName , String externalServerId) {
		String autoId =externalServerId + "-u-"+userName;
		return autoId;
	}
	
	@Override
	public FavouritedPlayerDetails addToFavorite(AddToFavorite requestData) throws PlayerAlreadyExistsException{
		// Generated Id
		String id = generateNewID(requestData.getUserName(), requestData.getExternalServerId());
		  Optional<FavouritedPlayer> optional = dao.findByUserNameAndExternalServerId(requestData.getUserName(), requestData.getExternalServerId());
	        if (optional.isPresent()) {
	            throw new PlayerAlreadyExistsException("Player already exists");
	        }
		// adding To Favorited Players 
		FavouritedPlayer player = utility.toFavouritedPlayer(requestData);
		player.setId(id);
		
		// Saving Player 
		  player = dao.save(player);
		// converting To Favorite Player Details 
		FavouritedPlayerDetails playerDetails = utility.convertToFavoritePlayerDetails(player);
		return playerDetails;
	}
	
	public FavouritedPlayer findPlayer(String userName,String externalServerId) throws PlayerNotFoundException {
		Optional<FavouritedPlayer> optional =  dao.findByUserNameAndExternalServerId(userName, externalServerId);
		if(!optional.isPresent()) {
			throw new PlayerNotFoundException("Player Not Found");
		}
		FavouritedPlayer playerGot = optional.get();
		return playerGot;
	}
	
	@Override
	public void removeFavorite(RemoveFavorite requestData) throws PlayerNotFoundException {
		FavouritedPlayer playerFound = findPlayer(requestData.getUserName(),requestData.getExternalServerId());
		dao.delete(playerFound);
	
	}

	@Override
	public List<FavouritedPlayerDetails> listFavoritePlayersByUserName(String userName) throws FavoritePlayerListEmptyException {
		
		List<FavouritedPlayer> playerList = dao.findByUserName(userName);
		
		List<FavouritedPlayerDetails> playerDetailsList = utility.toFavoritePlayerDetailsList(playerList);
		if(playerDetailsList.isEmpty()) {
			throw new FavoritePlayerListEmptyException("List is empty");
		}
		return playerDetailsList;
	}

	
}
