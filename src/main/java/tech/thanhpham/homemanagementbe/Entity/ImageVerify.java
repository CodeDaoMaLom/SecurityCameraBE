package tech.thanhpham.homemanagementbe.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "images_verify")
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
    @Column(name = "person")
    private String person;
    @Column(name = "probability")
    private int probability;
    @Column(name = "status")
    private String status;

    public ImageVerify(String path, LocalDateTime timeVerify, String person, int probability, String status) {
        this.path = path;
        this.timeVerify = timeVerify;
        this.person = person;
        this.probability = probability;
        this.status = status;
    }
}
