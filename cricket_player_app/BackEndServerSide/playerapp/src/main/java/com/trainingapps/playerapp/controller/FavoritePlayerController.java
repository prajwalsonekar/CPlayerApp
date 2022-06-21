package com.trainingapps.playerapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trainingapps.playerapp.dto.AddToFavorite;
import com.trainingapps.playerapp.dto.FavouritedPlayerDetails;
import com.trainingapps.playerapp.dto.RemoveFavorite;
import com.trainingapps.playerapp.exceptions.FavoritePlayerListEmptyException;
import com.trainingapps.playerapp.exceptions.PlayerAlreadyExistsException;
import com.trainingapps.playerapp.exceptions.PlayerNotFoundException;
import com.trainingapps.playerapp.service.IFavoritedPlayerService;

@RequestMapping("/favouritePlayers")
@RestController
public class FavoritePlayerController {
	
	@Autowired
	IFavoritedPlayerService service;
	
	@PostMapping("/add")
	public FavouritedPlayerDetails addFavoritePlayer(@RequestBody AddToFavorite requestPlayer) throws PlayerAlreadyExistsException {
		FavouritedPlayerDetails playerdetails = service.addToFavorite(requestPlayer);
		return playerdetails;
	}	

	@DeleteMapping("/delete")
	public void removeFavoritePlayer(@RequestBody RemoveFavorite requestPlayer) throws PlayerNotFoundException {
		 service.removeFavorite(requestPlayer);
	}	
	
	@GetMapping("/allFavoritedPlayer/{userName}")
	public List<FavouritedPlayerDetails> listOfFavoritePlayer(@PathVariable String userName) throws FavoritePlayerListEmptyException {
		List<FavouritedPlayerDetails> playerDetailsList = service.listFavoritePlayersByUserName(userName);
		return playerDetailsList;
	}
}
