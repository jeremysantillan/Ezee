package com.santillanj.ezeefinal;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;



public class Sms extends AppCompatActivity {


    private Button mSend;
    private Button mContacts;
    private EditText mTo;
    private EditText mText;
    private TextView mName;

    String message, to;
    private int PICK_CONTACT = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);

        mSend = (Button) findViewById(R.id.btnSend);
        mContacts = (Button) findViewById(R.id.btnContacts);
        mText = (EditText) findViewById(R.id.etText);
        mTo = (EditText) findViewById(R.id.etTo);
        mName = (TextView) findViewById(R.id.txtName);

        mTo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {
                mName.setText("");
                if (mTo.getText().toString().equals("")) {
                    mSend.setEnabled(false);
                }
                else {
                    mSend.setEnabled(true);
                }
            }
        });


        mContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(intent, PICK_CONTACT);

            }
        });

        mSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                to = mTo.getText().toString();
                message = mText.getText().toString();

                Intent smsIntent = new Intent(android.content.Intent.ACTION_VIEW);
                smsIntent.setType("vnd.android-dir/mms-sms");
                smsIntent.putExtra("address",to);
                smsIntent.putExtra("sms_body",message);
                startActivity(smsIntent);


            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch(requestCode){
            case 1: {

                if (resultCode == Activity.RESULT_OK)
                {

                    Uri contactData = data.getData();
                    Cursor c = managedQuery(contactData, null, null, null, null);
                    if (c.moveToFirst())
                    {
                        String id = c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts._ID));

                        String hasPhone = c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

                        if (hasPhone.equalsIgnoreCase("1"))
                        {
                            Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,
                                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = "+ id,null, null);
                            phones.moveToFirst();
                            String cNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                            String nameContact = c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME));
                            Toast.makeText(getApplicationContext(), nameContact, Toast.LENGTH_SHORT).show();

                            set(nameContact, cNumber);

                        }
                    }
                }
            }
        }
    }

    public  void set(String name, String number){



        if (mTo.getText().toString()!=null){
            mName.setText(name);
            mTo.append(number+"; ");
          mTo.setTextColor(getResources().getColor(R.color.colorAccent));
            mSend.setEnabled(true);
            mName.setText(name);

        }


    }


}
