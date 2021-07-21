package com.orem.bashhub.fragment;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.orem.bashhub.R;
import com.orem.bashhub.activity.MainActivity;
import com.orem.bashhub.data.BashDetailsPOJO;
import com.orem.bashhub.data.VenueListPOJO;
import com.orem.bashhub.databinding.DrinkMenuRowBinding;
import com.orem.bashhub.databinding.FragmentDrinkBottleListBinding;
import com.orem.bashhub.databinding.ItemSectionMenuRowBinding;
import com.orem.bashhub.utils.Const;
import com.orem.bashhub.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class DrinkBottleListFragment extends BaseFragment {
    ArrayList<HashMap<String, String>> drinkList;
    ArrayList<HashMap<String, String>> bottleList;
    FragmentDrinkBottleListBinding binding;
    BashDetailsPOJO data;
    VenueListPOJO.Venue venueData;
    String from;
    String type;
    int itemSectionIndex;
    ArrayList<HashMap<String, String>> tableList;
    ArrayList<HashMap<String, String>> sectionList;
    ArrayList<HashMap<String, String>> serviceList;
    ArrayList<HashMap<String, String>> itemSectionList;
    private ArrayList<HashMap<String, String>> ticketList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_drink_bottle_list, container, false);
        init();
        return binding.getRoot();
    }

    private void init() {
        binding.mAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type.equalsIgnoreCase("detail"))
                    ((MainActivity) mContext).getSupportFragmentManager().popBackStack();
                else {
                    if (data != null)
                        setDrinksData(from);
                    else
                        setVenueDrinksData(from);
                }
            }
        });
        if (from.equalsIgnoreCase("drink")) {
            binding.mTitle.setText("Drinks Menu");
            if (data != null)
                binding.mRecyclerView.setAdapter(new DrinkAdapter(mContext, data.getDrinks()));
            else
                binding.mRecyclerView.setAdapter(new VenueDrinkAdapter(mContext, venueData.getDrinks()));
        } else if (from.equalsIgnoreCase("service")) {
            binding.mTitle.setText("Other Items/Services Menu");
            if (data != null)
                binding.mRecyclerView.setAdapter(new ProductAdapter(mContext, data.getProducts()));
            else
                binding.mRecyclerView.setAdapter(new VenueProductAdapter(mContext, venueData.getProducts()));
        } else if (from.equalsIgnoreCase("itemSection")) {
            binding.mTitle.setText("Other Items/Services Menu");
            binding.mTT.setText("Item Name");
            if (data != null)
                binding.mRecyclerView.setAdapter(new ProductAdapter(mContext, data.getProducts()));
            else {
                binding.mTitle.setText(venueData.getItemSections().get(itemSectionIndex).getName());
                binding.mRecyclerView.setAdapter(new VenueItemSectionAdapter(mContext, venueData.getItemSections().get(itemSectionIndex).getItems()));
                binding.mTitle.setText(venueData.getItemSections().get(itemSectionIndex).getName());

            }
        } else {
            if (data != null)
                binding.mRecyclerView.setAdapter(new BottleAdapter(mContext, data.getBottles()));
            else
                binding.mRecyclerView.setAdapter(new VenueBottleAdapter(mContext, venueData.getBottles()));

            binding.mTitle.setText("Bottles Menu");
        }
        binding.mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) mContext).getSupportFragmentManager().popBackStack();
            }
        });
    }

    public void setDrinksData(String from) {
        if (from.equalsIgnoreCase("drink")) {
            drinkList = new ArrayList<>();
            bottleList = new ArrayList<>();
            tableList = new ArrayList<>();
            sectionList = new ArrayList<>();
            serviceList = new ArrayList<>();
            for (int i = 0; i < data.getDrinks().size(); i++) {
                HashMap<String, String> map = new HashMap<>();
                if (data.getDrinks().get(i).isChecked != null)
                    if (data.getDrinks().get(i).isChecked.equalsIgnoreCase("1")) {
                        map.put("id", data.getDrinks().get(i).getId());
                        map.put("price", data.getDrinks().get(i).getPrice());
                        map.put("name", data.getDrinks().get(i).getName());
                        map.put("qty", data.getDrinks().get(i).qty);
                        drinkList.add(map);
                    }
            }
            if (drinkList.size() > 0) {
                ConfirmOrderFragment fragment = new ConfirmOrderFragment();
                fragment.setData(data, ticketList, drinkList, bottleList, tableList, sectionList, serviceList, "4");
                Utils.goToFragment(mContext, fragment, R.id.fragment_container);
                ((MainActivity) mContext).binding.fragmentContainer.setVisibility(View.VISIBLE);
            } else {
                Toast.makeText(mContext, "Please select drink", Toast.LENGTH_SHORT).show();
            }
        } else if (from.equalsIgnoreCase("bottle")) {
            drinkList = new ArrayList<>();
            bottleList = new ArrayList<>();
            tableList = new ArrayList<>();
            sectionList = new ArrayList<>();
            serviceList = new ArrayList<>();
            for (int i = 0; i < data.getBottles().size(); i++) {
                HashMap<String, String> map = new HashMap<>();
                if (data.getBottles().get(i).isChecked != null)
                    if (data.getBottles().get(i).isChecked.equalsIgnoreCase("1")) {
                        map.put("id", data.getBottles().get(i).getId());
                        map.put("price", data.getBottles().get(i).getAmount());
                        map.put("name", data.getBottles().get(i).getName());
                        map.put("qty", data.getBottles().get(i).qty);
                        map.put("quantity", data.getBottles().get(i).getQuantity());
                        bottleList.add(map);
                    }
            }
            if (bottleList.size() > 0) {
                ConfirmOrderFragment fragment = new ConfirmOrderFragment();
                fragment.setData(data, ticketList, drinkList, bottleList, tableList, sectionList, serviceList, "5");
                Utils.goToFragment(mContext, fragment, R.id.fragment_container);
                ((MainActivity) mContext).binding.fragmentContainer.setVisibility(View.VISIBLE);
            } else {
                Toast.makeText(mContext, "Please select bottle", Toast.LENGTH_SHORT).show();
            }
        } else if (from.equalsIgnoreCase("service")) {
            drinkList = new ArrayList<>();
            bottleList = new ArrayList<>();
            tableList = new ArrayList<>();
            sectionList = new ArrayList<>();
            serviceList = new ArrayList<>();
            for (int i = 0; i < data.getProducts().size(); i++) {
                HashMap<String, String> map = new HashMap<>();
                if (data.getProducts().get(i).isChecked != null)
                    if (data.getProducts().get(i).isChecked.equalsIgnoreCase("1")) {
                        map.put("id", data.getProducts().get(i).getId() + "");
                        map.put("price", data.getProducts().get(i).getPrice());
                        map.put("name", data.getProducts().get(i).getName());
                        map.put("qty", data.getProducts().get(i).qty);
                        map.put("quantity", "");
                        serviceList.add(map);
                    }
            }
            if (serviceList.size() > 0) {
                ConfirmOrderFragment fragment = new ConfirmOrderFragment();
                fragment.setData(data, ticketList, drinkList, bottleList, tableList, sectionList, serviceList, "8");
                Utils.goToFragment(mContext, fragment, R.id.fragment_container);
                ((MainActivity) mContext).binding.fragmentContainer.setVisibility(View.VISIBLE);
            } else {
                Toast.makeText(mContext, "Please select any item/service", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void setVenueDrinksData(String from) {
        if (from.equalsIgnoreCase("drink")) {
            drinkList = new ArrayList<>();
            bottleList = new ArrayList<>();
            tableList = new ArrayList<>();
            sectionList = new ArrayList<>();
            serviceList = new ArrayList<>();
            itemSectionList = new ArrayList<>();
            for (int i = 0; i < venueData.getDrinks().size(); i++) {
                HashMap<String, String> map = new HashMap<>();
                if (venueData.getDrinks().get(i).isChecked != null)
                    if (venueData.getDrinks().get(i).isChecked.equalsIgnoreCase("1")) {
                        map.put("id", venueData.getDrinks().get(i).getId().toString());
                        map.put("price", venueData.getDrinks().get(i).getPrice().toString());
                        map.put("name", venueData.getDrinks().get(i).getName());
                        map.put("qty", venueData.getDrinks().get(i).qty);
                        drinkList.add(map);
                    }
            }
            if (drinkList.size() > 0) {
                ConfirmOrderFragment fragment = new ConfirmOrderFragment();
                fragment.setVenueData(venueData, venueData.getItemSections(), ticketList, drinkList, bottleList, tableList, sectionList, serviceList, "4");
                Utils.goToFragment(mContext, fragment, R.id.fragment_container);
                ((MainActivity) mContext).binding.fragmentContainer.setVisibility(View.VISIBLE);
            } else {
                Toast.makeText(mContext, "Please select drink", Toast.LENGTH_SHORT).show();
            }
        } else if (from.equalsIgnoreCase("bottle")) {
            drinkList = new ArrayList<>();
            bottleList = new ArrayList<>();
            tableList = new ArrayList<>();
            sectionList = new ArrayList<>();
            itemSectionList = new ArrayList<>();
            serviceList = new ArrayList<>();
            for (int i = 0; i < venueData.getBottles().size(); i++) {
                HashMap<String, String> map = new HashMap<>();
                if (venueData.getBottles().get(i).isChecked != null)
                    if (venueData.getBottles().get(i).isChecked.equalsIgnoreCase("1")) {
                        map.put("id", venueData.getBottles().get(i).getId().toString());
                        map.put("price", venueData.getBottles().get(i).getAmount());
                        map.put("name", venueData.getBottles().get(i).getName());
                        map.put("qty", venueData.getBottles().get(i).qty);
                        map.put("quantity", venueData.getBottles().get(i).getQuantity().toString());
                        bottleList.add(map);
                    }
            }
            if (bottleList.size() > 0) {
                ConfirmOrderFragment fragment = new ConfirmOrderFragment();
                fragment.setVenueData(venueData, venueData.getItemSections(), ticketList, drinkList, bottleList, tableList, sectionList, serviceList, "5");
                Utils.goToFragment(mContext, fragment, R.id.fragment_container);
                ((MainActivity) mContext).binding.fragmentContainer.setVisibility(View.VISIBLE);
            } else {
                Toast.makeText(mContext, "Please select bottle", Toast.LENGTH_SHORT).show();
            }
        } else if (from.equalsIgnoreCase("service")) {
            drinkList = new ArrayList<>();
            bottleList = new ArrayList<>();
            tableList = new ArrayList<>();
            sectionList = new ArrayList<>();
            serviceList = new ArrayList<>();
            itemSectionList = new ArrayList<>();
            for (int i = 0; i < venueData.getProducts().size(); i++) {
                HashMap<String, String> map = new HashMap<>();
                if (venueData.getProducts().get(i).isChecked != null)
                    if (venueData.getProducts().get(i).isChecked.equalsIgnoreCase("1")) {
                        map.put("id", venueData.getProducts().get(i).getId() + "");
                        map.put("price", venueData.getProducts().get(i).getPrice().toString());
                        map.put("name", venueData.getProducts().get(i).getName());
                        map.put("qty", venueData.getProducts().get(i).qty);
                        map.put("quantity", "");
                        serviceList.add(map);
                    }
            }
            if (serviceList.size() > 0) {
                ConfirmOrderFragment fragment = new ConfirmOrderFragment();
                fragment.setVenueData(venueData, venueData.getItemSections(), ticketList, drinkList, bottleList, tableList, sectionList, serviceList, "8");
                Utils.goToFragment(mContext, fragment, R.id.fragment_container);
                ((MainActivity) mContext).binding.fragmentContainer.setVisibility(View.VISIBLE);
            } else {
                Toast.makeText(mContext, "Please select any item/service", Toast.LENGTH_SHORT).show();
            }
        } else if (from.equalsIgnoreCase("itemSection")) {
            drinkList = new ArrayList<>();
            bottleList = new ArrayList<>();
            tableList = new ArrayList<>();
            sectionList = new ArrayList<>();
            serviceList = new ArrayList<>();
            itemSectionList = new ArrayList<>();
            for (int i = 0; i < venueData.getItemSections().get(itemSectionIndex).getItems().size(); i++) {
                HashMap<String, String> map = new HashMap<>();
                if (venueData.getItemSections().get(itemSectionIndex).getItems().get(i).isChecked != null)
                    if (venueData.getItemSections().get(itemSectionIndex).getItems().get(i).isChecked.equalsIgnoreCase("1")) {
                        map.put("id", venueData.getItemSections().get(itemSectionIndex).getItems().get(i).getId() + "");
                        map.put("price", venueData.getItemSections().get(itemSectionIndex).getItems().get(i).getPrice().toString());
                        map.put("name", venueData.getItemSections().get(itemSectionIndex).getItems().get(i).getName());
                        map.put("qty", venueData.getItemSections().get(itemSectionIndex).getItems().get(i).qty);
                        map.put("quantity", "");
                        itemSectionList.add(map);
                    }
            }
            if (itemSectionList.size() > 0) {
                ConfirmOrderFragment fragment = new ConfirmOrderFragment();
                fragment.setVenueData(venueData, venueData.getItemSections(), ticketList, drinkList, bottleList, tableList, sectionList, serviceList, "9");
                Utils.goToFragment(mContext, fragment, R.id.fragment_container);
                ((MainActivity) mContext).binding.fragmentContainer.setVisibility(View.VISIBLE);
            } else {
                Toast.makeText(mContext, "Please select any item/service", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void setData(BashDetailsPOJO data, String from, String type) {
        this.data = data;
        this.from = from;
        this.type = type;
    }

    public void setVenueData(VenueListPOJO.Venue data, String from, String type, int itemSectionIndex) {
        this.venueData = data;
        this.from = from;
        this.type = type;
        this.itemSectionIndex = itemSectionIndex;
    }

    public class DrinkAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private Context mContext;
        private List<BashDetailsPOJO.Drink> list;

        public DrinkAdapter(Context mContext, List<BashDetailsPOJO.Drink> list) {
            this.mContext = mContext;
            this.list = list;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new Holder(DrinkMenuRowBinding.inflate(LayoutInflater.from(mContext), parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            Holder h = (DrinkAdapter.Holder) holder;
            h.bind();
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public void optionMenu(int position, AppCompatTextView mDelete, AppCompatCheckBox mCheckBox, LinearLayout mRow) {
            final PopupMenu popup = new PopupMenu(mContext, mDelete);
            ArrayList rating = new ArrayList();
            rating.add("1");
            rating.add("2");
            rating.add("3");
            rating.add("4");
            rating.add("5");
            rating.add("6");
            rating.add("7");
            rating.add("8");
            rating.add("9");
            rating.add("10");
            for (int i = 0; i < rating.size(); i++) {
                popup.getMenu().add(Menu.NONE, (i + 1), Menu.NONE, rating.get(i).toString());
            }
            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    mDelete.setText(item.getTitle());
                    if (mRow.getTag().toString().equalsIgnoreCase("1")) {
                        mCheckBox.setChecked(true);
                        mRow.setTag("1");
                        data.getDrinks().get(position).isChecked = "1";
                        data.getDrinks().get(position).qty = mDelete.getText().toString();
                        if (type.equalsIgnoreCase("detail"))
                            BashDetailsWebFragment.setDrinksData(data, "drink");
                    }
                    return false;
                }
            });
            popup.show();
        }

        class Holder extends RecyclerView.ViewHolder {

            DrinkMenuRowBinding binding;

            Holder(DrinkMenuRowBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
            }

            public void bind() {
                BashDetailsPOJO.Drink item = list.get(getAdapterPosition());
                binding.mName.setText(item.getName());
                if (item.getPrice() == null || item.getPrice().equalsIgnoreCase("")) {
                    binding.mCheckBox.setVisibility(View.GONE);
                    binding.mCost.setVisibility(View.INVISIBLE);
                    binding.mQuantity.setVisibility(View.INVISIBLE);
                    binding.mRequestPricing.setVisibility(View.VISIBLE);
                    binding.mRequestPricing.setOnClickListener(v -> {
                    });
                } else {
                    binding.mCost.setText("$" + item.getPrice());
                    if (data.getDrinks().get(getAdapterPosition()).isChecked != null) {
                        if (data.getDrinks().get(getAdapterPosition()).isChecked.equalsIgnoreCase("1")) {
                            binding.mCheckBox.setChecked(true);
                            binding.mRow.setTag("1");
                            binding.mQuantity.setText(data.getDrinks().get(getAdapterPosition()).qty);
                        } else {
                            binding.mRow.setTag("0");
                            binding.mCheckBox.setChecked(false);
                            data.getDrinks().get(getAdapterPosition()).isChecked = "0";
                        }
                    } else {
                        binding.mRow.setTag("0");
                        binding.mCheckBox.setChecked(false);
                        data.getDrinks().get(getAdapterPosition()).isChecked = "0";
                    }
                    binding.mQuantity.setOnClickListener(v -> {
                        optionMenu(getAdapterPosition(), binding.mQuantity, binding.mCheckBox, binding.mRow);
                    });
                    binding.mCheckBox.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            binding.mRow.performClick();
                        }
                    });
                    binding.mRow.setOnClickListener(v -> {
                        if (binding.mRow.getTag().toString().equalsIgnoreCase("0")) {
                            binding.mCheckBox.setChecked(true);
                            binding.mRow.setTag("1");
                            data.getDrinks().get(getAdapterPosition()).isChecked = "1";
                            data.getDrinks().get(getAdapterPosition()).qty = binding.mQuantity.getText().toString();
                            if (type.equalsIgnoreCase("detail"))
                                BashDetailsWebFragment.setDrinksData(data, "drink");
                        } else {
                            binding.mRow.setTag("0");
                            binding.mCheckBox.setChecked(false);
                            data.getDrinks().get(getAdapterPosition()).isChecked = "0";
                            data.getDrinks().get(getAdapterPosition()).qty = "0";
                            if (type.equalsIgnoreCase("detail"))
                                BashDetailsWebFragment.setDrinksData(data, "drink");
                        }
                    });
                }

            }
        }

    }

    public class VenueDrinkAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private Context mContext;
        private List<VenueListPOJO.Drink> list;

        public VenueDrinkAdapter(Context mContext, List<VenueListPOJO.Drink> list) {
            this.mContext = mContext;
            this.list = list;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new VenueDrinkAdapter.Holder(DrinkMenuRowBinding.inflate(LayoutInflater.from(mContext), parent, false));
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

        public void optionMenu(int position, AppCompatTextView mDelete, AppCompatCheckBox mCheckBox, LinearLayout mRow) {
            final PopupMenu popup = new PopupMenu(mContext, mDelete);
            ArrayList rating = new ArrayList();
            rating.add("1");
            rating.add("2");
            rating.add("3");
            rating.add("4");
            rating.add("5");
            rating.add("6");
            rating.add("7");
            rating.add("8");
            rating.add("9");
            rating.add("10");
            for (int i = 0; i < rating.size(); i++) {
                popup.getMenu().add(Menu.NONE, (i + 1), Menu.NONE, rating.get(i).toString());
            }
            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    mDelete.setText(item.getTitle());
                    if (mRow.getTag().toString().equalsIgnoreCase("1")) {
                        mCheckBox.setChecked(true);
                        mRow.setTag("1");
                        venueData.getDrinks().get(position).isChecked = "1";
                        venueData.getDrinks().get(position).qty = mDelete.getText().toString();

                        if (type.equalsIgnoreCase("detail"))
                            VenueDetailsFragment.setDrinksData(venueData, "drink");
                    }
                    return false;
                }
            });
            popup.show();
        }

        class Holder extends RecyclerView.ViewHolder {

            DrinkMenuRowBinding binding;

            Holder(DrinkMenuRowBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
            }

            public void bind() {
                VenueListPOJO.Drink item = list.get(getAdapterPosition());
                binding.mName.setText(item.getName());
                if (item.getPrice() == null || item.getPrice().toString().equalsIgnoreCase("")) {
                    binding.mCheckBox.setVisibility(View.GONE);
                    binding.mCost.setVisibility(View.INVISIBLE);
                    binding.mQuantity.setVisibility(View.INVISIBLE);
                    binding.mRequestPricing.setVisibility(View.VISIBLE);
                    binding.mRequestPricing.setOnClickListener(v -> {
                    });
                } else {
                    binding.mCost.setText("$" + item.getPrice());
                    if (venueData.getDrinks().get(getAdapterPosition()).isChecked != null) {
                        if (venueData.getDrinks().get(getAdapterPosition()).isChecked.equalsIgnoreCase("1")) {
                            binding.mCheckBox.setChecked(true);
                            binding.mRow.setTag("1");
                            binding.mQuantity.setText(venueData.getDrinks().get(getAdapterPosition()).qty);
                        } else {
                            binding.mRow.setTag("0");
                            binding.mCheckBox.setChecked(false);
                            venueData.getDrinks().get(getAdapterPosition()).isChecked = "0";
                        }
                    } else {
                        binding.mRow.setTag("0");
                        binding.mCheckBox.setChecked(false);
                        venueData.getDrinks().get(getAdapterPosition()).isChecked = "0";
                    }
                    binding.mQuantity.setOnClickListener(v -> {
                        optionMenu(getAdapterPosition(), binding.mQuantity, binding.mCheckBox, binding.mRow);
                    });
                    binding.mCheckBox.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            binding.mRow.performClick();
                        }
                    });
                    binding.mRow.setOnClickListener(v -> {
                        if (binding.mRow.getTag().toString().equalsIgnoreCase("0")) {
                            binding.mCheckBox.setChecked(true);
                            binding.mRow.setTag("1");
                            venueData.getDrinks().get(getAdapterPosition()).isChecked = "1";
                            venueData.getDrinks().get(getAdapterPosition()).qty = binding.mQuantity.getText().toString();
                            if (type.equalsIgnoreCase("detail"))
                                VenueDetailsFragment.setDrinksData(venueData, "drink");
                        } else {
                            binding.mRow.setTag("0");
                            binding.mCheckBox.setChecked(false);
                            venueData.getDrinks().get(getAdapterPosition()).isChecked = "0";
                            venueData.getDrinks().get(getAdapterPosition()).qty = "0";
                            if (type.equalsIgnoreCase("detail"))
                                VenueDetailsFragment.setDrinksData(venueData, "drink");
                        }
                    });
                }

            }
        }

    }

    public class BottleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private Context mContext;
        private List<BashDetailsPOJO.Bottle> list;


        public BottleAdapter(Context mContext, List<BashDetailsPOJO.Bottle> list) {
            this.mContext = mContext;
            this.list = list;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new Holder(DrinkMenuRowBinding.inflate(LayoutInflater.from(mContext), parent, false));
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

        public void optionMenu(int position, AppCompatTextView mDelete, AppCompatCheckBox mCheckBox, LinearLayout mRow) {
            final PopupMenu popup = new PopupMenu(mContext, mDelete);
            ArrayList rating = new ArrayList();
            for (int i = 0; i < Integer.parseInt(list.get(position).getQuantity()); i++) {
                rating.add(i + 1);
            }

            if (rating.size() == 0) {
                Toast.makeText(mContext, "All bottles has been sold", Toast.LENGTH_SHORT).show();
            }
            for (int i = 0; i < rating.size(); i++) {
                popup.getMenu().add(Menu.NONE, (i + 1), Menu.NONE, rating.get(i).toString());
            }
            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    mDelete.setText(item.getTitle());
                    if (mRow.getTag().toString().equalsIgnoreCase("1")) {
                        mCheckBox.setChecked(true);
                        mRow.setTag("1");
                        data.getBottles().get(position).isChecked = "1";
                        data.getBottles().get(position).qty = mDelete.getText().toString();
                        if (type.equalsIgnoreCase("detail"))
                            BashDetailsWebFragment.setDrinksData(data, "bottle");
                    }
                    return false;
                }
            });
            popup.show();
        }

        class Holder extends RecyclerView.ViewHolder {

            DrinkMenuRowBinding binding;

            Holder(DrinkMenuRowBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
            }

            public void bind() {
                BashDetailsPOJO.Bottle item = list.get(getAdapterPosition());
                binding.mName.setText(item.getName());
                if (item.getAmount() == null || item.getAmount().equalsIgnoreCase("")) {
                    binding.mCheckBox.setVisibility(View.INVISIBLE);
                    binding.mCost.setVisibility(View.INVISIBLE);
                    binding.mQuantity.setVisibility(View.INVISIBLE);
                    binding.mRequestPricing.setVisibility(View.VISIBLE);

                    binding.mRequestPricing.setOnClickListener(v -> {
                        RequestPricingFragment fragment = new RequestPricingFragment();
                        fragment.setData(data, "bottle", getAdapterPosition());
                        Utils.goToFragment(mContext, fragment, R.id.fragment_container);
                    });
                } else {
                    if (list.get(getAdapterPosition()).getQuantity().equalsIgnoreCase("0")) {
                        binding.mQuantity.setText("0");
                    }
                    binding.mCost.setText("$" + item.getAmount());
                    if (data.getBottles().get(getAdapterPosition()).isChecked != null) {
                        if (data.getBottles().get(getAdapterPosition()).isChecked.equalsIgnoreCase("1")) {
                            binding.mCheckBox.setChecked(true);
                            binding.mRow.setTag("1");
                            binding.mQuantity.setText(data.getBottles().get(getAdapterPosition()).qty);
                        } else {
                            binding.mRow.setTag("0");
                            binding.mCheckBox.setChecked(false);
                            data.getBottles().get(getAdapterPosition()).isChecked = "0";
                        }
                    } else {
                        binding.mRow.setTag("0");
                        binding.mCheckBox.setChecked(false);
                        data.getBottles().get(getAdapterPosition()).isChecked = "0";
                    }
                    binding.mQuantity.setOnClickListener(v -> {
                        optionMenu(getAdapterPosition(), binding.mQuantity, binding.mCheckBox, binding.mRow);
                    });
                    binding.mCheckBox.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            binding.mRow.performClick();
                        }
                    });
                    binding.mRow.setOnClickListener(v -> {
                        if (binding.mRow.getTag().toString().equalsIgnoreCase("0")) {
                            if (list.get(getAdapterPosition()).getQuantity().equalsIgnoreCase("0")) {
                                Toast.makeText(mContext, "All bottles has been sold", Toast.LENGTH_SHORT).show();
                            } else {
                                binding.mCheckBox.setChecked(true);
                                binding.mRow.setTag("1");
                                data.getBottles().get(getAdapterPosition()).isChecked = "1";
                                data.getBottles().get(getAdapterPosition()).qty = binding.mQuantity.getText().toString();
                                if (type.equalsIgnoreCase("detail"))
                                    BashDetailsWebFragment.setDrinksData(data, "bottle");

                            }
                        } else {
                            binding.mRow.setTag("0");
                            binding.mCheckBox.setChecked(false);
                            data.getBottles().get(getAdapterPosition()).isChecked = "0";
                            data.getBottles().get(getAdapterPosition()).qty = "0";
                            if (type.equalsIgnoreCase("detail"))
                                BashDetailsWebFragment.setDrinksData(data, "bottle");
                        }
                    });

                }
            }
        }
    }

    public class VenueBottleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private Context mContext;
        private List<VenueListPOJO.Bottle> list;


        public VenueBottleAdapter(Context mContext, List<VenueListPOJO.Bottle> list) {
            this.mContext = mContext;
            this.list = list;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new Holder(DrinkMenuRowBinding.inflate(LayoutInflater.from(mContext), parent, false));
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

        public void optionMenu(int position, AppCompatTextView mDelete, AppCompatCheckBox mCheckBox, LinearLayout mRow) {
            final PopupMenu popup = new PopupMenu(mContext, mDelete);
            ArrayList rating = new ArrayList();
            for (int i = 0; i < list.get(position).getQuantity(); i++) {
                rating.add(i + 1);
            }

            if (rating.size() == 0) {
                Toast.makeText(mContext, "All bottles has been sold", Toast.LENGTH_SHORT).show();
            }
            for (int i = 0; i < rating.size(); i++) {
                popup.getMenu().add(Menu.NONE, (i + 1), Menu.NONE, rating.get(i).toString());
            }
            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    mDelete.setText(item.getTitle());
                    if (mRow.getTag().toString().equalsIgnoreCase("1")) {
                        mCheckBox.setChecked(true);
                        mRow.setTag("1");
                        venueData.getBottles().get(position).isChecked = "1";
                        venueData.getBottles().get(position).qty = mDelete.getText().toString();
                        if (type.equalsIgnoreCase("detail"))
                            VenueDetailsFragment.setDrinksData(venueData, "bottle");
                    }
                    return false;
                }
            });
            popup.show();
        }

        class Holder extends RecyclerView.ViewHolder {

            DrinkMenuRowBinding binding;

            Holder(DrinkMenuRowBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
            }

            public void bind() {
                VenueListPOJO.Bottle item = list.get(getAdapterPosition());
                binding.mName.setText(item.getName());
                if (item.getAmount() == null || item.getAmount().equalsIgnoreCase("")) {
                    binding.mCheckBox.setVisibility(View.INVISIBLE);
                    binding.mCost.setVisibility(View.INVISIBLE);
                    binding.mQuantity.setVisibility(View.INVISIBLE);
                    binding.mRequestPricing.setVisibility(View.VISIBLE);

                    binding.mRequestPricing.setOnClickListener(v -> {
                        RequestPricingFragment fragment = new RequestPricingFragment();
                        fragment.setVenueData(venueData, "bottle", getAdapterPosition());
                        Utils.goToFragment(mContext, fragment, R.id.fragment_container);
                    });
                } else {
                    if (list.get(getAdapterPosition()).getQuantity().toString().equalsIgnoreCase("0")) {
                        binding.mQuantity.setText("0");
                    }
                    binding.mCost.setText("$" + item.getAmount());
                    if (venueData.getBottles().get(getAdapterPosition()).isChecked != null) {
                        if (venueData.getBottles().get(getAdapterPosition()).isChecked.equalsIgnoreCase("1")) {
                            binding.mCheckBox.setChecked(true);
                            binding.mRow.setTag("1");
                            binding.mQuantity.setText(venueData.getBottles().get(getAdapterPosition()).qty);
                        } else {
                            binding.mRow.setTag("0");
                            binding.mCheckBox.setChecked(false);
                            venueData.getBottles().get(getAdapterPosition()).isChecked = "0";
                        }
                    } else {
                        binding.mRow.setTag("0");
                        binding.mCheckBox.setChecked(false);
                        venueData.getBottles().get(getAdapterPosition()).isChecked = "0";
                    }
                    binding.mQuantity.setOnClickListener(v -> {
                        optionMenu(getAdapterPosition(), binding.mQuantity, binding.mCheckBox, binding.mRow);
                    });
                    binding.mCheckBox.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            binding.mRow.performClick();
                        }
                    });
                    binding.mRow.setOnClickListener(v -> {
                        if (binding.mRow.getTag().toString().equalsIgnoreCase("0")) {
                            if (list.get(getAdapterPosition()).getQuantity().toString().equalsIgnoreCase("0")) {
                                Toast.makeText(mContext, "All bottles has been sold", Toast.LENGTH_SHORT).show();
                            } else {
                                binding.mCheckBox.setChecked(true);
                                binding.mRow.setTag("1");
                                venueData.getBottles().get(getAdapterPosition()).isChecked = "1";
                                venueData.getBottles().get(getAdapterPosition()).qty = binding.mQuantity.getText().toString();
                                if (type.equalsIgnoreCase("detail"))
                                    VenueDetailsFragment.setDrinksData(venueData, "bottle");

                            }
                        } else {
                            binding.mRow.setTag("0");
                            binding.mCheckBox.setChecked(false);
                            venueData.getBottles().get(getAdapterPosition()).isChecked = "0";
                            venueData.getBottles().get(getAdapterPosition()).qty = "0";
                            if (type.equalsIgnoreCase("detail"))
                                VenueDetailsFragment.setDrinksData(venueData, "bottle");
                        }
                    });

                }
            }
        }
    }

    public class ProductAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private Context mContext;
        private List<BashDetailsPOJO.Product> list;


        public ProductAdapter(Context mContext, List<BashDetailsPOJO.Product> list) {
            this.mContext = mContext;
            this.list = list;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new Holder(DrinkMenuRowBinding.inflate(LayoutInflater.from(mContext), parent, false));
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

        public void optionMenu(int position, AppCompatTextView mDelete, AppCompatCheckBox mCheckBox, LinearLayout mRow) {
            final PopupMenu popup = new PopupMenu(mContext, mDelete);
            ArrayList rating = new ArrayList();
            for (int i = 0; i < 10; i++) {
                rating.add(i + 1);
            }


            for (int i = 0; i < rating.size(); i++) {
                popup.getMenu().add(Menu.NONE, (i + 1), Menu.NONE, rating.get(i).toString());
            }
            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    mDelete.setText(item.getTitle());
                    if (mRow.getTag().toString().equalsIgnoreCase("1")) {
                        mCheckBox.setChecked(true);
                        mRow.setTag("1");
                        data.getProducts().get(position).isChecked = "1";
                        data.getProducts().get(position).qty = mDelete.getText().toString();
                        if (type.equalsIgnoreCase("detail"))
                            BashDetailsWebFragment.setDrinksData(data, "service");
                    }
                    return false;
                }
            });
            popup.show();
        }

        class Holder extends RecyclerView.ViewHolder {

            DrinkMenuRowBinding binding;

            Holder(DrinkMenuRowBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
            }

            public void bind() {
                BashDetailsPOJO.Product item = list.get(getAdapterPosition());
                binding.mName.setText(item.getName());
                binding.mImage.setVisibility(View.VISIBLE);
                Utils.loadImage(mContext, Const.IMAGE_BASE_EVENT + item.getImage(), binding.mImage, R.drawable.placeholder);


                binding.mCost.setText("$" + item.getPrice());
                if (data.getProducts().get(getAdapterPosition()).isChecked != null) {
                    if (data.getProducts().get(getAdapterPosition()).isChecked.equalsIgnoreCase("1")) {
                        binding.mCheckBox.setChecked(true);
                        binding.mRow.setTag("1");
                        binding.mQuantity.setText(data.getProducts().get(getAdapterPosition()).qty);
                    } else {
                        binding.mRow.setTag("0");
                        binding.mCheckBox.setChecked(false);
                        data.getProducts().get(getAdapterPosition()).isChecked = "0";
                    }
                } else {
                    binding.mRow.setTag("0");
                    binding.mCheckBox.setChecked(false);
                    data.getProducts().get(getAdapterPosition()).isChecked = "0";
                }
                binding.mQuantity.setOnClickListener(v -> {
                    optionMenu(getAdapterPosition(), binding.mQuantity, binding.mCheckBox, binding.mRow);
                });
                binding.mCheckBox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        binding.mRow.performClick();
                    }
                });
                binding.mRow.setOnClickListener(v -> {
                    if (binding.mRow.getTag().toString().equalsIgnoreCase("0")) {

                        binding.mCheckBox.setChecked(true);
                        binding.mRow.setTag("1");
                        data.getProducts().get(getAdapterPosition()).isChecked = "1";
                        data.getProducts().get(getAdapterPosition()).qty = binding.mQuantity.getText().toString();
                        if (type.equalsIgnoreCase("detail"))
                            BashDetailsWebFragment.setDrinksData(data, "service");


                    } else {
                        binding.mRow.setTag("0");
                        binding.mCheckBox.setChecked(false);
                        data.getProducts().get(getAdapterPosition()).isChecked = "0";
                        data.getProducts().get(getAdapterPosition()).qty = "0";
                        if (type.equalsIgnoreCase("detail"))
                            BashDetailsWebFragment.setDrinksData(data, "service");
                    }
                });


            }
        }
    }

    public class VenueProductAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private Context mContext;
        private List<VenueListPOJO.Product> list;


        public VenueProductAdapter(Context mContext, List<VenueListPOJO.Product> list) {
            this.mContext = mContext;
            this.list = list;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new Holder(DrinkMenuRowBinding.inflate(LayoutInflater.from(mContext), parent, false));
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

        public void optionMenu(int position, AppCompatTextView mDelete, AppCompatCheckBox mCheckBox, LinearLayout mRow) {
            final PopupMenu popup = new PopupMenu(mContext, mDelete);
            ArrayList rating = new ArrayList();
            for (int i = 0; i < 10; i++) {
                rating.add(i + 1);
            }


            for (int i = 0; i < rating.size(); i++) {
                popup.getMenu().add(Menu.NONE, (i + 1), Menu.NONE, rating.get(i).toString());
            }
            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    mDelete.setText(item.getTitle());
                    if (mRow.getTag().toString().equalsIgnoreCase("1")) {
                        mCheckBox.setChecked(true);
                        mRow.setTag("1");
                        venueData.getProducts().get(position).isChecked = "1";
                        venueData.getProducts().get(position).qty = mDelete.getText().toString();
                        if (type.equalsIgnoreCase("detail"))
                            VenueDetailsFragment.setDrinksData(venueData, "service");
                    }
                    return false;
                }
            });
            popup.show();
        }

        class Holder extends RecyclerView.ViewHolder {

            DrinkMenuRowBinding binding;

            Holder(DrinkMenuRowBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
            }

            public void bind() {
                VenueListPOJO.Product item = list.get(getAdapterPosition());
                binding.mName.setText(item.getName());
                binding.mImage.setVisibility(View.VISIBLE);
                Utils.loadImage(mContext, Const.IMAGE_BASE_EVENT + item.getImage(), binding.mImage, R.drawable.placeholder);
                binding.mCost.setText("$" + item.getPrice());
                if (venueData.getProducts().get(getAdapterPosition()).isChecked != null) {
                    if (venueData.getProducts().get(getAdapterPosition()).isChecked.equalsIgnoreCase("1")) {
                        binding.mCheckBox.setChecked(true);
                        binding.mRow.setTag("1");
                        binding.mQuantity.setText(venueData.getProducts().get(getAdapterPosition()).qty);
                    } else {
                        binding.mRow.setTag("0");
                        binding.mCheckBox.setChecked(false);
                        venueData.getProducts().get(getAdapterPosition()).isChecked = "0";
                    }
                } else {
                    binding.mRow.setTag("0");
                    binding.mCheckBox.setChecked(false);
                    venueData.getProducts().get(getAdapterPosition()).isChecked = "0";
                }
                binding.mQuantity.setOnClickListener(v -> {
                    optionMenu(getAdapterPosition(), binding.mQuantity, binding.mCheckBox, binding.mRow);
                });
                binding.mCheckBox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        binding.mRow.performClick();
                    }
                });
                binding.mRow.setOnClickListener(v -> {
                    if (binding.mRow.getTag().toString().equalsIgnoreCase("0")) {

                        binding.mCheckBox.setChecked(true);
                        binding.mRow.setTag("1");
                        venueData.getProducts().get(getAdapterPosition()).isChecked = "1";
                        venueData.getProducts().get(getAdapterPosition()).qty = binding.mQuantity.getText().toString();
                        if (type.equalsIgnoreCase("detail"))
                            VenueDetailsFragment.setDrinksData(venueData, "service");


                    } else {
                        binding.mRow.setTag("0");
                        binding.mCheckBox.setChecked(false);
                        venueData.getProducts().get(getAdapterPosition()).isChecked = "0";
                        venueData.getProducts().get(getAdapterPosition()).qty = "0";
                        if (type.equalsIgnoreCase("detail"))
                            VenueDetailsFragment.setDrinksData(venueData, "service");
                    }
                });


            }
        }
    }

    private class VenueItemSectionAdapter extends RecyclerView.Adapter<VenueItemSectionAdapter.ViewHolder> {
        Context mContext;
        List<VenueListPOJO.Items> items;

        public VenueItemSectionAdapter(Context mContext, List<VenueListPOJO.Items> items) {
            this.mContext = mContext;
            this.items = items;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(ItemSectionMenuRowBinding.inflate(LayoutInflater.from(mContext), parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.binding.mName.setText(items.get(position).getName());
            holder.binding.mDesc.setText(items.get(position).getDescription());
            holder.binding.mImage.setVisibility(View.VISIBLE);
            Utils.loadImage(mContext, Const.IMAGE_BASE_EVENT + items.get(position).getImage(), holder.binding.mImage, R.drawable.placeholder);
            holder.binding.mCost.setText("$" + items.get(position).getPrice());
            if (items.get(position).getManageStock() == 1) {
                holder.binding.mQuantity.setVisibility(View.VISIBLE);
                holder.binding.mQuantityEdit.setVisibility(View.GONE);
//                holder.binding.mQuantity.setText(items.get(position).getQuantity() + "");
            } else {
                holder.binding.mQuantity.setVisibility(View.GONE);
                holder.binding.mQuantityEdit.setVisibility(View.VISIBLE);

            }
            if (venueData.getItemSections().get(itemSectionIndex).getItems().get(position).isChecked != null) {
                if (venueData.getItemSections().get(itemSectionIndex).getItems().get(position).isChecked.equalsIgnoreCase("1")) {
                    holder.binding.mCheckBox.setChecked(true);
                    holder.binding.mRow.setTag("1");
                    if (items.get(position).getManageStock() == 1)
                        holder.binding.mQuantity.setText(venueData.getItemSections().get(itemSectionIndex).getItems().get(position).qty);
                    else
                        holder.binding.mQuantityEdit.setText(venueData.getItemSections().get(itemSectionIndex).getItems().get(position).qty);
                } else {
                    holder.binding.mRow.setTag("0");
                    holder.binding.mCheckBox.setChecked(false);
                    venueData.getItemSections().get(itemSectionIndex).getItems().get(position).isChecked = "0";
                }
            } else {
                holder.binding.mRow.setTag("0");
                holder.binding.mCheckBox.setChecked(false);
                venueData.getItemSections().get(itemSectionIndex).getItems().get(position).isChecked = "0";
            }
            holder.binding.mCheckBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.binding.mRow.performClick();
                }
            });
            holder.binding.mRow.setOnClickListener(v -> {
                if (holder.binding.mRow.getTag().toString().equalsIgnoreCase("0")) {

                    holder.binding.mCheckBox.setChecked(true);
                    holder.binding.mRow.setTag("1");
                    venueData.getItemSections().get(itemSectionIndex).getItems().get(position).isChecked = "1";
                    if (items.get(position).getManageStock() == 1) {
                        venueData.getItemSections().get(itemSectionIndex).getItems().get(position).qty = holder.binding.mQuantity.getText().toString();
                    } else {
                        if (holder.binding.mQuantityEdit.getText().toString().equalsIgnoreCase("")) {
                            holder.binding.mQuantityEdit.setText("1");
                        } else if (holder.binding.mQuantityEdit.getText().toString().equalsIgnoreCase("0")) {
                            holder.binding.mQuantityEdit.setText("1");
                        }
                        venueData.getItemSections().get(itemSectionIndex).getItems().get(position).qty = holder.binding.mQuantityEdit.getText().toString();
                    }
                    if (type.equalsIgnoreCase("detail"))
                        VenueDetailsFragment.setItemSectionData(venueData, itemSectionIndex, "itemSection");
                } else {
                    holder.binding.mRow.setTag("0");
                    holder.binding.mCheckBox.setChecked(false);
                    venueData.getItemSections().get(itemSectionIndex).getItems().get(position).isChecked = "0";
                    venueData.getItemSections().get(itemSectionIndex).getItems().get(position).qty = "0";
                    if (type.equalsIgnoreCase("detail"))
                        VenueDetailsFragment.setItemSectionData(venueData, itemSectionIndex, "itemSection");
                }
            });
            holder.binding.mQuantity.setOnClickListener(v -> {
                optionMenu(items, position, holder.binding.mQuantity, holder.binding.mCheckBox, holder.binding.mRow);
            });
            holder.binding.mQuantityEdit.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (!holder.binding.mQuantityEdit.getText().toString().equalsIgnoreCase("")) {
                        if (Integer.parseInt(holder.binding.mQuantityEdit.getText().toString()) > 0) {
                            holder.binding.mCheckBox.setChecked(true);
                            holder.binding.mRow.setTag("1");
                            venueData.getItemSections().get(itemSectionIndex).getItems().get(position).isChecked = "1";
                            venueData.getItemSections().get(itemSectionIndex).getItems().get(position).qty = holder.binding.mQuantityEdit.getText().toString();
                            if (type.equalsIgnoreCase("detail"))
                                VenueDetailsFragment.setItemSectionData(venueData, itemSectionIndex, "itemSection");
                        } else {
                            holder.binding.mCheckBox.setChecked(false);
                            holder.binding.mRow.setTag("0");
                            venueData.getItemSections().get(itemSectionIndex).getItems().get(position).isChecked = "0";
                            venueData.getItemSections().get(itemSectionIndex).getItems().get(position).qty = "0";
                            if (type.equalsIgnoreCase("detail"))
                                VenueDetailsFragment.setItemSectionData(venueData, itemSectionIndex, "itemSection");
                        }

                    } else {

                        holder.binding.mCheckBox.setChecked(false);
                        holder.binding.mRow.setTag("0");
                        venueData.getItemSections().get(itemSectionIndex).getItems().get(position).isChecked = "0";
                        venueData.getItemSections().get(itemSectionIndex).getItems().get(position).qty = "0";
                        if (type.equalsIgnoreCase("detail"))
                            VenueDetailsFragment.setItemSectionData(venueData, itemSectionIndex, "itemSection");
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }

        private void optionMenu(List<VenueListPOJO.Items> items, int position, AppCompatTextView mQuantity, AppCompatCheckBox mCheckBox, LinearLayout mRow) {
            final PopupMenu popup = new PopupMenu(mContext, mQuantity);
            ArrayList rating = new ArrayList();
            for (int i = 0; i < items.get(position).getQuantity(); i++) {
                rating.add(i + 1);
            }


            for (int i = 0; i < rating.size(); i++) {
                popup.getMenu().add(Menu.NONE, (i + 1), Menu.NONE, rating.get(i).toString());
            }
            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    mQuantity.setText(item.getTitle());
                    if (mRow.getTag().toString().equalsIgnoreCase("1")) {
                        mCheckBox.setChecked(true);
                        mRow.setTag("1");
                        venueData.getItemSections().get(itemSectionIndex).getItems().get(position).isChecked = "1";
                        venueData.getItemSections().get(itemSectionIndex).getItems().get(position).qty = mQuantity.getText().toString();
                        if (type.equalsIgnoreCase("detail"))
                            VenueDetailsFragment.setItemSectionData(venueData, itemSectionIndex, "itemSection");
                    }
                    return false;
                }
            });
            popup.show();
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            ItemSectionMenuRowBinding binding;

            public ViewHolder(@NonNull ItemSectionMenuRowBinding itemView) {
                super(itemView.getRoot());
                binding = itemView;
            }
        }
    }
}
