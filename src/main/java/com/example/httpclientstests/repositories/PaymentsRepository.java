package com.example.httpclientstests.repositories;

import com.example.httpclientstests.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentsRepository extends JpaRepository<Payment, Long> {
}
