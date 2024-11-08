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

import com.example.sprintproject.databinding.FragmentDiningBinding;
import com.example.sprintproject.model.FirebaseManager;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;

public class DiningFragment extends Fragment {

    private FragmentDiningBinding binding;

    private final DatabaseReference diningDatabaseReference = FirebaseDatabase.getInstance().getReference("diningReservations");

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DiningViewModel diningViewModel =
                new ViewModelProvider(this).get(DiningViewModel.class);

        binding = FragmentDiningBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // sophie stuff
        Button button_reservationSubmit;
        EditText editText_reservationLocation = binding.diningLocationInput;
        EditText editText_reservationWebsite = binding.diningWebsiteInput;
        EditText editText_reservationReview;
        EditText editText_reservationTime = binding.diningTimeInput;
        // end sophie stuff

        //Allyson Implementation ------------------------------------------------------------------------

        //Buttons for Dining Screen
        Button open_reservation_cardview_button = binding.openReservationCardviewButton;

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

        //End of Allyson Implementation ------------------------------------------------------------------

        // sophie stuff
        button_reservationSubmit.setOnClickListener(v -> {
            String resvLocation = editText_reservationLocation.getText().toString().trim();
            String resvTime = editText_reservationTime.getText().toString().trim();
            String resvWebsite = editText_reservationWebsite.getText().toString().trim();

            if (resvLocation.isEmpty() || resvTime.isEmpty() || resvWebsite.isEmpty()) {
                Toast.makeText(getContext(), "Please fill in all fields and try again.",
                        Toast.LENGTH_SHORT).show();
                return;
            }
        });
        // end of sophie stuff

        final TextView textView = binding.textDining;
        diningViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;



    }

    private void createReservation(String location, String time, String website) {
        FirebaseUser user = FirebaseManager.getInstance().getAuth().getCurrentUser();

        if (user != null) {
            String uid = user.getUid();

            String reservationId = diningDatabaseReference.push().getKey();

            Map<String, String> reservation = new HashMap<>();
            reservation.put("location", location);
            reservation.put("website", website);
            reservation.put("reservation_time", time);

            if (reservationId != null) {
                diningDatabaseReference.child("diningReservations").child(reservationId).setValue(reservation)
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
