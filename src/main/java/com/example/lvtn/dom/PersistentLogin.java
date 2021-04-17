package com.example.lvtn.dom;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "persistent_login")
@Getter
@Setter
public class PersistentLogin {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(length = 100, nullable = false)
    private String token;

    @Column(nullable = false, name = "last_update")
    private Timestamp lastUpdate;

    public PersistentLogin() {
    }

    public PersistentLogin(User user, String token, Timestamp lastUpdate) {
        this.user = user;
        this.token = token;
        this.lastUpdate = lastUpdate;
    }

    @Override
    public String toString() {
        return "PersistentLogin{" +
                "id='" + id + '\'' +
                ", user=" + user +
                ", token='" + token + '\'' +
                ", lastUpdate=" + lastUpdate +
                '}';
    }
}
