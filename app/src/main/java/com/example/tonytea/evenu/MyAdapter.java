package com.example.tonytea.evenu;

import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Context;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {


    Context context;
    ArrayList<Event> event;

    public MyAdapter(Context c, ArrayList<Event> e)
    {
        context = c;
        event = e;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.card_view, viewGroup, false ));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.date.setText(event.get(i).getEventDate());
        myViewHolder.description.setText(event.get(i).getEventDescription());
        myViewHolder.id.setText(event.get(i).getEventID());
        myViewHolder.keywords.setText(event.get(i).getEventKeywords());
        myViewHolder.location.setText(event.get(i).getEventLocation());
        myViewHolder.time.setText(event.get(i).getEventTime());
        myViewHolder.title.setText(event.get(i).getEventTitle());
        // to get images do this
        // Picasso.get().load(events.get(position).getProfilePic()).into(holder.profilePic)




    }

    @Override
    public int getItemCount() {
        return event.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView date, description, id, keywords,location, time, title;
        //Button btn;
       // ImageView profilePic;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            date = (TextView) itemView.findViewById(R.id.date);
            description = (TextView) itemView.findViewById(R.id.description);
            id = (TextView) itemView.findViewById(R.id.id);
            keywords = (TextView) itemView.findViewById(R.id.keywords);
            location = (TextView) itemView.findViewById(R.id.location);
            time = (TextView) itemView.findViewById(R.id.time);
            title = (TextView) itemView.findViewById(R.id.title);

           // profilePic = (ImageView) itemView.findViewById(R.id.profilePic);

        }
    }
}
