package com.falconssoft.woodysystem;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.falconssoft.woodysystem.models.Settings;
import com.falconssoft.woodysystem.models.Users;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout linearLayout;
    private EditText username, password, ipAddress, companyName;
    private Button login, saveSettings;
    private ImageView logoImage, settings;
    private DatabaseHandler databaseHandler;
    private List<Users> usersList = new ArrayList<>();
    private final int IMAGE_CODE = 5;
    private Animation animation;
    private Spinner storesSpinner;
    private List<String> storesList = new ArrayList<>();
    private ArrayAdapter<String> storesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        databaseHandler = new DatabaseHandler(this);
        databaseHandler.getSettings();

        username = findViewById(R.id.login_username);
        password = findViewById(R.id.login_password);
        logoImage = findViewById(R.id.login_logo);
        login = findViewById(R.id.login_login_btn);
        settings = findViewById(R.id.login_settings);
        linearLayout = findViewById(R.id.login_linearLayout);

        login.setOnClickListener(this);
        logoImage.setOnClickListener(this);
        settings.setOnClickListener(this);

        animation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.move_to_right);
        login.startAnimation(animation);

        animation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.bounce);
        linearLayout.startAnimation(animation);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_login_btn:
                String usernameText = username.getText().toString();
                String passwordText = password.getText().toString();

                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                setSlideAnimation();

//                if (!usernameText.equals("") || !usernameText.equals(null)){
//                    if (!passwordText.equals("") || !passwordText.equals(null)){
//                        usersList = databaseHandler.getUsers();
//                        for (int i = 0; i<usersList.size(); i++)
//                            if (usernameText.equals(usersList.get(i).getUsername())
//                                    && passwordText.equals(usersList.get(i).getPassword())){
//                                i = usersList.size();
//                                Intent intent = new Intent(this, MainActivity.class);
//                                startActivity(intent);
//                            }
//                    }
//                }

                break;
            case R.id.login_logo:
                Intent getImage = new Intent(Intent.ACTION_PICK);
                getImage.setType("image/*");
                startActivityForResult(getImage, IMAGE_CODE);
                break;
            case R.id.login_settings:
                Settings settings = new Settings();
                Dialog settingDialog = new Dialog(this);
                settingDialog.setContentView(R.layout.settings_dialog_layout);
                settingDialog.setTitle("Settings");
                companyName = settingDialog.findViewById(R.id.settings_company_name);
                ipAddress = settingDialog.findViewById(R.id.settings_ipAddress);
                storesSpinner = settingDialog.findViewById(R.id.settings_stores);
                saveSettings = settingDialog.findViewById(R.id.settings_save);

                storesList.add("Amman");
                storesList.add("Kalinovka");
                storesList.add("Rudniya Store");
                storesList.add("Rudniya Sawmill");
                storesAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, storesList);
                storesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                storesSpinner.setAdapter(storesAdapter);

                companyName.setText(SettingsFile.companyName);
                ipAddress.setText(SettingsFile.ipAddress);
                switch (SettingsFile.store) {
                    case "Amman":
                        storesSpinner.setSelection(0);
                        break;
                    case "Kalinovka":
                        storesSpinner.setSelection(1);
                        break;
                    case "Rudniya Store":
                        storesSpinner.setSelection(2);
                        break;
                    case "Rudniya Sawmill":
                        storesSpinner.setSelection(3);
                        break;
                    default:
                        storesSpinner.setSelection(0);
                }

                storesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        settings.setStore(parent.getItemAtPosition(position).toString());
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        settings.setStore("Amman");
                    }
                });

                saveSettings.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!TextUtils.isEmpty(companyName.getText().toString())) {
                            if (!TextUtils.isEmpty(ipAddress.getText().toString())) {
                                settings.setCompanyName(companyName.getText().toString());
                                settings.setIpAddress(ipAddress.getText().toString());
                                databaseHandler.deleteSettings();
                                databaseHandler.addSettings(settings);
                                Toast.makeText(LoginActivity.this, "Saved Successfully", Toast.LENGTH_SHORT).show();
                                settingDialog.dismiss();
                            } else {
                                ipAddress.setError("Required");
                            }
                        } else {
                            companyName.setError("Required");
                        }
                    }
                });
                settingDialog.show();
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            Uri selectedImage = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                logoImage.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setSlideAnimation() {
        overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
    }


}
