package net.therap.estaurant.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author nadimmahmud
 * @since 1/17/23
 */
@Entity
@Table(name = "order_table")
@NamedQueries({
        @NamedQuery(name = "Order.findAll", query = "SELECT o FROM Order o"),
        @NamedQuery(name = "Order.findOrderByTable", query = "SELECT o FROM Order o WHERE o.restaurantTable.id = :tableId")
})
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "orderTableSeq")
    @SequenceGenerator(name = "orderTableSeq", sequenceName = "order_table_seq", allocationSize = 1)
    private int id;

    @CreationTimestamp
    private Date placedAt;

    private int estServeTime;

    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToOne
    @JoinColumn(name = "restaurantTableId")
    private RestaurantTable restaurantTable;

    @OneToMany( fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
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

    public Date getPlacedAt() {
        return placedAt;
    }

    public void setPlacedAt(Date placedAt) {
        this.placedAt = placedAt;
    }

    public int getEstServeTime() {
        return estServeTime;
    }

    public void setEstServeTime(int estServeTime) {
        this.estServeTime = estServeTime;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
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

    public boolean isNew() {
        return id == 0;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", placedAt=" + placedAt +
                ", estServeTime=" + estServeTime +
                ", status=" + status +
                ", restaurantTable=" + restaurantTable +
                ", orderLineItemList=" + orderLineItemList +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
