package com.tokkaiiii.account.securityservice.config

import com.tokkaiiii.account.securityservice.util.PREFIX
import com.tokkaiiii.account.securityservice.util.SUFFIX
import com.tokkaiiii.account.securityservice.util.TEXT_HTML
import com.tokkaiiii.account.securityservice.util.UTF_8
import org.springframework.boot.web.servlet.view.MustacheViewResolver
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebMvcConfig : WebMvcConfigurer{

    override fun configureViewResolvers(registry: ViewResolverRegistry) {
        val resolver = MustacheViewResolver()

        resolver.setCharset(UTF_8)
        resolver.setContentType(TEXT_HTML)
        resolver.setPrefix(PREFIX)
        resolver.setSuffix(SUFFIX)
        registry.viewResolver(resolver)
    }
}

