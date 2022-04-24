package com.example.bluetoothadapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener{

    private MenuItem menuItem;
    private BluetoothAdapter bluetoothAdapter;
    private final int  ENABLE_REQUEST = 10;

    private Animator mCurrentAnimatorEffect;
    public int mShortAnimationDurationEffect;
    private boolean isImageScaled = false;
    private static ImageView imgview;
    private static final int REQ_ENABLE_BT = 10;
    int image = R.drawable.map;
    private float mScale = 1f;
    int count = 0;
    float x1;
    float x2;
    float x3;

    private ScaleGestureDetector mScaleGestureDetector;
    private GestureDetector gestureDetector;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        imgview.setOnTouchListener(this);
        gestureDetector = new GestureDetector(this, new GestureListener());
        mScaleGestureDetector = new ScaleGestureDetector(this, new ScaleGestureDetector.
                SimpleOnScaleGestureListener() {
            @Override
            public boolean onScale(ScaleGestureDetector detector) {

                float scale = 1 - detector.getScaleFactor();
                float prevScale = mScale;
                mScale += scale;

                if (mScale > 10f)
                    mScale = 10f;

                ScaleAnimation scaleAnimation = new ScaleAnimation(1f / prevScale,
                        1f / mScale, 1f / prevScale, 1f / mScale,
                        detector.getFocusX(), detector.getFocusY());
                scaleAnimation.setDuration(0);
                scaleAnimation.setFillAfter(true);
                imgview.startAnimation(scaleAnimation);
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        menuItem = menu.findItem(R.id.btEn);
        setBTicon();
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.btEn) {
            if (!bluetoothAdapter.isEnabled()) {
                enadbleBT();
            } else {
                bluetoothAdapter.disable();
                menuItem.setIcon(R.drawable.bluetooth_disable);
            }
        }
            else if(item.getItemId() == R.id.menu){
                if(bluetoothAdapter.isEnabled()) {
                    Intent intent = new Intent(MainActivity.this, ListActivity.class);
                    startActivity(intent);
                }
            }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == ENABLE_REQUEST){
            if(requestCode == RESULT_OK);
            setBTicon();
        }
    }

    private void init(){
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        imgview = (ImageView) findViewById(R.id.imageView);
    }

    private void setBTicon(){
        if(bluetoothAdapter.isEnabled()){
            menuItem.setIcon(R.drawable.bluetooth_disable);
        }
        else {
            menuItem.setIcon(R.drawable.bluetooth_enable);
        }
    }

    private void enadbleBT(){
        Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        startActivityForResult(intent, ENABLE_REQUEST );
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        super.dispatchTouchEvent(event);

        mScaleGestureDetector.onTouchEvent(event);
        gestureDetector.onTouchEvent(event);
        return gestureDetector.onTouchEvent(event);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        view.onTouchEvent(motionEvent);
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_MOVE:
                if(count == 0){
                    x1 = motionEvent.getX();
                    count++;
                }
                if(count == 1){
                    x2 = motionEvent.getX();
                   if( x1-x2 >= view.getWidth()/4 || x1-x2 <= -view.getWidth()/4 ) {
                       if (x1 - x2 > 60) {
                           x3 = 45;
                           startLeftRotation();
                           count = 3;
                       }

                       if (x1 - x2 < 60) {
                           System.out.println(x1 - x2);
                           x3 = 45;
                           startRightRotation();
                           count = 3;
                       }
                   }
                }
                if(count == 3){
                    count = 0;
                }
                break;
        }
        return true;
    }

    private void startRightRotation() {
        imgview.animate().rotation(imgview.getRotation() - x3);
        imgview=(ImageView) findViewById(R.id.imageView);
    }

    private void startLeftRotation() {
        imgview.animate().rotation(imgview.getRotation() + x3);
        imgview=(ImageView) findViewById(R.id.imageView);
    }

    private class GestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            return true;
        }
    }
}