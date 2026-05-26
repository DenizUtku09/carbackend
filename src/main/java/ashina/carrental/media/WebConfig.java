package ashina.carrental.media;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Exposes the uploads directory (configured by {@code app.media.upload-dir})
 * at {@code /uploads/**} so {@code <img src="/uploads/abc.jpg">} resolves
 * straight to the file on disk — no controller round-trip per image.
 */
@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final MediaStorageService storage;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // ResourceLocations needs a trailing slash on a file: URI
        String location = storage.rootPath().toUri().toString();
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations(location);
    }
}
