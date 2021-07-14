package tech.thanhpham.homemanagementbe.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import tech.thanhpham.homemanagementbe.Entity.ImageVerify;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
public class imageVerifyDTO {
    @JsonProperty
    private String person;
    @JsonProperty
    private String path;
    @JsonProperty
    private String timeVerify;
    @JsonProperty
    private String status;
    @JsonProperty
    private int probability;

    public imageVerifyDTO(ImageVerify imageVerify){
        this.person = imageVerify.getPerson();
        this.path = imageVerify.getPath();
        this.timeVerify = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(imageVerify.getTimeVerify());
        this.status = imageVerify.getStatus();
        this.probability = imageVerify.getProbability();
    }

    public imageVerifyDTO() {

    }
}
