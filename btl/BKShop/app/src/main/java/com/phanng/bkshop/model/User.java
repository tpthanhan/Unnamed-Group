/**
 *
 */
package com.phanng.bkshop.model;

import java.io.Serializable;

/**
 * @author tptha
 *
 */
@SuppressWarnings("serial")
public class User implements Serializable {

    private String _id;
    private String email;
    private String password;
    private String fullName;
    private String phoneNumber;
    private String address;
    private String dateOfBirth;
    private String avatar;
    private Long dateCreated;
    private Long dateModified;

    public User(String email, String password, String fullName, String phoneNumber, String address,
                String dateOfBirth) {
        super();
        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.dateOfBirth = dateOfBirth;
    }

    public User(String _id, String email, String fullName, String phoneNumber, String address, String dateOfBirth,
                String avatar, Long dateCreated, Long dateModified) {
        super();
        this._id = _id;
        this.email = email;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.dateOfBirth = dateOfBirth;
        this.avatar = avatar;
        this.dateCreated = dateCreated;
        this.dateModified = dateModified;
    }

    public User(String email, String password) {
        super();
        this.email = email;
        this.password = password;
    }

    /**
     * @return the _id
     */
    public String get_id() {
        return _id;
    }

    /**
     * @param _id
     *            the _id to set
     */
    public void set_id(String _id) {
        this._id = _id;
    }

    /**
     * @return the avatar
     */
    public String getAvatar() {
        return avatar;
    }

    /**
     * @param avatar
     *            the avatar to set
     */
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    /**
     * @return the dateCreated
     */
    public Long getDateCreated() {
        return dateCreated;
    }

    /**
     * @param dateCreated
     *            the dateCreated to set
     */
    public void setDateCreated(Long dateCreated) {
        this.dateCreated = dateCreated;
    }

    /**
     * @return the dateModified
     */
    public Long getDateModified() {
        return dateModified;
    }

    /**
     * @param dateModified
     *            the dateModified to set
     */
    public void setDateModified(Long dateModified) {
        this.dateModified = dateModified;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email
     *            the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password
     *            the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the fullName
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * @param fullName
     *            the fullName to set
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * @return the phoneNumber
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * @param phoneNumber
     *            the phoneNumber to set
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address
     *            the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return the dateOfBirth
     */
    public String getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     * @param dateOfBirth
     *            the dateOfBirth to set
     */
    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

}
