package com.nanoxic.api.client;

import java.math.BigDecimal;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.nanoxic.api.client.exceptions.InitializationException;

/**
 * 
 * Singleton class to initialize the Nanoxic client.
 * 
 * @author Koen De Voegt
 *
 */
public class Nanoxic {

	private static String key;
	private static String hostname = "localhost";
	private static int port = 8080;

	public enum State {
		OPEN, CANCELLED, EXPIRED, PENDING, PAID, ERROR;
	}
	
	// Constructor
	private Nanoxic() {
	}

	// Inialisation methodes
	/**
	 * Initialize the connection to Nanoxic. Using default port 8080 on localhost.
	 */
	public static void init(String key) {
		Nanoxic.key = key;
	}

	/**
	 * Initialize the connection to Nanoxic.
	 *
	 * @param hostname
	 *            The hostname of Nanoxic.
	 */
	public static void init(String key, final String hostname) {
		if (hostname == null) {
			throw new InitializationException("Hostname can not be null");
		}
		init(key);
		Nanoxic.hostname = hostname;
	}

	/**
	 * Initialize the connection to Nanoxic.
	 *
	 * @param hostname
	 *            The hostname of Nanoxic.
	 * @param port
	 *            The RPC port of Nanoxic.
	 */
	public static void init(String key, final String hostname, final int port) {
		init(key, hostname);
		Nanoxic.port = port;
	}

	// Getters and Setters
	/**
	 * Returns the hostname
	 * 
	 * @return hostname
	 */
	public static String getHostname() {
		return hostname;
	}

	/**
	 * Returns the port
	 * 
	 * @return port
	 */
	public static int getPort() {
		return port;
	}

	/**
	 * Returns the Nanoxic API key
	 * 
	 * @return The Nanoxic API key
	 */
	public static String getKey() {
		return key;
	}

	/**
	 * 
	 * @param amount
	 * @param currency
	 */
	public static ResponseStartPayement startPayment(BigDecimal amount, String currency) {
		if ((currency.equals("EURO")) || currency.equals("NANO")) {
			RequestStartPayment requestStartPayment = new RequestStartPayment();
			requestStartPayment.setAmount(amount);
			requestStartPayment.setCurrency(currency);
			ResponseStartPayement responseStartPayement = (ResponseStartPayement) HttpClient
					.getResponse(requestStartPayment, ResponseStartPayement.class);
			return responseStartPayement;
		} else {
			System.err.println("not a valid currency");
			return null;
		}
	}

	/**
	 * 
	 * @param paymentId
	 * @return
	 */
	public static ResponsePaymentStatus getPaymentStatus(String paymentId) {
		ResponsePaymentStatus responsePaymentStatus = (ResponsePaymentStatus) HttpClient
				.getResponse(new RequestPaymentStatus(paymentId), ResponsePaymentStatus.class);
		return responsePaymentStatus;
	}

	public static State waitForStatusChange(String paymentId, long timeout) {
		State state = null;
		ExecutorService executor = Executors.newSingleThreadExecutor();
		Future<State> future = executor.submit(new WaitForStatusChangeThread(paymentId));
		try {
			state = future.get(timeout, TimeUnit.SECONDS);
		} catch (TimeoutException e) {
			future.cancel(true);
			executor.shutdownNow();
			return state;
		} catch (Exception e) {

		}
		executor.shutdownNow();
		return state;
	}

	public static boolean waitForStatusChange(String paymentId, State status, long timeout) {
		ExecutorService executor = Executors.newSingleThreadExecutor();
		Future<State> future = executor.submit(new WaitForStatusChangeThread(paymentId, status));

		try {
			future.get(timeout, TimeUnit.SECONDS);
		} catch (TimeoutException e) {
			future.cancel(true);
			executor.shutdownNow();
			return false;
		} catch (Exception e) {

		}
		executor.shutdownNow();
		return true;
	}
	
	public static void addStatusChangeListener(String paymentId, StateChangeListener stateChangeListener) throws InterruptedException {
		StateMonitoringThread stateMonitoringThread = new StateMonitoringThread(paymentId, stateChangeListener);
		Thread thread = new Thread(stateMonitoringThread, "StateMonitoringThread " + paymentId);
		thread.start();
		thread.join();
	}

}
