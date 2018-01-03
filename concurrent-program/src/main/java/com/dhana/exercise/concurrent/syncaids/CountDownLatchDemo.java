package com.dhana.exercise.concurrent.syncaids;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import com.dhana.exercise.concurrent.highlevel.ConcurrentUtility;

public class CountDownLatchDemo implements Runnable {

	CountDownLatch prerequisiteJobLatch;
	CountDownLatch workerJobLatch;

	public CountDownLatchDemo(CountDownLatch prerequisiteJobLatch, CountDownLatch workerJobLatch) {
		this.prerequisiteJobLatch = prerequisiteJobLatch;
		this.workerJobLatch = workerJobLatch;
	}

	@Override
	public void run() {
		try {
			System.out.println("Awaiting for prerequisite job latch to complete/open.");
			prerequisiteJobLatch.await();
			doActualWork();
			System.out.println("Decrement the worker job latch count by 1.");
			workerJobLatch.countDown();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	private void doActualWork() {
		ConcurrentUtility.consumeTime(2, TimeUnit.SECONDS);
		System.out.println("Done my work.");
	}

}
