package net.therap.estaurant.entity;

import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author nadimmahmud
 * @since 1/17/23
 */
@Entity
@Table(name = "order_table")
@SQLDelete(sql = "UPDATE order_table SET access_status = 'DELETED' WHERE id = ? AND version = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "access_status <> 'DELETED'")
@NamedQueries({
        @NamedQuery(name = "Order.findAll", query = "SELECT o FROM Order o order by o.status"),
        @NamedQuery(name = "Order.findActiveOnly", query = "SELECT o FROM Order o WHERE o.restaurantTable.user.id = :waiterId AND o.status != 'SERVED'"),
        @NamedQuery(name = "Order.findOrderByTable", query = "SELECT o FROM Order o WHERE o.restaurantTable.id = :tableId")
})
public class Order extends Persistent {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "orderTableSeq")
    @SequenceGenerator(name = "orderTableSeq", sequenceName = "order_table_seq", allocationSize = 1)
    private int id;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Transient
    private int estTime;

    @ManyToOne
    @JoinColumn(name = "restaurantTableId")
    private RestaurantTable restaurantTable;

    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "orderId")
    private List<OrderLineItem> orderLineItemList;

    public Order() {
        orderLineItemList = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
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

    public double totalBill() {
        int bill = 0;

        for (OrderLineItem items : orderLineItemList) {
            bill += items.getItem().getPrice() * items.getQuantity();
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
