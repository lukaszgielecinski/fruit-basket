package com.fruits.basket;

import com.fruits.domain.Product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Basket {

    private List<BasketRecord> basketRecords = new ArrayList<>();

    public void addItem(Product product, long count) {
        boolean productInBasket = basketRecords.stream()
                .filter(records -> records.getProduct().equals(product))
                .peek(record -> record.addItems(count))
                .findAny()
                .isPresent();

        if (!productInBasket) {
            basketRecords.add(new BasketRecord(product, count));
        }
    }

    Map<Product, Long> getItemCounts() {
        return basketRecords.stream()
                .collect(Collectors.toMap(BasketRecord::getProduct, BasketRecord::getCount));
    }

    public BigDecimal getValue() {
        return basketRecords.stream()
                .map(this::calculateItemValue)
                .reduce(BigDecimal.valueOf(0), BigDecimal::add);
    }

    private BigDecimal calculateItemValue(BasketRecord record) {
        BigDecimal itemCount = BigDecimal.valueOf(record.getCount());
        return record.getProduct()
                .getPrice().multiply(itemCount);
    }
}
