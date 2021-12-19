package com.ariel.teamball.Classes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.ariel.teamball.Classes.Adapters.PlayerAdapter;
import com.ariel.teamball.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class playersList extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference db;
    PlayerAdapter playerAdapter;
    ArrayList<Player> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("TAG", "playersList");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_players_list);

        String category = "Soccer";
        String roomID = "-MrIdgsAd-Xt-r_tc_c-";

        recyclerView = findViewById(R.id.playersList);
        db = FirebaseDatabase.getInstance().getReference("users").child(category).child(roomID).child("usersList");
        Log.d("TAG", "DB: " + db);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        playerAdapter = new PlayerAdapter(this, list);
        recyclerView.setAdapter(playerAdapter);

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Player player = dataSnapshot.getValue(Player.class);
                    Log.d("TAG", "list.add(player): " + player.getId());
                    list.add(player);
                }
                playerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}