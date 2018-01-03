package com.dhana.exercise.concurrent.highlevel;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class ExecutorDemo {

	/**
	 * Single thread executor (corePoolSize:1, maxPoolSize:1).
	 * 
	 * Uses a single worker thread operating off an unbounded queue.
	 * 
	 * If any thread terminates due to a failure during execution prior to shutdown, a new one will take its place if
	 * needed to execute subsequent tasks.
	 * 
	 * Tasks are guaranteed to execute sequentially, and no more than one task will be active at any given time.
	 */
	// static final ExecutorService executor = Executors.newSingleThreadExecutor();

	/**
	 * Fixed size (nThreads) thread pool executor (corePoolSize: nThreads, maxPoolSize: nThreads)..
	 * 
	 * Creates a thread pool that reuses a fixed number of threads operating off a shared unbounded queue.
	 * 
	 * If any thread terminates due to a failure during execution prior to shutdown, a new one will take its place if
	 * needed to execute subsequent tasks.
	 * 
	 */
	// static final ExecutorService executor = Executors.newFixedThreadPool(2);

	/**
	 * Scheduled thread pool executor (specialization of thread pool executor) (corePoolSize:nThreads,
	 * maxPoolSize:nThreads).
	 * 
	 * Creates a thread pool that can schedule commands to run after a given delay, or to execute periodically.
	 * 
	 * */
	// static final ScheduledExecutorService executor = Executors.newScheduledThreadPool(2);

	/**
	 * Cached thread pool (corePoolSize:0, maxPoolSize:Int Max Value).
	 * 
	 * Creates a thread pool that creates new threads as needed, but will reuse previously constructed threads when they
	 * are available.
	 * 
	 * Threads that have not been used for sixty seconds are terminated and removed from the cache. Thus, a pool that
	 * remains idle for long enough will not consume any resources.
	 */
	static final ExecutorService executor = Executors.newCachedThreadPool();

	/**
	 * Unconfigurable executor service wrapper.
	 * 
	 * It doesn't expose any methods which alters its properties (corePoolSize, maxPoolSize,...) post initialization.
	 */
	// static final ExecutorService executor = Executors.unconfigurableExecutorService(executor);

	public static void main(String[] args) {
		System.out.println("AvailableProcessors:" + Runtime.getRuntime().availableProcessors());
		try {
			// testExecuteRunnable();
			// testSubmitCallable();
			// testSubmitRunnable();
			// testSubmitRunnableWithResult();
			// testInvokeAny();
			// testInvokeAnyWithTimeout();
			testInvokeAll();
			// testInvokeAllWithTimeout();
		} catch (Exception e) {
			e.printStackTrace();
		}
		executor.shutdown();
		System.out.println("isShutdown:" + executor.isShutdown());
		System.out.println("isTerminated:" + executor.isTerminated());
	}

	/**
	 * void execute(Runnable command): Executes the given command (Runnable) at some time in the future. And doesn't
	 * return any result or future.
	 */
	public static void testExecuteRunnable() {
		System.out.println("Executor Implementation:" + executor.getClass().getCanonicalName());
		executor.execute(new RunnableTask());
	}

	/**
	 * Future<T> submit(Callable<T> task): Submits a value-returning task (Callable) for execution and returns a Future
	 * representing the pending results of the task. The Future's get method will return the task's result upon
	 * successful completion.
	 */
	public static void testSubmitCallable() throws InterruptedException, ExecutionException {
		Future<Person> resultFuture = executor.submit(new CallableTask());
		/**
		 * get() : Waits for the computation to complete, and then retrieves its result (task's result upon successful
		 * completion).
		 */
		Person person = resultFuture.get();
		System.out.println("Result Obj:" + person);
	}

	/**
	 * Future<?> submit(Runnable task): Submits a Runnable task for execution and returns a Future representing that
	 * task. The Future's get method will return null upon successful completion.
	 */
	public static void testSubmitRunnable() throws InterruptedException, ExecutionException {
		Future<?> resultFuture = executor.submit(new RunnableTask());
		/**
		 * get() : Waits for the computation to complete, and then retrieves its result (null upon successful
		 * completion).
		 */
		System.out.println("Result Obj:" + resultFuture.get());
	}

	/**
	 * Future<T> submit(Runnable task, T result): Submits a Runnable task for execution and returns a Future
	 * representing that task. The Future's get method will return the given result upon successful completion.
	 */
	public static void testSubmitRunnableWithResult() throws InterruptedException, ExecutionException {
		Future<Person> resultFuture = executor.submit(new RunnableTask(), new Person("FirstName", "LastName"));
		/**
		 * get() : Waits for the computation to complete, and then retrieves its result (the given result upon
		 * successful completion).
		 */
		Person person = resultFuture.get();
		System.out.println("Result Obj:" + person);

	}

	/**
	 * T invokeAny(Collection<? extends Callable<T>> tasks): Executes the given tasks, returns the result of one of the
	 * completed (normal completion) tasks and cancels/interrupts the other incomplete tasks, if any.
	 */
	public static void testInvokeAny() throws InterruptedException, ExecutionException {
		Set<CallableTask> tasks = new HashSet<CallableTask>();
		for (int i = 0; i < 3; i++) {
			tasks.add(new CallableTask(i + 1));
		}

		Person resultObj = executor.invokeAny(tasks);
		System.out.println("Result Obj:" + resultObj);
	}

	/**
	 * T invokeAny(Collection<? extends Callable<T>> tasks): Executes the given tasks, returns the result of one of the
	 * completed (normal completion) tasks, before the given timeout elapses, and cancels/interrupts the other
	 * incomplete tasks, if any.
	 */
	public static void testInvokeAnyWithTimeout() throws InterruptedException, ExecutionException, TimeoutException {
		Set<CallableTask> tasks = new HashSet<CallableTask>();
		for (int i = 0; i < 3; i++) {
			tasks.add(new CallableTask(i + 1));
		}

		Person resultObj = executor.invokeAny(tasks, 6, TimeUnit.SECONDS);
		System.out.println("Result Obj:" + resultObj);
	}

	/**
	 * List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) : Executes the given tasks in a given order,
	 * blocked for completion of all the tasks, returning a list of Futures holding their status and results when all
	 * complete. Future.isDone is true for each element of the returned list.
	 */
	public static void testInvokeAll() throws InterruptedException, ExecutionException {
		Set<CallableTask> tasks = new HashSet<CallableTask>();
		for (int i = 0; i < 3; i++) {
			tasks.add(new CallableTask(i + 1));
		}
		List<Future<Person>> resultFutures = executor.invokeAll(tasks);
		for (Future<Person> future : resultFutures) {
			System.out.println("isCancelled:" + future.isCancelled());
			System.out.println("Result Obj:" + future.get());
		}
	}

	/**
	 * List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) : Executes the
	 * given tasks in a given order, blocked for completion or timeout, returning a list of Futures holding their status
	 * and results when all complete or the timeout expires. Future.isDone is true for each element of the returned
	 * list.
	 */
	public static void testInvokeAllWithTimeout() throws InterruptedException, ExecutionException {
		Set<CallableTask> tasks = new HashSet<CallableTask>();
		for (int i = 0; i < 3; i++) {
			tasks.add(new CallableTask(i + 1));
		}
		List<Future<Person>> resultFutures = executor.invokeAll(tasks, 6, TimeUnit.SECONDS);
		for (Future<Person> future : resultFutures) {
			System.out.println("isCancelled:" + future.isCancelled());
			System.out.println("Result Obj:" + future.get());
		}
	}
}

/**
 * Business Entity.
 */
class Person {
	String firstName;
	String lastName;

	public Person() {
	}

	public Person(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public final String getFirstName() {
		return firstName;
	}

	public final void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public final String getLastName() {
		return lastName;
	}

	public final void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Override
	public String toString() {
		return firstName + " " + lastName;
	}
}

/**
 * 
 * Runnable Task.
 */
class RunnableTask implements Runnable {

	long taskId = 0;

	public RunnableTask() {
	}

	public RunnableTask(long taskId) {
		this.taskId = taskId;
	}

	@Override
	public void run() {
		ConcurrentUtility.consumeTime(5, TimeUnit.SECONDS);
		System.out.println("Executed RunnableTask - " + taskId);
	}

}

/**
 * Callable Task.
 *
 */
class CallableTask implements Callable<Person> {

	long taskId = 0;

	public CallableTask() {
	}

	public CallableTask(long taskId) {
		this.taskId = taskId;
	}

	@Override
	public Person call() throws Exception {
		ConcurrentUtility.consumeTime(5, TimeUnit.SECONDS);
		System.out.println("Executed CallableTask - " + taskId);
		return new Person("F Name", "L Name-" + taskId);
	}

}