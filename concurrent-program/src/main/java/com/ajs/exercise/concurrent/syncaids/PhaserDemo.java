package com.ajs.exercise.concurrent.syncaids;

import java.util.concurrent.Phaser;

public class PhaserDemo implements Runnable {

	private Phaser phaser;

	public PhaserDemo(Phaser phaser) {
		this.phaser = phaser;
	}

	@Override
	public void run() {
		System.out.println("ArrivedParties:" + phaser.getArrivedParties());
		System.out.println("RegisteredParties:" + phaser.getRegisteredParties());
		System.out.println("UnarrivedParties:" + phaser.getUnarrivedParties());

		// Barrier point
		phaser.arriveAndAwaitAdvance();

		// Do the rest...
	}

}
