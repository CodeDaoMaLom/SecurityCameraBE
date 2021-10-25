package tech.thanhpham.homemanagementbe.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.thanhpham.homemanagementbe.Entity.ImageVerify;
import tech.thanhpham.homemanagementbe.Entity.Video;

import java.util.List;
import java.util.UUID;

@Repository
public interface VideoRepository extends JpaRepository<Video, UUID> {
    List<Video> findAllByOrderByCreationDateDesc();
    List<Video> findByVideoName(String s);
}