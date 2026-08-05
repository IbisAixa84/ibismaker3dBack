package org.example.dto;

import org.example.model.Product;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

/**
 * Representacion de un producto que se devuelve al cliente.
 */
public record ProductResponse(
        Long id,
        String identifier,
        String title,
        List<String> events,
        String image,
        String imageAlt,
        String important,
        BigDecimal price,
        List<String> colors,
        String detailsLabel,
        String placeholder,
        String category,
        String sectionTitle,
        String posterImage,
        String variant,
        String videoSrc,
        String ctaLabel,
        boolean active,
        Instant createdAt,
        Instant updatedAt
) {

    public static ProductResponse from(Product p) {
        return new ProductResponse(
                p.getId(),
                p.getIdentifier(),
                p.getTitle(),
                p.getEvents(),
                p.getImage(),
                p.getImageAlt(),
                p.getImportant(),
                p.getPrice(),
                p.getColors(),
                p.getDetailsLabel(),
                p.getPlaceholder(),
                p.getCategory(),
                p.getSectionTitle(),
                p.getPosterImage(),
                p.getVariant(),
                p.getVideoSrc(),
                p.getCtaLabel(),
                p.isActive(),
                p.getCreatedAt(),
                p.getUpdatedAt()
        );
    }
}
