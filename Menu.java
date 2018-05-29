package com.example.minhe.pproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import static com.example.minhe.pproject.MainActivity.USER_NAME;

public class Menu extends AppCompatActivity {
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Intent intent = getIntent();
        name = intent.getExtras().getString("USER_NAME");
    }

    public void onClick5(View view)
    {
        Intent intent1 = new Intent(this, Reserve.class);
        intent1.putExtra(USER_NAME, name);
        startActivity(intent1);
    }

    public void onClick6(View view)
    {
        Intent intent4 = new Intent(this,Myinfo.class);
        intent4.putExtra(USER_NAME, name);
        startActivity(intent4);
    }

    public void chat(View view)
    {
        Intent intent5 = new Intent(this,Chat.class);
        intent5.putExtra(USER_NAME, name);
        startActivity(intent5);
    }

    public void onClick8(View view)
    {
        Intent intent6 = new Intent(this,Commu.class);
        intent6.putExtra(USER_NAME, name);
        startActivity(intent6);
    }

}
