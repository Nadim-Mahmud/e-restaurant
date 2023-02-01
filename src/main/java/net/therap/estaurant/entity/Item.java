package net.therap.estaurant.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author nadimmahmud
 * @since 1/17/23
 */
@Entity
@Table(name = "item")
@NamedQueries({
        @NamedQuery(name = "Item.findAll", query = "SELECT i FROM Item i order by i.name"),
        @NamedQuery(name = "Item.findByName", query = "SELECT i FROM Item i WHERE i.name = :itemName"),
        @NamedQuery(name = "Item.findAvailable", query = "SELECT i FROM Item i where i.availability = 'AVAILABLE' order by i.name")
})
public class Item extends Persistent {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "itemSeq")
    @SequenceGenerator(name = "itemSeq", sequenceName = "item_seq", allocationSize = 1)
    private int id;

    @NotNull(message = "{input.text}")
    @Size(min = 1, max = 50, message = "{input.text}")
    private String name;

    @NotNull(message = "{input.paragraph}")
    @Size(min = 1, max = 3000, message = "{input.paragraph}")
    private String description;

    @NotNull(message = "{input.number}")
    private double price;

    @NotNull(message = "{input.radio}")
    @Enumerated(EnumType.STRING)
    private Availability availability;

    @ManyToOne(cascade = CascadeType.MERGE, optional = false)
    @JoinColumn(name = "categoryId")
    @NotNull(message = "{input.text}")
    private Category category;

    @ManyToMany
    @JoinTable(
            name = "chef_item",
            joinColumns = {@JoinColumn(name = "itemId")},
            inverseJoinColumns = {@JoinColumn(name = "userId")}
    )
    private List<User> userList;

    @OneToMany(fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "itemId")
    private List<OrderLineItem> orderLineItemList;

    public Item() {
        userList = new ArrayList<>();
    }

    public Item(int id) {
        this();

        this.id = id;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Availability getAvailability() {
        return availability;
    }

    public void setAvailability(Availability availability) {
        this.availability = availability;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public boolean isNew() {
        return id == 0;
    }
}
