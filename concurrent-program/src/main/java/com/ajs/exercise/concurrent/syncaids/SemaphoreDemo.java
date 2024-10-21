package com.ajs.exercise.concurrent.syncaids;

import java.util.concurrent.Semaphore;

/**
 * Semaphore vs Mutex:
 * 
 * * A mutex is essentially the same thing as a binary semaphore and sometimes uses the same basic implementation. The
 * differences between them are in how they are used. While a binary semaphore may be used as a mutex, a mutex is a more
 * specific use-case, which allows extra guarantees:
 * 
 * * Mutexes have a concept of an owner. Only the process that locked the mutex is supposed to unlock it. If the owner
 * is stored by the mutex this can be verified at runtime.
 * 
 * * Mutexes may provide priority inversion safety. If the mutex knows its current owner, it is possible to promote the
 * priority of the owner whenever a higher-priority task starts waiting on the mutex.
 * 
 * * Mutexes may also provide deletion safety, where the process holding the mutex cannot be accidentally deleted.
 * 
 *
 */
public class SemaphoreDemo {
	private static volatile Integer counter = 0;
	Semaphore semaphore = null;

	public Semaphore getSemaphore() {
		return semaphore;
	}

	public SemaphoreDemo(int maxNumberOfThreads, boolean fifoOrdered) {
		this.semaphore = new Semaphore(maxNumberOfThreads, fifoOrdered);
	}

	// Acquire and Release by same thread.

	public Integer incrementAndGet() {
		Integer retVal = null;
		try {
			System.out.println("Queue Length:" + semaphore.getQueueLength());
			System.out.println("Available Permits:" + semaphore.availablePermits());

			getSemaphore().acquire();
			System.out.println("Permit Acquired");

			retVal = counter++;
			Thread.sleep(10000);

			getSemaphore().release();
			System.out.println("Permit Released");

		} catch (InterruptedException e) {
			e.printStackTrace();
			return null;
		}

		return retVal;
	}

	// Acquire and Release by different scenarios.
	public void entry() {
		try {
			System.out.println(Thread.currentThread().getName() + " Entering... Current Queue Length:" + getSemaphore().getQueueLength());
			getSemaphore().acquire();
			System.out.println(Thread.currentThread().getName() + " Entered.");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void exit() {
		getSemaphore().release();
		System.out.println(Thread.currentThread().getName() + " Exited.");
	}

}
