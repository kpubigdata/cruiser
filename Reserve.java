package com.example.minhe.pproject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
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

public class Reserve extends AppCompatActivity implements View.OnClickListener{
   Button b1,b2,b3,b4,b5,b6,b7,b8,b9,b10,b11,b12,b13,b14;
   private String seatnum;
   private String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve);

        Intent intent = getIntent();
        name = intent.getExtras().getString("USER_NAME");

        b1 = (Button)findViewById(R.id.button9);
        b2 = (Button)findViewById(R.id.button10);
        b3 = (Button)findViewById(R.id.button11);
        b4 = (Button)findViewById(R.id.button12);
        b5 = (Button)findViewById(R.id.button13);
        b6 = (Button)findViewById(R.id.button14);
        b7 = (Button)findViewById(R.id.button43);
        b8 = (Button)findViewById(R.id.button15);
        b9 = (Button)findViewById(R.id.button16);
        b10 = (Button)findViewById(R.id.button41);
        b11 = (Button)findViewById(R.id.button18);
        b12 = (Button)findViewById(R.id.button42);
        b13 = (Button)findViewById(R.id.button20);
        b14 = (Button)findViewById(R.id.button44);

        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        b3.setOnClickListener(this);
        b4.setOnClickListener(this);
        b5.setOnClickListener(this);
        b6.setOnClickListener(this);
        b7.setOnClickListener(this);
        b8.setOnClickListener(this);
        b9.setOnClickListener(this);
        b10.setOnClickListener(this);
        b11.setOnClickListener(this);
        b12.setOnClickListener(this);
        b13.setOnClickListener(this);
        b14.setOnClickListener(this);

    }
    public void onClick(final View v) {
        if (v == b1) {
            seatnum="1";
        } else if (v == b2) {
            seatnum="2";
        } else if (v == b3) {
            seatnum="3";
        } else if (v == b4) {
            seatnum="4";
        } else if (v == b5) {
            seatnum="5";
        }else if (v == b6) {
            seatnum="6";
        }else if (v == b7) {
            seatnum="7";
        }else if (v == b8) {
            seatnum="8";
        }else if (v == b9) {
            seatnum="9";
        }else if (v == b10) {
            seatnum="10";
        }else if (v == b11) {
            seatnum="11";
        } else if (v == b12) {
            seatnum="12";
        } else if (v == b13) {
            seatnum="13";
        } else if (v == b14) {
            seatnum="14";
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("["+seatnum+"번]");
        builder.setMessage("["+seatnum+"번 좌석]을 예약하시겠습니까?");
        builder.setNeutralButton("예약", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                reser(seatnum);
                v.setBackgroundResource(R.color.pressed1);
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                reser_ex(seatnum);
                v.setBackgroundResource(R.color.pressed2);
            }
        });
        builder.show();
    }
    private void reser(final String seatnum) {
        class LoginAsync extends AsyncTask<String, Void, String> {
            private Dialog loadingDialog;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loadingDialog = ProgressDialog.show(Reserve.this, "Please wait", "Loading...");
            }

            @Override
            protected String doInBackground(String... params) {
                String seatnum = params[0];

                InputStream is = null;
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("seatnum", seatnum));
                nameValuePairs.add(new BasicNameValuePair("userID", name));
                String result = null;

                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(
                            "http://192.168.142.111/seat.php");
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
                if (s.equalsIgnoreCase("success")) {
                    Toast.makeText(getApplicationContext(), "> 예약완료 <", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
                }
            }
        }
        LoginAsync la = new LoginAsync();
        la.execute(seatnum);
    }

    private void reser_ex(final String seatnum) {
        class LoginAsync extends AsyncTask<String, Void, String> {
            private Dialog loadingDialog;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loadingDialog = ProgressDialog.show(Reserve.this, "Please wait", "Loading...");
            }

            @Override
            protected String doInBackground(String... params) {
                String seatnum = params[0];

                InputStream is = null;
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("seatnum", seatnum));
                nameValuePairs.add(new BasicNameValuePair("userID", name));
                String result = null;

                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(
                            "http://192.168.142.111/seat_ex.php");
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
                if (s.equalsIgnoreCase("success")) {
                    Toast.makeText(getApplicationContext(), "> 예약취소 <", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
                }
            }
        }
        LoginAsync la = new LoginAsync();
        la.execute(seatnum);
    }

}
