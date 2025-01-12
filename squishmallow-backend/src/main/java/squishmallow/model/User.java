package squishmallow.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity(name = "App_user")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Automatikusan generált azonosító
    private Long id;

    @Column(unique = true)  // biztosítja, hogy az email csak egyszer szerepelhet
    private String email;

    @Column(unique = true)  // biztosítja, hogy a username csak egyszer szerepelhet
    private String username;

    private String password;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
