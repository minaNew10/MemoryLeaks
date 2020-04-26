package com.example.testleaks;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

public class AsyncTaskReferenceLeakActivity extends AppCompatActivity {
    private TextView textView;
    private BackgroundTask backgroundTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_task_reference_leak);
        textView = findViewById(R.id.txtv);
        /*
         * Executing AsyncTask here!
         * */
        backgroundTask = new BackgroundTask(textView);
        backgroundTask.execute();
    }
    /*
     * Couple of things we should NEVER do here:
     * Mistake number 1. NEVER reference a class inside the activity. If we definitely need to, we should set the class as static as static inner classes don’t hold
     *    any implicit reference to its parent activity class
     * Mistake number 2. We should always cancel the asyncTask when activity is destroyed. This is because the asyncTask will still be executing even if the activity
     *    is destroyed.
     * Mistake number 3. Never use a direct reference of a view from acitivty inside an asynctask.
     * */
//    How to solve this?
//    Option 1: We should always cancel the asyncTask when activity is destroyed. This is because the asyncTask will still be executing even if the activity is destroyed.
//    Option 2: NEVER reference a class inside the activity. If we definitely need to, we should set the class as static as static inner classes don’t hold any implicit reference to its parent activity class.
//    Option 3: Use a weakReference of the textview.
    private class BackgroundTask extends AsyncTask<Void, Void, String> {
        TextView textView;

        public BackgroundTask(TextView textView) {
            this.textView = textView;
        }

        @Override
        protected String doInBackground(Void... voids) {

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "The task is completed!";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            textView.setText(s);
        }
    }
}
/*
 * Fix number 1
 * */
//    private static class BackgroundTask extends AsyncTask<Void, Void, String> {
//
//        private final WeakReference<TextView> messageViewReference;
//        private BackgroundTask(TextView textView) {
//            this.messageViewReference = new WeakReference<>(textView);
//        }
//
//
//        @Override
//        protected String doInBackground(Void... voids) {
//
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            return "The task is completed!";
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
//            super.onPostExecute(s);
//            /*
//             * Fix number 3
//             * */
//            TextView textView = messageViewReference.get();
//            if(textView != null) {
//                textView.setText(s);
//            }
//        }
//    }
//
//        @Override
//        protected void onDestroy() {
//            super.onDestroy();
//
//            /*
//             * Fix number 2
//             * */
//            if(backgroundTask != null) {
//                backgroundTask.cancel(true);
//            }
//        }
//    }


