package com.example.microservice.card.service.implMethods;

import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.microservice.card.constants.CardsConstants;
import com.example.microservice.card.dto.CardsDto;
import com.example.microservice.card.entity.Cards;
import com.example.microservice.card.exception.CardAlreadyExistsException;
import com.example.microservice.card.exception.ResourceNotFoundException;
import com.example.microservice.card.mapper.CardsMapper;
import com.example.microservice.card.repository.CardsRepository;
import com.example.microservice.card.service.ICardsService;

public class CardsServiceImpl implements ICardsService{

	@Autowired
	private CardsRepository cardsRepository;
	@Override
	public void createCard(String mobileNumber) {
		// TODO Auto-generated method stub
		Optional<Cards> optionalCards = cardsRepository.findByMobileNumber(mobileNumber);
		if(optionalCards.isPresent()) {
            throw new CardAlreadyExistsException("Card already registered with given mobileNumber "+mobileNumber);
		}
		cardsRepository.save(createNewCard(mobileNumber));
		
	}

	private Cards createNewCard(String mobileNumber) {
		// TODO Auto-generated method stub
		Cards newCard = new Cards();
        long randomCardNumber = 100000000000L + new Random().nextInt(900000000);
		newCard.setCardNumber(Long.toString(randomCardNumber));
		newCard.setMobileNumber(mobileNumber);
		newCard.setCardType(CardsConstants.CREDIT_CARD);
		newCard.setTotalLimit(CardsConstants.NEW_CARD_LIMIT);
		newCard.setAmountUsed(0);
		newCard.setAvailableAmount(CardsConstants.NEW_CARD_LIMIT);
        return newCard;
	}

	@Override
	public CardsDto fetchCard(String mobileNumber) {
		// TODO Auto-generated method stub
		 Cards cards = cardsRepository.findByMobileNumber(mobileNumber).orElseThrow(
	                () -> new ResourceNotFoundException("Card", "mobileNumber", mobileNumber)
	        );
	        return CardsMapper.mapToCardsDto(cards, new CardsDto());
	}

	@Override
	public boolean updateCard(CardsDto cardDto) {
		// TODO Auto-generated method stub
		Cards cards = cardsRepository.findByCardNumber(cardDto.getCardNumber()).orElseThrow(
				()-> new ResourceNotFoundException("Card","CardNumber",cardDto.getCardNumber())
				);
		CardsMapper.mapToCards(cardDto, cards);
		cardsRepository.save(cards);
		return true;
	}

	@Override
	public boolean deleteCard(String mobileNumber) {
		// TODO Auto-generated method stub
		Cards cards = cardsRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Card", "mobileNumber", mobileNumber)
        );
		cardsRepository.deleteById(cards.getCardId());
		return true;
	}

}
