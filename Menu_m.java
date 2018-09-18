package com.example.minhe.pproject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import static com.example.minhe.pproject.Choice_m.caffID;

public class Menu_m extends AppCompatActivity {
    //String ip = "http://192.168.143.69/";
    public String caffid;
    private String l_ip;
    String po_name;
    public static final String ip = "ip";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_m);

        Intent intent = getIntent();
        caffid = intent.getExtras().getString("caffID");
        l_ip = intent.getExtras().getString("ip");
    }

    public void c1(View view)
    {
        Intent intent1 = new Intent(this, Reserve_m.class);
        intent1.putExtra(caffID, caffid);
        intent1.putExtra(ip, l_ip);
        startActivity(intent1);
    }

    public void c3(View view)
    {
        Intent intent2 = new Intent(this, People_m.class);
        intent2.putExtra(caffID, caffid);
        intent2.putExtra(ip, l_ip);
        startActivity(intent2);
    }

    public void sound(View view)
    {
        String url = "https://thingspeak.com/channels/502687/charts/1?bgcolor=%23ffffff&color=%23d62020&days=1&dynamic=true&results=60&type=line";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }

    public void point(View view) {
        AlertDialog alertDialog = null;
        final AlertDialog.Builder builder = new AlertDialog.Builder(Menu_m.this);
        // Set title value.
        builder.setTitle(">> 적립 금액을 설정하세요");

        // Get custom login form view.
        final View loginFormView = getLayoutInflater().inflate(R.layout.point, null);
        builder.setView(loginFormView);

        // Register button click listener.
        Button registerButton = (Button)loginFormView.findViewById(R.id.loginFormRegisterButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {

                    // Close Alert Dialog.
                    EditText loginUserName = (EditText)loginFormView.findViewById(R.id.loginFormUserName);
                    po_name = loginUserName.getText().toString();

                    if (po_name.length() > 0) {
                        poname(po_name);
                    }
                    else {
                        Toast.makeText(Menu_m.this, "금액을 입력해주세요.", Toast.LENGTH_SHORT).show();
                        loginUserName.requestFocus();
                        return;
                    }
                }
                catch(Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        builder.setCancelable(true);
        alertDialog = builder.create();
        alertDialog.show();
        }

    private void poname(String pp) {
        class InsertData extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Menu_m.this, "Please Wait", null, true, true);
            }
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
            }
            protected String doInBackground(String... params) {
                try {
                    String pp = (String) params[0];

                    String link = l_ip+"update_point.php";
                    String data = URLEncoder.encode("sid", "UTF-8") + "=" + URLEncoder.encode(pp, "UTF-8");

                    URL url = new URL(link);
                    URLConnection conn = url.openConnection();

                    conn.setDoOutput(true);
                    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

                    wr.write(data);
                    wr.flush();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                    StringBuilder sb = new StringBuilder();
                    String line = null;

                    // Read Server Response
                    while ((line = reader.readLine()) != null) {
                        sb.append(line);
                        break;
                    }
                    return sb.toString();
                } catch (Exception e) {
                    return new String("Exception: " + e.getMessage());
                }
            }

        }
        InsertData task = new InsertData();
        task.execute(pp);
    }
    }
