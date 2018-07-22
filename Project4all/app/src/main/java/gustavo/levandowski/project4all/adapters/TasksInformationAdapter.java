package gustavo.levandowski.project4all.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;
import gustavo.levandowski.project4all.R;
import gustavo.levandowski.project4all.adapters.holder.TaskHolder;
import gustavo.levandowski.project4all.listeners.PositionAdapterTasksListener;

public class TasksInformationAdapter extends RecyclerView.Adapter<TaskHolder>{

    //region Builders/Properties
    private PositionAdapterTasksListener loadListener;
    private List<String> acessData;
    private Context context;

    public TasksInformationAdapter(List<String> acessData, Context context, PositionAdapterTasksListener listener) {
        this.acessData = acessData;
        this.context = context;
        this.loadListener = listener;
    }
    //endregion

    //region Inflate Card |elements managed by the holder | Count ArrayList
    @NonNull
    @Override
    public TaskHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_body_list_tasks_fragment, parent, false);
        TaskHolder taskHolder = new TaskHolder(v);
        return taskHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull TaskHolder holder, final int position) {
        holder.textIdListTasks.setText(acessData.get(position));
        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             loadListener.onLoadFragment(position);
            }});
    }

    @Override
    public int getItemCount() {
        return acessData.size();
    }
    //endregion
}
