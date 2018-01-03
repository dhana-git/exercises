package com.dhana.exercise.concurrent.syncaids;

import java.util.concurrent.Exchanger;

public class ExchangerDemo implements Runnable {

	private Exchanger<String> exchanger;
	private String objToBeExchanged;

	public ExchangerDemo(Exchanger<String> exchanger, String objToBeExchanged) {
		this.exchanger = exchanger;
		this.objToBeExchanged = objToBeExchanged;
	}

	@Override
	public void run() {
		try {
			String objectReceived = this.exchanger.exchange(objToBeExchanged);
			System.out.println(Thread.currentThread().getName() + " Exchanged " + objToBeExchanged + " for "
					+ objectReceived);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
