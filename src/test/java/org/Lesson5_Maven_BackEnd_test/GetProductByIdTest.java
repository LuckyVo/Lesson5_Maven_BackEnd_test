package org.Lesson5_Maven_BackEnd_test;

import org.Lesson5_Maven_BackEnd_test.api.ProductService;
import org.Lesson5_Maven_BackEnd_test.dto.Product;
import org.Lesson5_Maven_BackEnd_test.utils.RetrofitUtils;
import lombok.SneakyThrows;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.*;
import retrofit2.Response;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class GetProductByIdTest {

    static ProductService productService;

    @BeforeAll
    static void beforeAll() {
        productService = RetrofitUtils.getRetrofit().create(ProductService.class);
    }

    @SneakyThrows
    @Test
    @Tag("Positive")
    @DisplayName("Get product by ID (Positive)")
    void getCategoryByIdPositiveTest() {
        Response<Product> response = productService.getProductById(1).execute();

        assertThat(response.isSuccessful(), CoreMatchers.is(true));
        assertThat(response.code(), equalTo(200));
        assert response.body() != null;
        assertThat(response.body().getId(), equalTo(1));
        assertThat(response.body().getTitle(), equalTo("Milk"));
        assertThat(response.body().getPrice(), equalTo(95));
        assertThat(response.body().getCategoryTitle(), equalTo("Food"));

        response = productService.getProductById(5).execute();

        assertThat(response.isSuccessful(), CoreMatchers.is(true));
        assertThat(response.code(), equalTo(200));
        assert response.body() != null;
        assertThat(response.body().getId(), equalTo(5));
        assertThat(response.body().getTitle(), equalTo("LG TV 1"));
        assertThat(response.body().getPrice(), equalTo(50000));
        assertThat(response.body().getCategoryTitle(), equalTo("Electronic"));
    }

    @SneakyThrows
    @Test
    @Tag("Negative")
    @DisplayName("Get product by ID (Negative)")
    void getCategoryByIdNegativeTest() {
        Response<Product> response = productService.getProductById(0).execute();

        assertThat(response.isSuccessful(), CoreMatchers.is(false));
        assertThat(response.code(), equalTo(404));
    }
}
