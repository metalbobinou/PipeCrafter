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
        RUNNING,
        TO_RUN;
    }

    // endregion

    // region Attributes

    /** A unique id */
    public final UUID id = UUID.randomUUID();

    /** Name used by the user for this command */
    private String name = "";

    /** View controller used by the representation of this object */
    private Views.Command cmdView;

    /** Position of the command in the execution pipeline */
    private int position;

    /** Execution state of the command */
    private State state;

    /** The command entered by the user without the arguments */
    private String cmd = "";

    /** List of all arguments for this command */
    private List<Argument> argumentList;

    /** Exit code returned after execution of the command */
    private Integer exitCode;

    // endregion

    // region Constructors

    public Command(int position, State state, Views.Command cmdView) {
        this.position = position;
        this.state = state;
        this.cmdView = cmdView;
        argumentList = new ArrayList<>();
        exitCode = null;
    }

    // endregion

    // region Methods

    /**
     * Update the state of a command and make the UI match the change
     * 
     * @param state the new state for the command
     */
    public void updateState(State state) {
        this.state = state;
        cmdView.updateState();
    }

    /**
     * Update the position of a command and make the UI match the change
     * 
     * @param position the new position for the command
     */
    public void updatePosition(int position) {
        if (position < 1) {
            throw new IllegalArgumentException();
        }
        this.position = position;
        cmdView.updatePosition();
    }

    /**
     * State if a command is in a state where it can be edtited
     * 
     * @return true if it can be, false otherwise
     */
    public boolean canEdit() {
        return state != State.ALREADY_RUN && state != State.RUNNING;
    }

    // endregion

    // region Getters and Setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Views.Command getCmdView() {
        return cmdView;
    }

    public void setCmdView(Views.Command cmdView) {
        this.cmdView = cmdView;
    }

    public int getPosition() {
        return position;
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
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

    // endregion
}
