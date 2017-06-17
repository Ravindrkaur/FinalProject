package com.example.aman.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by Aman on 5/4/2017.
 */

public class EmployeeActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btn1,btn2,btn3;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.employee_fragment);
         btn1= (Button) findViewById(R.id.button1);
        btn2= (Button) findViewById(R.id.button2);
        btn3= (Button) findViewById(R.id.button3);

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button1:
                Toast.makeText(EmployeeActivity.this,"Employee logout successfully",Toast.LENGTH_LONG);
                Intent intent1=new Intent(EmployeeActivity.this,LoginActivity.class);
                startActivity(intent1);

            case R.id.button2:

               // Intent intent=new Intent(EmployeeActivity.this,MyInformation1.class);
                break;
            case R.id.button3:
                Intent intent=new Intent(EmployeeActivity.this,MyInformation1.class);
                startActivity(intent);
                break;
        }
    }
}
