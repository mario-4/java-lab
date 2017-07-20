package com.appgenesislab.rest.model;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.Identifiable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Simple object that administers all customer data. This user is the one on whose behalf modifications to {@link
 * Customer customer data} are made.
 *
 * @author Josh Long
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, isGetterVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.ANY)
@Entity
@Table(name = "user_account")
@EqualsAndHashCode(exclude = {"signupDate", "status"})
public class User implements Identifiable<Long>, Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
    private Set<Customer> customers = new HashSet<Customer>();

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "profile_photo_media_type")
    private String profilePhotoMediaType;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "user_name")
    private String username;

    @Column(name = "pass_word")
    private String password;

    @Column(name = "profile_photo_imported")
    private boolean profilePhotoImported;

    @Column(name = "enabled")
    private boolean enabled;

    @Column(name = "signup_date")
    private Date signupDate;

    public User() {
    }

    public User(Long id) {
        this.id = id;
    }

    // by default we don't attempt to represent the customers collection since it's
    // variable size and it might trigger Hibernate lazy-loading issues.
    public User(User usr) {
        this(usr, new HashSet<Customer>());
    }

    public User(User usr, Set<Customer> customerCollection) {
        this(usr.id, usr.username, usr.firstName, usr.lastName);
        this.customers = customerCollection;
        this.enabled = usr.enabled;
        this.profilePhotoMediaType = usr.profilePhotoMediaType;
        this.password = usr.password;
        this.profilePhotoImported = usr.profilePhotoImported;
        this.enabled = usr.enabled;
        this.signupDate = usr.signupDate;
    }

    public User(Long id, String u, String f, String l) {
        this(u, null, f, l);
        this.id = id;
    }

    public User(String username, String password, String firstName, String lastName) {
        this.firstName = firstName;
        this.password = password;
        this.lastName = lastName;
        this.username = username;
        this.signupDate = new Date();
        this.enabled = true;

    }

    @Override
    public Long getId() {
        return this.id;
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

    public String getProfilePhotoMediaType() {
        return profilePhotoMediaType;
    }

    public void setProfilePhotoMediaType(String profilePhotoMediaType) {
        this.profilePhotoMediaType = profilePhotoMediaType;
    }

    public boolean isProfilePhotoImported() {
        return profilePhotoImported;
    }

    public void setProfilePhotoImported(boolean profilePhotoImported) {
        this.profilePhotoImported = profilePhotoImported;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Date getSignupDate() {
        return signupDate;
    }

    public void setSignupDate(Date signupDate) {
        this.signupDate = signupDate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @com.fasterxml.jackson.annotation.JsonIgnore
    public Set<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(Set<Customer> customers) {
        this.customers = customers;
    }

}
