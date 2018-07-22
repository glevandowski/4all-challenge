package gustavo.levandowski.project4all.views;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;
import gustavo.levandowski.project4all.R;
import gustavo.levandowski.project4all.adapters.UserInformationAdapter;
import gustavo.levandowski.project4all.listeners.ActionEventsDialogListener;
import gustavo.levandowski.project4all.listeners.PositionAdapterCommentsListener;
import gustavo.levandowski.project4all.model.ListComments;
import gustavo.levandowski.project4all.model.User;
import gustavo.levandowski.project4all.util.AppDialogs;
import gustavo.levandowski.project4all.util.CheckConnection;
import gustavo.levandowski.project4all.util.RoundedCornersImageTransform;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import gustavo.levandowski.project4all.services.UserService;

/***
 * @author Gustavo Levandowski
 */
public class InfoUserActivity extends AppCompatActivity implements PositionAdapterCommentsListener,ActionEventsDialogListener {
    //region Properties
    private static String FLAG_ISMAPS = "mapa";
    private static String FLAG_ISNUMBER = "telefone";

    private String taskAtual;
    private TextView idUser;
    private TextView textBody;
    private TextView titleUser;
    private ImageView imageHead;
    private Button buttonCall;
    private Button buttonMaps;
    private Button buttonFullComments;
    private Button buttonServices;
    private ImageButton imageButtonLogo;

    private User userModel;
    private Gson gsonConvert = new Gson();
    private UserService userService;
    private RecyclerView reciclerViewComments;
    private AppDialogs appDialogs;

    private UserInformationAdapter adapterUser;
    private List<User> listUser = new ArrayList<>();
    private List<ListComments> listCommentsModel = new ArrayList<>();
    private CheckConnection checkConnection =new CheckConnection(this);
    //endregion

    //region LifeCycle

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_user);
        this.receivingBundle();
        this.initComponents();
        this.findViews();
        this.getUserData();
        this.setupAdapter();
        this.setupRecyclerViewLayout();
        this.actionEvents();
    }

    @Override
    protected void onResume() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        super.onResume();
    }
    //endregion

    //region Recebendo dados por Bundle e inicializando instâncias

    private void receivingBundle(){
        taskAtual  = getIntent().getExtras().getString("myjson","");
    }

    private void initComponents(){
        userModel = new User();
        appDialogs = new AppDialogs(this);
    }
    //endregion

    //region Referências e eventos
    private void findViews(){
        idUser = findViewById(R.id.id_text_info_user_activity);
        buttonCall = findViewById(R.id.button_call_info_user_activity);
        textBody = findViewById(R.id.text_info_user_activity);
        titleUser = findViewById(R.id.title_info_user_activity);
        reciclerViewComments = findViewById(R.id.recicler_view_comments_activity_info_user);
        imageHead = findViewById(R.id.image_head_info_user_activity);
        buttonMaps = findViewById(R.id.button_location_info_user_activity);
        imageButtonLogo = findViewById(R.id.button_favorite_info_user_activity);
        buttonFullComments = findViewById(R.id.button_comments_info_user_activity);
        buttonServices = findViewById(R.id.button_service_info_user_activity);
    }

    private void actionEvents(){

        buttonCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isConnect()==true) {
                    openIntentExternal(true, FLAG_ISNUMBER, null, userModel.getTelefone());
                }
            }
        });

        //Abre dialogs para verificar o mapa a ser inicializado, a mesma é gerenciada nesta classe
        buttonMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isConnect()==true) {
                    openDialogAskMaps();
                }
            }
        });

        //Tela completa de  detalhes de comentários
        buttonFullComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isConnect()==true) {
                    //Converting ArrayList Objects to send to another class
                    String jsonString = gsonConvert.toJson(listCommentsModel);
                    openActivity(ListCommentsActivity.class, true, null, jsonString);
                }
            }
        });

        //Abre tela de serviços
        buttonServices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isConnect()==true) {
                    openActivity(ListServiceActivity.class, true, null, "");
                }
            }
        });
    }
    //endregion

    //region Setup Views - Lógicas e  instâncias de configuração de view
    private void setupAdapter() {
        adapterUser = new UserInformationAdapter(listUser,this.getApplicationContext(),this);
        reciclerViewComments.setAdapter(adapterUser);
    }

    private void setupRecyclerViewLayout() {
        reciclerViewComments.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        reciclerViewComments.setLayoutManager(layoutManager);
    }

    private void setupAnimation() {
        int resId = R.anim.layout_anim_fall_down;
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(this, resId);
        reciclerViewComments.setLayoutAnimation(animation);
    }

    //Atualiza os dados da view, só é chamado após o onResponse do retrofit
    private void updateViews(){
        idUser.setText(taskAtual);
        textBody.setText(userModel.getTexto());
        titleUser.setText(userModel.getTitulo());
        changeImageFormat();
    }

    private void changeImageFormat(){
        Picasso.get().load(userModel.getUrlFoto()).fit().centerCrop().into(imageHead);

        Picasso.get().load(userModel.getUrlLogo())
                .resize(200, 200).centerCrop().
                transform( new RoundedCornersImageTransform()).into(imageButtonLogo);
    }
    //endregion

    //region Recebendo a posição do adapter por listener, e criando o dialog.

    /***
     * Listener retornando a position do adapter
     */
    @Override
    public void onLoadFragment(int position) {
        if(isConnect()==true) {
            openDialogsPositionAdapter(position);
        }
    }
    //endregion

    //region Criando chamada na API e realizando de forma dinâmica.
    private void getUserData(){
        appDialogs.showProgress("Buscando usuário...");
        createData();
        Call<User> callData = userService.getFindTasks(taskAtual);
        callData.enqueue(new Callback<User>() {

            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                 appDialogs.closeProgress();
                if(!response.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"Algum erro ocorreu! verifique sua conexão à internet",Toast.LENGTH_LONG).show();
                } else {
                    userModel = response.body();

                    //percorrendo lista de objetos retornada pela API e adicionando a lista local com as respectivas models
                    for(int i = 0;i < userModel.getComentarios().size();i++) {
                        listUser.add(userModel);
                        listCommentsModel.add(userModel.getComentarios().get(i));
                    }
                    existComment();
                    adapterUser.notifyDataSetChanged();

                    updateViews();
                    setupAnimation();
                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                appDialogs.closeProgress();
                call.cancel();
                Toast.makeText(getApplicationContext(),"Problema ao acessar o servidor. código: "+t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }

    private void createData(){
        Gson gsonGoogle = new GsonBuilder().create();
        Retrofit retrofitData = new Retrofit.Builder().baseUrl(UserService.BASE_URL).addConverterFactory(GsonConverterFactory.create(gsonGoogle)).build();
        userService = retrofitData.create(UserService.class);
    }
    //endregion

    //region Método auxiliar
    private boolean isConnect(){
        if(!checkConnection.isOnline()){
            Toast.makeText(getApplicationContext(),"Verifique sua conexão à internet",Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private void existComment(){
        if(userModel.getComentarios().size()==0){
            reciclerViewComments.setVisibility(View.INVISIBLE);
            buttonFullComments.setEnabled(false);
            Toast.makeText(getApplicationContext(),"Não há comentários",Toast.LENGTH_LONG).show();
        }else{
            reciclerViewComments.setVisibility(View.VISIBLE);
            buttonFullComments.setEnabled(true);
        }
    }
    //endregion

    //region Listener para gerenciar os comandos de AskMapsDialog

    /***
     * Listener para controle de ações do dialog de localização,gerenciando informações de responsabilidade da view
     */
    @Override
    public void accessMaps() {
        openActivity(InfoAdressActivity.class,true,userModel,"");
    }

    @Override
    public void accessExternalMaps() {
        Uri mapUri = Uri.parse("geo:0,0?q="+userModel.getLatitude()+","+userModel.getLongitude()+"("+userModel.getId_user()+")");
        openIntentExternal(true,FLAG_ISMAPS,mapUri,"");
    }
    //endregion

    //region Gerenciando toda a lógica de Intents/dialogs com o controle de backstack

    private void openActivity(Class<?> calledActivity, boolean clearBackStack,User userModel,String myJson) {
        Intent myIntent = new Intent(this, calledActivity);
        if(userModel != null) {
            Bundle bundle = new Bundle();
            bundle.putString("informationData", new Gson().toJson(userModel));
            myIntent.putExtra("informationData", bundle);
        }
        if(!myJson.equals(null)){
            myIntent.putExtra("KEY",myJson);
        }
        if (clearBackStack) {
            myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        this.startActivity(myIntent);
    }

    /***
     * Recebendo parâmetros,executando lógica e realizando intent externo.
     */
    private void openIntentExternal(boolean clearBackStack,String nameCall,Uri mapUri,String numberPhone){
        Intent myIntent = null;
        switch (nameCall){
            case "mapa":
                if(!numberPhone.equals(""))return;

                myIntent = new Intent(Intent.ACTION_VIEW, mapUri);
                myIntent.setPackage("com.google.android.apps.maps");
                break;
            case "telefone":
                if(mapUri != null)return;

                myIntent = new Intent(Intent.ACTION_DIAL);
                myIntent.setData(Uri.parse("tel:"+numberPhone));
                break;
        }
       if(myIntent == null)return;
        if (clearBackStack) {
            myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        }
       startActivity(myIntent);
    }

    private void openDialogAskMaps (){
        AskMapsDialog customDialog = new AskMapsDialog(InfoUserActivity.this,InfoUserActivity.this);
        customDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        customDialog.show();
    }

    private  void openDialogsPositionAdapter(int position){
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();

        alertDialog.setIcon(R.drawable.icon_marker);
        alertDialog.setTitle("Comentário de \n"+userModel.getComentarios().get(position).getNome()+" :");

        alertDialog.setMessage(userModel.getComentarios().get(position).getComentario());

        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Fechar",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }
    //endregion


}
