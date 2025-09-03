package org.example.taskflowd.domain.user.entity;

import org.example.taskflowd.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 사용자 정보를 DB에 저장하는 JPA Entity 클래스
 *
 */

@Getter
@Entity
@NoArgsConstructor
@Table(name = "users")
public class User extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String userName;

    @Column(unique = true)
    private String email;

    private String password;

    private boolean deleted = false;

    // 일반 생성자. 회원 가입시 사용
    public User(String userName, String email, String password) {
        this.userName = userName;
        this.email = email;
        this.password = password;
    }

    // 사용자 정보 업데이트
    public void update(String userName, String email, String password) {
        this.userName = userName;
        this.email = email;
        this.password = password;
    }

    // 사용자 삭제
    public void softDelete() {
        this.deleted = true;
    }



}
