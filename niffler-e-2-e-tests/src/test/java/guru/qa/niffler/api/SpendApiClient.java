package guru.qa.niffler.api;

import guru.qa.niffler.config.Config;
import guru.qa.niffler.model.CategoryJson;
import guru.qa.niffler.model.SpendJson;
import okhttp3.OkHttpClient;
import org.junit.jupiter.api.Assertions;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;
import java.util.List;

public class SpendApiClient {

  private static final Config CFG = Config.getInstance();

  private final OkHttpClient client = new OkHttpClient.Builder().build();
  private final Retrofit retrofit = new Retrofit.Builder()
      .baseUrl(CFG.spendUrl())
      .client(client)
      .addConverterFactory(JacksonConverterFactory.create())
      .build();

  private final SpendApi spendApi = retrofit.create(SpendApi.class);

  public SpendJson addSpend(SpendJson spend) {
    try{
      final Response<SpendJson> response;
      response = spendApi.addSpend(spend)
              .execute();
      Assertions.assertEquals(201, response.code(),"Response code is failed");
      return response.body();
    } catch (IOException e) {
      throw new AssertionError(e);
    }

  }

  public SpendJson editSpend(SpendJson spend) {
    try {
      Response<SpendJson> response = spendApi.editSpend(spend).execute();
      Assertions.assertEquals(201, response.code(),"Response code is failed");
      return response.body();
    } catch (IOException e) {
      throw new AssertionError(e);
    }
  }

  public SpendJson getSpend(String id, String username) {
    try {
      Response<SpendJson> response = spendApi.getSpend(id, username).execute();
      Assertions.assertEquals(201, response.code(),"Response code is failed");
      return response.body();
    } catch (IOException e) {
      throw new AssertionError(e);
    }
  }

  public List<SpendJson> getSpends(String username, String filterCurrency, String dateFrom, String dateTo) {
    try {
      Response<List<SpendJson>> response = spendApi.getSpends(username, filterCurrency, dateFrom, dateTo).execute();
      Assertions.assertEquals(201, response.code(),"Response code is failed");
      return response.body();
    } catch (IOException e) {
      throw new AssertionError(e);
    }
  }

  public void deleteSpends(String username, List<String> ids) {
    try {
      Response<Void> response = spendApi.deleteSpends(username, ids).execute();
      Assertions.assertEquals(201, response.code(),"Response code is failed");
    } catch (IOException e) {
      throw new AssertionError(e);
    }
  }

  public List<CategoryJson> getCategories(String username, boolean excludeArchived) {
    try {
      Response<List<CategoryJson>> response = spendApi.getCategories(username, excludeArchived).execute();
      Assertions.assertEquals(201, response.code(),"Response code is failed");
      return response.body();
    } catch (IOException e) {
      throw new AssertionError(e);
    }
  }

  public CategoryJson addCategory(CategoryJson category) {
    try {
      Response<CategoryJson> response = spendApi.addCategory(category).execute();
      Assertions.assertEquals(200, response.code(),"Response code is failed");
      return response.body();
    } catch (IOException e) {
      throw new AssertionError(e);
    }
  }

  public CategoryJson updateCategory(CategoryJson category) {
    try {
      Response<CategoryJson> response = spendApi.updateCategory(category).execute();
      Assertions.assertEquals(200, response.code(),"Response code is failed");
      return response.body();
    } catch (IOException e) {
      throw new AssertionError(e);
    }
  }
}
