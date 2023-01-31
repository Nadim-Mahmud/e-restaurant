package net.therap.estaurant.entity;

import net.therap.estaurant.validator.CookingTimeGroup;
import net.therap.estaurant.validator.QuantityGroup;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
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
        @NamedQuery(name = "OrderLineItem.findAll", query = "SELECT o FROM OrderLineItem o"),
        @NamedQuery(name = "OrderLineItem.findOrdersOnProcess", query = "SELECT o FROM OrderLineItem o WHERE (o.status = 'ORDERED' or o.status = 'PREPARING') and o.item in :itemList order by o.status")
})
public class OrderLineItem extends Persistent {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "orderLineSeq")
    @SequenceGenerator(name = "orderLineSeq", sequenceName = "order_line_seq", allocationSize = 1)
    private int id;

    @CreationTimestamp
    private Date acceptedAt;

    @Min(value = 0, message = "{input.number.estCookTime}", groups = CookingTimeGroup.class)
    @Max(value = 180, message = "{input.number.estCookTime}", groups = CookingTimeGroup.class)
    private int estCookingTime;

    @Min(value = 1, message = "{input.number.quantity}", groups = QuantityGroup.class)
    private int quantity;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne(optional = false)
    @JoinColumn(name = "itemId")
    private Item item;

    @ManyToOne(optional = false)
    @JoinColumn(name = "orderId")
    private Order order;

    public OrderLineItem() {
    }

    public OrderLineItem(int id) {
        super();

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

    public Date getAcceptedAt() {
        return acceptedAt;
    }

    public void setAcceptedAt(Date acceptedAt) {
        this.acceptedAt = acceptedAt;
    }

    public int getEstCookingTime() {
        return estCookingTime;
    }

    public void setEstCookingTime(int estCookingTime) {
        this.estCookingTime = estCookingTime;
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
}
