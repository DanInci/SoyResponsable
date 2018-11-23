package app.responsability;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.widget.Button;
import android.widget.Spinner;


import java.util.Arrays;


import app.responsability.Adapter.SpinnerPoppinsAdapter;
import customfonts.EditText_Poppins_Regular;
import customfonts.MyTextView_Poppins_Medium;

public class RegisterActivity extends AppCompatActivity {

    private EditText_Poppins_Regular email;
    private EditText_Poppins_Regular password;
    private EditText_Poppins_Regular name;
    private EditText_Poppins_Regular age;
    private MyTextView_Poppins_Medium login;
    private MyTextView_Poppins_Medium register;
    private Button upload;
    private Spinner gender;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        upload = (Button) findViewById(R.id.upload);
        name = (EditText_Poppins_Regular) findViewById(R.id.name_input);
        email = (EditText_Poppins_Regular) findViewById(R.id.username_input);
        password = (EditText_Poppins_Regular) findViewById(R.id.password_input);
        age = (EditText_Poppins_Regular) findViewById(R.id.age_input);
        login = (MyTextView_Poppins_Medium) findViewById(R.id.login);
        register = (MyTextView_Poppins_Medium) findViewById(R.id.register);
        gender = (Spinner) findViewById(R.id.gender_input);


        SpinnerPoppinsAdapter genderAdapter = new SpinnerPoppinsAdapter(getApplicationContext(), R.layout.spinner_gender, Arrays.asList(getResources().getStringArray(R.array.gender)));
        gender.setAdapter(genderAdapter);


    }
}


