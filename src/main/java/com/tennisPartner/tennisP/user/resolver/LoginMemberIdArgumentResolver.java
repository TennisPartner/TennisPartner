package com.tennisPartner.tennisP.user.resolver;

import com.tennisPartner.tennisP.user.jwt.JwtProvider;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.token.TokenService;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class LoginMemberIdArgumentResolver implements HandlerMethodArgumentResolver {

    private final JwtProvider jwtProvider;

    public LoginMemberIdArgumentResolver(final JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        return parameter.hasParameterAnnotation(LoginMemberId.class)
                && parameter.getParameterType().equals(Long.class);
    }

    @Override
    public Long resolveArgument(final MethodParameter parameter, final ModelAndViewContainer mavContainer
            , final NativeWebRequest webRequest, final WebDataBinderFactory binderFactory) throws Exception {
        String accessToken = webRequest.getHeader("Authorization").split("Bearer ")[1];
        return Long.parseLong(jwtProvider.getUserIdx(accessToken));
    }
}
