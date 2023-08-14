package com.mindhub.homebanking;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository,
									  AccountRepository accountRepository,
									  TransactionRepository transactionRepository,
									  LoanRepository loanRepository,
									  ClientLoanRepository clientLoanRepository,
									  CardRepository cardRepository){
		return (args) ->{
			//creo y guardo clientes
			Client client1 = new Client("123456", "Melba", "Morel", "melba@mindhub.com");
			Client client2 = new Client("98745612", "Guillermo", "Guevara", "guiGuevara@gmail");
			clientRepository.save(client1);
			clientRepository.save(client2);

			//creo una cuenta y agrego mas cuentas con el metodo add
			Account account1 = new Account( client1, "VIN001",LocalDate.now(), 5000);
			Account account2 = new Account(client1, "VIN002",LocalDate.now().plusDays(1), 7000);
			client1.addAccount(account2);
			Account account3 = new Account(client2, "VIN003",LocalDate.now().plusDays(1), 9000);
			client2.addAccount(account3);
			accountRepository.save(account1);
			accountRepository.save(account2);
			accountRepository.save(account3);


			//creo transacciones y las guardo
			Transaction transaction1 = new Transaction(account1, TransactionType.DEBIT, -50.455, "se retiro dinero", LocalDate.now());
			account1.addTransaction(transaction1);
			Transaction transaction2 = new Transaction(account2, TransactionType.CREDIT, 100.000, "Prestamo", LocalDate.now());
			account2.addTransaction(transaction2);
			transactionRepository.save(transaction1);
			transactionRepository.save(transaction2);

			//creo prestamos y guardo
			Loan Hipotecario = new Loan("Hipotecario", 500000.0, List.of(12,24,36,48,60));
			Loan Personal = new Loan("Personal", 1000000.0, List.of(6, 12, 24));
			Loan Automotriz = new Loan("Automotriz", 300000.0, List.of(6,12 ,24,36));
			loanRepository.save(Hipotecario);
			loanRepository.save(Personal);
			loanRepository.save(Automotriz);

			//crea las entidades ClientLoan necesarias para indicar que el cliente Melba tenga:
			ClientLoan Melba1 = new ClientLoan(400000.0, List.of(60), client1, Hipotecario);
			ClientLoan Melba2 = new ClientLoan(50000.0, List.of(12),client1, Personal);
			ClientLoan Guillermo1 = new ClientLoan(100000.0, List.of(24), client2, Personal);
			ClientLoan Guillermo2 = new ClientLoan(200000.0, List.of(36), client2, Automotriz);
			clientLoanRepository.save(Melba1);
			clientLoanRepository.save(Melba2);
			clientLoanRepository.save(Guillermo1);
			clientLoanRepository.save(Guillermo2);

			//agrego tarjetas al cliente Melva
			Card Card1 = new Card(client1, client1.toString(), CardType.DEBIT, CardColor.GOLD,
					"2363-5252-8545-5254", 562, LocalDate.now(), LocalDate.now().plusYears(5));
			Card Card2 = new Card(client1, client1.toString(), CardType.CREDIT, CardColor.TITANIUM,
					"8544-2351-5462-1147", 687, LocalDate.now(), LocalDate.now().plusYears(5));
			Card Card3 = new Card(client2, "Guillermo Guevara", CardType.CREDIT, CardColor.SILVER,
					"5696-8585-7452-1569", 548, LocalDate.now(), LocalDate.now().plusYears(5) );
			Card Card4 = new Card(client1, client1.toString(), CardType.CREDIT, CardColor.SILVER,
					"5454-8595.7474-8547", 896, LocalDate.now(), LocalDate.now().plusYears(5));
			cardRepository.save(Card1);
			cardRepository.save(Card2);
			cardRepository.save(Card3);
			cardRepository.save(Card4);



		};



	}

}
