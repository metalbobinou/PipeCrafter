package Models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/** Internal representation of command related data */
public class Command {

    // region Attributes

    /** A unique id */
    public final UUID id = UUID.randomUUID();

    /** Position of the command in the execution pipeline */
    private int position;

    /** List of all arguments for this command */
    private List<Argument> argumentList;

    /** Exit code returned after execution of the command */
    private Integer exitCode;

    // endregion

    // region Constructors

    public Command(int position) {
        this.position = position;
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

    // endregion
}
