package com.nanoxic.api.client;

import java.math.BigDecimal;

class ResponseStartPayement extends ResponseMessage {

	private String address;
	private BigDecimal amount;
	private String expire;
	private String nanoUrl;
	private String paymentId;
	private String paymentUrl;
	private boolean success;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getExpire() {
		return expire;
	}

	public void setExpire(String expire) {
		this.expire = expire;
	}

	public String getNanoUrl() {
		return nanoUrl;
	}

	public void setNanoUrl(String nanoUrl) {
		this.nanoUrl = nanoUrl;
	}

	public String getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}

	public String getPaymentUrl() {
		return paymentUrl;
	}

	public void setPaymentUrl(String paymentUrl) {
		this.paymentUrl = paymentUrl;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}
}
