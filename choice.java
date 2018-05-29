package com.example.minhe.pproject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import static com.example.minhe.pproject.MainActivity.USER_NAME;

public class choice extends AppCompatActivity implements View.OnClickListener {
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice);

        findViewById(R.id.textView13).setOnClickListener(this);
        findViewById(R.id.textView14).setOnClickListener(this);

        Intent intent = getIntent();
        name = intent.getExtras().getString("USER_NAME");
    }
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textView13:
                new AlertDialog.Builder(this)
                        .setTitle("드림 스터디카페")
                        .setMessage("▷드림 스터디카페\n▷010-7519-1737\n▷매일 00:00 - 24:00\n▷예약, 단체석, 무선 인터넷, 남/녀 화장실 구분")
                        .setNeutralButton("닫기", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dlg, int sumthin) {
                            }
                        })
                        .show();
                break;
            case R.id.textView14:
                new AlertDialog.Builder(this)
                        .setTitle("토즈 스터디카페")
                        .setMessage("▷토즈 스터디카페\n▷031-499-2229\n▷매일 09:00 - 02:00\n▷예약, 무선 인터넷, 남/녀 화장실 구분")
                        .setNeutralButton("닫기", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dlg, int sumthin) {
                            }
                        })
                        .show();
                break;
        }
    }

    public void onClick3(View view)
    {
        Intent intent1 = new Intent(this, Menu.class);
        intent1.putExtra(USER_NAME, name);
        startActivity(intent1);
    }


}
