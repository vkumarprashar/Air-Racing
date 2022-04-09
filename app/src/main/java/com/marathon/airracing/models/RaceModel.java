package com.marathon.airracing.models;

public class RaceModel {
    private String raceName;
    private String date;
    private String time;
    private String tickets;
    private String ticketType;
    private String price;

    public RaceModel(String raceName, String date, String time) {
        this.raceName = raceName;
        this.date = date;
        this.time = time;
    }

    public RaceModel(String raceName, String date, String time, String tickets, String ticketType, String price) {
        this.raceName = raceName;
        this.date = date;
        this.time = time;
        this.tickets = tickets;
        this.ticketType = ticketType;
        this.price = price;
    }

    public String getRaceName() {
        return raceName;
    }

    public void setRaceName(String raceName) {
        this.raceName = raceName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTickets() {
        return tickets;
    }

    public void setTickets(String tickets) {
        this.tickets = tickets;
    }

    public String getTicketType() {
        return ticketType;
    }

    public void setTicketType(String ticketType) {
        this.ticketType = ticketType;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
