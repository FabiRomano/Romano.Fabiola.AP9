package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import com.mindhub.homebanking.services.TransactionService;
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
    //inyecto los servicios

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private AccountService accountService;



    //creo transaccion/cliente autenticado/cuentas asociadas
    @Transactional
    @PostMapping("/transactions")
    public ResponseEntity<Object> createTransaction(Authentication authentication,
                                                    @RequestParam String fromAccountNumber,
                                                    @RequestParam String toAccountNumber,
                                                    @RequestParam Double amount,
                                                    @RequestParam String description) {

        Account rootAccount = accountService.findByNumber(fromAccountNumber);
        Account destinationAccount = accountService.findByNumber(toAccountNumber);
        Client client = clientService.findByEmail(authentication.getName());


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


     //agrego cambios y guardo transaccion
        String descripRootAccount = description + " " + toAccountNumber;
        Transaction rootAccountTransaction = new Transaction(TransactionType.DEBIT, -amount, descripRootAccount);
        rootAccount.addTransaction(rootAccountTransaction);
        transactionService.transactionSave(rootAccountTransaction);

    //agrego cambios en cuenta destino y guardo transaccion
        String descripDestinationAccount = description + " " + fromAccountNumber;
        Transaction destinationAccountTransaction = new Transaction(TransactionType.CREDIT, amount, descripDestinationAccount);
        destinationAccount.addTransaction(destinationAccountTransaction);
        transactionService.transactionSave(destinationAccountTransaction);

        // Actualiza los saldos de las cuentas
        rootAccount.setBalance(rootAccount.getBalance() - amount);
        destinationAccount.setBalance(destinationAccount.getBalance() + amount);

    //guardo los cambios en las cuentas
        accountService.accountSave(rootAccount);
        accountService.accountSave(destinationAccount);
        return new ResponseEntity<>("Successful transfer", HttpStatus.CREATED);
    }



}
