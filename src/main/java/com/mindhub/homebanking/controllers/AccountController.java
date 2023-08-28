package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class AccountController {

    @Autowired
    private AccountRepository accountRepository;
    @GetMapping("/accounts")
    public List<AccountDTO> getAccounts() {
        List<Account> allAccounts = accountRepository.findAll();

        List<AccountDTO> converterList = allAccounts
                .stream()
                .map(currentAccount -> new AccountDTO(currentAccount))
                .collect(Collectors.toList());

        return converterList;

    }


    @Autowired
    ClientRepository clientRepository;
    @GetMapping("/accounts/{id}")
    public AccountDTO getAccountById(@PathVariable Long id, Authentication authentication){
        Optional<Account> account = accountRepository.findById(id);
        return new AccountDTO(account.get());
    }

    @RequestMapping("/accounts/{id}")
    public ResponseEntity<Object>getAccount(@PathVariable Long id, Authentication authentication) {
        Client client = clientRepository.findByEmail(authentication.getName());
        Account account = accountRepository.findById(id).orElse(null);

        if (account == null){
            return new ResponseEntity<>("Account not found", HttpStatus.BAD_GATEWAY);
        }
        if (account.getClient().equals(client)) {
            AccountDTO accountDTO = new AccountDTO(account);
            return new ResponseEntity<>(accountDTO, HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>("This Account not is your, Im sorry", HttpStatus.I_AM_A_TEAPOT);
                }

        }


        @PostMapping("/clients/current/accounts")
        public ResponseEntity<Object> createAccounts(@NotNull Authentication authentication){
            Client clientAuth = clientRepository.findByEmail(authentication.getName());
            Set<Account> allAccounts =new HashSet<>();
            allAccounts = clientAuth.getAccounts();

        if (allAccounts.size() >= 3)
            return new ResponseEntity<>("you have three accounts, you can't create more, sorry", HttpStatus.FORBIDDEN);
         else {

             String numberAccount;
            Random random= new Random();
            numberAccount = "VIN" + random.nextInt(90000000);
            Account account = new Account(numberAccount, LocalDate.now(), 0);
            clientAuth.addAccount(account);
            accountRepository.save(account);
            clientRepository.save(clientAuth);
            return new ResponseEntity<>("New account created", HttpStatus.CREATED );
        }

        }


    }

