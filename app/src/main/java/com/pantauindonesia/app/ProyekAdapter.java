package com.pantauindonesia.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ProyekAdapter extends BaseAdapter {
    boolean tipe;
    // Declare Variables
    Context mContext;
    LayoutInflater inflater;
    private List<Proyek> proyeklist = null;
    private ArrayList<Proyek> arraylist;

    public ProyekAdapter(Context context,
                           List<Proyek> proyeklist) {
        mContext = context;
        this.proyeklist = proyeklist;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<Proyek>();
        this.arraylist.addAll(proyeklist);
    }



    public class ViewHolder {
        TextView wilayah;
        TextView rank;
    }

    @Override
    public int getCount() {
        return proyeklist.size();
    }
    public boolean check(boolean value) {
        tipe=value;
        return tipe;
    }
    @Override
    public Proyek getItem(int position) {
        return proyeklist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.proyek_item_listview, null);

            // Locate the TextViews in listview_item.xml
            holder.wilayah = (TextView) view.findViewById(R.id.namaProyek);
            holder.rank = (TextView) view.findViewById(R.id.alamat);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        // Set the results into TextViews
        holder.wilayah.setText(proyeklist.get(position).getNamaProyek());
        holder.rank.setText(proyeklist.get(position).getAlamatProyek());
        return view;
    }
}