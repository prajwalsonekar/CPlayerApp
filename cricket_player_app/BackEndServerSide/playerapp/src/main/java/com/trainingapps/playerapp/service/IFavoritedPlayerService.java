package com.trainingapps.playerapp.service;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

import com.trainingapps.playerapp.dto.AddToFavorite;
import com.trainingapps.playerapp.dto.FavouritedPlayerDetails;
import com.trainingapps.playerapp.dto.RemoveFavorite;
import com.trainingapps.playerapp.exceptions.FavoritePlayerListEmptyException;
import com.trainingapps.playerapp.exceptions.PlayerAlreadyExistsException;
import com.trainingapps.playerapp.exceptions.PlayerNotFoundException;

@Validated
public interface IFavoritedPlayerService {
	
	FavouritedPlayerDetails addToFavorite(@Valid AddToFavorite requestData) throws PlayerAlreadyExistsException;

	void removeFavorite(@Valid RemoveFavorite requestData) throws PlayerNotFoundException;

	List<FavouritedPlayerDetails> listFavoritePlayersByUserName(@NotBlank @Length(min = 2,max =15) String userName) throws FavoritePlayerListEmptyException  ;
}
