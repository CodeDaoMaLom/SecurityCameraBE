package tech.thanhpham.homemanagementbe.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "video")
@Getter
@Setter
public class Video {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(name = "video_name")
    private String videoName;
    @Column(name = "creation_date")
    private LocalDateTime creationDate;
    @Column(name = "active")
    private Boolean active;


    public Video(String videoName, LocalDateTime creationDate, Boolean active) {
        this.videoName = videoName;
        this.creationDate = creationDate;
        this.active = active;
    }

    public Video() {

    }

    public String getStringDate() {
        return String.valueOf(creationDate).split("T")[0];
    }

    public String getUrl() {
        return "/video/" + String.valueOf(creationDate).split("T")[0] + "/" + videoName;
    }
}
