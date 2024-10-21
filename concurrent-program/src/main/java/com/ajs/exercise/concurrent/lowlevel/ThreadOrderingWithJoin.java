package com.ajs.exercise.concurrent.lowlevel;

/**
 * We can fix the thread (sequential) execution order by using Thread.join(). The join() method blocks the current
 * thread (main/daemon), which invokes join(), until the specified thread (a target thread object on which the join()
 * method was invoked) dies or timeout elapses.
 * 
 * Upon invocation of join()/join(timeOut) method the currently executing thread (main/daemon), releases the ownership
 * on monitor and enters Waiting/Timed Waiting state.
 *
 */
public class ThreadOrderingWithJoin {

	private static final long THREAD_SLEEP_TIME_MILLIS = 3000;

	public static void main(String[] args) {
		testThreadOrdering(false);
	}

	private static void testThreadOrdering(boolean desc) {
		Thread[] threadArr = createThreads(3);
		System.out.println("Starting threads in " + (desc ? "Descending" : "Ascending") + " Order");
		int startIndex = desc ? threadArr.length - 1 : 0;
		for (; desc ? startIndex >= 0 : startIndex < threadArr.length;) {
			startSequentially(threadArr[startIndex]);
			startIndex = desc ? startIndex - 1 : startIndex + 1;
		}
		System.out.println("Daemon Thread Completed Execution and waiting for user threads to die...");
	}

	private static void startSequentially(Thread thread) {
		thread.start();
		try {
			thread.join(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private static Thread[] createThreads(int count) {
		Thread[] threadArr = new Thread[count];
		for (int i = 0; i < threadArr.length; i++) {
			threadArr[i] = new Thread(new Runnable() {
				@Override
				public void run() {
					String threadName = Thread.currentThread().getName();
					System.out.println(threadName + " Started");
					try {
						Thread.sleep(THREAD_SLEEP_TIME_MILLIS);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println(threadName + " Ended");
				}
			}, "Thread" + (i + 1));
		}
		return threadArr;
	}
}
