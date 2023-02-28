package com.example.httpclientstests;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentsService {

    private final FeignConnector feignConnector;
    private final WebClientConnector webClientConnector;
    private final ResponseHandler responseHandler;

    public void postPaymentFeign(Payment payment){
        PaymentResponse paymentResponse = feignConnector.postPayment(payment);
        responseHandler.handleResponse(paymentResponse);
    }

    public void postPaymentWebClient(Payment payment){
        webClientConnector.postPayment(payment);
    }
}
