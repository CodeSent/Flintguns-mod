
package net.codesent.flintguns;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// This makes the annotation available at runtime
@Retention(RetentionPolicy.RUNTIME) 
// This restricts the annotation so it can only be placed on classes
@Target(ElementType.TYPE) 
public @interface autoRegister {
    String value(); // This will hold the registry name (e.g., "flintlock_pistol")
}