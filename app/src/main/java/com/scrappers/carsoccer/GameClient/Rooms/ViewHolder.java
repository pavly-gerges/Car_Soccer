package com.scrappers.carsoccer.GameClient.Rooms;

import android.view.View;
import android.widget.Button;

import com.scrappers.carsoccer.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolder extends RecyclerView.ViewHolder {
    public final Button room;
    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        room=itemView.findViewById(R.id.roomID);
    }
}
