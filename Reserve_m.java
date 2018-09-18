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

public class Reserve_m extends AppCompatActivity implements View.OnClickListener{
    //String ip = "http://192.168.143.69/";

    Button b1,b2,b3,b4,b5,b6,b7,b8,b9,b10,b11,b12,b13,b14,b15,b16,b17,b18,b19,b20,b21,b22,b23,b24,b25,b26,b27,b28,b29,b30,b31,b32,b33,b34;
    private String seatnum, ip;
    public String caffid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve_m);

        Intent intent = getIntent();
        caffid = intent.getExtras().getString("caffID");
        ip = intent.getExtras().getString("ip");

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
        b15 = (Button)findViewById(R.id.button21);
        b16 = (Button)findViewById(R.id.button22);
        b17 = (Button)findViewById(R.id.button23);
        b18 = (Button)findViewById(R.id.button24);
        b19 = (Button)findViewById(R.id.button25);
        b20 = (Button)findViewById(R.id.button26);
        b21 = (Button)findViewById(R.id.button45);
        b22 = (Button)findViewById(R.id.button27);
        b23 = (Button)findViewById(R.id.button28);
        b24 = (Button)findViewById(R.id.button29);
        b25 = (Button)findViewById(R.id.button30);
        b26 = (Button)findViewById(R.id.button31);
        b27 = (Button)findViewById(R.id.button32);
        b28 = (Button)findViewById(R.id.button46);
        b29 = (Button)findViewById(R.id.button33);
        b30 = (Button)findViewById(R.id.button34);
        b31 = (Button)findViewById(R.id.button35);
        b32 = (Button)findViewById(R.id.button36);
        b33 = (Button)findViewById(R.id.button37);
        b34 = (Button)findViewById(R.id.button38);

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
        b15.setOnClickListener(this);
        b16.setOnClickListener(this);
        b17.setOnClickListener(this);
        b18.setOnClickListener(this);
        b19.setOnClickListener(this);
        b20.setOnClickListener(this);
        b21.setOnClickListener(this);
        b22.setOnClickListener(this);
        b23.setOnClickListener(this);
        b24.setOnClickListener(this);
        b25.setOnClickListener(this);
        b26.setOnClickListener(this);
        b27.setOnClickListener(this);
        b28.setOnClickListener(this);
        b29.setOnClickListener(this);
        b30.setOnClickListener(this);
        b31.setOnClickListener(this);
        b32.setOnClickListener(this);
        b33.setOnClickListener(this);
        b34.setOnClickListener(this);

        for(int i=1; i<35; i++) {
            String ii = Integer.toString(i);
            seatColor(ii);
        }
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
        } else if (v == b15) {
            seatnum="15";
        } else if (v == b16) {
            seatnum="16";
        } else if (v == b17) {
            seatnum="17";
        } else if (v == b18) {
            seatnum="18";
        }else if (v == b19) {
            seatnum="19";
        }else if (v == b20) {
            seatnum="20";
        }else if (v == b21) {
            seatnum="21";
        } else if (v == b22) {
            seatnum="22";
        } else if (v == b23) {
            seatnum="23";
        } else if (v == b24) {
            seatnum="24";
        } else if (v == b25) {
            seatnum="25";
        } else if (v == b26) {
            seatnum="26";
        } else if (v == b27) {
            seatnum="27";
        } else if (v == b28) {
            seatnum="28";
        }else if (v == b29) {
            seatnum="29";
        }else if (v == b30) {
            seatnum="30";
        }else if (v == b31) {
            seatnum="31";
        } else if (v == b32) {
            seatnum="32";
        } else if (v == b33) {
            seatnum="33";
        } else if (v == b34) {
            seatnum="34";
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("["+seatnum+"번]");
        builder.setMessage("["+seatnum+"번 좌석]을 예약하시겠습니까?");
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                v.setBackgroundResource(R.color.pressed2);
                reser_ex(seatnum);
            }
        });
        builder.show();
    }

    private void seatColor(final String seatnum) {
        class LoginAsync extends AsyncTask<String, Void, String> {
            private Dialog loadingDialog;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loadingDialog = ProgressDialog.show(Reserve_m.this, "Please wait", "Loading...");
            }

            @Override
            protected String doInBackground(String... params) {
                String seatnum = params[0];
                String url;

                if (caffid.equals("1")) {
                    url = ip+"seat_color.php";
                }
                else if (caffid.equals("2")) {
                    url = ip+"seat_color2.php";
                }
                else {
                    url =ip+"seat_color.php";
                }

                InputStream is = null;
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("seatnum", seatnum));
                String result = null;

                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(url);
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
                if (s.equalsIgnoreCase("1")) {
                    b1.setBackgroundResource(R.color.pressed1);
                } else if (s.equalsIgnoreCase("2")) {
                    b2.setBackgroundResource(R.color.pressed1);
                } else if (s.equalsIgnoreCase("3")) {
                    b3.setBackgroundResource(R.color.pressed1);
                } else if (s.equalsIgnoreCase("4")) {
                    b4.setBackgroundResource(R.color.pressed1);
                } else if (s.equalsIgnoreCase("5")) {
                    b5.setBackgroundResource(R.color.pressed1);
                } else if (s.equalsIgnoreCase("6")) {
                    b6.setBackgroundResource(R.color.pressed1);
                } else if (s.equalsIgnoreCase("7")) {
                    b7.setBackgroundResource(R.color.pressed1);
                } else if (s.equalsIgnoreCase("8")) {
                    b8.setBackgroundResource(R.color.pressed1);
                } else if (s.equalsIgnoreCase("9")) {
                    b9.setBackgroundResource(R.color.pressed1);
                } else if (s.equalsIgnoreCase("10")) {
                    b10.setBackgroundResource(R.color.pressed1);
                } else if (s.equalsIgnoreCase("11")) {
                    b11.setBackgroundResource(R.color.pressed1);
                } else if (s.equalsIgnoreCase("12")) {
                    b12.setBackgroundResource(R.color.pressed1);
                } else if (s.equalsIgnoreCase("13")) {
                    b13.setBackgroundResource(R.color.pressed1);
                } else if (s.equalsIgnoreCase("14")) {
                    b14.setBackgroundResource(R.color.pressed1);
                } else if (s.equalsIgnoreCase("15")) {
                    b15.setBackgroundResource(R.color.pressed1);
                } else if (s.equalsIgnoreCase("16")) {
                    b16.setBackgroundResource(R.color.pressed1);
                } else if (s.equalsIgnoreCase("17")) {
                    b17.setBackgroundResource(R.color.pressed1);
                } else if (s.equalsIgnoreCase("18")) {
                    b18.setBackgroundResource(R.color.pressed1);
                } else if (s.equalsIgnoreCase("19")) {
                    b19.setBackgroundResource(R.color.pressed1);
                } else if (s.equalsIgnoreCase("20")) {
                    b20.setBackgroundResource(R.color.pressed1);
                } else if (s.equalsIgnoreCase("21")) {
                    b21.setBackgroundResource(R.color.pressed1);
                } else if (s.equalsIgnoreCase("22")) {
                    b22.setBackgroundResource(R.color.pressed1);
                } else if (s.equalsIgnoreCase("23")) {
                    b23.setBackgroundResource(R.color.pressed1);
                } else if (s.equalsIgnoreCase("24")) {
                    b24.setBackgroundResource(R.color.pressed1);
                } else if (s.equalsIgnoreCase("25")) {
                    b25.setBackgroundResource(R.color.pressed1);
                } else if (s.equalsIgnoreCase("26")) {
                    b26.setBackgroundResource(R.color.pressed1);
                } else if (s.equalsIgnoreCase("27")) {
                    b27.setBackgroundResource(R.color.pressed1);
                } else if (s.equalsIgnoreCase("28")) {
                    b28.setBackgroundResource(R.color.pressed1);
                } else if (s.equalsIgnoreCase("29")) {
                    b29.setBackgroundResource(R.color.pressed1);
                } else if (s.equalsIgnoreCase("30")) {
                    b30.setBackgroundResource(R.color.pressed1);
                } else if (s.equalsIgnoreCase("31")) {
                    b31.setBackgroundResource(R.color.pressed1);
                } else if (s.equalsIgnoreCase("32")) {
                    b32.setBackgroundResource(R.color.pressed1);
                } else if (s.equalsIgnoreCase("33")) {
                    b33.setBackgroundResource(R.color.pressed1);
                } else if (s.equalsIgnoreCase("34")) {
                    b34.setBackgroundResource(R.color.pressed1);
                }
                else{
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
                loadingDialog = ProgressDialog.show(Reserve_m.this, "Please wait", "Loading...");
            }

            @Override
            protected String doInBackground(String... params) {
                String seatnum = params[0];
                String url;

                if (caffid.equals("1")) {
                    url = ip+"seat_ex_m.php";
                }
                else if (caffid.equals("2")) {
                    url = ip+"seat_ex_m2.php";
                }
                else {
                    url = ip+"seat_ex_m.php";
                }

                InputStream is = null;
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("seatnum", seatnum));
                String result = null;

                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(url);
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
                }
                else {
                    Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
                }
            }
        }
        LoginAsync la = new LoginAsync();
        la.execute(seatnum);
    }

}
