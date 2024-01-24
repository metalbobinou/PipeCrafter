package Models;

import java.util.ArrayList;
import java.util.List;
import Models.Argument.Type;

/** Internal representation of command related data */
public class Command {

    // #region Nested Types

    public enum State {
        ALREADY_RUN,
        SKIPPED,
        RUNNING,
        NEXT_TO_RUN,
        TO_RUN;
    }

    // #endregion

    // #region Attributes

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

    /** List of all arguments referring to this command */
    private List<Argument> referringArgumentList;

    /** Exit code returned after execution of the command */
    private Integer exitCode;

    // #endregion

    // #region Constructors

    public Command() {
        argumentList = new ArrayList<>();
        referringArgumentList = new ArrayList<>();
        exitCode = null;
    }

    // #endregion

    // #region Methods

    /**
     * Update the state of a command and make the UI match the change
     * 
     * @param state the new state for the command
     */
    public void updateState(State state) {
        State old = this.state;
        this.state = state;

        // If the sate went from SKIPPED to something else or vice versa,
        // refresh the referring arguments' views
        if (old == State.SKIPPED || state == State.SKIPPED) {
            refreshReferringArgs();
        }
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
        refreshReferringArgs();
    }

    /** Refresh the view of all args referring to the command */
    public void refreshReferringArgs() {
        for (Argument argument : referringArgumentList) {
            argument.getArgumentView().refresh();
        }
    }

    /** Make all referring arguments invalid with a null value and refresh view */
    public void makeReferringArgsInvalid() {
        // No need to increase i as by setting the argument as invalid it will
        // be removed from the reference list
        for (int i = 0; i < referringArgumentList.size();) {
            Argument argument = referringArgumentList.get(i);
            argument.setArgument(Type.INVALID, null);
            argument.getArgumentView().refresh();
            referringArgumentList.remove(i);
        }
    }

    /**
     * State if a command is in a state where it can be edtited
     * 
     * @return true if it can be, false otherwise
     */
    public boolean canEdit() {
        return state == State.TO_RUN || state == State.NEXT_TO_RUN;
    }

    // #endregion

    // #region Getters and Setters

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

    public int getIndex() {
        return position - 1;
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

    public List<Argument> getReferringArgumentList() {
        return referringArgumentList;
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

    public void setPosition(int position) {
        this.position = position;
    }

    public void setState(State state) {
        this.state = state;
    }

    // #endregion
}
