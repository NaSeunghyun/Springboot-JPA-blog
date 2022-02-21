package com.nas.blog.entity;

import com.nas.blog.user.model.RoleType;
import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50, unique = true)
    private String email;

    @Column(nullable = false, length = 100)
    private String password;

    @Column(nullable = false, length = 30)
    private String userName;

    @Enumerated(EnumType.STRING)
    private RoleType role;

    private String provider;

    private String providerId;

    @Builder
    public User(String email, String password, String userName, RoleType role, String provider, String providerId) {
        this.email = email;
        this.password = password;
        this.userName = userName;
        this.role = role;
        this.provider = provider;
        this.providerId = providerId;
    }
}
