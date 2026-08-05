package org.example.service;

import org.example.dto.ProductRequest;
import org.example.dto.ProductResponse;
import org.example.exception.ResourceNotFoundException;
import org.example.model.Product;
import org.example.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
public class ProductService {

    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public Page<ProductResponse> findAll(String category, String search, Pageable pageable) {
        Page<Product> products;
        if (category != null && !category.isBlank()) {
            products = repository.findByCategoryIgnoreCase(category, pageable);
        } else if (search != null && !search.isBlank()) {
            products = repository.findByTitleContainingIgnoreCase(search, pageable);
        } else {
            products = repository.findAll(pageable);
        }
        return products.map(ProductResponse::from);
    }

    @Transactional(readOnly = true)
    public ProductResponse findById(Long id) {
        return ProductResponse.from(getOrThrow(id));
    }

    @Transactional
    public ProductResponse create(ProductRequest request) {
        Product product = new Product();
        apply(product, request);
        return ProductResponse.from(repository.save(product));
    }

    @Transactional
    public ProductResponse update(Long id, ProductRequest request) {
        Product product = getOrThrow(id);
        apply(product, request);
        return ProductResponse.from(repository.save(product));
    }

    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Producto no encontrado con id " + id);
        }
        repository.deleteById(id);
    }

    private Product getOrThrow(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con id " + id));
    }

    private void apply(Product product, ProductRequest request) {
        product.setIdentifier(request.identifier());
        product.setTitle(request.title());
        product.setEvents(request.events() != null ? request.events() : new ArrayList<>());
        product.setImage(request.image());
        product.setImageAlt(request.imageAlt());
        product.setImportant(request.important());
        product.setPrice(request.price());
        product.setColors(request.colors() != null ? request.colors() : new ArrayList<>());
        product.setDetailsLabel(request.detailsLabel());
        product.setPlaceholder(request.placeholder());
        product.setCategory(request.category());
        product.setSectionTitle(request.sectionTitle());
        product.setPosterImage(request.posterImage());
        product.setVariant(request.variant());
        product.setVideoSrc(request.videoSrc());
        product.setCtaLabel(request.ctaLabel());
        if (request.active() != null) {
            product.setActive(request.active());
        }
    }
}
