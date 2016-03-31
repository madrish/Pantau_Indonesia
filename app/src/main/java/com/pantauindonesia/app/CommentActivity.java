package com.pantauindonesia.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

public class CommentActivity extends ActionBarActivity {

    private static final int MAX_CHAT_MESSAGES_TO_SHOW = 20;

    private ListView lvComment;
    private ArrayList<Comment> mComments;
    private CommentAdapter mAdapter;
    //List<ParseObject> mComment;
    private boolean mFirstLoad;

    private final ParseObject message = ParseObject.create("Comment");

    private String obid;
    String userName;

    Button btSend;
    EditText etMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        message.saveInBackground(new SaveCallback() {
            @Override
            public void done(com.parse.ParseException e) {
                receiveMessage();
            }
        });

        Intent comment = getIntent();
        obid = comment.getStringExtra("ob_id");
        ParseUser curuser = ParseUser.getCurrentUser();
        userName = curuser.getUsername();
        lvComment = (ListView) findViewById(R.id.lvComment);
        mComments = new ArrayList<Comment>();
        // Find the text field and button
        etMessage = (EditText) findViewById(R.id.txtComment);
        btSend = (Button) findViewById(R.id.sendComBtn);
        lvComment.setTranscriptMode(1);
        mFirstLoad = true;
        mAdapter = new CommentAdapter(CommentActivity.this, userName, mComments);
        lvComment.setAdapter(mAdapter);
        // When send button is clicked, create message object on Parse
        btSend.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String commentString = etMessage.getText().toString();
                if (commentString.equals("")) {
                    Toast.makeText(CommentActivity.this, "Kolom komentar tidak boleh kosong",
                            Toast.LENGTH_SHORT).show();
                }else{
                    message.put("proyekId", obid);
                    message.put("userId", userName);
                    message.put("commentList", commentString);
                    message.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(com.parse.ParseException e) {
                        /*Toast.makeText(CommentActivity.this, "You have commented",
                                Toast.LENGTH_SHORT).show();*/
                            receiveMessage();
                        }
                    });
                    etMessage.setText("");
                }
            }
        });

    }
    private void receiveMessage() {
        // Construct query to execute
        ParseQuery<Comment> query = ParseQuery.getQuery(Comment.class);
        // Configure limit and sort order
        query.whereEqualTo("proyekId", obid);
        query.setLimit(MAX_CHAT_MESSAGES_TO_SHOW);
        query.orderByAscending("createdAt");
        // Execute query to fetch all messages from Parse asynchronously
        // This is equivalent to a SELECT query with SQL
        query.findInBackground(new FindCallback<Comment>() {
            @Override
            public void done(List<Comment> comments, com.parse.ParseException e) {
                if (e == null) {
                    mComments.clear();
                    mComments.addAll(comments);
                    mAdapter.notifyDataSetChanged(); // update adapter
                    // Scroll to the bottom of the list on initial load
                    if (mFirstLoad) {
                        lvComment.setSelection(mAdapter.getCount() - 1);
                        mFirstLoad = false;
                    }
                } else {
                    Log.d("message", "Error: " + e.getMessage());
                }
            }
        });
    }
}
