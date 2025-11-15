package pl.wsb.merito.skillbridge.config.web.resolver;

import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import pl.wsb.merito.skillbridge.config.web.parameter.CurrentUserRole;
import pl.wsb.merito.skillbridge.domain.model.Role;
import pl.wsb.merito.skillbridge.domain.service.auth.UserPrincipal;

@Component
public class CurrentUserRoleArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(CurrentUserRole.class) && parameter.getParameterType().equals(Role.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth.getName() == null || (!(auth.getPrincipal() instanceof UserPrincipal)) ) {
            return null;
        }
        return ((UserPrincipal) auth.getPrincipal()).getAuthorities().stream().map(a -> Role.valueOf(a.getAuthority().substring(5))).findFirst().orElse(Role.STUDENT);
    }
}
