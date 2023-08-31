package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TransactionController {
    @Autowired
    ClientRepository clientRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    TransactionRepository transactionRepository;


    @Transactional
    @PostMapping("/transactions")
    public ResponseEntity<Object> createTransaction(Authentication authentication,
                                                    @RequestParam String fromAccountNumber,
                                                    @RequestParam String toAccountNumber,
                                                    @RequestParam Double amount,
                                                    @RequestParam String description) {

        Account rootAccount = accountRepository.findByNumber(fromAccountNumber);
        Account destinationAccount = accountRepository.findByNumber(toAccountNumber);
        Client client = clientRepository.findByEmail(authentication.getName());


        if (fromAccountNumber.isBlank() || toAccountNumber.isBlank() || description.isBlank()) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }

        if (amount <= 0) {
            return new ResponseEntity<>("your balance is equal to or you cannot perform this operation", HttpStatus.FORBIDDEN);
        }

        if (rootAccount == null || destinationAccount == null) {
            return new ResponseEntity<>("the source account does not exist", HttpStatus.FORBIDDEN);
        }


        if (rootAccount.equals(destinationAccount)) {
            return new ResponseEntity<>("you can't perform this operation", HttpStatus.FORBIDDEN);
        }

        if (!rootAccount.getClient().equals(client)) {
            return new ResponseEntity<>("I'm sorry you can't access this account", HttpStatus.FORBIDDEN);
        }

        if (rootAccount.getBalance() < amount) {
            return new ResponseEntity<>("you don't have enough balance", HttpStatus.FORBIDDEN);
        }

        String descripRootAccount = description + " " + toAccountNumber;
        Transaction rootAccountTransaction = transactionRepository.save(new Transaction(TransactionType.DEBIT, -amount, descripRootAccount));
        rootAccount.addTransaction(rootAccountTransaction);
        transactionRepository.save(rootAccountTransaction);

        String descripDestinationAccount = description + " " + fromAccountNumber;
        Transaction destinationAccountTransaction = transactionRepository.save(new Transaction(TransactionType.CREDIT, amount, descripDestinationAccount));
        destinationAccount.addTransaction(destinationAccountTransaction);
        transactionRepository.save(destinationAccountTransaction);


        accountRepository.save(rootAccount);
        accountRepository.save(destinationAccount);
        return new ResponseEntity<>("Successful transfer", HttpStatus.CREATED);
    }



}
