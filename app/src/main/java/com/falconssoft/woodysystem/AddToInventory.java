package com.falconssoft.woodysystem;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.falconssoft.woodysystem.models.BundleInfo;
import com.falconssoft.woodysystem.models.Settings;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AddToInventory extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private static final int WHITE = 0xFFFFFFFF;
    private static final int BLACK = 0xFF000000;
    private EditText thickness, length, width, noOfPieces, serialNo, backingList;
    private Spinner gradeSpinner, areaSpinner, descriptionSpinner;//, locationSpinner
    private TableLayout bundlesTable;
    private LinearLayout linearLayoutView;
    private TextView textView, addToInventory;
    private BundleInfo newBundle;
    private ArrayList<BundleInfo> bundleInfoList;
    private DatabaseHandler databaseHandler;
    private Animation animation;
    private List<String> gradeList = new ArrayList<>();
    //    private List<String> locationList = new ArrayList<>();
    private List<String> areaList = new ArrayList<>();
    private List<String> descriptionList = new ArrayList<>();
    private ArrayAdapter<String> gradeAdapter, areaAdapter, descriptionaAdapter;//, locationAdapter
    private String gradeText = "Fresh", areaText = "Zone 1", descriptionText = "Bundle Origin";//, locationText = "Loc 1"
    private JSONArray jsonArrayBundles;
    private String bundleNoString;
    private boolean mState = false;
    private final String STATE_VISIBILITY = "state-visibility";
    //    private WoodPresenter presenter;
    private Settings generalSettings;
    String bundleNumber;
    private TableRow publicTableRow = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_inventory);

        databaseHandler = new DatabaseHandler(this);
//        presenter = new WoodPresenter(this);
        generalSettings = new Settings();
        generalSettings = databaseHandler.getSettings();
        bundleInfoList = new ArrayList<>();

        thickness = findViewById(R.id.addToInventory_thickness);
        length = findViewById(R.id.addToInventory_length);
        width = findViewById(R.id.addToInventory_width);
        noOfPieces = findViewById(R.id.addToInventory_no_of_pieces);
        gradeSpinner = findViewById(R.id.addToInventory_grade);
        serialNo = findViewById(R.id.addToInventory_serial_no);
        backingList = findViewById(R.id.addToInventory_backing_list);
//        locationSpinner = findViewById(R.id.addToInventory_location);
        areaSpinner = findViewById(R.id.addToInventory_area);
        descriptionSpinner = findViewById(R.id.addToInventory_description);
        addToInventory = findViewById(R.id.addToInventory_add_button);
//        newBundleButton = findViewById(R.id.addToInventory_new_button);
        textView = findViewById(R.id.addToInventory_textView);
        bundlesTable = findViewById(R.id.addToInventory_table);
        linearLayoutView = findViewById(R.id.addToInventory_table_view);
        linearLayoutView.setVisibility(View.GONE);

        addToInventory.setOnClickListener(this);

        gradeList.add("Fresh");
        gradeList.add("BS");
        gradeList.add("Reject");
        gradeList.add("KD");
        gradeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, gradeList);
        gradeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gradeSpinner.setAdapter(gradeAdapter);
        gradeSpinner.setOnItemSelectedListener(this);

//        locationList.add("Amman");
//        locationList.add("Kalinovka");
//        locationList.add("Rudniya Store");
//        locationList.add("Rudniya Sawmill");
//        locationAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, locationList);
//        locationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        locationSpinner.setAdapter(locationAdapter);
//        locationSpinner.setOnItemSelectedListener(this);

        areaList.add("Zone 1");
        areaList.add("Zone 2");
        areaList.add("Zone 3");
        areaAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, areaList);
        areaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        areaSpinner.setAdapter(areaAdapter);
        areaSpinner.setOnItemSelectedListener(this);

        descriptionList.add("Bundle Origin");
        descriptionList.add("Finland Wood");
        descriptionList.add("Decore Wood");
        descriptionList.add("German Wood");
        descriptionList.add("Romanian Wood");
        descriptionList.add("Russian Wood");
        descriptionList.add("Ukrainian Wood");
        descriptionList.add("Canadian Wood");
        descriptionList.add("Swedian Wood");
        descriptionList.add("Latvian Wood");
        descriptionaAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, descriptionList);
        descriptionaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        descriptionSpinner.setAdapter(descriptionaAdapter);
        descriptionSpinner.setOnItemSelectedListener(this);

        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.move_to_right);
        textView.startAnimation(animation);

//        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
//        addToInventory.startAnimation(animation);
    }

    @Override
    public void onClick(View v) {
        String serialNoText = serialNo.getText().toString();
        String thicknessText = thickness.getText().toString();
        String lengthText = length.getText().toString();
        String widthText = width.getText().toString();
        String noOfPiecesText = noOfPieces.getText().toString();
        String backingListText = backingList.getText().toString();

//         String bundleNoString;,
        switch (v.getId()) {
            case R.id.addToInventory_add_button:
//                boolean foundBarcode = false;
//http://5.189.130.98:8085/import.php?FLAG=1.
                if (!TextUtils.isEmpty(serialNo.getText().toString())) {
                    if (!TextUtils.isEmpty(thickness.getText().toString())) {
                        if (!TextUtils.isEmpty(width.getText().toString())) {
                            if (!TextUtils.isEmpty(length.getText().toString())) {
                                if (!TextUtils.isEmpty(noOfPieces.getText().toString())) {
//                                    if (!TextUtils.isEmpty(backingList.getText().toString())) {
//                                    String serial = presenter.getSerialNo();
//                                    Log.e("serial", " " + !serial.equals(""));
//                                    Log.e("serial", " " + !serial.equals(null));
//                                    Log.e("serialNumber", "" + serial);
//                                    if ((!serial.equals("")) && (!serial.equals(null))) { //(!SettingsFile.serialNumber.equals("")) && (!SettingsFile.serialNumber.equals(null))
                                        String locationString = null, gradeString = null, detailString = null;
                                        switch (gradeText) {
                                            case "Fresh":
                                                gradeString = "FR";
                                                break;
                                            case "BS":
                                                gradeString = "BS";
                                                break;
                                            case "Reject":
                                                gradeString = "RJ";
                                                break;
                                            case "KD":
                                                gradeString = "KD";
                                                break;
                                        }

                                        switch (generalSettings.getStore()) {
                                            case "Amman":
                                                locationString = "Amm";
                                                break;
                                            case "Kalinovka":
                                                locationString = "Kal";
                                                break;
                                            case "Rudniya Store":
                                                locationString = "Rud";
                                                break;
                                            case "Rudniya Sawmill":
                                                locationString = "RLP";
                                                break;
                                        }

                                        switch (descriptionText) {
                                            case "Bundle Origin":
                                                detailString = "Origin";
                                                break;
                                            case "Finland Wood":
                                                detailString = "FIN";
                                                break;
                                            case "Decore Wood":
                                                detailString = "Decore";
                                                break;
                                            case "German Wood":
                                                detailString = "DEU";
                                                break;
                                            case "Romanian Wood":
                                                detailString = "ROU";
                                                break;
                                            case "Russian Wood":
                                                detailString = "RUS";
                                                break;
                                            case "Ukrainian Wood":
                                                detailString = "UKR";
                                                break;
                                            case "Canadian Wood":
                                                detailString = "CAN";
                                                break;
                                            case "Swedian Wood":
                                                detailString = "SWE";
                                                break;
                                            case "Latvian Wood":
                                                detailString = "LVA";
                                                break;
                                        }
//                                    Log.e("serial" , " " + SettingsFile.serialNumber);

                                        bundleNoString = "" + gradeString
                                                + locationString
                                                + thicknessText
                                                + "." + widthText
                                                + "." + lengthText
                                                + "." + noOfPiecesText
                                                + "." + serialNoText;//presenter.getSerialNo();//SettingsFile.serialNumber

//                                    List<String> checkBarcodeList = databaseHandler.getBundleNo();
//                                    for (int m = 0; m < checkBarcodeList.size(); m++)
//                                        if (bundleNoString.equals(checkBarcodeList.get(m))) {
//                                            foundBarcode = true;
//                                            break;
//                                        }

//                                    if (!foundBarcode) {
                                        jsonArrayBundles = new JSONArray();
                                        if (bundlesTable.getChildCount() == 0) {
                                            linearLayoutView.setVisibility(View.VISIBLE);
                                            mState = true;
                                            addTableHeader();
                                        }

                                        Date date = Calendar.getInstance().getTime();
                                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                                        String generateDate = simpleDateFormat.format(date);

                                        Log.e("addToInventory/", "serial/" + serialNoText);
                                        newBundle = new BundleInfo(Double.parseDouble(thicknessText)
                                                , Double.parseDouble(lengthText)
                                                , Double.parseDouble(widthText)
                                                , gradeText
                                                , Integer.parseInt(noOfPiecesText)
                                                , bundleNoString
                                                , generalSettings.getStore()
                                                , areaText
                                                , generateDate
                                                , 0
                                                , descriptionText
                                                , serialNoText
                                                , generalSettings.getUserNo()
                                                , backingListText);//presenter.getSerialNo());//SettingsFile.serialNumber

                                        bundleInfoList.add(newBundle);
//                                    Log.e("date is", generateDate);

                                        TableRow tableRow = new TableRow(this);
                                        for (int i = 0; i < 10; i++) {
                                            TextView textView = new TextView(this);
                                            textView.setBackgroundResource(R.color.light_orange);
                                            TableRow.LayoutParams textViewParam = new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 1f);
                                            textViewParam.setMargins(1, 5, 1, 1);
                                            textView.setTextSize(15);
                                            textView.setTextColor(ContextCompat.getColor(this, R.color.gray_dark_one));
                                            textView.setLayoutParams(textViewParam);
                                            switch (i) {
                                                case 0:
                                                    TableRow.LayoutParams param = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
                                                    param.setMargins(1, 5, 1, 1);
                                                    textView.setLayoutParams(param);
                                                    textView.setText(bundleNoString);
                                                    break;
                                                case 1:
                                                    textView.setText(lengthText);
                                                    break;
                                                case 2:
                                                    textView.setText(widthText);
                                                    break;
                                                case 3:
                                                    textView.setText(thicknessText);
                                                    break;
                                                case 4:
                                                    textView.setText(gradeText);
                                                    break;
                                                case 5:
                                                    textView.setText(noOfPiecesText);
                                                    break;
                                                case 6:
                                                    textView.setText(generalSettings.getStore());
                                                    break;
                                                case 7:
                                                    textView.setText(areaText);
                                                    break;
                                                case 8:
                                                    textView.setText(detailString);
                                                    break;
                                                case 9:
                                                    textView.setText(backingListText);
                                                    break;
                                            }
                                            tableRow.addView(textView);
                                        }
                                        jsonArrayBundles.put(newBundle.getJSONObject());
                                        publicTableRow = tableRow;
//                                      bundlesTable.addView(tableRow);
                                        new JSONTask().execute();

                                        thickness.setText("");
                                        length.setText("");
                                        width.setText("");
                                        noOfPieces.setText("");
                                        serialNo.setText("");
                                        backingList.setText("");
//                                        locationSpinner.setSelection(0);
                                        areaSpinner.setSelection(0);
                                        gradeSpinner.setSelection(0);
                                        descriptionSpinner.setSelection(0);

                                        tableRow.setOnLongClickListener(new View.OnLongClickListener() {
                                            @Override
                                            public boolean onLongClick(View v) {
//                                                TextView textView = ((TextView) tableRow.getChildAt(0));
//                                                tableRow.setBackgroundResource(R.color.light_orange_2);
                                                String bundleNo = ((TextView) tableRow.getChildAt(0)).getText().toString();
                                                Log.e("b", bundleNo);
                                                AlertDialog.Builder builder = new AlertDialog.Builder(AddToInventory.this);
                                                builder.setMessage("Are you want delete bundle number: " + bundleNo + " ?");
                                                builder.setTitle("Delete");
                                                builder.setIcon(R.drawable.ic_warning_black_24dp);
                                                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        bundlesTable.removeView(tableRow);
                                                        bundleNumber = bundleNo;
                                                        new JSONTask2().execute();
                                                    }
                                                });
                                                builder.show();
                                                return false;
                                            }
                                        });
                                        serialNo.requestFocus();
//                                            presenter.setSerialNo("");
//                                        SettingsFile.serialNumber = "";
//                                    } else {
//                                        Toast.makeText(this, "Barcode already exist", Toast.LENGTH_SHORT).show();
//                                    }
//                                    Log.e("serial" , " " + SettingsFile.serialNumber);

//                                    presenter.getImportData();
//                                    } else {
////                                        presenter.getImportData();
//                                        Toast.makeText(this, "No serial number is generated!", Toast.LENGTH_SHORT).show();
//                                    }
//                                    } else {
//                                        backingList.setError("Required!");
//                                    }
                                } else {
                                    noOfPieces.setError("Required!");
                                }
                            } else {
                                length.setError("Required!");
                            }
                        } else {
                            width.setError("Required!");
                        }
                    } else {
                        thickness.setError("Required!");
                    }
                } else {
                    serialNo.setError("Required!");
                }
                break;

        }
    }

    void addTableHeader() {
        TableRow tableRow = new TableRow(this);
        for (int i = 0; i < 10; i++) {
            TextView textView = new TextView(this);
            textView.setBackgroundResource(R.color.orange);
            TableRow.LayoutParams textViewParam = new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 1f);
//            textViewParam.setMargins(1, 5, 1, 1);
            textView.setTextSize(15);
            textView.setTextColor(ContextCompat.getColor(this, R.color.white));
            textView.setLayoutParams(textViewParam);
            switch (i) {
                case 0:
                    TableRow.LayoutParams param = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
//                    param.setMargins(1, 5, 1, 1);
                    textView.setLayoutParams(param);
                    textView.setText("Bundle#");
                    break;
                case 1:
                    textView.setText("Length");
                    break;
                case 2:
                    textView.setText("Width");
                    break;
                case 3:
                    textView.setText("Thic");
                    break;
                case 4:
                    textView.setText("Grade");
                    break;
                case 5:
                    textView.setText("Pieces");
                    break;
                case 6:
                    textView.setText("#Loc");
                    break;
                case 7:
                    textView.setText("Area");
                    break;
                case 8:
                    textView.setText("Details");
                    break;
                case 9:
                    textView.setText("B.List");
                    break;
            }
            tableRow.addView(textView);
        }
        bundlesTable.addView(tableRow);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
//        linearLayoutView.getVisibility();
        outState.putBoolean(STATE_VISIBILITY, mState);

        List<TableRow> tableRows = new ArrayList<>();
        int rowcount = bundlesTable.getChildCount();
        for (int i = 0; i < rowcount; i++) {
            TableRow row = (TableRow) bundlesTable.getChildAt(i);
            tableRows.add(row);
        }
        outState.putSerializable("table", (Serializable) tableRows);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {

        // Restore state members from saved instance
        mState = savedInstanceState.getBoolean(STATE_VISIBILITY);
        linearLayoutView.setVisibility(mState ? View.VISIBLE : View.GONE);
//        presenter.getImportData();
        List<TableRow> tableRows = (List<TableRow>) savedInstanceState.getSerializable("table");
        for (int i = 0; i < tableRows.size(); i++) {
            if (tableRows.get(i).getParent() != null) {
                ((ViewGroup) tableRows.get(i).getParent()).removeView(tableRows.get(i)); // <- fix
            }
            bundlesTable.addView(tableRows.get(i));
        }
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.addToInventory_grade:
                gradeText = parent.getItemAtPosition(position).toString();
                break;
//            case R.id.addToInventory_location:
//                locationText = parent.getItemAtPosition(position).toString();
//                break;
            case R.id.addToInventory_area:
                areaText = parent.getItemAtPosition(position).toString();
                break;
            case R.id.addToInventory_description:
                descriptionText = parent.getItemAtPosition(position).toString();
                break;
        }

//        Log.e("item", gradeText);
//        Log.e("item", locationText);
//        Log.e("item", areaText);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
//        gradeText = "Fresh";
//        locationText = "Loc 1";

    }

//    public void onBackPressed() {
//        super.onBackPressed();
//        Intent intent = new Intent(AddToInventory.this, Stage3.class);
//        startActivity(intent);
////        finish();
//    }

//    @Override
//    public void finish() {
//        super.finish();
////        setSlideAnimation();
//        overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
//    }

    private class JSONTask extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... params) {
            try {
//https://5.189.130.98/WOODY/export.php
                String JsonResponse = null;
                HttpClient client = new DefaultHttpClient();
                HttpPost request = new HttpPost();
                request.setURI(new URI("http://" + generalSettings.getIpAddress() + "/export.php"));//import 10.0.0.214

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
                Log.e("addToInventory/", "" + jsonArrayBundles.toString());
                nameValuePairs.add(new BasicNameValuePair("BUNDLE_INFO", jsonArrayBundles.toString().trim()));

                request.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                HttpResponse response = client.execute(request);

                BufferedReader in = new BufferedReader(new
                        InputStreamReader(response.getEntity().getContent()));

                StringBuffer sb = new StringBuffer("");
                String line = "";

                while ((line = in.readLine()) != null) {
                    sb.append(line);
                }

                in.close();

                JsonResponse = sb.toString();
                Log.e("tag", "" + JsonResponse);

                return JsonResponse;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s != null) {
                if (s.contains("BUNDLE_INFO SUCCESS")) {
//                    databaseHandler.addNewBundle(newBundle);
//                    presenter.getImportData();
                    Log.e("addToInventory", "" + publicTableRow);
                    bundlesTable.addView(publicTableRow);
                    Log.e("tag", "****Success");
                } else {
//                    presenter.setSerialNo("");
//                    SettingsFile.serialNumber = "";
                    Log.e("tag", "****Failed to export data");
                }
            } else {
//                presenter.setSerialNo("");
//                SettingsFile.serialNumber = "";
                Log.e("tag", "****Failed to export data Please check internet connection");
            }
        }
    }

    private class JSONTask2 extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... params) {
            try {
                String JsonResponse = null;
                HttpClient client = new DefaultHttpClient();
                HttpPost request = new HttpPost();
                request.setURI(new URI("http://" + generalSettings.getIpAddress() + "/export.php"));//import 10.0.0.214

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
                nameValuePairs.add(new BasicNameValuePair("DELETE_BUNDLE", "1"));
                nameValuePairs.add(new BasicNameValuePair("BUNDLE_NO", bundleNumber));

                request.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                HttpResponse response = client.execute(request);

                BufferedReader in = new BufferedReader(new
                        InputStreamReader(response.getEntity().getContent()));

                StringBuffer sb = new StringBuffer("");
                String line = "";

                while ((line = in.readLine()) != null) {
                    sb.append(line);
                }

                in.close();

                JsonResponse = sb.toString();
                Log.e("tag", "" + JsonResponse);

                return JsonResponse;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s != null) {
                if (s.contains("DELETE BUNDLE SUCCESS")) {
//                    databaseHandler.deleteBundle(bundleNumber);
                    Log.e("tag", "****Success");
                } else {
                    Log.e("tag", "****Failed to export data");
                }
            } else {
                Log.e("tag", "****Failed to export data Please check internet connection");
            }
        }
    }

}
