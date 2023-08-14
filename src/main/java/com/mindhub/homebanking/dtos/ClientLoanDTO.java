package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.ClientLoan;
import com.mindhub.homebanking.models.Loan;
import java.util.List;
import java.util.stream.Collectors;

public class ClientLoanDTO {

    private long id;

    private long loanId;
    private double amount;
    private List<Integer> payments;
    private String name;


    public ClientLoanDTO(ClientLoan clientLoan) {
        this.id = clientLoan.getId();
        this.name = clientLoan.getLoan().getName();
        this.loanId = clientLoan.getLoan().getId();
        this.amount = clientLoan.getAmount();
        this.payments = clientLoan.getPayments()
                .stream()
                .map(currentPayments -> new Integer(currentPayments))
                .collect(Collectors.toList());


    }

    public long getId() {
        return id;
    }

    public long getLoanId() {
        return loanId;
    }

    public double getAmount() {
        return amount;
    }

    public List<Integer> getPayments() {
        return payments;
    }

    public String getName() {
        return name;
    }
}
