package com.dhana.exercise.java8.lang.method_reference;

import java.util.function.Function;
import java.util.function.Supplier;

import com.dhana.exercise.java8.lang.functional_interface.FunctionalIface;
import com.dhana.exercise.java8.lang.lambda_exp.LambdaExpression;

/**
 * <p>
 * <b>Method References:</b> Lambda expressions allow us to define an anonymous method and treat it as an instance of a
 * functional interface. It is often desirable to do the same with an existing method. <br>
 * <br>
 * Method references are expressions which have the same treatment as lambda expressions (i.e., they require a target
 * type and encode functional interface instances), but instead of providing a method body, they refer an existing
 * method by name.
 * </p>
 * <p>
 * <b>Kinds of method references:</b>
 * <ul>
 * <li>A static method (ClassName::methName)
 * <li>An instance method of a particular object (instanceRef::methName)
 * <li>A super method of a particular object (super::methName)
 * <li>An instance method of an arbitrary object of a particular type (ClassName::methName)
 * <li>A class constructor reference (ClassName::new)
 * <li>An array constructor reference (TypeName[]::new)
 * </ul>
 * </p>
 *
 * @author DGOVINDAN
 */
public class MethodReference {

    public static void main(final String[] args) {

        // A static method (ClassName::methName)
        LambdaExpression.print(Base::staticMeth);

        // An instance method of a particular object (instanceRef::methName)
        final Child child = new Child();
        LambdaExpression.print(child::parentInstanceMeth);

        // An instance method of an arbitrary object of a particular type (ClassName::methName)
        final Function<String, String> fun = String::toLowerCase;
        System.out.println(fun.apply("lOwer-CaSE sTrIng"));

        final FunctionalIface fun2 = String::toLowerCase;
        System.out.println(fun2.singleAbstractMethod("lOwer-CaSE sTrIng"));

        /*-
         * For a reference to an instance method of an arbitrary object, the type to which the method belongs precedes
         * the delimiter, and the invocation's receiver is the first parameter of the functional interface method:
         *
         * Function<String, String> upperfier = String::toUpperCase;
         *
         * Here, the implicit lambda expression has one parameter, the string to be converted to upper case, which becomes the receiver of the invocation of the
         * toUpperCase() method.
         */

        // A class constructor reference (ClassName::new)
        final Supplier<Child> fun3 = Child::new;
        System.out.println(fun3.get());

        final Function<String, Child> fun4 = Child::new;
        System.out.println(fun4.apply("lOwer-CaSE sTrIng"));

        // An array constructor reference (TypeName[]::new)
        final Function<Integer, Child[]> fun5 = Child[]::new;
        System.out.println(fun5.apply(5).length);
    }
}

abstract class Base {
    static void staticMeth() {
        System.out.println("This is a static method.");
    }

    abstract void absMeth();

    void parentInstanceMeth() {
        System.out.println("This is an instance method of parent class.");
    }
}

class Child extends Base {
    public Child() {
        System.out.println("This is a constructor of child class.");
    }

    public Child(final String arg) {
        System.out.println("This is a parameterized constructor of child class.");
    }

    @Override
    void absMeth() {
        System.out.println("This is an abstract method overridden in Child.");
    }

    @Override
    void parentInstanceMeth() {
        System.out
        .println("This is an instance method of parent class, but overridden at Child. Invoking a method at parent class.");
        // A super method of a particular object (super::methName)
        LambdaExpression.print(super::parentInstanceMeth);

    }

}
