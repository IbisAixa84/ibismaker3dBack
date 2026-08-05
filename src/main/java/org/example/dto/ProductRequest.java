package org.example.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.List;

/**
 * Datos de entrada para crear o actualizar un producto.
 */
public record ProductRequest(

        @NotBlank(message = "El identificador es obligatorio")
        @Size(max = 255)
        String identifier,

        @NotBlank(message = "El titulo es obligatorio")
        @Size(max = 255)
        String title,

        List<String> events,

        @Size(max = 500)
        String image,

        @Size(max = 255)
        String imageAlt,

        @Size(max = 2000)
        String important,

        @NotNull(message = "El precio es obligatorio")
        @DecimalMin(value = "0.0", inclusive = true, message = "El precio no puede ser negativo")
        BigDecimal price,

        List<String> colors,

        @Size(max = 255)
        String detailsLabel,

        @Size(max = 255)
        String placeholder,

        @Size(max = 100)
        String category,

        @Size(max = 255)
        String sectionTitle,

        @Size(max = 500)
        String posterImage,

        @Size(max = 100)
        String variant,

        @Size(max = 500)
        String videoSrc,

        @Size(max = 255)
        String ctaLabel,

        Boolean active
) {
}
