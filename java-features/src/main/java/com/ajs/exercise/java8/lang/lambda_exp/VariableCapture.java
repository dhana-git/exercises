package com.ajs.exercise.java8.lang.lambda_exp;

/**
 * <p>
 * <b>Variable Capture:</b> A local variable referenced in an inner class/lambda expression is called as
 * "captured variable". Accessing local variables (not declared as final) from inner class is quite restrictive in Java
 * 7, but in Java 8, this has been relaxed (syntactically) for both lambda expressions and inner classes; and also
 * allowing the capture of final local variables. While we relax the syntactic restrictions on captured values, we still
 * prohibit capture of mutable local variables. <br>
 * <br>
 * Informally, a local variable is effectively final if its initial value is never changed. <br>
 * References to <code>this</code>, including implicit references through unqualified field references or method
 * invocations, are essentially references to a <code>final</code> local variable. Lambda bodies that contain such
 * references capture the appropriate instance of this. In other cases, no reference to this is retained by the object.
 * <br>
 * This has a beneficial implication for memory management: while inner class instances always hold a strong reference
 * to their enclosing instance, lambdas that do not capture members from the enclosing instance do not hold a reference
 * to it. This characteristic of inner class instances can often be a source of memory leaks.
 * </p>
 *
 * @author DGOVINDAN
 */
public class VariableCapture {

    String x;

    public  void main(final String[] args) {

        // Variable Capture
        String localVar = "Hello";
        C c = new C();
        LambdaExpression.print((arg) -> localVar + " - Varibale Capture"+c);
        // Error: Local variable localVar defined in an enclosing scope must be final or effectively final
        // localVar = localVar +"X";
    }

    class C {
        void m() {
            System.out.println(x);
        }
    }
}
