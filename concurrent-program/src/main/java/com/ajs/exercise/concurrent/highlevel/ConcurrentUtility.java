package com.ajs.exercise.concurrent.highlevel;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class ConcurrentUtility {
	private static volatile Semaphore semaphore = new Semaphore(1);

	public static void consumeTime(long time, TimeUnit unit) {
		long inTime = System.currentTimeMillis();
		long delayTime = unit.toMillis(time);
		while (true) {
			"X".equalsIgnoreCase("XY");
			long now = System.currentTimeMillis();
			if (now - inTime >= delayTime) {
				break;
			}
		}
	}

	public static void sleep(long time, TimeUnit unit) {
		try {
			semaphore.tryAcquire(2, time, unit);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
