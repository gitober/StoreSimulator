package db.connections.entity;

import java.time.LocalDateTime;

/**
 * The QueueHistory class represents the queue history of a customer in the company.
 * It encapsulates information such as the unique ID of the history record, customer ID,
 * service point name, arrival time, departure time, and queue time.
 */
public class QueueHistory {
    private int id;
    private int customerId;
    private String servicePointName;
    private LocalDateTime arrivalTime;
    private LocalDateTime departureTime;
    private double queueTime;

    /**
     * Constructs a QueueHistory object with the specified parameters.
     *
     * @param id              The unique ID of the queue history.
     * @param customerId      The ID of the customer associated with the queue history.
     * @param servicePointName The name of the service point.
     * @param arrivalTime     The time when the customer arrived at the service point.
     * @param departureTime   The time when the customer departed from the service point.
     * @param queueTime       The duration of time the customer spent in the queue.
     */
    public QueueHistory(int id, int customerId, String servicePointName, LocalDateTime arrivalTime, LocalDateTime departureTime, double queueTime) {
        this.id = id;
        this.customerId = customerId;
        this.servicePointName = servicePointName;
        this.arrivalTime = arrivalTime;
        this.departureTime = departureTime;
        this.queueTime = queueTime;
    }

    /**
     * Retrieves the unique ID of the queue history.
     *
     * @return The unique ID of the queue history.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the unique ID of the queue history.
     *
     * @param id The unique ID of the queue history to set.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Retrieves the ID of the customer associated with the queue history.
     *
     * @return The ID of the customer associated with the queue history.
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * Sets the ID of the customer associated with the queue history.
     *
     * @param customerId The ID of the customer associated with the queue history to set.
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**
     * Retrieves the name of the service point.
     *
     * @return The name of the service point.
     */
    public String getServicePointName() {
        return servicePointName;
    }

    /**
     * Sets the name of the service point.
     *
     * @param servicePointName The name of the service point to set.
     */
    public void setServicePointName(String servicePointName) {
        this.servicePointName = servicePointName;
    }

    /**
     * Retrieves the arrival time of the customer at the service point.
     *
     * @return The arrival time of the customer at the service point.
     */
    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    /**
     * Sets the arrival time of the customer at the service point.
     *
     * @param arrivalTime The arrival time of the customer at the service point to set.
     */
    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    /**
     * Retrieves the departure time of the customer from the service point.
     *
     * @return The departure time of the customer from the service point.
     */
    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    /**
     * Sets the departure time of the customer from the service point.
     *
     * @param departureTime The departure time of the customer from the service point to set.
     */
    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    /**
     * Retrieves the duration of time the customer spent in the queue.
     *
     * @return The duration of time the customer spent in the queue.
     */
    public double getQueueTime() {
        return queueTime;
    }

    /**
     * Sets the duration of time the customer spent in the queue.
     *
     * @param queueTime The duration of time the customer spent in the queue to set.
     */
    public void setQueueTime(double queueTime) {
        this.queueTime = queueTime;
    }
}
