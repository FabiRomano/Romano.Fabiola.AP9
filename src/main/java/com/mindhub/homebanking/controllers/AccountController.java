package com.mindhub.homebanking.controllers;
import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import com.mindhub.homebanking.utils.CardsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.*;
import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class AccountController {

    //inyecto los servicios.
    @Autowired
    private AccountService accountService;

    @Autowired
    private ClientService clientService;



    //trae lista de cuentasDTO
    @GetMapping("/accounts")
    public List<AccountDTO> getAccounts() {
        return accountService.getAccounts();
    }


    //trae Cuenta por ID. verifica client autenticado
    @GetMapping("/accounts/{id}")
    public ResponseEntity<Object>getAccount(@PathVariable Long id, Authentication authentication) {
        Client client = clientService.findByEmail(authentication.getName());
        Account account = accountService.findByID(id);

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

    //trae lista de cuentasDTO con clientes  autenticados
    @GetMapping("/clients/current/accounts")
    public List<AccountDTO> getCurrentAccounts(Authentication authentication){
        Client client = clientService.findByEmail(authentication.getName());
        return client.getAccounts().stream().map(AccountDTO::new).collect(toList());
    }


    //creo cuentas al cliente autenticado y guardo
        @PostMapping("/clients/current/accounts")
        public ResponseEntity<Object> createAccounts(Authentication authentication){
            Client clientAuth = clientService.findByEmail(authentication.getName());
            Set<Account> allAccounts =new HashSet<>();
            allAccounts = clientAuth.getAccounts();

        if (allAccounts.size() >= 3)
            return new ResponseEntity<>("you have three accounts, you can't create more, sorry", HttpStatus.FORBIDDEN);
         else {

             String numberAccount;
            numberAccount = CardsUtils.generateRandomVIN();
            Account account = new Account(numberAccount, LocalDate.now(), 0);
            clientAuth.addAccount(account);
            accountService.accountSave(account);
            clientService.clientSave(clientAuth);
            return new ResponseEntity<>("New account created", HttpStatus.CREATED );
        }

        }


    }

