package com.mindhub.homebanking.utils;

import java.util.Random;

public class CardsUtils {

    //metodo static para generar numero de cuenta, implementado en "CLIENT" y "ACCOUNTS" controllers.
    public static String generateRandomVIN() {
        // Crear una instancia de la clase Random
        Random random = new Random();
        // Generar un número aleatorio de 8 dígitos
        long numberAl = random.nextLong() % 90000000L + 10000000L; // Genera números entre 10,000,000 y 99,999,999
        // Asegurarse de que el número sea positivo
        if (numberAl < 0) {

            numberAl = -numberAl;
        }
        // Concatenar "VIN" al número aleatorio y devolverlo como una cadena
        String vin = "VIN-" + numberAl;

        return vin;
    }



//metodo static para generar numero para tarjeta de credito utilizado en card controller

    public static String generateRandomCreditCardNumber() {
        // Crear una instancia de la clase Random
        Random random = new Random();

        // Generar 4 grupos de 4 dígitos aleatorios
        StringBuilder creditCardNumber = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            int grupo = random.nextInt(10000); // Generar un número de 4 dígitos
            String grupoStr = String.format("%04d", grupo); // Asegurarse de que tenga 4 dígitos
            creditCardNumber.append(grupoStr);

            if (i < 3) {
                creditCardNumber.append(" "); // Agregar un espacio entre los grupos
            }
        }

        return creditCardNumber.toString();
    }

    //metodo static para generar cvv utilizado en CARD controller
    public static int generateRandomCVV() {
        // Crear una instancia de la clase Random
        Random random = new Random();

        // Generar un número aleatorio de 3 dígitos
        int cvv = random.nextInt(1000); // Genera números entre 0 y 999

        return cvv;
    }

}
