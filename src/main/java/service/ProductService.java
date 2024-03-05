package service;

import dto.req.ProductRequestDto;
import dto.res.ProductResponseDto;
import entity.Product;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import repository.ProductRepository;

@ApplicationScoped
@Transactional
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

    public List<ProductResponseDto> findPagination(int page, int size) {
        return productRepository.findAllProductPagination(page, size).stream().map(this::toDto).toList();
    }

    public Long addProduct(ProductRequestDto dto) {
        Product product = toEntity(dto);
        productRepository.persist(product);
        return product.getId();
    }

    private ProductResponseDto toDto(Product product) {
        return ProductResponseDto.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .description(product.getDescription())
                .build();
    }

    public Product toEntity(ProductRequestDto dto) {
        return Product.builder()
                .name(dto.getName())
                .price(dto.getPrice())
                .description(dto.getDescription())
                .build();
    }
}
