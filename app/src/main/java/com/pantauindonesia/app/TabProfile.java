package com.pantauindonesia.app;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.ParseUser;


public class TabProfile extends Fragment {
String username, phone, address, email;
TextView userv, phonev, addressv, emailv;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.tab_profile,container,false);
        final ProgressDialog dial = new ProgressDialog(getActivity());
        dial.setTitle("Please Wait ...");
        dial.setMessage("Loading User Data . . .");
        dial.show();

        ParseUser user = ParseUser.getCurrentUser();
        username = user.getUsername();
        email = user.getEmail();
        //phone = user.getString("phone");
        //address = user.getString("address");

        userv = (TextView)v.findViewById(R.id.username);
        //phonev = (TextView)v.findViewById(R.id.phone);
        emailv = (TextView)v.findViewById(R.id.email);
        //addressv = (TextView)v.findViewById(R.id.address);

        userv.setText(username);
        //phonev.setText(phone);
        emailv.setText(email);
        //addressv.setText(address);
        dial.dismiss();

        return v;

    }
}
