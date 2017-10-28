package kevinkuo.groupfind;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public EditText eventTag;
    public EditText numPeople;
    public EditText eventTagToSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        eventTag = (EditText) findViewById(R.id.eventTag);
        numPeople = (EditText) findViewById(R.id.numPeople);
        eventTagToSearch = (EditText) findViewById(R.id.eventTagToSearch);
    }

    public void addEvent(View button) {
        // create new event, grabbing from edittexts
        final Event newEvent = new Event(getEventTag(), getNumPeople(), getLocation());
        DbAction.addEvent(newEvent);
    }

    public void searchForEvent(View button) {
        final List<Event> eventList = DbAction.getEvents(getEventTagToSearch());





    }

    private String getEventTag() {
        return eventTag.getText().toString();
    }

    private int getNumPeople() {
        return Integer.parseInt(numPeople.getText().toString());
    }

    private String getLocation() {
        // TODO: implement

        return "";
    }

    private String getEventTagToSearch() {
        return eventTagToSearch.getText().toString();
    }

    private void displayEvents() {
        // TODO: implement
    }

}
