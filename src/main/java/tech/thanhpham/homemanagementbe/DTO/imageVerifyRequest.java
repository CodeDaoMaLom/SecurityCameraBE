package tech.thanhpham.homemanagementbe.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "name",
        "data"
})
public class imageVerifyRequest {

    @JsonProperty("name")
    private String name;
    @JsonProperty("data")
    private String data;

    public imageVerifyRequest(String name, String data) {
        this.name = name;
        this.data = data;
    }

    public imageVerifyRequest() {
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("data")
    public String getData() {
        return data;
    }

    @JsonProperty("data")
    public void setData(String data) {
        this.data = data;
    }

}