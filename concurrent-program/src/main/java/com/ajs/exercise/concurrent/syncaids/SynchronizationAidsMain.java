package com.ajs.exercise.concurrent.syncaids;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

import com.ajs.exercise.concurrent.highlevel.ConcurrentUtility;

/**
 * Synchronization Aids/barriers.
 * 
 * 1) Semaphore
 * 
 * 2) Exchanger
 * 
 * 3) CyclicBarrier
 * 
 * 4) CountDownLatch
 * 
 * 5) Phaser
 * 
 * 
 */
public class SynchronizationAidsMain {

	public static void main(String[] args) {
		// testSemaphoreScenario1();
		// testSemaphoreScenario2();
		// testExchanger();
		// testCyclicBarrier();
		// testCountDownLatch();
		testPhaser();
	}

	/**
	 * 1) Semaphore
	 * 
	 * * A counting semaphore, it has a set of permits/tokens, guards the critical section against entry by more than N
	 * threads at a time.
	 * 
	 * * It can also be used to send signals between two threads.
	 * 
	 * * e.g. A guard at the entrance takes into account the number of people can enter, or already entered into the
	 * building.
	 * 
	 * * A client can get permit(s) by calling acquire() method. If a requested number of permits available then returns
	 * immediately, otherwise a method call gets blocked until one is available.
	 * 
	 * * A client can release permit(s) by invoking release() method.
	 * 
	 * * There is no requirement that a thread that releases a permit must have acquired that permit by calling acquire.
	 * 
	 * * Used to restrict the number of threads that can access some (physical or logical) resource.
	 * 
	 * ** Classifications: Binary Semaphore (Size - 0) and Counting Semaphore (Size - >1)
	 * 
	 * ** Applications:
	 * 
	 * 1) Creating/Accessing pooled resources (JDBC connection, JMS connection,...).
	 * 
	 * 2) Producer/Consumer problem.
	 * 
	 */
	public static void testSemaphoreScenario1() {
		int threadPoolSize = 10;
		final SemaphoreDemo semaphoreDemo = new SemaphoreDemo(5, true);
		ExecutorService execServ = Executors.newFixedThreadPool(threadPoolSize);

		// Scenario 1 : Acquire and Release - By same thread.
		Runnable runnableTask = new Runnable() {
			@Override
			public void run() {
				System.out.println("Counter:" + semaphoreDemo.incrementAndGet());
			}
		};

		for (int i = 0; i < threadPoolSize; i++) {
			execServ.submit(runnableTask);
		}
		execServ.shutdown();
	}

	public static void testSemaphoreScenario2() {
		int threadPoolSize = 10;
		final SemaphoreDemo semaphoreDemo = new SemaphoreDemo(2, true);
		ExecutorService execServ = Executors.newFixedThreadPool(threadPoolSize);

		// Scenario 2 : Acquire and Release - By different threads.
		Runnable entryTask = new Runnable() {
			@Override
			public void run() {
				semaphoreDemo.entry();
			}
		};

		Runnable exitTask = new Runnable() {
			@Override
			public void run() {
				ConcurrentUtility.consumeTime(new Random().nextInt(10), TimeUnit.SECONDS);
				semaphoreDemo.exit();
			}
		};

		for (int i = 0; i < threadPoolSize; i++) {
			execServ.submit(entryTask);
		}

		for (int i = 0; i < threadPoolSize; i++) {
			execServ.submit(exitTask);
		}

		execServ.shutdown();
	}

	/**
	 * 2) Exchanger
	 * 
	 * * A point at which threads can pair and swap objects with each other.
	 * 
	 * * Each thread presents some object on entry to exchange method, matches with a partner thread, and receives it's
	 * partner's object on return.
	 * 
	 * * A presenter thread waits for another thread to arrive at this exchange point, and then transfers the given
	 * object to it, receiving its object in return.
	 * 
	 * * It can be viewed as a bi-directional SynchronousQueue.
	 * 
	 */

	public static void testExchanger() {
		Exchanger<String> exchanger = new Exchanger<String>();
		ExchangerDemo exchangerTask1 = new ExchangerDemo(exchanger, "Obj1");
		ExchangerDemo exchangerTask2 = new ExchangerDemo(exchanger, "Obj2");

		ExecutorService executor = Executors.newFixedThreadPool(2);
		executor.execute(exchangerTask1);
		executor.execute(exchangerTask2);
		executor.shutdown();
	}

	/**
	 * 3) CyclicBarrier
	 * 
	 * * Allows a set of threads to wait for each other to reach a common barrier point.
	 * 
	 * * Useful for involving a fixed sized party of threads that wait for each other.
	 * 
	 * * It can be reused (by reset) after the waiting threads are released.
	 * 
	 * * The last worker thread invokes await may optionally performs the common cyclic barrier action.
	 * 
	 * * The CyclicBarrier uses an all-or-none breakage model for failed synchronization attempts: If a thread leaves a
	 * barrier point prematurely because of interruption, failure, or timeout, all other threads waiting at that barrier
	 * point will also leave abnormally
	 */
	public static void testCyclicBarrier() {
		int parties = 3;

		// Barrier Action: Useful for updating shared resources state.
		Runnable barrierAction = new Runnable() {
			@Override
			public void run() {
				System.out.println("Last Worker Thread " + Thread.currentThread().getName()
						+ " - Executed common Barrier Action for barrier-2.");
			}
		};
		CyclicBarrier barrier1 = new CyclicBarrier(parties);
		CyclicBarrier barrier2 = new CyclicBarrier(parties, barrierAction);

		CyclicBarrierDemo barrierTask1 = new CyclicBarrierDemo(barrier1, barrier2);
		CyclicBarrierDemo barrierTask2 = new CyclicBarrierDemo(barrier1, barrier2);
		CyclicBarrierDemo barrierTask3 = new CyclicBarrierDemo(barrier1, barrier2);

		ExecutorService executor = Executors.newFixedThreadPool(parties);
		executor.execute(barrierTask1);
		executor.execute(barrierTask2);
		executor.execute(barrierTask3);
		executor.shutdown();
	}

	/**
	 * 4) CountDownLatch
	 * 
	 * * Allows one or more threads to wait until a set of operations being performed in other threads completes.
	 * 
	 * * It is initialized with a given count.
	 * 
	 * * The invocation of await() methods of all the threads block until the count reaches zero.
	 * 
	 * * Count can't be reset.
	 * 
	 * * All the blocked threads are released once the count reaches zero.
	 */
	public static void testCountDownLatch() {
		int startLatchCount = 1;
		int doneLatchCount = 5;
		CountDownLatch prerequisiteJobLatch = new CountDownLatch(startLatchCount);
		CountDownLatch workerJobLatch = new CountDownLatch(doneLatchCount);

		ExecutorService executor = Executors.newFixedThreadPool(doneLatchCount);
		for (int i = 0; i < doneLatchCount; i++) {
			executor.execute(new CountDownLatchDemo(prerequisiteJobLatch, workerJobLatch));
		}
		executor.shutdown();
		/*
		 * Do some main activity (prerequisites) and decrement prerequisiteJobLatch by 1 to allow other threads to start
		 * operation.
		 */
		ConcurrentUtility.consumeTime(2, TimeUnit.SECONDS);
		System.out.println("Done prerequisites.");

		prerequisiteJobLatch.countDown(); // Let worker threads start their operation.
		try {
			workerJobLatch.await(); // Await worker threads to complete their operation.
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 5) Phaser (Introduced in Java SE 7)
	 * 
	 * * Handles variable number of parties/threads.
	 * 
	 * * Reusable synchronization barrier similar to CyclicBarrier and ConuntDownLatch but supporting more flexibility.
	 * 
	 * * Parties may be registered at any time. And optionally de-registered upon any arrival.
	 * 
	 * * Registration
	 * 
	 * * Synchronization - Arrival & Waiting
	 * 
	 * * Termination
	 * 
	 * * Tiering
	 * 
	 * * Monitoring
	 */
	public static void testPhaser() {
		// Create phaser instance.
		Phaser phaser = new Phaser();

		// Register main thread with phaser.
		phaser.register();

		// Create phaser demo tasks.
		ExecutorService executor = Executors.newFixedThreadPool(2);
		for (int i = 0; i < 2; i++) {
			phaser.register();
			PhaserDemo task = new PhaserDemo(phaser);
			executor.execute(task);
		}

		ConcurrentUtility.consumeTime(5, TimeUnit.SECONDS);
		executor.shutdown();

		System.out.println("RegisteredParties..:" + phaser.getRegisteredParties());

		// Arrive and Deregister main thread from Phaser to allow other threads to advance further.
		phaser.arriveAndDeregister();

	}

}
