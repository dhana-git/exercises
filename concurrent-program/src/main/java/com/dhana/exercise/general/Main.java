package com.dhana.exercise.general;

public class Main {

	public static void main(String[] args) {

		long v = 1L << 63 - 3;
		System.out.println(v + "|" + Math.round(v));
	}

}
