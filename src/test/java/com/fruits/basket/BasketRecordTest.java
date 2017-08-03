package com.fruits.basket;

import com.fruits.domain.Product;
import org.junit.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class BasketRecordTest {

    @Test
    public void shouldIncreaseCountForItem() throws Exception {
        // given
        Product orange = new Product("Orange", BigDecimal.valueOf(3.45));
        BasketRecord record = new BasketRecord(orange, 4L);

        // when
        record.addItems(3L);

        // then
        assertThat(record.getCount()).isEqualTo(7L);
    }
}