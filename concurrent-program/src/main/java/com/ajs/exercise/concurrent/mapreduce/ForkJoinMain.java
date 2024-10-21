package com.ajs.exercise.concurrent.mapreduce;

/**
 * Fork/Join - A technique to split intensive task into chunks that can be performed in parallel to maximize the
 * computational power.
 * 
 * A support for parallelism.
 * 
 * Map Phase: Split the data space to be processed by an algorithm into independent chunks of data "small enough".
 * 
 * Reduce Phase: Once a set of chunks has been processed, partial results can be collected to construct the final
 * result.
 * 
 * 
 * ForJoinPool: An executor supports parallelism, dedicated to execute tasks/instances implementing ForkJoinTask.
 * Dispatches the tasks among its internal threads by stealing jobs when a task is waiting for another task to complete
 * and there are pending tasks to be run.
 * 
 * ForkJoinTask: Supports the creation of subtasks plus waiting for the subtasks to complete. Provides two two methods
 * the fork() and join().
 * 
 * fork(): Allows a ForkJoinTask to be planned for asynchronous execution and to be launched from existing one.
 * 
 * join(): Allows a ForkJoinTask to wait for the completion of another one.
 * 
 * 
 * Two specializations of ForkJoinTask:
 * 
 * 1) RecursiveAction - Resultless ForkJoinTask, join() method returns null upon completion of task.
 * 
 * 2) RecursiveTask - Result-bearing ForkJoinTask. It is preferred over RecursiveAction because most divide-and-conquer
 * algorithms return a value from a computation over a data set.
 * 
 * 
 * Best Practices:
 * 
 * 1) fork/join tasks should operate as in-memory algorithms, no I/O operations.
 * 
 * 2) Communication between tasks through shared state should be avoided, to avoid unnecessary locking.
 * 
 * 
 * 
 */
public class ForkJoinMain {

	public static void main(String[] args) {
		System.out.println("Available " + Runtime.getRuntime().availableProcessors());
	}
}
