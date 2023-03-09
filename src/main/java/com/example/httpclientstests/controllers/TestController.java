package com.example.httpclientstests.controllers;

import com.example.httpclientstests.Payment;
import com.example.httpclientstests.repositories.PaymentsRepository;
import com.example.httpclientstests.PaymentsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ForkJoinPool;

@Slf4j
@RestController
@RequestMapping("/api/v1/test")
@RequiredArgsConstructor
public class TestController {

    private final PaymentsRepository paymentsRepository;
    private final PaymentsService paymentsService;

    @PostMapping("/start")
    public String start(@RequestParam("connector") String connector) {
        log.info("start...");

        Page<Payment> page;
        int pageNumber = 0;
        do {
            page = paymentsRepository.findAll(PageRequest.of(pageNumber, 20));
            page.stream().parallel().forEach(payment -> postPayment(connector, payment).join());
            pageNumber++;
        } while (page.hasNext());
        log.info("done...");
        return "Done";
    }

    @PostMapping("/init")
    public String initialize(@RequestParam("count") int count) {
        log.info("init...");
        List<Payment> paymentList = new ArrayList<>();
        Random random = new Random(999999999999999L);
        for (int i = 0; i < count; i++) {
            paymentList.add(Payment.builder()
                    .account(String.valueOf(random.nextLong()))
                    .amount(new BigDecimal(random.nextDouble()))
                    .build());
        }
        paymentsRepository.saveAll(paymentList);
        return "Done";
    }

    private CompletableFuture<Void> postPayment(String connector, Payment payment) {
        log.info("posting payment with id: {}", payment.getId());
        return CompletableFuture.supplyAsync(() -> {
            if ("feign".equals(connector)) {
                paymentsService.postPaymentFeign(payment);
            } else {
                paymentsService.postPaymentWebClient(payment);
            }
            return null;
        }, new ForkJoinPool(10));
    }
}