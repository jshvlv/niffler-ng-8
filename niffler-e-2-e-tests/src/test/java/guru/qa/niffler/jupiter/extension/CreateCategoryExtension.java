package guru.qa.niffler.jupiter.extension;

import guru.qa.niffler.api.SpendApiClient;
import guru.qa.niffler.jupiter.annotation.Category;
import guru.qa.niffler.model.CategoryJson;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.platform.commons.support.AnnotationSupport;

import java.util.Random;

public class CreateCategoryExtension implements BeforeEachCallback, AfterTestExecutionCallback {

    public static final ExtensionContext.Namespace NAMESPACE = ExtensionContext.Namespace.create(CreateCategoryExtension.class);
    private final SpendApiClient spendApiClient = new SpendApiClient();

    @Override
    public void beforeEach(ExtensionContext context) {
        String categoryName = generateCategoryName();
        AnnotationSupport.findAnnotation(context.getRequiredTestMethod(), Category.class)
                .ifPresent(anno -> {
                    CategoryJson categoryJson = new CategoryJson(
                            null,
                            categoryName,
                            anno.username(),
                            anno.archived()
                    );
                    CategoryJson created = spendApiClient.addCategory(categoryJson);
                    if (anno.archived()) {
                        CategoryJson archiveJson = new CategoryJson(
                                created.id(),
                                created.name(),
                                created.username(),
                                true);
                        created = spendApiClient.updateCategory(archiveJson);
                    }
                    context.getStore(NAMESPACE).put(context.getUniqueId(), created);
                });
    }

    private String generateCategoryName() {
        return "Категория № " + new Random().nextInt(10000000);
    }


    @Override
    public void afterTestExecution(ExtensionContext context) {
        CategoryJson categoryJson = context.getStore(CreateCategoryExtension.NAMESPACE).get(context.getUniqueId(), CategoryJson.class);
//        if(!categoryJson.archived()){ // на самом деле в тесте мы разархивировали категорию и ее нужно заархивировать
        spendApiClient.getCategories(categoryJson.username(), true).stream()
                .filter(c -> c.id().equals(categoryJson.id())).findFirst()
                .ifPresent(update -> {
                    CategoryJson archived = new CategoryJson(
                            update.id(),
                            update.name(),
                            update.username(),
                            true
                    );
                    spendApiClient.updateCategory(archived);
                });
    }
}
