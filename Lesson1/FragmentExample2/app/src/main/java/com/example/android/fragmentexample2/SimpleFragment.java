package com.example.android.fragmentexample2;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 */
public class SimpleFragment extends Fragment {

    private final static int YES = 0;
    private final static int NO = 1;


    public SimpleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_simple,
                container, false);
        final RadioGroup rGroup = rootView.findViewById(R.id.rg1);
        final RatingBar rBar = rootView.findViewById(R.id.ratingBar);

        // Set radiogroup change listener
        rGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) {
                View radioButton = radioGroup.findViewById(id);
                int index = radioGroup.indexOfChild(radioButton);
                TextView tv = rootView.findViewById(R.id.tvFragmentHeader);

                switch (index) {
                    case YES:   // User chose 'yes'
                        tv.setText(R.string.yes_message);
                        break;
                    case NO:    // User chose 'no'
                        tv.setText(R.string.no_message);
                        break;
                    default:
                        // Do nothing
                        break;
                }
            }
        });

        // Rating bar change listener
        rBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                String toastMessage = getString(R.string.toast_rating) + String.valueOf(rating);
                Toast.makeText(rootView.getContext(), toastMessage,
                        Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;
    }

    public static SimpleFragment newInstance() {
        return new SimpleFragment();
    }

}
