package kevinkuo.groupfind;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by kevinkuo on 10/28/17.
 */

public class DbAction {

    static final List<Event> eventList = new ArrayList<>();

    public static void addEvent(final Event event) {
        // testing
        eventList.add(event);
    }

    public static List<Event> getEvents(final String tag) {
        // testing
        return eventList;
    }
}
