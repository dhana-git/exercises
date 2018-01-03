package com.dhana.exercise.concurrent.lowlevel;

import java.util.concurrent.TimeUnit;

import com.dhana.exercise.concurrent.highlevel.ConcurrentUtility;

/**
 * Producer Consumer pattern/problem using Plain Old Threads (POTs).
 */
public class ProducerConsumerProbPOT {
	public static final int maxSize = 10;
	public static volatile int[] stock = new int[maxSize];
	public static volatile int size = 0;

	public static void main(String[] args) {
		for (int i = 0; i < 5; i++) {
			Producer producer = new Producer();
			producer.setName("Producer-" + i);
			producer.start();
		}

		for (int i = 0; i < 5; i++) {
			Consumer consumer = new Consumer();
			consumer.setName("Consumer-" + i);
			consumer.start();
		}
	}

	static class Producer extends Thread {
		@Override
		public void run() {
			while (true) {
				synchronized (stock) {
					if (size < maxSize) {
						size = size + 1;
						if (size == 1) {
							stock.notifyAll();
						}
						stock[size - 1] = size;
						System.out.println("Produced Item:" + size);
					} else if (size == maxSize) {
						try {
							System.out.println("Producer (" + Thread.currentThread().getName()
									+ ") waiting for the items to be consumed...");
							stock.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
				ConcurrentUtility.sleep(3, TimeUnit.SECONDS);
			}
		}

	}

	static class Consumer extends Thread {

		@Override
		public void run() {
			while (true) {
				synchronized (stock) {
					if (size > 0) {
						size = size - 1;
						if (size == maxSize - 1) {
							stock.notifyAll();
						}
						System.out.println("Consumed Item:" + stock[size]);
						stock[size] = 0;
					} else {
						try {
							System.out.println("Consumer (" + Thread.currentThread().getName()
									+ ") waiting for the items to be added into the stock...");
							stock.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
				ConcurrentUtility.sleep(3, TimeUnit.SECONDS);
			}
		}
	}
}
