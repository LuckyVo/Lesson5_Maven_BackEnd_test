package org.Lesson5_Maven_BackEnd_test;

import org.Lesson5_Maven_BackEnd_test.api.ProductService;
import org.Lesson5_Maven_BackEnd_test.dto.Product;
import org.Lesson5_Maven_BackEnd_test.utils.RetrofitUtils;
import lombok.SneakyThrows;
import okhttp3.ResponseBody;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.*;
import retrofit2.Response;
import java.io.IOException;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import com.github.javafaker.Faker;

public class CreateProductTest {

    static ProductService productService;
    Faker faker = new Faker();
    Product product = null;
    String category;
    int id;

    @BeforeAll
    static void beforeAll() {
        productService = RetrofitUtils.getRetrofit().create(ProductService.class);
    }

//    @BeforeEach
    void setUp() {
        product = new Product()
                .withTitle(faker.food().ingredient())
                .withPrice((int) (Math.random() * 10000))
                .withCategoryTitle(category);
    }

    @Test
    @Tag("Positive")
    @DisplayName("Product creation (Positive)")
    void createProductTest() throws IOException {
        category = "Food";
        setUp();
        Response<Product> response = productService.createProduct(product).execute();

        assertThat(response.code(), equalTo(201));
        assert response.body() != null;
        assertThat(response.body().getCategoryTitle(), equalTo(category));
        assertThat(response.isSuccessful(), CoreMatchers.is(true));

        id =  response.body().getId();
        tearDown();

        category = "Electronic";
        setUp();
        response = productService.createProduct(product).execute();

        assertThat(response.code(), equalTo(201));
        assert response.body() != null;
        assertThat(response.body().getCategoryTitle(), equalTo(category));
        assertThat(response.isSuccessful(), CoreMatchers.is(true));

        id =  response.body().getId();
        tearDown();
    }

    @SneakyThrows
//    @AfterEach
    void tearDown() {
        Response<ResponseBody> response = productService.deleteProduct(id).execute();
        assertThat(response.isSuccessful(), CoreMatchers.is(true));
    }
}
