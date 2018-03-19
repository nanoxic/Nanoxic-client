package com.nanoxic.api.client;

import java.util.concurrent.Callable;

import com.nanoxic.api.client.Nanoxic.State;

class WaitForStatusChangeThread implements Callable<State> {

	private String paymentId;
	private State previousState = null;
	private State returnState;
	private State currentState;
	volatile boolean running = true;

	// Constructors
	public WaitForStatusChangeThread(String paymentId) {
		this.paymentId = paymentId;
	}

	public WaitForStatusChangeThread(String paymentId, State returnState) {
		this.paymentId = paymentId;
		this.returnState = returnState;
	}

	// Getters
	public State getCurrentState() {
		return currentState;
	}

	// Methods
	@Override
	public State call() throws InterruptedException {
		while (running) {
			currentState = Nanoxic.getPaymentStatus(paymentId).getState();
			if (!currentState.equals(previousState)) {
				if (previousState != null) {
					running = false;
					return currentState;
				}
				previousState = currentState;
			} 
			if (currentState.equals(returnState)) {
				running = false;
				return currentState;
			}
			Thread.sleep(300);
		}
		return null;
	}

}
