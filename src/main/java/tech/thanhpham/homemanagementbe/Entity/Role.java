package tech.thanhpham.homemanagementbe.Entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "role")
@Getter
@Setter
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String role_name;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "role")
    private Set<Account> accountList;
}
