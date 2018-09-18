package com.example.minhe.pproject;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;

public class Rank extends AppCompatActivity {
    //String ip = "http://192.168.143.69/";
    String url;
    public GettingPHP gPHP;
    private String caffid;
    private String ip;

    // Declare Variables
    ListView list;
    ListViewAdapter adapter;
    EditText editsearch;
    public String[] rank = new String[17];
    public String[] country = new String[17];
    public String[] population = new String[17];
    ArrayList<WorldPopulation> arraylist = new ArrayList<WorldPopulation>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);
        Intent intent = getIntent();
        caffid = intent.getExtras().getString("caffID");
        ip = intent.getExtras().getString("ip");
        if (caffid.equals("1")) {
            url = ip+"data2.php";
        }
        else if (caffid.equals("2")) {
            url = ip+"data2_2.php";
        }
        else {
            url = ip+"data2.php";
        }
        adapter = new ListViewAdapter(this, arraylist);

        gPHP = new GettingPHP();
        gPHP.execute(url);

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
                for ( int i = 0; i < results.length(); ++i ) {
                    JSONObject temp = results.getJSONObject(i);
                    rank[i] = String.valueOf(i+1);
                    country[i] = temp.get("Id").toString();
                    population[i] = temp.get("Counting").toString();
                    /*
                    zz += "[ No. " + (i+1)+" ] ";
                    zz += "\nId : " + temp.get("Id");
                    zz += "\nCounting : " + temp.get("Counting");
                    zz += "\n----------------------------------------------------------------------------------------\n";
                    */
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            // Locate the ListView in listview_main.xml
            list = (ListView) findViewById(R.id.listview);

            for (int i = 0; i < rank.length; i++) {
                WorldPopulation wp = new WorldPopulation(rank[i], country[i], population[i]);
                arraylist.add(wp);
            }

            // Binds the Adapter to the ListView
            list.setAdapter(adapter);

            // Locate the EditText in listview_main.xml
            editsearch = (EditText) findViewById(R.id.search);

            // Capture Text in EditText
            editsearch.addTextChangedListener(new TextWatcher() {

                @Override
                public void afterTextChanged(Editable arg0) {
                    // TODO Auto-generated method stub
                    String text = editsearch.getText().toString();
                    adapter.filter(text);
                }

                @Override
                public void beforeTextChanged(CharSequence arg0, int arg1,
                                              int arg2, int arg3) {
                    // TODO Auto-generated method stub
                }

                @Override
                public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                    // TODO Auto-generated method stub
                }
            });
        }
    }
}
