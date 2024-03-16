package me.project.batch.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;

@ToString
@Getter
@NoArgsConstructor
@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String email;

    private LocalDateTime created;

    private LocalDateTime loginAt;

    private CustomerStatus status;

    public Customer(String name, String email) {
        this.name = name;
        this.email = email;
        this.created = LocalDateTime.now();
        this.loginAt = LocalDateTime.now();
        this.status = CustomerStatus.NORMAL;
    }

    public void setLoginAt(LocalDateTime loginAt) {
        this.loginAt = loginAt;
    }

    public void setStatus(CustomerStatus status) {
        this.status = status;
    }


}
