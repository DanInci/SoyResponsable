package app.responsability;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import app.responsability.Adapter.CommentAdapter;
import app.responsability.models.Comment;
import app.responsability.models.Issue;
import app.responsability.services.ServiceManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

public class IssueActivity extends AppCompatActivity {

    private Issue issue;
    private List<Comment> commentList;
    private TextView title;
    private EditText comment_input;
    private TextView description;
    private ImageView image;
    private ImageButton commentBtn;
    private String issueId;
    private Bundle bundle;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerViewComments;

    private Callback<Long> commentCallback = new Callback<Long>() {
        @Override
        public void onResponse(Call<Long> call, Response<Long> response) {
            if (response.isSuccessful()){
                Long id = response.body();
                commentList = issue.getComments();
                adapter = new CommentAdapter(commentList);
                recyclerViewComments.setAdapter(adapter);
            }
            else{
                Toast.makeText(getApplicationContext(),"Comment not posted!", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFailure(Call<Long> call, Throwable t) {
            Toast.makeText(getApplicationContext(),"An error occured!", Toast.LENGTH_SHORT).show();
        }
    };

    private Callback<Issue> issueCallback = new Callback<Issue>() {
        @Override
        public void onResponse(Call<Issue> call, Response<Issue> response) {
            if (response.isSuccessful()){
                Issue issue = response.body();
                title.setText(issue.getTitle());
                description.setText(issue.getDescription());
                Picasso.get().load(issue.getPicture()).into(image);

                commentList = issue.getComments();
                adapter = new CommentAdapter(commentList);
                recyclerViewComments.setAdapter(adapter);

            }
            else{
                finish();
            }
        }

        @Override
        public void onFailure(Call<Issue> call, Throwable t) {
            finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue);
        bundle = getIntent().getExtras();

        if (bundle!=null){
            issueId = bundle.getString("issueId");
        }


        recyclerViewComments = (RecyclerView) findViewById(R.id.comments);
        recyclerViewComments.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewComments.setLayoutManager(layoutManager);

        title = (TextView) findViewById(R.id.title_issue);
        description = (TextView) findViewById(R.id.description_issue);
        image = (ImageView) findViewById(R.id.image_issue);
        comment_input = (EditText) findViewById(R.id.comment_input);

        commentBtn = (ImageButton) findViewById(R.id.post_comment);
        commentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (comment_input.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Empty comment", Toast.LENGTH_SHORT).show();
                }else{
                    Comment comment = new Comment();
                    comment.setContent(comment_input.getText().toString());
                    Call<Long> call = ServiceManager.getCommentsService().createComment( Long.parseLong(issueId), comment);
                    call.enqueue(commentCallback);
                }
            }
        });

        Call<Issue> call = ServiceManager.getIssuesService().getIssueById(Long.parseLong(issueId));
        call.enqueue(issueCallback);
    }
}
