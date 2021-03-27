package com.clients.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(ClientRepository repository) {
        return args -> {
            log.info("Preloading " + repository.save(new Client("Ryan Shin", "1111 Main St", "ryan.shin@gmail.com", "Las Vegas", "NV", "USA", "(702)-111-2222", "LLC")));
            log.info("Preloading " + repository.save(new Client("Bobby Mills", "1113 Main St", "bobby.mills@gmail.com", "Los Angeles", "CA", "USA", "(818)-112-2332", "Sole Proprietorship")));
            log.info("Preloading " + repository.save(new Client("Ben Johnson", "1333 Phoenix St", "ben.johnson@gmail.com", "Phoenix", "AZ", "USA", "(313)-332-2392", "S Corp")));
        };
    }
}
