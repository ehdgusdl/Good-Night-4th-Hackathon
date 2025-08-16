package dev.ticketseller.ticket_seller.global.config;

import dev.ticketseller.ticket_seller.domain.seat.Repository.SeatRepository;
import dev.ticketseller.ticket_seller.domain.seat.domain.Seat;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final SeatRepository seatRepository;

    @Override
    public void run(String... args) throws Exception {
        // 이미 데이터가 있는지 확인
        if (seatRepository.count() == 0) {
            // 9개 좌석 초기 데이터 생성 (3x3 격자)
            for (int i = 1; i <= 9; i++) {
                Seat seat = Seat.builder()
                        .number(i)
                        .isAvailable(true)
                        .build();
                seatRepository.save(seat);
            }
            System.out.println("9개 좌석 초기 데이터가 생성되었습니다.");
        }
    }
}
