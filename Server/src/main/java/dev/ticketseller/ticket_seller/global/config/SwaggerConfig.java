package dev.ticketseller.ticket_seller.global.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        // 1. JWT 인증 방식을 설정합니다. "bearerAuth"는 임의의 이름입니다.
        SecurityScheme securityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")
                .in(SecurityScheme.In.HEADER).name("Authorization");

        // 2. API 요청에 인증 정보를 포함하도록 설정합니다.
        SecurityRequirement securityRequirement = new SecurityRequirement().addList("bearerAuth");

        return new OpenAPI()
                // 3. Components 객체에 위에서 정의한 SecurityScheme을 추가합니다.
                .components(new Components().addSecuritySchemes("bearerAuth", securityScheme))
                // 4. API 명세에 SecurityRequirement를 추가하여 자물쇠 아이콘을 표시합니다.
                .addSecurityItem(securityRequirement)
                .info(apiInfo());
    }

    private Info apiInfo() {
        return new Info()
                .title("Ticket Seller API")
                .description("티켓 판매 시스템 REST API")
                .version("1.0.0");
    }
}
