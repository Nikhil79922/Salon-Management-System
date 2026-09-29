package com.example.payment_service.controller;

import com.example.payment_service.dto.*;
import com.example.payment_service.dto.commonRes.SuccessResponse;
import com.example.payment_service.entity.enums.PaymentMethod;
import com.example.payment_service.mapper.FeignClientResponseMapper;
import com.example.payment_service.service.PaymentService;
import com.example.payment_service.service.client.UserFeignClient;
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
    private final UserFeignClient userFeignClient;
    private final FeignClientResponseMapper feignClientResponseMapper;

    @PostMapping("/create")
    public ResponseEntity<SuccessResponse<PaymentLinkResponse>>
    createPaymentLink(
            @Valid @RequestBody BookingDto booking,
            @RequestParam PaymentMethod paymentMethod,
            @RequestHeader("Authorization") String token
    ) {
        UsersDto usersDto = feignClientResponseMapper.mapToDto(
                userFeignClient.getUserProfile(token));
        PaymentRequest paymentRequest =
                new PaymentRequest(paymentMethod);

        PaymentLinkResponse resDetail = paymentService.createPayment(
                paymentRequest,
                usersDto,
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
            @PathVariable String paymentId
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
            @RequestParam String paymentLinkId
    ) {

        Boolean success =
                paymentService.proceedPayment(
                        paymentId,
                        paymentLinkId
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
