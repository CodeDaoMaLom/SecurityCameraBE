package tech.thanhpham.homemanagementbe.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class FacialSetupDTO {
    @JsonProperty
    private String name;
    @JsonProperty
    private List<Integer> data = null;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Integer> getData() {
        return data;
    }

    public void setData(List<Integer> data) {
        this.data = data;
    }
}
