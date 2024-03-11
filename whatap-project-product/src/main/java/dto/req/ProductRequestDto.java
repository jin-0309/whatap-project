package dto.req;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductRequestDto {

    private String name;
    private Double price;
    private String description;

    @Builder
    public ProductRequestDto(String name, Double price, String description) {
        this.name = name;
        this.price = price;
        this.description = description;
    }
}
