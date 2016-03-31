package com.pantauindonesia.app;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseObject;
import com.parse.ParseUser;

public class TabLapor extends Fragment {
String username , userId, kepada, judul, laporan;
EditText kepadaEt, judulEt, laporanEt;
Button kirim;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_lapor, container, false);

        kepadaEt = (EditText)v.findViewById(R.id.kepadatxt);
        judulEt = (EditText)v.findViewById(R.id.judultxt);
        laporanEt = (EditText)v.findViewById(R.id.isitxt);
        kirim = (Button)v.findViewById(R.id.kirimlaporan);

        ParseUser user = ParseUser.getCurrentUser();
        userId = user.getObjectId();
        username = user.getUsername();
        final ProgressDialog dial = new ProgressDialog(getActivity());
        dial.setTitle("Please Wait ...");
        dial.setMessage("Uploading data . . .");
        kirim.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                kepada = kepadaEt.getText().toString();
                judul = judulEt.getText().toString();
                laporan = laporanEt.getText().toString();
                dial.show();
                if (kepada.equals("") || judul.equals("") || laporan.equals("")) {
                    Toast.makeText(getContext(), "Isi data dengan benar" + kepada, Toast.LENGTH_LONG).show();
                } else {
                    new Thread(new uploadLapor()).start();
                    kepadaEt.setText("");
                    judulEt.setText("");
                    laporanEt.setText("");
                }

                dial.dismiss();
            }
        });



        return v;

    }



    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // save views as variables in this method
        // "view" is the one returned from onCreateView

    }


        class uploadLapor implements Runnable{
            @Override
            public void run(){
                ParseObject lapor = new ParseObject("Report");
                lapor.put("ProyekName", kepada);
                lapor.put("Judul", judul);
                lapor.put("isi_laporan", laporan);
                lapor.put("Username", username);
                lapor.put("UserId", userId);
                lapor.saveInBackground();

            }
        }
}