package com.dhana.exercise.concurrent.lowlevel;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import com.dhana.exercise.concurrent.highlevel.ConcurrentUtility;

/**
 * Thread.yield() causes the currently executing thread object to temporarily pause and allow other threads to execute.
 * 
 * Thread's suspension time depends on OS thread scheduler, hence you may not be able to check whether the thread is
 * suspended or not.
 * 
 * This would be useful when there are cases of thread starvation.
 *
 */
public class ThreadSuspensionWithYield {

	private static final long THREAD_SLEEP_TIME_MILLIS = 60000;

	public static void main(String[] args) {
		testThreadSuspension();
	}

	private static void testThreadSuspension() {
		final Thread[] threadArr = createThreads(5);
		for (Thread thread : threadArr) {
			thread.start();
		}

		Thread monitoringThread = new Thread(new Runnable() {
			@Override
			public void run() {
				outterloop: while (true) {
					for (Thread thread : threadArr) {
						if (thread.getThreadGroup() == null || thread.getThreadGroup().activeCount() <= 0) {
							break outterloop;
						}
						System.out.println(thread.getName() + " in " + thread.getState() + " state.");
					}
					ConcurrentUtility.consumeTime(1000, TimeUnit.MILLISECONDS);
				}
			}
		});
		monitoringThread.start();
	}

	private static Thread[] createThreads(int count) {
		Thread[] threadArr = new Thread[count];
		for (int i = 0; i < threadArr.length; i++) {
			threadArr[i] = new Thread(new Runnable() {
				@Override
				public void run() {
					String threadName = Thread.currentThread().getName();
					System.out.println(threadName + " Started");
					if (new Random().nextBoolean()) {
						Thread.yield();
						System.out.println(threadName + " Suspended.");
					}

					ConcurrentUtility.consumeTime(THREAD_SLEEP_TIME_MILLIS, TimeUnit.MILLISECONDS);
					System.out.println(threadName + " Ended");
				}
			}, "Thread" + (i + 1));
		}
		return threadArr;
	}
}
