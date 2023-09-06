package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.models.Loan;

import java.util.List;

public interface LoanService {

    //trae una lista de LOANDTO
    List<LoanDTO> getLoans();

    //trae Loan por ID
    Loan findLoanById(Long loanId);



}
