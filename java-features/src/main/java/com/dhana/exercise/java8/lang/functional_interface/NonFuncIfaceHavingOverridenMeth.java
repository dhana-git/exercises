package com.dhana.exercise.java8.lang.functional_interface;

/**
 * Non functional interface since, if an interface declares an abstract method overriding one of the public methods of
 * java.lang.Object, that also does not count toward the interface's abstract method count since any implementation of
 * the interface will have an implementation from java.lang.Object or elsewhere.
 *
 * @author DGOVINDAN
 */
// @FunctionalInterface
public interface NonFuncIfaceHavingOverridenMeth {
    @Override
    String toString();
}
