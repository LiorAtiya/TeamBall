package com.ariel.teamball.View;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ariel.teamball.Controller.Adapters.ParticipantsAdapter;
import com.ariel.teamball.Model.Classes.Player;
import com.ariel.teamball.Model.DAL.PlayerDAL;
import com.ariel.teamball.Model.DAL.RoomDAL;
import com.ariel.teamball.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Participants extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference database;
    ParticipantsAdapter participantsAdapter;
    ArrayList<Player> participantsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participants);

        Log.d("TAG", "Participants class");

        /*-----  Information from the previous page ------*/
        String category = getIntent().getExtras().get("category").toString();
        String roomID = getIntent().getExtras().get("roomID").toString();

        recyclerView = findViewById(R.id.participantsList);
        database = FirebaseDatabase.getInstance().getReference("Rooms").child(category).child(roomID).child("usersList");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        participantsList = new ArrayList<>();
        participantsAdapter = new ParticipantsAdapter(this, participantsList);
        recyclerView.setAdapter(participantsAdapter);

        database.addValueEventListener(new ValueEventListener() {
            int playersReceived = 0; // how many objects returned from the DB
            int numOfPlayers; // how many players there are in the room

            // run after finish to add all the players to the list
            Runnable runNotify = new Runnable() {
                @Override
                public void run() {
                    Log.d("TAG", "run");
                    // notify when all the players added to the list
                    participantsAdapter.notifyDataSetChanged();
                }
            };

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                // checks how many players there are in the room
                RoomDAL.getNumOfPlayers(roomID, category, (getNumOfPlayers) -> {
                    numOfPlayers = getNumOfPlayers;
                });

                // gets add all the players in the room to the participants list
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String playerID = dataSnapshot.getKey();
                    PlayerDAL.getPlayer(playerID, (player) -> {
                        participantsList.add(player);
                        playersReceived++;
                        // checks if all the players was retrieved
                        Log.d("TAG", "check if equals: " + playersReceived + " == " + numOfPlayers);
                        if(playersReceived == numOfPlayers) {
                            runNotify.run();
                        }
                    });
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}