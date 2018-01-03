package com.dhana.exercise.concurrent.locks;

import java.util.Calendar;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import com.dhana.exercise.concurrent.highlevel.ConcurrentUtility;

public class LocksMain {

	public static void main(String[] args) {
		// testReentrantLock();
		testReentrantReadWriteLock();
		testStampedLock();
	}

	/**
	 * ReentrantLock is more similar to the implicit monitor lock accessed using synchronized block/method, but provides
	 * additional capabilities (An owner of a ReentrantLock can acquire lock repeatedly, can release the lock out of the
	 * method/block,...).
	 * 
	 * A ReentrantLock is owned by the thread last successfully locking, but not yet unlocking it.
	 * 
	 * The current thread should be a holder of the lock to release it, otherwise an exception will be thrown.
	 * 
	 */
	private static void testReentrantLock() {
		ReentrantLock lock = new ReentrantLock();
		ExecutorService executor = Executors.newFixedThreadPool(10);

		for (int i = 0; i < 10; i++) {
			ReentrantLockDemo lockDemo = new ReentrantLockDemo(lock);
			executor.execute(lockDemo);
		}
		executor.execute(new ReentrantLockMonitor(lock));

		executor.shutdown();

	}

	/**
	 * The ReadWriteLock (with two modes) solves the Reader-Writer problem.
	 * 
	 * It has a pair of associated locks one for read-only operations and one for writing.
	 * 
	 * The ReadLock is shared one and can be acquired by any number of threads unless the WriteLock is acquired and not
	 * yet released by Writer threads.
	 * 
	 * The WriteLock is exclusive and can be acquired by a thread, and when acquired none of the threads do read
	 * operations.
	 * 
	 */
	private static void testReentrantReadWriteLock() {
		final ReadWriteLockDemo readWriteLockDemo = ReadWriteLockDemo.getInstance();

		Runnable readTask = new Runnable() {
			@Override
			public void run() {
				System.out.println("Cached Item:" + readWriteLockDemo.getItem("X"));
			}
		};

		Runnable writeTask = new Runnable() {
			@Override
			public void run() {
				System.out.println("Old Value:" + readWriteLockDemo.addItem("X", Calendar.getInstance().getTime()));
			}
		};

		ExecutorService executor = Executors.newFixedThreadPool(20);
		executor.execute(writeTask);
		for (int i = 0; i < 10; i++) {
			executor.execute(writeTask);
		}
		ConcurrentUtility.consumeTime(1, TimeUnit.MILLISECONDS);
		for (int i = 0; i < 10; i++) {
			executor.execute(readTask);
		}

		executor.shutdown();

	}

	/**
	 * A Lock with three modes (Read, Write, Optimistic Read).
	 * 
	 * Read Lock: Acquires non-exclusive lock only if the lock is not currently held in write mode.
	 * 
	 * Write Lock: Acquires an exclusive lock.
	 * 
	 * Optimistic Read Lock: Acquires non-exclusive lock only if the lock is not currently held in write mode. It
	 * validates the given stamp that if the lock has not been acquired in write mode since obtaining the given stamp.
	 * 
	 */
	private static void testStampedLock() {

	}
}
