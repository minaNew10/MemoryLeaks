package com.example.testleaks;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import java.lang.ref.WeakReference;

public class InnerClassReferenceLeakActivity extends AppCompatActivity {

    /*
     * Mistake Number 1:
     * Never create a static variable of an inner class
     * Fix I:
     * private LeakyClass leakyClass;
     */
    private static LeakyClass leakyClass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inner_class_reference_leak);
        new LeakyClass(this).redirectToSecondScreen();

        /*
         * Inner class is defined here
         * */
        leakyClass = new LeakyClass(this);
        leakyClass.redirectToSecondScreen();
    }

    /*
     * Mistake Number 2:
     * 1. Never create a inner variable of an inner class
     * 2. Never pass an instance of the activity to the inner class
     */
    private class LeakyClass {

        private Activity activity;
        public LeakyClass(Activity activity) {
            this.activity = activity;
        }

        public void redirectToSecondScreen() {
            this.activity.startActivity(new Intent(activity, SingletonLeakExampleActivity.class));
        }
    }
    /*
     * How to fix the above class:
     * Fix memory leaks:
     * Option 1: The class should be set to static
     * Explanation: Instances of anonymous classes do not hold an implicit reference to their outer class
     * when they are "static".
     *
     * Option 2: Use a weakReference of the textview or any view/activity for that matter
     * Explanation: Weak References: Garbage collector can collect an object if only weak references
     * are pointing towards it.
     * */
//    private static class LeakyClassSol {
//
//        private final WeakReference<Activity> messageViewReference;
//
//        public LeakyClassSol(Activity activity) {
//            this.messageViewReference = new WeakReference<>(activity);
//        }
//
//        public void redirectToSecondScreen() {
//            Activity activity = messageViewReference.get();
//            if (activity != null) {
//                activity.startActivity(new Intent(activity, SecondActivity.class));
//            }
//        }
//    }
}
