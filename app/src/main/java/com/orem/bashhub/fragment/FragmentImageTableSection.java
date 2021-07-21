package com.orem.bashhub.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.github.chrisbanes.photoview.PhotoView;
import com.orem.bashhub.R;
import com.orem.bashhub.activity.MainActivity;
import com.orem.bashhub.data.BashDetailsPOJO;
import com.orem.bashhub.databinding.ImageSectionTableBinding;
import com.orem.bashhub.databinding.TableRowBinding;
import com.orem.bashhub.utils.Const;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

public class FragmentImageTableSection extends BaseFragment {
    private ImageSectionTableBinding binding;
    List<String> tables;
    List<String> section;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.image_section_table, container, false);
        init();
        return binding.getRoot();
    }

    private void init() {
        binding.mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) mContext).getSupportFragmentManager().popBackStack();
            }
        });
        if (section!=null){
            binding.mTitle.setText("Section Floor Plan");
            SectionAdapterImage adapter=new SectionAdapterImage(section);
            binding.ivImage.setSliderAdapter(adapter);
        } if (tables!=null){
            TableAdapterImage adapter=new TableAdapterImage(tables);
            binding.ivImage.setSliderAdapter(adapter);
        }

    }
    public  void setData(List<String> sections){
        this.section=sections;

    }  public  void setData1(List<String> tables){
        this.tables=tables;

    }

    private class SectionAdapterImage extends SliderViewAdapter<SectionAdapterImage.viewHolder> {
        List<String> section;
        public SectionAdapterImage(List<String> section) {
       this.section=section; }

        @Override
        public viewHolder onCreateViewHolder(ViewGroup parent) {
            View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_view, null);
            return new viewHolder(inflate);
        }

        @Override
        public void onBindViewHolder(viewHolder viewHolder, int position) {
            Glide.with(viewHolder.itemView)
                    .load(Const.IMAGE_BASE_EVENT + section.get(position))
                    .apply(new RequestOptions().placeholder(R.drawable.placeholder))
                    .into(viewHolder.imageGifContainer);


        }

        @Override
        public int getCount() {
            return section.size();
        }

        public class viewHolder extends SliderViewAdapter.ViewHolder {
            View itemView;
            PhotoView imageGifContainer;
            public viewHolder(View itemView) {
                super(itemView);
                this.itemView = itemView;

                imageGifContainer = itemView.findViewById(R.id.mImage);
            }
        }
    }
    private class TableAdapterImage extends SliderViewAdapter<TableAdapterImage.viewHolder> {
        List<String> table;
        public TableAdapterImage(List<String> table) {
       this.table=table;
        }

        @Override
        public viewHolder onCreateViewHolder(ViewGroup parent) {
            View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_view, null);
            return new viewHolder(inflate);
        }

        @Override
        public void onBindViewHolder(viewHolder viewHolder, int position) {
            Glide.with(getActivity())
                    .load(Const.IMAGE_BASE_EVENT + table.get(position))
                    .apply(new RequestOptions().placeholder(R.drawable.placeholder))
                    .into(viewHolder.imageGifContainer);
            Log.e("image_ss",Const.IMAGE_BASE_EVENT + table.get(position));
        }

        @Override
        public int getCount() {
            return table.size();
        }

        public class viewHolder extends SliderViewAdapter.ViewHolder {
            View itemView;
            PhotoView imageGifContainer;
            public viewHolder(View itemView) {
                super(itemView);
                this.itemView = itemView;

                imageGifContainer = itemView.findViewById(R.id.mImage);
            }
        }
    }
}
