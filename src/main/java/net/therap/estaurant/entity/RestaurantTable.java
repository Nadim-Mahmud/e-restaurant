package net.therap.estaurant.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
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
@Table(name = "restaurant_table")
@NamedQueries({
        @NamedQuery(name = "RestaurantTable.findAll", query = "SELECT r FROM RestaurantTable r")
})
public class RestaurantTable implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "restaurantTableSeq")
    @SequenceGenerator(name = "restaurantTableSeq", sequenceName = "restaurant_table_seq", allocationSize = 1)
    private int id;

    @NotNull(message = "{input.text}")
    @Size(min = 1, max = 45, message = "{input.text}")
    private String name;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @OneToOne(mappedBy = "restaurantTable")
    private Order order;

    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;

    public RestaurantTable() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public boolean isNew() {
        return id == 0;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;

        if (!(o instanceof RestaurantTable)) return false;

        RestaurantTable that = (RestaurantTable) o;

        return getId() == that.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
