package com.example.grainstorage.controller;

import com.example.grainstorage.dto.request.PaymentRequest;
import com.example.grainstorage.dto.response.ApiResponse;
import com.example.grainstorage.entity.Payment;
import com.example.grainstorage.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
@CrossOrigin(origins = "*")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Payment>> recordPayment(@Valid @RequestBody PaymentRequest request) {
        Payment payment = paymentService.recordPayment(request);
        return new ResponseEntity<>(ApiResponse.ok("Payment processed and accounting journal posted successfully", payment), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Payment>>> getAllPayments() {
        List<Payment> payments = paymentService.getAllPayments();
        return ResponseEntity.ok(ApiResponse.ok(payments));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Payment>> getPaymentById(@PathVariable Long id) {
        Payment payment = paymentService.getPaymentById(id);
        return ResponseEntity.ok(ApiResponse.ok(payment));
    }
}
