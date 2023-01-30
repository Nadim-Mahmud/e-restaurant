package net.therap.estaurant.entity;

/**
 * @author nadimmahmud
 * @since 1/17/23
 */
public enum Type {

    ADMIN("Admin"),
    CHEF("Chef"),
    WAITER("Waiter");

    private final String label;

    Type(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
