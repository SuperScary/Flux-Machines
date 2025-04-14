package net.superscary.fluxmachines.core.mixin;

import org.spongepowered.asm.mixin.injection.At;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CaptureOwner {
	String[] method () default {};

	At[] at ();

	int require () default 1;

	int expect () default 1;

	int allow () default -1;
}
