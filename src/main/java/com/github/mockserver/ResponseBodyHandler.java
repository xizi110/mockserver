package com.github.mockserver;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author xizi
 * @date 2024/2/1 15:09
 * @description
 */
public interface ResponseBodyHandler {

    @ResponseBody
    void handle(HttpServletResponse response);

}
