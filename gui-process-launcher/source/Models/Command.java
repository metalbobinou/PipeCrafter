package Models;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/** Internal representation of command related data */
public class Command {

    // region Nested Types

    public enum State {
        ALREADY_RUN,
        NEXT_TO_RUN,
        TO_RUN;
    }

    // endregion

    // region Attributes

    /** A unique id */
    public final UUID id = UUID.randomUUID();

    /** Position of the command in the execution pipeline */
    private int position;

    /** Execution state of the command */
    private State state;

    /** List of all arguments for this command */
    private List<Argument> argumentList;

    /** Exit code returned after execution of the command */
    private Integer exitCode;

    // endregion

    // region Constructors

    public Command(int position, State state) {
        this.position = position;
        this.state = state;
        argumentList = new ArrayList<>();
        exitCode = null;
    }

    // endregion

    // region Getters and Setters

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        if (position < 1) {
            throw new IllegalArgumentException();
        }
        this.position = position;
    }

    public List<Argument> getArgumentList() {
        return argumentList;
    }

    public Integer getExitCode() {
        return exitCode;
    }

    public void setExitCode(Integer exitCode) {
        this.exitCode = exitCode;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    // endregion
}
