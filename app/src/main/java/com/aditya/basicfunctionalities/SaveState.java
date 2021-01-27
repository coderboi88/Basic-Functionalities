package com.aditya.basicfunctionalities;

import android.content.Context;
import android.content.SharedPreferences;


//Class to save the state of theme :- dark/Night
public class SaveState {
    Context context;
    SharedPreferences sharedPreferences;

    public SaveState(Context context ){
        this.context = context;
    }

    public void setState(boolean bvalue){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("bkey",bvalue);
        editor.apply();
    }

    public Boolean getState(){
        return sharedPreferences.getBoolean("bkey",false);
    }
}
