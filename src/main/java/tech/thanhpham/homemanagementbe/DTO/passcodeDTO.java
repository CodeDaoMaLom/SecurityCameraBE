package tech.thanhpham.homemanagementbe.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

public class passcodeDTO {
    @JsonProperty
    private int passcode;

    public passcodeDTO() {
    }

    public passcodeDTO(int passcode) {
        this.passcode = passcode;
    }

    public int getPasscode() {
        return passcode;
    }

    public void setPasscode(int passcode) {
        this.passcode = passcode;
    }
}
