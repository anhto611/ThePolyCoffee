package com.project.pro112.hydrateam.thepolycoffee.fragment;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.project.pro112.hydrateam.thepolycoffee.R;
import com.project.pro112.hydrateam.thepolycoffee.activity.shopping.Order;
import com.project.pro112.hydrateam.thepolycoffee.models.AddressLocation;
import com.project.pro112.hydrateam.thepolycoffee.tempdatabase.TempDBLocation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;

public class ShoppingFragment extends Fragment implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private SupportMapFragment mapFragment;
    private GoogleApiClient googleApiClient;
    private GoogleMap map;
    private Marker marker;
    private Geocoder geocoder;

    Button btnChooseAddress;
    ImageButton btnSearchLocation;
    View mapView;
    ImageView locationButton;

    private String address;
    private double lati;
    private double longi;
    private String ID_ADDRESS_ORDER = "AddressOrder";

    private TempDBLocation tempDBLocation;
    private ArrayList<AddressLocation> listLocations;


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

        if (mapView != null &&
                mapView.findViewById(Integer.parseInt("1")) != null) {
            // Get the button view
            locationButton = ((View) mapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
            locationButton.setImageDrawable(getResources().getDrawable(R.mipmap.ic_mylocation));
        }

        btnChooseAddress = getView().findViewById(R.id.btnChooseAddress);
        btnSearchLocation = getView().findViewById(R.id.btnSearchLocation);

        btnSearchLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findPlace();
            }
        });

        setEventButton();

        tempDBLocation = new TempDBLocation(getActivity());
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        buildGoogleApiClient();
        map = googleMap;

        LatLng latLng = new LatLng(10.790845, 106.682393);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);

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
                getAddress();
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

    // A place has been received; use requestCode to track the request.
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(getActivity(), data);

                //Get latitude and longitude
                lati = place.getLatLng().latitude;
                longi = place.getLatLng().longitude;
                LatLng latLng = new LatLng(lati, longi);

                //Get Address
                address = place.getAddress().toString();
                if (marker != null) marker.remove();
                marker = map.addMarker(new MarkerOptions().position(latLng).title(address));
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                btnChooseAddress.setText(R.string.shopping_order);
                setEventButton();
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(getActivity(), data);
                // TODO: Handle the error.
                Log.i(TAG, status.getStatusMessage());

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }

    // Get Vị trí hiện tại
    @SuppressLint("MissingPermission")
    public void xuLyMap() {
        map.setMyLocationEnabled(true);
    }

    // Get String Địa chỉ hiện tại
    @SuppressLint("MissingPermission")
    public void getAddress() {
        Location location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        LatLng myLatLng = new LatLng(location.getLatitude(), location.getLongitude());
        CameraPosition cameraPosition = new CameraPosition.Builder().target(
                myLatLng)                   // Sets the center of the map to Mountain View
                .zoom(15)                   // Sets the zoom
                .bearing(0)                // Sets the orientation of the camera to east
                .tilt(0)                   // Sets the tilt of the camera
                .build();
        if (marker != null) marker.remove();
        try {
            //Get latitude and longitude
            lati = location.getLatitude();
            longi = location.getLongitude();
            List<Address> addressList = geocoder.getFromLocation(lati, longi, 1);

            //Get Address
            address = addressList.get(0).getAddressLine(0);
            marker = map.addMarker(new MarkerOptions().position(myLatLng).title(address));
            map.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            btnChooseAddress.setText(R.string.shopping_order);
            setEventButton();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
//
//        if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
//            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 2000, 10, new LocationListener() {
//                @Override
//                public void onLocationChanged(AddressLocation location) {
//                    double latitude = location.getLatitude();
//                    double longitude = location.getLongitude();
//                    LatLng latLng = new LatLng(latitude, longitude);
//                    try {
//                        List<Address> addressList = geocoder.getFromLocation(latitude, longitude, 1);
//                        //Get Address
//                        String address = addressList.get(0).getAddressLine(0);
//                        marker = map.addMarker(new MarkerOptions().position(latLng).title(address));
//                        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
//                        progressBarLoadMarker.setVisibility(View.INVISIBLE);
//                        btnChooseAddress.setText(R.string.shopping_order);
//                        setEventButton();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//
//                @Override
//                public void onStatusChanged(String provider, int status, Bundle extras) {
//
//                }
//
//                @Override
//                public void onProviderEnabled(String provider) {
//
//                }
//
//                @Override
//                public void onProviderDisabled(String provider) {
//
//                }
//            });
//        } else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
//            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 10, new LocationListener() {
//                @Override
//                public void onLocationChanged(AddressLocation location) {
//                    double latitude = location.getLatitude();
//                    double longitude = location.getLongitude();
//                    LatLng latLng = new LatLng(latitude, longitude);
//                    try {
//                        List<Address> addressList = geocoder.getFromLocation(latitude, longitude, 1);
//                        //Get Address
//                        String address = addressList.get(0).getAddressLine(0);
//                        marker = map.addMarker(new MarkerOptions().position(latLng).title(address));
//                        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
//                        progressBarLoadMarker.setVisibility(View.INVISIBLE);
//                        btnChooseAddress.setText(R.string.shopping_order);
//                        setEventButton();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//
//                @Override
//                public void onStatusChanged(String provider, int status, Bundle extras) {
//
//                }
//
//                @Override
//                public void onProviderEnabled(String provider) {
//
//                }
//
//                @Override
//                public void onProviderDisabled(String provider) {
//
//                }
//            });
//        }
    }

    private void setEventButton() {
        String textBtnSearchLocation = btnChooseAddress.getText().toString();
        if (isAdded() && getActivity() != null) {
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
                        listLocations = tempDBLocation.getLocation(ID_ADDRESS_ORDER);
                        if (listLocations.size() == 0) {
                            tempDBLocation.insertLocation(new AddressLocation(ID_ADDRESS_ORDER, address, lati, longi));
                        } else {
                            tempDBLocation.updateLocation(new AddressLocation(ID_ADDRESS_ORDER, address, lati, longi));
                        }
                        Toast.makeText(getActivity(), "" + address + "\n" + lati + "\n" + longi, Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                    }
                });
            }
        }
    }

    private void findPlace() {
        AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
                .setTypeFilter(AutocompleteFilter.TYPE_FILTER_NONE)
                .setCountry("VN")
                .build();
        try {
            Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY)
                    .setFilter(typeFilter)
                    .build(getActivity());
            startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
        } catch (GooglePlayServicesRepairableException e) {
            // TODO: Handle the error.
        } catch (GooglePlayServicesNotAvailableException e) {
            // TODO: Handle the error.
        }
    }

    private void buildGoogleApiClient() {
        googleApiClient = new GoogleApiClient.Builder(getActivity()).addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .build();
        googleApiClient.connect();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
