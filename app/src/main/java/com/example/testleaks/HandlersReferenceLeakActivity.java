package com.example.testleaks;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

public class HandlersReferenceLeakActivity extends AppCompatActivity {
    private TextView textView;

    /*
     * Mistake Number 1
     * */
    private Handler leakyHandler = new Handler();

    //    /*
//     * Fix number I
//     * */
//    private final LeakyHandler leakyHandler = new LeakyHandler(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handlers_reference_leak);
        /*
         * Mistake Number 2
         * */
        leakyHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                textView.setText(getString(R.string.text_handler_1));
            }
        }, 5000);
    }
}
//    How to solve this?
//    Option 1: NEVER reference a class inside the activity. If we definitely
//    need to, we should set the class as static.
//    This is because when a Handler is instantiated on the main thread,
//    it is associated with the Looper’s message queue.
//    Messages posted to the message queue will hold a reference to the Handler so that the framework can call Handler#handleMessage(Message) when the Looper eventually processes the message.
//    Option 2: Use a weakReference of the Activity.
/*
 * Fix number II - define as static
 * */
//private static class LeakyHandler extends Handler {
//
//    /*
//     * Fix number III - Use WeakReferences
//     * */
//    private WeakReference<HandlersReferenceLeakActivity> weakReference;
//    public LeakyHandler(HandlersReferenceLeakActivity activity) {
//        weakReference = new WeakReference<>(activity);
//    }
//
//    @Override
//    public void handleMessage(Message msg) {
//        HandlersReferenceLeakActivity activity = weakReference.get();
//        if (activity != null) {
//            activity.textView.setText(activity.getString(R.string.text_handler_2));
//        }
//    }
//}
//
//    private static final Runnable leakyRunnable = new Runnable() {
//        @Override
//        public void run() { /* ... */ }
//    }
//}
