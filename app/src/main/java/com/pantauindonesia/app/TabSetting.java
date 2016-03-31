package com.pantauindonesia.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.ParseUser;

/**
 * Created by A MADRISH on 12/3/2015.
 */

    public class TabSetting extends Fragment {

    TextView logout;

        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.tab_setting,container,false);
            logout = (TextView)v.findViewById(R.id.textView5);
            logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ParseUser.logOut();
                    Intent inten = new Intent(getActivity(), LoginActivity.class);
                    startActivity(inten);
                    status("status",false);
                    getActivity().finish();
                }
            });
            return v;
        }

    private void status(String key, boolean val) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, val);
        editor.apply();
    }
    }

