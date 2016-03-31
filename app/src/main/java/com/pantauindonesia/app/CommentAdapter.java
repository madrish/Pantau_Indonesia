package com.pantauindonesia.app;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.parse.ParseObject;

import java.util.List;

public class CommentAdapter extends ArrayAdapter<Comment> {

    //private Context mContext;
    private List<ParseObject> mComment;

    public CommentAdapter(Context context, String userId, List<Comment> comment) {
        super(context,0, comment);
        //mContext = context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).
                    inflate(R.layout.activity_comment_adapter, parent, false);
            final ViewHolder holder = new ViewHolder();
            holder.usernameComment = (TextView) convertView
                    .findViewById(R.id.namaComment);
            holder.messageComment = (TextView) convertView
                    .findViewById(R.id.textComment);
            convertView.setTag(holder);
        }

        final Comment comment = (Comment)getItem(position);
        final ViewHolder holder = (ViewHolder) convertView.getTag();

        holder.messageComment.setText(comment.getBody());
        holder.usernameComment.setText(comment.getUserId());
/*
        // title
        String username = statusObject.getString("objectId");
        holder.usernameComment.setText(username);

        // content
        String status = statusObject.getString("commentList");
        holder.messageComment.setText(status);
*/
        return convertView;
    }

    public static class ViewHolder {
        TextView usernameComment;
        TextView messageComment;

    }

}
