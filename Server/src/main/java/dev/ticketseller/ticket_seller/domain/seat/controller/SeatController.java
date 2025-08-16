package dev.ticketseller.ticket_seller.domain.seat.controller;

import dev.ticketseller.ticket_seller.domain.reservation.DTO.ReservationRequestDto;
import dev.ticketseller.ticket_seller.domain.reservation.domain.Reservation;
import dev.ticketseller.ticket_seller.domain.seat.domain.Seat;
import dev.ticketseller.ticket_seller.domain.seat.service.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/seats")
@RequiredArgsConstructor
public class SeatController {

    private final SeatService seatService;

    // 좌석 목록 조회 API
    @GetMapping
    public ResponseEntity<List<Seat>> getAllSeats() {
        List<Seat> seats = seatService.getAllSeats();
        return ResponseEntity.ok(seats);
    }

    // 좌석 예약 요청 API
    @PostMapping("/reserve")
    public ResponseEntity<Reservation> reserveSeat(@RequestBody ReservationRequestDto request) {
        try {
            Reservation reservation = seatService.reserveSeat(
                    request.getSeatNumber(),
                    request.getFname(),
                    request.getLname(),
                    request.getEmail()
            );
            return ResponseEntity.ok(reservation);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
