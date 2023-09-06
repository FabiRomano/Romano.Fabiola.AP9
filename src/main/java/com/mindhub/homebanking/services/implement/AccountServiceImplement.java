package com.mindhub.homebanking.services.implement;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class AccountServiceImplement implements AccountService {
    @Autowired
    private AccountRepository accountRepository;


    //trae lista de cuentasDTO
    @Override
    public List<AccountDTO> getAccounts() {
        return accountRepository.findAll().stream().map(AccountDTO::new).collect(toList());
    }

    //trae Cuenta por ID
    @Override
    public Account findByID(Long id) {
        return accountRepository.findById(id).orElse(null);
    }

    //busca la cuenta por su numero
    @Override
    public Account findByNumber(String number) {
        return accountRepository.findByNumber(number);
    }

    @Override
    public void accountSave(Account account) {
        accountRepository.save(account);
    }


}
