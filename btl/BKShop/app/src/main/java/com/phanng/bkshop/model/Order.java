/**
 *
 */
package com.phanng.bkshop.model;

/**
 * @author tptha
 *
 */
public class Order {
    private String _id;
    private String productID;
    private int productQuantity;
    private Long dateCreated;
    private Long dateModified;
    private String createdBy;
    private int status;

    // use for get from server
    public Order(String _id, String productID, int productQuantity, Long dateCreated, Long dateModified,
                 String createdBy, int status) {
        super();
        this._id = _id;
        this.productID = productID;
        this.productQuantity = productQuantity;
        this.dateCreated = dateCreated;
        this.dateModified = dateModified;
        this.createdBy = createdBy;
        this.status = status;
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
     * @return the productID
     */
    public String getProductID() {
        return productID;
    }

    /**
     * @param productID
     *            the productID to set
     */
    public void setProductID(String productID) {
        this.productID = productID;
    }

    /**
     * @return the productQuantity
     */
    public int getProductQuantity() {
        return productQuantity;
    }

    /**
     * @param productQuantity
     *            the productQuantity to set
     */
    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
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
     * @return the createdBy
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * @param createdBy
     *            the createdBy to set
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * @return the status
     */
    public int getStatus() {
        return status;
    }

    /**
     * @param status
     *            the status to set
     */
    public void setStatus(int status) {
        this.status = status;
    }

}
