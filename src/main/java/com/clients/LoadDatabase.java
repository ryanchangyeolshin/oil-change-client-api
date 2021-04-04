package com.clients;

import com.clients.client.items.Client;
import com.clients.client.repositories.ClientRepository;
import com.clients.transactions.items.Transaction;
import com.clients.transactions.repositories.TransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(ClientRepository clientRepository, TransactionRepository transactionRepository) {
        return args -> {
            Client ryan = new Client("Ryan Shin", "1111 Main St", "ryan.shin@gmail.com", "Las Vegas", "NV", "USA", "(702)-111-2222", "LLC");
            Client bobby = new Client("Bobby Mills", "1113 Main St", "bobby.mills@gmail.com", "Los Angeles", "CA", "USA", "(818)-112-2332", "Sole Proprietorship");
            Client ben = new Client("Ben Johnson", "1333 Phoenix St", "ben.johnson@gmail.com", "Phoenix", "AZ", "USA", "(313)-332-2392", "S Corp");

            log.info("Preloading " + clientRepository.save(ryan));
            log.info("Preloading " + clientRepository.save(bobby));
            log.info("Preloading " + clientRepository.save(ben));

            log.info("Preloading " + transactionRepository.save(new Transaction(ryan, 50.00)));
            log.info("Preloading " + transactionRepository.save(new Transaction(ryan, 80.01)));
            log.info("Preloading " + transactionRepository.save(new Transaction(ryan, 60.53)));

            log.info("Preloading " + transactionRepository.save(new Transaction(bobby, 14.83)));
            log.info("Preloading " + transactionRepository.save(new Transaction(bobby, 72.53)));

            log.info("Preloading " + transactionRepository.save(new Transaction(ben, 99.99)));
        };
    }
}
