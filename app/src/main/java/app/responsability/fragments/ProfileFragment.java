package app.responsability.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.squareup.picasso.Picasso;

import app.responsability.AppSoyResponsable;
import app.responsability.R;
import app.responsability.component.LocationAutoCompleteTextView;
import app.responsability.component.LocationListAdapter;
import app.responsability.models.Location;
import app.responsability.models.UserProfile;
import app.responsability.models.component.Sex;
import app.responsability.services.ServiceManager;
import customfonts.EditText_Poppins_Regular;
import customfonts.MyTextView_Poppins_Medium;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment implements View.OnClickListener {

    public ProfileFragment() {}

    private ImageView profilePicture;

    private MyTextView_Poppins_Medium tvName;
    private EditText_Poppins_Regular etName;
    private ImageButton btnEditName;
    private ImageButton btnCheckEditName;

    private MyTextView_Poppins_Medium tvEmail;

    private MyTextView_Poppins_Medium tvAge;
    private EditText_Poppins_Regular etAge;
    private ImageButton btnEditAge;
    private ImageButton btnCheckEditAge;

    private MyTextView_Poppins_Medium tvSex;
    private Spinner spinnerSex;
    private ImageButton btnEditSex;
    private ImageButton btnCheckEditSex;

    private MyTextView_Poppins_Medium tvRadius;
    private EditText_Poppins_Regular etRadius;
    private ImageButton btnEditRadius;
    private ImageButton btnCheckEditRadius;

    private MyTextView_Poppins_Medium tvLocation;
    private LocationAutoCompleteTextView etLocation;
    private ImageButton btnEditLocation;
    private ImageButton btnCheckEditLocation;


    private Callback<UserProfile> getProfileCallback = new Callback<UserProfile>() {
        @Override
        public void onResponse(Call<UserProfile> call, Response<UserProfile> response) {
            if(response.isSuccessful()) {
                AppSoyResponsable.loggedInUser = response.body();
                updateProfileFields(response.body());
            }
            else {
                Log.e(AppSoyResponsable.SOY_RESPONSABLE, "Failed to load user profile");
            }
        }

        @Override
        public void onFailure(Call<UserProfile> call, Throwable t) {
            Log.e(AppSoyResponsable.SOY_RESPONSABLE, "Failed to load user profile");
        }
    };

    private Callback<Long> updateProfileCallback = new Callback<Long>() {
        @Override
        public void onResponse(Call<Long> call, Response<Long> response) {
            if(response.isSuccessful()) {
      //          reloadProfileInfo();
            }
            else {
                Log.e(AppSoyResponsable.SOY_RESPONSABLE, "Failed to update user profile");
            }
        }
        @Override
        public void onFailure(Call<Long> call, Throwable t) {
            Log.e(AppSoyResponsable.SOY_RESPONSABLE, "Failed to update user profile");
        }
    };

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_profile, container, false);

        profilePicture = view.findViewById(R.id.profilePic);

        tvName = view.findViewById(R.id.tvName);
        etName = view.findViewById(R.id.etName);
        btnEditName = view.findViewById(R.id.btnEditName);
        btnEditName.setOnClickListener(this);
        btnCheckEditName = view.findViewById(R.id.btnCheckEditName);

        tvEmail = view.findViewById(R.id.tvEmail);

        tvAge = view.findViewById(R.id.tvAge);
        etAge = view.findViewById(R.id.etAge);
        btnEditAge = view.findViewById(R.id.btnEditAge);
        btnEditAge.setOnClickListener(this);
        btnCheckEditAge = view.findViewById(R.id.btnCheckEditAge);

        tvSex = view.findViewById(R.id.tvSex);
        spinnerSex = view.findViewById(R.id.spinnerSex);
        setupLoadingSpinner();
        btnEditSex = view.findViewById(R.id.btnEditSex);
        btnEditSex.setOnClickListener(this);
        btnCheckEditSex= view.findViewById(R.id.btnCheckEditSex);

        tvRadius = view.findViewById(R.id.tvRadius);
        etRadius = view.findViewById(R.id.etRadius);
        btnEditRadius = view.findViewById(R.id.btnEditRadius);
        btnEditRadius.setOnClickListener(this);
        btnCheckEditRadius = view.findViewById(R.id.btnCheckEditRadius);

        tvLocation = view.findViewById(R.id.tvLocation);
        etLocation = view.findViewById(R.id.etLocation);
        etLocation.setAdapter(new LocationListAdapter(getContext()));
        etLocation.setLoadingIndicator((ProgressBar) view.findViewById(R.id.locationProgressBar));
        etLocation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Location loc = (Location) adapterView.getItemAtPosition(position);
                etLocation.setText(loc.getName());
                etLocation.setSelectedLocation(loc);
            }
        });
        btnEditLocation = view.findViewById(R.id.btnEditLocation);
        btnEditLocation.setOnClickListener(this);
        btnCheckEditLocation = view.findViewById(R.id.btnCheckEditLocation);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        reloadProfileInfo();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnEditName:
                switchToEditName();
                break;
            case R.id.btnEditAge:
                switchToEditAge();
                break;
            case R.id.btnEditSex:
                switchToEditSex();
                break;
            case R.id.btnEditRadius:
                switchToEditRadius();
                break;
            case R.id.btnEditLocation:
                switchToEditLocation();
                break;
        }
    }

    private void reloadProfileInfo() {
        updateProfileFields(AppSoyResponsable.loggedInUser);
        Call<UserProfile> call = ServiceManager.getUsersService().getUserProfile();
        call.enqueue(getProfileCallback);
    }

    private void switchToEditName() {
        hideEditButtons();
        btnCheckEditName.setVisibility(View.VISIBLE);
        tvName.setVisibility(View.GONE);
        etName.setVisibility(View.VISIBLE);

        final String currentValue = tvName.getText().toString();
        etName.setText(currentValue);
        btnCheckEditName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newValue = etName.getText().toString();
                if(!newValue.equals(currentValue) && !newValue.equals("")) {
                    tvName.setText(newValue);
                    Long id = AppSoyResponsable.loggedInUser.getId();
                    UserProfile update = new UserProfile();
                    update.setName(newValue);
                    Call<Long> call = ServiceManager.getUsersService().updateUserProfile(id, update);
                    call.enqueue(updateProfileCallback);
                }
                btnCheckEditName.setOnClickListener(null);
                btnCheckEditName.setVisibility(View.GONE);
                etName.setVisibility(View.GONE);
                tvName.setVisibility(View.VISIBLE);
                showEditButtons();
            }
        });
    }

    private void switchToEditAge() {
        hideEditButtons();
        btnCheckEditAge.setVisibility(View.VISIBLE);
        tvAge.setVisibility(View.GONE);
        etAge.setVisibility(View.VISIBLE);

        final String currentValue = tvAge.getText().toString();
        etAge.setText(currentValue);
        btnCheckEditAge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newValue = etAge.getText().toString();
                if(!newValue.equals(currentValue) && !newValue.equals("")) {
                    tvAge.setText(newValue);
                    Long id = AppSoyResponsable.loggedInUser.getId();
                    UserProfile update = new UserProfile();
                    update.setAge(Integer.valueOf(newValue));
                    Call<Long> call = ServiceManager.getUsersService().updateUserProfile(id, update);
                    call.enqueue(updateProfileCallback);
                }
                btnCheckEditAge.setOnClickListener(null);
                btnCheckEditAge.setVisibility(View.GONE);
                etAge.setVisibility(View.GONE);
                tvAge.setVisibility(View.VISIBLE);
                showEditButtons();
            }
        });
    }

    private void switchToEditSex() {
        hideEditButtons();
        btnCheckEditSex.setVisibility(View.VISIBLE);
        tvSex.setVisibility(View.GONE);
        spinnerSex.setVisibility(View.VISIBLE);

        final Sex currentValue = Sex.getSexByStr(tvSex.getText().toString());
        if(currentValue == Sex.MALE) {
            spinnerSex.setSelection(0);
        }
        else if(currentValue == Sex.FEMALE) {
            spinnerSex.setSelection(1);
        }

        btnCheckEditSex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Sex newValue = (Sex) spinnerSex.getSelectedItem();
                if(newValue != currentValue) {
                    tvSex.setText(newValue.toString());
                    Long id = AppSoyResponsable.loggedInUser.getId();
                    UserProfile update = new UserProfile();
                    update.setSex(newValue);
                    Call<Long> call = ServiceManager.getUsersService().updateUserProfile(id, update);
                    call.enqueue(updateProfileCallback);
                }
                btnCheckEditSex.setOnClickListener(null);
                btnCheckEditSex.setVisibility(View.GONE);
                spinnerSex.setVisibility(View.GONE);
                tvSex.setVisibility(View.VISIBLE);
                showEditButtons();
            }
        });
    }

    private void switchToEditRadius() {
        hideEditButtons();
        btnCheckEditRadius.setVisibility(View.VISIBLE);
        tvRadius.setVisibility(View.GONE);
        etRadius.setVisibility(View.VISIBLE);

        final String currentValue = tvRadius.getText().toString().replace(" meters", "");
        etRadius.setText(currentValue);
        btnCheckEditRadius.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newValue = etRadius.getText().toString();
                if(!newValue.equals(currentValue) && !newValue.equals("")) {
                    tvRadius.setText(newValue + " meters");
                    Long id = AppSoyResponsable.loggedInUser.getId();
                    UserProfile update = new UserProfile();
                    update.setRadius(Double.valueOf(newValue));
                    Call<Long> call = ServiceManager.getUsersService().updateUserProfile(id, update);
                    call.enqueue(updateProfileCallback);
                }
                btnCheckEditRadius.setOnClickListener(null);
                btnCheckEditRadius.setVisibility(View.GONE);
                etRadius.setVisibility(View.GONE);
                tvRadius.setVisibility(View.VISIBLE);
                showEditButtons();
            }
        });
    }

    private void switchToEditLocation() {
        hideEditButtons();
        btnCheckEditLocation.setVisibility(View.VISIBLE);
        tvLocation.setVisibility(View.GONE);
        etLocation.setVisibility(View.VISIBLE);

        final String currentValue = tvLocation.getText().toString();
        final Location currentLocation = new Location(
                AppSoyResponsable.loggedInUser.getLocationName(),
                AppSoyResponsable.loggedInUser.getLatitude(),
                AppSoyResponsable.loggedInUser.getLongitude()
        );
        etLocation.setText(currentValue);
        etLocation.setSelectedLocation(currentLocation);
        btnCheckEditLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Location newValue = etLocation.getSelectedLocation();
                if(newValue != currentLocation) {
                    tvLocation.setText(newValue.getName());
                    Long id = AppSoyResponsable.loggedInUser.getId();
                    UserProfile update = new UserProfile();
                    update.setLocationName(newValue.getName());
                    update.setLatitude(newValue.getLat());
                    update.setLongitude(newValue.getLng());
                    Call<Long> call = ServiceManager.getUsersService().updateUserProfile(id, update);
                    call.enqueue(updateProfileCallback);
                }
                btnCheckEditLocation.setOnClickListener(null);
                btnCheckEditLocation.setVisibility(View.GONE);
                etLocation.setVisibility(View.GONE);
                tvLocation.setVisibility(View.VISIBLE);
                showEditButtons();
            }
        });
    }

    private void hideEditButtons() {
        btnEditName.setVisibility(View.GONE);
        btnEditAge.setVisibility(View.GONE);
        btnEditSex.setVisibility(View.GONE);
        btnEditRadius.setVisibility(View.GONE);
        btnEditLocation.setVisibility(View.GONE);
    }

    private void showEditButtons() {
        btnEditName.setVisibility(View.VISIBLE);
        btnEditAge.setVisibility(View.VISIBLE);
        btnEditSex.setVisibility(View.VISIBLE);
        btnEditRadius.setVisibility(View.VISIBLE);
        btnEditLocation.setVisibility(View.VISIBLE);
    }

    private void updateProfileFields(UserProfile profile) {
        if(profile != null) {
            if(profile.getProfilePic() != null) {
                Picasso.get().load(profile.getProfilePic()).error(R.drawable.ic_avatar).into(profilePicture);
            }
            tvName.setText(profile.getName() == null ? "" : profile.getName());
            tvEmail.setText(profile.getEmail() == null ? "" : profile.getEmail());
            tvAge.setText(profile.getAge() == null ? "" : profile.getAge().toString());
            tvSex.setText(profile.getSex() == null ? "" : profile.getSex().toString());
            tvRadius.setText(profile.getRadius() == null ? "" : profile.getRadius().toString() + " meters");
            tvLocation.setText(profile.getLocationName() == null ? "" : profile.getLocationName());
        }
    }

    private void setupLoadingSpinner() {
        ArrayAdapter<Sex> adapter = new ArrayAdapter<>(getContext(), R.layout.spinner_item, Sex.values());
        spinnerSex.setAdapter(adapter);
    }
}
