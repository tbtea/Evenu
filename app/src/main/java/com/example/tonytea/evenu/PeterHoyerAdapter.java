package com.example.tonytea.evenu;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class PeterHoyerAdapter extends ArrayAdapter<Event> {
    private List<Event> fullList;
    private List<Event> suggestions = new ArrayList<>();

    public PeterHoyerAdapter(@NonNull Context context, @NonNull List<Event> eventList){
        super(context, 0, eventList);
        fullList = new ArrayList<>(eventList);
    }

    @NonNull
    @Override
    public Filter getFilter(){
        return eventFilter;
    }

    @NonNull
    @Override
    public View getView(int pos, @Nullable View convertView, @NonNull ViewGroup parent){
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.event_autocomplete_row, parent, false);
        }
        TextView textViewName = convertView.findViewById(R.id.text_view_name);

        Event event = getItem(pos);

        if(event != null){
            textViewName.setText(event.getEventTitle());
        }

        return convertView;
    }

    private Filter eventFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            suggestions = new ArrayList<>();
            FilterResults results = new FilterResults();

            if(constraint == null || constraint.length() == 0){
                suggestions.addAll(fullList);
            }else{
                String filterPattern = constraint.toString().toLowerCase().trim();

                for(Event item : fullList){

                    //first check the title of the event, if nothing found search keywords
                    if(item.getEventTitle().toLowerCase().contains(filterPattern)){
                        suggestions.add(item);
                    }else{
                        if(item.getKeywords() != null){
                            for(int i = 0; i<item.getKeywords().size(); i++){
                                if(item.getKeywords().get(i).toLowerCase().contains(filterPattern)){
                                    suggestions.add(item);
                                }
                            }
                        }
                    }
                }
            }

            results.values = suggestions;
            results.count = suggestions.size();

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            clear();
            addAll((List)results.values);
            notifyDataSetChanged();
        }

        @Override
        public CharSequence convertResultToString(Object resultValue){
            return ((Event) resultValue).getEventTitle();
        }
    };

    public List<Event> getSuggestions() {
        return suggestions;
    }
}
