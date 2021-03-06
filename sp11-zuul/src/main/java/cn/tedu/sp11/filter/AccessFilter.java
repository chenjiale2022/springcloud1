package cn.tedu.sp11.filter;

import javax.servlet.http.HttpServletRequest;

import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import cn.tedu.web.util.JsonResult;

/**
 * zuul请求过滤器
 * 没有token不允许访问
 *
 * http://localhost:3001/item-service/35?token=1234
 * */
@Component
public class AccessFilter extends ZuulFilter{
    @Override
    public boolean shouldFilter() {
        //判断该过滤器是否需要被执行
        //对指定的serviceid过滤，如果要过滤所有服务，直接返回 true

        //这里是对item-service进行过滤
        RequestContext ctx = RequestContext.getCurrentContext();
        String serviceId = (String) ctx.get(FilterConstants.SERVICE_ID_KEY);
        if(serviceId.equals("item-service")) {
            return true;
        }
        return false;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest req = ctx.getRequest();
        String token = req.getParameter("token");
        if (token == null) {
            //此设置会阻止请求被路由到后台微服务
            ctx.setSendZuulResponse(false);
            //向客户端的响应
            ctx.setResponseStatusCode(200);
            ctx.setResponseBody(JsonResult.err().code(JsonResult.NOT_LOGIN).toString());
        }
        //zuul过滤器返回的数据设计为以后扩展使用，
        //目前该返回值没有被使用
        return null;
    }

    @Override
    public String filterType() {
        //public static final java.lang.String PRE_TYPE = "pre";
        //代表过滤器在请求被路由之前进行
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        //public static final int PRE_DECORATION_FILTER_ORDER = 5;
        //该过滤器顺序要 > 5，才能得到 serviceid
        return FilterConstants.PRE_DECORATION_FILTER_ORDER+1;
    }
}
