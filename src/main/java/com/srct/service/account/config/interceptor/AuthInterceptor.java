package com.srct.service.account.config.interceptor;

import com.srct.service.account.service.AuthTokenService;
import com.srct.service.account.service.IotAuthService;
import com.srct.service.config.annotation.Auth;
import com.srct.service.config.holder.ClientTypeHolder;
import com.srct.service.config.holder.TokenHolder;
import com.srct.service.config.security.AuthBaseInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * Title: AuthInterceptor
 * Description: Copyright: Copyright (c) 2019 Company: BHFAE
 *
 * @author Sharp
 * @date 2019/10/3 16:57
 * @description Project Name: Grote
 * @Package: com.srct.service.account.config.interceptor
 */
@Component
public class AuthInterceptor extends AuthBaseInterceptor {

    @Resource
    private AuthTokenService authTokenService;

    @Resource
    private IotAuthService iotAuthService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        authTokenService.enableCrossDomain(response);
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        Class clazz = handlerMethod.getBeanType();
        Auth.AuthType authType = Auth.AuthType.NONE;

        if (method.isAnnotationPresent(Auth.class)) {
            Auth auth = method.getAnnotation(Auth.class);
            authType = auth.role();
        } else if (clazz.isAnnotationPresent(Auth.class)) {
            Auth auth = (Auth) clazz.getDeclaredAnnotation(Auth.class);
            authType = auth.role();
        }
        if (authType.equals(Auth.AuthType.IOT)) {
            iotAuthService.validate(request, response);
        } else {
            authTokenService.validate(request, response, authType);
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {

        TokenHolder.remove();
        ClientTypeHolder.remove();
        super.postHandle(request, response, handler, modelAndView);
    }
        /*
        String holder = request.getHeader("accessToken");
        if (holder.equals("test")) {
            return true;
        }
        if (StringUtils.isEmpty(holder)) {
            throw new UserNotLoginException();
        }
        String uid = redisTokenOperateService.getUid(holder);
        if (StringUtils.isEmpty(uid)) {
            throw new AccessTokenExpiredException();
        }
        String serverToken = redisTokenOperateService.getAccessToken(Integer.valueOf(uid));
        if (StringUtils.isEmpty(serverToken)) {
            throw new AccessTokenExpiredException();
        }
        if (!holder.equals(serverToken)) {
            throw new UserNotLoginException();
        }
        String roleStr = redisTokenOperateService.getUserRole(Integer.valueOf(uid));
        if (StringUtils.isEmpty(roleStr)) {
            throw new UserNotLoginException();
        }
        Integer role = Integer.valueOf(roleStr);
        if (role == null || role < 0) {
            throw new UserNotLoginException();
        }
        Annotation annotation[] = method.getDeclaredAnnotations();
            Arrays.stream(annotation).filter(it -> it.annotationType().equals(TokenRole.class)).forEach(it -> {
                int[] roles = ((TokenRole) it).roles();
                Arrays.stream(roles).forEach(methodRole -> {
                    Log.i(this.getClass(), "roles = " + methodRole);
                    if (methodRole == role)
                        hasAuth = true;
                });
            });
        if (hasAuth) {
            hasAuth = false;
            return true;
        }
        throw new ServiceException();

    }*/

}
