package com.fruits.basket;

import com.fruits.domain.Product;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class BasketTest {

    @Test
    public void shouldAdd_whenSingleItem() throws Exception {
        // given
        Basket basket = new Basket();
        Product product = new Product("Apple", BigDecimal.valueOf(1));

        // when
        basket.addItem(product, 1);

        // then
        Map<Product, Long> items = basket.getItemCounts();
        assertThat(items).hasSize(1)
                .containsOnlyKeys(product)
                .containsValue(1L);
    }

    @Test
    public void shouldSumCounts_whenSameItemAddedTwice() throws Exception {
        // given
        Basket basket = new Basket();
        Product product = new Product("Apple", BigDecimal.valueOf(1));

        // when
        basket.addItem(product, 1);
        basket.addItem(product, 2);

        // then
        Map<Product, Long> items = basket.getItemCounts();
        assertThat(items).hasSize(1)
                .containsOnlyKeys(product)
                .containsValue(3L);
    }

    @Test
    public void shouldAddTwoItems_whenSameNameButDifferentPrice() throws Exception {
        // given
        Basket basket = new Basket();
        Product product1 = new Product("Apple", BigDecimal.valueOf(1));
        Product product2 = new Product("Apple", BigDecimal.valueOf(2));

        // when
        basket.addItem(product1, 1);
        basket.addItem(product1, 3);
        basket.addItem(product2, 2);

        // then
        Map<Product, Long> items = basket.getItemCounts();
        assertThat(items).hasSize(2)
                .containsOnlyKeys(product1, product2)
                .containsEntry(product1, 4L)
                .containsEntry(product2, 2L);
    }

    @Test
    public void shouldReturnZero_whenNoItemsAddedToTheBasket() throws Exception {
        // given
        Basket basket = new Basket();
        // when
        BigDecimal basketValue = basket.getValue();
        // then
        assertThat(basketValue).isEqualTo(BigDecimal.valueOf(0));
    }

    @Test
    public void shouldReturnSingleItemPrice_whenOneItemAdded() throws Exception {
        // given
        Basket basket = new Basket();
        Product product = new Product("Apple", BigDecimal.valueOf(3.03));
        basket.addItem(product, 1);
        // when
        BigDecimal basketValue = basket.getValue();
        // then
        assertThat(basketValue).isEqualTo(BigDecimal.valueOf(3.03));
    }

    @Test
    public void shouldReturnPrice_whenSingleItemInTheBasketAddedManyTimes() throws Exception {
        // given
        Basket basket = new Basket();
        Product product = new Product("Apple", BigDecimal.valueOf(3.03));
        basket.addItem(product, 2);
        // when
        BigDecimal basketValue = basket.getValue();
        // then
        assertThat(basketValue).isEqualTo(BigDecimal.valueOf(6.06));
    }

    @Test
    public void shouldReturnPrice_whenManyItemsAdded() throws Exception {
        // given
        Basket basket = new Basket();
        Product product1 = new Product("Apple", BigDecimal.valueOf(1.02));
        Product product2 = new Product("Orange", BigDecimal.valueOf(2.10));
        Product product3 = new Product("Peach", BigDecimal.valueOf(3.13));
        basket.addItem(product1, 1);
        basket.addItem(product2, 2);
        basket.addItem(product3, 3);
        // when
        BigDecimal basketValue = basket.getValue();
        // then
        assertThat(basketValue).isEqualTo(BigDecimal.valueOf(14.61));
    }
}