package Utils;

/** A tuple class */
public class Tuple<A, B> {

    // #region Attributes

    /** The first element of the tuple */
    public A first;

    /** The second element of the tuple */
    public B second;

    // #endregion

    // #region Constructor

    public Tuple(A first, B second) {
        this.first = first;
        this.second = second;
    }

    // #endregion

}
