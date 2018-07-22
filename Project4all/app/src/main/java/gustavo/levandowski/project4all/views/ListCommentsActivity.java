package gustavo.levandowski.project4all.views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import gustavo.levandowski.project4all.R;
import gustavo.levandowski.project4all.adapters.FullCommentsAdapter;
import gustavo.levandowski.project4all.model.ListComments;

/***
 * @author Gustavo Levandowski
 */
public class ListCommentsActivity extends AppCompatActivity{

    //region Propriedades/Builder
    private RecyclerView reciclerViewComments;
    private FullCommentsAdapter adapterComments;
    private List<ListComments> listComments = new ArrayList<>();

    public ListCommentsActivity() { }
    //endregion

    //region LifeCycle
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_comments);
        this.findViews();
        this.receivingBundle();
        this.setupAdapter();
        this.setupRecyclerViewLayout();
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

    //region Recebendo ArrayList de objetos por bundle.

    private void receivingBundle() {
        Bundle bundle = getIntent().getExtras();
        String jsonString = bundle.getString("KEY");

        Gson gson = new Gson();
        Type listOfdoctorType = new TypeToken<List<ListComments>>() {}.getType();
        listComments = gson.fromJson(jsonString,listOfdoctorType );

    }
    //endregion

    //region Configurações Adapter/ReciclerView e referências

    private void findViews(){
        reciclerViewComments = findViewById(R.id.recicler_full_comments_activity);
    }

    private void setupAdapter() {
        adapterComments = new FullCommentsAdapter(listComments,this);
        reciclerViewComments.setAdapter(adapterComments);
    }

    private void setupRecyclerViewLayout() {
        reciclerViewComments.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        reciclerViewComments.setLayoutManager(layoutManager);
    }
    //endregion
}
