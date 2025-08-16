package dev.ticketseller.ticket_seller.domain.users.domain;

import dev.ticketseller.ticket_seller.domain.common.BaseEntity;
import dev.ticketseller.ticket_seller.domain.reservation.domain.Reservation;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "user")
public class Users extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String username; // 로그인 ID

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String phoneNumber;
    
    // User(1)가 Reservation(N)을 가질 수 있으므로 @OneToMany 설정
    // mappedBy는 Reservation 엔티티의 user 필드에 의해 관리됨을 의미
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Reservation> reservations = new ArrayList<>();
}
