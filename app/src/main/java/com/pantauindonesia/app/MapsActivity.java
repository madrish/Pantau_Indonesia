package com.pantauindonesia.app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class MapsActivity extends ActionBarActivity implements OnMapReadyCallback{
    private MenuItem mSearchAction;
    private boolean isSearchOpened = false;
    private EditText edtSeach;
    private GoogleMap mMap;
    public int[] markernumber = {R.mipmap.marker_building_1,R.mipmap.marker_bulding_2,R.mipmap.marker_building_3,
                                 R.mipmap.marker_road_1,R.mipmap.marker_road_2,R.mipmap.marker_road_3,
                                 R.mipmap.marker_transport_1,R.mipmap.marker_transport_2,R.mipmap.marker_transport_3};
    public int icon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        mSearchAction = menu.findItem(R.id.action_search);
        return super.onPrepareOptionsMenu(menu);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            /*case R.id.action_settings:
            return true;*/
            case R.id.action_search:
                handleMenuSearch();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void handleMenuSearch() {
        ActionBar action = getSupportActionBar(); //get the actionbar

        if(isSearchOpened){ //test if the search is open

            action.setDisplayShowCustomEnabled(false); //disable a custom view inside the actionbar
            action.setDisplayShowTitleEnabled(true); //show the title in the action bar

            //hides the keyboard
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(edtSeach.getWindowToken(), 0);

            //add the search icon in the action bar
            mSearchAction.setIcon(getResources().getDrawable(R.mipmap.ic_search));

            isSearchOpened = false;
        } else { //open the search entry

            action.setDisplayShowCustomEnabled(true); //enable it to display a
            // custom view in the action bar.
            action.setCustomView(R.layout.search_bar);//add the custom view
            action.setDisplayShowTitleEnabled(false); //hide the title

            edtSeach = (EditText)action.getCustomView().findViewById(R.id.edtSeach); //the text editor

            //this is a listener to do a search when the user clicks on search button
            edtSeach.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                        doSearch();
                        return true;
                    }
                    return false;
                }
            });


            edtSeach.requestFocus();

            //open the keyboard focused in the edtSearch
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(edtSeach, InputMethodManager.SHOW_IMPLICIT);


            //add the close icon
            mSearchAction.setIcon(getResources().getDrawable(R.mipmap.ic_close_search));

            isSearchOpened = true;
        }
    }

    private void doSearch() {

    }
    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(-6.2293867,106.6894296), 12));


        //mengambil data dar Parse
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Proyek");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(final List<ParseObject> list, ParseException e) {
                if (e == null) {
                    int stat;

                    ParseGeoPoint location;
                    for (int i = 0; i < list.size(); i++) {
                        location = list.get(i).getParseGeoPoint("lokasi");
                        //memilih icon marker yang digunakan
                        stat = list.get(i).getInt("status");
                        if (stat == 11){
                            icon = markernumber[0];
                        }else if (stat == 12){
                            icon = markernumber[1];
                        }else if (stat == 13){
                            icon = markernumber[2];
                        }else if (stat == 21){
                            icon = markernumber[3];
                        }else if (stat == 22){
                            icon = markernumber[4];
                        }else if (stat == 23){
                            icon = markernumber[5];
                        }else if (stat == 31){
                            icon = markernumber[6];
                        }else if (stat == 32){
                            icon = markernumber[7];
                        }else if (stat == 33){
                            icon = markernumber[8];
                        }
                        double latitude = location.getLatitude();
                        double longitude = location.getLongitude();
                        //menambahkan marker
                        LatLng latLng = new LatLng(latitude, longitude);
                        mMap.addMarker(new MarkerOptions().position(latLng).icon(BitmapDescriptorFactory.fromResource(icon)
                        ).anchor(0.5f,1.0f));
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                        //mengambil objectId

                        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                            @Override
                            public boolean onMarkerClick(Marker marker) {
                                String obid = marker.getId();
                                Intent m = new Intent(MapsActivity.this, InformasiDetil.class);
                                m.putExtra("ob_id", obid);
                                startActivity(m);
                                return true;
                            }

                        });

                    }

                }else{

                }

            }
        });
        /*
        LatLng latLng = new LatLng(-7.8313625, 111.984942);
        mMap.addMarker(new MarkerOptions().position(latLng).title("Lokasi"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        */

    }
}
