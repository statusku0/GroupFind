package kevinkuo.groupfind;

import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kevinkuo on 10/28/17.
 */

public class EventUtilities {

    public static Intent createIntent(final List<Event> eventList,
                                final Context start,
                                final Class endClass) {

        final ArrayList<String> tags = new ArrayList<>();
        final ArrayList<Integer> numPeoples = new ArrayList<>();
        final ArrayList<String> locations = new ArrayList<>();

        for (final Event event : eventList) {
            tags.add(event.getTag());
            numPeoples.add(event.getNumPeople());
            locations.add(event.getLocation());
        }

        final Intent intent = new Intent(start, endClass);
        intent.putStringArrayListExtra(Constants.EVENT_TAGS, tags);
        intent.putIntegerArrayListExtra(Constants.EVENT_NUM_PEOPLE, numPeoples);
        intent.putStringArrayListExtra(Constants.EVENT_LOCATION, locations);

        return intent;
    }

    public static List<Event> getEventsFromIntent(final Intent intent) {
        final List<String> tags = intent.getStringArrayListExtra(Constants.EVENT_TAGS);
        final List<Integer> numPeoples =
                intent.getIntegerArrayListExtra(Constants.EVENT_NUM_PEOPLE);
        final List<String> locations = intent.getStringArrayListExtra(Constants.EVENT_LOCATION);

        final List<Event> eventList = new ArrayList<>();
        for (int k1 = 0; k1 < tags.size(); k1++) {
            eventList.add(new Event(tags.get(k1), numPeoples.get(k1), locations.get(k1)));
        }

        return eventList;
    }
}
