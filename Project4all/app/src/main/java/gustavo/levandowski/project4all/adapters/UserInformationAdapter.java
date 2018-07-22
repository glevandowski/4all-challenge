package gustavo.levandowski.project4all.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.List;
import gustavo.levandowski.project4all.R;
import gustavo.levandowski.project4all.adapters.holder.UserHolder;
import gustavo.levandowski.project4all.listeners.PositionAdapterCommentsListener;
import gustavo.levandowski.project4all.model.User;
import gustavo.levandowski.project4all.util.RoundedCornersImageTransform;

/***
 * Extends UserHolder diminuindo código, e gerênciando melhor as referências
 */
public class UserInformationAdapter extends RecyclerView.Adapter<UserHolder>{

    //region Builders/Properties
    private PositionAdapterCommentsListener loadListener;
    private List<User> acessData;
    private Context context;

    public UserInformationAdapter(List<User> acessData, Context context, PositionAdapterCommentsListener listener) {
        this.acessData = acessData;
        this.context = context;
        this.loadListener = listener;
    }
    //endregion

    //region Inflate Card |elements managed by the holder | Count ArrayList

    @NonNull
    @Override
    public UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_comments_info_user_activity, parent, false);
        UserHolder createHolder = new UserHolder(v);
        return createHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final UserHolder holder, @SuppressLint("RecyclerView") final int position) {
        this.setupUserComment(holder,position);
        this.setRatingBar(holder,position);

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

    //region updateViews

    private void setRatingBar(UserHolder holder, int position){
        holder.textRatingComments.setText(acessData.get(position).getComentarios().get(position).getNota());

        //Está configuração permite que a ratingBar receba números do tipo double.
        holder.ratingBar.setEnabled(false);
        holder.ratingBar.setMax(5);
        holder.ratingBar.setStepSize(0.01f);
        holder.ratingBar.setRating(Float.parseFloat(acessData.get(position).getComentarios().get(position).getNota()));
        holder.ratingBar.invalidate();
    }

    /***
     * Método qua adiciona a lista de comentários textos e imagens
     */
    private void setupUserComment(UserHolder holder, int position){
        holder.titleComments.setText(acessData.get(position).getComentarios().get(position).getNome());

        Picasso.get().load(acessData.get(position).getComentarios().get(position).getUrlFoto())
                .resize(200, 200).centerCrop().
                transform( new RoundedCornersImageTransform()).into(holder.imageComments);
    }
    //endregion

}
