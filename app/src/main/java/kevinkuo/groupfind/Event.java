package kevinkuo.groupfind;

/**
 * Created by kevinkuo on 10/28/17.
 */

public class Event {

    private String tag;
    private int numPeople;
    private String location;
    private String description;
    private String eventId;

    public Event() {}

    public Event(final String tag, final int numPeople,
                 final String location, final String description) {
        this.tag = tag;
        this.numPeople = numPeople;
        this.location = location;
        this.description = description;
        this.eventId = tag + System.currentTimeMillis();
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

    public String getEventId() {
        return eventId;
    }

    public String getDescription() {
        return description;
    }

}
