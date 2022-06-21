package com.trainingapps.playerapp.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.trainingapps.playerapp.entity.FavouritedPlayer;

public interface IFavoritedPlayerRepository extends MongoRepository<FavouritedPlayer,String> {
	Optional<FavouritedPlayer> findByUserNameAndExternalServerId(String userName , String id);
	List<FavouritedPlayer> findByUserName(String userName);
}
