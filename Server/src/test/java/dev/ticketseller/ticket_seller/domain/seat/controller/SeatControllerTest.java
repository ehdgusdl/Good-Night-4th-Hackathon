package dev.ticketseller.ticket_seller.domain.seat.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.ticketseller.ticket_seller.domain.reservation.DTO.ReservationRequestDto;
import dev.ticketseller.ticket_seller.domain.reservation.domain.Reservation;
import dev.ticketseller.ticket_seller.domain.seat.domain.Seat;
import dev.ticketseller.ticket_seller.domain.seat.service.SeatService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@SuppressWarnings("deprecation")
class SeatControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SeatService seatService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("좌석 목록 조회 API가 정상 작동한다")
    void getAllSeats_Success() throws Exception {
        // given
        List<Seat> seats = Arrays.asList(
                Seat.builder().id(1).number(1).isAvailable(true).build(),
                Seat.builder().id(2).number(2).isAvailable(false).build()
        );
        when(seatService.getAllSeats()).thenReturn(seats);

        // when & then
        mockMvc.perform(get("/api/v1/seats"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].number").value(1))
                .andExpect(jsonPath("$[0].available").value(true))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].number").value(2))
                .andExpect(jsonPath("$[1].available").value(false));
    }

    @Test
    @DisplayName("좌석 예약 API가 정상 작동한다")
    void reserveSeat_Success() throws Exception {
        // given
        ReservationRequestDto requestDto = new ReservationRequestDto(1, "홍", "길동", "hong@example.com");
        
        Seat seat = Seat.builder()
                .id(1)
                .number(1)
                .isAvailable(false)
                .build();
        
        Reservation reservation = Reservation.builder()
                .id(1)
                .fname("홍")
                .lname("길동")
                .email("hong@example.com")
                .seat(seat)
                .build();
        
        when(seatService.reserveSeat(eq(1), eq("홍"), eq("길동"), eq("hong@example.com")))
                .thenReturn(reservation);

        // when & then
        mockMvc.perform(post("/api/v1/seats/reserve")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.fname").value("홍"))
                .andExpect(jsonPath("$.lname").value("길동"))
                .andExpect(jsonPath("$.email").value("hong@example.com"))
                .andExpect(jsonPath("$.seat.id").value(1))
                .andExpect(jsonPath("$.seat.number").value(1))
                .andExpect(jsonPath("$.seat.available").value(false));
    }

    @Test
    @DisplayName("좌석 예약 실패 시 BadRequest를 반환한다")
    void reserveSeat_Failure_ReturnsBadRequest() throws Exception {
        // given
        ReservationRequestDto requestDto = new ReservationRequestDto(999, "홍", "길동", "hong@example.com");
        
        when(seatService.reserveSeat(eq(999), eq("홍"), eq("길동"), eq("hong@example.com")))
                .thenThrow(new RuntimeException("좌석을 찾을 수 없습니다."));

        // when & then
        mockMvc.perform(post("/api/v1/seats/reserve")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isBadRequest());
    }
}
