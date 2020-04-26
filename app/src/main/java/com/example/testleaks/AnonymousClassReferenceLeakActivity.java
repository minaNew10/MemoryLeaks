package com.example.testleaks;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.lang.ref.WeakReference;

public class AnonymousClassReferenceLeakActivity extends AppCompatActivity {
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anonymous_class_reference_leak);
        textView = findViewById(R.id.activity_text);
//        textView.setText(getString(R.string.text_inner_class_1));
//        findViewById(R.id.activity_dialog_btn).setVisibility(View.INVISIBLE);

        /*
         * Runnable class is defined here
         * */
        new Thread(new LeakyRunnable(textView)).start();

    }

    private static class LeakyRunnable implements Runnable {

        private final WeakReference<TextView> messageViewReference;

        private LeakyRunnable(TextView textView) {
            this.messageViewReference = new WeakReference<>(textView);
        }

        @Override
        public void run() {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            TextView textView = messageViewReference.get();
            if (textView != null) {
                textView.setText("Runnable class has completed its work");
            }
        }
    }
}