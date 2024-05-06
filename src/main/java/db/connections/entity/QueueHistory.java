package db.connections.entity;

import java.time.LocalDateTime;

/**
 * This class represents the queue history of a customer in the company.
 */
public class QueueHistory {

    private int id;
    private int customerId;
    private String servicePointName;
    private LocalDateTime arrivalTime;
    private LocalDateTime departureTime;
    private double queueTime;

    public QueueHistory(int id, int customerId, String servicePointName, LocalDateTime arrivalTime,
                        LocalDateTime departureTime, double queueTime) {
        this.id = id;
        this.customerId = customerId;
        this.servicePointName = servicePointName;
        this.arrivalTime = arrivalTime;
        this.departureTime = departureTime;
        this.queueTime = queueTime;
    }

    // Getters and setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getServicePointName() {
        return servicePointName;
    }

    public void setServicePointName(String servicePointName) {
        this.servicePointName = servicePointName;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public double getQueueTime() {
        return queueTime;
    }

    public void setQueueTime(double queueTime) {
        this.queueTime = queueTime;
    }
}
