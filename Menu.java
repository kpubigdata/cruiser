package com.example.minhe.pproject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
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
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.example.minhe.pproject.MainActivity.USER_NAME;
import static com.example.minhe.pproject.choice.caffID;

public class Menu extends AppCompatActivity {
    //String ip = "http://192.168.143.69/";
    String name;
    private String caffid;
    private String l_ip;
    String url;
    public static final String ip = "ip";


    // Declare Variables
    public String[] date = new String[10];
    public String[] time = new String[10];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Intent intent = getIntent();
        name = intent.getExtras().getString("USER_NAME");
        caffid = intent.getExtras().getString("caffID");
        l_ip = intent.getExtras().getString("ip");
    }

    public void onClick5(View view)
    {
        Intent intent1 = new Intent(this, Reserve.class);
        intent1.putExtra(USER_NAME, name);
        intent1.putExtra(caffID, caffid);
        intent1.putExtra(ip, l_ip);
        startActivity(intent1);
    }

    public void onClick6(View view)
    {
        Intent intent4 = new Intent(this,Myinfo.class);
        intent4.putExtra(USER_NAME, name);
        intent4.putExtra(caffID, caffid);
        intent4.putExtra(ip, l_ip);
        startActivity(intent4);
    }

    public void chat(View view)
    {
        Intent intent5 = new Intent(this,Chat.class);
        intent5.putExtra(USER_NAME, name);
        intent5.putExtra(caffID, caffid);
        intent5.putExtra(ip, l_ip);
        startActivity(intent5);
    }

    public void onClick8(View view)
    {
        Intent intent6 = new Intent(this,Commu.class);
        intent6.putExtra(USER_NAME, name);
        intent6.putExtra(caffID, caffid);
        intent6.putExtra(ip, l_ip);
        startActivity(intent6);
    }

    public void chart(View view) {
        Intent intent7 = new Intent(this,Chart.class);
        intent7.putExtra(USER_NAME, name);
        intent7.putExtra(caffID, caffid);
        intent7.putExtra(ip, l_ip);
        startActivity(intent7);
    }

    public void rank(View view) {
        Intent intent8 = new Intent(this,Rank.class);
        intent8.putExtra(USER_NAME, name);
        intent8.putExtra(caffID, caffid);
        intent8.putExtra(ip, l_ip);
        startActivity(intent8);
    }

    public void relogo(View view) {
        class LoginAsync extends AsyncTask<String, Void, String> {
            private Dialog loadingDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loadingDialog = ProgressDialog.show(Menu.this, "Please wait", "Loading...");
            }

            @Override
            protected String doInBackground(String... params) {
                if (caffid.equals("1")) {
                    url = l_ip+"data5.php";
                }
                else if (caffid.equals("2")) {
                    url = l_ip+"data5_2.php";
                }
                else {
                    url = l_ip+"data5.php";
                }

                InputStream is = null;
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("userID", "1"));
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

                Date nowday = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

                String week = "";
                String today = s.substring(0,10);
                String time1 = s.substring(14,15);
                String time2 = s.substring(16,17);

                try {
                    Date date = dateFormat.parse(today);

                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(date);
                    int dayNum = calendar.get(Calendar.DAY_OF_WEEK) ;
                    switch(dayNum){
                        case 1:
                            week = "일"; break ;
                        case 2:
                            week = "월"; break ;
                        case 3:
                            week = "화"; break ;
                        case 4:
                            week = "수"; break ;
                        case 5:
                            week = "목"; break ;
                        case 6:
                            week = "금"; break ;
                        case 7:
                            week = "토"; break ;
                    }
                } catch (Exception e) {
                }
                AlertDialog.Builder alert = new AlertDialog.Builder(Menu.this);
                alert.setPositiveButton("닫기", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alert.setTitle(">> ["+dateFormat.format(nowday)+"] 오늘의 추천");
                alert.setMessage(week+"요일 / AM."+time1+" & PM."+time2+" :)");
                alert.show();
            }
        }
        LoginAsync la = new LoginAsync();
        la.execute();
   }
}
