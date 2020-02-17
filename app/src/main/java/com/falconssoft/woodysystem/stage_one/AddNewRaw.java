package com.falconssoft.woodysystem.stage_one;

import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.falconssoft.woodysystem.R;
import com.falconssoft.woodysystem.WoodPresenter;
import com.falconssoft.woodysystem.models.NewRowInfo;
import com.falconssoft.woodysystem.models.Settings;
import com.falconssoft.woodysystem.models.SupplierInfo;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class AddNewRaw extends AppCompatActivity implements View.OnClickListener {

    private Settings generalSettings;
    private WoodPresenter presenter;
    private TextView addNewSupplier, searchSupplier, addButton, acceptRowButton, backAcceptRow;
    private EditText thickness, width, length, noOfPieces, noOfBundles, noOfRejected;
    private Spinner gradeSpinner;
    private ArrayList<String> gradeList = new ArrayList<>();
    private ArrayAdapter<String> gradeAdapter;
    public static List<SupplierInfo> supplierInfoList = new ArrayList<>();
    private List<NewRowInfo> newRowList = new ArrayList<>();
    private String gradeText = "Fresh";
    public static String supplierName = "";
    private LinearLayout headerLayout, acceptRowLayout;
    private Button doneAcceptRow;
    private TableLayout tableLayout;
    private TableRow tableRow;
    private Dialog searchDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_raw);

        generalSettings = new Settings();
        presenter = new WoodPresenter(this);
//        presenter.getsuppliersInfo();
        supplierInfoList.clear();
        SupplierInfo info = new SupplierInfo("1", "Fortune");
        supplierInfoList.add(info);
        SupplierInfo info1 = new SupplierInfo("2", "Dolya-krok");
        supplierInfoList.add(info1);
        SupplierInfo info2 = new SupplierInfo("3", "Artin");
        supplierInfoList.add(info2);
        SupplierInfo info3 = new SupplierInfo("4", "Ventura");
        supplierInfoList.add(info3);
        SupplierInfo info4 = new SupplierInfo("5", "Busk LMG");
        supplierInfoList.add(info4);

        addNewSupplier = findViewById(R.id.addNewRaw_add_supplier);
        searchSupplier = findViewById(R.id.addNewRaw_search_supplier);
        thickness = findViewById(R.id.addNewRaw_thickness);
        width = findViewById(R.id.addNewRaw_width);
        length = findViewById(R.id.addNewRaw_length);
        noOfPieces = findViewById(R.id.addNewRaw_no_of_pieces);
        noOfBundles = findViewById(R.id.addNewRaw_no_of_bundles);
        noOfRejected = findViewById(R.id.addNewRaw_no_of_rejected);
        gradeSpinner = findViewById(R.id.addNewRaw_grade);
        addButton = findViewById(R.id.addNewRaw_add_button);
        acceptRowButton = findViewById(R.id.addNewRaw_acceptRaw_button);
        headerLayout = findViewById(R.id.addNewRaw_linearLayoutHeader);
        acceptRowLayout = findViewById(R.id.addNewRaw_acceptRow_linear);
        doneAcceptRow = findViewById(R.id.addNewRaw_acceptRow_done);
        backAcceptRow = findViewById(R.id.addNewRaw_acceptRow_back);
        tableLayout = findViewById(R.id.addNewRaw_table);

        headerLayout.setVisibility(View.VISIBLE);
        acceptRowLayout.setVisibility(View.GONE);

        gradeList.add("Fresh");
        gradeList.add("KD");
        gradeList.add("Blue Stain");
        gradeList.add("KD Blue Stain");
        gradeList.add("AST");
        gradeList.add("AST Blue Stain");
        gradeList.add("Second Sort");
        gradeList.add("Reject");
        gradeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, gradeList);
        gradeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gradeSpinner.setAdapter(gradeAdapter);
        gradeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                switch (parent.getId()) {
//                    case R.id.addNewRaw_grade:
                gradeText = parent.getItemAtPosition(position).toString();
//                        break;
//                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        backAcceptRow.setOnClickListener(this);
        doneAcceptRow.setOnClickListener(this);
        acceptRowButton.setOnClickListener(this);
        addNewSupplier.setOnClickListener(this);
        searchSupplier.setOnClickListener(this);
        addButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addNewRaw_acceptRow_back:
                headerLayout.setVisibility(View.VISIBLE);
                acceptRowLayout.setVisibility(View.GONE);
                break;
            case R.id.addNewRaw_acceptRow_done:
                break;
            case R.id.addNewRaw_acceptRaw_button:
                headerLayout.setVisibility(View.GONE);
                acceptRowLayout.setVisibility(View.VISIBLE);
                break;
            case R.id.addNewRaw_add_button:
                String thicknessLocal = thickness.getText().toString();
                String widthLocal = width.getText().toString();
                String lengthLocal = length.getText().toString();
                String noOfPiecesLocal = noOfPieces.getText().toString();
                String noOfRejectedLocal = noOfRejected.getText().toString();
                String noOfBundlesLocal = noOfBundles.getText().toString();

                if (!TextUtils.isEmpty(supplierName)) {
                    if (!TextUtils.isEmpty(thicknessLocal))
                        if (!TextUtils.isEmpty(widthLocal))
                            if (!TextUtils.isEmpty(lengthLocal))
                                if (!TextUtils.isEmpty(noOfPiecesLocal))
                                    if (!TextUtils.isEmpty(noOfRejectedLocal))
                                        if (!TextUtils.isEmpty(noOfBundlesLocal)) {
                                            NewRowInfo rowInfo = new NewRowInfo();
                                            rowInfo.setSupplierName(supplierName);
                                            rowInfo.setThickness(Double.parseDouble(thicknessLocal));
                                            rowInfo.setWidth(Double.parseDouble(widthLocal));
                                            rowInfo.setLength(Double.parseDouble(lengthLocal));
                                            rowInfo.setNoOfPieces(Double.parseDouble(noOfPiecesLocal));
                                            rowInfo.setNoOfRejected(Double.parseDouble(noOfRejectedLocal));
                                            rowInfo.setNoOfBundles(Double.parseDouble(noOfBundlesLocal));
                                            rowInfo.setGrade(gradeText);

                                            newRowList.add(rowInfo);
                                            if (tableLayout.getChildCount() == 0)
                                                addTableHeader(tableLayout);
                                            tableRow = new TableRow(this);
                                            fillTableRow(tableRow, thicknessLocal, widthLocal, lengthLocal, noOfPiecesLocal, noOfRejectedLocal, noOfBundlesLocal);
                                            tableLayout.addView(tableRow);

                                        } else {
                                            noOfBundles.setError("Required!");
                                        }
                                    else {
                                        noOfRejected.setError("Required!");
                                    }
                                else {
                                    noOfPieces.setError("Required!");
                                }
                            else {
                                length.setError("Required!");
                            }
                        else {
                            width.setError("Required!");
                        }
                    else {
                        thickness.setError("Required!");
                    }
                } else {
                    searchSupplier.setError("Please Select First!");
                }

                break;
            case R.id.addNewRaw_add_supplier:
                Intent intent = new Intent(AddNewRaw.this, AddNewSupplier.class);
                startActivity(intent);
                break;
            case R.id.addNewRaw_search_supplier:
                searchDialog = new Dialog(this);
                searchDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                searchDialog.setContentView(R.layout.search_supplier_dialog);

                SearchView searchView = searchDialog.findViewById(R.id.search_supplier_searchView);
                RecyclerView recyclerView = searchDialog.findViewById(R.id.search_supplier_recyclerView);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                SuppliersAdapter adapter = new SuppliersAdapter(this, supplierInfoList);
                recyclerView.setAdapter(adapter);

                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        adapter.filter(newText);
                        return false;
                    }
                });
                searchDialog.show();
                break;
        }

    }

    public void getSearchSupplierInfo(String supplierNameLocal, String supplierNoLocal) {
        supplierName = supplierNameLocal;
        searchSupplier.setText(supplierName);
        searchDialog.dismiss();

    }

    void addTableHeader(TableLayout tableLayout) {
        TableRow tableRow = new TableRow(this);
        for (int i = 0; i < 8; i++) {
            TextView textView = new TextView(this);
            textView.setBackgroundResource(R.color.orange);
            TableRow.LayoutParams textViewParam = new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 1f);
//            textViewParam.setMargins(1, 5, 1, 1);
            textView.setTextSize(15);
            textView.setTextColor(ContextCompat.getColor(this, R.color.white));
            textView.setLayoutParams(textViewParam);
            switch (i) {
                case 0:
                    TableRow.LayoutParams param = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.MATCH_PARENT);
//                    param.setMargins(1, 5, 1, 1);
                    textView.setLayoutParams(param);
                    textView.setText("Supplier #");
                    break;
                case 1:
                    textView.setText("Thic");
                    break;
                case 2:
                    textView.setText("Width");
                    break;
                case 3:
                    textView.setText("Length");
                    break;
                case 4:
                    textView.setText("# Pieces");
                    break;
                case 5:
                    textView.setText("# Rejected");
                    break;
                case 6:
                    textView.setText("# Bundles");
                    break;
                case 7:
                    textView.setText("Grade");
                    break;
            }
            tableRow.addView(textView);
        }
        tableLayout.addView(tableRow);
//        bundlesTable.addView(tableRow);
    }

    void fillTableRow(TableRow tableRow, String thicknessText, String widthText, String lengthText, String noOfPiecesText
            , String noOfRejectedText, String noBundleText) {
        for (int i = 0; i < 8; i++) {
            TextView textView = new TextView(this);
            textView.setBackgroundResource(R.color.light_orange);
            TableRow.LayoutParams textViewParam = new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 1f);
            textViewParam.setMargins(1, 5, 1, 1);
            textView.setPadding(0, 10, 0, 10);
            textView.setTextSize(15);
            textView.setTextColor(ContextCompat.getColor(this, R.color.gray_dark_one));
            textView.setLayoutParams(textViewParam);
            switch (i) {
                case 0:
                    TableRow.LayoutParams param = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
                    param.setMargins(1, 5, 1, 1);
                    textView.setPadding(0, 10, 0, 10);
                    textView.setLayoutParams(param);
                    textView.setText(supplierName);
                    break;
                case 1:
                    textView.setText(thicknessText);
                    break;
                case 2:
                    textView.setText(widthText);
                    break;
                case 3:
                    textView.setText(lengthText);
                    break;
                case 4:
                    textView.setText(noOfPiecesText);
                    break;
                case 5:
                    textView.setText(noOfRejectedText);
                    break;
                case 6:
                    textView.setText(noBundleText);
                    break;
                case 7:
                    textView.setText(gradeText);
                    break;
            }
            tableRow.addView(textView);
        }

    }

    void chooseSpinnersContent() {
        String gradeString;
        switch (gradeText) {
            case "Fresh":
                gradeString = "FR";
                break;
            case "Blue Stain":
                gradeString = "BS";
                break;
            case "Reject":
                gradeString = "RJ";
                break;
            case "KD":
                gradeString = "KD";
                break;
            case "KD Blue Stain":
                gradeString = "KDBS";
                break;
            case "Second Sort":
                gradeString = "SS";
                break;
            case "AST":
                gradeString = "AST";
                break;
            case "AST Blue Stain":
                gradeString = "ASTBS";
                break;
        }

    }

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
                request.setURI(new URI("http://" + generalSettings.getIpAddress() + "/export_row_info.php"));//import 10.0.0.214

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
//                Log.e("addToInventory/", "" + jsonArrayBundles.toString());
//                nameValuePairs.add(new BasicNameValuePair("ROW_INFO_DETAILS", jsonArrayBundles.toString().trim()));// list
//                nameValuePairs.add(new BasicNameValuePair("ROW_INFO_MASTER", jsonArrayBundles.toString().trim())); // json object

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
//
                    Log.e("tag", "****Success");
                } else {
//                    presenter.setSerialNo("");
//                    SettingsFile.serialNumber = "";
                    Log.e("tag", "****Failed to export data");
//                    Toast.makeText(AddToInventory.this, "Failed to export data Please check internet connection", Toast.LENGTH_LONG).show();
                }
            } else {
//                presenter.setSerialNo("");
//                SettingsFile.serialNumber = "";
                Log.e("tag", "****Failed to export data Please check internet connection");
//                Toast.makeText(AddToInventory.this, "Failed to export data Please check internet connection", Toast.LENGTH_LONG).show();
            }
        }
    }
}


