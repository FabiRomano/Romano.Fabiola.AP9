package com.mindhub.homebanking.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Account {
    //Aqui comienzan los atributos
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="clientId")
    private Client client;
    private String number;
    private LocalDate creationDate = LocalDate.now();

    private double balance;

    //uno cuenta con transacciones
    @OneToMany(mappedBy="account", fetch=FetchType.EAGER)
    private Set<Transaction> transactions = new HashSet<>();


    //aqui comienzan los metodos.
    public Account() {
    }


    public Account(Client client, String number, LocalDate creationDate, double balance) {
        this.client = client;
        this.number = number;
        this.creationDate = creationDate;
        this.balance = balance;
    }

    public long getId() {
        return id;
    }

    @JsonIgnore
    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;

    }

    public Set<Transaction> getTransactions() {
        return transactions;
    }
    public void addTransaction(Transaction transaction){
        transaction.setAccount(this);
        transactions.add(transaction);
    }
    public void setTransactions(Set<Transaction> transactions) {
        this.transactions = transactions;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }


}
