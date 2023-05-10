package cn.anselyuki.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * @author AnselYuki
 * @date 2022/10/16 10:52
 */
@SpringBootConfiguration
public class SpringDocConfiguration {
    @Bean
    public OpenAPI emergencyLogistics() {
        return new OpenAPI().info(docInfos()).externalDocs(new ExternalDocumentation().description("AnselYuki 的博客园").url("https://www.anselyuki.cn"));
    }

    private Info docInfos() {
        final String systemDescription = "本课题针对上述社会需求展开研究，在传统的物资调度基础上进行算法改进，对于应急物资的需求紧迫度以及配送路线规划展开研究，针对应急物资管理，拟开发一个集应急配送，路线规划，物资申请的多终端平台。";
        Info info = new Info();
        info.title("Emergency-logistics API");
        info.description(systemDescription);
        info.version("v1.0.0");
        info.license(new License().name("本项目采用 MIT License").url("https://gitee.com/anselyuki/emergency-logistics-back-end"));
        return info;
    }

    @Bean
    public GroupedOpenApi userApi() {
        return GroupedOpenApi.builder().group("system").pathsToMatch("/system/**").build();
    }

    @Bean
    public GroupedOpenApi businessApi() {
        return GroupedOpenApi.builder().group("business").pathsToMatch("/business/**").build();
    }

}