#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.configuration.openapi;

import com.fasterxml.classmate.TypeResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Tag;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.spi.DocumentationType.SWAGGER_2;

@Configuration
@EnableSwagger2
public class SpringFoxConfig implements WebMvcConfigurer {

    private static final Tag[] TAGS = { new Tag("Application", "Application") };

    @Bean
    public Docket apiDocket() {
        TypeResolver typeResolver = new TypeResolver();

        return new Docket(SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("${package}.entrypoints"))
                .build()
                .apiInfo(apiInfo())
                .tags(TAGS[0], TAGS);
    }

    public ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Study ${package}.configuration.Application")
                .description("Api to practice Spring Rest")
                .version("1")
                .contact(new Contact("StudyApplication", "https://", "company-email@gmail.com"))
                .build();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String locationResourcesSwagger = "classpath:/META-INF/resources/";

        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations(locationResourcesSwagger);
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations(locationResourcesSwagger.join("", "webjars/"));
    }
}
