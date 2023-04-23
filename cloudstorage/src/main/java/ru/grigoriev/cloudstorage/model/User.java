package ru.grigoriev.cloudstorage.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.util.CollectionUtils;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@SuperBuilder
@Table(name = "users", indexes = @Index(columnList = "email"))
public class User extends AbstractBaseEntity {


    @NotBlank(message = "Email cannot be empty")
    @Column(name = "email", nullable = false, unique = true)
    @Email
    @Size(max = 128)
    private String email;

    @NotBlank(message = "Password cannot be empty")
    @Size(min = 5, max = 128, message = "Password length must be between 5 and 128 characters")
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "enabled", nullable = false, columnDefinition = "bool default true")
    private boolean enabled = true;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, columnDefinition = "ACTIVE")
    protected Status status;

}