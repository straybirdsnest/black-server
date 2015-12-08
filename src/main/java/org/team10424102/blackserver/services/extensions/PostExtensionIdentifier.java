package org.team10424102.blackserver.services.extensions;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PostExtensionIdentifier {
    String value();
}
