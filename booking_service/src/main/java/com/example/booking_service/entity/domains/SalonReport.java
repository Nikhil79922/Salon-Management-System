package com.example.booking_service.entity.domains;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SalonReport {
    private Long id;
//    private String salonName;
    private Long totalEarning;
    private Long totalBookings;
    private Long cancelledBookings;
    private Long totalRefund;
}
