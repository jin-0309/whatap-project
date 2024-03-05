package dto.res;

import entity.Product;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
public class ProductResponseDto {
    private Long id;
    private String name;
    private Float price;
    private String description;

    @Builder
    public ProductResponseDto(Long id, String name, Float price, String description) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
    }
}
