package com.fruits.basket;

import com.fruits.domain.Product;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.math.BigDecimal;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class BasketTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Basket basket;

    @Before
    public void setup() throws Exception {
        basket = new Basket();
    }

    @Test
    public void shouldAdd_whenSingleItem() throws Exception {
        // given
        Product product = new Product("Apples", BigDecimal.valueOf(1));

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
        Product product = new Product("Apples", BigDecimal.valueOf(1));

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
        Product product1 = new Product("Apples", BigDecimal.valueOf(1));
        Product product2 = new Product("Apples", BigDecimal.valueOf(2));

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
    public void shouldAllowToAdd_whenOnlyKnownProducts() throws Exception {
        // given
        Product bananas = new Product("Bananas", BigDecimal.valueOf(1));
        Product oranges = new Product("Oranges", BigDecimal.valueOf(2));
        Product apples = new Product("Apples", BigDecimal.valueOf(2));
        Product lemons = new Product("Lemons", BigDecimal.valueOf(2));
        Product peaches = new Product("Peaches", BigDecimal.valueOf(2));
        basket.addItem(bananas, 1);
        basket.addItem(oranges, 2);
        basket.addItem(apples, 3);
        basket.addItem(lemons, 4);
        basket.addItem(peaches, 5);

        // when
        Map<Product, Long> items = basket.getItemCounts();

        // then
        assertThat(items).hasSize(5)
                .containsOnlyKeys(bananas, oranges, apples, lemons, peaches)
                .containsEntry(bananas, 1L)
                .containsEntry(oranges, 2L)
                .containsEntry(apples, 3L)
                .containsEntry(lemons, 4L)
                .containsEntry(peaches, 5L);
    }

    @Test
    public void shouldThrowIllegalArgumentException_whenAddedUnknownProduct() throws Exception {
        // given
        Product unknown = new Product("Unknown Product", BigDecimal.valueOf(1));

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Cannot add \"Unknown Product\" to the basket as is not allowed.");
        // when
        basket.addItem(unknown, 1);
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