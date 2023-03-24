//package com.fly.config;
//
//import com.alibaba.fastjson2.JSONReader;
//import com.alibaba.fastjson2.JSONWriter;
//import com.alibaba.fastjson2.support.config.FastJsonConfig;
//import com.alibaba.fastjson2.support.spring.http.converter.FastJsonHttpMessageConverter;
//import com.fly.filter.DesensitizationFilter;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.MediaType;
//import org.springframework.http.converter.HttpMessageConverter;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//import java.nio.charset.StandardCharsets;
//import java.util.Collections;
//import java.util.List;
//
///**
// * WebMvcConfigurer是SpringMVC提供用于以Java Bean方式设置SpringMVC配置的扩展接口,
// * 该接口支持自定义Handler(处理器)，Interceptor(拦截器)，ViewResolver(视图解析器)，
// * MessageConverter(消息转换器)、Formatters(格式化器)、ReturnValueHandler(返回值处理器)、
// * ArgumentResolvers(参数解析器)、ViewController(视图控制器)、ResourceHandler(资源处理器)。
// *
// * @Description SpringMVC配置
// * @Author zchengfeng
// * @Date 2023/3/15 17:04
// */
//@Configuration
//public class WebMvcConfig implements WebMvcConfigurer {
//    /**
//     * 配置消息转换器
//     * @param converters 转换器列表,converters初始化为空。转换器需要实现HttpMessageConverter接口
//     */
//    @Override
//    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
//        // 创建fastjson Http消息转换器
//        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
//        FastJsonConfig config = new FastJsonConfig();
//        // 设置日期格式化
//        config.setDateFormat("yyyy-MM-dd HH:mm:ss");
//        config.setReaderFeatures(JSONReader.Feature.FieldBased, JSONReader.Feature.SupportArrayToBean);
//        config.setWriterFeatures(JSONWriter.Feature.WriteMapNullValue, JSONWriter.Feature.PrettyFormat);
//        // 设置写入过滤器
////        config.setWriterFilters(new DesensitizationFilter());
//        converter.setFastJsonConfig(config);
//        // 设置消息转换器字符编码
//        converter.setDefaultCharset(StandardCharsets.UTF_8);
//        // 设置消息转换器字符串媒体类型集合
//        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.APPLICATION_JSON));
//        // 向SpringMVC Http消息转换器列表添加fastjson解析器
//        converters.add(0, converter);
//    }
//}
