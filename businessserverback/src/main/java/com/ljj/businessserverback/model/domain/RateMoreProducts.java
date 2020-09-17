package com.ljj.businessserverback.model.domain;

public class RateMoreProducts {
    private String _id;
    private int productId;
    private int count;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }


    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
