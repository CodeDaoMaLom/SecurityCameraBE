package tech.thanhpham.homemanagementbe.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class accountDTO {
    @JsonProperty
    private UUID id;
    @JsonProperty
    private String username;
    @JsonProperty
    private String firstname;
    @JsonProperty
    private String lastname;
    @JsonProperty
    private String name;
    @JsonProperty
    private String mail;
    @JsonProperty
    private String phone;
    @JsonProperty
    private String role;

    public String getName() {
        if (this.getFirstname() == null && this.getLastname() == null) {
            return "";
        }
        return String.format("%s %s", this.getFirstname(), this.getLastname());
    }
}
