package com.example.sprintproject;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import androidx.annotation.NonNull;

import com.example.sprintproject.model.FirebaseManager;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.junit.Before;
import org.junit.Test;

public class FirebaseNotesTest {
    private DatabaseReference databaseReference;

    @Before
    public void setUp() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        database.useEmulator("10.0.2.2", 9000);
        //This should be getting information from the J_Yunet user in Firebase.
        //DO NOT DELETE J_Yunet FROM FIREBASE
        databaseReference = database.getReference("travelLogs/zJo9Gqr6TUg8FS1mhBw74adAmyM2/notes");
        databaseReference.setValue("Expected Value");
    }

    @Test
    public void testNotesExistence() {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                assertNotNull(snapshot);

                String actualValue = snapshot.getValue(String.class);
                assertEquals("Expected Value", actualValue);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                fail("Database read operation failed: " + error.getMessage());
            }
        });
    }
}
