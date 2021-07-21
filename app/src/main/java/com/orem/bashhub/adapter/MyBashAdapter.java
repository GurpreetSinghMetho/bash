package com.orem.bashhub.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.orem.bashhub.R;
import com.orem.bashhub.activity.CreateBashActivity;
import com.orem.bashhub.activity.MainActivity;
import com.orem.bashhub.data.BashDetailsPOJO;
import com.orem.bashhub.data.MyBashInnerPOJO;
import com.orem.bashhub.databinding.AdapterMyBashBinding;
import com.orem.bashhub.fragment.BashDetailsWebFragment;
import com.orem.bashhub.fragment.BashDetialFragment;
import com.orem.bashhub.fragment.EventUsersFragment;
import com.orem.bashhub.fragment.MyBashWebDetailFragment;
import com.orem.bashhub.fragment.TicketsFragment;
import com.orem.bashhub.interfaces.OnBgApi;
import com.orem.bashhub.utils.Const;
import com.orem.bashhub.utils.Utils;

import java.util.List;

public class MyBashAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<MyBashInnerPOJO> list;
    private OnBgApi listener;
    private String loggedInID;

    public MyBashAdapter(Context mContext, List<MyBashInnerPOJO> list, OnBgApi listener) {
        this.mContext = mContext;
        this.list = list;
        this.listener = listener;
        this.loggedInID = Const.getLoggedInUserID(mContext);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(AdapterMyBashBinding.inflate(LayoutInflater.from(mContext), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Holder h = (Holder) holder;
        h.bind();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class Holder extends RecyclerView.ViewHolder {

        AdapterMyBashBinding binding;

        Holder(AdapterMyBashBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind() {
            BashDetailsPOJO item = list.get(getAdapterPosition()).bash_data;
            binding.setData(item);
            binding.setUserID(loggedInID);
            binding.executePendingBindings();
            Utils.underlineTextView(binding.tvEdit);
            if (!list.get(getAdapterPosition()).bash_data.getCreatedFrom().toString().equalsIgnoreCase("1")){
                binding.webViewLL.setVisibility(View.GONE);
                binding.mViewDetail.setVisibility(View.VISIBLE);
            }
            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (list.get(getAdapterPosition()).bash_data.getCreatedFrom()==1){
                        BashDetialFragment fragment = new BashDetialFragment();
                        fragment.setData(list.get(getAdapterPosition()).bash_data, true);
                        Utils.goToFragment(mContext, fragment, R.id.fragment_container);
                        ((MainActivity) mContext).binding.fragmentContainer.setVisibility(View.VISIBLE);
                    }else {
                        MyBashWebDetailFragment fragment = new MyBashWebDetailFragment();
                        fragment.setData(list.get(getAdapterPosition()).bash_data, true);
                        Utils.goToFragment(mContext, fragment, R.id.fragment_container);
                        ((MainActivity) mContext).binding.fragmentContainer.setVisibility(View.VISIBLE);
                    }

                }
            });
            binding.rlTicket.setOnClickListener(v -> {
                TicketsFragment fragment = new TicketsFragment();
                fragment.setData(item,"ticket");
                Utils.goToFragment(mContext, fragment, R.id.fragment_container);
            });
            binding.tvEdit.setOnClickListener(v -> {
                CreateBashActivity createBashActivity = new CreateBashActivity();
                Intent intent = new Intent(mContext, createBashActivity.getClass());
                mContext.startActivity(intent);
                CreateBashActivity.setData(item, listener);

              /*  CreateBashFragment fragment = new CreateBashFragment();
                fragment.setData(item, listener);
                Utils.goToFragment(mContext, fragment, R.id.fragment_container);*/
            });
            binding.tvRSVP.setOnClickListener(v -> {
                EventUsersFragment fragment = new EventUsersFragment();
                fragment.setData(item);
                Utils.goToFragment(mContext, fragment, R.id.fragment_container);
            });
            binding.ivUber.setOnClickListener(v -> Const.onUberClick(mContext));
        }
    }
}
