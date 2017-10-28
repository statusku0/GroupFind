package kevinkuo.groupfind;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by kevinkuo on 10/28/17.
 */

public class EventAdapter extends ArrayAdapter<Event> {

    public EventAdapter(Context context, ArrayList<Event> events) {
        super(context, R.layout.event_entry, events);
    }

    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        final LayoutInflater inflater = LayoutInflater.from(getContext());
        final View view = inflater.inflate(R.layout.event_entry, parent, false);

        final Event event = getItem(position);
        final TextView tag = (TextView) view.findViewById(R.id.entryTag);
        final TextView numPeople = (TextView) view.findViewById(R.id.entryNumPeople);
        final TextView location = (TextView) view.findViewById(R.id.entryLocation);

        tag.setText(event.getTag());
        numPeople.setText(Integer.toString(event.getNumPeople()));
        location.setText(event.getLocation());

        return view;
    }

}
