package com.example.barberzur.Modelo;

public class Appointment {
    private String selectedDate;
    private String selectedTime;
    private String customerName;
    private int haircutPrice;
    private int Id;
    private String appointmentId;

    public Appointment(String selectedDate, String selectedTime, String customerName, int haircutPrice) {
        this.selectedDate = selectedDate;
        this.selectedTime = selectedTime;
        this.customerName = customerName;
        this.haircutPrice = haircutPrice;
    }

    // Getters and Setters

    public String getSelectedDate() {
        return selectedDate;
    }

    public void setSelectedDate(String selectedDate) {
        this.selectedDate = selectedDate;
    }

    public String getSelectedTime() {
        return selectedTime;
    }

    public void setSelectedTime(String selectedTime) {
        this.selectedTime = selectedTime;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public int getHaircutPrice() {
        return haircutPrice;
    }

    public void setHaircutPrice(int haircutPrice) {
        this.haircutPrice = haircutPrice;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "appointmentId='" + appointmentId + '\'' +
                "selectedDate='" + selectedDate + '\'' +
                ", selectedTime='" + selectedTime + '\'' +
                ", customerName='" + customerName + '\'' +
                ", haircutPrice=" + haircutPrice +
                ", Id=" + Id +
                '}';
    }
}
