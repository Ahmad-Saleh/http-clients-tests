package com.example.httpclientstests;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentsRepository extends JpaRepository<Payment, Long> {
}
