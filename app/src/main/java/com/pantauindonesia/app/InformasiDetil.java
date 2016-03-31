package com.pantauindonesia.app;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;


public class InformasiDetil extends AppCompatActivity {
String obid;
TextView namaKons, lokasiKons, insansiKons, satkerKons, paguKons, nilaiHPS,penggarapKons,alpengKons,npwpKons,nLikes,nDislikes, nComments;
ImageButton likeBtn,dislikeBtn,commentBtn,shareBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informasi_detil);

        final ProgressDialog dial = new ProgressDialog(InformasiDetil.this);
        dial.setTitle("Please Wait ...");
        dial.setMessage("Downloading Data ...");
        dial.show();

        namaKons = (TextView)findViewById(R.id.namaKonstruksi);
        lokasiKons = (TextView)findViewById(R.id.lokasiKonstruksi);
        insansiKons = (TextView)findViewById(R.id.instansiKonstruksi);
        satkerKons =(TextView)findViewById(R.id.satkerKonstruksi);
        paguKons = (TextView)findViewById(R.id.nilaiPagu);
        nilaiHPS = (TextView)findViewById(R.id.nilaiHPS);
        penggarapKons = (TextView)findViewById(R.id.namaPenggarap);
        alpengKons = (TextView)findViewById(R.id.alamatPenggarap);
        npwpKons = (TextView)findViewById(R.id.nomorNPWP);
        nLikes = (TextView)findViewById(R.id.nLike);
        nDislikes = (TextView)findViewById(R.id.nDislike);
        nComments = (TextView)findViewById(R.id.nComment);

        likeBtn = (ImageButton)findViewById(R.id.likeButton);
        dislikeBtn = (ImageButton)findViewById(R.id.dislikeButton);
        commentBtn = (ImageButton)findViewById(R.id.commentButton);
        shareBtn =(ImageButton)findViewById(R.id.shareButton);

        Intent intent = getIntent();
        obid=intent.getStringExtra("ob_id");
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Proyek");
        query.whereEqualTo("marker_id", obid);
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(final ParseObject object, ParseException e) {
                String nama = object.getString("nama_lelang");
                String instansi = object.getString("instansi");
                String satker = object.getString("satker");
                String penggarap = object.getString("penggarap");
                String nilaipagu = object.getString("nilaipagu");
                String nilaihps = object.getString("nilaihps");
                String alamatpenggarap = object.getString("alamatpenggarap");
                String lokasiproyek = object.getString("lokasi_proyek");
                String npwp = object.getString("npwp");
                final int like = object.getInt("like");
                final int dislike = object.getInt("dislike");
                namaKons.setText(nama);
                insansiKons.setText(instansi);
                satkerKons.setText(satker);
                paguKons.setText(nilaipagu);
                nilaiHPS.setText(nilaihps);
                penggarapKons.setText(penggarap);
                alpengKons.setText(alamatpenggarap);
                npwpKons.setText(npwp);
                lokasiKons.setText(lokasiproyek);
                nLikes.setText("" + like);
                nDislikes.setText("" + dislike);
                dial.dismiss();

            }
        });

        //runnable class
        class Like implements Runnable{
            @Override
            public void run(){

                ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Proyek");
                query.whereEqualTo("marker_id", obid);
                query.getFirstInBackground(new GetCallback<ParseObject>() {
                    @Override
                    public void done(final ParseObject object, ParseException e) {
                        if (e == null) {
                            dial.show();
                            int like = object.getInt("like");
                            like += 1;
                            object.put("like", like);
                            nLikes.setText("" + like);
                            object.saveInBackground();
                            dial.dismiss();
                        } else {
                            Log.d("error", String.valueOf(e));
                            //
                        }
                    }
                });

            }
        }


        class Dislike implements Runnable{
            @Override
            public void run(){
                ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Proyek");
                query.whereEqualTo("marker_id", obid);
                query.getFirstInBackground(new GetCallback<ParseObject>() {
                    @Override
                    public void done(final ParseObject object, ParseException e) {
                        if (e == null) {
                            dial.show();
                            int dislike = object.getInt("dislike");
                            dislike += 1;
                            object.put("dislike", dislike);
                            nDislikes.setText("" + dislike);
                            object.saveInBackground();
                            dial.dismiss();
                        } else {
                            Log.d("error", String.valueOf(e));
                        }
                    }

                });
            }
        }

        likeBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new Thread(new Like()).start();
            }
        });

        dislikeBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new Thread(new Dislike()).start();
            }
        });

        commentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent comment =  new Intent(InformasiDetil.this, CommentActivity.class);
                comment.putExtra("ob_id",obid);
                startActivity(comment);
                InformasiDetil.this.finish();
            }
        });

    }


}
