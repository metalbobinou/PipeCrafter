package Utils;

/** Parent class for all page classes */
public abstract class SuperPage {

    // #region Attributes

    private final TestFXBase drive;

    // #endregion

    // #region Constructor

    public SuperPage(TestFXBase drive) {
        this.drive = drive;
    }

    // #endregion
}
