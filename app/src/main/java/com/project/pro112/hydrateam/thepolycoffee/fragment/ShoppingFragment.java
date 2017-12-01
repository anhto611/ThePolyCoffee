package com.project.pro112.hydrateam.thepolycoffee.fragment;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.project.pro112.hydrateam.thepolycoffee.R;
import com.project.pro112.hydrateam.thepolycoffee.activity.shopping.Order;

import java.io.IOException;
import java.util.List;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;

public class ShoppingFragment extends Fragment implements OnMapReadyCallback, PlaceSelectionListener {

    SupportMapFragment mapFragment;
    PlaceAutocompleteFragment autocompleteFragment;
    GoogleMap map;
    LocationManager locationManager;
    Button btnChooseAddress;
    View mapView;
    ImageView locationButton;
    ProgressBar progressBarLoadMarker;
    Marker marker;
    Geocoder geocoder;

    int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;

    public ShoppingFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shopping, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mapFragment = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapShopping));
        mapView = mapFragment.getView();
        mapFragment.getMapAsync(this);

        geocoder = new Geocoder(getActivity().getApplicationContext());

        progressBarLoadMarker = getView().findViewById(R.id.progressLoadMarker);
        btnChooseAddress = getView().findViewById(R.id.btnChooseAddress);

        setEventButton();

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        LatLng latLng = new LatLng(10.790845, 106.682393);

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        if (mapView != null &&
                mapView.findViewById(Integer.parseInt("1")) != null) {
            // Get the button view
            locationButton = ((View) mapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
            locationButton.setImageDrawable(getResources().getDrawable(R.mipmap.ic_mylocation));
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//bang M
            if (getActivity().checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                    && getActivity().checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                xuLyMap();
            } else {
                ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
            }
        } else {
            xuLyMap();
        }
        UiSettings uisetting = map.getUiSettings();

        uisetting.setCompassEnabled(true);
        uisetting.setScrollGesturesEnabled(true);
        uisetting.setTiltGesturesEnabled(true);
        uisetting.setMyLocationButtonEnabled(true);

        map.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @SuppressLint("MissingPermission")
            @Override
            public boolean onMyLocationButtonClick() {
                progressBarLoadMarker.setVisibility(View.VISIBLE);
                if (marker != null) marker.remove();
                locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

                if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 2000, 10, new LocationListener() {
                        @Override
                        public void onLocationChanged(Location location) {
                            double latitude = location.getLatitude();
                            double longitude = location.getLongitude();
                            LatLng latLng = new LatLng(latitude, longitude);
                            try {
                                List<Address> addressList = geocoder.getFromLocation(latitude, longitude, 1);
                                //Get Address
                                String address = addressList.get(0).getAddressLine(0);
                                marker = map.addMarker(new MarkerOptions().position(latLng).title(address));
                                progressBarLoadMarker.setVisibility(View.INVISIBLE);
                                btnChooseAddress.setText(R.string.shopping_order);
                                setEventButton();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onStatusChanged(String provider, int status, Bundle extras) {

                        }

                        @Override
                        public void onProviderEnabled(String provider) {

                        }

                        @Override
                        public void onProviderDisabled(String provider) {

                        }
                    });
                } else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 10, new LocationListener() {
                        @Override
                        public void onLocationChanged(Location location) {
                            double latitude = location.getLatitude();
                            double longitude = location.getLongitude();
                            LatLng latLng = new LatLng(latitude, longitude);
                            try {
                                List<Address> addressList = geocoder.getFromLocation(latitude, longitude, 1);
                                //Get Address
                                String address = addressList.get(0).getAddressLine(0);
                                marker = map.addMarker(new MarkerOptions().position(latLng).title(address));
                                progressBarLoadMarker.setVisibility(View.INVISIBLE);
                                btnChooseAddress.setText(R.string.shopping_order);
                                setEventButton();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onStatusChanged(String provider, int status, Bundle extras) {

                        }

                        @Override
                        public void onProviderEnabled(String provider) {

                        }

                        @Override
                        public void onProviderDisabled(String provider) {

                        }
                    });
                }
                return false;
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            xuLyMap();
        }
    }

    @SuppressLint("MissingPermission")
    public void xuLyMap() {
        map.setMyLocationEnabled(true);
    }

    public void setEventButton() {
        String textBtnSearchLocation = btnChooseAddress.getText().toString();
        if(isAdded() && getActivity() !=null){
            if (textBtnSearchLocation.equals(getResources().getString(R.string.shopping_choose_your_address))) {
                btnChooseAddress.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        findPlace();
                    }
                });
            } else {
                btnChooseAddress.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), Order.class);
                        startActivity(intent);
                    }
                });
            }
        }
    }

    public void findPlace() {
        AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
                .setTypeFilter(AutocompleteFilter.TYPE_FILTER_ADDRESS)
                .setCountry("VN")
                .build();
        try {
            Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                    .setFilter(typeFilter)
                    .build(getActivity());
            startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
        } catch (GooglePlayServicesRepairableException e) {
            // TODO: Handle the error.
        } catch (GooglePlayServicesNotAvailableException e) {
            // TODO: Handle the error.
        }
    }


    // A place has been received; use requestCode to track the request.
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(getActivity(), data);
                Log.i(TAG, "Place: " + place.getName());
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(getActivity(), data);
                // TODO: Handle the error.
                Log.i(TAG, status.getStatusMessage());

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }

    @Override
    public void onPlaceSelected(Place place) {
        // remove old marker when add new marker
        if (marker != null) marker.remove();
        LatLng myLatLng = place.getLatLng();
        marker = map.addMarker(
                new MarkerOptions().position(myLatLng).title(String.valueOf(place.getName())));
        map.animateCamera(CameraUpdateFactory.newLatLng(myLatLng));
    }

    @Override
    public void onError(Status status) {

    }
}
