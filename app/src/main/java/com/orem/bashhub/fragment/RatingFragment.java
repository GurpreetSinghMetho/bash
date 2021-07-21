package com.orem.bashhub.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.orem.bashhub.R;
import com.orem.bashhub.adapter.GiveComplementAdapter;
import com.orem.bashhub.data.BashDetailsPOJO;
import com.orem.bashhub.databinding.FragmentRatingBinding;
import com.orem.bashhub.utils.Const;
import com.orem.bashhub.utils.Utils;
import com.orem.bashhub.utils.apiinterface.Events;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class RatingFragment extends BaseFragment {

    private FragmentRatingBinding binding;
    private BashDetailsPOJO bashData;
    private String skip;
    private boolean isRating = false;
    private GiveComplementAdapter adapter;

    public RatingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_rating, container, false);
        ini();
        return binding.getRoot();
    }

    private void ini() {
        binding.setData(bashData);
        binding.ivBack.setOnClickListener(v -> Objects.requireNonNull(getActivity()).onBackPressed());
        adapter = new GiveComplementAdapter(mContext);
        binding.rvComplements.setAdapter(adapter);
        binding.btSubmit.setOnClickListener(v -> {
            if (binding.ratingBar.getRating() > 0) {
                skip = Const.ZERO;
                apiRating();
            } else {
                Utils.showToast(mContext, getString(R.string.choose_lit_factor));
            }
        });
    }

    public void onBackPressed() {
        if (!isRating) {
            skip = Const.ONE;
            apiRating();
        }
    }

    public void setData(BashDetailsPOJO bashData) {
        this.bashData = bashData;
    }

    private void apiRating() {
        registerEventBus();
        EventBus.getDefault().post(new Events.RequestBasic(mContext, Const.apiRating(mContext, bashData.id,
                skip, "" + binding.ratingBar.getRating(), adapter.getSelected()), true, false));
    }

    @Subscribe
    public void apiRatingRes(Events.GetBasicData res) {
        unRegisterEventBus();
        if (skip.equals(Const.ZERO))
            Utils.showToast(mContext, getString(R.string.rating_success));
        isRating = true;
        binding.ivBack.performClick();
    }
}
