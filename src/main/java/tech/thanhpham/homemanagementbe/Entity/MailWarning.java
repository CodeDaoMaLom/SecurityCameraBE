package tech.thanhpham.homemanagementbe.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "list_mail")
@Getter
@Setter
public class MailWarning {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(name = "email")
    private String email;

    public MailWarning(String email) {
        this.email = email;
    }

    public MailWarning() {

    }
}
