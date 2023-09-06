package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.CardDTO;
import com.mindhub.homebanking.models.Card;

import java.util.List;

public interface CardService {

    //trae una lista de cardsDTO
    List<CardDTO> getCards();

    //busca por numero de tarjeta "card"
    Card findCardByNumber(String randomNumber);

    //guarda nuevo registro
    void cardSave(Card card);





}
