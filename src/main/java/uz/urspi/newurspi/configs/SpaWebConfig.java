package uz.urspi.newurspi.configs;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.ResourceResolver;
import org.springframework.web.servlet.resource.ResourceResolverChain;

import java.io.IOException;
import java.util.List;

/**
 * Serves Vite/React build from classpath:/static/ and falls back to index.html
 * for client-side routes (not /api, swagger, uploads, or real static files).
 */
@Configuration
public class SpaWebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/")
                .resourceChain(true)
                .addResolver(new SpaResourceResolver());
    }

    private static final class SpaResourceResolver implements ResourceResolver {

        private final Resource index = new ClassPathResource("/static/index.html");

        @Override
        public Resource resolveResource(
                HttpServletRequest request,
                String requestPath,
                List<? extends Resource> locations,
                ResourceResolverChain chain
        ) {
            if (isBackendPath(requestPath)) {
                return null;
            }
            Resource resolved = chain.resolveResource(request, requestPath, locations);
            if (resolved != null && resolved.exists() && resolved.isReadable()) {
                return resolved;
            }
            return index.exists() ? index : null;
        }

        @Override
        public String resolveUrlPath(
                String resourcePath,
                List<? extends Resource> locations,
                ResourceResolverChain chain
        ) {
            if (isBackendPath(resourcePath)) {
                return null;
            }
            String resolved = chain.resolveUrlPath(resourcePath, locations);
            if (resolved != null) {
                return resolved;
            }
            return index.exists() ? "/index.html" : null;
        }

        private boolean isBackendPath(String path) {
            String p = path.startsWith("/") ? path.substring(1) : path;
            return p.startsWith("api/")
                    || p.startsWith("swagger-ui")
                    || p.startsWith("v3/")
                    || p.startsWith("uploads/")
                    || p.startsWith("actuator/")
                    || p.startsWith("webjars/");
        }
    }
}
