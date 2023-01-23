package net.therap.estaurant.entity;

import net.therap.estaurant.util.Encryption;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author nadimmahmud
 * @since 1/17/23
 */
@Entity
@Table(name = "user")
@NamedQueries({
        @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u"),
        @NamedQuery(name = "User.findByEmail", query = "SELECT u FROM User u WHERE email= :email"),
        @NamedQuery(name = "User.findChef", query = "SELECT u FROM User u WHERE u.type = 'CHEF'"),
})
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userSeq")
    @SequenceGenerator(name = "userSeq", sequenceName = "user_seq", allocationSize = 1)
    private int id;

    @NotNull(message = "{input.text}")
    @Size(min = 1, max = 45, message = "{input.text}")
    private String firstName;

    @NotNull(message = "{input.text}")
    @Size(min = 1, max = 45, message = "{input.text}")
    private String lastName;

    @NotNull(message = "{input.date}")
    private Date dateOfBirth;

    @Size(min = 1, max = 45, message = "{input.email}")
    @Email(message = "{input.email}")
    private String email;

    @NotNull(message = "{input.text}")
    @Size(min = 1, max = 45, message = "{input.text}")
    private String password;

    @Enumerated(EnumType.STRING)
    private Type type;

    @NotNull(message = "{input.date}")
    private Date joiningDate;

    @OneToMany(mappedBy = "user")
    private List<RestaurantTable> restaurantTableList;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "chef_item",
            joinColumns = {@JoinColumn(name = "userId")},
            inverseJoinColumns = {@JoinColumn(name = "itemId")}
    )
    private List<Item> itemList;

    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;

    public User() {
        itemList = new ArrayList<>();
    }

    public User(String firstName, String lastName, Date dateOfBirth, String email, String password, Type type, Date joiningDate, List<RestaurantTable> restaurantTableList, List<Item> itemList) {
        super();

        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.password = password;
        this.type = type;
        this.joiningDate = joiningDate;
        this.restaurantTableList = restaurantTableList;
        this.itemList = itemList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        this.password = Encryption.getPBKDF2(password);
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Date getJoiningDate() {
        return joiningDate;
    }

    public void setJoiningDate(Date joiningDate) {
        this.joiningDate = joiningDate;
    }

    public List<RestaurantTable> getRestaurantTableList() {
        return restaurantTableList;
    }

    public void setRestaurantTableList(List<RestaurantTable> restaurantTableList) {
        this.restaurantTableList = restaurantTableList;
    }

    public List<Item> getItemList() {
        return itemList;
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
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

    public boolean isNew(){
        return id == 0;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", type=" + type +
                ", joiningDate=" + joiningDate +
                ", restaurantTableList=" + restaurantTableList +
                ", itemList=" + itemList +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
