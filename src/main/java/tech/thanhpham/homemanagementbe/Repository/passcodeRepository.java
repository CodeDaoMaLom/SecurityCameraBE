package tech.thanhpham.homemanagementbe.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.thanhpham.homemanagementbe.Entity.Passcode;

@Repository
public interface passcodeRepository extends JpaRepository<Passcode, String> {
}
