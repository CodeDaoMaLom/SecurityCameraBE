package tech.thanhpham.homemanagementbe.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.thanhpham.homemanagementbe.Entity.Settings;

import java.util.UUID;

@Repository
public interface SettingsRepository extends JpaRepository<Settings, String> {
}