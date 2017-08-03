package com.fruits.basket;

import com.fruits.domain.Product;
import com.google.common.collect.Lists;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;

public class Basket {

    private static final Collection<String> KNOWN_PRODUCTS = Collections.unmodifiableCollection(Lists.newArrayList("Bananas", "Oranges", "Apples", "Lemons", "Peaches"));

    private List<BasketRecord> basketRecords = new ArrayList<>();

    public void addItem(Product product, long count) {
        validateProduct(product);

        boolean productInBasket = basketRecords.stream()
                .filter(records -> records.getProduct().equals(product))
                .peek(record -> record.addItems(count))
                .findAny()
                .isPresent();

        if (!productInBasket) {
            basketRecords.add(new BasketRecord(product, count));
        }
    }

    private void validateProduct(Product product) {
        if (!KNOWN_PRODUCTS.contains(product.getName())) {
            String exceptionMessage = MessageFormat.format("Cannot add \"{0}\" to the basket as is not allowed.", product.getName());
            throw new IllegalArgumentException(exceptionMessage);
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
