package com.trainingapps.playerapp;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;


@SpringBootApplication
public class PlayerappApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(PlayerappApplication.class, args);
	}
   
	@Bean
	public CorsFilter coresFilter(){
		UrlBasedCorsConfigurationSource src=new UrlBasedCorsConfigurationSource();
		CorsConfiguration configuration=new CorsConfiguration();
		configuration.addAllowedHeader("*");
		configuration.addAllowedMethod("*");
		configuration.setAllowCredentials(true);
		configuration.addAllowedOrigin("http://localhost:4200");
		src.registerCorsConfiguration("/**",configuration);
		return new CorsFilter(src);
	}
}
