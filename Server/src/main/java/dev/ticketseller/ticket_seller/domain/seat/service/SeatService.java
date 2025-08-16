package dev.ticketseller.ticket_seller.domain.seat.service;

import dev.ticketseller.ticket_seller.domain.reservation.Repository.ReservationRepository;
import dev.ticketseller.ticket_seller.domain.reservation.domain.Reservation;
import dev.ticketseller.ticket_seller.domain.seat.Repository.SeatRepository;
import dev.ticketseller.ticket_seller.domain.seat.domain.Seat;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SeatService {
    
    private final SeatRepository seatRepository;
    private final ReservationRepository reservationRepository;
    private final Random random = new Random();
    
    // 모든 좌석 조회
    public List<Seat> getAllSeats() {
        return seatRepository.findAllByOrderByIdAsc();
    }
    
    // 좌석 예약 (99% 성공, 1% 실패)
    @Transactional
    public Reservation reserveSeat(Integer seatNumber, String fname, String lname, String email) {
        // 좌석 번호로 좌석 찾기
        Seat seat = seatRepository.findById(seatNumber)
                .orElseThrow(() -> new RuntimeException("좌석을 찾을 수 없습니다."));
        
        // 좌석이 이미 예약되었는지 확인
        if (!seat.isAvailable()) {
            throw new RuntimeException("이미 예약된 좌석입니다.");
        }
        
        // 99% 확률로 성공, 1% 확률로 실패 처리
        int randomValue = random.nextInt(100);
        if (randomValue < 1) { // 1% 확률로 실패
            throw new RuntimeException("예약 처리 중 오류가 발생했습니다. 다시 시도해주세요.");
        }
        
        // 좌석 상태를 예약됨으로 변경 (불변 객체이므로 새로 생성)
        Seat updatedSeat = Seat.builder()
                .id(seat.getId())
                .number(seat.getNumber())
                .isAvailable(false)
                .build();
        seatRepository.save(updatedSeat);
        
        // 예약 생성
        Reservation reservation = Reservation.builder()
                .fname(fname)
                .lname(lname)
                .email(email)
                .seat(updatedSeat)
                .build();
        
        return reservationRepository.save(reservation);
    }
}
