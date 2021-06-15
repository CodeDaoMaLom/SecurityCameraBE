package tech.thanhpham.homemanagementbe.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tech.thanhpham.homemanagementbe.Entity.Account;
import tech.thanhpham.homemanagementbe.Repository.accountRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class accountService {
    private final accountRepository accountRepository;

    public Account findByUsername(String Username){
        return accountRepository.findByUsername(Username).get();
    }

    public Account findById(UUID uuid){
        return accountRepository.findById(uuid).get();
    }
}
