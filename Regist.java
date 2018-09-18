package com.example.minhe.pproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class Regist extends AppCompatActivity {
    //String ip = "http://192.168.143.69/";
    private EditText et_id, et_pw, et_name, et_phone, et_email;
    private String sid, spw, sname, sphone, semail, ip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);

        et_id = (EditText) findViewById(R.id.editText3);
        et_pw = (EditText) findViewById(R.id.editText4);
        et_name = (EditText) findViewById(R.id.editText5);
        et_phone = (EditText) findViewById(R.id.editText6);
        et_email = (EditText) findViewById(R.id.editText7);

        Intent intent = getIntent();
        ip = intent.getExtras().getString("ip");

    }

    public void insert(View view) {
        sid = et_id.getText().toString();
        spw = et_pw.getText().toString();
        sname = et_name.getText().toString();
        sphone = et_phone.getText().toString();
        semail = et_email.getText().toString();

        // 아이디 입력 확인
        if( sid.length() == 0 ) {
            Toast.makeText(Regist.this, "아이디를 입력하세요.", Toast.LENGTH_SHORT).show();
            et_id.requestFocus();
            return;
        }
        // 비밀번호 입력 확인
        if( spw.length() == 0 ) {
            Toast.makeText(Regist.this, "비밀번호를 입력하세요.", Toast.LENGTH_SHORT).show();
            et_pw.requestFocus();
            return;
        }
        else {
            insertoToDatabase(sid, spw, sname, sphone, semail);
        }
    }

    private void insertoToDatabase(String sid, String spw, String sname, String sphone, String semail) {
        class InsertData extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Regist.this, "Please Wait", null, true, true);
            }
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
            }
            protected String doInBackground(String... params) {
                try {
                    String sid = (String) params[0];
                    String spw = (String) params[1];
                    String sname = (String) params[2];
                    String sphone = (String) params[3];
                    String semail = (String) params[4];

                    String link = ip+"regi.php";
                    String data = URLEncoder.encode("sid", "UTF-8") + "=" + URLEncoder.encode(sid, "UTF-8");
                    data += "&" + URLEncoder.encode("spw", "UTF-8") + "=" + URLEncoder.encode(spw, "UTF-8");
                    data += "&" + URLEncoder.encode("sname", "UTF-8") + "=" + URLEncoder.encode(sname, "UTF-8");
                    data += "&" + URLEncoder.encode("sphone", "UTF-8") + "=" + URLEncoder.encode(sphone, "UTF-8");
                    data += "&" + URLEncoder.encode("semail", "UTF-8") + "=" + URLEncoder.encode(semail, "UTF-8");

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
        task.execute(sid, spw, sname, sphone, semail);
    }
}



