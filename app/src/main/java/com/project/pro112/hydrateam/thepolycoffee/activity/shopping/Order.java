
package com.project.pro112.hydrateam.thepolycoffee.activity.shopping;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.project.pro112.hydrateam.thepolycoffee.R;
import com.project.pro112.hydrateam.thepolycoffee.adapter.SimpleFragmentPagerAdapter;
import com.project.pro112.hydrateam.thepolycoffee.dialog.updateProductDialog;
import com.project.pro112.hydrateam.thepolycoffee.models.Food;
import com.project.pro112.hydrateam.thepolycoffee.models.OrderedFood;
import com.project.pro112.hydrateam.thepolycoffee.tempdatabase.tempdatabase;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Order extends AppCompatActivity implements ViewPager.OnPageChangeListener, View.OnClickListener {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private FragmentManager fragmentManager;
    public static LinearLayout linearButtonViewCart;
    private SimpleFragmentPagerAdapter adapter;
    private TextView price;
    private ProgressBar progressBar;
    private static final int REQUEST_CAMERA = 3;
    private static final int SELECT_FILE = 2;
    private ImageView foodImg;
    Uri imageHoldUri = null;
    private String linkImg = "";
    private int currentPage;
    private Activity activity;
    String hinh[] = {"https://firebasestorage.googleapis.com/v0/b/the-poly-coffe.appspot.com/o/populars%2FChilly%20juice.jpg?alt=media&token=3a0f2533-2e9c-4d18-9ce2-92969b02ff76"
            , "https://firebasestorage.googleapis.com/v0/b/the-poly-coffe.appspot.com/o/populars%2FChocolate%20cup.jpg?alt=media&token=8fce5e19-0d1e-41a0-a6ca-deae1a5a1b65",
            "https://firebasestorage.googleapis.com/v0/b/the-poly-coffe.appspot.com/o/populars%2FChocolate%20milk.jpg?alt=media&token=49c53562-3359-4827-8bd3-379d79e78329",
            "https://firebasestorage.googleapis.com/v0/b/the-poly-coffe.appspot.com/o/populars%2FGreen%20tea.jpg?alt=media&token=ab330591-392e-49c9-98fb-10d546443705",
            "https://firebasestorage.googleapis.com/v0/b/the-poly-coffe.appspot.com/o/populars%2FHoney%20lovely.jpg?alt=media&token=02af8fe4-55e4-41ef-b3f2-21ec795ee53c",
            "https://firebasestorage.googleapis.com/v0/b/the-poly-coffe.appspot.com/o/populars%2Flovely%20cake.jpg?alt=media&token=a64e6103-6581-4ee0-ab4e-524c06731185"};
    String name[] = {"Chilly juice",
            " Chocolate cup",
            "Chocolate cup",
            "Green tea",
            "Honey lovely",
            "lovely cake"};

    String des[] = {"Nutrition Facts Per Serving (23 g)",
            "Nutrition Facts Per Serving (43 g)",
            "Nutrition Facts Per Serving (53 g)",
            "Nutrition Facts Per Serving (112 g)",
            "Nutrition Facts Per Serving (25 g)",
            "Nutrition Facts Per Serving (16 g)"};
    String prices[] = {"34000",
            "34000",
            "24000",
            "25000",
            "32000",
            "25000"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        initView();
        setUpToolbar();
        setUpViewPager();
        updateViewcartButton();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpViewPager();
        updateViewcartButton();
    }
    //        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("Foods").child("Popular");
//        for (int i = 0; i < prices.length; i++) {
//            myRef.push().setValue(new Food(des[i],
//                    hinh[i], name[i], prices[i]));
//        }
//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                Food food = dataSnapshot  .child("1").getValue(Food.class);
//                Toast.makeText(Order.this, food.getImage()+"", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });

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
        txtTitle.setText("Order");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void setUpViewPager() {
        adapter =
                new SimpleFragmentPagerAdapter(this, getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        //app su kien chuyen page
        viewPager.setOnPageChangeListener(this);
        viewPager.setOffscreenPageLimit(0);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        //new interface kết với với fragment
        currentPage = position;
        if (linearButtonViewCart.getVisibility() == View.INVISIBLE)
            linearButtonViewCart.setVisibility(View.VISIBLE);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void initView() {
        activity = Order.this;
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        linearButtonViewCart = (LinearLayout) findViewById(R.id.btnViewCart);
        linearButtonViewCart.setOnClickListener(this);
        TextView btnS = (TextView) linearButtonViewCart.getChildAt(1);
        price = (TextView) linearButtonViewCart.getChildAt(0);
        if (getUserId().equals(getResources().getString(R.string.idadmin))) {
            btnS.setText("Add");
        } else {
            btnS.setText("View cart");
        }
    }

    // view cart click
    @Override
    public void onClick(View v) {
        if (getUserId().equals(getResources().getString(R.string.idadmin))) {
            linearButtonViewCart.setOnClickListener(this);
            TextView btnS = (TextView) linearButtonViewCart.getChildAt(1);
            btnS.setText("Add");
            switch (currentPage) {
                case 0:
                    AddProduct("Popular");
                    break;
                case 1:
                    AddProduct("Drinks");
                    break;
                case 2:
                    AddProduct("Cakes");
                    break;
            }
        } else {
            if (price.getVisibility() == View.INVISIBLE) {
                Toast.makeText(this, "Vui lòng chọn ít nhất một món", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(Order.this, Cart.class);
                startActivity(intent);
            }
        }
    }

    private void AddProduct(final String productName) {
        final String[] name = new String[1];
        final String[] des = new String[1];
        final double[] price = new double[1];
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference[] myRef = new DatabaseReference[1];
        final updateProductDialog updateProDuctDialog = new updateProductDialog(activity, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        updateProDuctDialog.show();
        updateProDuctDialog.update.setVisibility(View.GONE);
        foodImg = updateProDuctDialog.foodImg;
        //cancel dialog
        updateProDuctDialog.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProDuctDialog.dismiss();
            }
        });
        //click ta take aphoto
        updateProDuctDialog.foodImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profilePicSelection();
            }
        });


        //add product click
        updateProDuctDialog.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // them product
                name[0] = updateProDuctDialog.pName.getText().toString();
                des[0] = updateProDuctDialog.pDes.getText().toString();
                try {
                    price[0] = Double.parseDouble(updateProDuctDialog.pPrice.getText().toString());
                } catch (NumberFormatException e) {
                    Toast.makeText(Order.this, "Your price need to be a number!", Toast.LENGTH_SHORT).show();
                }
                if (name[0].equals("") || des[0].equals("")) {
                    Toast.makeText(Order.this, "There is something empty!", Toast.LENGTH_SHORT).show();
                } else {
                    switch (productName) {
                        case "Popular": {
                            myRef[0] = database.getReference("Foods/Popular");
                            break;
                        }
                        case "Drinks": {
                            myRef[0] = database.getReference("Foods/Drinks");
                            break;
                        }
                        case "Cakes": {
                            myRef[0] = database.getReference("Foods/Cakes");
                            break;
                        }
                    }
                    if (linkImg.equals("")) {
                        Toast.makeText(Order.this, "Please choose an image!", Toast.LENGTH_SHORT).show();
                    } else {
                        myRef[0].push().setValue(new Food(des[0] + "", "" + linkImg, name[0] + "", price[0] + ""));
                        Toast.makeText(Order.this, "Add product successfully!", Toast.LENGTH_SHORT).show();
                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);
                    }
                }
            }
        });
    }

    public void updateViewcartButton() {
        LinearLayout linearLayout = linearButtonViewCart;
        TextView price = (TextView) linearLayout.getChildAt(0);
        TextView sl = (TextView) linearLayout.getChildAt(2);
        TextView nameTv = (TextView) linearLayout.getChildAt(1);
        if (getUserId().equals(R.string.idadmin)) {
            if (sl.getVisibility() == View.VISIBLE) {
                sl.setVisibility(View.INVISIBLE);
            }
            if (price.getVisibility() == View.VISIBLE) {
                price.setVisibility(View.INVISIBLE);
            }
            nameTv.setText("Add");
        } else {
            ArrayList<OrderedFood> orderedFoods;
            double truePrice = 0;
            int trueAmount = 0;
            tempdatabase tempdatabase = new tempdatabase(getBaseContext());
            DecimalFormat formatter = new DecimalFormat("#.#");

            orderedFoods = tempdatabase.getOrderedFoods();
            for (int i = 0; i < orderedFoods.size(); i++) {
                truePrice = truePrice + (orderedFoods.get(i).getAmount() * orderedFoods.get(i).getPrice());
                trueAmount = trueAmount + orderedFoods.get(i).getAmount();
            }

            price.setText(formatter.format(truePrice) + "đ");
            sl.setText(trueAmount + "");
            if (sl.getVisibility() == View.INVISIBLE && truePrice > 0)
                sl.setVisibility(View.VISIBLE);
            if (price.getVisibility() == View.INVISIBLE && truePrice > 0)
                price.setVisibility(View.VISIBLE);

            if (sl.getVisibility() == View.VISIBLE && truePrice <= 0) {
                sl.setVisibility(View.INVISIBLE);
            }
            if (price.getVisibility() == View.VISIBLE && truePrice <= 0) {
                price.setVisibility(View.INVISIBLE);
            }
        }
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

    //Code click vao TextView Edit Photo:
    public void profilePicSelection() {
        //DISPLAY DIALOG TO CHOOSE CAMERA OR GALLERY
        final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(Order.this);
        builder.setTitle("Add Photo!");

        //SET ITEMS AND THERE LISTENERS
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    cameraIntent();
                } else if (items[item].equals("Choose from Library")) {
                    galleryIntent();
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    //Mo galary:
    private void galleryIntent() {
        //CHOOSE IMAGE FROM GALLERY
        Log.d("gola", "entered here");
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, SELECT_FILE);
    }

    //Mo Camera chup hinh:
    private void cameraIntent() {
        //CHOOSE CAMERA
        Log.d("gola", "entered here");
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    // ActivityResultIntent
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //SAVE URI FROM GALLERY
        if (requestCode == SELECT_FILE && resultCode == RESULT_OK) {
            Uri imageUri = data.getData();
            CropImage.activity(imageUri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1, 1)
                    .start(this);

        } else if (requestCode == REQUEST_CAMERA && resultCode == RESULT_OK) {
            //SAVE URI FROM CAMERA
            Uri imageUri = data.getData();
            CropImage.activity(imageUri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1, 1)
                    .start(this);
        }

        //image crop library code
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                foodImg.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.VISIBLE);
                imageHoldUri = result.getUri();
//                foodImg.setImageURI(imageHoldUri);
                //Submit Image:
                StorageReference mStorageRef;
                mStorageRef = FirebaseStorage.getInstance().getReference();
                String local = "";
                if (currentPage == 0) {
                    local = "populars";
                } else if (currentPage == 1) {
                    local = "drinks";
                } else {
                    local = "cakes";
                }
                StorageReference mStorage = mStorageRef.child("" + local).child(imageHoldUri.getLastPathSegment());
                mStorage.putFile(imageHoldUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        //Lấy link Avatar Từ Storage:
                        linkImg = String.valueOf(taskSnapshot.getDownloadUrl());
                        //KHI THAY DOI AVATAR LAP TUC THAY DOI DAI DIEN:
                        Picasso.with(Order.this).load(linkImg).into(foodImg);
                        foodImg.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.INVISIBLE);

                    }
                });

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

}
