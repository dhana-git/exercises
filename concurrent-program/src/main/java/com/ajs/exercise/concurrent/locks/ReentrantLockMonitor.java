package com.ajs.exercise.concurrent.locks;

import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockMonitor implements Runnable {

	ReentrantLock lock;

	public ReentrantLockMonitor(ReentrantLock lock) {
		this.lock = lock;
	}

	@Override
	public void run() {
		while (lock.getQueueLength() > 0) {
			System.out.println("--------------------------------------------");
			System.out.println("QueueLength:" + lock.getQueueLength());
			System.out.println("QueuedThreads:" + lock.hasQueuedThreads());
			System.out.println("isLocked:" + lock.isLocked());
			System.out.println("--------------------------------------------");
		}
	}
}
