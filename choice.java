package com.example.minhe.pproject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import static com.example.minhe.pproject.MainActivity.USER_NAME;

public class choice extends AppCompatActivity implements View.OnClickListener {
    //String ip = "http://192.168.143.69/";
    String name;
    private String l_ip;
    private TextView c1,c2;
    public static final String caffID = "caffID";
    public static final String ip = "ip";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice);

        findViewById(R.id.textView13).setOnClickListener(this);
        findViewById(R.id.textView14).setOnClickListener(this);
        c1 = (TextView) findViewById(R.id.textView34);
        c2 = (TextView) findViewById(R.id.textView35);

        Intent intent = getIntent();
        name = intent.getExtras().getString("USER_NAME");
        l_ip = intent.getExtras().getString("ip");

        viname();
        viname2();
    }
    public void onClick(View v) {
        ImageView imageV1 = (ImageView) findViewById(R.id.imageView12);
        switch (v.getId()) {
            case R.id.textView13:
                new AlertDialog.Builder(this)
                        .setTitle("드림 스터디카페")
                        .setMessage("▷ 드림 스터디카페\n▷ 010-7519-1737\n▷ 매일 00:00 - 24:00\n▷ 예약, 단체석, 무선 인터넷, 남/녀 화장실 구분")
                        .setNeutralButton("닫기", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dlg, int sumthin) {
                            }
                        })
                        .show();
                imageV1.setImageResource(R.drawable.dream_map);
                break;
            case R.id.textView14:
                new AlertDialog.Builder(this)
                        .setTitle("토즈 스터디카페")
                        .setMessage("▷ 토즈 스터디카페\n▷ 031-499-2229\n▷ 매일 09:00 - 02:00\n▷ 예약, 무선 인터넷, 남/녀 화장실 구분")
                        .setNeutralButton("닫기", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dlg, int sumthin) {
                            }
                        })
                        .show();
                imageV1.setImageResource(R.drawable.toz_map);
                break;
        }
    }

    private void viname() {
        class LoginAsync extends AsyncTask<String, Void, String> {
            private Dialog loadingDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loadingDialog = ProgressDialog.show(choice.this, "Please wait", "Loading...");
            }

            @Override
            protected String doInBackground(String... params) {
                InputStream is = null;
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("userID", "1"));
                String result = null;

                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(l_ip+"result_seat.php");
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
                    c1.setText(s+" / 34");
                }
                else {
                    c1.setText("남은 좌석 없음");
                }
            }
        }
        LoginAsync la = new LoginAsync();
        la.execute();
    }
    private void viname2() {
        class LoginAsync extends AsyncTask<String, Void, String> {
            private Dialog loadingDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loadingDialog = ProgressDialog.show(choice.this, "Please wait", "Loading...");
            }

            @Override
            protected String doInBackground(String... params) {
                InputStream is = null;
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("userID", "1"));
                String result = null;

                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(l_ip+"result_seat2.php");
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
                    c2.setText(s+" / 34");
                }
                else {
                    c2.setText("남은 좌석 없음");
                }
            }
        }
        LoginAsync la = new LoginAsync();
        la.execute();
    }

    public void onClick3(View view) {
        Intent intent1 = new Intent(this, Menu.class);
        intent1.putExtra(USER_NAME, name);
        intent1.putExtra(caffID, "1");
        intent1.putExtra(ip, l_ip);
        startActivity(intent1);
    }

    public void onClick9(View view) {
        Intent intent2 = new Intent(this, Menu.class);
        intent2.putExtra(USER_NAME, name);
        intent2.putExtra(caffID, "2");
        intent2.putExtra(ip, l_ip);
        startActivity(intent2);
    }
}