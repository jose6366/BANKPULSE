package com.usfq.bankpulse.repository;
import com.usfq.bankpulse.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
public interface AccountRepository extends JpaRepository<Account, Long> {}
