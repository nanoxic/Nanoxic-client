package com.nanoxic.api.client;

import com.nanoxic.api.client.Nanoxic.State;

class StateMonitoringThread implements Runnable {

	private String paymentId;
	private State previousState = null;
	private State currentState;
	private StateChangeListener stateChangeListener;
	volatile boolean running = true;

	// Constructors
	public StateMonitoringThread(String paymentId, StateChangeListener stateChangeListener) {
		this.paymentId = paymentId;
		this.stateChangeListener = stateChangeListener;
		stateChangeListener.setStateMonitoringThread(this);
	}

	// Getters
	public State getCurrentState() {
		return currentState;
	}

	// Methods
	@Override
	public void run() {
		while (running) {
			currentState = Nanoxic.getPaymentStatus(paymentId).getState();
			if (!currentState.equals(previousState)) {
				previousState = currentState;
				stateChangeListener.statusChanged(currentState);
			}
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				running = false;
			}
		}
	}

	public void stop() {
		running = false;
	}

}
