package com.aditya.basicfunctionalities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
