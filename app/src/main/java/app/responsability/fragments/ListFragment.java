package app.responsability.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import app.responsability.Adapter.IssueAdapter;
import app.responsability.services.ServiceManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import app.responsability.R;
import app.responsability.models.Issue;

public class ListFragment extends Fragment {

    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private List<Issue> issueList;
    private RecyclerView recyclerViewIssues;
    private TextView noneIssues;

    private Callback<List<Issue>> issueCallback = new Callback<List<Issue>>() {


        @Override
        public void onResponse(Call<List<Issue>> call, Response<List<Issue>> response) {
            if(response.isSuccessful()){
                issueList = response.body();

                if(issueList.isEmpty() || issueList==null){
                    noneIssues.setVisibility(View.VISIBLE);
                    recyclerViewIssues.setVisibility(View.GONE);
                }
                else{
                    layoutManager = new LinearLayoutManager(getContext());
                    recyclerViewIssues.setHasFixedSize(true);
                    recyclerViewIssues.setLayoutManager(layoutManager);
                    adapter = new IssueAdapter(issueList);
                    recyclerViewIssues.setAdapter(adapter);

                    noneIssues.setVisibility(View.GONE);
                    recyclerViewIssues.setVisibility(View.VISIBLE);

                }
            }
            else{
                noneIssues.setVisibility(View.VISIBLE);
                recyclerViewIssues.setVisibility(View.GONE);
                noneIssues.setText("An error occured1...");
            }
        }

        @Override
        public void onFailure(Call<List<Issue>> call, Throwable t) {
            noneIssues.setVisibility(View.VISIBLE);
            recyclerViewIssues.setVisibility(View.GONE);
            noneIssues.setText("An error occured...");
        }
    };

    public ListFragment() { }

    public static ListFragment newInstance() {
        ListFragment fragment = new ListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_list, container, false);
        recyclerViewIssues = (RecyclerView) view.findViewById(R.id.issues_list);

        noneIssues = (TextView) view.findViewById(R.id.noneIssues);

        Call<List<Issue>> call = ServiceManager.getIssuesService().getIssues(45.74940, 21.22720, 20000.0);
        call.enqueue(issueCallback);

        return view;
    }



}
