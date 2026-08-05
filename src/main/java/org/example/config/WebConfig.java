package org.example.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.data.web.config.EnableSpringDataWebSupport.PageSerializationMode;

/**
 * Serializa los {@link org.springframework.data.domain.Page} devueltos por los
 * controllers como un DTO estable (PagedModel) en lugar de exponer la estructura
 * interna de {@code PageImpl}, evitando el warning de Spring Data.
 */
@Configuration
@EnableSpringDataWebSupport(pageSerializationMode = PageSerializationMode.VIA_DTO)
public class WebConfig {
}
