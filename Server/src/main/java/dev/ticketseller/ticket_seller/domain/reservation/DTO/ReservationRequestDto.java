package dev.ticketseller.ticket_seller.domain.reservation.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationRequestDto {
    private Integer seatNumber;
    private String fname;
    private String lname;
    private String email;
}
