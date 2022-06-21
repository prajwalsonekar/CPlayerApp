package com.trainingapps.playerapp.dto;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

public class RemoveFavorite {
	@NotBlank
	@Length(max=50)
	private String externalServerId;
	@NotBlank
	@Length(min = 2,max =15)
	private String userName;
	
	
	public String getExternalServerId() {
		return externalServerId;
	}
	public void setExternalServerId(String externalServerId) {
		this.externalServerId = externalServerId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	
	

}
