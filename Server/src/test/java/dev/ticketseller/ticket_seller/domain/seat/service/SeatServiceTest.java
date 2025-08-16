package dev.ticketseller.ticket_seller.domain.seat.service;

import dev.ticketseller.ticket_seller.domain.reservation.Repository.ReservationRepository;
import dev.ticketseller.ticket_seller.domain.reservation.domain.Reservation;
import dev.ticketseller.ticket_seller.domain.seat.Repository.SeatRepository;
import dev.ticketseller.ticket_seller.domain.seat.domain.Seat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SeatServiceTest {

    @Mock
    private SeatRepository seatRepository;

    @Mock
    private ReservationRepository reservationRepository;

    @InjectMocks
    private SeatService seatService;

    private Seat availableSeat;
    private Seat unavailableSeat;

    @BeforeEach
    void setUp() {
        availableSeat = Seat.builder()
                .id(1)
                .number(1)
                .isAvailable(true)
                .build();

        unavailableSeat = Seat.builder()
                .id(2)
                .number(2)
                .isAvailable(false)
                .build();
    }

    @Test
    @DisplayName("모든 좌석을 조회할 수 있다")
    void getAllSeats_Success() {
        // given
        List<Seat> seats = Arrays.asList(availableSeat, unavailableSeat);
        when(seatRepository.findAllByOrderByIdAsc()).thenReturn(seats);

        // when
        List<Seat> result = seatService.getAllSeats();

        // then
        assertThat(result).hasSize(2);
        assertThat(result).containsExactly(availableSeat, unavailableSeat);
        verify(seatRepository, times(1)).findAllByOrderByIdAsc();
    }

    @Test
    @DisplayName("예약 가능한 좌석을 성공적으로 예약할 수 있다")
    void reserveSeat_Success() {
        // given
        String fname = "홍";
        String lname = "길동";
        String email = "hong@example.com";
        
        when(seatRepository.findById(1)).thenReturn(Optional.of(availableSeat));
        
        Seat updatedSeat = Seat.builder()
                .id(1)
                .number(1)
                .isAvailable(false)
                .build();
        when(seatRepository.save(any(Seat.class))).thenReturn(updatedSeat);
        
        Reservation expectedReservation = Reservation.builder()
                .id(1)
                .fname(fname)
                .lname(lname)
                .email(email)
                .seat(updatedSeat)
                .build();
        when(reservationRepository.save(any(Reservation.class))).thenReturn(expectedReservation);

        // when
        Reservation result = seatService.reserveSeat(1, fname, lname, email);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getFname()).isEqualTo(fname);
        assertThat(result.getLname()).isEqualTo(lname);
        assertThat(result.getEmail()).isEqualTo(email);
        assertThat(result.getSeat().isAvailable()).isFalse();
        
        verify(seatRepository, times(1)).findById(1);
        verify(seatRepository, times(1)).save(any(Seat.class));
        verify(reservationRepository, times(1)).save(any(Reservation.class));
    }

    @Test
    @DisplayName("존재하지 않는 좌석 예약 시 예외가 발생한다")
    void reserveSeat_SeatNotFound_ThrowsException() {
        // given
        when(seatRepository.findById(999)).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> seatService.reserveSeat(999, "홍", "길동", "hong@example.com"))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("좌석을 찾을 수 없습니다.");
        
        verify(seatRepository, times(1)).findById(999);
        verify(seatRepository, never()).save(any(Seat.class));
        verify(reservationRepository, never()).save(any(Reservation.class));
    }

    @Test
    @DisplayName("이미 예약된 좌석 예약 시 예외가 발생한다")
    void reserveSeat_SeatAlreadyReserved_ThrowsException() {
        // given
        when(seatRepository.findById(2)).thenReturn(Optional.of(unavailableSeat));

        // when & then
        assertThatThrownBy(() -> seatService.reserveSeat(2, "홍", "길동", "hong@example.com"))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("이미 예약된 좌석입니다.");
        
        verify(seatRepository, times(1)).findById(2);
        verify(seatRepository, never()).save(any(Seat.class));
        verify(reservationRepository, never()).save(any(Reservation.class));
    }
}
