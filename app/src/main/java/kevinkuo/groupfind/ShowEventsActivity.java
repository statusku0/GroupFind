package kevinkuo.groupfind;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

public class ShowEventsActivity extends AppCompatActivity {

    public ListView eventList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_events);

        eventList = (ListView) findViewById(R.id.entryList);
        
    }
}
