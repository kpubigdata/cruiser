package com.example.minhe.pproject;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
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

public class Group_reserve extends AppCompatActivity {
    private EditText et_date, et_time, et_peple, et_room;
    private String sdate, stime, speple, sroom;

    private String ip;
    private String s_date, s_time, s_peple, s_room, s_d, s_t, s_p, s_r, link;
    private String name;
    public String caffid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_reserve);
        Intent intent = getIntent();
        ip = intent.getExtras().getString("ip");
        name = intent.getExtras().getString("USER_NAME");
        caffid = intent.getExtras().getString("caffID");

        et_date = (EditText) findViewById(R.id.editText12);
        et_time = (EditText) findViewById(R.id.editText13);
        et_peple = (EditText) findViewById(R.id.editText14);
        et_room = (EditText) findViewById(R.id.editText15);
    }

    public void g_re(View view) {
        sdate = et_date.getText().toString();
        stime = et_time.getText().toString();
        speple = et_peple.getText().toString();
        sroom = et_room.getText().toString();

        // 날짜 입력 확인
        if( sdate.length() == 0 ) {
            Toast.makeText(Group_reserve.this, "날짜를 입력하세요.", Toast.LENGTH_SHORT).show();
        }
        // 시간 입력 확인
        if( stime.length() == 0 ) {
            Toast.makeText(Group_reserve.this, "시간을 입력하세요.", Toast.LENGTH_SHORT).show();
        }
        // 인원 수 입력 확인
        if( speple.length() == 0 ) {
            Toast.makeText(Group_reserve.this, "인원 수를 입력하세요.", Toast.LENGTH_SHORT).show();
        }
        // 방 번호 입력 확인
        if( sroom.length() == 0 ) {
            Toast.makeText(Group_reserve.this, "방 번호를 입력하세요.", Toast.LENGTH_SHORT).show();
        }

        yourseat_r(sdate, stime, speple, sroom);

    }

    private void yourseat_r(final String sdate, String stime, String speple, String sroom) {
        class LoginAsync extends AsyncTask<String, Void, String> {
            private Dialog loadingDialog;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loadingDialog = ProgressDialog.show(Group_reserve.this, "Please wait", "Loading...");
            }

            @Override
            protected String doInBackground(String... params) {
                s_date = params[0];
                s_time = params[1];
                s_peple = params[2];
                s_room = params[3];
                String url;

                if (caffid.equals("1")) {
                    url =ip+"semi1.php";
                }
                else if (caffid.equals("2")) {
                    url =ip+"semi2.php";
                }
                else {
                    url =ip+"semi1.php";
                }

                InputStream is = null;
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("userID", name));
                nameValuePairs.add(new BasicNameValuePair("s_date", s_date));
                nameValuePairs.add(new BasicNameValuePair("s_time", s_time));
                nameValuePairs.add(new BasicNameValuePair("s_room", s_room));
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
                    // 예약가능하다면 데이터 삽입
                    insertoToDatabase(s_date, s_time, s_peple, s_room);
                    Toast.makeText(getApplicationContext(), "> 예약완료 <", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(getApplicationContext(), "> 예약불가 <", Toast.LENGTH_LONG).show();
                }
            }
        }
        LoginAsync la = new LoginAsync();
        la.execute(sdate, stime, speple, sroom);
    }

    // 예약 정보 db에 입력
    private void insertoToDatabase(String sdate, String stime, String speple, String sroom) {
        class InsertData extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Group_reserve.this, "Please Wait", null, true, true);
            }
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
            }
            protected String doInBackground(String... params) {
                try {
                    s_d = (String) params[0];
                    s_t = (String) params[1];
                    s_p = (String) params[2];
                    s_r = (String) params[3];

                    if (caffid.equals("1")) {
                        link = ip+"semi_insert1.php";
                    }
                    else if (caffid.equals("2")) {
                        link = ip+"semi_insert2.php";
                    }
                    else {
                        link = ip+"semi_insert1.php";
                    }

                    String data = URLEncoder.encode("s_d", "UTF-8") + "=" + URLEncoder.encode(s_d, "UTF-8");
                    data += "&" + URLEncoder.encode("s_t", "UTF-8") + "=" + URLEncoder.encode(s_t, "UTF-8");
                    data += "&" + URLEncoder.encode("s_p", "UTF-8") + "=" + URLEncoder.encode(s_p, "UTF-8");
                    data += "&" + URLEncoder.encode("s_r", "UTF-8") + "=" + URLEncoder.encode(s_r, "UTF-8");
                    data += "&" + URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8");

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
        task.execute(sdate, stime, speple, sroom);
    }

}
