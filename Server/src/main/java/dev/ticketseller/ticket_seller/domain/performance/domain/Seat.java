package dev.ticketseller.ticket_seller.domain.performance.domain;

import dev.ticketseller.ticket_seller.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "seat")
public class Seat extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seat_id")
    private Long id;

    @Column(nullable = false)
    private String section; // 구역 (예: A구역, B구역)

    @Column(nullable = false)
    private String seatRow; // 행 (예: 1열, 2열)

    @Column(nullable = false)
    private int seatNumber; // 좌석 번호

    @Column(nullable = false)
    private String grade; // 등급 (예: VIP, R, S)

    @Column(nullable = false)
    private int price;
    
    // Seat(N)은 Theater(1)에 속하므로 @ManyToOne 설정
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "theater_id") // 외래키
    private Theater theater;
    
}
