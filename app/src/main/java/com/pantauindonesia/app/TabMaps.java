package com.pantauindonesia.app;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;


public class TabMaps extends Fragment {
    Button globe;
    private ArrayList<Comment> mComments;
    ListView listview;
    List<ParseObject> ob;
    ProyekAdapter adapter;
    private List<Proyek> proyekList = null;
    ProgressDialog mProgressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.tab_maps,container,false);

        globe = (Button)v.findViewById(R.id.petaProyek);
        globe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MapsActivity.class);
                startActivity(intent);
            }
        });
        listview = (ListView) v.findViewById(R.id.listView);
        new RemoteDataTask().execute();
        return v;

    }


        private class RemoteDataTask extends AsyncTask<Void, Void, Void> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                // Create a progressdialog
                mProgressDialog = new ProgressDialog(getActivity());
                // Set progressdialog title
                mProgressDialog.setTitle("Mengambil data");
                // Set progressdialog message
                mProgressDialog.setMessage("Loading...");
                mProgressDialog.setIndeterminate(false);
                // Show progressdialog
                mProgressDialog.show();
            }

            @Override
            protected Void doInBackground(Void... params) {
                // Create the array
                proyekList = new ArrayList<Proyek>();
                try {
                    // Locate the class table named "Country" in Parse.com
                    ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Proyek");
                    // Locate the column named "ranknum" in Parse.com and order list
                    // by ascending
                    query.orderByAscending("createdAt");
                    query.setLimit(10);
                    ob = query.find();
                    for (ParseObject Proyek : ob) {
                        Proyek map = new Proyek();
                        map.setNamaProyek((String) Proyek.get("nama_lelang"));
                        map.setAlamatProyek((String) Proyek.get("lokasi_proyek"));
                        proyekList.add(map);
                    }
                } catch (ParseException e) {
                    Log.e("Error", e.getMessage());
                    e.printStackTrace();
                }
                return null;
            }
            @Override
            protected void onPostExecute(Void result) {
                // Locate the listview in listview_main.xml

                // Pass the results into ListViewAdapter.java
                adapter = new ProyekAdapter(getActivity(),
                        proyekList);
                // Binds the Adapter to the ListView
                listview.setAdapter(adapter);
                adapter.check(true);
                // Close the progressdialog
                mProgressDialog.dismiss();
            }
        }

}
