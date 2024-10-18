package com.example.crud.controllers;

import com.example.crud.domain.product.Product;
import com.example.crud.domain.product.ProductRepository;
import com.example.crud.domain.product.RequestProduct;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductRepository productRepository;

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        var allProducts = productRepository.findAllByActiveTrue();
        return ResponseEntity.ok(allProducts);
    }

    @PostMapping
    public ResponseEntity<String> registerProduct(@RequestBody @Valid RequestProduct data) {
        Product newProduct = new Product(data);
        productRepository.save(newProduct);
        return ResponseEntity.ok("Produto criado com sucesso.");
    }

    @PutMapping
    @Transactional
    public ResponseEntity<String> uptadeProduct(@RequestBody @Valid RequestProduct data) {
        Optional<Product> optionalProduct = productRepository.findById(data.id());
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            product.setName(data.name());
            product.setPrice_in_cents(data.price_in_cents());
            return ResponseEntity.ok("Produto atualizado com sucesso.");
        }
        throw new EntityNotFoundException();
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deleteProduct(@PathVariable String id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            product.setActive(false);
            return ResponseEntity.noContent().build();
        }
        throw new EntityNotFoundException();
    }

    @PatchMapping("/{id}/add/{quantity}")
    @Transactional
    public ResponseEntity<String> incrementQuantity(@PathVariable String id, @PathVariable int quantity) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            product.addQuantity(quantity);
            return ResponseEntity.ok("Quantidade atualizada para " + quantity);
        }
        throw new EntityNotFoundException();
    }
}