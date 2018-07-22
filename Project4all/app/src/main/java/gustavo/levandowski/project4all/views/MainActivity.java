package gustavo.levandowski.project4all.views;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import gustavo.levandowski.project4all.R;

/***
 * @author Gustavo Levandowski
 */
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //region Propriedades
    private DrawerLayout drawer;
    private Toolbar toolbar;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    //endregion

    //region LifeCycle
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.findViews();
        this.setupNavigation();
        this.openFragment(new ListTasksFragment(),true);
    }
    //endregion

    //region Lógica: navigationView/onBackPressed
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_home:
                openFragment(new ListTasksFragment(),false);
                break;
            case R.id.nav_about:
                openDialogAbout();
                break;
            case R.id.nav_exit:
                finish();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    //endregion

    //region Referências e configurando navegação
    private void findViews(){
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
    }

    private void setupNavigation(){
        setSupportActionBar(toolbar);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }
    //endregion

    //region FLUXO PRINCIPAL
    /***
     *Métodos para gerenciar o fluxo da aplicação,também adicionando em pilha. Obs: responsabilidade da main.
     */
    public void openFragment(Fragment calledFragment, boolean noAddToBackStack) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (noAddToBackStack) {
            fragmentManager.beginTransaction().replace(R.id.content_main_id, calledFragment).commit();

        }else {
            fragmentManager.beginTransaction().replace(R.id.content_main_id, calledFragment).addToBackStack(calledFragment.getClass().getName()).commit();
        }
    }

    public void openActivity(Class<?> calledActivity, boolean clearBackStack, String stringTransfer) {
        Intent myIntent = new Intent(MainActivity.this, calledActivity);
        myIntent.putExtra("myjson", stringTransfer);
        if (clearBackStack) {
            myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        this.startActivity(myIntent);
    }

    private void openDialogAbout(){
        AboutDialog customDialog = new AboutDialog(this);
        customDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        customDialog.show();
    }
    //endregion
}
