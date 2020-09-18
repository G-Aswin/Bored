package com.example.bored;

import android.content.ClipData;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FavouritesAdapter extends RecyclerView.Adapter<FavouritesAdapter.FavouritesViewHolder> {
    @NonNull
    @Override
    public FavouritesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favourites_row, parent, false);
        return new FavouritesViewHolder(view);
    }

    private List<ActivityObject> FavouritesList = MainScreen.database.favouritesDao().GetAllFavourites();

    @Override
    public void onBindViewHolder(@NonNull FavouritesViewHolder holder, int position) {
        ActivityObject current = FavouritesList.get(position);
        holder.linearLayout.setTag(current);
        holder.textView.setText(current.activity);
    }

    @Override
    public int getItemCount() {
        return FavouritesList.size();
    }

    public ActivityObject getActivityandDeleteAt(int position){
        ActivityObject activityObject =  FavouritesList.get(position);
        FavouritesList.remove(position);
        return activityObject;
    }

    public static class FavouritesViewHolder extends RecyclerView.ViewHolder{

        public LinearLayout linearLayout;
        public TextView textView;

        public FavouritesViewHolder(@NonNull View itemView) {
            super(itemView);
            this.linearLayout = itemView.findViewById(R.id.favourite_row);
            this.textView = itemView.findViewById(R.id.favourite_row_entry);


        }
    }
}
