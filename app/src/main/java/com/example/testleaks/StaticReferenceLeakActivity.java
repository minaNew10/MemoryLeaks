package com.example.testleaks;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class StaticReferenceLeakActivity extends AppCompatActivity {

    /*
     * This is a bad idea!
     */
    private static TextView textView;
    private static Activity activity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_static_reference_leak);


        textView = findViewById(R.id.activity_text);
        textView.setText("Bad Idea!");

        activity = this;
    }
//    How to solve this? Always remember to NEVER use static variables
//    for views or activities or contexts.
}
