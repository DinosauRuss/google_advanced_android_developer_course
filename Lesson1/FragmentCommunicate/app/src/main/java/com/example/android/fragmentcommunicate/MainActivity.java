/*
 * Copyright (C) 2017 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.fragmentcommunicate;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements
        SimpleFragment.OnFragmentInteractionListener {

    private Button btnOpen;

    // Radio button choice, default value
    private int mRadioButtonChoice = 2;

    // Flag if fragment is currently displayed
    private boolean fragmentCurrentlyDisplayed = false;

    // Key for savedInstanceState
    static final String STATE_FRAGMENT = "state_of_fragment";

    // For Logging
    private final static String TAG = "something";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnOpen = findViewById(R.id.btnOpen);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.d(TAG, "OnSave");
        // Save the state of the fragment displayed boolean
        outState.putBoolean(STATE_FRAGMENT, fragmentCurrentlyDisplayed);

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        Log.d(TAG, "OnRestore");
        fragmentCurrentlyDisplayed = savedInstanceState.getBoolean(STATE_FRAGMENT);

        if (fragmentCurrentlyDisplayed) {
            btnOpen.setText(R.string.close);
        }

        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onRadioButtonChoice(int choice) {
        // Save radio button choice to send back to fragment
        mRadioButtonChoice = choice;

        String toastMessage = "Choice is: " + Integer.toString(choice);
        Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show();
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
     * Open an instance of SimpleFragment
     */
    public void displayFragment() {
        SimpleFragment frago = SimpleFragment.newInstance(mRadioButtonChoice);

        // Get fragment manager and start transaction
        FragmentManager fragManager = getFragmentManager();
        FragmentTransaction fragTransaction = fragManager.beginTransaction();

        // Add the SimpleFragment
        fragTransaction.add(R.id.frameLayoutFragmentContainer,
                frago).addToBackStack(null).commit();

        // Update the button text
        btnOpen.setText(R.string.close);

        // Set flag to indicate fragment is open
        fragmentCurrentlyDisplayed = true;
    }

    /**
     * Close an open instance of SimpleFragment
     */
    public void closeFragment() {
        // Get the fragment manager
        FragmentManager fragManager = getFragmentManager();

        // Check if fragment is currently displayed
        SimpleFragment simplo = (SimpleFragment) fragManager
                .findFragmentById(R.id.frameLayoutFragmentContainer);
        if (simplo != null) {
            // Create and commit transaction to remove fragment
            FragmentTransaction fragTransaction = fragManager.beginTransaction();
            fragTransaction.remove(simplo).commit();

            // Update button text
            btnOpen.setText(R.string.open);

            // Set flag to indicate fragment is open
            fragmentCurrentlyDisplayed = false;
        }
    }

    /**
     * Button callback to open SecondActivity
     * @param view  The button which called this method
     */
    public void launchSecondActivity(View view) {
        Intent intento = new Intent(this, SecondActivity.class);
        startActivity(intento);
    }

}
