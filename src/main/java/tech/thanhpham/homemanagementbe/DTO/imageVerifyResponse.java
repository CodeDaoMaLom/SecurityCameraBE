package tech.thanhpham.homemanagementbe.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

public class imageVerifyResponse {
    @JsonProperty
    private String person;
    @JsonProperty
    private String probability;

    public imageVerifyResponse() {
    }

    public imageVerifyResponse(String person, String probability) {
        this.person = person;
        this.probability = probability;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public String getProbability() {
        return probability;
    }

    public void setProbability(String probability) {
        this.probability = probability;
    }

    public int getProbabilityInt(){
        return  (int) (Float.parseFloat(this.getProbability()) * 100);
    }
}
