package com.falconssoft.woodysystem.stage_two;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.falconssoft.woodysystem.DatabaseHandler;
import com.falconssoft.woodysystem.R;
import com.falconssoft.woodysystem.models.BundleInfo;
import com.falconssoft.woodysystem.models.CustomerInfo;
import com.falconssoft.woodysystem.models.SupplierInfo;
import com.falconssoft.woodysystem.stage_one.AddSupplierAdapter;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class AddNewCustomer extends AppCompatActivity {

    private DatabaseHandler DHandler;
    private RecyclerView recyclerView;
    private AddCustomerAdapter adapter;
    private EditText custName;
    private TextView add;
    private List<CustomerInfo> customers;
    private JSONArray jsonArray;

    private static String custNameDel="12";
    private static String custNoDel;
    private static int i;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_customer);

        jsonArray = new JSONArray();
        DHandler = new DatabaseHandler(AddNewCustomer.this);
        customers = new ArrayList<>();
        custName = findViewById(R.id.editText);
        add = findViewById(R.id.textView15);
        recyclerView = findViewById(R.id.addSupplier_recyclerView);

        new JSONTask().execute();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AddCustomerAdapter(AddNewCustomer.this, customers);
        recyclerView.setAdapter(adapter);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(custName.getText().toString())) {

                    jsonArray = new JSONArray();
                    CustomerInfo customerInfo = new CustomerInfo();
                    try {
                        int max = Integer.parseInt(customers.get(0).getCustNo());
                        for (int i = 1; i < customers.size(); i++)
                            if (Integer.parseInt(customers.get(i).getCustNo()) > max)
                                max = Integer.parseInt(customers.get(i).getCustNo());
                        customerInfo.setCustNo("" + (max + 1));
                    } catch (Exception e) {
                        customerInfo.setCustNo("" + (customers.size() + 1));
                    }
//                    customerInfo.setCustNo("" + (customers.size() + 1));
                    customerInfo.setCustName(custName.getText().toString());

                    customers.add(customerInfo);
                    jsonArray.put(customerInfo.getJSONObject());
                    new JSONTask2().execute();
                }
            }
        });

    }

    void deleteCustomer(int index){
        i = index;
        custNameDel = customers.get(index).getCustName();
        Log.e("****" ,custNameDel );
        custNoDel = customers.get(index).getCustNo();
        new JSONTask3().execute();
    }


    private class JSONTask extends AsyncTask<String, String, List<CustomerInfo>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected List<CustomerInfo> doInBackground(String... params) {
            URLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL("http://" + DHandler.getSettings().getIpAddress() + "/import.php?FLAG=7");

                URLConnection conn = url.openConnection();
                conn.setDoOutput(true);

                reader = new BufferedReader(new
                        InputStreamReader(conn.getInputStream()));

                StringBuilder sb = new StringBuilder();
                String line = null;

                // Read Server Response
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }

                String finalJson = sb.toString();
                Log.e("finalJson*********", finalJson);

                JSONObject parentObject = new JSONObject(finalJson);

                try {
                    JSONArray parentArrayOrders = parentObject.getJSONArray("CUSTOMERS");

                    for (int i = 0; i < parentArrayOrders.length(); i++) {
                        JSONObject innerObject = parentArrayOrders.getJSONObject(i);

                        CustomerInfo customer = new CustomerInfo();
                        customer.setCustNo(innerObject.getString("CUSTOMERS_NO"));
                        customer.setCustName(innerObject.getString("CUSTOMERS_NAME"));

                        customers.add(customer);

                    }
                } catch (JSONException e) {
                    Log.e("Import Data2", e.getMessage().toString());
                }

            } catch (MalformedURLException e) {
                Log.e("Customer", "********ex1");
                e.printStackTrace();
            } catch (IOException e) {
                Log.e("Customer", e.getMessage().toString());
                e.printStackTrace();

            } catch (JSONException e) {
                Log.e("Customer", "********ex3  " + e.toString());
                e.printStackTrace();
            } finally {
                Log.e("Customer", "********finally");
                if (connection != null) {
                    Log.e("Customer", "********ex4");
                    // connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return customers;
        }


        @Override
        protected void onPostExecute(final List<CustomerInfo> result) {
            super.onPostExecute(result);

            if (result != null) {
                Log.e("result", "*****************" + result.size());
//                fillTable();
                adapter.notifyDataSetChanged();
            } else {
                Toast.makeText(AddNewCustomer.this, "Not able to fetch data from server, please check url.", Toast.LENGTH_SHORT).show();
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
                request.setURI(new URI("http://" + DHandler.getSettings().getIpAddress() + "/export.php"));

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
                nameValuePairs.add(new BasicNameValuePair("CUSTOMERS", jsonArray.toString().trim()));

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
                if (s.contains("CUSTOMERS SUCCESS")) {
                    adapter.notifyDataSetChanged();
                    custName.setText("");
                    Log.e("tag", "****Success");
                } else {
                    Log.e("tag", "****Failed to export data");
                }
            } else {
                Log.e("tag", "****Failed to export data Please check internet connection");
            }
        }
    }

    private class JSONTask3 extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //showDialog();

        }

        @Override
        protected String doInBackground(String... params) {
            try {
                String JsonResponse = null;
                HttpClient client = new DefaultHttpClient();
                HttpPost request = new HttpPost();
                request.setURI(new URI("http://" +  DHandler.getSettings().getIpAddress()  + "/export.php"));

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
                nameValuePairs.add(new BasicNameValuePair("DELETE_CUSTOMER", "1"));
                nameValuePairs.add(new BasicNameValuePair("DELETE_CUSTOMER_NAME", custNameDel));
                nameValuePairs.add(new BasicNameValuePair("DELETE_CUSTOMER_NO", custNoDel));
                Log.e("****in" ,custNameDel );
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
                if (s.contains("DELETE_CUSTOMER SUCCESS")) {

                    customers.remove(i);
                    adapter.notifyDataSetChanged();


                    Toast.makeText(AddNewCustomer.this, "Customer Deleted!", Toast.LENGTH_SHORT).show();
                    Log.e("tag", "DELETE_CUSTOMER SUCCESS");
                } else {
                    Toast.makeText(AddNewCustomer.this, "Failed to export data!", Toast.LENGTH_SHORT).show();
                    Log.e("tag", "Failed to export data!");
                }

            } else {
                Toast.makeText(AddNewCustomer.this, "No internet connection!", Toast.LENGTH_SHORT).show();
            }
        }
    }


}
