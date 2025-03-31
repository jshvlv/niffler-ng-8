package guru.qa.niffler.jupiter;

import com.github.javafaker.Bool;
import guru.qa.niffler.model.CurrencyValues;
import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@ExtendWith({CreateCategoryExtension.class, CategoryResolverExtension.class})
public @interface Category {
  String username();
  boolean archived();
}
