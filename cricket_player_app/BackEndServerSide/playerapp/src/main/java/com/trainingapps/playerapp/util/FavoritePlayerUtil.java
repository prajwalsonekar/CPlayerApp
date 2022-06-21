package com.trainingapps.playerapp.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Component;

import com.trainingapps.playerapp.dto.AddToFavorite;
import com.trainingapps.playerapp.dto.FavouritedPlayerDetails;
import com.trainingapps.playerapp.entity.FavouritedPlayer;

@Component
public class FavoritePlayerUtil {
	
	public FavouritedPlayer toFavouritedPlayer(AddToFavorite requestData) {
		FavouritedPlayer player=new FavouritedPlayer();
		player.setExternalServerId(requestData.getExternalServerId());
		player.setUserName(requestData.getUserName());
		player.setName(requestData.getName());
		player.setBattingStyle(requestData.getBattingStyle());
		player.setBowlingStyle(requestData.getBowlingStyle());
		player.setPlaceOfBirth(requestData.getPlaceOfBirth());
		player.setCountry(requestData.getCountry());
		player.setPlayerImg(requestData.getImageUrl());
		player.setStats(requestData.getStats());
		return player;
	}
	public FavouritedPlayerDetails convertToFavoritePlayerDetails(FavouritedPlayer requestData) {
		
		FavouritedPlayerDetails playerDetails = new FavouritedPlayerDetails();
	
		playerDetails.setExternalServerId(requestData.getExternalServerId());
		playerDetails.setName(requestData.getName());
		playerDetails.setBattingStyle(requestData.getBattingStyle());
		playerDetails.setBowlingStyle(requestData.getBowlingStyle());
		playerDetails.setPlaceOfBirth(requestData.getPlaceOfBirth());
		playerDetails.setCountry(requestData.getCountry());
		playerDetails.setImageUrl(requestData.getPlayerImg());
		playerDetails.setStats(requestData.getStats());
		return playerDetails;
	}

	public List<FavouritedPlayerDetails> toFavoritePlayerDetailsList(Collection<FavouritedPlayer> requestData) {
		List<FavouritedPlayerDetails> playerDetailsList = new ArrayList<>();
		for (FavouritedPlayer iterate : requestData) {
			FavouritedPlayerDetails playerDetail = convertToFavoritePlayerDetails(iterate);
			playerDetailsList.add(playerDetail);
		}
		return playerDetailsList;
	}
}
