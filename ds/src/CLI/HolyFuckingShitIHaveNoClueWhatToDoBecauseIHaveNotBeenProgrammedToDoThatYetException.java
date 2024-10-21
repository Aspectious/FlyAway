package net.eastern.FlyAway.CLI;

import jdk.jshell.spi.ExecutionControl;

public class HolyFuckingShitIHaveNoClueWhatToDoBecauseIHaveNotBeenProgrammedToDoThatYetException extends ExecutionControl.NotImplementedException {

    public HolyFuckingShitIHaveNoClueWhatToDoBecauseIHaveNotBeenProgrammedToDoThatYetException(String message) {
        super(message);
        System.err.println("you fuck up");
    }
}
