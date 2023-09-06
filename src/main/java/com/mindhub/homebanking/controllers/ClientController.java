package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;


@RestController
@RequestMapping("/api")
public class ClientController {

   //inyecto servicios
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ClientService clientService;

    @Autowired
    private AccountService accountService;

//Trae lista de clientes DTO
    @GetMapping("/clients")
    public List<ClientDTO> getClients() {

        return clientService.getClients();
    }

    //Trae el cliente por id
    @GetMapping("/clients/{id}")
    public ClientDTO getClientById(@PathVariable Long id) {
        return clientService.getClientById(id);
    }


    //trae clienteDTO autenticado
    @GetMapping("/clients/current")
    public ClientDTO getClientCurrentAll(Authentication authentication){
        return clientService.getClientCurrentAll(authentication.getName());


    }

    //nuevo cliente
    @PostMapping("/clients")
    public ResponseEntity<Object> register(@RequestParam String firstName,
                                           @RequestParam String lastName,
                                           @RequestParam String email,
                                           @RequestParam String password) {


        if (firstName.isBlank() || lastName.isBlank() || email.isBlank() || password.isBlank()) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }

        if (clientService.findByEmail(email) !=  null) {
            return new ResponseEntity<>("Email already in use", HttpStatus.FORBIDDEN);
        }else{
            Client client = new Client(firstName, lastName, email, passwordEncoder.encode(password));

            String numAccount;

            //genero numero random para nueva cuenta
            do {
                Random random = new Random();
                numAccount = "VIN-" + random.nextInt(90000000);
            } while (accountService.findByNumber(numAccount) != null);

            //creo cuenta nueva la agrego al cliente y guardo los nuevos registros.
            Account account = new Account(numAccount, LocalDate.now(), 0.0);
            client.addAccount(account);
            clientService.clientAdd(client);
            accountService.accountSave(account);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }




}