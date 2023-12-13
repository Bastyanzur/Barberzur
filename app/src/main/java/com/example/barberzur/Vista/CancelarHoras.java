package com.example.barberzur.Vista;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import android.widget.ListView;
import com.example.barberzur.Modelo.Appointment;
import com.example.barberzur.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;


import androidx.appcompat.app.AppCompatActivity;


public class CancelarHoras extends AppCompatActivity {

    private ListView listViewCitas;
    private ArrayAdapter<Appointment> adapter;
    private DatabaseReference appointmentsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancelar_horas);

        listViewCitas = findViewById(R.id.listViewCitas);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new ArrayList<>());
        listViewCitas.setAdapter(adapter);

        // Initialize Firebase
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        appointmentsRef = firebaseDatabase.getReference("appointments");

        // Retrieve appointments from Firebase
        fetchAppointments();

        listViewCitas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Appointment selectedAppointment = adapter.getItem(position);
                if (selectedAppointment != null) {
                    deleteAppointment(selectedAppointment);
                }
            }
        });
    }

    private void fetchAppointments() {
        appointmentsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                adapter.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Appointment appointment = snapshot.getValue(Appointment.class);
                    if (appointment != null) {
                        adapter.add(appointment);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle errors here
            }
        });
    }

    private void deleteAppointment(Appointment appointment) {
        appointmentsRef.child(appointment.getAppointmentId()).removeValue();
    }
}

