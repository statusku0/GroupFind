package kevinkuo.groupfind;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

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
        final ArrayList<String> descriptions = new ArrayList<>();

        for (final Event event : eventList) {
            tags.add(event.getTag());
            numPeoples.add(event.getNumPeople());
            locations.add(event.getLocation());
            descriptions.add(event.getDescription());
        }

        final Intent intent = new Intent(start, endClass);
        intent.putStringArrayListExtra(Constants.EVENT_TAGS, tags);
        intent.putIntegerArrayListExtra(Constants.EVENT_NUM_PEOPLE, numPeoples);
        intent.putStringArrayListExtra(Constants.EVENT_LOCATION, locations);
        intent.putStringArrayListExtra(Constants.EVENT_DESCRIPTION, descriptions);

        return intent;
    }

    public static Bundle fillBundle(final List<Event> eventList, final Bundle bundle) {

        final ArrayList<String> tags = new ArrayList<>();
        final ArrayList<Integer> numPeoples = new ArrayList<>();
        final ArrayList<String> locations = new ArrayList<>();
        final ArrayList<String> descriptions = new ArrayList<>();

        for (final Event event : eventList) {
            tags.add(event.getTag());
            numPeoples.add(event.getNumPeople());
            locations.add(event.getLocation());
            descriptions.add(event.getDescription());
        }

        bundle.putStringArrayList(Constants.EVENT_TAGS, tags);
        bundle.putIntegerArrayList(Constants.EVENT_NUM_PEOPLE, numPeoples);
        bundle.putStringArrayList(Constants.EVENT_LOCATION, locations);
        bundle.putStringArrayList(Constants.EVENT_DESCRIPTION, descriptions);

        return bundle;
    }

    public static List<Event> getEventsFromIntent(final Intent intent) {
        final List<String> tags = intent.getStringArrayListExtra(Constants.EVENT_TAGS);
        final List<Integer> numPeoples =
                intent.getIntegerArrayListExtra(Constants.EVENT_NUM_PEOPLE);
        final List<String> locations = intent.getStringArrayListExtra(Constants.EVENT_LOCATION);
        final List<String> descriptions = intent.getStringArrayListExtra(
                Constants.EVENT_DESCRIPTION);

        final List<Event> eventList = new ArrayList<>();
        for (int k1 = 0; k1 < tags.size(); k1++) {
            eventList.add(new Event(
                    tags.get(k1), numPeoples.get(k1), locations.get(k1), descriptions.get(k1), ""));
        }

        return eventList;
    }

    public static List<Event> getEventsFromBundle(final Bundle bundle) {
        final List<String> tags = bundle.getStringArrayList(Constants.EVENT_TAGS);
        final List<Integer> numPeoples =
                bundle.getIntegerArrayList(Constants.EVENT_NUM_PEOPLE);
        final List<String> locations = bundle.getStringArrayList(Constants.EVENT_LOCATION);
        final List<String> descriptions = bundle.getStringArrayList(
                Constants.EVENT_DESCRIPTION);

        final List<Event> eventList = new ArrayList<>();
        for (int k1 = 0; k1 < tags.size(); k1++) {
            eventList.add(new Event(
                    tags.get(k1), numPeoples.get(k1), locations.get(k1), descriptions.get(k1), ""));
        }

        return eventList;
    }
}
