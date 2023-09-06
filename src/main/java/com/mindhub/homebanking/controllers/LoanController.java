package com.mindhub.homebanking.controllers;


import com.mindhub.homebanking.dtos.LoanApplicationDTO;
import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class LoanController {

    @Autowired
   private LoanRepository loanRepository;
    @Autowired
   private AccountRepository accountRepository;
    @Autowired
   private ClientRepository clientRepository;
    @Autowired
   private ClientLoanRepository clientLoanRepository;
    @Autowired
   private TransactionRepository transactionRepository;

    @GetMapping("/loans")
    public List<LoanDTO> getLoans(){
        return loanRepository.findAll()
                .stream()
                .map(currentLoan -> new LoanDTO(currentLoan))
                .collect(Collectors.toList());
    }

@Transactional
@PostMapping("/loans")
    public ResponseEntity<Object>applyForLoan(@RequestBody LoanApplicationDTO loanApplicationDTO,
                                              @NotNull Authentication authentication ){
        Client client = clientRepository.findByEmail(authentication.getName());




    if (loanApplicationDTO.getLoanId() == 0 || loanApplicationDTO.getAmount()== 0 ||
            loanApplicationDTO.getPayments() == 0 ) {
        return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
    }

    Account toAccount = accountRepository.findByNumber(loanApplicationDTO.getToAccountNumber());

    if (toAccount == null){
        return new ResponseEntity<>("sorry the destination account does not exist", HttpStatus.FORBIDDEN);
    }
    if (!toAccount.getClient().getEmail().equals(client.getEmail())){
        return new ResponseEntity<>("error the account does not belong to a client", HttpStatus.FORBIDDEN);
    }

    Loan loan =loanRepository.findById(loanApplicationDTO.getLoanId()).orElse(null);
    if (loan == null){
        return new ResponseEntity<>("I'm sorry, the requested loan does not exist", HttpStatus.FORBIDDEN);
    }

    if (loanApplicationDTO.getAmount()<= 0 || loanApplicationDTO.getAmount() >= loan.getMaxAmount()){
        return new ResponseEntity<>("the requested amount is not possible", HttpStatus.FORBIDDEN);
    }

    if (!loan.getPayments().contains(loanApplicationDTO.getPayments())){
        return new ResponseEntity<>("payment is not available", HttpStatus.FORBIDDEN);
    }



        double requestedAmount = loanApplicationDTO.getAmount() + (loanApplicationDTO.getAmount() * 0.20);
        ClientLoan clientLoan = new ClientLoan(requestedAmount, loanApplicationDTO.getPayments());
        client.addClientLoan(clientLoan);
        loan.addClientLoan(clientLoan);

        String description = loan.getName() + " Loan approved. ";
        Transaction transaction = new Transaction(TransactionType.CREDIT, requestedAmount, description);
        toAccount.addTransaction(transaction);
        clientRepository.save(client);
        accountRepository.save(toAccount);
        transactionRepository.save(transaction);

    return new ResponseEntity<>("the credit was successfully approved" , HttpStatus.CREATED);
}


}
