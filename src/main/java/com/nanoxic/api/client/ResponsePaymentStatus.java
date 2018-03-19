package com.nanoxic.api.client;

import com.nanoxic.api.client.Nanoxic.State;

class ResponsePaymentStatus extends ResponseMessage {

	private State state;
	private boolean success;

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

}
