package com.orem.bashhub.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.SectionIndexer;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.orem.bashhub.data.BashUsersPOJO;
import com.orem.bashhub.databinding.AdapterPartyMemberListsBinding;
import com.orem.bashhub.interfaces.OnScanTicket;

import java.util.ArrayList;
import java.util.List;

public class ListOfPartyMemberAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements SectionIndexer {

    private Context mContext;
    private List<BashUsersPOJO.Data> list;
    private List<Integer> mSectionPositions;

    public ListOfPartyMemberAdapter(Context mContext, List<BashUsersPOJO.Data> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(AdapterPartyMemberListsBinding.inflate(LayoutInflater.from(mContext), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        Holder h = (Holder) holder;
        h.bind();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getSectionForPosition(int position) {
        return 0;
    }

    @Override
    public Object[] getSections() {
        List<String> sections = new ArrayList<>(26);
        mSectionPositions = new ArrayList<>(26);
        for (int i = 0; i < list.size(); i++) {
            String section = String.valueOf(list.get(i).user_data.fname.charAt(0)).toUpperCase();
            if (!sections.contains(section)) {
                sections.add(section);
                mSectionPositions.add(i);
            }
        }
        return sections.toArray(new String[0]);
    }

    @Override
    public int getPositionForSection(int sectionIndex) {
        return mSectionPositions.get(sectionIndex);
    }

    class Holder extends RecyclerView.ViewHolder {

        AdapterPartyMemberListsBinding binding;

        public Holder(@NonNull AdapterPartyMemberListsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind() {
            BashUsersPOJO.Data item = list.get(getAdapterPosition());
            binding.setData(item);
            binding.executePendingBindings();
        }
    }
}
