package Utils;

/** Page class to interact with the main page */
public class MainPage extends SuperPage {
    // #region Constructor

    public MainPage(TestFXBase drive) {
        super(drive);
    }

    // #endregion

    // #region Methods

    /** Add a command */
    public void addCmd() {
        drive.clickOn(Ids.ADD_CMD_BUTTON_ID);
    }

    // #endregion
}
