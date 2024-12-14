package com.tokkaiiii.account.securityservice.config

import org.springframework.boot.web.servlet.view.MustacheViewResolver
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType.TEXT_HTML_VALUE
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import kotlin.text.Charsets.UTF_8

@Configuration
class WebMvcConfig : WebMvcConfigurer{

    override fun configureViewResolvers(registry: ViewResolverRegistry) {
        val resolver = MustacheViewResolver()
        resolver.setCharset(UTF_8.name())
        resolver.setContentType(TEXT_HTML_VALUE)
        resolver.setPrefix("classpath:/templates/")
        resolver.setSuffix(".html")
        registry.viewResolver(resolver)
    }
}