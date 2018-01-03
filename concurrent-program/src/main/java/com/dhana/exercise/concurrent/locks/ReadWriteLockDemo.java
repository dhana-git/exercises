package com.dhana.exercise.concurrent.locks;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.dhana.exercise.concurrent.highlevel.ConcurrentUtility;

public class ReadWriteLockDemo {

	private volatile Map<String, Object> cache = new HashMap<String, Object>();
	private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
	private static final ReadWriteLockDemo readWriteLockDemo = new ReadWriteLockDemo();

	private ReadWriteLockDemo() {

	}

	public static ReadWriteLockDemo getInstance() {
		return readWriteLockDemo;
	}

	public Object getItem(String cacheKey) {
		readWriteLock.readLock().lock();
		Object cachedValue;
		try {
			cachedValue = cache.get(cacheKey);
		} finally {
			readWriteLock.readLock().unlock();
		}
		return cachedValue;
	}

	public Object addItem(String cacheKey, Object cacheItem) {
		readWriteLock.writeLock().lock();
		Object oldValue;
		try {
			ConcurrentUtility.consumeTime(2, TimeUnit.SECONDS);
			oldValue = cache.put(cacheKey, cacheItem);
		} finally {
			readWriteLock.writeLock().unlock();
		}
		return oldValue;
	}

	public Object removeItem(String cacheKey) {
		readWriteLock.writeLock().lock();
		Object oldValue;
		try {
			ConcurrentUtility.consumeTime(2, TimeUnit.SECONDS);
			oldValue = cache.remove(cacheKey);
		} finally {
			readWriteLock.writeLock().unlock();
		}
		return oldValue;
	}

}
