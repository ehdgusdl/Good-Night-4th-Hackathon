package dev.ticketseller.ticket_seller.domain.reservation.Repository;

import dev.ticketseller.ticket_seller.domain.reservation.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
}
