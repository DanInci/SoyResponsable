package app.responsability;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.List;

import app.responsability.fragments.ListFragment;
import app.responsability.fragments.ProfileFragment;
import app.responsability.models.Issue;
import app.responsability.services.ServiceManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static app.responsability.AppSoyResponsable.CURRENT_LOCATION_NAME;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener,
        OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleMap.OnMarkerClickListener {

    private final Integer MY_PERMISSIONS_REQUEST_LOCATION = 23;

    private final FragmentManager fm = getSupportFragmentManager();
    private final ProfileFragment profileFragment = ProfileFragment.newInstance();
    private final SupportMapFragment mapFragment = SupportMapFragment.newInstance();
    private final ListFragment listFragment = ListFragment.newInstance();

    private GoogleMap map;
    private Circle regionRadius;
    private List<Marker> addedMarkers;
    private GoogleApiClient googleApiClient;
    private FusedLocationProviderClient fusedLocationClient;
    private BottomNavigationView bottomNavigation;

    private Callback<List<Issue>> issuesCallback = new Callback<List<Issue>>() {
        @Override
        public void onResponse(Call<List<Issue>> call, Response<List<Issue>> response) {
            if(response.isSuccessful()) {
                for(Issue issue: response.body()) {
                    addIssueToMap(issue);
                }
            }else {
                Log.e(AppSoyResponsable.SOY_RESPONSABLE, "Failed to load issues");
            }
        }
        @Override
        public void onFailure(Call<List<Issue>> call, Throwable t) {
            Log.e(AppSoyResponsable.SOY_RESPONSABLE, "Failed to load issues");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigation = findViewById(R.id.navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(this);
        bottomNavigation.setSelectedItemId(R.id.navigation_map);

        mapFragment.getMapAsync(this);
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
    }

    @Override
    protected void onStart() {
        checkLocationPreferences();
        googleApiClient.connect();
        super.onStart();
    }

    @Override
    protected void onStop() {
        googleApiClient.disconnect();
        cleanMap();
        super.onStop();
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Toast.makeText(MainActivity.this, "onMarkerClick", Toast.LENGTH_SHORT).show();
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_profile:
                if (!AppSoyResponsable.isLoggedIn) {
                    Intent intent = new Intent(this, LoginActivity.class);
                    startActivity(intent);
                    return false;
                } else {
                    fm.beginTransaction().replace(R.id.frame_layout, profileFragment).commit();
                    return true;
                }
            case R.id.navigation_map:
                fm.beginTransaction().replace(R.id.frame_layout, mapFragment).commit();
                return true;
            case R.id.navigation_issues:
                fm.beginTransaction().replace(R.id.frame_layout, listFragment).commit();
                return true;
            case R.id.navigation_statistics:
                return false;

        }

        return false;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.getUiSettings().setZoomControlsEnabled(true);
        checkLocationPermissions();
        checkLocationPreferences();
    }

    private void checkLocationPreferences() {
        if(map == null) return;
        if (AppSoyResponsable.isLoggedIn && !AppSoyResponsable.loggedInUser.getLocationName().equals(CURRENT_LOCATION_NAME)) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            map.setMyLocationEnabled(false);
            moveMap(
                    AppSoyResponsable.loggedInUser.getLatitude(),
                    AppSoyResponsable.loggedInUser.getLongitude(),
                    AppSoyResponsable.loggedInUser.getRadius()
            );
        } else {
            map.setMyLocationEnabled(true);
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        if (map.isMyLocationEnabled()) {
            moveToCurrentLocation();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
    }

    @SuppressLint("MissingPermission")
    private void moveToCurrentLocation() {
        map.clear();
        fusedLocationClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                Double radius = AppSoyResponsable.loggedInUser == null ? 1000 : AppSoyResponsable.loggedInUser.getRadius();
                moveMap(location.getLatitude(), location.getLongitude(), radius);
            }
        });
    }

    private void checkLocationPermissions() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            String[] permissions = new String[] {Manifest.permission.ACCESS_FINE_LOCATION};
            ActivityCompat.requestPermissions(this, permissions, MY_PERMISSIONS_REQUEST_LOCATION);
        }
    }

    private void cleanMap() {
        regionRadius.remove();
        for(Marker marker : addedMarkers) {
            marker.remove();
        }
        addedMarkers = null;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == MY_PERMISSIONS_REQUEST_LOCATION) {
            checkLocationPreferences();
            onConnected(null);
        }
    }

    private void moveMap(Double latitude, Double longitude, Double radius) {
        LatLng latLng = new LatLng(latitude, longitude);
        if(regionRadius != null) {
            regionRadius.remove();
        }
        regionRadius = map.addCircle(new CircleOptions()
                        .center(latLng)
                        .radius(radius)
                        .strokeColor(R.color.colorBlue)
                        .strokeWidth(2f));
        map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        map.animateCamera(CameraUpdateFactory.zoomTo(15));

        Call<List<Issue>> call = ServiceManager.getIssuesService().getIssues(latitude, longitude, radius);
        call.enqueue(issuesCallback);
    }

    private void addIssueToMap(Issue issue) {
        if(map != null) {
            if(addedMarkers == null) addedMarkers = new ArrayList<>();
            LatLng latLng = new LatLng(issue.getLatitude(), issue.getLongitude());
            Marker marker = map.addMarker(new MarkerOptions()
                                .position(latLng)
                                .title(issue.getTitle())
                                .draggable(false));
            addedMarkers.add(marker);
        }
    }

}
