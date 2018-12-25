package com.example.android.fragmentexample2;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.android.fragmentexample2.R;
import com.example.android.fragmentexample2.SimpleFragment;

public class SecondActivity extends AppCompatActivity {

    private TextView tvFragStatus;
    private boolean fragmentCurrentlyDisplayed = false;

    static final String STATE_FRAGMENT = "state_of_fragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        tvFragStatus = findViewById(R.id.tvFragStatus);

        if (savedInstanceState != null) {
            fragmentCurrentlyDisplayed = savedInstanceState.getBoolean(STATE_FRAGMENT);

            if (fragmentCurrentlyDisplayed) {
                tvFragStatus.setText(R.string.frag_open);
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(STATE_FRAGMENT, fragmentCurrentlyDisplayed);

        super.onSaveInstanceState(outState);
    }


    /**
     * Button callback to toggle display of SimpleFragment instance
     * @param view  The button which called this method
     */
    public void openCloseFragment(View view) {
        if (fragmentCurrentlyDisplayed) {
            closeFragment();
        } else {
            displayFragment();
        }
    }

    /**
     * Display an instance of SimpleFragment
     */
    public void displayFragment() {
        // Fragment
        SimpleFragment frago = SimpleFragment.newInstance();

        // Get fragment manager and transaction
        FragmentManager fragManager = getFragmentManager();
        FragmentTransaction fragTransaction = fragManager.beginTransaction();
        // Add transaction
        fragTransaction.add(R.id.secondActivityFragContainer, frago)
                .addToBackStack(null).commit();

        // Update text
        tvFragStatus.setText(R.string.frag_open);

        // Set flag
        fragmentCurrentlyDisplayed = true;
    }

    /**
     * Close an instance of SimpleFragment
     */
    public void closeFragment() {
        // Get fragment manager
        FragmentManager fragManager = getFragmentManager();

        // Check if fragment is currently displayed
        SimpleFragment simplo = (SimpleFragment) fragManager
                .findFragmentById(R.id.secondActivityFragContainer);
        if (simplo != null) {
            // Create and commit transaction to remove fragment
            FragmentTransaction fragTransaction = fragManager.beginTransaction();
            fragTransaction.remove(simplo).commit();

            // Update text
            tvFragStatus.setText(R.string.frag_not_open);

            // Set flag
            fragmentCurrentlyDisplayed = false;
        }
    }

}
