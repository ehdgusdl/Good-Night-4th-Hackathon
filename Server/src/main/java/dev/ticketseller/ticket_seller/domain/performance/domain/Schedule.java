package dev.ticketseller.ticket_seller.domain.performance.domain;

import dev.ticketseller.ticket_seller.domain.common.BaseEntity;
import dev.ticketseller.ticket_seller.domain.reservation.domain.Reservation;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "schedule")
public class Schedule extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_id")
    private Long id;

    @Column(nullable = false)
    private LocalDate performanceDate; // 공연 날짜

    @Column(nullable = false)
    private LocalTime startTime; // 시작 시간

    @Column(nullable = false)
    private LocalTime endTime; // 종료 시간

    // Schedule(N)은 Performance(1)에 속하므로 @ManyToOne 설정
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "performance_id") // 외래키
    private Performance performance;

    // Schedule(1)은 여러 Reservation(N)을 가질 수 있음
    @OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    private List<Reservation> reservations = new ArrayList<>();
    
}
