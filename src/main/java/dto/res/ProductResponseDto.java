package dto.res;

import entity.Product;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
public class ProductResponseDto {
    private String name;
    private Float price;
    private String description;

    @Builder
    public ProductResponseDto(String name, Float price, String description) {
        this.name = name;
        this.price = price;
        this.description = description;
    }
}
