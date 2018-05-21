package com.phanng.bkshop.model;

import java.io.Serializable;
import java.util.List;

public class Product implements Serializable {

    private String _id;
    private String productName;
    private String productCategory;
    private int productPrice;
    private List<String> productImages;
    private String productDescription;
    private List<String> productTags;
    private int productQuantity;
    private String dateModified;
    private String createdBy;
    private String dateCreated;
    private Integer status;

    public Product(String _id, String productName, String productCategory, int productPrice, List<String> productImages,
                   String productDescription, List<String> productTags, int productQuantity, String dateModified,
                   String createdBy, String dateCreated, int status) {
        super();
        this._id = _id;
        this.productName = productName;
        this.productCategory = productCategory;
        this.productPrice = productPrice;
        this.productImages = productImages;
        this.productDescription = productDescription;
        this.productTags = productTags;
        this.productQuantity = productQuantity;
        this.dateModified = dateModified;
        this.createdBy = createdBy;
        this.dateCreated = dateCreated;
        this.status = status;
    }

    /**
     * @see for creating to server
     * @param productName
     * @param productCategory
     * @param productPrice
     * @param productImages
     * @param productDescription
     * @param productTags
     * @param productQuantity
     */
    public Product(String productName, String productCategory, int productPrice, List<String> productImages,
                   String productDescription, List<String> productTags, int productQuantity) {
        super();
        this.productName = productName;
        this.productCategory = productCategory;
        this.productPrice = productPrice;
        this.productImages = productImages;
        this.productDescription = productDescription;
        this.productTags = productTags;
        this.productQuantity = productQuantity;
    }

    public Product() {
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
     * @return the productName
     */
    public String getProductName() {
        return productName;
    }

    /**
     * @param productName
     *            the productName to set
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }

    /**
     * @return the productCategory
     */
    public String getProductCategory() {
        return productCategory;
    }

    /**
     * @param productCategory
     *            the productCategory to set
     */
    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    /**
     * @return the productPrice
     */
    public int getProductPrice() {
        return productPrice;
    }

    /**
     * @param productPrice
     *            the productPrice to set
     */
    public void setProductPrice(int productPrice) {
        this.productPrice = productPrice;
    }

    /**
     * @return the productImages
     */
    public List<String> getProductImages() {
        return productImages;
    }

    /**
     * @param productImages
     *            the productImages to set
     */
    public void setProductImages(List<String> productImages) {
        this.productImages = productImages;
    }

    /**
     * @return the productDescription
     */
    public String getProductDescription() {
        return productDescription;
    }

    /**
     * @param productDescription
     *            the productDescription to set
     */
    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    /**
     * @return the productTags
     */
    public List<String> getProductTags() {
        return productTags;
    }

    /**
     * @param productTags
     *            the productTags to set
     */
    public void setProductTags(List<String> productTags) {
        this.productTags = productTags;
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
     * @return the dateModified
     */
    public String getDateModified() {
        return dateModified;
    }

    /**
     * @param dateModified
     *            the dateModified to set
     */
    public void setDateModified(String dateModified) {
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
     * @return the dateCreated
     */
    public String getDateCreated() {
        return dateCreated;
    }

    /**
     * @param dateCreated
     *            the dateCreated to set
     */
    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}

