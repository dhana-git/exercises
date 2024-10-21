package com.ajs.exercise.concurrent.mapreduce;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.TimeUnit;

import com.ajs.exercise.concurrent.highlevel.ConcurrentUtility;

public class SimpleForkJoin {

	public static void main(String[] args) {
		int arraySize = Integer.MAX_VALUE >> 15;
		final Integer[] naturalNumbers = new Integer[arraySize];
		System.out.println("Array Size:" + arraySize);
		for (int i = 0; i < naturalNumbers.length; i++) {
			naturalNumbers[i] = new Integer(i + 1);
		}

		new Thread(new Runnable() {

			@Override
			public void run() {
				long startTime1 = System.currentTimeMillis();
				computeOnSingleThread(naturalNumbers);
				System.out.println("Elapsed Time (computeOnSingleThread):" + (System.currentTimeMillis() - startTime1));

			}
		}).start();
		;

		new Thread(new Runnable() {

			@Override
			public void run() {
				long startTime2 = System.currentTimeMillis();
				computeOnMultipleThreads(naturalNumbers);
				System.out.println("Elapsed Time (computeOnMultipleThreads):"
						+ (System.currentTimeMillis() - startTime2));

			}
		}).start();
		;

	}

	private static Integer computeOnSingleThread(Integer[] naturalNumbers) {
		SumTask task = new SumTask(naturalNumbers);
		Integer result = task.compute();
		System.out.println("Result:" + result);
		return result;
	}

	private static Integer computeOnMultipleThreads(Integer[] naturalNumbers) {
		ForkJoinPool forkJoinPool = new ForkJoinPool(16);
		SumForkJoinTask task = new SumForkJoinTask(naturalNumbers);
		Integer result = null;
		try {
			// new PoolMonitor(forkJoinPool).start();
			result = forkJoinPool.submit(task).get();
			System.out.println("Result:" + result);
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		forkJoinPool.shutdown();
		return result;
	}

	static class PoolMonitor extends Thread {
		ForkJoinPool pool;

		public PoolMonitor(ForkJoinPool pool) {
			this.pool = pool;
		}

		@Override
		public void run() {
			while (true) {
				System.out.println(this.pool.toString());
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if (this.pool.isTerminated()) {
					break;
				}
			}

		}
	}

	static class SumTask {
		private static final long serialVersionUID = 1L;
		Integer[] inputData;

		public SumTask(Integer[] inputData) {
			this.inputData = inputData;
		}

		protected Integer compute() {
			int length = inputData.length;
			if (length == 1) {
				ConcurrentUtility.consumeTime(5, TimeUnit.MILLISECONDS);
				return inputData[0];
			} else {
				int midKey = length / 2;
				SumTask task1 = new SumTask(Arrays.copyOfRange(inputData, 0, midKey));
				SumTask task2 = new SumTask(Arrays.copyOfRange(inputData, midKey, length));
				return task1.compute() + task2.compute();
			}
		}
	}

	static class SumForkJoinTask extends RecursiveTask<Integer> {
		private static final long serialVersionUID = 1L;
		Integer[] inputData;

		public SumForkJoinTask(Integer[] inputData) {
			this.inputData = inputData;
		}

		@Override
		protected Integer compute() {
			int length = inputData.length;
			if (length == 1) {
				ConcurrentUtility.consumeTime(5, TimeUnit.MILLISECONDS);
				return inputData[0];
			} else {
				int midKey = length / 2;
				SumForkJoinTask task1 = new SumForkJoinTask(Arrays.copyOfRange(inputData, 0, midKey));
				SumForkJoinTask task2 = new SumForkJoinTask(Arrays.copyOfRange(inputData, midKey, length));
				task2.fork();
				return task1.invoke() + task2.join();
			}
		}
	}
}
