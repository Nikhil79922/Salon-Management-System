package com.example.booking_service.config;

import com.example.booking_service.dto.PaymentDTO;
import com.example.booking_service.entity.enums.PaymentOrderStatus;
import com.example.booking_service.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RabbitmqConfig {
    private final BookingService bookingService;

    @Bean
    public Queue bookingQueue() {
        return new Queue("booking_queue", true);
    }

    @RabbitListener(queues = "booking_queue")
    public void bookingUpdateListener(PaymentDTO paymentDetails) {
bookingService.bookingSuccess(paymentDetails);
    }
}
