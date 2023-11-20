package Models;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

/** Internal representation of process related data */
public class Process {

    // region Attributes

    /** A unique id */
    public final UUID id = UUID.randomUUID();

    /** Position of the process in the execution pipeline */
    private int position;

    /** List of all arguments for this process */
    private List<Argument> argumentList;

    /** Exit code returned after execution of the process */
    private Integer exitCode;

    // endregion

    // region Constructors

    public Process(int position) {
        this.position = position;
        argumentList = Collections.emptyList();
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
