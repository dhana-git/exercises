package com.ajs.exercise.java8.lang.interface_method;

/**
 * <p>
 * <b>Default (Virtual/Fallback) Interface Methods:</b> <br>
 * Default methods enable new functionality to be added to the interfaces of libraries and ensure binary compatibility
 * with code written for older versions of those interfaces.<br>
 * <br>
 * Default methods provide a more object-oriented way to add concrete behavior to an interface. And this
 * behavior/implementation is inherited by classes that do not override it.<br>
 * <br>
 * When one interface extends another, it can add a default to an inherited abstract method, provide a new default for
 * an inherited default method, or reabstract a default method by redeclaring the method as abstract.
 * </p>
 * <p>
 * <b>Inheritance of default methods:</b><br>
 * Default methods are inherited just like other methods; in most cases, the behavior is just as one would expect.
 * However, when a class's or interface's supertypes provide multiple methods with the same signature, the inheritance
 * rules attempt to resolve the conflict. Two basic principles drive these rules:
 * <ul>
 * <li>Class method declarations are preferred to interface defaults. This is true whether the class method is concrete
 * or abstract. (Hence the default keyword: default methods are a fallback if the class hierarchy doesn't say anything.)
 * <li>Methods that are already overridden by other candidates are ignored. This circumstance can arise when supertypes
 * share a common ancestor.
 * </ul>
 * </p>
 */
public class DefaultInterfaceMethod {
    public static void main(String... args) {
        new ClassInheritsDefaultMethIface1().defaultMeth();
    }
}

interface DefaultMethIface1 {
    default void defaultMeth() {
        System.out.println("This is a default interface method @ DefaultMethIface1.");
    }
}

interface DefaultMethIface2 {
    default void defaultMeth() {
        System.out.println("This is a default interface method @ DefaultMethIface2.");
    }
}

interface DefaultMethOverride extends DefaultMethIface1 {
    // New default implementation for an inherited default method.
    @Override
    default void defaultMeth() {
        System.out.println("This is a default interface method overridden @ DefaultToAbsMethConversion.");
    }
}

interface DefaultToAbsMethConversion extends DefaultMethIface1 {
    // Re-abstract a default method by redeclaring the method as abstract.
    void defaultMeth();
}

interface AbsToDefaultMethConversion extends DefaultToAbsMethConversion {
    // Add a default implementation to an inherited abstract method.
    default void defaultMeth() {
        System.out.println("This is a default interface method @ AbsToDefaultMethConversion.");
    }
}

interface ImplicitDefaultMethInheritance extends DefaultMethIface1 {
}

interface ImplicitDefaultMethInheritanceFromDirectSuperType extends DefaultMethIface1, DefaultToAbsMethConversion,
        ImplicitDefaultMethInheritance {
    // Inherits or overrides default interface method from/at direct super type
    @Override
    public default void defaultMeth() {

    }
}

interface PreferredDefaultMeth extends ImplicitDefaultMethInheritanceFromDirectSuperType,
        ImplicitDefaultMethInheritance {
    @Override
    public default void defaultMeth() {
        /*-
         * Explicitly override the supertype methods; picking the preferred default, and declaring a body that invokes
         * the preferred default. 
         * 
         * Note: The name preceding super must refer to a direct superinterface that defines or
         * inherits a default for the invoked method.
         */
        ImplicitDefaultMethInheritanceFromDirectSuperType.super.defaultMeth();
    }
}

// Duplicate default methods inheritance

/*
 * Error: Duplicate default methods named defaultMeth with the parameters () and () are inherited from the types
 * DefaultIface2 and DefaultIface1
 */
/*-interface DefaultIfaceDuplicateInheritance extends DefaultMethIface1, DefaultMethIface2 {

 }*/

class ClassInheritsDefaultMethIface1 implements DefaultMethIface1 {

}

/*
 * Error: Duplicate default methods named defaultMeth with the parameters () and () are inherited from the types
 * DefaultIface2 and DefaultIface1
 */
/*-class DefaultIfaceImplicitDuplicateInheritance extends ClassInheritsDefaultMethIface1 implements DefaultMethIface2 {

 }*/

