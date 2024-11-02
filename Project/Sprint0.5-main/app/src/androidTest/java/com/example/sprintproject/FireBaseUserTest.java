package com.example.sprintproject;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.junit.Before;
import org.junit.Test;

public class FireBaseUserTest {
    private DatabaseReference databaseReference;

    @Before
    public void setUp() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        database.useEmulator("10.0.2.2", 9000);
        //This gets information from the J_Yunet user in Firebase.
        //DO NOT DELETE J_Yunet FROM FIREBASE
        databaseReference = database.getReference("travelLogs/zJo9Gqr6TUg8FS1mhBw74adAmyM2/notes");
        databaseReference.setValue("Expected Value");
    }

    @Test
    public void testContributorExistence() {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                assertNotNull(snapshot);

                String actualValue = snapshot.getValue(String.class);
                assertEquals("Expected Value", actualValue);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
