package com.ajs.exercise.concurrent.syncaids;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.ajs.exercise.concurrent.highlevel.ConcurrentUtility;

public class CyclicBarrierDemo implements Runnable {

	CyclicBarrier barrier1;
	CyclicBarrier barrier2;

	public CyclicBarrierDemo(CyclicBarrier barrier1, CyclicBarrier barrier2) {
		this.barrier1 = barrier1;
		this.barrier2 = barrier2;
	}

	@Override
	public void run() {
		try {

			// Do some operation.
			ConcurrentUtility.consumeTime(1, TimeUnit.SECONDS);

			// Check point -1
			System.out.println(Thread.currentThread().getName() + " Waiting @ barricade 1");
			this.barrier1.await(10, TimeUnit.SECONDS);
			System.out.println(Thread.currentThread().getName() + " Crossed @ barricade 1");

			// Do some operation.
			ConcurrentUtility.consumeTime(1, TimeUnit.SECONDS);

			// Check point - 2
			System.out.println(Thread.currentThread().getName() +" Waiting @ barricade 2");
			this.barrier2.await(5, TimeUnit.SECONDS);
			System.out.println(Thread.currentThread().getName() + " Crossed @ barricade 2");

		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (BrokenBarrierException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
			e.printStackTrace();
		}

	}

}
