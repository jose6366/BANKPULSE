package com.usfq.bankpulse.repository;

import com.usfq.bankpulse.model.Account;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
  List<Account> findByOwnerUsernameOrderByAccountNumber(String ownerUsername);

  @Lock(LockModeType.PESSIMISTIC_WRITE)
  @Query("select a from Account a where a.id = :id and a.ownerUsername = :username")
  Optional<Account> findOwnedForUpdate(@Param("id") Long id, @Param("username") String username);
}
