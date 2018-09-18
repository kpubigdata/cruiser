package com.example.minhe.pproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class People_m extends AppCompatActivity {
    //String ip = "http://192.168.143.69/";
    // 데이터를 보기위한 TextView
    TextView tv;
    // PHP를 읽어올때 사용할 변수
    public GettingPHP gPHP;
    private EditText et_id;
    private String sid;
    private String ip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people_m);
        gPHP = new GettingPHP();

        et_id = (EditText) findViewById(R.id.people);
        tv = (TextView)findViewById(R.id.textView27);

        Intent intent = getIntent();
        ip = intent.getExtras().getString("ip");

        gPHP.execute(ip+"data.php");
    }

    class GettingPHP extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            StringBuilder jsonHtml = new StringBuilder();
            try {
                URL phpUrl = new URL(params[0]);
                HttpURLConnection conn = (HttpURLConnection)phpUrl.openConnection();

                if ( conn != null ) {
                    conn.setConnectTimeout(10000);
                    conn.setUseCaches(false);

                    if ( conn.getResponseCode() == HttpURLConnection.HTTP_OK ) {
                        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                        while ( true ) {
                            String line = br.readLine();
                            if ( line == null )
                                break;
                            jsonHtml.append(line + "\n");
                        }
                        br.close();
                    }
                    conn.disconnect();
                }
            } catch ( Exception e ) {
                e.printStackTrace();
            }
            return jsonHtml.toString();
        }

        protected void onPostExecute(String str) {
            try {
                // PHP에서 받아온 JSON 데이터를 JSON오브젝트로 변환
                JSONObject jObject = new JSONObject(str);
                // results라는 key는 JSON배열로 되어있다.
                JSONArray results = jObject.getJSONArray("results");
                String zz = "";
                zz += "** Number of results : " + jObject.get("num_result")+" **";
                zz += "\n\n";
                for ( int i = 0; i < 5; ++i ) {
                    JSONObject temp = results.getJSONObject(i);
                    zz += "[ No. " + (i+1)+" ] ";
                    zz += "\nId : " + temp.get("Id");
                    zz += "\nPassword : " + temp.get("Password");
                    zz += "\nName : " + temp.get("Name");
                    zz += "\nPhoneNum : " + temp.get("PhoneNum");
                    zz += "\nEmail : " + temp.get("Email");
                    zz += "\n----------------------------------------------------------------------------------------\n";
                }
                tv.setText(zz);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void insert2(View view) {
        sid = et_id.getText().toString();
        insertoToDatabase(sid);
    }

    private void insertoToDatabase(String sid) {
        class InsertData extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(People_m.this, "Please Wait", null, true, true);
            }
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
            }
            protected String doInBackground(String... params) {
                try {
                    String sid = (String) params[0];

                    String link =ip+"del.php";
                    String data = URLEncoder.encode("sid", "UTF-8") + "=" + URLEncoder.encode(sid, "UTF-8");

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
        task.execute(sid);
    }
}



