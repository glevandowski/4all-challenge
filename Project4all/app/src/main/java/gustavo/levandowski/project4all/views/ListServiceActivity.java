package gustavo.levandowski.project4all.views;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import gustavo.levandowski.project4all.R;
import gustavo.levandowski.project4all.model.ListTasks;
import gustavo.levandowski.project4all.services.UserService;
import gustavo.levandowski.project4all.util.AppDialogs;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/***
 * @author Gustavo Levandowski
 */
public class ListServiceActivity extends AppCompatActivity {

    //region Propriedades

    private UserService taskService;
    private TextView textApi;
    private Button buttonOpenBrowser;
    private AppDialogs appDialogs;
    //endregion

    //region LifeCycle
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_service);
        this.findViews();
        this.getServiceData();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    //gerenciando onbackpressed
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    //endregion

    //region Referências

    private void findViews(){
        buttonOpenBrowser = findViewById(R.id.button_open_url_service_activity);
        textApi = findViewById(R.id.text_api_service_activity);
        buttonOpenBrowser.setEnabled(false);
        appDialogs = new AppDialogs(this);
    }
    //endregion

    //region Eventos e Intent para URL Externa
    private void setActionEvents(){
      buttonOpenBrowser.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              String url = UserService.BASE_URL+"/tarefa";
              Intent i = new Intent(Intent.ACTION_VIEW);
              i.setData(Uri.parse(url));
              startActivity(i);
          }
      });

    }
    //endregion

    //region Buscando tarefas na API a atualizando elementos da view
    private void getServiceData(){
        appDialogs.showProgress("Buscando dados...");
        createData();
        Call<ListTasks> callData = taskService.getAllTasks();
        callData.enqueue(new Callback<ListTasks>() {

            @Override
            public void onResponse(Call<ListTasks> call, Response<ListTasks> response) {
               appDialogs.closeProgress();
                if(!response.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"Algum erro ocorreu! verifique sua conexão à internet",Toast.LENGTH_LONG).show();
                } else {
                     textApi.setText("{lista:"+response.body().getLista().toString()+"}");
                     buttonOpenBrowser.setEnabled(true);
                     setActionEvents();
                }
            }
            @Override
            public void onFailure(Call<ListTasks> call, Throwable t) {
                appDialogs.closeProgress();
                call.cancel();
                Toast.makeText(getApplicationContext(),"Problema ao acessar o servidor. código: "+t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }

    private void createData(){
        Gson gsonGoogle = new GsonBuilder().create();
        Retrofit retrofitData = new Retrofit.Builder().baseUrl(UserService.BASE_URL).addConverterFactory(GsonConverterFactory.create(gsonGoogle)).build();
        taskService = retrofitData.create(UserService.class);
    }
    //endregion
}
