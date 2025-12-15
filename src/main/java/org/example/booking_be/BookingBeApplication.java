package org.example.booking_be;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
//Enable Scheduling (xoá ghế giữ quá hạn)
public class BookingBeApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookingBeApplication.class, args);
    }

}
