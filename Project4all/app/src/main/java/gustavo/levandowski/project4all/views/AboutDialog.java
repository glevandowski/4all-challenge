package gustavo.levandowski.project4all.views;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import gustavo.levandowski.project4all.R;

/***
 * Criando customDialog
 * @author Gustavo Levandowski
 */
public class AboutDialog extends Dialog{

    //region Propriedades
    private Activity activityParameter;
    private Button buttonClose;
    //endregion

    //region Builder/LifeCycle

    public AboutDialog(Activity yourContext) {
        super(yourContext);
        this.activityParameter = yourContext;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_about);
        setCancelable(false);
        this.findViews();
        this.actionEvents();
    }
    //endregion

    //region Referências/Eventos

    private void findViews(){
        buttonClose = (Button) findViewById(R.id.button_close_about_dialog);
    }

    private void actionEvents(){
        buttonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
    //endregion
}
