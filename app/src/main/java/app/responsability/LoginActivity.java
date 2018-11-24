package app.responsability;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;

import java.io.ByteArrayOutputStream;

import app.responsability.component.LocationAutoCompleteTextView;
import app.responsability.component.LocationListAdapter;
import app.responsability.models.Location;
import app.responsability.models.UserProfile;
import app.responsability.models.UserReg;
import app.responsability.models.component.Sex;
import app.responsability.services.ServiceManager;
import customfonts.Button_Poppins_Regular;
import customfonts.EditText_Poppins_Regular;
import customfonts.MyTextView_Poppins_Medium;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private FrameLayout loginFrame;
    private FrameLayout registerFrame;

    private EditText_Poppins_Regular etEmail;
    private EditText_Poppins_Regular etPass;

    private EditText_Poppins_Regular etRegEmail;
    private EditText_Poppins_Regular etRegPass;
    private EditText_Poppins_Regular etName;
    private EditText_Poppins_Regular etAge;
    private EditText_Poppins_Regular etRadius;
    private LocationAutoCompleteTextView etLocation;
    private Spinner sexSpinner;

    private ImageView profilePic;
    private boolean isProfileChanged;
    private Button_Poppins_Regular btnLogin;
    private MyTextView_Poppins_Medium toLogin;
    private Button_Poppins_Regular btnUploadProfilePic;
    private ProgressBar loginProgressBar;
    private MyTextView_Poppins_Medium loginTvInfo;
    private Button_Poppins_Regular btnRegister;
    private MyTextView_Poppins_Medium toRegister;
    private ProgressBar regProgressBar;
    private MyTextView_Poppins_Medium regTvInfo;

    private Callback<UserProfile> loginCallback = new Callback<UserProfile>() {
        @Override
        public void onResponse(Call<UserProfile> call, Response<UserProfile> response) {
            loginProgressBar.setVisibility(View.GONE);
            btnLogin.setVisibility(View.VISIBLE);
            resetLoginFields();
            if(response.isSuccessful()) {
                UserProfile profile = response.body();
                Location loc = new Location(profile.getName(), profile.getLatitude(), profile.getLongitude());
                AppSoyResponsable.saveSession(ServiceManager.currentEmail, ServiceManager.currentPassword, loc);
                AppSoyResponsable.setLoggedIn(loc);
                finish();
            }
            else {
                ServiceManager.invalidateSession();
                onLoginErrorResponse(call, response);
            }
        }

        @Override
        public void onFailure(Call<UserProfile> call, Throwable t) {
            loginProgressBar.setVisibility(View.GONE);
            btnLogin.setVisibility(View.VISIBLE);
            resetLoginFields();
            ServiceManager.invalidateSession();
            onLoginErrorResponse(call, null);
        }
    };

    private Callback<Long> registerCallback = new Callback<Long>() {
        @Override
        public void onResponse(Call<Long> call, Response<Long> response) {
            regProgressBar.setVisibility(View.GONE);
            btnRegister.setVisibility(View.VISIBLE);
            btnUploadProfilePic.setVisibility(View.VISIBLE);
            resetRegisterFields();
            if(response.isSuccessful()) {
                regTvInfo.setVisibility(View.VISIBLE);
                regTvInfo.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.yesGreen));
                regTvInfo.setText("Registration was successful");
            } else {
                onRegisterErrorResponse(call, response);
            }
        }

        @Override
        public void onFailure(Call<Long> call, Throwable t) {
            regProgressBar.setVisibility(View.GONE);
            btnRegister.setVisibility(View.VISIBLE);
            btnUploadProfilePic.setVisibility(View.VISIBLE);
            resetRegisterFields();
            onRegisterErrorResponse(call, null);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginFrame = findViewById(R.id.loginFrame);
        registerFrame = findViewById(R.id.registerFrame);

        etEmail = findViewById(R.id.etEmail);
        etPass = findViewById(R.id.etPass);

        etRegEmail = findViewById(R.id.etEmailReg);
        etRegPass = findViewById(R.id.etPassReg);
        etName = findViewById(R.id.etName);
        etAge = findViewById(R.id.etAge);
        etRadius = findViewById(R.id.etRadius);
        etLocation = findViewById(R.id.etLocation);
        etLocation.setAdapter(new LocationListAdapter(this));
        etLocation.setLoadingIndicator((ProgressBar) findViewById(R.id.locationProgressBar));
        etLocation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Location loc = (Location) adapterView.getItemAtPosition(position);
                etLocation.setText(loc.getName());
                etLocation.setSelectedLocation(loc);
            }
        });
        etLocation.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus && etLocation.getSelectedLocation() == null) {
                    etLocation.setText("");
                }
            }
        });
        sexSpinner = findViewById(R.id.spinnerSex);
        setupLoadingSpinner();

        btnLogin = findViewById(R.id.btnLogin);
        toLogin = findViewById(R.id.toLogin);
        btnUploadProfilePic = findViewById(R.id.uploadProfilePic);
        profilePic = findViewById(R.id.profilePic);
        loginProgressBar = findViewById(R.id.loginProgressBar);
        loginTvInfo = findViewById(R.id.loginTvInfo);
        btnRegister = findViewById(R.id.btnRegister);
        toRegister = findViewById(R.id.toRegister);
        regProgressBar = findViewById(R.id.regProgressBar);
        regTvInfo = findViewById(R.id.regTvInfo);

        switchLayoutViews(false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnLogin:
                if(checkLoginFields()) {
                    loginUser(etEmail.getText().toString(), etPass.getText().toString());
                }
                break;
            case R.id.btnRegister:
                if(checkRegisterFields()) {
                    registerUser();
                }
                break;
            case R.id.uploadProfilePic:
                Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto , 0);
                break;
            case R.id.toLogin:
                switchLayoutViews(false);
                break;
            case R.id.toRegister:
                switchLayoutViews(true);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 0) {
            if(resultCode == RESULT_OK){
                Uri selectedImage = data.getData();
                profilePic.setImageURI(selectedImage);
                isProfileChanged = true;
            }
        }
    }

    private void switchLayoutViews(boolean isRegisterLayout) {
        if(isRegisterLayout) {
            loginFrame.setVisibility(View.GONE);
            registerFrame.setVisibility(View.VISIBLE);
            resetRegisterFields();
        }
        else {
            loginFrame.setVisibility(View.VISIBLE);
            registerFrame.setVisibility(View.GONE);
            resetLoginFields();
        }
    }

    private void setupLoadingSpinner() {
        String[] genders = {"Male", "Female"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_item, genders);
        sexSpinner.setAdapter(adapter);
    }

    private void resetLoginFields() {
        etEmail.setFocusableInTouchMode(true);
        etEmail.setText("");
        etEmail.setError(null);
        etEmail.requestFocus();
        etPass.setFocusableInTouchMode(true);
        etPass.setText("");
        etPass.setError(null);
        btnLogin.setOnClickListener(this);
        toRegister.setOnClickListener(this);
    }

    private void resetRegisterFields() {
        etName.setFocusableInTouchMode(true);
        etName.setText("");
        etName.setError(null);
        etName.requestFocus();
        etRegEmail.setFocusableInTouchMode(true);
        etRegEmail.setText("");
        etRegEmail.setError(null);
        etRegPass.setFocusableInTouchMode(true);
        etRegPass.setText("");
        etRegPass.setError(null);
        etAge.setFocusableInTouchMode(true);
        etAge.setText("");
        etAge.setError(null);
        sexSpinner.setFocusableInTouchMode(true);
        sexSpinner.setSelection(0);
        etRadius.setFocusableInTouchMode(true);
        etRadius.setText("");
        etRadius.setError(null);
        etLocation.setFocusableInTouchMode(true);
        etLocation.setText("");
        etLocation.setSelected(false);
        etLocation.setError(null);

        btnRegister.setOnClickListener(this);
        btnUploadProfilePic.setOnClickListener(this);
        toLogin.setOnClickListener(this);
    }

    private boolean checkLoginFields() {
        boolean isValid = true;
        loginTvInfo.setVisibility(View.GONE);

        String email = etEmail.getText().toString();
        String pass = etPass.getText().toString();
        if(email.equals("")) {
            etEmail.setError("This field is required");
            isValid = false;
        }
        else if(!email.contains("@")) {
            etEmail.setError("Email is invalid");
            isValid = false;
        }
        if(pass.equals("")) {
            etPass.setError("This field is required");
            isValid = false;
        }
        return isValid;
    }

    private boolean checkRegisterFields() {
        boolean isValid = true;
        regTvInfo.setVisibility(View.GONE);
        String name = etName.getText().toString();
        String email = etRegEmail.getText().toString();
        String age = etAge.getText().toString();
        String radius = etRadius.getText().toString();
        String location = etLocation.getText().toString();
        String pass = etRegPass.getText().toString();
        if(name.equals("")) {
            etName.setError("This field is required");
            isValid = false;
        }
        if(email.equals("")) {
            etRegEmail.setError("This field is required");
            isValid = false;
        }
        else if(!email.contains("@")) {
            etRegEmail.setError("Email is invalid");
            isValid = false;
        }
        if(age.equals("")) {
            etAge.setError("This field is required");
            isValid = false;
        }
        if(radius.equals("")) {
            etRadius.setError("This field is required");
            isValid = false;
        }
        if(location.equals("")) {
            etLocation.setError("This field is required");
            isValid = false;
        }
        if(pass.equals("")) {
            etRegPass.setError("This field is required");
            isValid = false;
        }
        else if(pass.length() < 4) {
            etRegPass.setError("This field should be at least 4 chars long");
            isValid = false;
        }
        return isValid;
    }

    private void loginUser(String email, String pass) {
        loginTvInfo.setVisibility(View.GONE);
        btnLogin.setVisibility(View.GONE);
        toRegister.setOnClickListener(null);
        loginProgressBar.setVisibility(View.VISIBLE);

        etEmail.setFocusable(false);
        etPass.setFocusable(false);

        ServiceManager.invalidateSession();
        ServiceManager.createSession(email, pass);
        Call<UserProfile> call = ServiceManager.getUsersService().getUserProfile();
        call.enqueue(loginCallback);
    }

    private void registerUser() {
        regTvInfo.setVisibility(View.GONE);
        btnRegister.setVisibility(View.GONE);
        btnUploadProfilePic.setVisibility(View.GONE);
        toLogin.setOnClickListener(null);
        regProgressBar.setVisibility(View.VISIBLE);

        etName.setFocusable(false);
        etRegEmail.setFocusable(false);
        etRegPass.setFocusable(false);
        etAge.setFocusable(false);
        etRadius.setFocusable(false);
        sexSpinner.setFocusable(false);
        etLocation.setFocusable(false);

        if(isProfileChanged) {
            BitmapDrawable drawable = (BitmapDrawable) profilePic.getDrawable();
            Bitmap profilePic = drawable.getBitmap();

            new PrepareProfilePicture().execute(profilePic);
        } else {
            afterImageProcessing(null);
        }
    }

    private void afterImageProcessing(String base64Pic) {
        String name = etName.getText().toString();
        String email = etRegEmail.getText().toString();
        String pass = etRegPass.getText().toString();
        Integer age = Integer.valueOf(etAge.getText().toString());
        Sex sex = Sex.getSexByStr(sexSpinner.getSelectedItem().toString());
        String locationName = etLocation.getSelectedLocation().getName();
        Double lat = etLocation.getSelectedLocation().getLat();
        Double lng = etLocation.getSelectedLocation().getLng();
        Double radius = Double.valueOf(etRadius.getText().toString());

        UserReg reg = new UserReg(email, pass, base64Pic, name, age, sex, locationName, lat, lng, radius);

        Call<Long> call = ServiceManager.getUsersService().createUser(reg);
        call.enqueue(registerCallback);
    }

    private void onLoginErrorResponse(Call<UserProfile> call, Response<UserProfile> response) {
        loginTvInfo.setVisibility(View.VISIBLE);
        loginTvInfo.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.noRed));
        if(response == null) {
            loginTvInfo.setText("There was an error connecting to the server");
        } else {
            switch(response.code()) {
                case 401:
                    loginTvInfo.setText("Email or password was incorrect");
                    break;
                case 500:
                    loginTvInfo.setText("There was an error on the server");
                    break;
                default:
                    loginTvInfo.setText("Error not known");
            }
        }
    }

    private void onRegisterErrorResponse(Call<Long> call, Response<Long> response) {
        regTvInfo.setVisibility(View.VISIBLE);
        regTvInfo.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.noRed));
        if(response == null) {
            regTvInfo.setText("There was an error connecting to the server");
        } else {
            switch(response.code()) {
                case 400:
                    regTvInfo.setText("Email already exists");
                    break;
                case 500:
                    regTvInfo.setText("There was an error on the server");
                    break;
                default:
                    regTvInfo.setText("Error not known");
            }
        }
    }

    private class PrepareProfilePicture extends AsyncTask<Bitmap, Void, String> {

        @Override
        protected String doInBackground(Bitmap... bitmaps) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmaps[0].compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            String base64ProfilePic = Base64.encodeToString(byteArray, Base64.NO_WRAP);
            return "data:image/png;base64," + base64ProfilePic;
        }

        @Override
        protected void onPostExecute(String result) {
            if(result.length() > 1024 * 2048) {
                resetRegisterFields();
                btnRegister.setVisibility(View.VISIBLE);
                btnUploadProfilePic.setVisibility(View.VISIBLE);
                regTvInfo.setVisibility(View.VISIBLE);
                regTvInfo.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.noRed));
                regTvInfo.setText("Image is too big. Maximum 2Mb");
            } else {
                afterImageProcessing(result);
            }
        }

        @Override
        protected void onPreExecute() {}

        @Override
        protected void onProgressUpdate(Void... values) {}
    }
}
