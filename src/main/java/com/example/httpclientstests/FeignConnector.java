package com.example.httpclientstests;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Component
@FeignClient(name = "feign-connector-test",
        url = "${integration.mw.url}")
public interface FeignConnector {

    @GetMapping("${integration.postPayment.api}")
    PaymentResponse postPayment(@RequestBody Payment payment);
}

