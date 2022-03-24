package cn.tedu.sp11.fallback;

import cn.tedu.web.util.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Slf4j
@Component
public class ItemServiceFallback implements FallbackProvider {

    /**
     * getRoute() 方法中指定应用此降级类的服务id，星号或null值可以通配所有服务
     * */
    @Override
    public String getRoute() {
        //当执行item-service失败，
        //应用当前降级类
        return "item-service";

        //星号和null都表示所有微服务失败都应用当前降级类
        //"*";//null;

    }

    //该方法返回封装降级响应的对象
    //ClientHttpResponse中封装降级响应
    @Override
    public ClientHttpResponse fallbackResponse(String route, Throwable cause) {
        return response();
    }


    private ClientHttpResponse response() {
        return new ClientHttpResponse() {
            //下面三个方法都是协议号
            @Override
            public HttpStatus getStatusCode() throws IOException {
                return HttpStatus.OK;
            }
            @Override
            public int getRawStatusCode() throws IOException {
                return HttpStatus.OK.value();
            }
            @Override
            public String getStatusText() throws IOException {
                return HttpStatus.OK.getReasonPhrase();
            }

            @Override
            public void close() {
            }

            /**
             * 设置响应体信息
             * */
            @Override
            public InputStream getBody() throws IOException {
                //log.info("fallback body");
                String s = JsonResult.err().msg("后台服务错误").toString();
                return new ByteArrayInputStream(s.getBytes("UTF-8"));
            }

            /**
             * 设置响应头信息
             * */
            @Override
            public HttpHeaders getHeaders() {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                return headers;
            }
        };
    }


}
