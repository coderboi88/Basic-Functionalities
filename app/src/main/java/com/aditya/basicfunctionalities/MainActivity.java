package com.aditya.basicfunctionalities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.annotation.TargetApi;
import android.app.PictureInPictureParams;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.util.Rational;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.roger.catloadinglibrary.CatLoadingView;

public class MainActivity extends AppCompatActivity {

    MediaPlayer mediaPlayer;
    private Switch aSwitch;
    private Button laodCustomView;
    private SaveState saveState;

    CatLoadingView catLoadingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*saveState = new saveState(this);
        if(saveState.getState()==true){
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        else{
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }*/

        if(AppCompatDelegate.getDefaultNightMode()==AppCompatDelegate.MODE_NIGHT_YES){
            setTheme(R.style.DarkTheme);
        }
        else{
            setTheme(R.style.AppTheme);
        }

        setContentView(R.layout.activity_main);

        aSwitch = (Switch) findViewById(R.id.switch1);
        /*if(saveState.getState()==true){
            aSwitch.setChecked(true);
        }*/

        if(AppCompatDelegate.getDefaultNightMode()==AppCompatDelegate.MODE_NIGHT_YES){
            aSwitch.setChecked(true);
        }

        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    //saveState.setState(true);
                    //getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    reset();
                }
                else{
                    //saveState.setState(false);
                    //getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    reset();
                }
            }
        });

        catLoadingView = new CatLoadingView();
        laodCustomView = (Button) findViewById(R.id.customLoadview);
        laodCustomView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                catLoadingView.show(getSupportFragmentManager(),"");
            }
        });
        mediaPlayer = MediaPlayer.create(this,R.raw.audio);
        IntentFilter intentFilter =  new IntentFilter(Intent.ACTION_HEADSET_PLUG);
        BroadcastReceiver audio = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if(action.equals(Intent.ACTION_HEADSET_PLUG)){
                    int state = intent.getIntExtra("state",-1);
                    switch(state){
                        case 0:
                            Toast.makeText(context, "HeadsetUnpluged", Toast.LENGTH_SHORT).show();
                            mediaPlayer.pause();
                            break;
                        case 1:
                            Toast.makeText(context, "Headset Plugged", Toast.LENGTH_SHORT).show();
                            mediaPlayer.start();
                            break;
                    }
                }
            }
        };
        this.registerReceiver(audio,intentFilter);
    }

    private void reset() {
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void play(View view) {
        mediaPlayer.start();
    }

    public void nextActivity(View view) {
        Intent i = new Intent(MainActivity.this,PasswordSaverActivity.class);
        startActivity(i);
    }

    //@androidx.annotation.RequiresApi(api = android.os.Build.VERSION_CODES.O)
    public void pipMode(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            Display d = getWindowManager().getDefaultDisplay();
            Point p = new Point();
            d.getSize(p);
            int width = p.x;
            int height = p.y;

            Rational ratio = new Rational(width,height);
            PictureInPictureParams.Builder builder = new PictureInPictureParams.Builder();
            builder.setAspectRatio(ratio).build();
            enterPictureInPictureMode(builder.build());
        }

    }

}
