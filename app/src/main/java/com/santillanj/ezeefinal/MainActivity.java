package com.santillanj.ezeefinal;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;


public class MainActivity extends AppCompatActivity implements GestureDetector.OnGestureListener{

    private TextView mText;
    private GestureDetectorCompat mGesture;
    private File imageFile;
    private Uri tempuri;
    static final int REQUEST_IMAGE_CAPTURE = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        this.mGesture = new GestureDetectorCompat(this, this);

    }


    @Override
    public boolean onDown(MotionEvent motionEvent) {

        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {
        takePicture();
    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        Intent intent = new Intent(MainActivity.this,Contacts.class);
        startActivity(intent);
        return true;
    }
    @Override
    public boolean onTouchEvent (MotionEvent event){
        this.mGesture.onTouchEvent(event);
        return super.onTouchEvent(event);
    }



    public void takePicture() {

        Intent intent_takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        imageFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                "sample.jpg");
        tempuri = Uri.fromFile(imageFile);

        intent_takePicture.putExtra(MediaStore.EXTRA_OUTPUT,tempuri);

        if (intent_takePicture.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent_takePicture, REQUEST_IMAGE_CAPTURE);
        }
        else{
            Toast.makeText(this,"NO ACTIVITY WAS FOUND TO HANDLE THIS ACTION",
                    Toast.LENGTH_SHORT).show();

        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {

            Toast.makeText(this,"The file was saved at"+imageFile.getAbsolutePath(),
                    Toast.LENGTH_LONG).show();

            Intent intent_share = new Intent(Intent.ACTION_SEND);
            intent_share.setType("image/*");
            intent_share.putExtra(Intent.EXTRA_STREAM,tempuri);
            startActivity(Intent.createChooser(intent_share,"Share image using"));
        }
    }
}




