package kevinkuo.groupfind;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ShowEventsActivity extends AppCompatActivity {

    public ListView eventList;
    public List<Event> eventsFromIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_events);

        eventsFromIntent = EventUtilities.getEventsFromIntent(getIntent());

        eventList = (ListView) findViewById(R.id.entryList);
        eventList.setAdapter(new EventAdapter(this, new ArrayList<>(eventsFromIntent)));
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        EventUtilities.fillBundle(eventsFromIntent, savedInstanceState);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        eventsFromIntent = EventUtilities.getEventsFromBundle(savedInstanceState);
        eventList.setAdapter(new EventAdapter(this, new ArrayList<>(eventsFromIntent)));
    }
}
