package com.fc.dialogdemo;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void normalDialog(View view) {

        DFragment dFragment = DFragment.createDFragment(DFragment.NORMAL_DIALOG);
        dFragment.show(getSupportFragmentManager(),"normal");

    }

    public void itemDialog(View view) {

        DFragment dFragment = DFragment.createDFragment(DFragment.ITEM_DIALOG);
        dFragment.show(getSupportFragmentManager(),"item");

    }

    public void singleChoiceDialog(View view) {

        DFragment dFragment = DFragment.createDFragment(DFragment.SINGLE_CHOICE_DIALOG);
        dFragment.show(getSupportFragmentManager(),"single");

    }

    public void multChoiceDialog(View view) {

        DFragment dFragment = DFragment.createDFragment(DFragment.MULTI_CHOICE_DIALOG);
        dFragment.show(getSupportFragmentManager(),"multi");

    }

    public void defineChoiceDialog(View view) {

        DFragment dFragment = DFragment.createDFragment(DFragment.DEFINE_DIALOG);
        dFragment.show(getSupportFragmentManager(),"define");

    }

    public void acDialog(View view) {

        startActivity(new Intent(this, DialogActivity.class));

    }
}
