package com.qader.app.flashcards.ui.subject_list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.qader.app.flashcards.R;
import com.qader.app.flashcards.database.entity.SubjectEntity;
import com.qader.app.flashcards.databinding.ListItemSubjectBinding;

import java.util.List;

public class SubjectListAdapter extends RecyclerView.Adapter<SubjectListAdapter.SubjectViewHolder> {

    private List<SubjectEntity> mSubjectEntities;

    @NonNull
    @Override
    public SubjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ListItemSubjectBinding listItemSubjectBinding =
            DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
            R.layout.list_item_subject, parent, false);
        return new SubjectViewHolder(listItemSubjectBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull SubjectListAdapter.SubjectViewHolder holder, final int position) {
        if (mSubjectEntities != null) {

            SubjectEntity currentSubjectEntity = mSubjectEntities.get(position);
            holder.listItemSubjectBinding.setSubjectEntity(currentSubjectEntity);

        }
    }

    public void setSubjectEntities(List<SubjectEntity> subjectEntities) {
        mSubjectEntities = subjectEntities;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mSubjectEntities != null) {
            return mSubjectEntities.size();
        } else {
            return 0;
        }
    }

    /**
     * Method to get item by position.
     * @param position
     */
    @Nullable
    public SubjectEntity getItem(int position) {
        return mSubjectEntities.get(position);
    }


    /**
     * Custom click item listener.
     */
    private onItemClickListener mOnItemClickListener;

    public void setClickListener(onItemClickListener itemClickListener) {
        mOnItemClickListener = itemClickListener;
    }

    public interface onItemClickListener {
        void ItemClicked(View v, int position);
    }


    /**
     * View Holder Class
     */
    class SubjectViewHolder extends RecyclerView.ViewHolder{
//        private final TextView subjectTitleTextView;
        private ListItemSubjectBinding listItemSubjectBinding;


        private SubjectViewHolder(@NonNull ListItemSubjectBinding listItemSubjectBinding) {
            super(listItemSubjectBinding.getRoot());

            this.listItemSubjectBinding = listItemSubjectBinding;

            listItemSubjectBinding.getRoot().setOnClickListener(v -> {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.ItemClicked(v, getAdapterPosition());
                }
            });

        }
    }
}
