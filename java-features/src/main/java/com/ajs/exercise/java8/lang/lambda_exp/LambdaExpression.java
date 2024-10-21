package com.ajs.exercise.java8.lang.lambda_exp;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * <p>
 * <b>Vertical Problem:</b> The biggest pain point for anonymous classes is bulkiness; surrounding a piece of code with
 * a method, and a class.
 * </p>
 * <p>
 * <b>Lambda Expressions:</b> Lambda Expressions are anonymous methods addressing the "vertical problem" by replacing
 * the bulky anonymous inner classes with a light-weight mechanism. They can only appear in contexts that have target
 * types. The Lambda expressions will have context-dependent types. <br>
 * <br>
 * Lambda expressions allow us to define an anonymous method and treat it as an instance of a functional interface. <br>
 * Implements <em>code-as-data<em> or <em>behavior parameterization<em> pattern.<br>
 * Lambda expressions and method references add a lot of expressiveness to the Java language.<br>
 * </p>
 * <p>
 * 1) The general syntax consists of an argument list, the arrow token ->, and a body. e.g.:
 * <code>(String s) -> { System.out.println(s); }</code> <br>
 * 2) The body can either be a single expression, or a statement block. <br>
 * 3) In the expression form, the body is simply evaluated and returned. <br>
 * 4) In the block form, the body is evaluated like a method body. A return statement returns control to the caller of
 * the anonymous method; break and continue are illegal at the top level, but are of course permitted within loops; and
 * if the body produces a result, every control path must return something or throw an exception. <br>
 * </p>
 * <p>
 * <b>Target Typing:</b> The name of a functional interface is not part of the lambda expression syntax. Its type is
 * inferred from the surrounding context. <br>
 * <br>
 * The compiler is responsible for inferring the type of each lambda expression. It uses the type expected in the
 * context in which the expression appears; this type is called the target type. A lambda expression can only appear in
 * a context whose target type is a functional interface. <br>
 * <br>
 * Of course, no lambda expression will be compatible with every possible target type. The compiler checks that the
 * types used by the lambda expression are consistent with the target type's method signature. That is, a lambda
 * expression can be assigned to a target type T if all of the following conditions hold:<br>
 * 1) T is a functional interface type<br>
 * 2) The lambda expression has the same number of parameters as T's method, and those parameters' types are the same <br>
 * 3) Each expression returned by the lambda body is compatible with T's method's return type <br>
 * 4) Each exception thrown by the lambda body is allowed by T's method's throws clause<br>
 * </p>
 * <p>
 * <b>Lexical Scoping:</b> Lambda Expressions are lexically scoped, meaning names in the body are interpreted just as
 * they are in the enclosing environment (with the addition of new names for the lambda expression's formal parameters).
 * As a natural extension, the <code>this</code> keyword and references to its members have the same meaning as they
 * would immediately outside the lambda expression.
 * </p>
 *
 * @author DGOVINDAN
 */
public class LambdaExpression {
    public static void print(final FnIface fnIface) {
        fnIface.sam();
    }

    public static void print(final FnIfaceComplex fnIface) {
        try {
            System.out.println(fnIface.sam(null));
        } catch (final FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    static FnIface result1() {
        return () -> System.out.println("Hello");
    }

    static FnIfaceComplex result2() {
        return arg -> "Hello";
    }

    public static void main(final String[] args) {
        // Expression Form
        final FnIface f1 = () -> System.out.println("Hello");
        final FnIfaceComplex fc1 = (arg) -> "Hello";
        print(() -> System.out.println("Hello"));
        print(arg -> "Hello");

        // Block Form
        final FnIface f2 = () -> {
            System.out.println("Hello");
        };

        final FnIfaceComplex fc2 = (arg) -> {
            if (arg == null) {
                throw new FileNotFoundException("Not Found.");
            }
            return "Hello";
        };
        print(() -> {
            System.out.println("Hello");
        });

        final Callable<Boolean> callable = () -> {
            System.out.println("Action Done");
            return true;
        };

        final List<Callable<Integer>> callables = new ArrayList<>();
        callables.add(() -> 1);
        callables.add(() -> 2);
        callables.add(() -> 3);

    }
}