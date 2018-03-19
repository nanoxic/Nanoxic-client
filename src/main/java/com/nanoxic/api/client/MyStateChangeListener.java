package com.nanoxic.api.client;

import com.nanoxic.api.client.Nanoxic.State;

public class MyStateChangeListener extends StateChangeListener {

	@Override
	public void statusChanged(State state) {
		System.out.println("StateChangeListener " + state);
		switch (state) {
		case ERROR:
			getStateMonitoringThread().stop();
			break;
		case PAID:
			getStateMonitoringThread().stop();
			break;
		default:
			break;
		}
	}

}
