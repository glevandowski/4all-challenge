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
    public class FullCommentsHolder extends RecyclerView.ViewHolder {
      public TextView userComments;
      public TextView textRatingBar;
      public TextView textTitleComment;
      public TextView textComment;
      public ImageView imageUserComment;
      public RatingBar ratingBar;

        public FullCommentsHolder(View holder){
            super(holder);
            userComments = holder.findViewById(R.id.title_card_comments);
            imageUserComment = holder.findViewById(R.id.image_card_comments_dialog);
            ratingBar = holder.findViewById(R.id.ratingbar_comments_card_dialog);
            textRatingBar = holder.findViewById(R.id.text_rating_cards_comments_dialog);
            textTitleComment = holder.findViewById(R.id.text_title_comments_dialog);
            textComment = holder.findViewById(R.id.text_comment_dialog);
        }
    }

