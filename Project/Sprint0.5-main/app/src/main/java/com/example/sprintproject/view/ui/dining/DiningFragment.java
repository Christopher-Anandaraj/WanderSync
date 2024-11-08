package com.example.sprintproject.view.ui.dining;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sprintproject.databinding.FragmentDiningBinding;
import com.example.sprintproject.model.FirebaseManager;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;

import java.util.ArrayList;

public class DiningFragment extends Fragment {

    private FragmentDiningBinding binding;

    private final DatabaseReference diningDatabaseReference = FirebaseDatabase.getInstance().getReference("diningReservations");

    //holds all entries for dining (Allyson)
    ArrayList<DiningEntry> diningEntries = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DiningViewModel diningViewModel =
                new ViewModelProvider(this).get(DiningViewModel.class);

        binding = FragmentDiningBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // sophie stuff
        EditText editText_reservationName = binding.diningNameInput;
        Button button_reservationSubmit = binding.addReservationButton;
        EditText editText_reservationLocation = binding.diningLocationInput;
        EditText editText_reservationWebsite = binding.diningWebsiteInput;
        EditText editText_reservationTime = binding.diningTimeInput;
        // end sophie stuff

        //Allyson Implementation ------------------------------------------------------------------------

        //recycle view
        RecyclerView recyclerView = binding.diningList;

        //setup dining list
        //setUpDiningEntriesArray();

        //testing for recycle view and acts as setUpDiningEntriesArray method for testing
        diningEntries.add(new DiningEntry("Tech Dr.", "Wingnuts", "2:00", "wingnuts.com"));

        //Buttons for Dining Screen
        Button open_reservation_cardview_button = binding.openReservationCardviewButton;
        Button add_reservation_button = binding.addReservationButton;

        //cardviews
        CardView reservation_cardview = binding.reservationCardview;

        //Opens and closes the Add reservation viewcard
        open_reservation_cardview_button.setOnClickListener(view -> {
            if (reservation_cardview.getVisibility() == View.GONE) {
                reservation_cardview.setVisibility(View.VISIBLE);
            } else {
                reservation_cardview.setVisibility(View.GONE);
            }
        });

        //setup recycleview functionality (sending dining entries to adapter)
        //call constructor
        DiningRecycleViewAdapter adapter = new DiningRecycleViewAdapter(this.getContext(), diningEntries);
        recyclerView.setAdapter(adapter);

        //layout is linear layout for the boxes
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        //End of Allyson Implementation ------------------------------------------------------------------

        // sophie stuff
        button_reservationSubmit.setOnClickListener(v -> {
            String resvName = editText_reservationName.getText().toString().trim();
            String resvLocation = editText_reservationLocation.getText().toString().trim();
            String resvTime = editText_reservationTime.getText().toString().trim();
            String resvWebsite = editText_reservationWebsite.getText().toString().trim();

            if (resvLocation.isEmpty() || resvTime.isEmpty() || resvWebsite.isEmpty() || resvName.isEmpty()) {
                Toast.makeText(getContext(), "Please fill in all fields and try again.",
                        Toast.LENGTH_SHORT).show();
                return;
            }
            createReservation(resvName,resvLocation,resvTime, resvWebsite);
            editText_reservationName.setText("");
            editText_reservationLocation.setText("");
            editText_reservationTime.setText("");
            editText_reservationWebsite.setText("");
        });
        // end of sophie stuff

        final TextView textView = binding.textDining;
        diningViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;

    }

    private void createReservation(String restaurantName, String location, String time, String website) {
        FirebaseUser user = FirebaseManager.getInstance().getAuth().getCurrentUser();

        if (user != null) {
            String uid = user.getUid();

            DatabaseReference reservationRef = FirebaseManager.getInstance().getDatabaseReference()
                    .child("diningReservations").child(uid);

            String reservationId = reservationRef.child("reservations").push().getKey();

            Map<String, String> reservationMap = new HashMap<>();
            reservationMap.put("name", restaurantName);
            reservationMap.put("location", location);
            reservationMap.put("website", website);
            reservationMap.put("reservation_time", time);

            if (reservationId != null) {
                reservationRef.child(reservationId).setValue(reservationMap)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(getContext(), "Restaurant reservation added to log!",
                                        Toast.LENGTH_SHORT).show();
//                                ListView listView = binding.listViewTravelLogs;
//                                loadTravelLogs(listView);
                            } else {
                                Toast.makeText(getContext(), "Failed to add reservation.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
            } else {
                Toast.makeText(getContext(), "Failed to generate reservation ID.",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    //Allyson Implementation ------------------------------------------------------------------------
    private void setUpDiningEntriesArray() {
    }


    //End of Allyson Implementation ------------------------------------------------------------------

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
