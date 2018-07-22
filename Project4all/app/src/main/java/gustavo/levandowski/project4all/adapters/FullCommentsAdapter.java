package gustavo.levandowski.project4all.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.squareup.picasso.Picasso;
import java.util.List;
import gustavo.levandowski.project4all.R;
import gustavo.levandowski.project4all.adapters.holder.FullCommentsHolder;
import gustavo.levandowski.project4all.model.ListComments;
import gustavo.levandowski.project4all.util.RoundedCornersImageTransform;

public class FullCommentsAdapter extends RecyclerView.Adapter<FullCommentsHolder> {

    //region Builders/Properties
    private List<ListComments> acessData;
    private Context context;

    public FullCommentsAdapter(List<ListComments> acessData, Context context) {
        this.acessData = acessData;
        this.context = context;
    }
    //endregion

    //region Inflate Card |elements managed by the holder | Count ArrayList
    @NonNull
    @Override
    public FullCommentsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_full_list_comments_activity, parent, false);
        FullCommentsHolder holderComments = new FullCommentsHolder(v);
        return holderComments;
    }


    @Override
    public void onBindViewHolder(@NonNull FullCommentsHolder holder, final int position) {
        this.setView(holder,position);
        this.setRatingBar(holder,position);
        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return acessData.size();
    }
    //endregion

    //region Update Values
    private void setView(FullCommentsHolder holder,int position){
        holder.userComments.setText(acessData.get(position).getNome());
        holder.textTitleComment.setText(acessData.get(position).getTitulo());
        holder.textRatingBar.setText(acessData.get(position).getNota());
        holder.textComment.setText(acessData.get(position).getComentario());

        Picasso.get().load(acessData.get(position).getUrlFoto())
                .resize(200, 200).centerCrop().
                transform( new RoundedCornersImageTransform()).into(holder.imageUserComment);
    }

    private void setRatingBar(FullCommentsHolder holder, int position){

        //Está configuração permite que a ratingBar receba números do tipo double.
        holder.ratingBar.setEnabled(false);
        holder.ratingBar.setMax(5);
        holder.ratingBar.setStepSize(0.01f);
        holder.ratingBar.setRating(Float.parseFloat(acessData.get(position).getNota()));
        holder.ratingBar.invalidate();
    }
    //endregion
}

