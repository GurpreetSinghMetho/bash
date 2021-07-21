package com.orem.bashhub.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;

import com.orem.bashhub.R;
import com.orem.bashhub.activity.CreateBashActivity;
import com.orem.bashhub.adapter.SelectPlaylistAdapter;
import com.orem.bashhub.data.SpotifyListPOJO;
import com.orem.bashhub.data.UsersListPOJO;
import com.orem.bashhub.databinding.DialogPlaylistBinding;
import com.orem.bashhub.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class DialogPlaylist extends DialogFragment implements View.OnClickListener {

    public static List<SpotifyListPOJO.Item> items;
    Context mContext;
    private DialogPlaylistBinding binding;
    private SelectPlaylistAdapter adapter;

    public DialogPlaylist(Context mContext, List<SpotifyListPOJO.Item> items) {
        this.items = items;
        this.mContext = mContext;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_playlist, container, false);
        return binding.getRoot();
    }

    @Override
    public void setupDialog(@NonNull Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init() {
        adapter=new SelectPlaylistAdapter(mContext, items);
        binding.mRecyclerview.setAdapter(adapter);
        binding.btCancel.setOnClickListener(this);
        binding.btAdd.setOnClickListener(this);
        binding.etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                filter(editable.toString().toLowerCase());
            }
        });
    }
    private void filter(String text) {
        List<SpotifyListPOJO.Item> filteredList = new ArrayList<>();
        if (text.isEmpty()) {
            filteredList.addAll(items);
        } else {
            for (SpotifyListPOJO.Item item : items) {
                if (item.getName().toLowerCase().contains(text))
                    filteredList.add(item);
            }
        }
        adapter.submitList(filteredList);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btCancel:
                dismiss();
                break;
            case R.id.btAdd:
                ArrayList<SpotifyListPOJO.Item> itemArrayList = new ArrayList<>();

                boolean isSelected = false;
                for (int i = 0; i < items.size(); i++) {
                    if (items.get(i).isSelected()) {
                        isSelected = true;
                        SpotifyListPOJO.Item item1 = new SpotifyListPOJO.Item();
                        item1.setHref(items.get(i).getHref());
                        item1.setId(items.get(i).getId());
                        item1.setName(items.get(i).getName());
                        SpotifyListPOJO.Image image = new SpotifyListPOJO.Image();
                        image.setHeight(300);
                        image.setWidth(200);
                        image.setUrl(items.get(i).getImages().get(0).url);
                        ArrayList<SpotifyListPOJO.Image> imageArrayList = new ArrayList<>();
                        imageArrayList.add(image);
                        item1.setImages(imageArrayList);
                        itemArrayList.add(item1);
                    }
                }
                if (!isSelected) {
                    Utils.showToast(mContext, mContext.getString(R.string.please_select_playlist_add));
                    return;
                }
                CreateBashActivity.playlist(itemArrayList);
                dismiss();
                break;
        }
    }
}