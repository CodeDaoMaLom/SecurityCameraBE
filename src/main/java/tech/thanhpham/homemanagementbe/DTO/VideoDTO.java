package tech.thanhpham.homemanagementbe.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;


public class VideoDTO {
    @JsonProperty
    private String videoName;

    public VideoDTO(String videoName) {
        this.videoName = videoName;
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

}
