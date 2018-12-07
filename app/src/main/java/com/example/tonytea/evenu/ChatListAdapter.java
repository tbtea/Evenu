package com.example.tonytea.evenu;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChatListAdapter extends BaseAdapter {

    private Activity mActivity;
    private DatabaseReference mDatabaseReference;
    private  String mDisplayName;
    private ArrayList<Comment> comment_list;
    private String this_event_id;


    private ChildEventListener mListener = new ChildEventListener() {
        @Override
        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            FirebaseDatabase.getInstance().getReference().child("comments").child(dataSnapshot.getKey()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    comment_list.add(dataSnapshot.getValue(Comment.class));
                    notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });



        }

        @Override
        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

        }

        @Override
        public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

        }

        @Override
        public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

    public ChatListAdapter(Activity activity, DatabaseReference ref, String name, String id){

        mActivity = activity;
        mDisplayName = name;
        this_event_id = id;

        FirebaseDatabase.getInstance().getReference().child("commentsections").child(id).addChildEventListener(mListener);

        comment_list = new ArrayList<>();

    }


    private static class ViewHolder{
        TextView authorName;
        TextView body;
        LinearLayout.LayoutParams params;
    }

    @Override
    public int getCount() {
        return comment_list.size();
    }

    @Override
    public Comment getItem(int position) {
        return comment_list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.chat_msg_row, parent, false);

            final ViewHolder holder = new ViewHolder();
            holder.body = convertView.findViewById(R.id.comment);
            holder.authorName = convertView.findViewById(R.id.author);
            holder.params = (LinearLayout.LayoutParams) holder.authorName.getLayoutParams();
            convertView.setTag(holder);
        }

        final Comment comment = (Comment) getItem(position);
        final ViewHolder holder = (ViewHolder) convertView.getTag();

        String author = comment.getAuthor();
        holder.authorName.setText(author);

        String msg = comment.getMessage();
        holder.body.setText(msg);

        return convertView;
    }


    public void cleanup(){

        FirebaseDatabase.getInstance().getReference().child("commentsections").child(this_event_id).removeEventListener(mListener);
    }



}
