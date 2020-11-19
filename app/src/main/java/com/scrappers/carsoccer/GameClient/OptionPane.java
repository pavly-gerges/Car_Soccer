package com.scrappers.carsoccer.GameClient;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class OptionPane {

    private final AppCompatActivity context;
    private View inflater;
    private AlertDialog alertDialog;

    public OptionPane(@NonNull AppCompatActivity context){
        this.context=context;
    }
    public void showDialog(int designedLayout, int gravity){
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        inflater=context.getLayoutInflater().inflate(designedLayout,null);
        builder.setView(inflater);

        alertDialog=builder.create();
        assert  alertDialog.getWindow() !=null;
        alertDialog.getWindow().setGravity(gravity);
        alertDialog.show();
    }
    @NonNull
    public AlertDialog getAlertDialog() {
        return alertDialog;
    }
    @NonNull
    public View getInflater() {
        return inflater;
    }
}
