package org.team10424102.blackserver.notifications;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface HandleNotificationType {
    int[] value();
}
