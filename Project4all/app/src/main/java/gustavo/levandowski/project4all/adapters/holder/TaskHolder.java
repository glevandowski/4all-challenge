package gustavo.levandowski.project4all.adapters.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import gustavo.levandowski.project4all.R;

/**
 * Classe auxiliar responsável por gerenciar as referências do adapter
 */
public class TaskHolder extends RecyclerView.ViewHolder {
   public TextView textIdListTasks;
    public TaskHolder(View holder){
        super(holder);
        textIdListTasks = holder.findViewById(R.id.text_id_card_tasks_fragments);
    }
}
