package com.example.minhe.pproject;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
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

public class Myinfo extends AppCompatActivity {
    private EditText et_pw, et_pw_r;
    private String spw, spw_r, name;
    private TextView na_t,na_num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myinfo);

        et_pw = (EditText) findViewById(R.id.editText8);
        et_pw_r = (EditText) findViewById(R.id.editText9);
        na_t = (TextView) findViewById(R.id.textView30);
        na_num = (TextView) findViewById(R.id.textView31);

        Intent intent = getIntent();
        name = intent.getExtras().getString("USER_NAME");

        na_t.setText(name);
        viname(name);
    }

    private void viname(final String userID) {
        class LoginAsync extends AsyncTask<String, Void, String> {
            private Dialog loadingDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loadingDialog = ProgressDialog.show(Myinfo.this, "Please wait", "Loading...");
            }

            @Override
            protected String doInBackground(String... params) {
                String userID = params[0];

                InputStream is = null;
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("userID", userID));
                String result = null;

                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(
                            "http://192.168.142.111/myinfo_seat.php");
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                    HttpResponse response = httpClient.execute(httpPost);

                    HttpEntity entity = response.getEntity();

                    is = entity.getContent();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8);
                    StringBuilder sb = new StringBuilder();

                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    result = sb.toString();
                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return result;
            }

            @Override
            protected void onPostExecute(String result) {
                String s = result.trim();
                System.out.println(s);
                loadingDialog.dismiss();
                if (s.length() > 0) {
                    na_num.setText(s+"번 좌석 사용중");
                }
                else {
                    na_num.setText("예약 좌석 없음");
                }
            }
        }
        LoginAsync la = new LoginAsync();
        la.execute(name);
    }

    public void insert3(View view) {
        spw = et_pw.getText().toString();
        spw_r = et_pw_r.getText().toString();

        // 아이디 입력 확인
        if(spw.equals(spw_r)) {
            Toast.makeText(Myinfo.this, name, Toast.LENGTH_SHORT).show();
            insertoToDatabase(spw, name);
        }
        // 비밀번호 입력 확인
        else {
            Toast.makeText(Myinfo.this, "비밀번호를 확인해주세요.", Toast.LENGTH_SHORT).show();
            Toast.makeText(Myinfo.this,spw, Toast.LENGTH_SHORT).show();
            Toast.makeText(Myinfo.this,spw_r, Toast.LENGTH_SHORT).show();
            et_pw.requestFocus();
            return;
        }
    }

    private void insertoToDatabase(String spw, String name) {
        class InsertData extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Myinfo.this, "Please Wait", null, true, true);
            }
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
            }
            protected String doInBackground(String... params) {
                try {
                    String spw = (String) params[0];
                    String sname = (String) params[1];

                    String link = "http://192.168.142.111/update_pass.php";
                    String data = URLEncoder.encode("sid", "UTF-8") + "=" + URLEncoder.encode(sname, "UTF-8");
                    data += "&" + URLEncoder.encode("spw", "UTF-8") + "=" + URLEncoder.encode(spw, "UTF-8");

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
        task.execute(spw,name);
    }
}

