package edu.meialua.kidsgrace.adapters.in;

import jakarta.persistence.*;
import jdk.jfr.DataAmount;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 250, nullable = false)
    private String name;

    @Column(name = "user_name", length = 250, nullable = false, unique = true)
    private String userName;
    @Column(name = "email", length = 250, nullable = false)
    private String email;
    @Column(name = "password", length = 250, nullable = false)
    private String password;

    @Column(name = "telephone", length = 250, nullable = false)
    private String telephone;

    @Column(name = "address", length = 500, nullable = false)
    private String address;

    @Column(name = "imageProfile", length = 500)
    private int imageProfile;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private List<edu.meialua.kidsgrace.adapters.in.Role> roles = new ArrayList<>();

    public User() {
    }

    public User(Long id, String name, String userName, String email, String password, String telephone, String address,
            List<Role> roles, int imageProfile) {
        this.id = id;
        this.name = name;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.telephone = telephone;
        this.address = address;
        this.roles = roles;
        this.imageProfile = imageProfile;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getImageProfile() {
        return imageProfile;
    }

    public void setImageProfile(int imageProfile) {
        this.imageProfile = imageProfile;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
