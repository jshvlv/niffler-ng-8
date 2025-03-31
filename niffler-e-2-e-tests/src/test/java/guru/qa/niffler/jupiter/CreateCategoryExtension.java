package guru.qa.niffler.jupiter;

import guru.qa.niffler.api.SpendApiClient;
import guru.qa.niffler.model.CategoryJson;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.platform.commons.support.AnnotationSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

public class CreateCategoryExtension implements BeforeEachCallback {

  public static final ExtensionContext.Namespace NAMESPACE = ExtensionContext.Namespace.create(CreateCategoryExtension.class);
    private static final Logger log = LoggerFactory.getLogger(CreateCategoryExtension.class);
    private final SpendApiClient spendApiClient = new SpendApiClient();

  @Override
  public void beforeEach(ExtensionContext context) {
      String categoryName = generateCategoryName();
    AnnotationSupport.findAnnotation(context.getRequiredTestMethod(), Category.class)
        .ifPresent(anno -> {
            CategoryJson categoryJson = new CategoryJson(
                    null, // кажется нужно генерить уид
                    categoryName,
                    anno.username(),
                    anno.archived()
            );
        CategoryJson created = spendApiClient.addCategory(categoryJson);
        log.debug("category created " + categoryName);
        if(anno.archived()){
            created = spendApiClient.updateCategory(categoryJson);
            log.debug("category updated " + categoryName);
        }
        context.getStore(NAMESPACE).put(context.getUniqueId(), created);
        });
  }

  private String generateCategoryName(){
      return "это категория № " + new Random().nextInt(100000);
  }

}
