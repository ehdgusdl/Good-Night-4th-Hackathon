package dev.ticketseller.ticket_seller.domain.seat.service;

import dev.ticketseller.ticket_seller.domain.reservation.Repository.ReservationRepository;
import dev.ticketseller.ticket_seller.domain.reservation.domain.Reservation;
import dev.ticketseller.ticket_seller.domain.seat.Repository.SeatRepository;
import dev.ticketseller.ticket_seller.domain.seat.domain.Seat;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SeatService {
    
    private final SeatRepository seatRepository;
    private final ReservationRepository reservationRepository;
    
    // 모든 좌석 조회
    public List<Seat> getAllSeats() {
        return seatRepository.findAllByOrderByIdAsc();
    }
    
    // 좌석 예약
    @Transactional
    public Reservation reserveSeat(Integer seatNumber, String fname, String lname, String email) {
        // 좌석 번호로 좌석 찾기
        Seat seat = seatRepository.findById(seatNumber)
                .orElseThrow(() -> new RuntimeException("좌석을 찾을 수 없습니다."));
        
        // 좌석이 이미 예약되었는지 확인
        if (!seat.isAvailable()) {
            throw new RuntimeException("이미 예약된 좌석입니다.");
        }
        
        // 좌석 상태를 예약됨으로 변경
        seat.setAvailable(false);
        seatRepository.save(seat);
        
        // 예약 생성
        Reservation reservation = Reservation.builder()
                .fname(fname)
                .lname(lname)
                .email(email)
                .seat(seat)
                .build();
        
        return reservationRepository.save(reservation);
    }
}
