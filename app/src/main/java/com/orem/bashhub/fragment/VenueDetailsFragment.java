package com.orem.bashhub.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.orem.bashhub.R;
import com.orem.bashhub.activity.MainActivity;
import com.orem.bashhub.data.VenueListPOJO;
import com.orem.bashhub.databinding.FragmentVenueDetailsBinding;
import com.orem.bashhub.databinding.ItemSectionRowBinding;
import com.orem.bashhub.databinding.OpenHourRowBinding;
import com.orem.bashhub.databinding.OrderRowBinding;
import com.orem.bashhub.databinding.TableRowBinding;
import com.orem.bashhub.utils.Const;
import com.orem.bashhub.utils.OnSwipeListener;
import com.orem.bashhub.utils.Utils;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class VenueDetailsFragment extends BaseFragment {
    static Context mContext;
    static ArrayList<HashMap<String, String>> drinkList;
    static ArrayList<HashMap<String, String>> bottleList;
    static ArrayList<HashMap<String, String>> tableList;
    static ArrayList<HashMap<String, String>> sectionList;
    static ArrayList<HashMap<String, String>> serviceList;
    static ArrayList<HashMap<String, String>> itemSectionsList;
    static int itemSectionsIndex = 0;
    private static FragmentVenueDetailsBinding binding;
    private static VenueListPOJO.Venue data;
    private static SectionAdapter mSectionAdapter;
    private static ItemSectionAdapter mItemSectionAdapter;
    private static DrinkAdapter mDrinkAdapter;
    private static TableAdapter mTableAdapter;
    private static BottleAdapter mBottleAdapter;
    private static ProductAdapter mProductAdapter;
    private GestureDetector gestureDetector;
    private boolean isDetailShow = false;
    private String instaLink = "", spotifyLink = "";
    private Integer ticketQTY;
    private Integer ticketprice;
    private Integer ticketId;
    private ArrayList<HashMap<String, String>> ticketList;
    private JSONArray itemJsonList;

    public static void setDrinksData(VenueListPOJO.Venue datas, String from) {
        data = datas;
//        Toast.makeText(mContext, "clicked", Toast.LENGTH_SHORT).show();
        if (from.equalsIgnoreCase("drink")) {
            ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
            drinkList = new ArrayList<>();
            for (int i = 0; i < data.getDrinks().size(); i++) {
                HashMap<String, String> map = new HashMap<>();
                if (data.getDrinks().get(i).isChecked != null)
                    if (data.getDrinks().get(i).isChecked.equalsIgnoreCase("1")) {
                        map.put("id", data.getDrinks().get(i).getId().toString());
                        map.put("price", data.getDrinks().get(i).getPrice().toString());
                        map.put("name", data.getDrinks().get(i).getName());
                        map.put("qty", data.getDrinks().get(i).qty);
                        arrayList.add(map);
                    }
            }
            if (arrayList.size() > 0) {
                drinkList = arrayList;
                binding.mOrderDrinkRV.setVisibility(View.VISIBLE);
                mDrinkAdapter = new DrinkAdapter(mContext, arrayList);
                binding.mOrderDrinkRV.setAdapter(mDrinkAdapter);
            } else binding.mOrderDrinkRV.setVisibility(View.GONE);
        } else if (from.equalsIgnoreCase("bottle")) {
            ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
            bottleList = new ArrayList<>();
            for (int i = 0; i < data.getBottles().size(); i++) {
                HashMap<String, String> map = new HashMap<>();
                if (data.getBottles().get(i).isChecked != null)
                    if (data.getBottles().get(i).isChecked.equalsIgnoreCase("1")) {
                        map.put("id", data.getBottles().get(i).getId().toString());
                        map.put("price", data.getBottles().get(i).getAmount());
                        map.put("name", data.getBottles().get(i).getName());
                        map.put("qty", data.getBottles().get(i).qty);
                        map.put("quantity", data.getBottles().get(i).getQuantity().toString());
                        arrayList.add(map);
                    }
            }
            if (arrayList.size() > 0) {
                bottleList = arrayList;
                binding.mOBottleServiceRV.setVisibility(View.VISIBLE);
                mBottleAdapter = new BottleAdapter(mContext, arrayList);
                binding.mOBottleServiceRV.setAdapter(mBottleAdapter);
            } else binding.mOBottleServiceRV.setVisibility(View.GONE);
        } else if (from.equalsIgnoreCase("table")) {
            ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
            for (int i = 0; i < data.getTables().size(); i++) {
                HashMap<String, String> map = new HashMap<>();
                tableList = new ArrayList<>();
                if (data.getTables().get(i).isChecked != null)
                    if (data.getTables().get(i).isChecked.equalsIgnoreCase("1")) {
                        map.put("id", data.getTables().get(i).getId().toString());
                        map.put("price", data.getTables().get(i).getPrice().toString());
                        map.put("name", data.getTables().get(i).getName());
                        map.put("guest", data.getTables().get(i).getGuestPerTable() + "");
                        map.put("table_id", data.getTables().get(i).getDesignationId() + "");
                        map.put("des", data.getTables().get(i).getDescription());
                        arrayList.add(map);
                    }
            }
            if (arrayList.size() > 0) {
                tableList = arrayList;
                binding.mTableServiceRV.setVisibility(View.VISIBLE);
                mTableAdapter = new TableAdapter(mContext, arrayList);
                binding.mTableServiceRV.setAdapter(mTableAdapter);
            } else binding.mTableServiceRV.setVisibility(View.GONE);
        } else if (from.equalsIgnoreCase("section")) {
            ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
            sectionList = new ArrayList<>();
            for (int i = 0; i < data.getSections().size(); i++) {
                HashMap<String, String> map = new HashMap<>();
                if (data.getSections().get(i).isChecked != null)
                    if (data.getSections().get(i).isChecked.equalsIgnoreCase("1")) {
                        map.put("id", data.getSections().get(i).getId().toString());
                        map.put("price", data.getSections().get(i).getPrice().toString());
                        map.put("name", data.getSections().get(i).getName());
                        map.put("guest", data.getSections().get(i).getGuest() + "");
                        arrayList.add(map);
                    }
            }
            if (arrayList.size() > 0) {
                sectionList = arrayList;
                binding.mSectionRV.setVisibility(View.VISIBLE);
                mSectionAdapter = new SectionAdapter(mContext, arrayList);
                binding.mSectionRV.setAdapter(mSectionAdapter);
            } else binding.mSectionRV.setVisibility(View.GONE);
        } else if (from.equalsIgnoreCase("service")) {
            ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
            serviceList = new ArrayList<>();
            for (int i = 0; i < data.getProducts().size(); i++) {
                HashMap<String, String> map = new HashMap<>();
                if (data.getProducts().get(i).isChecked != null)
                    if (data.getProducts().get(i).isChecked.equalsIgnoreCase("1")) {
                        map.put("id", data.getProducts().get(i).getId().toString());
                        map.put("price", data.getProducts().get(i).getPrice().toString());
                        map.put("name", data.getProducts().get(i).getName());
                        map.put("qty", data.getProducts().get(i).qty);
                        map.put("quantity", "");
                        arrayList.add(map);
                    }
            }
            if (arrayList.size() > 0) {
                serviceList = arrayList;
                binding.mOtherServiceRV.setVisibility(View.VISIBLE);
                mProductAdapter = new ProductAdapter(mContext, arrayList);
                binding.mOtherServiceRV.setAdapter(mProductAdapter);
            } else binding.mOtherServiceRV.setVisibility(View.GONE);
        }
    }

    public static void setItemSectionData(VenueListPOJO.Venue datas, int index, String from) {
        data = datas;
        itemSectionsIndex = index;
//        Toast.makeText(mContext, "clicked", Toast.LENGTH_SHORT).show();
//        ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
//        itemSectionsList = new ArrayList<>();
//        for (int i = 0; i < data.getItemSections().get(index).getItems().size(); i++) {
//            HashMap<String, String> map = new HashMap<>();
//            if (data.getItemSections().get(index).getItems().get(i).isChecked != null)
//                if (data.getItemSections().get(index).getItems().get(i).isChecked.equalsIgnoreCase("1")) {
//                    map.put("id", data.getItemSections().get(index).getItems().get(i).getId().toString());
//                    map.put("price", data.getItemSections().get(index).getItems().get(i).getPrice().toString());
//                    map.put("name", data.getItemSections().get(index).getItems().get(i).getName());
//                    map.put("qty", data.getItemSections().get(index).getItems().get(i).qty);
//                    map.put("quantity", data.getItemSections().get(index).getItems().get(i).getQuantity() + "");
//                    arrayList.add(map);
//                }

//            if (arrayList.size() > 0) {
//                data.getItemSections().get(index).itemSectionsList = arrayList;
        mItemSectionAdapter = new ItemSectionAdapter(data.getItemSections());
        binding.mItemSectionRV.setAdapter(mItemSectionAdapter);
//            }
//        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if (serviceList != null) {
            if (serviceList.size() > 0) {
                mProductAdapter.notifyDataSetChanged();
            }
        }
        if (drinkList != null) {
            if (drinkList.size() > 0) {
                mDrinkAdapter.notifyDataSetChanged();
            }
        }
        if (bottleList != null) {
            if (bottleList.size() > 0) {
                mBottleAdapter.notifyDataSetChanged();
            }
        }
        if (tableList != null) {
            if (tableList.size() > 0) {
                mTableAdapter.notifyDataSetChanged();
            }
        }
        if (sectionList != null) {
            if (sectionList.size() > 0) {
                mSectionAdapter.notifyDataSetChanged();
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_venue_details, container, false);
        if (data != null) init();
        else Objects.requireNonNull(getActivity()).onBackPressed();
        return binding.getRoot();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void init() {
        ticketList = new ArrayList<>();
        drinkList = new ArrayList<>();
        bottleList = new ArrayList<>();
        sectionList = new ArrayList<>();
        tableList = new ArrayList<>();
        serviceList = new ArrayList<>();
        itemSectionsList = new ArrayList<>();
        mContext = getActivity();
        binding.mBottleMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bottleList.size() == 0) {
                    for (int i = 0; i < data.getBottles().size(); i++) {
                        data.getBottles().get(i).qty = "1";
                        data.getBottles().get(i).isChecked = "0";
                    }
                }
                DrinkBottleListFragment fragment = new DrinkBottleListFragment();
                fragment.setVenueData(data, "bottle", "detail", 0);
                Utils.goToFragment(mContext, fragment, R.id.fragment_container);
            }
        });
        binding.mDrinkMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drinkList.size() == 0) {
                    for (int i = 0; i < data.getDrinks().size(); i++) {
                        data.getDrinks().get(i).qty = "1";
                        data.getDrinks().get(i).isChecked = "0";
                    }
                }
                DrinkBottleListFragment fragment = new DrinkBottleListFragment();
                fragment.setVenueData(data, "drink", "detail", 0);
                Utils.goToFragment(mContext, fragment, R.id.fragment_container);
            }
        });
        binding.mOtherServiceMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (serviceList.size() == 0) {
                    for (int i = 0; i < data.getProducts().size(); i++) {
                        data.getProducts().get(i).qty = "1";
                        data.getProducts().get(i).isChecked = "0";
                    }
                }
                DrinkBottleListFragment fragment = new DrinkBottleListFragment();
                fragment.setVenueData(data, "service", "detail", 0);
                Utils.goToFragment(mContext, fragment, R.id.fragment_container);
            }
        });
        binding.mTableMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tableList.size() == 0) {
                    for (int i = 0; i < data.getTables().size(); i++) {
                        data.getTables().get(i).isChecked = "0";
                    }
                }
                TableSectionListFragment fragment = new TableSectionListFragment();
                fragment.setVenueData(data, "table", "detail");
                Utils.goToFragment(mContext, fragment, R.id.fragment_container);
            }
        });
        binding.mSectionMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sectionList.size() == 0) {
                    for (int i = 0; i < data.getSections().size(); i++) {
                        data.getSections().get(i).isChecked = "0";
                    }
                }
                TableSectionListFragment fragment = new TableSectionListFragment();
                fragment.setVenueData(data, "section", "detail");
                Utils.goToFragment(mContext, fragment, R.id.fragment_container);
            }
        });


        binding.setData(data);
        binding.executePendingBindings();
        binding.ivShare.setOnClickListener(v -> Utils.shareContent(mContext, data.getShareData(mContext)));
        binding.ivNavigation.setOnClickListener(v -> Utils.intentToMap(mContext, data.getLat(), data.getLng()));

//        Log.e("event_image", Const.IMAGE_BASE_EVENT + data.eventImage);
        Utils.loadImage(mContext, Const.IMAGE_BASE_EVENT + data.getImage(), binding.mVenuImage, R.drawable.placeholder);

        if (data.getRoster() != null) {
            binding.mClosed.setVisibility(View.GONE);
            binding.mTimeRV.setVisibility(View.VISIBLE);
            ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
            HashMap<String, String> map = new HashMap<>();
            map.put("name", data.getRoster().getSunday().getName());
            map.put("isOpen", data.getRoster().getSunday().getIsOpen().toString());
            map.put("open", data.getRoster().getSunday().getStartTime().toString());
            map.put("close", data.getRoster().getSunday().getEndTime().toString());
            arrayList.add(map);
            map = new HashMap<>();
            map.put("name", data.getRoster().getMonday().getName());
            map.put("isOpen", data.getRoster().getMonday().getIsOpen().toString());
            map.put("open", data.getRoster().getMonday().getStartTime().toString());
            map.put("close", data.getRoster().getMonday().getEndTime().toString());
            arrayList.add(map);
            map = new HashMap<>();
            map.put("name", data.getRoster().getTuesday().getName());
            map.put("isOpen", data.getRoster().getTuesday().getIsOpen().toString());
            map.put("open", data.getRoster().getTuesday().getStartTime().toString());
            map.put("close", data.getRoster().getTuesday().getEndTime().toString());
            arrayList.add(map);
            map = new HashMap<>();
            map.put("name", data.getRoster().getWednesday().getName());
            map.put("isOpen", data.getRoster().getWednesday().getIsOpen().toString());
            map.put("open", data.getRoster().getWednesday().getStartTime().toString());
            map.put("close", data.getRoster().getWednesday().getEndTime().toString());
            arrayList.add(map);
            map = new HashMap<>();
            map.put("name", data.getRoster().getThursday().getName());
            map.put("isOpen", data.getRoster().getThursday().getIsOpen().toString());
            map.put("open", data.getRoster().getThursday().getStartTime().toString());
            map.put("close", data.getRoster().getThursday().getEndTime().toString());
            arrayList.add(map);
            map = new HashMap<>();
            map.put("name", data.getRoster().getFriday().getName());
            map.put("isOpen", data.getRoster().getFriday().getIsOpen().toString());
            map.put("open", data.getRoster().getFriday().getStartTime().toString());
            map.put("close", data.getRoster().getFriday().getEndTime().toString());
            arrayList.add(map);
            map = new HashMap<>();
            map.put("name", data.getRoster().getSaturday().getName());
            map.put("isOpen", data.getRoster().getSaturday().getIsOpen().toString());
            map.put("open", data.getRoster().getSaturday().getStartTime().toString());
            map.put("close", data.getRoster().getSaturday().getEndTime().toString());
            arrayList.add(map);
            binding.mTimeRV.setAdapter(new TimeAdapter(mContext, arrayList));
            binding.llCrashBash.setVisibility(View.VISIBLE);
            boolean isClose = true;
            if (data.getItemSections() != null)
                if (data.getItemSections().size() == 0) {
                    binding.mItemSectionRV.setVisibility(View.GONE);

                } else {
                    if (isClose) {
                        isClose = false;
                    }
                    binding.mItemSectionRV.setVisibility(View.VISIBLE);
                    mItemSectionAdapter = new ItemSectionAdapter(data.getItemSections());
                    binding.mItemSectionRV.setAdapter(mItemSectionAdapter);
                }
            else binding.mItemSectionRV.setVisibility(View.GONE);
            if (data.getDrinks() != null)
                if (data.getDrinks().size() == 0) {
                    binding.mDrinkLayout.setVisibility(View.GONE);
                } else {
                    if (isClose) {
                        isClose = false;
                    }
                    binding.mDrinkLayout.setVisibility(View.VISIBLE);
                }
            else binding.mDrinkLayout.setVisibility(View.GONE);

            if (data.getBottles() != null)
                if (data.getBottles().size() > 0) {
                    if (isClose) {
                        isClose = false;
                    }
                    binding.mBottleLayout.setVisibility(View.VISIBLE);
                } else
                    binding.mBottleLayout.setVisibility(View.GONE);
            else binding.mBottleLayout.setVisibility(View.GONE);

            if (data.getTables() != null)
                if (data.getTables().size() > 0) {
                    if (isClose) {
                        isClose = false;
                    }
                    binding.mTableLayout.setVisibility(View.VISIBLE);
                } else
                    binding.mTableLayout.setVisibility(View.GONE);
            else binding.mTableLayout.setVisibility(View.GONE);

            if (data.getSections() != null)
                if (data.getSections().size() > 0) {
                    if (isClose) {
                        isClose = false;
                    }
                    binding.mSectionLayout.setVisibility(View.VISIBLE);
                } else
                    binding.mSectionLayout.setVisibility(View.GONE);
            else binding.mSectionLayout.setVisibility(View.GONE);

            if (isClose) {
                binding.llCrashBash.setVisibility(View.GONE);
            }
        } else {
            binding.llCrashBash.setVisibility(View.GONE);
            binding.mClosed.setVisibility(View.VISIBLE);
            binding.mTimeRV.setVisibility(View.GONE);
            binding.mDrinkLayout.setVisibility(View.GONE);
            binding.mSectionLayout.setVisibility(View.GONE);
            binding.mTableLayout.setVisibility(View.GONE);
            binding.mBottleLayout.setVisibility(View.GONE);
            binding.mItemSectionRV.setVisibility(View.GONE);
        }

//            if (data.getVenue().getId() != null) {

//        if (data.getProducts() != null)
//            if (data.getProducts().size() > 0)
//                binding.mOtherServicesLayout.setVisibility(View.VISIBLE);
//            else
//                binding.mOtherServicesLayout.setVisibility(View.GONE);
//        else binding.mOtherServicesLayout.setVisibility(View.GONE);

//            } else {
//                binding.mVenueMenuLayout.setVisibility(View.GONE);
//            }
//
        binding.fullLL.setVisibility(View.VISIBLE);
//        binding.fullLL.setVisibility(isDetailShow ? View.VISIBLE : View.GONE);

        Animation slideUp = AnimationUtils.loadAnimation(baseActivity, R.anim.slide_up);
        binding.topRL.setVisibility(View.VISIBLE);
        binding.topRL.startAnimation(slideUp);
        gestureDetector = new GestureDetector(baseActivity, new OnSwipeListener() {

            @Override
            public boolean onSwipe(Direction direction) {
                if (direction == Direction.up) {
                    if (binding.fullLL.getVisibility() != View.VISIBLE) {
                        Animation slideUp = AnimationUtils.loadAnimation(baseActivity, R.anim.slide_up);
                        binding.fullLL.setVisibility(View.VISIBLE);
                        binding.fullLL.startAnimation(slideUp);
                    }
                } else if (direction == Direction.down) {
                    Animation slideUp = AnimationUtils.loadAnimation(baseActivity, R.anim.slide_bottom);
                    binding.topRL.setVisibility(View.GONE);
                    binding.topRL.startAnimation(slideUp);
                    slideUp.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {
                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            Objects.requireNonNull(getActivity()).onBackPressed();
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {
                        }
                    });
                }
                return true;
            }
        });

        binding.fullViewLL.setOnTouchListener((view12, motionEvent) -> {
            gestureDetector.onTouchEvent(motionEvent);
            return true;
        });
        binding.mSpotyfy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.intentToBrowser(mContext, spotifyLink);
            }
        });
        binding.mClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation slideUp = AnimationUtils.loadAnimation(baseActivity, R.anim.slide_bottom);
                binding.topRL.setVisibility(View.GONE);
                binding.topRL.startAnimation(slideUp);
                slideUp.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        Objects.requireNonNull(getActivity()).onBackPressed();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                    }
                });
            }
        });

        binding.llCrashBash.setOnClickListener(v -> {

            if (data.getItemSections() == null && ticketList == null && drinkList == null && bottleList == null && tableList == null && sectionList == null && serviceList == null) {
                Toast.makeText(mContext, "Please select any service to buy", Toast.LENGTH_SHORT).show();
            } else if (data.getItemSections().size() == 0 && ticketList.size() == 0 && drinkList.size() == 0 && bottleList.size() == 0 && tableList.size() == 0 && sectionList.size() == 0 && serviceList.size() == 0) {
                Toast.makeText(mContext, "Please select any service to buy", Toast.LENGTH_SHORT).show();
            } else {
                ConfirmOrderFragment fragment = new ConfirmOrderFragment();
                fragment.setVenueData(data, data.getItemSections(), ticketList, drinkList, bottleList, tableList, sectionList, serviceList, "1");
                Utils.goToFragment(mContext, fragment, R.id.fragment_container);
                ((MainActivity) mContext).binding.fragmentContainer.setVisibility(View.VISIBLE);
            }

        });
    }


    public void setData(VenueListPOJO.Venue data, boolean isDetailShow) {
        this.data = data;
        this.isDetailShow = isDetailShow;
    }

    public static class ProductAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private Context mContext;
//        private ArrayList<HashMap<String, String>> list;


        public ProductAdapter(Context mContext, ArrayList<HashMap<String, String>> list) {
            this.mContext = mContext;
//            this.list = list;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ProductAdapter.Holder(OrderRowBinding.inflate(LayoutInflater.from(mContext), parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            ProductAdapter.Holder h = (ProductAdapter.Holder) holder;
            h.bind();
        }

        @Override
        public int getItemCount() {
            return serviceList.size();
        }

        public void optionMenu(int position, AppCompatTextView mDelete, AppCompatTextView mPrice, String pp) {
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
                    Double price = Double.parseDouble(pp.replace("$", "")) * Double.parseDouble(mDelete.getText().toString());
                    mPrice.setText("$" + price);
//                    data.getDrinks().get(position).qty = mDelete.getText().toString();
//                    data.notifyChange();
                    HashMap<String, String> map = new HashMap<>();
                    map.put("id", serviceList.get(position).get("id"));
                    map.put("price", serviceList.get(position).get("price"));
                    map.put("name", serviceList.get(position).get("name"));
                    map.put("qty", mDelete.getText().toString());
                    serviceList.set(position, map);
                    return false;
                }
            });
            popup.show();
        }

        class Holder extends RecyclerView.ViewHolder {

            OrderRowBinding binding;

            Holder(OrderRowBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
            }

            public void bind() {
                HashMap<String, String> item = serviceList.get(getAdapterPosition());
                binding.mTitle.setText(item.get("name"));
                Double price = Double.parseDouble(item.get("price")) * Double.parseDouble(item.get("qty"));
                binding.mPrice.setText("$" + price);
                binding.mQuantity.setText(item.get("qty"));
                binding.mQuantity.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        optionMenu(getAdapterPosition(), binding.mQuantity, binding.mPrice, item.get("price"));
                    }
                });
                binding.mDelete.setOnClickListener(v -> {
//                    data.getBottles().get(getAdapterPosition()).isChecked = "0";
//                    data.getBottles().get(getAdapterPosition()).qty = "0";
//                    data.notifyChange();
//                    list.remove(getAdapterPosition());
                    serviceList.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                });
            }
        }
    }

    public static class DrinkAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private Context mContext;
//        private ArrayList<HashMap<String, String>> list;


        public DrinkAdapter(Context mContext, ArrayList<HashMap<String, String>> list) {
            this.mContext = mContext;
//            this.list = list;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new DrinkAdapter.Holder(OrderRowBinding.inflate(LayoutInflater.from(mContext), parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            DrinkAdapter.Holder h = (DrinkAdapter.Holder) holder;
            h.bind();
        }

        @Override
        public int getItemCount() {
            return drinkList.size();
        }

        public void optionMenu(int position, AppCompatTextView mDelete, AppCompatTextView mPrice, String pp) {
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
                    Double price = Double.parseDouble(pp.replace("$", "")) * Double.parseDouble(mDelete.getText().toString());
                    mPrice.setText("$" + price);
//                    data.getDrinks().get(position).qty = mDelete.getText().toString();
//                    data.notifyChange();
                    HashMap<String, String> map = new HashMap<>();
                    map.put("id", drinkList.get(position).get("id"));
                    map.put("price", drinkList.get(position).get("price"));
                    map.put("name", drinkList.get(position).get("name"));
                    map.put("qty", mDelete.getText().toString());
                    drinkList.set(position, map);
                    return false;
                }
            });
            popup.show();
        }

        class Holder extends RecyclerView.ViewHolder {

            OrderRowBinding binding;

            Holder(OrderRowBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
            }

            public void bind() {
                HashMap<String, String> item = drinkList.get(getAdapterPosition());
                binding.mTitle.setText(item.get("name"));
                Double price = Double.parseDouble(item.get("price")) * Double.parseDouble(item.get("qty"));
                binding.mPrice.setText("$" + price);
                binding.mQuantity.setText(item.get("qty"));
                binding.mQuantity.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        optionMenu(getAdapterPosition(), binding.mQuantity, binding.mPrice, item.get("price"));
                    }
                });
                binding.mDelete.setOnClickListener(v -> {
//                    data.getBottles().get(getAdapterPosition()).isChecked = "0";
//                    data.getBottles().get(getAdapterPosition()).qty = "0";
//                    data.notifyChange();
//                    list.remove(getAdapterPosition());
                    drinkList.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                });
            }
        }
    }

    public static class BottleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private Context mContext;
//        private ArrayList<HashMap<String, String>> list;

        public BottleAdapter(Context mContext, ArrayList<HashMap<String, String>> list) {
            this.mContext = mContext;
//            this.list = list;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new BottleAdapter.Holder(OrderRowBinding.inflate(LayoutInflater.from(mContext), parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            BottleAdapter.Holder h = (BottleAdapter.Holder) holder;
            h.bind();
        }

        @Override
        public int getItemCount() {
            return bottleList.size();
        }

        public void optionMenu(int position, AppCompatTextView mDelete, AppCompatTextView mPrice, String pp) {
            final PopupMenu popup = new PopupMenu(mContext, mDelete);
            ArrayList rating = new ArrayList();
            for (int i = 0; i < Integer.parseInt(bottleList.get(position).get("quantity")); i++) {
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
                    Double price = Double.parseDouble(pp.replace("$", "")) * Double.parseDouble(mDelete.getText().toString());
                    mPrice.setText("$" + price);
//                    data.getBottles().get(position).qty = mDelete.getText().toString();
//                    data.notifyChange();
                    HashMap<String, String> map = new HashMap<>();
                    map.put("id", bottleList.get(position).get("id"));
                    map.put("price", bottleList.get(position).get("price"));
                    map.put("name", bottleList.get(position).get("name"));
                    map.put("qty", mDelete.getText().toString());
                    bottleList.set(position, map);
                    return false;
                }
            });
            popup.show();
        }

        class Holder extends RecyclerView.ViewHolder {

            OrderRowBinding binding;

            Holder(OrderRowBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
            }

            public void bind() {
                HashMap<String, String> item = bottleList.get(getAdapterPosition());
                binding.mTitle.setText(item.get("name"));
                Double price = Double.parseDouble(item.get("price")) * Double.parseDouble(item.get("qty"));
                binding.mPrice.setText("$" + price);
                binding.mQuantity.setText(item.get("qty"));
                binding.mDelete.setOnClickListener(v -> {
//                    notifyDataSetChanged();
//                    data.getBottles().get(getAdapterPosition()).isChecked = "0";
//                    data.getBottles().get(getAdapterPosition()).qty = "0";
//                    data.notifyChange();
//                    list.remove(getAdapterPosition());
                    bottleList.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                    notifyDataSetChanged();
                });
                binding.mQuantity.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        optionMenu(getAdapterPosition(), binding.mQuantity, binding.mPrice, item.get("price"));
                    }
                });
            }
        }
    }

    public static class TableAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private Context mContext;
//        private ArrayList<HashMap<String, String>> list;


        public TableAdapter(Context mContext, ArrayList<HashMap<String, String>> list) {
            this.mContext = mContext;
//            this.list = list;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new TableAdapter.Holder(TableRowBinding.inflate(LayoutInflater.from(mContext), parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            TableAdapter.Holder h = (TableAdapter.Holder) holder;
            h.bind();
        }

        @Override
        public int getItemCount() {
            return tableList.size();
        }

        class Holder extends RecyclerView.ViewHolder {

            TableRowBinding binding;

            Holder(TableRowBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
            }

            public void bind() {
                HashMap<String, String> item = tableList.get(getAdapterPosition());
                binding.mName.setText(item.get("name"));
                binding.mGuest.setText(item.get("guest") + " Guest");
                binding.mDescription.setText(item.get("des"));
                binding.mPrice.setText("$" + item.get("price"));
                binding.mTableId.setText("Table : " + item.get("table_id"));
                binding.mDelete.setOnClickListener(v -> {
//                    notifyDataSetChanged();
//                    data.getTables().set(getAdapterPosition(),).isChecked = "0";
//                    data.notifyChange();
//                    list.remove(getAdapterPosition());
                    tableList.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                });
            }
        }
    }

    public static class SectionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private Context mContext;
//        private ArrayList<HashMap<String, String>> list;


        public SectionAdapter(Context mContext, ArrayList<HashMap<String, String>> list) {
            this.mContext = mContext;
//            this.list = list;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new SectionAdapter.Holder(TableRowBinding.inflate(LayoutInflater.from(mContext), parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            SectionAdapter.Holder h = (SectionAdapter.Holder) holder;
            h.bind();
        }

        @Override
        public int getItemCount() {
            return sectionList.size();
        }

        class Holder extends RecyclerView.ViewHolder {

            TableRowBinding binding;

            Holder(TableRowBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
            }

            public void bind() {
                HashMap<String, String> item = sectionList.get(getAdapterPosition());
                binding.mName.setText(item.get("name"));
                binding.mGuest.setText(item.get("guest") + " Guest");
                binding.mDescription.setVisibility(View.GONE);
                binding.mPrice.setText("$" + item.get("price"));
                binding.mTableId.setVisibility(View.GONE);
                binding.mDelete.setOnClickListener(v -> {
//                    notifyDataSetChanged();
//                    data.getTables().get(getAdapterPosition()).isChecked = "0";
//                    data.notifyChange();
                    sectionList.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                });
            }
        }
    }

    private static class ItemSectionAdapter extends RecyclerView.Adapter<ItemSectionAdapter.ViewHolder> {
        List<VenueListPOJO.ItemSection> itemSections;

        public ItemSectionAdapter(List<VenueListPOJO.ItemSection> itemSections) {
            this.itemSections = itemSections;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(ItemSectionRowBinding.inflate(LayoutInflater.from(mContext), parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.binding.mTitle.setText(itemSections.get(position).getName());
            Glide.with(mContext).load(Const.IMAGE_BASE_EVENT + itemSections.get(position).getImage()).into(holder.binding.iv388);
            holder.binding.mMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemSections.get(position).getItems().size() > 0) {
//                            for (int i = 0; i < data.getItemSections().get(position).getItems().size(); i++) {
//                                data.getItemSections().get(position).getItems().get(i).qty = "1";
//                                data.getItemSections().get(position).getItems().get(i).isChecked = "0";
//                            }
                        DrinkBottleListFragment fragment = new DrinkBottleListFragment();
                        fragment.setVenueData(data, "itemSection", "detail", position);
                        Utils.goToFragment(mContext, fragment, R.id.fragment_container);
                    } else {
                        Utils.showToast(mContext, "No item available right now");
                    }
                }
            });
//            if (position == itemSectionsIndex)
            if (itemSections.get(position).getItems().size() > 0)
                holder.binding.mItemSectionRV.setAdapter(new ItemSectionMenuAdapter(itemSections.get(position).getItems()));

        }

        @Override
        public int getItemCount() {
            return itemSections.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            ItemSectionRowBinding binding;

            public ViewHolder(@NonNull ItemSectionRowBinding itemView) {
                super(itemView.getRoot());
                binding = itemView;
            }
        }

        private class ItemSectionMenuAdapter extends RecyclerView.Adapter<ItemSectionMenuAdapter.ViewHolder> {
            List<VenueListPOJO.Items> itemSections;

            public ItemSectionMenuAdapter(List<VenueListPOJO.Items> itemSections) {
                this.itemSections = itemSections;
            }

            @NonNull
            @Override
            public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new ViewHolder(OrderRowBinding.inflate(LayoutInflater.from(mContext), parent, false));
            }

            @Override
            public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
                if (itemSections.get(position).isChecked != null)
                    if (itemSections.get(position).isChecked.equalsIgnoreCase("1")) {
                        if (itemSections.get(position).getManageStock() == 1) {
                            holder.binding.mQuantity.setVisibility(View.VISIBLE);
                            holder.binding.mQuantityEdit.setVisibility(View.GONE);
                            holder.binding.mQuantity.setText(itemSections.get(position).qty);
                        } else {
                            holder.binding.mQuantity.setVisibility(View.GONE);
                            holder.binding.mQuantityEdit.setVisibility(View.VISIBLE);
                            holder.binding.mQuantityEdit.setText(itemSections.get(position).qty);
                        }
                        if (itemSections.get(position).qty.equals("0")) {
                            holder.binding.mPrice.setText("$" + itemSections.get(position).getPrice());
                        } else {
                            Double price = Double.parseDouble(itemSections.get(position).getPrice()) * Double.parseDouble(itemSections.get(position).qty);
                            holder.binding.mPrice.setText("$" + price);
                        }
                        holder.binding.mRow.setVisibility(View.VISIBLE);

                        holder.binding.mTitle.setText(itemSections.get(position).getName());
                        holder.binding.mQuantityEdit.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                if (!holder.binding.mQuantityEdit.getText().toString().equalsIgnoreCase("")) {
                                    if (Integer.parseInt(holder.binding.mQuantityEdit.getText().toString()) > 0) {
                                        Double price = Double.parseDouble(holder.binding.mPrice.getText().toString().replace("$", "")) * Double.parseDouble(holder.binding.mQuantityEdit.getText().toString());
                                        holder.binding.mPrice.setText("$" + price);
                                        itemSections.get(position).qty = holder.binding.mQuantityEdit.getText().toString();
//                                        ItemSectionAdapter.this.notifyDataSetChanged();
                                    }
                                }
                            }

                            @Override
                            public void afterTextChanged(Editable s) {

                            }
                        });
//                        if (itemSections.get(position).qty.equalsIgnoreCase("0")) {
//                            holder.binding.mQuantity.setVisibility(View.GONE);
//                            holder.binding.mQuantityTitle.setVisibility(View.GONE);
//                        } else
//                            holder.binding.mQuantity.setText(itemSections.get(position).qty);
//
                        holder.binding.mDelete.setOnClickListener(v -> {
//                    notifyDataSetChanged();
                            itemSections.get(position).isChecked = "0";
                            itemSections.get(position).qty = "0";
//                    data.notifyChange();
//                    list.remove(getAdapterPosition());
//                    itemSectionsList.remove(position);
//                    notifyItemRemoved(position);
                            notifyDataSetChanged();
                        });
                        holder.binding.mQuantity.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                optionMenu(itemSections, position, holder.binding.mQuantity, holder.binding.mPrice, itemSections.get(position).getPrice(), itemSections.get(position).getQuantity().toString());
                            }
                        });
                    } else {
                        holder.binding.mRow.setVisibility(View.GONE);
                        holder.itemView.setLayoutParams(new ViewGroup.LayoutParams(0, 0));
                    }
                else {
                    holder.binding.mRow.setVisibility(View.GONE);
                    holder.itemView.setLayoutParams(new ViewGroup.LayoutParams(0, 0));
                }

            }

            private void optionMenu(List<VenueListPOJO.Items> itemSections, int position, AppCompatTextView mQuantity, AppCompatTextView mPrice, String pp, String qty) {
                final PopupMenu popup = new PopupMenu(mContext, mQuantity);
                ArrayList rating = new ArrayList();
                for (int i = 0; i < Integer.parseInt(qty); i++) {
                    rating.add(i + 1);
                }


                for (int i = 0; i < rating.size(); i++) {
                    popup.getMenu().add(Menu.NONE, (i + 1), Menu.NONE, rating.get(i).toString());
                }
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        mQuantity.setText(item.getTitle());
                        Double price = Double.parseDouble(pp.replace("$", "")) * Double.parseDouble(mQuantity.getText().toString());
                        mPrice.setText("$" + price);
                        itemSections.get(position).qty = mQuantity.getText().toString();
                        ItemSectionAdapter.this.notifyDataSetChanged();
//                        HashMap<String, String> map = new HashMap<>();
//                        map.put("id", itemSectionsList.get(position).get("id"));
//                        map.put("price", itemSectionsList.get(position).get("price"));
//                        map.put("name", itemSectionsList.get(position).get("name"));
//                        map.put("qty", mQuantity.getText().toString());
//                        map.put("quantity", qty);
//                        itemSectionsList.set(position, map);
                        return false;
                    }
                });
                popup.show();
            }

            @Override
            public int getItemCount() {
                return itemSections.size();
            }

            public class ViewHolder extends RecyclerView.ViewHolder {
                OrderRowBinding binding;

                public ViewHolder(@NonNull OrderRowBinding itemView) {
                    super(itemView.getRoot());
                    binding = itemView;
                }
            }
        }
    }

    private class TimeAdapter extends RecyclerView.Adapter<TimeAdapter.ViewHolder> {
        Context mContext;
        ArrayList<HashMap<String, String>> arrayList;

        public TimeAdapter(Context mContext, ArrayList<HashMap<String, String>> arrayList) {
            this.mContext = mContext;
            this.arrayList = arrayList;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(OpenHourRowBinding.inflate(LayoutInflater.from(mContext), parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.binding.mTitle.setText(arrayList.get(position).get("name"));
            if (arrayList.get(position).get("isOpen").equalsIgnoreCase("1")) {
                holder.binding.mCloseTime.setText(arrayList.get(position).get("close"));
                holder.binding.mOpenTime.setText(arrayList.get(position).get("open"));
                holder.binding.mOpen.setText("Open");
            } else {
                holder.binding.mOpen.setText("Closed");
                holder.binding.mTimeLayout.setVisibility(View.GONE);
            }
        }

        @Override
        public int getItemCount() {
            return arrayList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            OpenHourRowBinding binding;

            public ViewHolder(@NonNull OpenHourRowBinding itemView) {
                super(itemView.getRoot());
                binding = itemView;
            }
        }
    }
}