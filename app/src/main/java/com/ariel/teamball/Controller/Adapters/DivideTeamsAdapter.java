//package com.ariel.teamball.Controller.Adapters;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.ariel.teamball.Model.Classes.Player;
//import com.ariel.teamball.R;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//
//public class DivideTeamsAdapter extends RecyclerView.Adapter<ParticipantsAdapter.MyViewHolder> {
//
//    Context context;
//    HashMap<Integer, ArrayList<Player>> teamMap;
//
//    public DivideTeamsAdapter(Context context, HashMap<Integer, ArrayList<Player>> teamMap) {
//        this.context = context;
//        this.teamMap = teamMap;
//    }
//
//    @NonNull
//    @Override
//    public ParticipantsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View v = LayoutInflater.from(context).inflate(R.layout.participants_item, parent, false);
//        return new ParticipantsAdapter.MyViewHolder(v);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ParticipantsAdapter.MyViewHolder holder, int position) {
//
//        Player player = teamMap.get(position);
//        holder.nickName.setText(player.getNickName());
//        holder.fullName.setText(player.getFullName());
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return teamMap.size();
//    }
//
//    public static class MyViewHolder extends RecyclerView.ViewHolder{
//
//        TextView nickName, fullName;
//
//        public MyViewHolder(@NonNull View itemView) {
//            super(itemView);
//
//            nickName = itemView.findViewById(R.id.tvNickName);
//            fullName = itemView.findViewById(R.id.tvFullName);
//        }
//    }
//
//}
