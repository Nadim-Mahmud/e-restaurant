package net.therap.estaurant.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author nadimmahmud
 * @since 1/17/23
 */
@Entity
@Table(name = "order_table")
@NamedQueries({
        @NamedQuery(name = "Order.findAll", query = "SELECT o FROM Order o order by o.status"),
        @NamedQuery(name = "Order.findActiveOnly", query = "SELECT o FROM Order o WHERE o.restaurantTable.user.id = :waiterId AND o.status != 'SERVED'"),
        @NamedQuery(name = "Order.findByWaiterId", query = "SELECT o FROM Order o WHERE o.restaurantTable.user.id = :waiterId"),
        @NamedQuery(name = "Order.findOrderByTable", query = "SELECT o FROM Order o WHERE o.restaurantTable.id = :tableId")
})
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "orderTableSeq")
    @SequenceGenerator(name = "orderTableSeq", sequenceName = "order_table_seq", allocationSize = 1)
    private int id;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Transient
    private int estTime;

    @OneToOne
    @JoinColumn(name = "restaurantTableId")
    private RestaurantTable restaurantTable;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "orderId")
    private List<OrderLineItem> orderLineItemList;

    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;

    public Order() {
        orderLineItemList = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getEstTime() {
        return estTime;
    }

    public void setEstTime(int estTime) {
        this.estTime = estTime;
    }

    public RestaurantTable getRestaurantTable() {
        return restaurantTable;
    }

    public void setRestaurantTable(RestaurantTable restaurantTable) {
        this.restaurantTable = restaurantTable;
    }

    public List<OrderLineItem> getOrderLineItemList() {
        return orderLineItemList;
    }

    public void setOrderLineItemList(List<OrderLineItem> orderLineItemList) {
        this.orderLineItemList = orderLineItemList;
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

    public double totalBill() {
        int bill = 0;

        for (OrderLineItem items : orderLineItemList) {
            bill += items.getItem().getPrice();
        }

        return bill;
    }

    public boolean isNew() {
        return id == 0;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;

        if (!(o instanceof Order)) return false;

        Order order = (Order) o;

        return getId() == order.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
