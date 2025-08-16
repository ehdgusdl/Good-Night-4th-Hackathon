package dev.ticketseller.ticket_seller.domain.seat.Repository;

import dev.ticketseller.ticket_seller.domain.seat.domain.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SeatRepository extends JpaRepository<Seat, Integer> {
    // JpaRepository를 상속받는 것만으로 기본적인 CRUD(Save, FindById, Delete 등)가 가능

    // 정렬해서 모든 좌석 찾기
    List<Seat> findAllByOrderByIdAsc();
}
