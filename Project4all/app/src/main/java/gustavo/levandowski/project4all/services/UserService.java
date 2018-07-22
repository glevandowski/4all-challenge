package gustavo.levandowski.project4all.services;

import retrofit2.Call;
import retrofit2.http.GET;
import gustavo.levandowski.project4all.model.*;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UserService {
    String BASE_URL = "http://dev.4all.com:3003";

    @GET("/tarefa")
    Call<ListTasks> getAllTasks();

    @GET("/tarefa/{id}")
    Call<User> getFindTasks(@Path("id") String information);
}
