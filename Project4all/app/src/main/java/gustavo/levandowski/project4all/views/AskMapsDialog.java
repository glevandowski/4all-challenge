package gustavo.levandowski.project4all.views;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import gustavo.levandowski.project4all.R;
import gustavo.levandowski.project4all.listeners.ActionEventsDialogListener;

/***
 * @author Gustavo Levandowski
 */
public class AskMapsDialog extends Dialog {

    //region PROPRIEDADES
    private Activity activityParameter;
    private Button buttonExternalMaps;
    private Button buttonAcessMaps;
    private ActionEventsDialogListener listener;
    //endregion

    //region Builder / LifeCycle
    public AskMapsDialog(Activity yourContext,ActionEventsDialogListener listener) {
        super(yourContext);
        this.activityParameter = yourContext;
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_ask_maps);
        this.findViews();
        this.actionEvents();
    }
    //endregion

    //region Referências/ Eventos obs: recebe listener, sendo gerenciado pela view InfoUserActivity
    private void findViews(){
        buttonExternalMaps = (Button) findViewById(R.id.button_gmaps_external_ask_dialog);
        buttonAcessMaps = (Button) findViewById(R.id.button_acess_maps_ask_dialog);
    }

    private void actionEvents(){
        buttonAcessMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.accessMaps();
                dismiss();
            }
        });

        buttonExternalMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            listener.accessExternalMaps();
            dismiss();
            }
        });
    }
    //endregion

}