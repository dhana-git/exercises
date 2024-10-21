package com.ajs.exercise.java8.lang.interface_method;

/**
 * <p>
 * <b>Static Interface Methods:</b><br>
 * Java 8 introduces the ability to place static methods in interfaces. This allows helper methods that are specific to
 * an interface to live with the interface, rather than in a side class (which is often named for the plural of the
 * interface). For example, Collection-Collections, Comparator-Comparators.
 * 
 * @author DGOVINDAN
 */
public interface StaticInterfaceMethod {
    static void staticMethod() {
        System.out.println("This is a static method at interface compilation unit.");
    }
}
