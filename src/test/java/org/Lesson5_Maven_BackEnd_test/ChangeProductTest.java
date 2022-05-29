package org.Lesson5_Maven_BackEnd_test;

import org.Lesson5_Maven_BackEnd_test.api.ProductService;
import org.Lesson5_Maven_BackEnd_test.dto.Product;
import org.Lesson5_Maven_BackEnd_test.utils.RetrofitUtils;
import lombok.SneakyThrows;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.*;
import retrofit2.Response;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class ChangeProductTest {

    static ProductService productService;
    Product product = null;
    int id = 1;
    String title;
    int price;
    String category;

    @BeforeAll
    static void beforeAll() {
        productService = RetrofitUtils.getRetrofit().create(ProductService.class);
    }

    void setUp() {
        product = new Product()
                .withId(id)
                .withTitle(title)
                .withPrice(price)
                .withCategoryTitle(category);
    }

    @Test
    @Tag("Positive")
    @DisplayName("Change product (Positive)")
    void modifyProductPositiveTest() throws IOException {
        Response<Product> response = productService.getProductById(id).execute();
        assertThat(response.isSuccessful(), CoreMatchers.is(true));
        assert response.body() != null;
        String titleOld = response.body().getTitle();
        int priceOld = response.body().getPrice();
        String categoryOld = response.body().getCategoryTitle();

        title = "Test";
        price = 99999;
        category = "Electronic";

        assertThat(title != titleOld, CoreMatchers.is(true));
        assertThat(price != priceOld, CoreMatchers.is(true));
        assertThat(category != categoryOld, CoreMatchers.is(true));

        setUp();
        response = productService.modifyProduct(product).execute();
        assertThat(response.isSuccessful(), CoreMatchers.is(true));
        assertThat(response.code(), equalTo(200));
        assert response.body() != null;
        assertThat(response.body().getTitle() != titleOld, is (true));
        assertThat(response.body().getPrice() != priceOld, is (true));
        assertThat(response.body().getCategoryTitle() != categoryOld, is (true));

        title = titleOld;
        price = priceOld;
        category = categoryOld;

        tearDown();
    }

    @Test
    @Tag("Negative")
    @DisplayName("Change product (Negative)")
    void modifyProductNegativeTest() throws IOException {
        Response<Product> response = productService.getProductById(id).execute();
        assertThat(response.isSuccessful(), CoreMatchers.is(true));
        assert response.body() != null;
        title = response.body().getTitle();
        price = response.body().getPrice();
        category = "Auto";

        setUp();
        response = productService.modifyProduct(product).execute();
        assertThat(response.isSuccessful(), CoreMatchers.is(false));
        assertThat(response.code(), equalTo(500));
    }

    @SneakyThrows
    void tearDown() {
        setUp();
        Response<Product> response = productService.modifyProduct(product).execute();
        assertThat(response.isSuccessful(), CoreMatchers.is(true));
    }
}