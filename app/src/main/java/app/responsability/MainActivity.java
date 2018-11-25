package app.responsability;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import app.responsability.fragments.ListFragment;
import app.responsability.fragments.ProfileFragment;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, BottomNavigationView.OnNavigationItemSelectedListener {

    private final FragmentManager fm = getSupportFragmentManager();
    private final ProfileFragment profileFragment = ProfileFragment.newInstance();
    private final SupportMapFragment mapFragment = SupportMapFragment.newInstance();
    private final ListFragment listFragment = ListFragment.newInstance();

    private GoogleMap map;
    private BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigation = findViewById(R.id.navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(this);
        bottomNavigation.setSelectedItemId(R.id.navigation_map);

        mapFragment.getMapAsync(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_profile:
                if(!AppSoyResponsable.isLoggedIn)  {
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

        // Add a marker in Sydney and move the camera

        LatLng sydney = new LatLng(45.74940,  	21.22720);
        map.addMarker(new MarkerOptions().position(sydney).title("Marker in Timisoara"));
        map.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
