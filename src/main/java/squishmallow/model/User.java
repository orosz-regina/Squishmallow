package squishmallow.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity(name = "App_user")
@Data
public class User {


    @Column(unique = true)  // biztosítja, hogy az email csak egyszer szerepelhet
    private String email;

    @Id
    @Column(unique = true)  // biztosítja, hogy a username csak egyszer szerepelhet
    private String username;

    private String password;

    private LocalDateTime createdAt;
}
