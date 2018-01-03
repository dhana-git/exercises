package com.dhana.exercise.concurrent.locks;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import com.dhana.exercise.concurrent.highlevel.ConcurrentUtility;

public class ReentrantLockDemo implements Runnable {
	private static long sharedValue = 0;

	ReentrantLock lock;

	public ReentrantLockDemo(ReentrantLock lock) {
		this.lock = lock;
	}

	@Override
	public void run() {
		// Acquiring a lock
		this.lock.lock();

		System.out.println("New value:" + increment());

		// Releasing a lock
		this.lock.unlock();
	}

	private long increment() {
		ConcurrentUtility.consumeTime(2, TimeUnit.SECONDS);
		sharedValue = sharedValue + 1;
		return sharedValue;
	}

}
