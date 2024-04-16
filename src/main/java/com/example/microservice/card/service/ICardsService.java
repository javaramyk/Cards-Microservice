package com.example.microservice.card.service;
import com.example.microservice.card.dto.CardsDto;

public interface ICardsService {

	void createCard(String mobileNumber);
	CardsDto fetchCard(String mobileNumber);
	boolean updateCard(CardsDto cardDto);
	boolean deleteCard(String mobileNumber);
}
