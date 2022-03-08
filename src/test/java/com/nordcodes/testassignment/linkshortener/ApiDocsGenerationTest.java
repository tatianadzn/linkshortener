package com.nordcodes.testassignment.linkshortener;

import io.github.swagger2markup.Swagger2MarkupConfig;
import io.github.swagger2markup.Swagger2MarkupConverter;
import io.github.swagger2markup.builder.Swagger2MarkupConfigBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ApiDocsGenerationTest {

    private static final String remoteSwaggerFilePath = "http://localhost:8080/v2/api-docs";
    private static final String propertiesFilePath = "src/test/resources/swagger2markup.properties";
    private static final Path outputDirectory = Paths.get("docs/api");
    private static final Path securityFile = Paths.get("docs/api/security.md");
    private static final Path overviewFile = Paths.get("docs/api/overview.md");

    @Test
    public void convertRemoteSwaggerToMarkup() throws IOException {
        final Properties properties = getPropertiesFromFile(propertiesFilePath);
        final Swagger2MarkupConfig config = new Swagger2MarkupConfigBuilder(properties)
                .build();

        Swagger2MarkupConverter.from(new URL(remoteSwaggerFilePath))
                .withConfig(config)
                .build()
                .toFolder(outputDirectory);

        Files.deleteIfExists(securityFile);
        Files.deleteIfExists(overviewFile);
    }

    private Properties getPropertiesFromFile(final String propertiesFilePath) throws IOException {
        final Properties properties = new Properties();
        try (InputStream input = new FileInputStream(propertiesFilePath)) {
            properties.load(input);
        }
        return properties;
    }
}
