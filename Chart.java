package com.example.minhe.pproject;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Chart extends AppCompatActivity {
    //String ip = "http://192.168.143.69/";
    private LineChart lineChart;
    public GettingPHP gPHP;
    private String caffid;
    private String ip;
    String url;

    // Declare Variables
    ListView list;
    ListViewAdapter adapter;
    EditText editsearch;
    public String[] rank = new String[10];
    public String[] country = new String[10];
    public String[] population = new String[10];
    ArrayList<WorldPopulation> arraylist = new ArrayList<WorldPopulation>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        Intent intent = getIntent();
        caffid = intent.getExtras().getString("caffID");
        ip = intent.getExtras().getString("ip");
        if (caffid.equals("1")) {
            url = ip+"data3.php";
        }
        else if (caffid.equals("2")) {
            url = ip+"data3_2.php";
        }
        else {
            url = ip+"data3.php";
        }

        adapter = new ListViewAdapter(this, arraylist);
        gPHP = new GettingPHP();
        gPHP.execute(url);

        ArrayList<Entry> entries = new ArrayList<>();

        entries.add(new Entry(4f, 0));
        entries.add(new Entry(8f, 1));
        entries.add(new Entry(6f, 2));
        entries.add(new Entry(2f, 3));
        entries.add(new Entry(18f, 4));
        entries.add(new Entry(9f, 5));
        entries.add(new Entry(16f, 6));
        entries.add(new Entry(5f, 7));
        entries.add(new Entry(3f, 8));
        entries.add(new Entry(7f, 10));
        entries.add(new Entry(9f, 11));
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
                LineChart lineChart = (LineChart) findViewById(R.id.chart);

                // PHP에서 받아온 JSON 데이터를 JSON오브젝트로 변환
                JSONObject jObject = new JSONObject(str);
                // results라는 key는 JSON배열로 되어있다.
                JSONArray results = jObject.getJSONArray("results");
                List<Entry> entries = new ArrayList<>();
                for ( int i = 0; i < results.length(); ++i ) {
                    JSONObject temp = results.getJSONObject(i);
                    rank[i] = String.valueOf(i+1);
                    country[i] = temp.get("date").toString();
                    population[i] = temp.get("Counting").toString();
                    /*
                    zz += "[ No. " + (i+1)+" ] ";
                    zz += "\nId : " + temp.get("Id");
                    zz += "\nCounting : " + temp.get("Counting");
                    zz += "\n----------------------------------------------------------------------------------------\n";
                    */
                    int con = Integer.parseInt(country[i].substring(country[i].length()-2, country[i].length()));
                    int pop = Integer.parseInt(population[i]);
                    entries.add(new Entry(con, pop));
                }
                LineDataSet dataset = new LineDataSet(entries, "예약 수");

        /*
        ArrayList<String> labels = new ArrayList<String>();
        labels.add("January");
        labels.add("February");
        labels.add("March");
        labels.add("April");
        labels.add("May");
        labels.add("June");
        labels.add("July");
        labels.add("August");
        labels.add("September");
        labels.add("October");
        labels.add("November");
        labels.add("December");

        LineData data = new LineData(dataset);
        dataset.setColors(ColorTemplate.COLORFUL_COLORS);
        dataset.setDrawFilled(true); //그래프 밑부분 색칠
        */

                dataset.setLineWidth(2);
                dataset.setCircleRadius(6);
                dataset.setCircleColor(Color.parseColor("#FFA1B4DC"));
                dataset.setCircleColorHole(Color.BLUE);
                dataset.setColor(Color.parseColor("#FFA1B4DC"));
                dataset.setDrawCircleHole(true);
                dataset.setDrawCircles(true);
                dataset.setDrawHorizontalHighlightIndicator(false);
                dataset.setDrawHighlightIndicators(false);
                dataset.setDrawValues(false);

                LineData lineData = new LineData(dataset);
                lineChart.setData(lineData);

                XAxis xAxis = lineChart.getXAxis();

                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                xAxis.setTextColor(Color.BLACK);
                xAxis.enableGridDashedLine(3, 14, 0);

                YAxis yLAxis = lineChart.getAxisLeft();
                yLAxis.setTextColor(Color.BLACK);

                YAxis yRAxis = lineChart.getAxisRight();
                yRAxis.setDrawLabels(false);
                yRAxis.setDrawAxisLine(false);
                yRAxis.setDrawGridLines(false);

                Description description = new Description();
                description.setText("");

                lineChart.setDoubleTapToZoomEnabled(false);
                lineChart.setDrawGridBackground(false);
                lineChart.setDescription(description);
                lineChart.animateY(2000, Easing.EasingOption.EaseInCubic);
                lineChart.invalidate();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}


