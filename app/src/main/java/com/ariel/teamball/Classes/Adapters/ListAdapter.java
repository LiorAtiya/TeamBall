package com.ariel.teamball.Classes.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ariel.teamball.Classes.DAO.RoomDAO;
import com.ariel.teamball.Classes.Room;
import com.ariel.teamball.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListAdapter extends ArrayAdapter<Room> {

    private static final String TAG = "RoomListAdapter";
    private Context mContext;
    RoomDAO roomDAO;
    int mResource;

    public ListAdapter(Context context,int resource, ArrayList<Room> roomArrayList){
        super(context, resource,roomArrayList);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

//        String room_name = getItem(position).getName();
//        String city = getItem(position).getCity();
//        String field = getItem(position).getField();
//        String admin = getItem(position).getAdmin();
//        int capacity = getItem(position).getCapacity();
//        String time = getItem(position).getTime();
//        String date = getItem(position).getDate();
//        int numOfPlayers = getItem(position).getNumOfPlayers();
        String roomID = getItem(position).getRoomID();
        String category = getItem(position).getCategory();

//        Room room = new Room(room_name, capacity, field, city, time, date, admin);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource,parent,false);

        TextView roomName = convertView.findViewById(R.id.room_name);
        TextView details = convertView.findViewById(R.id.details);
//        ImageView imageView = convertView.findViewById(R.id.profile_pic);
//        Button edit = convertView.findViewById(R.id.edit_room_btn);

        //Fill the details of room in TextView
        roomDAO = new RoomDAO(mContext);
        DatabaseReference roomRef = roomDAO.getPathReference("Rooms/"+category+"/"+roomID);

        roomRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Room room = snapshot.getValue(Room.class);
                //        imageView.setImageResource(room.getImageID());
                roomName.setText(room.getName());
                details.setText("Capacity: " + room.getNumOfPlayers()+ "/" + room.getCapacity() +
                        " | City: " + room.getCity() +" " + "\nField: " + room.getField() );
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

//        //        imageView.setImageResource(room.getImageID());
//        roomName.setText(room_name);
//        details.setText("Capacity: " + numOfPlayers+ "/" + capacity + " | City: " + city +" " +
//                "\nField: " + field );

        return convertView;
    }
}
