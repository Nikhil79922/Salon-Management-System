package com.example.payment_service.controller;

import com.example.payment_service.dto.*;
import com.example.payment_service.dto.commonRes.SuccessResponse;
import com.example.payment_service.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;


@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/create")
    public ResponseEntity<SuccessResponse<PaymentLinkResponse>> createPaymentLink(
            @Valid @RequestBody BookingDto booking , @RequestParam PaymentRequest  paymentRequest) {
        //Temporary
        UsersDto user = new UsersDto(1L, "Nikhil", "ns94301918@gmail.com", null, "7992238245", null, null, null);

        PaymentLinkResponse resDetail = paymentService.createPayment(
                paymentRequest,
                user,
                booking
        );

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new SuccessResponse<PaymentLinkResponse>(
                        true,
                        "Payment session link created Successfully",
                        resDetail,
                        LocalDateTime.now(),
                        HttpStatus.CREATED.value()
                ));
    }


    @GetMapping("/{paymentId}")
    public ResponseEntity<SuccessResponse<PaymentResponse>> findPaymentById(
            @PathVariable Long paymentId
    ) {
        PaymentResponse resDetail = paymentService.getPaymentOrderByPaymentId(paymentId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new SuccessResponse<PaymentResponse>(
                        true,
                        "Payment fetched Successfully by payment Id",
                        resDetail,
                        LocalDateTime.now(),
                        HttpStatus.OK.value()
                ));
    }

    @GetMapping("/paymentOrder/{id}")
    public ResponseEntity<SuccessResponse<PaymentResponse>> findPaymentOrderById(
            @PathVariable Long id
    ) {
        PaymentResponse resDetail = paymentService.getPaymentOrderById(id);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new SuccessResponse<PaymentResponse>(
                        true,
                        "Payment fetched Successfully by payment order Id",
                        resDetail,
                        LocalDateTime.now(),
                        HttpStatus.OK.value()
                ));
    }

    @PostMapping("/{paymentId}/proceed")
    public ResponseEntity<SuccessResponse<Boolean>> proceedPayment(
            @PathVariable Long paymentId,
            @RequestParam String paymentLinkedId
    ) {

        Boolean success =
                paymentService.proceedPayment(
                        paymentId,
                        paymentLinkedId
                );

        return ResponseEntity.ok(
                new SuccessResponse<>(
                        success,
                        success
                                ? "Payment completed successfully"
                                : "Payment is not completed",
                        success,
                        LocalDateTime.now(),
                        HttpStatus.OK.value()
                )
        );
    }
}
