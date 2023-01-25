package net.therap.estaurant.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * @author nadimmahmud
 * @since 1/17/23
 */
@Entity
@Table(name = "order_line_item")
@NamedQueries({
        @NamedQuery(name = "OrderLineItem.findAll", query = "SELECT o FROM OrderLineItem o")
})
public class OrderLineItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "orderLineSeq")
    @SequenceGenerator(name = "orderLineSeq", sequenceName = "order_line_seq", allocationSize = 1)
    private int id;

    @Min(value = 1, message = "{input.number.quantity}")
    private int quantity;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "itemId")
    private Item item;

    @ManyToOne
    @JoinColumn(name = "orderId")
    private Order order;

    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;

    public OrderLineItem() {
    }

    public OrderLineItem(int id) {
        this.item = new Item(id);
    }

    public boolean isNew() {
        return id == 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;

        if (!(o instanceof OrderLineItem)) return false;

        OrderLineItem orderLineItem = (OrderLineItem) o;

        return getItem().getId() == orderLineItem.getItem().getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getItem());
    }

    @Override
    public String toString() {
        return "OrderLineItem{" +
                "id=" + id +
                ", quantity=" + quantity +
                ", status=" + status +
                ", item=" + item +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
