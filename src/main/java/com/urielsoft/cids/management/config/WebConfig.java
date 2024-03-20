package com.urielsoft.cids.management.config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    private final String uploadDirectory;

    public WebConfig(@Value("${upload.directory}") String uploadDirectory) {
        this.uploadDirectory = uploadDirectory;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String uploadFolderPath;
        if (uploadDirectory.startsWith("/")) {
            uploadFolderPath = System.getProperty("user.dir") + uploadDirectory;
        } else {
            uploadFolderPath = uploadDirectory;
        }

        registry.addResourceHandler("/upload/**")
                .addResourceLocations("file:" + uploadFolderPath + "/");
    }
}
