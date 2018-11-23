package app.responsability;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import customfonts.EditText_Poppins_Regular;
import customfonts.MyTextView_Poppins_Medium;

public class LoginActivity extends AppCompatActivity {

    private EditText_Poppins_Regular email;
    private EditText_Poppins_Regular password;
    private MyTextView_Poppins_Medium forgot_password;
    private MyTextView_Poppins_Medium login;
    private ImageView facebook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = (EditText_Poppins_Regular) findViewById(R.id.username_input);
        password = (EditText_Poppins_Regular) findViewById(R.id.password_input);
        forgot_password = (MyTextView_Poppins_Medium) findViewById(R.id.forgot_password);
        login = (MyTextView_Poppins_Medium) findViewById(R.id.login);
        facebook = (ImageView) findViewById(R.id.facebook);
    }
}
