package dev.ticketseller.ticket_seller.domain.performance.domain;

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
@Table(name = "performance")
public class Performance extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "performance_id")
    private Long id;

    @Column(nullable = false)
    private String title; // 공연명

    @Column(nullable = false)
    private String genre;

    private String rating; // 관람 등급

    private int runningTime; // 공연 시간 (분 단위)

    private String posterUrl; // 포스터 이미지 URL

    @Lob // 대용량 텍스트를 위한 어노테이션
    @Column(columnDefinition = "TEXT")
    private String description; // 상세 설명

    // Performance(1)가 Schedule(N)을 가질 수 있으므로 @OneToMany 설정
    @OneToMany(mappedBy = "performance", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    private List<Schedule> schedules = new ArrayList<>();
}
