package app.responsability.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import app.responsability.R;
import app.responsability.models.Issue;
import app.responsability.services.ServiceManager;
import customfonts.Button_Poppins_Regular;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

public class IssueDialogFragment extends DialogFragment {
    private Uri selectedImage;
    private Callback<Long> issueCallback = new Callback<Long>() {
        @Override
        public void onResponse(Call<Long> call, Response<Long> response) {
            if(response.isSuccessful()){
                Toast.makeText(getContext(), "Issue Added", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(getContext(), "Failed to add issue!", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFailure(Call<Long> call, Throwable t) {

        }
    };

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {



        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View dialogView = inflater.inflate(R.layout.issue_add, null);
        final EditText title = (EditText) dialogView.findViewById(R.id.title_issue_add);
        final EditText description = (EditText) dialogView.findViewById(R.id.description_add);
        Button_Poppins_Regular select = (Button_Poppins_Regular) dialogView.findViewById(R.id.selectPicture);

        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto , 0);
            }
        });

        dialogBuilder.setView(dialogView);
        dialogBuilder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(title.getText().toString().isEmpty() || description.getText().toString().isEmpty()){
                    Toast.makeText(getContext(), "There are empty fields!", Toast.LENGTH_SHORT).show();
                }else if(selectedImage==null){
                    Toast.makeText(getContext(), "Select an image!", Toast.LENGTH_SHORT).show();
                }
                else {
                    Issue issue = new Issue(title.getText().toString(), description.getText().toString(), selectedImage.toString(), "21321.21", "3734.23");
                    Call<Long> call = ServiceManager.getIssuesService().createIssue(issue);
                    call.enqueue(issueCallback);
                }

            }
        });

        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismiss();
            }
        });


        return dialogBuilder.create();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 0) {
            if(resultCode == RESULT_OK){
                selectedImage = data.getData();
            }
        }
    }
}
