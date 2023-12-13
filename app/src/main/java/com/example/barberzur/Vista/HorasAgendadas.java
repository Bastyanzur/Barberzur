package com.example.barberzur.Vista;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.barberzur.Modelo.Appointment;
import com.example.barberzur.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class HorasAgendadas extends AppCompatActivity {

    private ListView listViewAppointments;;
    private ArrayAdapter<Appointment> adapter;
    private DatabaseReference appointmentsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horas_agendadas);

        listViewAppointments = findViewById(R.id.ListReservas);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new ArrayList<>());
        listViewAppointments.setAdapter(adapter);

        listarContactos();
    }

    private void listarContactos() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        appointmentsRef = database.getReference("appointment");

        appointmentsRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Appointment appointment = snapshot.getValue(Appointment.class);
                if (appointment != null) {
                    adapter.add(appointment);
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                // Handle item removal if needed
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                // Handle item movement if needed
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle errors here
            }
        });

        listViewAppointments.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Appointment selectedAppointment = adapter.getItem(position);
                if (selectedAppointment != null) {
                    showAppointmentDetails(selectedAppointment);
                }
            }
        });
    }

    private void showAppointmentDetails(Appointment appointment) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle("Detalles de la cita");

        String details = "Fecha: " + appointment.getSelectedDate() + "\n\n";
        details += "Hora Reserva: " + appointment.getSelectedTime() + "\n\n";
        details += "Nombre Cliente: " + appointment.getCustomerName() + "\n\n";
        details += "Precio Corte: " + appointment.getHaircutPrice() + "\n\n";

        builder.setMessage(details);
        builder.show();
    }
}
