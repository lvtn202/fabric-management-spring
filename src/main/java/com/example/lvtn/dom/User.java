package com.example.lvtn.dom;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name",length = 50)
    private String firstName;

    @Column(name = "last_name", length = 50, nullable = false)
    private String lastName;

    @Column(length = 100, nullable = false)
    private String email;

    @Column(length = 50, nullable = false)
    private String password;

    @Column(length = 10, nullable = false)
    private String sex;

    @OneToMany(mappedBy = "user")
    private Set<PersistentLogin> persistentLogins;

    @OneToMany(mappedBy = "user")
    private Set<ImportSlip> importSlips;

    @OneToMany(mappedBy = "user")
    private Set<ExportSlip> exportSlips;

    @OneToMany(mappedBy = "user")
    private Set<Payment> payments;

    @OneToMany(mappedBy = "user")
    private Set<ReturnSlip> returnSlips;

    @ManyToMany(mappedBy = "users")
    private Set<Role> roles;

    public User() {
    }

    public User(String firstName, String lastName, String email, String password, String sex, Set<PersistentLogin> persistentLogins, Set<ImportSlip> importSlips, Set<ExportSlip> exportSlips, Set<Payment> payments, Set<ReturnSlip> returnSlips, Set<Role> roles) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.sex = sex;
        this.persistentLogins = persistentLogins;
        this.importSlips = importSlips;
        this.exportSlips = exportSlips;
        this.payments = payments;
        this.returnSlips = returnSlips;
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", sex='" + sex + '\'' +
                '}';
    }
}
