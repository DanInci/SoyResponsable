package app.responsability.Adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import app.responsability.R;
import app.responsability.models.Issue;

public class IssueAdapter extends RecyclerView.Adapter<IssueAdapter.ViewHolder> {

    private List<Issue> issueList;

    public IssueAdapter(List<Issue> issueList) {
        this.issueList = issueList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.issue_item, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Issue issue = issueList.get(position);

        holder.createdAt.setText(issue.getCreatedAt().toString());
        holder.title.setText(issue.getTitle());
        Bitmap bm = BitmapFactory.decodeFile(issue.getPicture());
        holder.image.setImageBitmap(bm);

    }

    @Override
    public int getItemCount() {
        return issueList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView createdAt;
        public ImageView image;

        public ViewHolder(View v) {
            super(v);
            title = (TextView) v.findViewById(R.id.title_issue);
            createdAt = (TextView) v.findViewById(R.id.created_at);
            image = (ImageView) v.findViewById(R.id.issue_image);
        }

    }
}
