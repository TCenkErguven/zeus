package com.zeus.configuration

import org.springframework.boot.autoconfigure.http.HttpMessageConverters
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter


@Configuration
class HttpMessageConverterConfiguration {

    @Bean
    fun customConverters(): HttpMessageConverters {
        val converters: MutableList<HttpMessageConverter<*>?> = ArrayList<HttpMessageConverter<*>?>()
        converters.add(MappingJackson2HttpMessageConverter())
        return HttpMessageConverters(false, converters)
    }

}