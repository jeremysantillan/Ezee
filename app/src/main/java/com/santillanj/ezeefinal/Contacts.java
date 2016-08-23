package com.santillanj.ezeefinal;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Contacts extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        mCall = (Button) findViewById(R.id.btnCall);
        mMessage = (Button) findViewById(R.id.btnMessage);

        mCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Contacts.this,Call.class);
                startActivity(intent);
            }
        });

        mMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Contacts.this,Sms.class);
                startActivity(intent);
            }
        });
    }


    private Button mCall;
    private Button mMessage;





}
