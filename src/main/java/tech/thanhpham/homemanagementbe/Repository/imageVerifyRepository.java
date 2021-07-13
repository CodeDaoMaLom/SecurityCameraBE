package tech.thanhpham.homemanagementbe.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.thanhpham.homemanagementbe.Entity.ImageVerify;

import java.util.UUID;

@Repository
public interface imageVerifyRepository extends JpaRepository<ImageVerify, UUID> {
}
