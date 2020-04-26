package com.example.testleaks;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;

public class TimerTaskReferenceLeakActivity extends AppCompatActivity {
    private CountDownTimer countDownTimer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer_task_reference_leak);
        startTimer();
    }
    /*
     * Mistake 1: Cancel Timer is never called
     * even though activity might be completed
     * */
    public void cancelTimer() {
        if(countDownTimer != null) countDownTimer.cancel();
    }


    private void startTimer() {
        countDownTimer = new CountDownTimer(1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                final int secondsRemaining = (int) (millisUntilFinished / 1000);
                //update UI
            }

            @Override
            public void onFinish() {
                //handle onFinish
            }
        };
        countDownTimer.start();
    }
    /*
     * Fix 1: Cancel Timer when
     * activity might be completed
     * */
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        cancelTimer();
//    }
//}
//    So basically to summarise:
//        1. Use applicationContext() instead of activity context when possible. If you really have to use activity context, then when the activity is destroyed, ensure that the context you passed to the class is set to null.
//        2. Never use static variables to declare views or activity context.
//        3. Never reference a class inside the activity. If we need to, we should declare it as static, whether it is a thread or a handler or a timer or an asyncTask.
//        4. Always make sure to unregister broadcastReceivers or timers inside the activity. Cancel any asyncTasks or Threads inside onDestroy().
//        5. Always use a weakReference of the activity or view when needed.