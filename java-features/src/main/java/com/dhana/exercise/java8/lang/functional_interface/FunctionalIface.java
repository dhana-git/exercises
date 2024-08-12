package com.dhana.exercise.java8.lang.functional_interface;

import java.util.Comparator;
import java.util.concurrent.Callable;

/**
 * A functional interface (callback interface, or closure) is an interface having exactly one abstract method. Usage:
 * Creating an anonymous inner class with an interface having exactly one abstract method. E.g: {@link Runnable},
 * {@link Callable}, {@link Comparable} , {@link Comparator}. <br>
 * <br>
 * Nothing special needs to be done to declare an interface as functional, <br>
 * <br>
 * By <b>structural type system</b>, the compiler identifies it as such based on its structure. <br>
 * This identification process is a little more than just counting method declarations; an interface might redundantly
 * declare a method that is automatically provided by the class Object, such as <i>toString()</i>, or might declare
 * <i>static</i> or <i>default methods</i>, none of which count against the one-method limit. <br>
 * <br>
 * By <b>nominal type system</b>, API authors may additionally capture the design intent that an interface be functional
 * (as opposed to accidentally having only one method) with the @{@link FunctionalInterface} annotation, in which case
 * the compiler will validate that the interface meets the structural requirements to be a functional interface.
 *
 * @author DGOVINDAN
 */
@FunctionalInterface
public interface FunctionalIface {
    String singleAbstractMethod(String arg);
}
