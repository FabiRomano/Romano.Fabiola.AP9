package com.mindhub.homebanking.services.implement;

import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.repositories.TransactionRepository;
import com.mindhub.homebanking.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImplement implements TransactionService {
    @Autowired
    TransactionRepository transactionRepository;


    //guardo la transaccion en el repo del mismo.
    @Override
    public void transactionSave(Transaction transaction) {
       transactionRepository.save(transaction);
    }
}
