package cn.com.code.common.conf;

import io.swagger.annotations.ApiOperation;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/** 
* @ClassName: SwaggerConfig
* @Description: swagger配置
* @author: 55555
* @date: 2020年04月04日 10:47 下午
*/
@SpringBootConfiguration
@EnableSwagger2
@ConditionalOnClass(DocumentationType.class)
public class SwaggerConfig {



    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select()
                // 含有ApiOperation注解的方法加入接口文档
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any()).build();
        
    }

    /**
     * 描述
     * @return
     */
	private ApiInfo apiInfo() {
        Contact contact = new Contact("55555", "http://hanyukun.cn/", "2014058596@qq.com");
        return new ApiInfoBuilder()
                .title("55555 Doc")
                .description("接口文档说明")
                .contact(contact).version("1.0").build();
    }

}

