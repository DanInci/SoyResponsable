package app.responsability;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import customfonts.EditText_Poppins_Regular;
import customfonts.MyTextView_Poppins_Medium;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText_Poppins_Regular email;
    private EditText_Poppins_Regular password;
    private MyTextView_Poppins_Medium forgot_password;
    private MyTextView_Poppins_Medium login;
    private MyTextView_Poppins_Medium register;
    private ImageView facebook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = (EditText_Poppins_Regular) findViewById(R.id.username_input);
        password = (EditText_Poppins_Regular) findViewById(R.id.password_input);
        forgot_password = (MyTextView_Poppins_Medium) findViewById(R.id.forgot_password);
        login = (MyTextView_Poppins_Medium) findViewById(R.id.login);
        register = (MyTextView_Poppins_Medium) findViewById(R.id.register);
        facebook = (ImageView) findViewById(R.id.facebook);

        facebook.setOnClickListener(this);
        register.setOnClickListener(this);
        login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.register:
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
                finish();
                break;
            case R.id.facebook:
                break;
            case R.id.login:
                break;
        }
    }
}
