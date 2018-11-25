package app.responsability.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import app.responsability.IssueActivity;
import app.responsability.R;
import app.responsability.models.Issue;



public class IssueAdapter extends RecyclerView.Adapter<IssueAdapter.ViewHolder> {

    private List<Issue> issueList;
    private Context context;
    public IssueAdapter(List<Issue> issueList) {
        this.issueList = issueList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.issue_item, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final IssueAdapter.ViewHolder holder, final int position) {
        final Issue issue = issueList.get(position);
        holder.createdAt.setText(issue.getCreatedAt().toString());
        holder.title.setText(issue.getTitle());
        Picasso.get().load(issue.getPicture()).into(holder.image);
        holder.issue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, IssueActivity.class);
                intent.putExtra("issueId", issue.getId().toString());

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return issueList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView createdAt;
        public ImageView image;
        public CardView issue;

        public ViewHolder(View v) {
            super(v);
            title = (TextView) v.findViewById(R.id.title_issue);
            createdAt = (TextView) v.findViewById(R.id.created_at);
            image = (ImageView) v.findViewById(R.id.issue_image);
            issue = (CardView) v.findViewById(R.id.issue);
        }

    }



}
