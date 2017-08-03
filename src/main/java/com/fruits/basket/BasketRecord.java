package com.fruits.basket;

import com.fruits.domain.Product;

class BasketRecord {

    private Product product;
    private long count;

    BasketRecord(Product product, long count) {
        this.product = product;
        this.count = count;
    }

    Product getProduct() {
        return product;
    }

    long getCount() {
        return count;
    }

    void addItems(long increase) {
        count += increase;
    }
}
