package com.falconssoft.woodysystem.stage_one;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import com.falconssoft.woodysystem.AddToInventory;
import com.falconssoft.woodysystem.LoadingOrder;
import com.falconssoft.woodysystem.R;
import com.falconssoft.woodysystem.ReportsActivity;

public class StageOne extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout addRaw, acceptInfo, generateBarcode;
    private Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stage_one);
        addRaw = findViewById(R.id.stage1_new_raw);
        acceptInfo = findViewById(R.id.stage1_accept_info);
        generateBarcode = findViewById(R.id.stage1_generate_barcode);

        addRaw.setOnClickListener(this);
        acceptInfo.setOnClickListener(this);
        generateBarcode.setOnClickListener(this);

        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down);
        addRaw.setAnimation(animation);
        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down);
        acceptInfo.setAnimation(animation);
        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down);
        generateBarcode.setAnimation(animation);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.stage1_new_raw:
                Intent intent = new Intent(this, AddNewRaw.class);
                startActivity(intent);
//                setSlideAnimation();
                break;
            case R.id.stage1_accept_info:
                Intent intent2 = new Intent(this, AcceptRow.class);
                startActivity(intent2);
                break;
            case R.id.stage1_generate_barcode:
                break;
        }
    }
}