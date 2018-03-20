package com.nanoxic.api.client;

import com.nanoxic.api.client.Nanoxic.State;

public abstract class StateChangeListener {

	private StateMonitoringThread stateMonitoringThread;

	public abstract void statusChanged(State state);

	public StateMonitoringThread getStateMonitoringThread() {
		return stateMonitoringThread;
	}

	public void setStateMonitoringThread(StateMonitoringThread stateMonitoringThread) {
		this.stateMonitoringThread = stateMonitoringThread;
	}
	
	public void stopStateMonitoringThread() {
		stateMonitoringThread.stop();
	}
}
