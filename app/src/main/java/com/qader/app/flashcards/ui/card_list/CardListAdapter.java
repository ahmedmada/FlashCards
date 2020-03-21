package com.qader.app.flashcards.ui.card_list;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.qader.app.flashcards.R;
import com.qader.app.flashcards.database.entity.CardEntity;
import com.qader.app.flashcards.databinding.ListItemCardBinding;

import java.util.List;


public class CardListAdapter extends PagedListAdapter<CardEntity, CardListAdapter.CardViewHolder> {

    private List<CardEntity> mCardEntities;

    public CardListAdapter() {
        super(CardEntity.DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public CardListAdapter.CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListItemCardBinding listItemCardBinding =
            DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.list_item_card, parent, false);
        return new CardViewHolder(listItemCardBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CardListAdapter.CardViewHolder holder, int position) {
        if (mCardEntities != null) {
            CardEntity currentCardEntity = mCardEntities.get(position);
            holder.listItemCardBinding.setCardEntity(currentCardEntity);
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

    class CardViewHolder extends RecyclerView.ViewHolder {
        ListItemCardBinding listItemCardBinding;

        public CardViewHolder(ListItemCardBinding listItemCardBinding) {
            super(listItemCardBinding.getRoot());

            this.listItemCardBinding = listItemCardBinding;

        }
    }

}
