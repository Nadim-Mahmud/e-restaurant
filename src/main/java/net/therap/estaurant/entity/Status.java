package net.therap.estaurant.entity;

/**
 * @author nadimmahmud
 * @since 1/17/23
 */
public enum Status {

    ORDERED("Ordered"),
    PREPARING("Preparing"),
    PREPARED("prepared"),
    SERVED("served");

    private final String label;

    Status(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
