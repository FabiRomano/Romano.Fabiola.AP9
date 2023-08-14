package com.mindhub.homebanking.dtos;


import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.ClientLoan;
import com.mindhub.homebanking.models.Loan;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class ClientDTO {

    private long id;
    private String  dni;
    private String firstName;
    private String lastName;
    private String email;

    private Set<AccountDTO> accounts = new HashSet<>();
    private Set<ClientLoanDTO> loans;
    private Set<CardDTO> cards;



    public ClientDTO(Client client) {
        this.id = client.getId();
        this.firstName = client.getFirstName();
        this.lastName = client.getLastName();
        this.email = client.getEmail();
        this.accounts = client.getAccounts()
                                    .stream()
                                    .map(currentAccount -> new AccountDTO(currentAccount))
                                    .collect(Collectors.toSet());

        this.loans = client.getLoans()
                .stream()
                .map(currentLoan -> new ClientLoanDTO(currentLoan))
                .collect(Collectors.toSet());

        this.cards = client.getCards()
                .stream()
                .map(curentCard -> new CardDTO(curentCard))
                .collect(Collectors.toSet());
    }

    public long getId() {
        return id;
    }

    public String getDni() {
        return dni;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public Set<AccountDTO> getAccounts() {
        return accounts;
    }

    public Set<ClientLoanDTO> getLoans() {
        return loans;
    }

    public Set<CardDTO> getCards() {
        return cards;
    }
}
