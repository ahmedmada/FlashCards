package com.qader.app.flashcards.ui.practice_list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.qader.app.flashcards.R;
import com.qader.app.flashcards.database.entity.CardEntity;
import com.qader.app.flashcards.databinding.ListItemPracticeBinding;

import java.util.List;

public class PracticeListAdapter extends PagedListAdapter<CardEntity, PracticeListAdapter.CardViewHolder> {

    private List<CardEntity> mCardEntities;

    public PracticeListAdapter() {
        super(CardEntity.DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public PracticeListAdapter.CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListItemPracticeBinding listItemPracticeBinding =
            DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.list_item_practice, parent, false);
        return new CardViewHolder(listItemPracticeBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull PracticeListAdapter.CardViewHolder holder,final int position) {
        if (mCardEntities != null) {
            CardEntity currentCardEntity = mCardEntities.get(position);
            holder.listItemPracticeBinding.setCardEntity(currentCardEntity);
        }
    }

    public void setCardEntities(List<CardEntity> cardEntities) {
        mCardEntities = cardEntities;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mCardEntities != null) {
            return mCardEntities.size();
        } else {
            return 0;
        }
    }

    /**
     * Method to get item by position.
     * @param position
     * @return
     */
    @Nullable
    public CardEntity getItem(int position) {
        return mCardEntities.get(position);
    }


    /**
     * Custom click item listener.
     */
    onItemClickListener mOnItemClickListener;

    public void setClickListener(onItemClickListener itemClickListener) {
        mOnItemClickListener = itemClickListener;
    }


    public interface onItemClickListener {
        void ItemClicked(View v, int position);
    }


    /**
     * View Holder Class
     */
    class CardViewHolder extends RecyclerView.ViewHolder {

        ListItemPracticeBinding listItemPracticeBinding;

        public CardViewHolder(ListItemPracticeBinding listItemPracticeBinding) {
            super(listItemPracticeBinding.getRoot());
            this.listItemPracticeBinding = listItemPracticeBinding;

            listItemPracticeBinding.getRoot().setOnClickListener(view -> {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.ItemClicked(view, getAdapterPosition());
                }
            });
        }
    }

}
