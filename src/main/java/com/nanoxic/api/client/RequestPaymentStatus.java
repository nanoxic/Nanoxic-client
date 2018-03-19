package com.nanoxic.api.client;

class RequestPaymentStatus extends RequestMessage {

	private static final long serialVersionUID = -2390001417270126147L;

	private String paymentId;

	public RequestPaymentStatus(String paymentId) {
		super("get_payment_status");
		setPaymentId(paymentId);
	}

	public String getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}

}
