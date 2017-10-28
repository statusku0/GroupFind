package kevinkuo.groupfind;

/**
 * Created by kevinkuo on 10/28/17.
 */

public class Event {

    private final String tag;
    private final int numPeople;
    private final String location;

    public Event(final String tag, final int numPeople, final String location) {
        this.tag = tag;
        this.numPeople = numPeople;
        this.location = location;
    }

    public String getTag() {
        return tag;
    }

    public int getNumPeople() {
        return numPeople;
    }

    public String getLocation() {
        return location;
    }

}
