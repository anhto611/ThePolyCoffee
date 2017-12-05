package com.project.pro112.hydrateam.thepolycoffee.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import com.project.pro112.hydrateam.thepolycoffee.R;

/**
 * Created by VI on 05/12/2017.
 */

public class updateProductDialog extends Dialog implements View.OnClickListener{
    public Activity c;
    public Dialog d;
    public Button update, btnAdd,cancel;
    public TextInputEditText pName,pPrice,pDes;
    public ImageView foodImg;
    public updateProductDialog(@NonNull Activity context, int themeResId) {
        super(context, themeResId);
        this.c = context;
    }

    public updateProductDialog(@NonNull Activity context) {
        super(context);
        this.c = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_form_update_product);
        update = (Button) findViewById(R.id.btnUpdate);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        cancel = (Button) findViewById(R.id.btnCancel);
        pName = (TextInputEditText) findViewById(R.id.pName);
        pPrice = (TextInputEditText) findViewById(R.id.pPrice);
        pDes = (TextInputEditText) findViewById(R.id.pDes);
        foodImg = findViewById(R.id.foodImg);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCancel:
                dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }


}

