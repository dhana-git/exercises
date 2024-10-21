package com.ajs.exercise.java8.lib.internal_iteration;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;

/**
 * <p>
 * <b>Support for Internal Iteration</b><br>
 * Before Java 8, the Collection framework relies on the concept of <em>External Iteration</em>, where an aggregate
 * provides an iterator and clients uses this to step through the elements of an aggregate. i.e. the client controls the
 * iteration, by calling next() or previous() methods. <br>
 * <br>
 * The alternative to <em>external iteration</em> is <em>internal iteration</em>, where instead of controlling the
 * iteration, the client delegates that to the library and passes a code as data (behavior/closure) to execute at
 * various points in the computation.<br>
 * <ul>
 * <li>The control of the operation has been handed off from the client code to the library code
 * <li>Client code can be clearer.
 * </ul>
 * </p>
 * 
 * @author DGOVINDAN
 */
public class InternalIteration {
    public static void main(String[] args) {
        List<Technology> techs = buildTechs();
        Consumer<Technology> action = (arg) -> System.out.println(arg.getName());
        techs.forEach(action); // Internal iteration
    }

    static List<Technology> buildTechs() {
        List<Technology> techs = new ArrayList<>();
        BiFunction<String, String, Technology> techGenerator = Technology::new;
        techs.add(techGenerator.apply("Java", "OOP"));
        techs.add(techGenerator.apply("C", "OOP"));
        techs.add(techGenerator.apply("JavaScript", "Functional Programing"));
        return techs;
    }
}

class Technology {
    String name;
    String type;

    public Technology(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public final String getName() {
        return name;
    }

    public final void setName(String name) {
        this.name = name;
    }

    public final String getType() {
        return type;
    }

    public final void setType(String type) {
        this.type = type;
    }

}
