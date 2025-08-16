package dev.ticketseller.ticket_seller.domain.performance.domain;

import dev.ticketseller.ticket_seller.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "theater")
public class Theater extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "theater_id")
    private Long id;

    @Column(nullable = false)
    private String name; // 공연장명

    @Column(nullable = false)
    private String address;

    private int totalSeats; // 총 좌석 수
    
    // Theater(1)가 Seat(N)을 가질 수 있으므로 @OneToMany 설정
    @OneToMany(mappedBy = "theater", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    private List<Seat> seats = new ArrayList<>();
}
