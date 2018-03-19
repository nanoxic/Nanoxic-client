package com.nanoxic.api.client;

import java.math.BigDecimal;

class RequestStartPayment extends RequestMessage {

	private static final long serialVersionUID = -2390001417270126147L;

	private BigDecimal amount;
	private String currency;

	public RequestStartPayment() {
		super("start_payment");
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

}
