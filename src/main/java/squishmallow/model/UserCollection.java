package squishmallow.model;

import jakarta.persistence.*;

import java.time.OffsetDateTime;

@Entity
@Table(name = "user_collection")
public class UserCollection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "squishmallow_id", nullable = false)
    private Squishmallow squishmallow;

    @Column(name = "added_at", nullable = false)
    private OffsetDateTime addedAt;
}

