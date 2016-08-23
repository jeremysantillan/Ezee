package com.santillanj.ezeefinal;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class Call extends AppCompatActivity {


    private int PICK_CONTACT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);

        mContacts = (Button) findViewById(R.id.btnContacts);
        mCall = (Button) findViewById(R.id.btnTawag);
      mName = (TextView) findViewById(R.id.txtName);
        mPhone = (EditText) findViewById(R.id.etNumber);

        mPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (mPhone.getText().toString().equals("")){
                    mCall.setEnabled(false);
                }
                else {
                    mCall.setEnabled(true);
                }
            }
        });

        mCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_call = new Intent(Intent.ACTION_CALL);
                intent_call.setData(Uri.parse("tel:" +mPhone.getText().toString()));
                if (intent_call.resolveActivity(getPackageManager())!=null){
                    startActivity(intent_call);
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



    }

    private Button mContacts;
    private Button mCall;
    private TextView mName;
    private EditText mPhone;


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



        if (mPhone.getText().toString()!=null){
            mPhone.setText(number+"; ");
            mPhone.setTextColor(getResources().getColor(R.color.colorAccent));
            mCall.setEnabled(true);
            mName.setText(name);


        }


    }

}