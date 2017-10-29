package kevinkuo.groupfind;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.Manifest;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity implements DbFetchCallback {

    public EditText eventTag;
    public EditText numPeople;
    public EditText description;
    public EditText eventTagToSearch;
    public EditText password;
    public EditText lookupPassword;
    public TextView address;

    private Location currentLocation;
    private String currentAddress;
    private AddressResultReceiver addressResultReceiver;
    private LocationResultReceiver locationResultReceiver;
    private DbAction dbAction;

    private static final int MY_PERMISSION_ACCESS_FINE_LOCATION = 0;

    private String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        eventTag = (EditText) findViewById(R.id.eventTag);
        numPeople = (EditText) findViewById(R.id.numPeople);
        description = (EditText) findViewById(R.id.description);
        eventTagToSearch = (EditText) findViewById(R.id.eventTagToSearch);
        password = (EditText) findViewById(R.id.password);
        lookupPassword = (EditText) findViewById(R.id.lookupPassword);
        address = (TextView) findViewById(R.id.address);

        dbAction = new DbAction(this);

        // request location permissions if necessary
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSION_ACCESS_FINE_LOCATION);

            return;
        }

        // if location permission granted
        addressResultReceiver = new AddressResultReceiver(new Handler());
        locationResultReceiver = new LocationResultReceiver(new Handler());
        Log.d(TAG, "receivers instantiated");
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString(Constants.ADDRESS, address.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        address.setText(savedInstanceState.getString(Constants.ADDRESS));
    }

    @Override
    public void onEventDatabaseResultReceived(List<Event> events) {
        startActivity(EventUtilities.createIntent(
                events, MainActivity.this, ShowEventsActivity.class));
    }

    @Override
    public void onPasswordListDatabaseResultReceived(List<String> passwords) {
        // get password in edittext and compare
        final String currentPass = getPassword();
        if (passwords.contains(currentPass)) {
            // password taken
            Toast.makeText(this, "password is taken",
                    Toast.LENGTH_LONG).show();
        } else {
            // password not taken, add event as normal
            try {
                final Event newEvent = new Event(
                        getEventTag(), getNumPeople(), getLocation(),
                        getDescription(), getPassword());
                dbAction.addEvent(newEvent);
                Toast.makeText(this, "event successfully added",
                        Toast.LENGTH_LONG).show();
            } catch (NumberFormatException e) {
                Toast.makeText(this, "too many people",
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onPasswordDatabaseResultReceived(List<Event> events) {
        if (events.isEmpty()) {
            Toast.makeText(this, "Event not found", Toast.LENGTH_LONG).show();
            return;
        }

        // remove event from db
        dbAction.deleteEvent(events.get(0));
        Toast.makeText(this, "Event deleted", Toast.LENGTH_LONG).show();
    }

    /**
     * Class extending ResultReceiver that, when receiving a location result, updates the displayed
     * location, address, and fetches Creation objects for the current location.
     */
    class LocationResultReceiver extends ResultReceiver {
        public LocationResultReceiver(Handler handler) { super(handler); }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            currentLocation = resultData.getParcelable("LOCATION_KEY");
            getAddress();
        }

    }

    /**
     * Class extending ResultReceiver that, when receiving an address result, updates the
     * displayed address.
     */
    class AddressResultReceiver extends ResultReceiver {
        public AddressResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {

            // Display the address string
            // or an error message sent from the intent service.
            currentAddress = resultData.getString(LocationConstants.RESULT_DATA_KEY);
            currentAddress = modifyAddress(currentAddress);
            displayAddressOutput();

            Log.d(TAG, "address found");

        }
    }

    private String modifyAddress(final String address) {
        String edited_adr = "";
        int cnt = 0;
        for (int k1 = 0; k1 < address.length(); k1++) {
            if (address.charAt(k1) == 10) {
                cnt++;
                if (cnt > 1) {
                    edited_adr += ", ";
                } else {
                    edited_adr += (char) 10;
                }
            } else {
                edited_adr += address.charAt(k1);
            }
        }

        return edited_adr;
    }

    private void displayAddressOutput() {
        address.setText(currentAddress);
    }

    /**
     * Starts the FetchAddressIntentService to fetch the current address from
     * surrounding geocodes.
     */
    protected void getAddress() {
        if (currentLocation != null) {
            // Determine whether a Geocoder is available.
            if (!Geocoder.isPresent()) {
                Toast.makeText(this, "no geocoder available",
                        Toast.LENGTH_LONG).show();
                return;
            }

            ServiceStarter.startAddressIntentService(
                    getApplicationContext(), addressResultReceiver, currentLocation);
        }
    }

    public void addEvent(View button) {
        dbAction.getPasswords();
    }

    public void searchForEvent(View button) {
        dbAction.getEvents(getEventTagToSearch());
    }

    public void deleteEvent(View button) {
        // get event from lookupPassword
        dbAction.getEventsByPassword(getLookupPassword());
    }

    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSION_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d(TAG, "permission granted");

                    addressResultReceiver = new AddressResultReceiver(new Handler());
                    locationResultReceiver = new LocationResultReceiver(new Handler());

                    Log.d(TAG, "starting location service");
                    ServiceStarter.startLocationService(getApplicationContext(),
                            locationResultReceiver);

                } else {
                    Log.d(TAG, "permission not granted");
                }
                return;
            }
        }
    }


    @Override
    protected void onStart() {
        Log.d(TAG, "onStart called");
        super.onStart();
    }

    @Override
    protected void onStop() {
        Log.d(TAG, "onStop called");
        super.onStop();
        if (!isChangingConfigurations()) {
            Log.d(TAG, "stopping location service");
            this.stopService(new Intent(this, LocationService.class));
        }

    }

    @Override
    protected void onPause() {
        Log.d(TAG, "onPause called");
        super.onPause();
        if (!isChangingConfigurations()) {
            Log.d(TAG, "stopping location service");
            this.stopService(new Intent(this, LocationService.class));
        }

    }

    @Override
    public void onResume() {
        Log.d(TAG, "onResume called");
        super.onResume();
        Log.d(TAG, "starting location service");
        ServiceStarter.startLocationService(getApplicationContext(),
                locationResultReceiver);

    }

    private String getEventTag() {
        return eventTag.getText().toString().trim();
    }

    private int getNumPeople() {
        return Integer.parseInt(numPeople.getText().toString().trim());
    }

    private String getDescription() {
        return description.getText().toString().trim();
    }

    private String getLocation() {
        return address.getText().toString().trim();
    }

    private String getEventTagToSearch() {
        return eventTagToSearch.getText().toString().trim();
    }

    private String getPassword() {
        return password.getText().toString().trim();
    }

    private String getLookupPassword() {
        return lookupPassword.getText().toString().trim();
    }

}
