package tech.thanhpham.homemanagementbe.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.thanhpham.homemanagementbe.Entity.ImageSetup;
import tech.thanhpham.homemanagementbe.Entity.ImageVerify;

import java.util.List;
import java.util.UUID;

@Repository
public interface imageSetupRepository extends JpaRepository<ImageSetup, UUID> {
    List<ImageSetup> findAllByName(String person);

}
