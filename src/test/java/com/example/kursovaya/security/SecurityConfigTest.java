package com.example.kursovaya.security;

import com.example.kursovaya.Product;
import com.example.kursovaya.controller.ProductController;
import com.example.kursovaya.controller.ProductRequestMapper;
import com.example.kursovaya.service.ProductService;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureWebMvc;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.context.ContextConfiguration;
import com.example.kursovaya.KursovayaApplication;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
@ContextConfiguration(classes = KursovayaApplication.class)
@Import({
        SecurityConfig.class,
        SecurityUsersConfig.class,
        ProductRequestMapper.class
})
class SecurityConfigTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductService productService;

    // Проверка: без авторизации доступ запрещён
    @Test
    void unauthorizedUserCannotGetProducts() throws Exception {
        mockMvc.perform(get("/api/products"))
                .andExpect(status().isUnauthorized());
    }

    // Проверка: все четыре роли могут просматривать товары
    @Test
    void allRolesCanGetProducts() throws Exception {
        when(productService.getFilteredProducts(null, null, null))
                .thenReturn(java.util.Collections.emptyList());

        String[][] users = {
                {"admin", "admin123"},
                {"manager", "manager123"},
                {"customer", "customer123"},
                {"user", "user123"}
        };

        for (String[] user : users) {
            mockMvc.perform(get("/api/products")
                            .with(httpBasic(user[0], user[1])))
                    .andExpect(status().isOk());
        }
    }

    // Проверка: менеджер может добавлять товар
    @Test
    void managerCanAddProduct() throws Exception {
        when(productService.addProduct(any(Product.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        mockMvc.perform(post("/api/products")
                        .with(httpBasic("manager", "manager123"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "article": "TEST-1",
                                    "productName": "Test Smartphone",
                                    "category": "Smartphones",
                                    "productType": "SMARTPHONE",
                                    "price": 50000,
                                    "stockQuantity": 10
                                }
                                """))
                .andExpect(status().isCreated());
    }

    // Проверка: покупатель не может добавлять товар
    @Test
    void customerCannotAddProduct() throws Exception {
        mockMvc.perform(post("/api/products")
                        .with(httpBasic("customer", "customer123"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "article": "TEST-2",
                                    "productName": "Test Smartphone",
                                    "category": "Smartphones",
                                    "productType": "SMARTPHONE",
                                    "price": 50000,
                                    "stockQuantity": 10
                                }
                                """))
                .andExpect(status().isForbidden());
    }

    // Проверка: администратор может добавлять товар
    @Test
    void adminCanAddProduct() throws Exception {
        when(productService.addProduct(any(Product.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        mockMvc.perform(post("/api/products")
                        .with(httpBasic("admin", "admin123"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "article": "TEST-3",
                                    "productName": "Test Laptop",
                                    "category": "Laptops",
                                    "productType": "LAPTOP",
                                    "price": 80000,
                                    "stockQuantity": 5
                                }
                                """))
                .andExpect(status().isCreated());
    }

    // Проверка: менеджер может изменять товар
    @Test
    void managerCanUpdateProduct() throws Exception {
        when(productService.updateProduct(eq("TEST-1"), any(Product.class)))
                .thenAnswer(invocation -> invocation.getArgument(1));

        mockMvc.perform(put("/api/products/TEST-1")
                        .with(httpBasic("manager", "manager123"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "productName": "Updated Smartphone",
                                    "category": "Smartphones",
                                    "productType": "SMARTPHONE",
                                    "price": 55000,
                                    "stockQuantity": 15
                                }
                                """))
                .andExpect(status().isOk());
    }

    // Проверка: обычный пользователь не может изменять товар
    @Test
    void userCannotUpdateProduct() throws Exception {
        mockMvc.perform(put("/api/products/TEST-1")
                        .with(httpBasic("user", "user123"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "productName": "Updated Smartphone",
                                    "category": "Smartphones",
                                    "productType": "SMARTPHONE",
                                    "price": 55000,
                                    "stockQuantity": 15
                                }
                                """))
                .andExpect(status().isForbidden());
    }

    // Проверка: менеджер не может удалять товар
    @Test
    void managerCannotDeleteProduct() throws Exception {
        mockMvc.perform(delete("/api/products/TEST-1")
                        .with(httpBasic("manager", "manager123")))
                .andExpect(status().isForbidden());
    }

    // Проверка: администратор может удалять товар
    @Test
    void adminCanDeleteProduct() throws Exception {
        when(productService.deleteProduct("TEST-1"))
                .thenReturn(true);

        mockMvc.perform(delete("/api/products/TEST-1")
                        .with(httpBasic("admin", "admin123")))
                .andExpect(status().isNoContent());
    }

    // Проверка: неверный пароль не даёт доступ
    @Test
    void invalidPasswordCannotAccessProducts() throws Exception {
        mockMvc.perform(get("/api/products")
                        .with(httpBasic("admin", "wrong-password")))
                .andExpect(status().isUnauthorized());
    }
}
