package tech.thanhpham.homemanagementbe.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "images_setup")
@Getter
@Setter
@NoArgsConstructor
public class ImageSetup {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(name = "path")
    private String path;
    @Column(name = "name")
    private String name;
    @Column(name = "time_setup")
    private LocalDateTime timeSetup;

    public ImageSetup(String name, String path, LocalDateTime timeSetup) {
        this.name = name;
        this.path = path;
        this.timeSetup = timeSetup;
    }
}
