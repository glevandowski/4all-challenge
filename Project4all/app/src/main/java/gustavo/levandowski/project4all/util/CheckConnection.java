package gustavo.levandowski.project4all.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;

/***
 * @author Gustavo Levandowski
 * Verify Connection
 */
public class CheckConnection {
    Activity context;

    public CheckConnection(Activity context) {
        this.context = context;
    }

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected()) {
            return true;
        }
        else{
            return false;
        }
    }

    //region Primeira inicialização do app, verifica se existe conexão.
    public void dialogIfOffline() {
        new AlertDialog.Builder(context)
                .setTitle("Problema de conexão")
                .setMessage("Para acessar o app é preciso estar conectado, verifique sua conexão.")
                .setNegativeButton("Ajustar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        context.startActivity(new Intent(WifiManager.ACTION_PICK_WIFI_NETWORK));
                    }
                }).show();
    }

    public static boolean isConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        return info != null && info.isConnectedOrConnecting();
    }
    //endregion
}
