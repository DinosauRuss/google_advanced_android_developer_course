package com.example.android.fragmentcommunicate;


import android.content.Context;
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
    private static final int NONE = 2;

    private RadioGroup rGroup;
    private RatingBar rBar;

    public int mRadioButtonChoice = NONE;
    OnFragmentInteractionListener mListener;
    private static final String CHOICE = "choice";


    public SimpleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_simple,
                container, false);

        rGroup = rootView.findViewById(R.id.rg1);
        rBar = rootView.findViewById(R.id.ratingBar);

        if ( getArguments().containsKey(CHOICE) ) {
            // A choice was made, get it
            mRadioButtonChoice = getArguments().getInt(CHOICE);
            // Check the choice
            if ( mRadioButtonChoice != NONE ) {
                rGroup.check(
                        rGroup.getChildAt(mRadioButtonChoice).getId() );
            }
        }

        // Add radiogroup change listener
        radioGroupListen(rootView);
        // Add rating bar change listener
        ratingBarListen(rootView);

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // Verify host Activity has implemented OnFragmentListener
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new ClassCastException( context.toString()
                    + getResources().getString(R.string.exception_message) );
        }
    }

    public static SimpleFragment newInstance(int choice) {
        SimpleFragment frago = new SimpleFragment();
        Bundle args = new Bundle();
        args.putInt(CHOICE, choice);
        frago.setArguments(args);

        return frago;
    }

    /**
     * Add Change listener to RadioGroup
     * @param v The fragemnt View
     */
    private void radioGroupListen(final View v) {
        rGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) {
                View radioButton = radioGroup.findViewById(id);
                int index = radioGroup.indexOfChild(radioButton);
                TextView tv = v.findViewById(R.id.tvFragmentHeader);

                switch (index) {
                    case YES:   // User chose 'yes'
                        tv.setText(R.string.yes_message);
                        mRadioButtonChoice = YES;
                        mListener.onRadioButtonChoice(YES);
                        break;
                    case NO:    // User chose 'no'
                        tv.setText(R.string.no_message);
                        mRadioButtonChoice = NO;
                        mListener.onRadioButtonChoice(NO);
                        break;
                    default:    // No choice made
                        mRadioButtonChoice = NONE;
                        mListener.onRadioButtonChoice(NONE);
                        break;
                }
            }
        });
    }

    /**
     * Add Change listener to RatingBar
     * @param v The fragemnt View
     */
    private void ratingBarListen(final View v) {
        rBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                String toastMessage = getString(R.string.toast_rating) + String.valueOf(rating);
                Toast.makeText(v.getContext(), toastMessage,
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    interface OnFragmentInteractionListener {

        void onRadioButtonChoice(int choice);
    }

}
