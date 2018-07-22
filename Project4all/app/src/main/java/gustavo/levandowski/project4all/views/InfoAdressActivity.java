package gustavo.levandowski.project4all.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import gustavo.levandowski.project4all.R;
import gustavo.levandowski.project4all.model.User;

/***
 * @author Gustavo Levandowski
 */
public class InfoAdressActivity extends AppCompatActivity implements OnMapReadyCallback {

    //region Propriedades
    private User userModel;
    private TextView textBottomAdress;
    //endregion

    //region LifeCycle
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_adress);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.createMaps();
        this.receivingBundle();
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

    //region Recebendo parâmetros por bundle

    /***
     * Recebendo dados por bundle
     */
    private void receivingBundle() {
        textBottomAdress = findViewById(R.id.text_bottom_info_adress_activity);

        String receivingStringGson = getIntent().getBundleExtra("informationData").getString("informationData", "");
        userModel = new Gson().fromJson(receivingStringGson, User.class);
        textBottomAdress.setText(userModel.getEndereco().concat(","+userModel.getBairro().concat(". "+userModel.getCidade())));
    }
    //endregion

    //region Carregando e criando mapa

    /***
     * LoadingMaps
     */
    @Override
    public void onMapReady(GoogleMap map) {
        makersPosition(map);
    }

    /****
     * create Maps
     */
    private void createMaps() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync((OnMapReadyCallback) InfoAdressActivity.this);
    }
    //endregion

    //region Criando Marker e recebendo posições

    /***
     * Marker in position
     */
    private void makersPosition(GoogleMap gMaps) {

        gMaps.addMarker(new MarkerOptions()
                .position(new LatLng(Double.parseDouble(userModel.getLatitude()),
                        Double.parseDouble(userModel.getLongitude())))
                .title(userModel.getEndereco()).icon(BitmapDescriptorFactory.fromBitmap(getMarkerBitmapFromView(R.drawable.icon_marker))));

        //zoom in position
        gMaps.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.parseDouble(userModel.getLatitude()),
                Double.parseDouble(userModel.getLongitude())), 15));
    }

    /***
     *Inflando layout para utilizar de marker no mapa
     */
    private Bitmap getMarkerBitmapFromView(@DrawableRes int resId) {

        View customMarkerView = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.custom_marker_view, null);
        ImageView markerImageView = (ImageView) customMarkerView.findViewById(R.id.profile_image);

        markerImageView.setImageResource(resId);
        customMarkerView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        customMarkerView.layout(0, 0, customMarkerView.getMeasuredWidth(), customMarkerView.getMeasuredHeight());
        customMarkerView.buildDrawingCache();

        Bitmap returnedBitmap = Bitmap.createBitmap(customMarkerView.getMeasuredWidth(), customMarkerView.getMeasuredHeight(),
                Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(returnedBitmap);
        canvas.drawColor(Color.WHITE, PorterDuff.Mode.SRC_IN);
        Drawable drawable = customMarkerView.getBackground();
        if (drawable != null)
            drawable.draw(canvas);
        customMarkerView.draw(canvas);

        return returnedBitmap;
    }
    //endregion
}
