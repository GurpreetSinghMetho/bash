package com.orem.bashhub.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.orem.bashhub.R;
import com.orem.bashhub.activity.MainActivity;
import com.orem.bashhub.data.BashDetailsPOJO;
import com.orem.bashhub.data.VenueListPOJO;
import com.orem.bashhub.databinding.FragmentTableSectionListBinding;
import com.orem.bashhub.databinding.TableMenuRowBinding;
import com.orem.bashhub.utils.Const;
import com.orem.bashhub.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class TableSectionListFragment extends BaseFragment {
    FragmentTableSectionListBinding binding;
    BashDetailsPOJO data;
    VenueListPOJO.Venue venueData;
    String from;
    String type;
    ArrayList<HashMap<String, String>> tableList;
    ArrayList<HashMap<String, String>> serviceList;
    ArrayList<HashMap<String, String>> sectionList;
    ArrayList<HashMap<String, String>> drinkList;
    ArrayList<HashMap<String, String>> bottleList;
    private ArrayList<HashMap<String, String>> ticketList;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_table_section_list, container, false);
        init();
        return binding.getRoot();
    }

    private void init() {
        if (from.equalsIgnoreCase("table")) {
            binding.mTitle.setText("Tables");
            if (data != null) {

                binding.mRecyclerView.setAdapter(new TableAdapter(mContext, data.getTables()));
                if (data.getVenue().table_plan_image != null) {
                    binding.mViewFloorPlan.setVisibility(View.VISIBLE);
                    binding.mViewFloorPlan.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            FragmentImageTableSection fragment = new FragmentImageTableSection();
                            fragment.setData1(data.getVenue().table_plan_image);
                            Utils.goToFragment(mContext, fragment, R.id.fragment_container);
//                        ImageDialogBox cdd = new ImageDialogBox(getActivity(), data.getVenue().table_plan_image);
//                        cdd.show(((AppCompatActivity) mContext).getSupportFragmentManager(), "");
                        }
                    });
                } else binding.mViewFloorPlan.setVisibility(View.GONE);
                Utils.loadImage(mContext, Const.IMAGE_BASE_EVENT + data.getVenue().getImage(), binding.mVenuImage, R.drawable.placeholder);
                binding.mLocation.setText(data.getVenue().getAddress());
                binding.mVenueName.setText(data.getVenue().getName());
            } else {
                binding.mRecyclerView.setAdapter(new VenueTableAdapter(mContext, venueData.getTables()));
                if (venueData.getTablePlanImage() != null) {
                    binding.mViewFloorPlan.setVisibility(View.VISIBLE);
                    binding.mViewFloorPlan.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            FragmentImageTableSection fragment = new FragmentImageTableSection();
                            fragment.setData1(venueData.getTablePlanImage());
                            Utils.goToFragment(mContext, fragment, R.id.fragment_container);
//                        ImageDialogBox cdd = new ImageDialogBox(getActivity(), data.getVenue().table_plan_image);
//                        cdd.show(((AppCompatActivity) mContext).getSupportFragmentManager(), "");
                        }
                    });
                } else binding.mViewFloorPlan.setVisibility(View.GONE);
                Utils.loadImage(mContext, Const.IMAGE_BASE_EVENT + venueData.getImage(), binding.mVenuImage, R.drawable.placeholder);
                binding.mLocation.setText(venueData.getAddress());
                binding.mVenueName.setText(venueData.getName());
            }
        } else {
            binding.mTitle.setText("Sections");
            if (data != null) {
                binding.mRecyclerView.setAdapter(new SectionAdapter(mContext, data.getSections()));
                if (data.getVenue().section_plan_image != null) {
                    binding.mViewFloorPlan.setVisibility(View.VISIBLE);
                    binding.mViewFloorPlan.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            FragmentImageTableSection fragment = new FragmentImageTableSection();
                            fragment.setData(data.getVenue().section_plan_image);
                            Utils.goToFragment(mContext, fragment, R.id.fragment_container);
//                        ImageDialogBox cdd = new ImageDialogBox(getActivity(), data.getVenue().section_plan_image);
//                        cdd.show(((AppCompatActivity) mContext).getSupportFragmentManager(), "");
                        }
                    });
                } else binding.mViewFloorPlan.setVisibility(View.GONE);
                Utils.loadImage(mContext, Const.IMAGE_BASE_EVENT + data.getVenue().getImage(), binding.mVenuImage, R.drawable.placeholder);
                binding.mLocation.setText(data.getVenue().getAddress());
                binding.mVenueName.setText(data.getVenue().getName());
            } else {
                binding.mRecyclerView.setAdapter(new VenueSectionAdapter(mContext, venueData.getSections()));
                if (venueData.getSectionPlanImage() != null) {
                    binding.mViewFloorPlan.setVisibility(View.VISIBLE);
                    binding.mViewFloorPlan.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            FragmentImageTableSection fragment = new FragmentImageTableSection();
                            fragment.setData(venueData.getSectionPlanImage());
                            Utils.goToFragment(mContext, fragment, R.id.fragment_container);
//                        ImageDialogBox cdd = new ImageDialogBox(getActivity(), data.getVenue().section_plan_image);
//                        cdd.show(((AppCompatActivity) mContext).getSupportFragmentManager(), "");
                        }
                    });
                } else binding.mViewFloorPlan.setVisibility(View.GONE);
                Utils.loadImage(mContext, Const.IMAGE_BASE_EVENT + venueData.getImage(), binding.mVenuImage, R.drawable.placeholder);
                binding.mLocation.setText(venueData.getAddress());
                binding.mVenueName.setText(venueData.getName());
            }
        }
        binding.mAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type.equalsIgnoreCase("quick")) {
                    if (data!=null)
                    setDrinksData(from);
                    else setVenueDrinksData(from);
                } else
                    ((MainActivity) mContext).getSupportFragmentManager().popBackStack();
            }
        });
        binding.mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) mContext).getSupportFragmentManager().popBackStack();
            }
        });

    }

    public void setDrinksData(String from) {
        if (from.equalsIgnoreCase("table")) {
            drinkList = new ArrayList<>();
            bottleList = new ArrayList<>();
            tableList = new ArrayList<>();
            sectionList = new ArrayList<>();
            serviceList = new ArrayList<>();
            for (int i = 0; i < data.getTables().size(); i++) {
                HashMap<String, String> map = new HashMap<>();

                if (data.getTables().get(i).isChecked != null)
                    if (data.getTables().get(i).isChecked.equalsIgnoreCase("1")) {
                        map.put("id", data.getTables().get(i).getId());
                        map.put("price", data.getTables().get(i).getPrice());
                        map.put("name", data.getTables().get(i).getName());
                        map.put("guest", data.getTables().get(i).getGuestPerTable() + "");
                        map.put("table_id", data.getTables().get(i).getDesignationId() + "");
                        map.put("des", data.getTables().get(i).getDescription());
                        tableList.add(map);
                    }
            }
            if (tableList.size() > 0) {
                ConfirmOrderFragment fragment = new ConfirmOrderFragment();
                fragment.setData(data, ticketList, drinkList, bottleList, tableList, sectionList, serviceList, "6");
                Utils.goToFragment(mContext, fragment, R.id.fragment_container);
                ((MainActivity) mContext).binding.fragmentContainer.setVisibility(View.VISIBLE);
            } else {
                Toast.makeText(mContext, "Select Table", Toast.LENGTH_SHORT).show();
            }
        } else if (from.equalsIgnoreCase("section")) {
            drinkList = new ArrayList<>();
            bottleList = new ArrayList<>();
            tableList = new ArrayList<>();
            sectionList = new ArrayList<>();
            serviceList = new ArrayList<>();
            for (int i = 0; i < data.getSections().size(); i++) {
                HashMap<String, String> map = new HashMap<>();
                if (data.getSections().get(i).isChecked != null)
                    if (data.getSections().get(i).isChecked.equalsIgnoreCase("1")) {
                        map.put("id", data.getSections().get(i).getId());
                        map.put("price", data.getSections().get(i).getPrice());
                        map.put("name", data.getSections().get(i).getName());
                        map.put("guest", data.getSections().get(i).getGuest() + "");
                        sectionList.add(map);
                    }
            }
            if (sectionList.size() > 0) {
                ConfirmOrderFragment fragment = new ConfirmOrderFragment();
                fragment.setData(data, ticketList, drinkList, bottleList, tableList, sectionList, serviceList, "7");
                Utils.goToFragment(mContext, fragment, R.id.fragment_container);
                ((MainActivity) mContext).binding.fragmentContainer.setVisibility(View.VISIBLE);
            } else {
                Toast.makeText(mContext, "Select Section", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void setVenueDrinksData(String from) {
        if (from.equalsIgnoreCase("table")) {
            drinkList = new ArrayList<>();
            bottleList = new ArrayList<>();
            tableList = new ArrayList<>();
            sectionList = new ArrayList<>();
            serviceList = new ArrayList<>();
            for (int i = 0; i < venueData.getTables().size(); i++) {
                HashMap<String, String> map = new HashMap<>();

                if (venueData.getTables().get(i).isChecked != null)
                    if (venueData.getTables().get(i).isChecked.equalsIgnoreCase("1")) {
                        map.put("id", venueData.getTables().get(i).getId().toString());
                        map.put("price", venueData.getTables().get(i).getPrice().toString());
                        map.put("name", venueData.getTables().get(i).getName());
                        map.put("guest", venueData.getTables().get(i).getGuestPerTable() + "");
                        map.put("table_id", venueData.getTables().get(i).getDesignationId() + "");
                        map.put("des", venueData.getTables().get(i).getDescription());
                        tableList.add(map);
                    }
            }
            if (tableList.size() > 0) {
                ConfirmOrderFragment fragment = new ConfirmOrderFragment();
                fragment.setVenueData(venueData,venueData.getItemSections(), ticketList, drinkList, bottleList, tableList, sectionList, serviceList, "6");
                Utils.goToFragment(mContext, fragment, R.id.fragment_container);
                ((MainActivity) mContext).binding.fragmentContainer.setVisibility(View.VISIBLE);
            } else {
                Toast.makeText(mContext, "Select Table", Toast.LENGTH_SHORT).show();
            }
        } else if (from.equalsIgnoreCase("section")) {
            drinkList = new ArrayList<>();
            bottleList = new ArrayList<>();
            tableList = new ArrayList<>();
            sectionList = new ArrayList<>();
            serviceList = new ArrayList<>();
            for (int i = 0; i < venueData.getSections().size(); i++) {
                HashMap<String, String> map = new HashMap<>();
                if (venueData.getSections().get(i).isChecked != null)
                    if (venueData.getSections().get(i).isChecked.equalsIgnoreCase("1")) {
                        map.put("id", venueData.getSections().get(i).getId().toString());
                        map.put("price", venueData.getSections().get(i).getPrice().toString());
                        map.put("name", venueData.getSections().get(i).getName());
                        map.put("guest", venueData.getSections().get(i).getGuest() + "");
                        sectionList.add(map);
                    }
            }
            if (sectionList.size() > 0) {
                ConfirmOrderFragment fragment = new ConfirmOrderFragment();
                fragment.setVenueData(venueData, venueData.getItemSections(),ticketList, drinkList, bottleList, tableList, sectionList, serviceList, "7");
                Utils.goToFragment(mContext, fragment, R.id.fragment_container);
                ((MainActivity) mContext).binding.fragmentContainer.setVisibility(View.VISIBLE);
            } else {
                Toast.makeText(mContext, "Select Section", Toast.LENGTH_SHORT).show();
            }
        }
    }


    public void setData(BashDetailsPOJO data, String from, String type) {
        this.data = data;
        this.from = from;
        this.type = type;
    }

    public void setVenueData(VenueListPOJO.Venue data, String from, String type) {
        this.venueData = data;
        this.from = from;
        this.type = type;
    }

    public class TableAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private Context mContext;
        private List<BashDetailsPOJO.Table> list;

        public TableAdapter(Context mContext, List<BashDetailsPOJO.Table> list) {
            this.mContext = mContext;
            this.list = list;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new TableAdapter.Holder(TableMenuRowBinding.inflate(LayoutInflater.from(mContext), parent, false));
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

            TableMenuRowBinding binding;

            Holder(TableMenuRowBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
            }

            public void bind() {
                BashDetailsPOJO.Table item = list.get(getAdapterPosition());
                if (item.isChecked != null)
                    if (item.isChecked.equalsIgnoreCase("1")) {
                        binding.mRow.setTag("1");
                        data.getTables().get(getAdapterPosition()).isChecked = "1";
                        binding.mAdd.setText("Added");
                        binding.mAdd.setBackgroundResource(R.drawable.menu_bg);
                        binding.mAdd.setTextColor(mContext.getResources().getColor(R.color.white));
                    }
                binding.mName.setText(item.getName());
                binding.mGuest.setText(item.getGuestPerTable() + " Guest");
                binding.mDescription.setText(item.getDescription());
                if (item.getPriceRequest().equalsIgnoreCase("1")) {
                    binding.mRequestPricing.setVisibility(View.VISIBLE);
                    binding.mPrice.setVisibility(View.GONE);
                    binding.mAdd.setVisibility(View.GONE);
                } else {
                    binding.mPrice.setText("$" + item.getPrice());
                    binding.mRequestPricing.setVisibility(View.GONE);
                    binding.mPrice.setVisibility(View.VISIBLE);
                }
                binding.mTableId.setText("Table : " + item.getDesignationId());
                binding.mRequestPricing.setOnClickListener(v -> {
                    RequestPricingFragment fragment = new RequestPricingFragment();
                    fragment.setData(data, "table", getAdapterPosition());
                    Utils.goToFragment(mContext, fragment, R.id.fragment_container);

                });
                binding.mAdd.setOnClickListener(v -> {
                    if (binding.mRow.getTag().toString().equalsIgnoreCase("0")) {
                        binding.mRow.setTag("1");
                        data.getTables().get(getAdapterPosition()).isChecked = "1";
                        if (type.equalsIgnoreCase("detail"))
                            BashDetailsWebFragment.setDrinksData(data, "table");
                        binding.mAdd.setText("Added");
                        binding.mAdd.setBackgroundResource(R.drawable.menu_bg);
                        binding.mAdd.setTextColor(mContext.getResources().getColor(R.color.white));
                    } else {
                        binding.mRow.setTag("0");
                        data.getTables().get(getAdapterPosition()).isChecked = "0";
                        if (type.equalsIgnoreCase("detail"))
                            BashDetailsWebFragment.setDrinksData(data, "table");
                        binding.mAdd.setText("Add");
                        binding.mAdd.setBackgroundResource(R.drawable.purple_outline);
                        binding.mAdd.setTextColor(mContext.getResources().getColor(R.color.colorPurple));
                    }
                });
            }
        }
    }

    public class VenueTableAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private Context mContext;
        private List<VenueListPOJO.Table> list;

        public VenueTableAdapter(Context mContext, List<VenueListPOJO.Table> list) {
            this.mContext = mContext;
            this.list = list;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new Holder(TableMenuRowBinding.inflate(LayoutInflater.from(mContext), parent, false));
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

            TableMenuRowBinding binding;

            Holder(TableMenuRowBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
            }

            public void bind() {
                VenueListPOJO.Table item = list.get(getAdapterPosition());
                if (item.isChecked != null)
                    if (item.isChecked.equalsIgnoreCase("1")) {
                        binding.mRow.setTag("1");
                        venueData.getTables().get(getAdapterPosition()).isChecked = "1";
                        binding.mAdd.setText("Added");
                        binding.mAdd.setBackgroundResource(R.drawable.menu_bg);
                        binding.mAdd.setTextColor(mContext.getResources().getColor(R.color.white));
                    }
                binding.mName.setText(item.getName());
                binding.mGuest.setText(item.getGuestPerTable() + " Guest");
                binding.mDescription.setText(item.getDescription());
                if (item.getPriceRequest().toString().equalsIgnoreCase("1")) {
                    binding.mRequestPricing.setVisibility(View.VISIBLE);
                    binding.mPrice.setVisibility(View.GONE);
                    binding.mAdd.setVisibility(View.GONE);
                } else {
                    binding.mPrice.setText("$" + item.getPrice());
                    binding.mRequestPricing.setVisibility(View.GONE);
                    binding.mPrice.setVisibility(View.VISIBLE);
                }
                binding.mTableId.setText("Table : " + item.getDesignationId());
                binding.mRequestPricing.setOnClickListener(v -> {
                    RequestPricingFragment fragment = new RequestPricingFragment();
                    fragment.setVenueData(venueData, "table", getAdapterPosition());
                    Utils.goToFragment(mContext, fragment, R.id.fragment_container);

                });
                binding.mAdd.setOnClickListener(v -> {
                    if (binding.mRow.getTag().toString().equalsIgnoreCase("0")) {
                        binding.mRow.setTag("1");
                        venueData.getTables().get(getAdapterPosition()).isChecked = "1";
                        if (type.equalsIgnoreCase("detail"))
                            VenueDetailsFragment.setDrinksData(venueData, "table");
                        binding.mAdd.setText("Added");
                        binding.mAdd.setBackgroundResource(R.drawable.menu_bg);
                        binding.mAdd.setTextColor(mContext.getResources().getColor(R.color.white));
                    } else {
                        binding.mRow.setTag("0");
                        venueData.getTables().get(getAdapterPosition()).isChecked = "0";
                        if (type.equalsIgnoreCase("detail"))
                            VenueDetailsFragment.setDrinksData(venueData, "table");
                        binding.mAdd.setText("Add");
                        binding.mAdd.setBackgroundResource(R.drawable.purple_outline);
                        binding.mAdd.setTextColor(mContext.getResources().getColor(R.color.colorPurple));
                    }
                });
            }
        }
    }

    public class SectionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private Context mContext;
        private List<BashDetailsPOJO.Section> list;

        public SectionAdapter(Context mContext, List<BashDetailsPOJO.Section> list) {
            this.mContext = mContext;
            this.list = list;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new SectionAdapter.Holder(TableMenuRowBinding.inflate(LayoutInflater.from(mContext), parent, false));
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

            TableMenuRowBinding binding;

            Holder(TableMenuRowBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
            }

            public void bind() {
                BashDetailsPOJO.Section item = list.get(getAdapterPosition());
                if (item.isChecked != null)
                    if (item.isChecked.equalsIgnoreCase("1")) {
                        binding.mRow.setTag("1");
                        data.getSections().get(getAdapterPosition()).isChecked = "1";
                        binding.mAdd.setText("Added");
                        binding.mAdd.setBackgroundResource(R.drawable.menu_bg);
                        binding.mAdd.setTextColor(mContext.getResources().getColor(R.color.white));
                    }
                binding.mName.setText(item.getName());
                binding.mGuest.setText(item.getGuest() + " Guest");
                binding.mDescription.setText(item.description);
                binding.mTableId.setText("Section No. " + item.getSlug());
                if (item.price_request.equalsIgnoreCase("1")) {
                    binding.mRequestPricing.setVisibility(View.VISIBLE);
                    binding.mPrice.setVisibility(View.GONE);
                    binding.mAdd.setVisibility(View.GONE);
                } else {
                    binding.mPrice.setText("$" + item.getPrice());
                    binding.mRequestPricing.setVisibility(View.GONE);
                    binding.mPrice.setVisibility(View.VISIBLE);
                }
                binding.mRequestPricing.setOnClickListener(v -> {
                    RequestPricingFragment fragment = new RequestPricingFragment();
                    fragment.setData(data, "section", getAdapterPosition());
                    Utils.goToFragment(mContext, fragment, R.id.fragment_container);
                });
                binding.mAdd.setOnClickListener(v -> {
                    if (binding.mRow.getTag().toString().equalsIgnoreCase("0")) {
                        binding.mRow.setTag("1");
                        data.getSections().get(getAdapterPosition()).isChecked = "1";
                        if (type.equalsIgnoreCase("detail"))
                            BashDetailsWebFragment.setDrinksData(data, "section");
                        binding.mAdd.setText("Added");
                        binding.mAdd.setBackgroundResource(R.drawable.menu_bg);
                        binding.mAdd.setTextColor(mContext.getResources().getColor(R.color.white));
                    } else {
                        binding.mRow.setTag("0");
                        data.getSections().get(getAdapterPosition()).isChecked = "0";
                        if (type.equalsIgnoreCase("detail"))
                            BashDetailsWebFragment.setDrinksData(data, "section");
                        binding.mAdd.setText("Add");
                        binding.mAdd.setBackgroundResource(R.drawable.purple_outline);
                        binding.mAdd.setTextColor(mContext.getResources().getColor(R.color.colorPurple));
                    }
                });
            }
        }
    }

    public class VenueSectionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private Context mContext;
        private List<VenueListPOJO.Section> list;

        public VenueSectionAdapter(Context mContext, List<VenueListPOJO.Section> list) {
            this.mContext = mContext;
            this.list = list;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new Holder(TableMenuRowBinding.inflate(LayoutInflater.from(mContext), parent, false));
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

            TableMenuRowBinding binding;

            Holder(TableMenuRowBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
            }

            public void bind() {
                VenueListPOJO.Section item = list.get(getAdapterPosition());
                if (item.isChecked != null)
                    if (item.isChecked.equalsIgnoreCase("1")) {
                        binding.mRow.setTag("1");
                        venueData.getSections().get(getAdapterPosition()).isChecked = "1";
                        binding.mAdd.setText("Added");
                        binding.mAdd.setBackgroundResource(R.drawable.menu_bg);
                        binding.mAdd.setTextColor(mContext.getResources().getColor(R.color.white));
                    }
                binding.mName.setText(item.getName());
                binding.mGuest.setText(item.getGuest() + " Guest");
                binding.mDescription.setText("");
                binding.mTableId.setText("Section No. " + item.getSlug());
                if (item.price_request != null)
                    if (item.price_request.equalsIgnoreCase("1")) {
                        binding.mRequestPricing.setVisibility(View.VISIBLE);
                        binding.mPrice.setVisibility(View.GONE);
                        binding.mAdd.setVisibility(View.GONE);
                    } else {
                        binding.mPrice.setText("$" + item.getPrice());
                        binding.mRequestPricing.setVisibility(View.GONE);
                        binding.mPrice.setVisibility(View.VISIBLE);
                    }
                else {
                    binding.mPrice.setText("$" + item.getPrice());
                    binding.mRequestPricing.setVisibility(View.GONE);
                    binding.mPrice.setVisibility(View.VISIBLE);
                }
                binding.mRequestPricing.setOnClickListener(v -> {
                    RequestPricingFragment fragment = new RequestPricingFragment();
                    fragment.setVenueData(venueData, "section", getAdapterPosition());
                    Utils.goToFragment(mContext, fragment, R.id.fragment_container);
                });
                binding.mAdd.setOnClickListener(v -> {
                    if (binding.mRow.getTag().toString().equalsIgnoreCase("0")) {
                        binding.mRow.setTag("1");
                        venueData.getSections().get(getAdapterPosition()).isChecked = "1";
                        if (type.equalsIgnoreCase("detail"))
                            VenueDetailsFragment.setDrinksData(venueData, "section");
                        binding.mAdd.setText("Added");
                        binding.mAdd.setBackgroundResource(R.drawable.menu_bg);
                        binding.mAdd.setTextColor(mContext.getResources().getColor(R.color.white));
                    } else {
                        binding.mRow.setTag("0");
                        venueData.getSections().get(getAdapterPosition()).isChecked = "0";
                        if (type.equalsIgnoreCase("detail"))
                            VenueDetailsFragment.setDrinksData(venueData, "section");
                        binding.mAdd.setText("Add");
                        binding.mAdd.setBackgroundResource(R.drawable.purple_outline);
                        binding.mAdd.setTextColor(mContext.getResources().getColor(R.color.colorPurple));
                    }
                });
            }
        }
    }
}