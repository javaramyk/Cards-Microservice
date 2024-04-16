package com.example.microservice.card.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.microservice.card.entity.Cards;

public interface CardsRepository extends JpaRepository<Cards,Long>{
  Optional<Cards> findByMobileNumber(String mobileNumber);
  Optional<Cards> findByCardNumber(String cardNumber);
} 
 