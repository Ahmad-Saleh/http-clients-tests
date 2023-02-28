package com.example.httpclientstests;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentResponseRepository extends JpaRepository<PaymentResponse, Long> {
}
