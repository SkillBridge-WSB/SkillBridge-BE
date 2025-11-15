package pl.wsb.merito.skillbridge.config.web.parameter;


import java.lang.annotation.*;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CurrentUserRole {
}