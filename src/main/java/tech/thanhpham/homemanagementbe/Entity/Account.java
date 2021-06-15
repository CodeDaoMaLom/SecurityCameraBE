package tech.thanhpham.homemanagementbe.Entity;

import lombok.Getter;
import lombok.Setter;
import tech.thanhpham.homemanagementbe.DTO.accountDTO;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "account")
@Getter
@Setter
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private String mail;
    private String phone;
    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    public accountDTO toDTO(){
        accountDTO accountDTO = new accountDTO();
        accountDTO.setId(this.getId());
        accountDTO.setUsername(this.getUsername());
        accountDTO.setFirstname(this.firstname);
        accountDTO.setLastname(this.lastname);
        accountDTO.setMail(this.getMail());
        accountDTO.setPhone(this.phone);
        accountDTO.setRole(this.getRole().getRole_name());
        return accountDTO;
    }
}
