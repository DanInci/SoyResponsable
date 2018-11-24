package app.responsability.component;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import app.responsability.AppSoyResponsable;
import app.responsability.R;
import app.responsability.models.Location;
import app.responsability.models.component.GoogleLocation;
import app.responsability.models.component.LocationSearchResult;
import app.responsability.services.google.GoogleMapsServiceManager;
import retrofit2.Call;
import retrofit2.Response;

public class LocationListAdapter extends BaseAdapter implements Filterable {

    private static final int MAX_RESULTS = 5;

    private Context context;
    private List<Location> resultList = Collections.singletonList(AppSoyResponsable.CURRENT_LOCATION);

    public LocationListAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return resultList.size();
    }

    @Override
    public Location getItem(int position) {
        return resultList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.location_dropdown_item, parent, false);
        }
        ((TextView) convertView.findViewById(R.id.name)).setText(getItem(position).getName());
        ((TextView) convertView.findViewById(R.id.coordinates)).setText("(" + getItem(position).getLat() + "," + getItem(position).getLng() + ")");
        return convertView;
    }

    @Override
    public Filter getFilter() {
        return new LocationsListFilter();
    }

    private class LocationsListFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence prefix) {
            FilterResults results = new FilterResults();
            if (prefix != null && prefix.length() != 0) {
                List<Location> onlySome = Collections.singletonList(AppSoyResponsable.CURRENT_LOCATION);
                int i = 0;
                for(Location loc : findLocations(prefix)) {
                    if(i == MAX_RESULTS - 1) {
                        break;
                    }
                    onlySome.add(loc);
                }
                results.values = onlySome;
                results.count = onlySome.size();
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence prefix, FilterResults results) {
            if (results != null && results.count > 0) {
                resultList = (List<Location>) results.values;
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }
    }


    /**
     * Returns a search result for the given location name.
     */
    private List<Location> findLocations(CharSequence locationName) {
        Call<LocationSearchResult> call = GoogleMapsServiceManager.findLocationsByName(locationName.toString());
        List<Location> locations = new ArrayList<>();
        try {
            Response<LocationSearchResult> result = call.execute();
            if(result.isSuccessful()) {
                for(GoogleLocation loc : result.body().getCandidates()) {
                    locations.add(new Location(loc.getName(), loc.getGeometry().getLocation().getLat(), loc.getGeometry().getLocation().getLng()));
                }
            } else {
                Log.e(AppSoyResponsable.SOY_RESPONSABLE, "Failed to load locations from google.");
            }
        } catch (IOException e ){
            Log.e(AppSoyResponsable.SOY_RESPONSABLE, "Failed to load locations from google.");
        }
        return locations;
    }
}
