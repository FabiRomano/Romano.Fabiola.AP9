package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.CardDTO;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.services.CardService;
import com.mindhub.homebanking.services.ClientService;
import com.mindhub.homebanking.utils.CardsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class CardController {


    //inyecto los servicios
    @Autowired
    private CardService cardService;

    @Autowired
    private ClientService clientService;


    //trae una lista de cardsDTO
    @GetMapping("/cards")
    public List<CardDTO> getCards() {
        return cardService.getCards();

    }


    //ingresa nueva tarjeta la asocial al cliente y guarda los nuevos registros
    @PostMapping("/clients/current/cards")
    public ResponseEntity<Object> createCard (Authentication authentication,
                                              @RequestParam CardType cardType,
                                              @RequestParam CardColor cardColor) {
        Client client = clientService.findByEmail(authentication.getName());

        if (cardType == null || cardColor == null) {
            return new ResponseEntity<>("Missing data", HttpStatus.NO_CONTENT);
        }
        Set<Card> cards = client.getCards();

        int cardLimit;
        if (cardType == CardType.CREDIT || cardType == CardType.DEBIT) {
            cardLimit = 3;

            long cardsSameType = cards.stream()
                    .filter(newCard -> newCard.getType() == cardType)
                    .count();
            if (cardsSameType >= cardLimit) {
                return new ResponseEntity<>("Max amount of card reached", HttpStatus.FORBIDDEN);
            }
            boolean colorUsed = cards.stream()
                    .anyMatch(card -> card.getType() == cardType && card.getColor() == cardColor);
            if (colorUsed) {
                return new ResponseEntity<>("Color already used", HttpStatus.BAD_REQUEST);
            }
        }
                String numberCard;
        do {
            numberCard = CardsUtils.generateRandomCreditCardNumber();
        }
        while (cardService.findCardByNumber(numberCard) != null);
        int randomCvvNumber = CardsUtils.generateRandomCVV();
        Card card = new Card(client.getFirstName(), cardType, cardColor, numberCard, randomCvvNumber, LocalDate.now(), LocalDate.now().plusYears(5));
        client.addCard(card);
        cardService.cardSave(card);
        return new ResponseEntity<>("Account created succesfully", HttpStatus.CREATED);
    }




}
