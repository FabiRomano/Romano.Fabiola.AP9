package com.mindhub.homebanking.services;

import com.mindhub.homebanking.models.Transaction;

public interface TransactionService {

    //guardo transaccion
    void transactionSave(Transaction transaction);

}
