package org.example.booking_be.configuration;

import lombok.RequiredArgsConstructor;
import org.example.booking_be.entity.Showtime;
import org.example.booking_be.reponsitory.ShowtimeReponsitory;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class HoldingSeatCleaner {

    private final ShowtimeReponsitory showtimeReponsitory;
    private final SimpMessagingTemplate messagingTemplate;

    // Chạy mỗi 1 phút
    @Scheduled(fixedRate = 60000)
    public void cleanExpiredSeats() {
        List<Showtime> showtimes = showtimeReponsitory.findAll();
        LocalDateTime now = LocalDateTime.now();

        for (Showtime showtime : showtimes) {
            boolean removed = showtime.getHoldingSeats().removeIf(h -> h.getHoldUntil().isBefore(now));
            if (removed) {
                showtimeReponsitory.save(showtime);
                messagingTemplate.convertAndSend(
                        "/topic/showtime/" + showtime.getId() + "/seats",
                        showtime
                );
            }
        }
    }
}

