package com.fruits.basket;

import com.fruits.domain.Product;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class BasketTest {

    private Basket basket;

    @Before
    public void setup() throws Exception {
        basket = new Basket();
    }

    @Test
    public void shouldReturnZero_whenNoItemsAddedToTheBasket() throws Exception {
        // when
        BigDecimal basketValue = basket.getValue();
        // then
        assertThat(basketValue).isEqualTo(BigDecimal.valueOf(0));
    }

    @Test
    public void shouldReturnSingleItemPrice_whenOneItemAdded() throws Exception {
        // given
        Product product = new Product("Apples", BigDecimal.valueOf(3.03));
        basket.addItem(product, 1);
        // when
        BigDecimal basketValue = basket.getValue();
        // then
        assertThat(basketValue).isEqualTo(BigDecimal.valueOf(3.03));
    }

    @Test
    public void shouldReturnPrice_whenSingleItemInTheBasketAddedManyTimes() throws Exception {
        // given
        Product product = new Product("Apples", BigDecimal.valueOf(3.03));
        basket.addItem(product, 2);
        // when
        BigDecimal basketValue = basket.getValue();
        // then
        assertThat(basketValue).isEqualTo(BigDecimal.valueOf(6.06));
    }

    @Test
    public void shouldReturnPrice_whenManyItemsAdded() throws Exception {
        // given
        Product product1 = new Product("Apples", BigDecimal.valueOf(1.02));
        Product product2 = new Product("Oranges", BigDecimal.valueOf(2.10));
        Product product3 = new Product("Peaches", BigDecimal.valueOf(3.13));
        basket.addItem(product1, 1);
        basket.addItem(product2, 2);
        basket.addItem(product3, 3);
        // when
        BigDecimal basketValue = basket.getValue();
        // then
        assertThat(basketValue).isEqualTo(BigDecimal.valueOf(14.61));
    }
}