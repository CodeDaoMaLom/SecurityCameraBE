package tech.thanhpham.homemanagementbe.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "image_verify")
@Getter
@Setter
public class ImageVerify {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(name = "path")
    private String path;
    @Column(name = "time_verify")
    private LocalDateTime timeVerify;
    @Column(name = "status")
    private String status;
}
