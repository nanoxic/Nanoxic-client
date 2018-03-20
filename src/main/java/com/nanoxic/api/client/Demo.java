package com.nanoxic.api.client;

import java.math.BigDecimal;

import com.nanoxic.api.client.Nanoxic.State;

public class Demo {

	public static void main(String[] args) throws InterruptedException {
		Nanoxic.init(
				"eyJhbGciOiJIUzI1NiIsInppcCI6IkRFRiJ9.eNqqViotTi3KS8xNVbJSKkktLlHSUcosLgZy8hLz8isyk5VqAQAAAP__.GSqLm1se5QxBvsp6HYlxc8u3lYndGXbZRc4DhniL0GM");
		ResponseStartPayement responseStartPayement = Nanoxic.startPayment(new BigDecimal("0.0025"), "NANO");
		ResponsePaymentStatus responsePaymentStatus = Nanoxic.getPaymentStatus(responseStartPayement.getPaymentId());

		System.out.println(responsePaymentStatus.getState());
		System.out.println(responseStartPayement.getAddress());
		System.out.println(responseStartPayement.getAmount());

		StateChangeListener stateChangeListener = new MyStateChangeListener();
		Nanoxic.addStatusChangeListener(responseStartPayement.getPaymentId(), stateChangeListener);

		boolean success = Nanoxic.waitForStatusChange(responseStartPayement.getPaymentId(), State.ERROR, 180L);
		System.out.println(success);

	}

}
