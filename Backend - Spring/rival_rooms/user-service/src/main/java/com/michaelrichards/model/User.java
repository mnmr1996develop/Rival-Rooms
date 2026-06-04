package com.michaelrichards.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.UUID;

@Entity
@Builder
@Getter
@Setter
@Table(name = "m_user",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),
                @UniqueConstraint(columnNames = "email")
        })
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(unique = true, nullable = false)
    private UUID userId;


    @NotBlank
    @Column(length = 25)
    @Size(min = 2, max = 25)
    private String firstName;


    @Column(length = 25)
    @Size(min = 2, max = 25)
    private String lastName;

    @Size(min = 3, max = 20)
    @Column(unique = true, nullable = false, length = 20)
    private String username;

    @Email
    @Column(unique = true, nullable = false)
    private String email;

    private String password;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdDate;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedDate;

    @Column(nullable = false, updatable = false)
    private LocalDate birthDate;

    @Transient
    public Integer getAge() {
        return Period.between(this.birthDate, LocalDate.now()).getYears();
    }


}
