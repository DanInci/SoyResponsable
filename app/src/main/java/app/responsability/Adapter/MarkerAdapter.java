package app.responsability.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import app.responsability.R;
import app.responsability.models.Issue;

public class MarkerAdapter implements GoogleMap.InfoWindowAdapter {

    private final View view;
    private Issue issue;

    public MarkerAdapter(Context context, Issue issue) {
        this.issue = issue;
        this.view = LayoutInflater.from(context).inflate(R.layout.marker_view, null);
    }

    private void renderWindow(Marker marker, View view){
        TextView title_issue = (TextView) view.findViewById(R.id.title_issue);
        TextView description_issue = (TextView) view.findViewById(R.id.description_issue);
        TextView thumbs_up = (TextView) view.findViewById(R.id.thumbs_up_issue_txt);
        TextView thumbs_down = (TextView) view.findViewById(R.id.thumbs_down_issue_txt);
        ImageView image_issue = (ImageView) view.findViewById(R.id.image_issue);

        String title = issue.getTitle();
        String description = issue.getDescription();
        String thumbsUp = issue.getUpvotes().toString();
        String thumbsDown = issue.getDownvotes().toString();
        String img = issue.getPicture();
        Bitmap btm = BitmapFactory.decodeFile(img);

        title_issue.setText(title);
        description_issue.setText(description);
        thumbs_up.setText(thumbsUp);
        thumbs_down.setText(thumbsDown);
        image_issue.setImageBitmap(btm);

    }

    @Override
    public View getInfoWindow(Marker marker) {
        renderWindow(marker, view);
        return view;
    }

    @Override
    public View getInfoContents(Marker marker) {
        renderWindow(marker, view);
        return view;
    }
}
