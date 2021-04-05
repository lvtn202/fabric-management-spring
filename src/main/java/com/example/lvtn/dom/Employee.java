package com.example.lvtn.dom;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "employee")
@Getter
@Setter
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "first_name",length = 50)
    private String firstName;

    @Column(name = "middle_name",length = 50)
    private String middleName;

    @Column(name = "last_name", length = 50, nullable = false)
    private String lastName;

    @Column(name = "user_name", length = 100, nullable = false)
    private String userName;

    @Column(length = 100, nullable = false)
    private String email;

    @Column(length = 50, nullable = false)
    private String password;

    @Column(length = 10, nullable = false)
    private String sex;

    @OneToMany(mappedBy = "employee")
    private Set<PersistentLogin> persistentLogins;

    @OneToMany(mappedBy = "employee")
    private Set<ImportSlip> importSlips;

    @OneToMany(mappedBy = "employee")
    private Set<ExportSlip> exportSlips;

    @OneToMany(mappedBy = "employee")
    private Set<Payment> payments;

    @OneToMany(mappedBy = "employee")
    private Set<ReturnSlip> returnSlips;

    public Employee() {
    }

    public Employee(String firstName, String middleName, String lastName, String userName, String email, String password, String sex, Set<PersistentLogin> persistentLogins, Set<ImportSlip> importSlips, Set<ExportSlip> exportSlips, Set<Payment> payments, Set<ReturnSlip> returnSlips) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.sex = sex;
        this.persistentLogins = persistentLogins;
        this.importSlips = importSlips;
        this.exportSlips = exportSlips;
        this.payments = payments;
        this.returnSlips = returnSlips;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", sex='" + sex + '\'' +
                '}';
    }
}
