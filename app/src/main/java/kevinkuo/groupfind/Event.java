package kevinkuo.groupfind;

/**
 * Created by kevinkuo on 10/28/17.
 */

public class Event {

    private final String tag;
    private final int numPeople;

    public Event(final String tag, final int numPeople) {
        this.tag = tag;
        this.numPeople = numPeople;
    }

    public String getTag() {
        return tag;
    }

    public int getNumPeople() {
        return numPeople;
    }
}
