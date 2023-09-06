package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.models.Account;
import java.util.List;

public interface AccountService {

    //trae lista de cuentasDTO
    List<AccountDTO> getAccounts();

    //trae Cuenta por ID
    Account findByID (Long id);

    //trae numero de cuenta verificado
    Account findByNumber(String number);


    //guardo nueva cuenta
    void accountSave(Account account);



}
