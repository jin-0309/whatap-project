package service;

import dto.req.ProductRequestDto;
import entity.Product;
import exception.ProductNotFoundException;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import repository.ProductRepository;

@QuarkusTest
@TestTransaction
class ProductServiceTest {

    @Inject
    ProductRepository productRepository;

    @Inject
    ProductService productService;

    @Test
    void findById() {
        Product product = Product.builder()
                .name("test")
                .price(1000.)
                .description("test")
                .build();
        productRepository.persist(product);
        Assertions.assertEquals(product.getId(), productService.findById(product.getId()).getId());
        Assertions.assertThrows(ProductNotFoundException.class, () -> productService.findById(0L));
    }

    @Test
    void findPagination() {
        Product product1 = Product.builder()
                .name("test")
                .price(1000.)
                .description("test")
                .build();
        Product product2 = Product.builder()
                .name("test2")
                .price(1000.)
                .description("test")
                .build();
        productRepository.persist(product1);
        productRepository.persist(product2);
        Assertions.assertEquals(2, productService.findPagination(0, 2).size());
    }

    @Test
    void save() {
        ProductRequestDto productRequestDto = ProductRequestDto.builder()
                .name("test")
                .price(1000.)
                .description("test")
                .build();
        Long productId = productService.save(productRequestDto);
        Assertions.assertEquals(productId, productRepository.findById(productId).getId());
    }

    @Test
    void update() {
        ProductRequestDto productRequestDto = ProductRequestDto.builder()
                .name("test")
                .price(1000.)
                .description("test")
                .build();
        Long productId = productService.save(productRequestDto);
        ProductRequestDto updateDto = ProductRequestDto.builder()
                .name("update")
                .price(1500.)
                .description("update")
                .build();
        productService.update(updateDto, productId);
        Product product = productRepository.findById(productId);
        Assertions.assertEquals(updateDto.getName(), product.getName());
        Assertions.assertEquals(updateDto.getPrice(), product.getPrice());
        Assertions.assertEquals(updateDto.getDescription(), product.getDescription());
    }

    @Test
    void deleteById() {
        ProductRequestDto productRequestDto1 = ProductRequestDto.builder()
                .name("test")
                .price(1000.)
                .description("test")
                .build();
        ProductRequestDto productRequestDto2 = ProductRequestDto.builder()
                .name("test")
                .price(1000.)
                .description("test")
                .build();
        Long productId = productService.save(productRequestDto1);
        productService.save(productRequestDto2);
        Assertions.assertEquals(2, productRepository.findAll().stream().count());
        productService.deleteById(productId);
        Assertions.assertEquals(1, productRepository.findAll().stream().count());
        Assertions.assertThrows(ProductNotFoundException.class, () -> productService.findById(productId));
    }
}