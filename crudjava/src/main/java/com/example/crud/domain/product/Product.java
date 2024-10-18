package com.example.crud.domain.product;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.UUID;

@Table(name="product")
@Entity(name="product")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Product {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String name;

    private Integer price_in_cents;

    private boolean active;

    private int quantity;

    public Product(RequestProduct requestProduct) {
        this.name = requestProduct.name();
        this.price_in_cents = requestProduct.price_in_cents();
        this.active = true;
        this.quantity = 1;
    }

    public void setName(@NotBlank String name) {
        this.name = name;
    }
    public void setPrice_in_cents(@NotBlank Integer price_in_cents) {
        this.price_in_cents = price_in_cents;
    }

    public String getId() {
        return this.id;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean getActive() {
        return this.active;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void addQuantity(int quantity) {
        this.quantity = quantity;
    }
}
