package net.eastern.FlyAway.util;

import jdk.jshell.spi.ExecutionControl;

public class ComedicallyLargeErrorNameException extends ExecutionControl.NotImplementedException {

    public ComedicallyLargeErrorNameException(String message) {
        super(message);
        System.err.println("you fuck up");
    }
}
