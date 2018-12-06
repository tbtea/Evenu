package com.example.tonytea.evenu;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {


    Context context;
    ArrayList<Event> event_list;

    public MyAdapter(Context c, ArrayList<Event> e)
    {
        context = c;
        event_list = e;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.card_view, viewGroup, false ));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int pos) {
        /*myViewHolder.date.setText(event_list.get(pos).getEventDate());
        myViewHolder.description.setText(event_list.get(pos).getEventDescription());
        myViewHolder.id.setText(event_list.get(pos).getEventID());
        myViewHolder.keywords.setText(event_list.get(pos).getEventKeywords());
        myViewHolder.location.setText(event_list.get(pos).getEventLocation());
        myViewHolder.time.setText(event_list.get(pos).getEventTime());*/
        myViewHolder.title.setText(event_list.get(pos).getEventTitle());

        myViewHolder.carrier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayEvent(event_list.get(pos));
            }
        });

        // to get images do this
        // Picasso.get().load(events.get(position).getProfilePic()).into(holder.profilePic)

    }

    public void displayEvent(Event e){
        Intent intent = new Intent(context, EventDisplayActivity.class);
        intent.putExtra("event", e);
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return event_list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView date, description, id, keywords,location, time, title;
        LinearLayout carrier;
        //Button btn;
       // ImageView profilePic;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
//            date = (TextView) itemView.findViewById(R.id.date);
//            description = (TextView) itemView.findViewById(R.id.description);
//            id = (TextView) itemView.findViewById(R.id.id);
//            keywords = (TextView) itemView.findViewById(R.id.keywords);
//            location = (TextView) itemView.findViewById(R.id.location);
//            time = (TextView) itemView.findViewById(R.id.time);
            title = (TextView) itemView.findViewById(R.id.title);
            carrier = itemView.findViewById(R.id.carrier);

           // profilePic = (ImageView) itemView.findViewById(R.id.profilePic);

        }
    }
}
