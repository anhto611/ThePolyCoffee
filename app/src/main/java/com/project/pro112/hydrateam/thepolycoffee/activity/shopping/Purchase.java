package com.project.pro112.hydrateam.thepolycoffee.activity.shopping;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.pro112.hydrateam.thepolycoffee.R;
import com.project.pro112.hydrateam.thepolycoffee.models.AddressLocation;
import com.project.pro112.hydrateam.thepolycoffee.models.OrderedFireBaseFood;
import com.project.pro112.hydrateam.thepolycoffee.models.OrderedFood;
import com.project.pro112.hydrateam.thepolycoffee.models.UserRank;
import com.project.pro112.hydrateam.thepolycoffee.tempdatabase.TempDBLocation;
import com.project.pro112.hydrateam.thepolycoffee.tempdatabase.tempdatabase;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.project.pro112.hydrateam.thepolycoffee.activity.shopping.Order.linearButtonViewCart;

public class Purchase extends AppCompatActivity implements View.OnClickListener, OnMapReadyCallback {
    private ScrollView scrollView;
    private Button order;
    private GoogleMap mMap;
    private TextView total;
    private TextView txtaddressOrder;
    private TextView txtnameOrder;
    private TextView txtphoneOrder;
    private boolean isPushDataDone;
    private Double totalp;

    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference mReference;
    private String user_id;

    private TempDBLocation tempDBLocation;
    private ArrayList<AddressLocation> listLocations;

    double latitude;
    double longitude;
    String address;

    String nameUser = "";
    String phoneUser = "";

    String nameRank = null;
    double totalMonney = 0;
    int numofStart = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase);
        initView();
        setUpToolbar();
        //như bên cart
        setUpHideButtonWhenSrollToTheBottom();
        setTotal();
        setUpMaps();
//        Toast.makeText(this, "User id: "+getUserId()+"", Toast.LENGTH_SHORT).show();
//        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
//        Toast.makeText(this, "Date: "+currentDateTimeString, Toast.LENGTH_SHORT).show();

        latitude = addressOrder().get(0).getLatitude();
        longitude = addressOrder().get(0).getLongitude();
        address = addressOrder().get(0).getAddress();

        txtaddressOrder.setText(address);

        mAuth = FirebaseAuth.getInstance();
        user_id = mAuth.getCurrentUser().getUid();
        database = FirebaseDatabase.getInstance();
        mReference = database.getReference().child("Users").child(user_id);
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                nameUser = (String) dataSnapshot.child("fullName").getValue();
                phoneUser = (String) dataSnapshot.child("contactNumber").getValue();
                txtnameOrder.setText(nameUser);
                txtphoneOrder.setText(phoneUser);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mReference = database.getReference().child("UserRank").child(user_id);
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserRank userRank = dataSnapshot.getValue(UserRank.class);
                nameRank = userRank.getNameRank();
                totalMonney = userRank.getTotalMoney();
                numofStart = userRank.getNumOfSrart();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private String getUserId() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid;
        if (user != null) {
            // User is signed in
            uid = user.getUid();
        } else {
            // No user is signed in
            uid = null;
        }
        return "" + uid;
    }

    //init
    private void initView() {
        tempDBLocation = new TempDBLocation(this);

        order = (Button) findViewById(R.id.btnS);
        order.setText("Order");
        order.setOnClickListener(this);
        scrollView = (ScrollView) findViewById(R.id.scrollView);
        total = (TextView) findViewById(R.id.total);
        txtaddressOrder = (TextView) findViewById(R.id.addressOrder);
        txtnameOrder = (TextView) findViewById(R.id.nameOrder);
        txtphoneOrder = (TextView) findViewById(R.id.phoneOrder);
        isPushDataDone = false;
    }

    public void setTotal() {
        LinearLayout linearLayout = linearButtonViewCart;
        TextView tv = (TextView) linearLayout.getChildAt(0);
        totalp = Double.parseDouble(tv.getText().toString().substring(0, tv.getText().toString().length() - 1));
        if (totalp > 200000) {

        } else {
            totalp = totalp + 10000;
        }
        DecimalFormat formatter = new DecimalFormat("#.#");
        total.setText(formatter.format(totalp) + "đ");
    }

    //Setup tool bar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    private void setUpToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView txtTitle = (TextView) findViewById(R.id.tvTitleToolbar);
        toolbar.setTitle("");
        txtTitle.setText("Information and purchase");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void setUpHideButtonWhenSrollToTheBottom() {
        scrollView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                View child = scrollView.getChildAt(0);
                if (child != null) {
                    int childHeight = child.getHeight();
                    if (scrollView.getHeight() < childHeight) {
                        scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
                            @Override
                            public void onScrollChanged() {
                                if (scrollView != null) {
                                    if (scrollView.getChildAt(0).getBottom() == (scrollView.getHeight() + scrollView.getScrollY())) {
                                        order.setVisibility(View.INVISIBLE);
                                    } else {
                                        order.setVisibility(View.VISIBLE);
                                    }
                                }
                            }
                        });
                    } else {
                        order.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
    }

    // click vào nút order
    @Override
    public void onClick(View v) {
        tempdatabase tempdatabase = new tempdatabase(getBaseContext());
        PushDataToFireBase();
        if (isPushDataDone && tempdatabase.getOrderedFoods().size() == 0) {
            Toast.makeText(this, "Đã xảy ra lỗi, mua hàng thất bại!", Toast.LENGTH_SHORT).show();
        } else if (phoneUser.equals("")) {
            Toast.makeText(this, "Vui lòng cập nhập số điện thoại trước khi đặt hàng!", Toast.LENGTH_SHORT).show();
            showDialogUpdatePhone();
        } else {
            tempdatabase.deleteAlldata();
            setUserRank();
            Toast.makeText(this, "Mua hàng hành công!", Toast.LENGTH_SHORT).show();
        }
    }

    //Set up maps
    public void setUpMaps() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.maps);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker, and move the camera.
        LatLng sydney = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions().position(sydney).title(address));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 15));
    }

    private void PushDataToFireBase() {
        tempdatabase tempdatabase = new tempdatabase(getBaseContext());
        ArrayList<OrderedFood> orderedFoods = tempdatabase.getOrderedFoods();
        if (getUserId() != null && orderedFoods.size() > 0) {
            // get firebase instance
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef;
            myRef = database.getReference();
            myRef.child("Orders").child("" + getUserId());
            Map myMapFoods = new HashMap();
            if (orderedFoods.size() >= 0) {
                for (int i = 0; i < orderedFoods.size(); i++) {
                    OrderedFireBaseFood food = new OrderedFireBaseFood(orderedFoods.get(i).getDiscription() + "",
                            "" + orderedFoods.get(i).getImage(),
                            "" + orderedFoods.get(i).getName(),
                            orderedFoods.get(i).getPrice(),
                            orderedFoods.get(i).getAmount());
                    myMapFoods.put("Food" + (i + 1), food);
                }
            } else {
                Toast.makeText(this, "Vui lòng chọn món!", Toast.LENGTH_SHORT).show();
            }

            Map mParent = new HashMap();
            mParent.put("Date", "" + getCurrentTime());
            mParent.put("Total", totalp);
            mParent.put("Foods", myMapFoods);
            myRef.child("Orders").child("" + getUserId()).push().setValue(mParent);
        } else {
            Toast.makeText(this, "Vui lòng chọn ít nhất 1 món!", Toast.LENGTH_SHORT).show();
        }
        isPushDataDone = true;
    }

    private String getCurrentTime() {
        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
        return currentDateTimeString;
    }

    private ArrayList<AddressLocation> addressOrder() {
        listLocations = new ArrayList<>();
        listLocations = tempDBLocation.getAllLocation();
        return listLocations;
    }

    private void showDialogUpdatePhone() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(Purchase.this).inflate(R.layout.dialog_update_phone_number, null);
        builder.setView(view);
        builder.setCancelable(false);
        final AlertDialog alertDialog = builder.create();
        final EditText edtInputPhone = (EditText) view.findViewById(R.id.inputPhoneOrder);
        Button btnCancel = (Button) view.findViewById(R.id.updatePhoneCancel);
        Button btnUpdate = (Button) view.findViewById(R.id.updatePhoneYes);


        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                reference.child("Users").child(mUser.getUid()).child("contactNumber").setValue(edtInputPhone.getText().toString());
                Toast.makeText(Purchase.this, "Successful phone number updates", Toast.LENGTH_SHORT).show();
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }

    private void setUserRank() {
        double totalMonney;
        int numofStart;
        totalMonney = this.totalMonney += totalp;
        numofStart = (int) (Math.floor(totalMonney / 10000));
        if (numofStart >= 0 && numofStart < 1000) {
            nameRank = "New member";
        } else if (numofStart >= 1000 && numofStart < 3000) {
            nameRank = "Gold";
        } else if (numofStart >= 3000) {
            nameRank = "Diamond";
        }
        mReference = database.getReference().child("UserRank").child(user_id);
        mReference.setValue(new UserRank(totalMonney, numofStart, nameRank));
    }
}
