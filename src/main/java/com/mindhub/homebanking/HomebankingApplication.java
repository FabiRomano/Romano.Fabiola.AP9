package com.mindhub.homebanking;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository){
		return (args) ->{
			Client client1 = new Client("123456", "Melba", "Morel", "melba@mindhub.com");
			clientRepository.save(client1);

			Client client2 = new Client("98745612", "Guillermo", "Guevara", "guiGuevara@gmail");
			clientRepository.save(client2);

	//creo una cuenta
			Account account1 = new Account( client1, "VIN001",LocalDate.now(), 5000);
			accountRepository.save(account1);

	//agrego otra cuenta con el metodo add
			Account account2 = new Account(client1, "VIN002",LocalDate.now().plusDays(1), 7000);
			client1.addAccount(account2);
			accountRepository.save(account2);


			Account account3 = new Account(client2, "VIN003",LocalDate.now().plusDays(1), 9000);
			client2.addAccount(account3);
			accountRepository.save(account3);


		};



	}

}
