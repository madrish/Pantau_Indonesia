package com.pantauindonesia.app;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class FragmentGetting3 extends Fragment {
    Button btnRed, btnBlack;
    TextView tNext;
    RadioButton red, black;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_getting3, null);
        tNext = (TextView) view.findViewById(R.id.textNext);
        btnRed = (Button)view.findViewById(R.id.buttonRed);
        btnBlack = (Button)view.findViewById(R.id.buttonBlack);
        red = (RadioButton)view.findViewById(R.id.radioButtonRed);
        black = (RadioButton)view.findViewById(R.id.radioButtonBlack);
        btnRed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                red.setChecked(true);
                black.setChecked(false);

            }
        });
        btnBlack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                red.setChecked(false);
                black.setChecked(true);
            }
        });

        tNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(
                        getActivity(),
                        LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        return view;
    }

}