package com.example.testleaks;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class ThreadReferenceLeakActivity extends AppCompatActivity {

    /*
     * Mistake Number 1: Do not use static variables
     * */
    private static LeakyThread thread;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_reference_leak);
        createThread();
        redirectToNewScreen();
    }
    private void createThread() {
        thread = new LeakyThread();
        thread.start();
    }

    private void redirectToNewScreen() {
        startActivity(new Intent(this, MainActivity.class));
    }


    /*
     * Mistake Number 2: Non-static anonymous classes hold an
     * implicit reference to their enclosing class.
     * */
    private class LeakyThread extends Thread {
        @Override
        public void run() {
            while (true) {
            }
        }
    }
//    How to solve this?
//    Option 1: Non-static anonymous classes hold an implicit reference to their enclosing class.
//    Option 2: Close thread in activity onDestroy() to avoid thread leak.
//public class ThreadReferenceLeakActivity extends AppCompatActivity {
//
//    /*
//     * FIX I: make variable non static
//     * */
//    private LeakyThread leakyThread = new LeakyThread();
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_first);
//
//        createThread();
//        redirectToNewScreen();
//    }
//
//
//    private void createThread() {
//        leakyThread.start();
//    }
//
//    private void redirectToNewScreen() {
//        startActivity(new Intent(this, SecondActivity.class));
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        // FIX II: kill the thread
//        leakyThread.interrupt();
//    }
//
//
//    /*
//     * Fix III: Make thread static
//     * */
//    private static class LeakyThread extends Thread {
//        @Override
//        public void run() {
//            while (!isInterrupted()) {
//            }
//        }
//    }
//}

}
