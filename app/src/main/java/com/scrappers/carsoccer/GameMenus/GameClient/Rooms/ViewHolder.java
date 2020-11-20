package com.scrappers.carsoccer.GameMenus.GameClient.Rooms;

import android.view.View;
import android.widget.Button;

import com.scrappers.carsoccer.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolder extends RecyclerView.ViewHolder {
    public final Button room;
    public final Button numOfPlayers;
    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        room=itemView.findViewById(R.id.roomID);
        numOfPlayers=itemView.findViewById(R.id.numberOfPlayers);
    }
}
