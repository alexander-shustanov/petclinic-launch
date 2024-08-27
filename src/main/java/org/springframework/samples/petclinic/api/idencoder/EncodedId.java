package org.springframework.samples.petclinic.api.idencoder;

import org.springframework.core.annotation.AliasFor;
import org.springframework.web.bind.annotation.PathVariable;

import java.lang.annotation.*;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EncodedId {


	@AliasFor("name")
	String value() default "";


	@AliasFor("value")
	String name() default "";

	String encoder();

	boolean required() default true;
}
