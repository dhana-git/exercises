package com.dhana.exercise.java8.lang.functional_interface;

/**
 * Non functional interface since default methods have an implementation, they are not abstract.
 *
 * @author DGOVINDAN
 */
public interface NonFuncIfaceHavingDefaultMeth {
    public default void nonAbstractMethod() {
    }
}
