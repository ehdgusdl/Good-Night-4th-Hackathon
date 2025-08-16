package dev.ticketseller.ticket_seller.domain.seat.domain;

import dev.ticketseller.ticket_seller.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "seats") // seats 테이블과 매핑
public class Seat extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private Integer number;

    @Column(name = "is_available", nullable = false)
    @Builder.Default
    private boolean isAvailable = true;
    
}
