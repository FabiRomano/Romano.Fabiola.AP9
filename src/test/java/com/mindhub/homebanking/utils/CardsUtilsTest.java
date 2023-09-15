package com.mindhub.homebanking.utils;

import com.mindhub.homebanking.services.CardService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest
class CardsUtilsTest {
    @Autowired
    private CardService cardService;
/*

    //Test unitarios

    //******NumerAcount con VIN******
    //verifico que String vinCardNumber no sea null
    @Test
    void generateRandomVIN() {
        String vinCardNumber = CardsUtils.generateRandomVIN();
        assertThat(vinCardNumber, is(not(emptyOrNullString())));
    }

    //verifico que los VIN generados deben ser diferentes
    @Test
    void generateRandomVINDifferent() {
        String vinCardNumber1 = CardsUtils.generateRandomVIN();
        String vinCardNumber2 = CardsUtils.generateRandomVIN();
        assertNotEquals(vinCardNumber1, vinCardNumber2);
    }

    // Verifico que comience con "VIN-"
    @Test
    void generateRandomVINValidFormat() {
        String vinCardNumber = CardsUtils.generateRandomVIN();
        assertTrue(vinCardNumber.startsWith("VIN-"));
    }



    //****************NumerCardRandom*****************
    //verifico que String cardNumber no sea null.
    @Test
    void generateRandomCreditCardNumber() {
        String cardNumber = CardsUtils.generateRandomCreditCardNumber();
        assertThat(cardNumber,is(not(emptyOrNullString())));
    }

    //verifico que el  String cardNumber sea diferente
    @Test
    void generateRandomCreditCardNumberDifferent() {
        String cardNumber = CardsUtils.generateRandomCreditCardNumber();
        String cardNumber1 = CardsUtils.generateRandomCreditCardNumber();
        assertNotEquals(cardNumber, cardNumber1);
    }

    // Verifica el formato con espacios.
    @Test
    void generateRandomCreditCardNumberValidFormatWithSpaces() {
        String cardNumber = CardsUtils.generateRandomCreditCardNumber();
        assertThat(cardNumber, matchesPattern("\\d{4} \\d{4} \\d{4} \\d{4}"));
    }



    //*****************RandonCvv**********************
    //verifico que el CVV no sea generado menor a 0
    @Test
    void generateRandomCVV() {
        int cvvNumber = CardsUtils.generateRandomCVV();
        assertTrue(cvvNumber > 0);

    }

    // CVV válido de 3 dígitos
    @Test
    void generateRandomCVVInRange() {
        int cvvNumber = CardsUtils.generateRandomCVV();
        assertTrue(cvvNumber >= 100 && cvvNumber <= 999);
    }

    //verifico que los CVV generados deben ser diferentes
    @Test
    void generateRandomCVVIsDifferent() {
        int cvvNumber1 = CardsUtils.generateRandomCVV();
        int cvvNumber2 = CardsUtils.generateRandomCVV();
        assertNotEquals(cvvNumber1, cvvNumber2);
    }

*/

}