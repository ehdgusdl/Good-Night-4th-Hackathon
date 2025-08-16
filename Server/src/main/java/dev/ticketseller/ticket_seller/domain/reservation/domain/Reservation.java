package dev.ticketseller.ticket_seller.domain.reservation.domain;

import dev.ticketseller.ticket_seller.domain.common.BaseEntity;
import dev.ticketseller.ticket_seller.domain.performance.domain.Seat;
import dev.ticketseller.ticket_seller.domain.performance.domain.Schedule;
import dev.ticketseller.ticket_seller.domain.users.domain.Users;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "reservation")
public class Reservation extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private Long id;

    @Column(nullable = false)
    private LocalDateTime reservationDate; // 예매 일시

    // 예매 상태 [예매 완료, 취소]
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReservationStatus status;

    private int totalPrice; // 총 결제 금액

    // Reservation(N)은 User(1)에 속함
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users user;

    // Reservation(N)은 Schedule(1)에 속함
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    // 하나의 예매는 여러 좌석을 포함할 수 있고, 하나의 좌석은 여러 예매에 포함될 수 있음 (다른 스케줄일 경우)
    // 다대다 관계는 중간 테이블(reservation_seat)을 생성하여 관리
    @ManyToMany
    @JoinTable(
        name = "reservation_seat",
        joinColumns = @JoinColumn(name = "reservation_id"),
        inverseJoinColumns = @JoinColumn(name = "seat_id")
    )
    @Builder.Default
    private List<Seat> seats = new ArrayList<>();
}
