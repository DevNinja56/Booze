package com.my.booze.activities;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.material.snackbar.Snackbar;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.my.booze.R;
import com.my.booze.models.LocationHelper;
import com.my.booze.models.UserResponseModel;
import com.my.booze.network.GetDataService;
import com.my.booze.network.RetrofitClientInstance;
import com.my.booze.utilities.Utilities;
import com.xgc1986.ripplebutton.widget.RippleButton;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import okhttp3.internal.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    RippleButton btn_SaveAddress;
    RelativeLayout btn_Back;
    private FusedLocationProviderClient client;
    private Location myLocation;
    String locationFor;
    private LocationHelper userLocation;
    private String apiKey, address, selectedLocationAddress = "notSelected", key, shopName;
    private PlacesClient placesClient;
    private LatLng latlng1;
    private MarkerOptions markerOptions;
    private AutocompleteSupportFragment autocompleteSupportFragment1;
    private LatLng currentLatLng;
    private Snackbar snackbar;
    ImageView btn_currentLocationc, location_Pin;
    Boolean isMarker=false;
    RelativeLayout btn_currentLocation;
    TextView confirm_AddressText;
    GetDataService service;
    String user_Id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        btn_Back = findViewById(R.id.btn_Back);
        btn_SaveAddress = findViewById(R.id.btn_SaveAddress);
        location_Pin = findViewById(R.id.location_Pin);
        btn_currentLocation = findViewById(R.id.btn_currentLocation);
        confirm_AddressText = findViewById(R.id.confirm_AddressText);
        user_Id = Utilities.getString(MapsActivity.this, "user_Id");
        location_Pin.setVisibility(View.GONE);
        client = LocationServices.getFusedLocationProviderClient(MapsActivity.this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.google_maps);
        mapFragment.getMapAsync(this);

        btn_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_SaveAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveAddress();
            }
        });

        btn_currentLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLocation();
            }
        });

        client = LocationServices.getFusedLocationProviderClient(MapsActivity.this);
        apiKey = "AIzaSyAG3GwR9PI6pCePXZQDwergz4z99xWUt-Q";
        if (!Places.isInitialized()) {
            Places.initialize(MapsActivity.this, apiKey);
        }
        placesClient = Places.createClient(MapsActivity.this);
        autocompleteSupportFragment1 =
                (AutocompleteSupportFragment) getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment1);
        autocompleteSupportFragment1.setHint("Search Location");
        autocompleteSupportFragment1.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.LAT_LNG, Place.Field.NAME));
        autocompleteSupportFragment1.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                Log.i("mcdkckvnfv", "here");
                latlng1 = place.getLatLng();
                Log.i("mcdkckvnfv", latlng1.latitude+"\n"+latlng1.longitude);

                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng1, 150));
                userLocation = new LocationHelper(
                        latlng1.latitude,
                        latlng1.longitude
                );
//                carLocation = new LocationHelper(
//                        latlng1.longitude,
//                        latlng1.latitude
//                );
                isMarker = true;
                location_Pin.setVisibility(View.GONE);
                mMap.clear();
                mMap.addMarker(new MarkerOptions()
                        .position(latlng1)
                        .title("Car Location")
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_map_pin)));
                selectedLocationAddress = Utilities.getAddress(MapsActivity.this, latlng1.latitude, latlng1.longitude);
//                Log.i("fakharAddredsdsd", );
//                Log.i("fakharAddredsdsd", selectedLocationAddress);
                Log.i("fakharAddredsdsd", selectedLocationAddress);
                autocompleteSupportFragment1.setText(selectedLocationAddress);
                confirm_AddressText.setText(selectedLocationAddress);
//                carLocation = new LocationHelper(carLocation.getLatitude(), carLocation.getLongitude());
            }

            @Override
            public void onError(@NonNull Status status) {
            }
        });

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        getLocation();
        mMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                if(isMarker == true){
                    isMarker=false;
                    return;
                }
                location_Pin.setVisibility(View.VISIBLE);
//                    Toast.makeText(getActivity(), "here", Toast.LENGTH_SHORT).show();
                mMap.clear();
                LatLng center = mMap.getCameraPosition().target;
                selectedLocationAddress = Utilities.getAddress(MapsActivity.this, center.latitude, center.longitude);
                userLocation = new LocationHelper(center.latitude, center.longitude);
                autocompleteSupportFragment1.setText(selectedLocationAddress);
                confirm_AddressText.setText(selectedLocationAddress);
                Log.i("Home Fragment", "" + Utilities.getAddress(MapsActivity.this, center.latitude, center.longitude));
            }
        });
    }

    public void getLocation(){
        Dexter.withActivity(MapsActivity.this)
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
//                        buildGoogleApiClient();
                        if (ActivityCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                        mMap.setMyLocationEnabled(true);
                        mMap.getUiSettings().setMyLocationButtonEnabled(false);
                        client.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                            @Override
                            public void onComplete(@NonNull Task<Location> task) {
                                Location location = task.getResult();
                                if (location != null) {
                                    Log.i("car Location", "" + location.getLatitude());
                                    Log.i("car Location", "" + location.getLongitude());
                                    currentLatLng = new LatLng(location.getLatitude(), location.getLongitude());
                                    selectedLocationAddress = Utilities.getAddress(MapsActivity.this, location.getLatitude(), location.getLongitude());
                                    userLocation = new LocationHelper(location.getLatitude(), location.getLongitude());
                                    autocompleteSupportFragment1.setText(selectedLocationAddress);
                                    confirm_AddressText.setText(selectedLocationAddress);
//                                    mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLatLng));
//                                    mMap.animateCamera(CameraUpdateFactory.zoomTo(150));
                                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 150));
                                    location_Pin.setVisibility(View.GONE);
                                    mMap.clear();
                                    mMap.addMarker(new MarkerOptions()
                                            .position(currentLatLng)
                                            .title("Car Location")
                                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_map_pin)));
                                    isMarker = true;
                                }
                            }
                        });
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        Toast.makeText(MapsActivity.this, "You must on your location", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                    }
                }).check();
    }


    public void saveAddress() {

        String address = Utilities.getAddress(MapsActivity.this, userLocation.getLatitude(), userLocation.getLongitude());
        ProgressDialog progressDialog;
        progressDialog  = new ProgressDialog(MapsActivity.this);
        progressDialog.setTitle("Saving Location...");
        progressDialog.show();
        progressDialog.setCancelable(false);
        service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<UserResponseModel> call = service.saveLocation(user_Id, ""+userLocation.getLatitude(),
                ""+userLocation.getLongitude(), address);
        call.enqueue(new Callback<UserResponseModel>() {
            @Override
            public void onResponse(Call<UserResponseModel> call, Response<UserResponseModel> response) {
                assert response.body() != null;
                int status  = response.body().getStatus();
//                Log.i("checking_Data11", ""+response.body().getMsg());
//                Log.i("checking_Data11", ""+response.body().getStatus());
//                Log.i("checking_Data11", ""+status);
                if (status == 200){
                    Utilities.saveString(MapsActivity.this, "User_RecentAddress", ""+address);
                    Utilities.saveString(MapsActivity.this, "location_Added", "yes");
                    progressDialog.dismiss();
//                    Toast.makeText(MapsActivity.this, ""+response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MapsActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                else if (status == 400){
                    progressDialog.dismiss();
                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content).getRootView(),
                            "Something went wrong, please try again", Snackbar.LENGTH_LONG);
                    snackbar.setBackgroundTint(ContextCompat.getColor(MapsActivity.this, R.color.red));
                    snackbar.show();
//                    Toast.makeText(MapsActivity.this, "Something went wrong, please", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<UserResponseModel> call, Throwable t) {
//                progressDialog.dismiss();
//                Utilities.stopAnim();
                Log.i("checkingResponse", t.getMessage());
                Toast.makeText(MapsActivity.this, t.getMessage() + " Not Called", Toast.LENGTH_LONG).show();
            }
        });

    }

}