package tech.thanhpham.homemanagementbe.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "settings")
@Getter
@Setter
public class Settings {
    @Id
    private String key;
    @Column(name = "value")
    private String value;

    public Settings(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public Settings() {

    }

    public boolean getBooleanValue(){
        return Boolean.parseBoolean(this.value);
    }
    public void setBooleanValue(Boolean value){
        this.value =  String.valueOf(value);
    }
}
