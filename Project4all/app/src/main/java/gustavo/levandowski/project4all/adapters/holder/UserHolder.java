package gustavo.levandowski.project4all.adapters.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import gustavo.levandowski.project4all.R;

/**
 * Classe auxiliar responsável por gerenciar as referências do adapter
 */
public class UserHolder extends RecyclerView.ViewHolder {
     public TextView titleComments;
     public ImageView imageComments;
     public TextView textRatingComments;
     public RatingBar ratingBar;

    public UserHolder(View holder){
        super(holder);
        textRatingComments = holder.findViewById(R.id.text_rating_cards_comments);
        imageComments = holder.findViewById(R.id.image_card_comments_user_activity);
        titleComments = holder.findViewById(R.id.title_card_comments);
        ratingBar = holder.findViewById(R.id.ratingbar_comments_card);
    }
}