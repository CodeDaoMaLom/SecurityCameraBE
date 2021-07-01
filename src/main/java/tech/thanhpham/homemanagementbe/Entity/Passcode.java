package tech.thanhpham.homemanagementbe.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "passcode")
@Getter
@Setter
public class Passcode {
    @Id
    private String code;
    private int passcode;
}
