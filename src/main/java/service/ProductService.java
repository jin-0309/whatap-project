package service;

import dto.res.ProductResponseDto;
import entity.Product;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.Optional;
import repository.ProductRepository;

@ApplicationScoped
public class ProductService {

    ProductRepository productRepository;

    @Inject
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductResponseDto findById(Long id) {
        Optional<Product> product = productRepository.findByIdOptional(id);
        return toDto(product.orElseThrow());
    }

    private ProductResponseDto toDto(Product product) {
        return ProductResponseDto.builder()
                .name(product.getName())
                .price(product.getPrice())
                .description(product.getDescription())
                .build();
    }
}
