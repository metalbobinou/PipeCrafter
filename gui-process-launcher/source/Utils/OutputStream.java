package Utils;

/**
 * Enum representing the stream used and providing a string representing the
 * corresponding extension
 */
public enum OutputStream {

    OUT(".out"),
    ERR(".err");

    private final String associatedString;

    OutputStream(String associatedString) {
        this.associatedString = associatedString;
    }

    public String getExtension() {
        return associatedString;
    }
}
