package Utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/** Class holding all details for arguments of type output */
public class OutputParameters {
    // region Nested Types

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

    /** The formats for an argument of type output */
    public enum Format {
        PATH(" (path)"),
        CONTENT(" (content)");

        private final String associatedString;

        Format(String associatedString) {
            this.associatedString = associatedString;
        }

        public String getString() {
            return associatedString;
        }
    }

    // endregion

    // region Attributes

    /** Step to use */
    private int step;

    /** The stream to use */
    private OutputStream stream;

    /** Format of the argument */
    private Format format;

    // endregion

    // region Constructors

    public OutputParameters(int step, OutputStream stream, Format format) {
        this.step = step;
        this.stream = stream;
        this.format = format;
    }

    // endregion

    // region Methods

    @Override
    public String toString() {
        String filePath = Utils.getPathForOutputOfStep(step, stream);
        switch (format) {
            case CONTENT:
                try {
                    return Files.readString(Paths.get(filePath));

                } catch (IOException e) {
                    Alerts.getFailedReadingOutputFileAlert().showAndWait();
                    return "Error reading ourput file";
                }
            case PATH:
                return filePath;
            default:
                return "<error>";
        }
    }

    // endregion

    // region Getters and Setters

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public OutputStream getStream() {
        return stream;
    }

    public void setStream(OutputStream stream) {
        this.stream = stream;
    }

    public Format getFormat() {
        return format;
    }

    public void setFormat(Format format) {
        this.format = format;
    }

    // endregion
}
