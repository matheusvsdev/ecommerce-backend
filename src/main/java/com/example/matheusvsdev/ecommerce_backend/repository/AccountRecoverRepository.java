package com.example.matheusvsdev.ecommerce_backend.repository;

import com.example.matheusvsdev.ecommerce_backend.entities.AccountRecover;
import com.example.matheusvsdev.ecommerce_backend.entities.PasswordRecover;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface AccountRecoverRepository extends JpaRepository<AccountRecover, Long> {

    @Query(value = "SELECT * FROM tb_account_recover WHERE token = :token AND expiration > :now", nativeQuery = true)
    List<AccountRecover> searchValidToken(String token, Instant now);
}
