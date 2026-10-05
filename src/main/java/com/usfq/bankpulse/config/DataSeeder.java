package com.usfq.bankpulse.config;

import com.usfq.bankpulse.model.Account;
import com.usfq.bankpulse.repository.AccountRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.math.BigDecimal;

@Configuration
public class DataSeeder {
  @Bean
  CommandLineRunner seedBankpulse(AccountRepository repo) {
    return args -> {
      if (repo.count() == 0) {
        repo.save(new Account("001-000123", "Cliente Demo", "demo", new BigDecimal("5000.00")));
        repo.save(new Account("001-000456", "Cliente Demo", "demo", new BigDecimal("2250.50")));
      }
    };
  }
}
