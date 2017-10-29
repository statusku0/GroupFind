package kevinkuo.groupfind;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by kevinkuo on 10/28/17.
 */

public class DbAction {

    static final List<Event> eventList = new ArrayList<>();
    private static final String TAG = "DbAction";

    private DbFetchCallback activityClass;

    public DbAction(final DbFetchCallback activityClass) {
        this.activityClass = activityClass;
    }

    public void addEvent(final Event event) {
        final DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();
        databaseRef.child("tags").child(event.getTag()).child(event.getEventId()).setValue(event);
    }

    public List<Event> getEvents(final String tag) {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        Query ref = database.getReference("tags").child(tag).orderByKey();
        Log.d(TAG, "fetching query");
        ref.addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Log.d(TAG, "onDataChange called");
                        List<Event> events = new ArrayList<>();

                        for (DataSnapshot child : dataSnapshot.getChildren()) {
                            Log.d(TAG, child.getValue().toString());
                            events.add(child.getValue(Event.class));
                        }
                        Collections.reverse(events);
                        activityClass.onEventDatabaseResultReceived(events);
                    }


                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //handle databaseError
                    }
                });

        return Collections.emptyList();
    }
}
