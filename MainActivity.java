package com.example.minhe.pproject;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    // EditText inputID, inputPW;
    private EditText editTextStudentID;
    private EditText editTextPassword;
    private CheckBox ckbox;
    String studentid;
    String password;

    public static final String USER_NAME = "USER_NAME";
    public static final String ip = "ip";
    String l_ip = "http://192.168.0.12/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextStudentID = (EditText) findViewById(R.id.editText);
        editTextPassword = (EditText) findViewById(R.id.editText2);
        ckbox = (CheckBox) findViewById(R.id.checkBox);
    }

    // 로그인 값 전달
    public void invokeLogin(View view) {
        studentid = editTextStudentID.getText().toString();
        password = editTextPassword.getText().toString();

        // 아이디 입력 확인
        if (studentid.length() == 0) {
            Toast.makeText(MainActivity.this, "아이디를 입력하세요!", Toast.LENGTH_SHORT).show();
            editTextStudentID.requestFocus();
            return;
        }
        // 비밀번호 입력 확인
        if (password.length() == 0) {
            Toast.makeText(MainActivity.this, "비밀번호를 입력하세요!", Toast.LENGTH_SHORT).show();
            editTextPassword.requestFocus();
            return;
        }
        if (ckbox.isChecked() == true) {
            login_r(studentid, password);
        } else {
            login(studentid, password);
        }
    }

    // 관리자 로그인
    private void login_r(final String studentid, String password) {
        class LoginAsync extends AsyncTask<String, Void, String> {
            private Dialog loadingDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loadingDialog = ProgressDialog.show(MainActivity.this, "Please wait", "Loading...");
            }

            @Override
            protected String doInBackground(String... params) {
                String studentid = params[0];
                String pass = params[1];

                InputStream is = null;
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("studentid", studentid));
                nameValuePairs.add(new BasicNameValuePair("password", pass));
                String result = null;

                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(
                            l_ip+"post_root.php");
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
                    Intent intent = new Intent(MainActivity.this, Choice_m.class);
                    intent.putExtra(USER_NAME, studentid);
                    intent.putExtra(ip, l_ip);
                    finish();
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "등록되지 않은 학번 / 비밀번호 입니다.", Toast.LENGTH_LONG).show();
                }
            }
        }

        LoginAsync la = new LoginAsync();
        la.execute(studentid, password);

    }

    // 회원 로그인
    private void login(final String studentid, String password) {
        class LoginAsync extends AsyncTask<String, Void, String> {
            private Dialog loadingDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loadingDialog = ProgressDialog.show(MainActivity.this, "Please wait", "Loading...");
            }

            @Override
            protected String doInBackground(String... params) {
                String studentid = params[0];
                String pass = params[1];

                InputStream is = null;
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("studentid", studentid));
                nameValuePairs.add(new BasicNameValuePair("password", pass));
                String result = null;

                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(
                            l_ip+"post.php");
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
                    Intent intent = new Intent(MainActivity.this, choice.class);
                    intent.putExtra(USER_NAME, studentid);
                    intent.putExtra(ip, l_ip);
                    finish();
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "등록되지 않은 학번 / 비밀번호 입니다.", Toast.LENGTH_LONG).show();
                }
            }
        }
        LoginAsync la = new LoginAsync();
        la.execute(studentid, password);
    }

    // 회원가입
    public void onClick1(View view)
    {
        Intent intent1 = new Intent(this, Regist.class);
        intent1.putExtra(ip, l_ip);
        startActivity(intent1);
    }
}
