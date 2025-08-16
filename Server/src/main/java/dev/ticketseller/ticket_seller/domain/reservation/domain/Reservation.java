package dev.ticketseller.ticket_seller.domain.reservation.domain;

import dev.ticketseller.ticket_seller.domain.common.BaseEntity;
import dev.ticketseller.ticket_seller.domain.performance.domain.Seat;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "reservations") // reservations 테이블과 매핑
public class Reservation extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String fname;

    @Column(nullable = false)
    private String lname;

    private String email;

    @OneToOne // 좌석과 1:1 관계
    @JoinColumn(name = "seat_id", nullable = false)
    private Seat seat;
}