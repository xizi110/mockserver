package com.github.mockserver;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author xizi
 * @date 2024/2/1 16:40
 * @description
 */
@SpringBootApplication
@RestController
public class MockServer implements CommandLineRunner {
    @Value("${interfaces.location}")
    private String location;
    private final Yaml yaml = new Yaml();
    @Autowired
    private RequestMappingHandlerMapping requestMappingHandlerMapping;
    private final List<Interface> interfaces = new ArrayList<>();

    public static void main(String[] args) {
        SpringApplication.run(MockServer.class, args);
    }

    private void loadInterfaces() throws FileNotFoundException, NoSuchMethodException {
        List<File> files = loadInterfaceYaml();
        for (File file : files) {
            Interface api = yaml.loadAs(new FileInputStream(file), Interface.class);
            interfaces.add(api);
            registerApi(api);
        }
    }

    private List<File> loadInterfaceYaml() throws FileNotFoundException {
        URL url = ResourceUtils.getURL(location);
        File file = new File(url.getFile());
        if (!file.exists()) {
            throw new FileNotFoundException("file not found：" + file);
        }
        // 单文件
        if (file.isFile() && file.getName().endsWith(".yml")) {
            return List.of(file);
        } else if (file.isDirectory()) {
            // 文件夹
            return Arrays.stream(Objects.requireNonNull(file.listFiles()))
                    .filter(f -> f.isFile() && f.getName().endsWith(".yml"))
                    .collect(Collectors.toList());
        }
        // 未知文件
        return List.of();
    }

    private void registerApi(Interface api) throws NoSuchMethodException {
        RequestMappingInfo requestMappingInfo = builderRequestMappingInfo(api);
        ResponseBodyHandler handler = response -> {
            List<Header> responseHeaders = api.getResponse().getHeaders();
            if (responseHeaders != null && !responseHeaders.isEmpty()) {
                responseHeaders.forEach(header -> response.addHeader(header.getName(), header.getValue()));
            }
            try {
                response.getOutputStream().print(api.getResponse().getContent());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        };
        requestMappingHandlerMapping.registerMapping(requestMappingInfo, handler, ResponseBodyHandler.class.getDeclaredMethod("handle", HttpServletResponse.class));
    }

    private static RequestMappingInfo builderRequestMappingInfo(Interface api) {
        String method = api.getMethod();
        RequestMappingInfo.Builder requestMappingInfoBuilder = RequestMappingInfo.paths(api.getUrl())
                .params(parseParams(api))
                .headers(parseHeaders(api));
        if (method != null && !method.isEmpty()) {
            requestMappingInfoBuilder.methods(RequestMethod.valueOf(method.toUpperCase(Locale.ROOT)));
        }
        return requestMappingInfoBuilder.build();
    }

    private static String[] parseHeaders(Interface api) {
        String[] headers = new String[0];
        if (api.getRequest() == null) {
            return headers;
        }
        List<String> headerList = api.getRequest().getHeaders();
        if (headerList != null && !headerList.isEmpty()) {
            headers = headerList.toArray(String[]::new);
        }
        return headers;
    }

    private static String[] parseParams(Interface api) {
        String[] params = new String[0];
        if (api.getRequest() == null) {
            return params;
        }
        List<String> parameters = api.getRequest().getParameters();
        if (parameters != null && !parameters.isEmpty()) {
            params = parameters.toArray(String[]::new);
        }
        return params;
    }

    @Override
    public void run(String... args) throws Exception {
        loadInterfaces();
    }

    @GetMapping("/interfaces")
    public List<Interface> interfaces() {
        return interfaces;
    }
}
