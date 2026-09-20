package com.example.payment_service.entity.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Arrays;

public enum PaymentOrderStatus {
    PENDING,
    SUCCESS,
    FAILED;

    @JsonCreator
    public static BookingStatus fromValue(String value) {

        try {
            return BookingStatus.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException(
                    "Invalid booking status. Allowed values: " +
                            Arrays.toString(values())
            );
        }
    }
}
