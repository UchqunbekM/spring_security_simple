package com.example.appspringsecurityfirst.Controller;

import com.example.appspringsecurityfirst.Entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpEntity;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/product")
public class ProductCont {

    @Autowired
    ProductRepo productRepo;

    @PreAuthorize(value = "hasAnyAuthority('READ_ALL')")
    @GetMapping
    public HttpEntity<?> get() {
        return ResponseEntity.ok(productRepo.findAll());
    }

    @PreAuthorize(value = "hasAnyAuthority('ADD_PRODUCT')")
    @PostMapping
    public HttpEntity<?> save(@RequestBody Product product) {
        return ResponseEntity.ok(productRepo.save(product));
    }

    @PutMapping("/{id}")
    public HttpEntity<?> edit(@PathVariable int id, @RequestBody Product product) {
        Optional<Product> byId = productRepo.findById(id);
        if (byId.isPresent()) {
            Product product1 = byId.get();
            product1.setName(product.getName());
            ResponseEntity.ok(productRepo.save(product1));
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> delete(@PathVariable int id) {
        productRepo.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public HttpEntity<?> byid(@PathVariable int id) {
        Optional<Product> byId = productRepo.findById(id);
        return ResponseEntity.status(byId.isPresent() ? 201 : 409).body(byId.orElse(null));
    }
}
