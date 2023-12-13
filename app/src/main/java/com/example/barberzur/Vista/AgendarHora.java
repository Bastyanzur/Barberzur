package com.example.barberzur.Vista;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.barberzur.Modelo.Appointment;
import com.example.barberzur.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class AgendarHora extends AppCompatActivity {

    private CalendarView calendarView;
    private Spinner timeSlotsSpinner;
    private EditText customerNameEditText;
    private NumberPicker haircutPricePicker;

    private DatabaseReference appointmentsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agendar_hora);

        Spinner serviceSpinner = findViewById(R.id.serviceSpinner);

        haircutPricePicker = findViewById(R.id.haircutPricePicker);

        String[] services = {"Corte de Cabello $5000", "Corte de Cabello y Barba $6000"};

        ArrayAdapter<String> serviceAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, services);
        serviceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        serviceSpinner.setAdapter(serviceAdapter);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        appointmentsRef = firebaseDatabase.getReference("appointments");

        calendarView = findViewById(R.id.calendarView);
        timeSlotsSpinner = findViewById(R.id.timeSlotsSpinner);
        customerNameEditText = findViewById(R.id.customerNameEditText);
        View bookAppointmentButton = findViewById(R.id.bookAppointmentButton);

        haircutPricePicker.setMinValue(5000);
        haircutPricePicker.setMaxValue(5000);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.time_slots_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        timeSlotsSpinner.setAdapter(adapter);

        bookAppointmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bookAppointment();
            }
        });
    }

    private void bookAppointment() {
        String selectedDate = getSelectedDate();
        String selectedTime = timeSlotsSpinner.getSelectedItem().toString();
        String customerName = customerNameEditText.getText().toString().trim();
        int haircutPrice = haircutPricePicker.getValue();

        if (!customerName.isEmpty()) {
            Appointment appointment = new Appointment(selectedDate, selectedTime, customerName, haircutPrice);
            appointmentsRef.push().setValue(appointment);
            Toast.makeText(this, "Cita agendada exitosamente", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Ingrese el nombre del cliente", Toast.LENGTH_SHORT).show();
        }
    }

    private String getSelectedDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(calendarView.getDate());
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        return day + "/" + month + "/" + year;
    }
}
