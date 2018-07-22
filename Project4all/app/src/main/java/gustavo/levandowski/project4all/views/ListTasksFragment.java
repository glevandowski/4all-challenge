package gustavo.levandowski.project4all.views;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.ArrayList;
import java.util.List;
import gustavo.levandowski.project4all.R;
import gustavo.levandowski.project4all.adapters.TasksInformationAdapter;
import gustavo.levandowski.project4all.listeners.PositionAdapterTasksListener;
import gustavo.levandowski.project4all.model.ListTasks;
import gustavo.levandowski.project4all.services.UserService;
import gustavo.levandowski.project4all.util.AppDialogs;
import gustavo.levandowski.project4all.util.CheckConnection;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/***
 * @author Gustavo Levandowski
 */
public class ListTasksFragment extends Fragment implements PositionAdapterTasksListener {

    //region Propriedades
    private TextView textAlert;
    private AppDialogs appDialogs;
    private CheckConnection checkConnection;
    private RecyclerView reciclerView;
    private TasksInformationAdapter adapterUser;
    private UserService service;
    private List<String> tasksListAdapter = new ArrayList<>();
    private List<String> retrieveList = new ArrayList<>();
    //endregion

    //region LifeCycle
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_tasks, container, false);
         this.findViews(view);
         this.instantiateObject();
         this.setupAdapter();
         this.setupRecyclerViewLayout();
         this.getUserData();

         return view;
    }
    //endregion

    //region Referências

    private void findViews(View view){
       reciclerView = view.findViewById(R.id.recycler_view_list_tasks_activity);
       textAlert = view.findViewById(R.id.text_alert_tasks_fragment);
    }
    private void instantiateObject(){
        appDialogs = new AppDialogs(getActivity());
        checkConnection = new CheckConnection(getActivity());
    }
    //endregion

    //region Configurando Adapter/ReciclerView e animação
    private void setupAdapter() {
        adapterUser = new TasksInformationAdapter(tasksListAdapter,getContext().getApplicationContext(),this);
        reciclerView.setAdapter(adapterUser);
    }

    private void setupRecyclerViewLayout() {
        reciclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        reciclerView.setLayoutManager(layoutManager);
    }

    private void setupAnimation() {
        int resId = R.anim.layout_anim_fall_down;
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getActivity(), resId);
        reciclerView.setLayoutAnimation(animation);
    }
    //endregion

    //region Abrindo Acitivity na posição das tarefas.
    @Override
    public void onLoadFragment(int position) {
        if(!checkConnection.isOnline()){
            Toast.makeText(getActivity(),"Verifique sua conexão à internet",Toast.LENGTH_LONG).show();
            return;
        }
        ((MainActivity) getActivity()).openActivity(InfoUserActivity.class,true,tasksListAdapter.get(position));
    }
    //endregion

    //region Solicitando tarefas iniciais na API, lembrando que todas solicitações são dinâmicas, independente do retorno.
    public void getUserData(){
        appDialogs.showProgress("Buscando os dados...");
        createData();
        Call<ListTasks> call = service.getAllTasks();
        call.enqueue(new Callback<ListTasks>() {

            @Override
            public void onResponse(Call<ListTasks> call, Response<ListTasks> response) {
                appDialogs.closeProgress();
                if(!response.isSuccessful()){
                    textAlert.setVisibility(View.VISIBLE);
                    textAlert.setText("Verifique sua conexão");
                    Toast.makeText(getActivity(),"Algum erro ocorreu! verifique sua conexão à internet",Toast.LENGTH_LONG).show();
                } else {

                    //for responsável por buscar a sublista retornada pela api de forma dinâmica.
                    for (int i=0;i<=response.body().getLista().size();i++) {
                         retrieveList = response.body().getLista().subList(0, i);
                    }

                    //for responsável por adicionar os dados ao reciclerview
                    for(int list = 0; list< retrieveList.size(); list++) {
                        tasksListAdapter.add(retrieveList.get(list));
                    }
                    adapterUser.notifyDataSetChanged();
                    setupAnimation();
                }
            }
            @Override
            public void onFailure(Call<ListTasks> call, Throwable t) {
            appDialogs.closeProgress();
            textAlert.setVisibility(View.VISIBLE);
            textAlert.setText("Verifique sua conexão");
            call.cancel();
            Toast.makeText(getActivity(),"Problema ao acessar o servidor. código: "+t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }

    private void createData(){
        Gson g = new GsonBuilder().create();
        Retrofit retro = new Retrofit.Builder().baseUrl(UserService.BASE_URL).addConverterFactory(GsonConverterFactory.create(g)).build();
        service = retro.create(UserService.class);
    }
    //endregion
}
