package com.aditya.basicfunctionalities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    MediaPlayer mediaPlayer;
    private Switch aSwitch;
    private saveState saveState;

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

        setContentView(R.layout.activity_main);

        aSwitch = (Switch) findViewById(R.id.switch1);
        /*if(saveState.getState()==true){
            aSwitch.setChecked(true);
        }*/
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    saveState.setState(true);
                    getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
                else{
                    saveState.setState(false);
                    getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                }
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

    public void play(View view) {
        mediaPlayer.start();
    }

    public void nextActivity(View view) {
        Intent i = new Intent(MainActivity.this,PasswordSaverActivity.class);
        startActivity(i);
    }
}
