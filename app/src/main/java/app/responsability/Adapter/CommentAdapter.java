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
import app.responsability.models.Comment;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {
    private List<Comment> commentList;

    public CommentAdapter(List<Comment> commentList){
        this.commentList = commentList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.comment_view, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Comment comment = commentList.get(i);
        viewHolder.createdAt.setText(comment.getCreatedAt().toString());
        viewHolder.user.setText(comment.getCommentedByUser().getName());
        viewHolder.description.setText(comment.getContent());
        Bitmap bm = BitmapFactory.decodeFile(comment.getCommentedByUser().getProfilePic());
        viewHolder.imageView.setImageBitmap(bm);
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView imageView;
        public TextView user, description, createdAt;

        public ViewHolder(View v){
            super(v);
            imageView = (ImageView) v.findViewById(R.id.profile_image);
            user = (TextView) v.findViewById(R.id.comment_user);
            description = (TextView) v.findViewById(R.id.comment_description);
            createdAt = (TextView) v.findViewById(R.id.comment_createdAt);
        }

    }
}
