package com.orem.bashhub.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.orem.bashhub.R;
import com.orem.bashhub.activity.MainActivity;
import com.orem.bashhub.data.BashDetailsPOJO;
import com.orem.bashhub.data.VenueListPOJO;
import com.orem.bashhub.databinding.FragmentOrderDetailBinding;
import com.orem.bashhub.databinding.OrderDrinkBottleRowBinding;
import com.orem.bashhub.databinding.OrderTableSectionRowBinding;
import com.orem.bashhub.utils.Const;
import com.orem.bashhub.utils.Utils;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class OrderDetailFragment extends BaseFragment {

    String from;
    int index;
    private FragmentOrderDetailBinding binding;
    private BashDetailsPOJO data;
    private VenueListPOJO.Venue venuedata;
    private List<VenueListPOJO.Item_____> itemSectiondata;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_order_detail, container, false);
        ini();
        return binding.getRoot();
    }

    private void ini() {
        binding.mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) mContext).getSupportFragmentManager().popBackStack();
            }
        });
        if (data != null) {
            if (from.equalsIgnoreCase("table")) {
                binding.mRecyclerView.setAdapter(new TableAdapter(mContext, data.getQrTable().get(index).orderItem));

            } else if (from.equalsIgnoreCase("service")) {
                binding.mRecyclerView.setAdapter(new ServiceAdapter(mContext, data.getQrProduct().get(index).getOrderItem()));

            } else if (from.equalsIgnoreCase("section")) {
                binding.mRecyclerView.setAdapter(new SectionAdapter(mContext, data.getQrSection().get(index).orderItem));

            } else if (from.equalsIgnoreCase("drink")) {
                binding.mRecyclerView.setAdapter(new DrinkAdapter(mContext, data.getQrDrink().get(index).orderItem));

            } else if (from.equalsIgnoreCase("bottle")) {
                binding.mRecyclerView.setAdapter(new BottleAdapter(mContext, data.getQrBottle().get(index).orderItem));

            }
        } else if (venuedata != null) {
            if (from.equalsIgnoreCase("table")) {
                binding.mRecyclerView.setAdapter(new VenueTableAdapter(mContext, venuedata.getQrTable().get(index).getOrderItem()));

            } else if (from.equalsIgnoreCase("service")) {
                binding.mRecyclerView.setAdapter(new VenueServiceAdapter(mContext, venuedata.getQrProduct().get(index).getOrderItem()));

            } else if (from.equalsIgnoreCase("section")) {
                binding.mRecyclerView.setAdapter(new VenueSectionAdapter(mContext, venuedata.getQrSection().get(index).getOrderItem()));

            } else if (from.equalsIgnoreCase("drink")) {
                binding.mRecyclerView.setAdapter(new VenueDrinkAdapter(mContext, venuedata.getQrDrink().get(index).getOrderItem()));

            } else if (from.equalsIgnoreCase("bottle")) {
                binding.mRecyclerView.setAdapter(new VenueBottleAdapter(mContext, venuedata.getQrBottle().get(index).getOrderItem()));

            }
        } else {
            binding.mRecyclerView.setAdapter(new VenueItemSectionAdapter(mContext, itemSectiondata));

        }

    }

    public void setData(BashDetailsPOJO data, String from, int index) {
        this.data = data;
        this.from = from;
        this.index = index;
    }

    public void setVenueData(VenueListPOJO.Venue data, String from, int index) {
        this.venuedata = data;
        this.from = from;
        this.index = index;
    }

    public void setItemSectionData(List<VenueListPOJO.Item_____> data, String from, int index) {
        this.itemSectiondata = data;
        this.from = from;
        this.index = index;
    }

    public static class DrinkAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private Context mContext;
        private List<BashDetailsPOJO.OrderItem_> list;


        public DrinkAdapter(Context mContext, List<BashDetailsPOJO.OrderItem_> list) {
            this.mContext = mContext;
            this.list = list;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new DrinkAdapter.Holder(OrderDrinkBottleRowBinding.inflate(LayoutInflater.from(mContext), parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            DrinkAdapter.Holder h = (DrinkAdapter.Holder) holder;
            h.bind();
        }

        @Override
        public int getItemCount() {
            return list.size();
        }


        class Holder extends RecyclerView.ViewHolder {

            OrderDrinkBottleRowBinding binding;

            Holder(OrderDrinkBottleRowBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
            }

            public void bind() {
                BashDetailsPOJO.OrderItem_ item = list.get(getAdapterPosition());
                binding.mName.setText(item.getName());
                Double price = Double.parseDouble(item.getPrice()) * item.getQuantity();
                binding.mPrice.setText("$" + price);

                binding.mQuantity.setText(item.getQuantity() + "");

            }
        }
    }

    public static class VenueDrinkAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private Context mContext;
        private List<VenueListPOJO.OrderItem> list;


        public VenueDrinkAdapter(Context mContext, List<VenueListPOJO.OrderItem> list) {
            this.mContext = mContext;
            this.list = list;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new Holder(OrderDrinkBottleRowBinding.inflate(LayoutInflater.from(mContext), parent, false));
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

            OrderDrinkBottleRowBinding binding;

            Holder(OrderDrinkBottleRowBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
            }

            public void bind() {
                VenueListPOJO.OrderItem item = list.get(getAdapterPosition());
                binding.mName.setText(item.getName());
                Double price = Double.parseDouble(item.getPrice()) * item.getQuantity();
                binding.mPrice.setText("$" + price);

                binding.mQuantity.setText(item.getQuantity() + "");

            }
        }
    }

    public static class ServiceAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private Context mContext;
        private List<BashDetailsPOJO.OrderItem_____> list;


        public ServiceAdapter(Context mContext, List<BashDetailsPOJO.OrderItem_____> list) {
            this.mContext = mContext;
            this.list = list;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ServiceAdapter.Holder(OrderDrinkBottleRowBinding.inflate(LayoutInflater.from(mContext), parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            ServiceAdapter.Holder h = (ServiceAdapter.Holder) holder;
            h.bind();
        }

        @Override
        public int getItemCount() {
            return list.size();
        }


        class Holder extends RecyclerView.ViewHolder {

            OrderDrinkBottleRowBinding binding;

            Holder(OrderDrinkBottleRowBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
            }

            public void bind() {
                BashDetailsPOJO.OrderItem_____ item = list.get(getAdapterPosition());
                binding.mName.setText(item.getName());
                binding.mImage.setVisibility(View.VISIBLE);
                Utils.loadImage(mContext, Const.IMAGE_BASE_EVENT + item.getImage(), binding.mImage, R.drawable.placeholder);
                if (item.getPrice() != null) {
                    Double price = Double.parseDouble(item.getPrice().toString()) * item.getQuantity();
                    binding.mPrice.setText("$" + price);
                } else
                    binding.mPrice.setText("$0");
                binding.mQuantity.setText(item.getQuantity() + "");

            }
        }
    }

    public static class VenueServiceAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private Context mContext;
        private List<VenueListPOJO.OrderItem____> list;


        public VenueServiceAdapter(Context mContext, List<VenueListPOJO.OrderItem____> list) {
            this.mContext = mContext;
            this.list = list;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new Holder(OrderDrinkBottleRowBinding.inflate(LayoutInflater.from(mContext), parent, false));
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

            OrderDrinkBottleRowBinding binding;

            Holder(OrderDrinkBottleRowBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
            }

            public void bind() {
                VenueListPOJO.OrderItem____ item = list.get(getAdapterPosition());
                binding.mName.setText(item.getName());
                binding.mImage.setVisibility(View.VISIBLE);
                Utils.loadImage(mContext, Const.IMAGE_BASE_EVENT + item.getImage(), binding.mImage, R.drawable.placeholder);
                if (item.getPrice() != null) {
                    Double price = Double.parseDouble(item.getPrice().toString()) * item.getQuantity();
                    binding.mPrice.setText("$" + price);
                } else
                    binding.mPrice.setText("$0");
                binding.mQuantity.setText(item.getQuantity() + "");

            }
        }
    }

    public static class BottleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private Context mContext;
        private List<BashDetailsPOJO.OrderItem__> list;


        public BottleAdapter(Context mContext, List<BashDetailsPOJO.OrderItem__> list) {
            this.mContext = mContext;
            this.list = list;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new BottleAdapter.Holder(OrderDrinkBottleRowBinding.inflate(LayoutInflater.from(mContext), parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            BottleAdapter.Holder h = (BottleAdapter.Holder) holder;
            h.bind();
        }

        @Override
        public int getItemCount() {
            return list.size();
        }


        class Holder extends RecyclerView.ViewHolder {

            OrderDrinkBottleRowBinding binding;

            Holder(OrderDrinkBottleRowBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
            }

            public void bind() {
                BashDetailsPOJO.OrderItem__ item = list.get(getAdapterPosition());
                binding.mName.setText(item.getName());
                Double price = Double.parseDouble(item.getPrice()) * item.getQuantity();
                binding.mPrice.setText("$" + price);
                binding.mQuantity.setText(item.getQuantity() + "");


            }
        }
    }

    public static class VenueBottleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private Context mContext;
        private List<VenueListPOJO.OrderItem_> list;


        public VenueBottleAdapter(Context mContext, List<VenueListPOJO.OrderItem_> list) {
            this.mContext = mContext;
            this.list = list;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new Holder(OrderDrinkBottleRowBinding.inflate(LayoutInflater.from(mContext), parent, false));
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

            OrderDrinkBottleRowBinding binding;

            Holder(OrderDrinkBottleRowBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
            }

            public void bind() {
                VenueListPOJO.OrderItem_ item = list.get(getAdapterPosition());
                binding.mName.setText(item.getName());
                Double price = Double.parseDouble(item.getPrice()) * item.getQuantity();
                binding.mPrice.setText("$" + price);
                binding.mQuantity.setText(item.getQuantity() + "");


            }
        }
    }

    public static class TableAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private Context mContext;
        private List<BashDetailsPOJO.OrderItem___> list;


        public TableAdapter(Context mContext, List<BashDetailsPOJO.OrderItem___> list) {
            this.mContext = mContext;
            this.list = list;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new TableAdapter.Holder(OrderTableSectionRowBinding.inflate(LayoutInflater.from(mContext), parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            TableAdapter.Holder h = (TableAdapter.Holder) holder;
            h.bind();
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class Holder extends RecyclerView.ViewHolder {

            OrderTableSectionRowBinding binding;

            Holder(OrderTableSectionRowBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
            }

            public void bind() {
                BashDetailsPOJO.OrderItem___ item = list.get(getAdapterPosition());
                binding.mName.setText(item.getName());
                binding.mDescription.setText(item.getDescription());
                binding.mPrice.setText("$" + item.getPrice());
            }
        }
    }

    public static class VenueTableAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private Context mContext;
        private List<VenueListPOJO.OrderItem__> list;


        public VenueTableAdapter(Context mContext, List<VenueListPOJO.OrderItem__> list) {
            this.mContext = mContext;
            this.list = list;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new Holder(OrderTableSectionRowBinding.inflate(LayoutInflater.from(mContext), parent, false));
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

            OrderTableSectionRowBinding binding;

            Holder(OrderTableSectionRowBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
            }

            public void bind() {
                VenueListPOJO.OrderItem__ item = list.get(getAdapterPosition());
                binding.mName.setText(item.getName());
                binding.mDescription.setText(item.getDescription());
                binding.mPrice.setText("$" + item.getPrice());
            }
        }
    }

    public static class SectionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private Context mContext;
        private List<BashDetailsPOJO.OrderItem____> list;


        public SectionAdapter(Context mContext, List<BashDetailsPOJO.OrderItem____> list) {
            this.mContext = mContext;
            this.list = list;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new SectionAdapter.Holder(OrderTableSectionRowBinding.inflate(LayoutInflater.from(mContext), parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            SectionAdapter.Holder h = (SectionAdapter.Holder) holder;
            h.bind();
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class Holder extends RecyclerView.ViewHolder {

            OrderTableSectionRowBinding binding;

            Holder(OrderTableSectionRowBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
            }

            public void bind() {
                BashDetailsPOJO.OrderItem____ item = list.get(getAdapterPosition());
                binding.mName.setText(item.getName());
                binding.mDescription.setText(item.getDescription());
                binding.mPrice.setText("$" + item.getPrice());
            }
        }
    }

    public static class VenueSectionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private Context mContext;
        private List<VenueListPOJO.OrderItem___> list;


        public VenueSectionAdapter(Context mContext, List<VenueListPOJO.OrderItem___> list) {
            this.mContext = mContext;
            this.list = list;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new Holder(OrderTableSectionRowBinding.inflate(LayoutInflater.from(mContext), parent, false));
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

            OrderTableSectionRowBinding binding;

            Holder(OrderTableSectionRowBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
            }

            public void bind() {
                VenueListPOJO.OrderItem___ item = list.get(getAdapterPosition());
                binding.mName.setText(item.getName());
                binding.mDescription.setText(item.getDescription());
                binding.mPrice.setText("$" + item.getPrice());
            }
        }
    }

    private class VenueItemSectionAdapter extends RecyclerView.Adapter<VenueItemSectionAdapter.ViewHolder> {
        Context mContext; List<VenueListPOJO.Item_____> itemSectiondata;
        public VenueItemSectionAdapter(Context mContext, List<VenueListPOJO.Item_____> itemSectiondata) {
            this.mContext=mContext;
            this.itemSectiondata=itemSectiondata;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(OrderDrinkBottleRowBinding.inflate(LayoutInflater.from(mContext), parent, false));

        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
holder.binding.mName.setText(itemSectiondata.get(position).getName());
holder.binding.mDescription.setText(itemSectiondata.get(position).getDescription());
holder.binding.mQuantity.setText(itemSectiondata.get(position).getQuantity().toString());
holder.binding.mPrice.setText("$"+itemSectiondata.get(position).getPrice());
//holder.binding.mImage.setVisibility(View.VISIBLE);
//            Glide.with(mContext).load(Const.IMAGE_BASE_EVENT +itemSectiondata.get(position).get()).into(holder.binding.mTicketImage);

        }

        @Override
        public int getItemCount() {
            return itemSectiondata.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            OrderDrinkBottleRowBinding binding;
            public ViewHolder(@NonNull OrderDrinkBottleRowBinding itemView) {
                super(itemView.getRoot());
                binding=itemView;
            }
        }
    }
}