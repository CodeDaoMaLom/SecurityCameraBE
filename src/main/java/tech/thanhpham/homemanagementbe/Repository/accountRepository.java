package tech.thanhpham.homemanagementbe.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.thanhpham.homemanagementbe.Entity.Account;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface accountRepository extends JpaRepository<Account, UUID> {
    Optional<Account> findByUsername(String username);
}
