package com.ajs.exercise.java8.lang.functional_interface;

@FunctionalInterface
public interface FunctionalIfaceHavingDefaultOverriddenStaticMeth {
    // Single Abstract Method
    void sam();

    // Static method
    static void statucMeth() {

    }

    // Overriding a method provided by Object class.
    @Override
    public String toString();

    // Default method
    default void defaultMeth() {

    }
}
