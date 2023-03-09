package com.example.httpclientstests.repositories;

import com.example.httpclientstests.PaymentResponse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentResponseRepository extends JpaRepository<PaymentResponse, Long> {
}
