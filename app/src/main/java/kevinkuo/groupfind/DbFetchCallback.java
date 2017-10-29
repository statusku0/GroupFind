package kevinkuo.groupfind;

import java.util.List;

/**
 * Created by kevinkuo on 10/28/17.
 * Callback interface for fetching data from db.
 */
public interface DbFetchCallback {
    void onEventDatabaseResultReceived(List<Event> events);
}
