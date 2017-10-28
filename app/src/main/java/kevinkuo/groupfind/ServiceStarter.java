package kevinkuo.groupfind;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.ResultReceiver;
import android.util.Log;

/**
 * Created by kevinkuo on 4/18/17.
 *
 * Includes methods for starting services.
 */

public class ServiceStarter {

    final static String TAG = "ServiceStarter";

    /**
     * Starts FetchAddressIntentService.
     *
     * @param context - Context object used to pass Intent to service
     * @param receiver - ResultReceiver that is passed to the service
     * @param current_location - Location object that indicates the device's current location.
     */

    public static void startAddressIntentService(Context context, ResultReceiver receiver, Location current_location) {
        Intent intent = new Intent(context, FetchAddressIntentService.class);
        intent.putExtra(LocationConstants.RECEIVER, receiver);
        intent.putExtra(LocationConstants.LOCATION_DATA_EXTRA, current_location);
        context.startService(intent);
    }

    /**
     * Starts LocationService.
     *
     * @param context - Context object used to pass Intent to service
     * @param receiver - ResultReceiver that is passed to the service
     */
    public static void startLocationService(Context context, ResultReceiver receiver) {
        Intent intent = new Intent(context, LocationService.class);
        intent.putExtra("LOCATION_RECEIVER", receiver);
        Log.d(TAG, "starting location service (in ServiceStarter)");
        context.startService(intent);
    }

}

