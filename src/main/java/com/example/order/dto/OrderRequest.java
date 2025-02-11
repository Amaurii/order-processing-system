package com.example.order.dto;

import com.example.order.model.Product;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Builder
public class OrderRequest {

    @NotNull(message = "CustomerId cannot be null")
    private String customerId;

    private List<Product> products;
}
