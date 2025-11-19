package pl.wsb.merito.skillbridge.config.web;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import pl.wsb.merito.skillbridge.config.web.resolver.CurrentUserIdArgumentResolver;
import pl.wsb.merito.skillbridge.config.web.resolver.CurrentUserRoleArgumentResolver;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final CurrentUserIdArgumentResolver currentUserIdArgumentResolver;
    private final CurrentUserRoleArgumentResolver currentUserRoleArgumentResolver;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(currentUserIdArgumentResolver);
        resolvers.add(currentUserRoleArgumentResolver);
    }
}
