package com.mindhub.homebanking;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

@SpringBootApplication
public class HomebankingApplication {

	@Autowired
	private PasswordEncoder passwordEncoder;

	public static void main(String[] args) {

		SpringApplication.run(HomebankingApplication.class, args);

		//llamo el metodo "generar cvv con clase randon"
		System.out.println("Cvv generado: " + generationCvv());
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
			Client client1 = new Client( "Melba", "Morel", "melba@mindhub.com", passwordEncoder.encode("123"));
			Client client2 = new Client( "Guillermo", "Guevara", "guiGuevara@gmail", passwordEncoder.encode("321"));
			Client clientAdmin = new Client("Fabi", "Romano", "fabiromano11@gmail.com", passwordEncoder.encode("555"));
			clientRepository.save(client1);
			clientRepository.save(client2);
			clientRepository.save(clientAdmin);

			//creo una cuenta y agrego mas cuentas con el metodo add
			Account account1 = new Account(  "VIN001",LocalDate.now(), 5000);
			Account account2 = new Account("VIN002",LocalDate.now().plusDays(1), 7000);
			client1.addAccount(account2);
			client1.addAccount(account1);
			Account account3 = new Account("VIN003",LocalDate.now().plusDays(1), 9000);
			client2.addAccount(account3);
			accountRepository.save(account1);
			accountRepository.save(account2);
			accountRepository.save(account3);


			//creo transacciones y las guardo
			Transaction transaction1 = new Transaction(TransactionType.DEBIT, -50.455, "se retiro dinero");
			Transaction transaction2 = new Transaction( TransactionType.CREDIT, 100.000, "Prestamo");
			account1.addTransaction(transaction1);
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
			ClientLoan Melba1 = new ClientLoan(400000.0,60);
			client1.addClientLoan(Melba1);
			Hipotecario.addClientLoan(Melba1);

			ClientLoan Melba2 = new ClientLoan(50000.0,12);
			client1.addClientLoan(Melba2);
			Personal.addClientLoan(Melba2);

			ClientLoan Guillermo1 = new ClientLoan(100000.0, 24);
			client2.addClientLoan(Guillermo1);
			Automotriz.addClientLoan(Guillermo1);

			ClientLoan Guillermo2 = new ClientLoan(200000.0,36);
			client2.addClientLoan(Guillermo2);
			Personal.addClientLoan(Guillermo2);

			clientLoanRepository.save(Melba1);
			clientLoanRepository.save(Melba2);
			clientLoanRepository.save(Guillermo1);
			clientLoanRepository.save(Guillermo2);

			//agrego tarjetas al cliente Melva
			Card Card1 = new Card(client1.toString(), CardType.DEBIT, CardColor.GOLD,
					"2363-5252-8545-5254", generationCvv(), LocalDate.now(), LocalDate.now().plusYears(5));
			Card Card2 = new Card( client1.toString(), CardType.CREDIT, CardColor.TITANIUM,
					"8544-2351-5462-1147", generationCvv(), LocalDate.now(), LocalDate.now().plusYears(5));
			Card Card3 = new Card( "Guillermo Guevara", CardType.CREDIT, CardColor.SILVER,
					"5696-8585-7452-1569", 548, LocalDate.now(), LocalDate.now().plusYears(5) );
			Card Card4 = new Card( client1.toString(), CardType.CREDIT, CardColor.SILVER,
					"5454-8595.7474-8547", generationCvv(), LocalDate.now(), LocalDate.now().plusYears(5));
			client1.addCard(Card1);
			cardRepository.save(Card1);
			client1.addCard(Card2);
			cardRepository.save(Card2);
			client1.addCard(Card3);
			cardRepository.save(Card3);
			client2.addCard(Card4);
			cardRepository.save(Card4);




		};


	}

	//metodo para generar un numero randon de 3 cifras, utilizando la clase "Randon"
	public static int generationCvv(){
		Random random = new Random();
		int cvv = random.nextInt(900)+100;
		return cvv;
	}


}
