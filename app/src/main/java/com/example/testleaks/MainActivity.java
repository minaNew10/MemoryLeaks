package com.example.testleaks;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.lang.ref.WeakReference;

public class MainActivity extends AppCompatActivity {
    MyAsyncTask myAsyncTask;
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.go).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(myAsyncTask != null){
                    finish();
                }
                myAsyncTask = new MyAsyncTask(MainActivity.this);
                myAsyncTask.execute();
            }
        });
    }

    @Override
    protected void onDestroy() {
        //we can cancel the async task to avoid memory leak
        myAsyncTask.cancel(true);
        super.onDestroy();
        Log.i(TAG, "onDestroy: ");
    }

    private static class MyAsyncTask extends AsyncTask<Void,Void,Void>{
        //this is how to slove in case we don't end the asynctask in onDestroy
//        private WeakReference<Context> context;
        private Context context;
        public MyAsyncTask(Context context) {
//            this.context = new WeakReference<>(context);
            this.context = context;
        }

        @Override
        protected Void doInBackground(Void... voids) {
//            Bitmap bitmap = BitmapFactory.decodeResource(context.get().getResources(),R.drawable.ic_launcher_background);
            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),R.drawable.ic_launcher_background);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }
        
        
    }
}
