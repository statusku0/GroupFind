package kevinkuo.groupfind;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    public EditText eventTag;
    public EditText numPeople;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        eventTag = (EditText) findViewById(R.id.eventTag);
        numPeople = (EditText) findViewById(R.id.numPeople);
    }

    public void addEvent(View button) {
        // create new event, grabbing from edittexts
        final Event newEvent = new Event(getEventTag(), getNumPeople());
        DbAction.addEvent(newEvent);
    }

    public void searchForEvent(View button) {
        // show popup to input tag




    }

    private String getEventTag() {
        return eventTag.getText().toString();
    }

    private int getNumPeople() {
        return Integer.parseInt(numPeople.getText().toString());
    }

    private void displayEvents() {
        // TODO: implement
    }

}
