package com.ajs.exercise.java8.lang.functional_interface;

public class FunctionalIfaceUsage {

    void demoFunctionalMethodUsageOldForm() {
        methAcceptsCallback(new FunctionalIface() {
            @Override
            public String singleAbstractMethod(final String arg) {
                // do some action here.
                return arg + " Funcational Value";
            }
        });
    }

    void demoFunctionalMethodUsageLatestForm() {
        methAcceptsCallback(arg -> arg + " Funcational Value");
    }

    void methAcceptsCallback(final FunctionalIface funcIface) {
        System.out.println(funcIface.singleAbstractMethod("Caller Value"));
    }

    public static void main(final String[] args) {
        new FunctionalIfaceUsage().demoFunctionalMethodUsageLatestForm();
    }
}
