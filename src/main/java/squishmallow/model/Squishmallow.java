package squishmallow.model;

import jakarta.persistence.*;
        import lombok.Data;

@Entity
@Data
public class Squishmallow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String type;
    private String size;
    private String category;

}