package com.ariel.teamball.Controller.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.ariel.teamball.Model.Classes.Player;
import com.ariel.teamball.R;

import java.util.ArrayList;

public class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.MyViewHolder> {

    Context context;
    ArrayList<Player> list;

    public PlayerAdapter(Context context, ArrayList<Player> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.player_item,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Log.d("TAG", "onBindViewHolder");
        Player player = list.get(position);
        holder.fullName.setText(player.getFullName());
        holder.nickName.setText(player.getNickName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView nickName, fullName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            Log.d("TAG", "MyViewHolder");
            fullName = itemView.findViewById(R.id.tvFullName);
            nickName = itemView.findViewById(R.id.tvNickName);
        }
    }

}
