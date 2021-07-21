package com.orem.bashhub.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.orem.bashhub.R;
import com.orem.bashhub.activity.MainActivity;
import com.orem.bashhub.adapter.HostsAdapter;
import com.orem.bashhub.adapter.SpotifyDetailAdapter;
import com.orem.bashhub.data.BashDetailsPOJO;
import com.orem.bashhub.data.SpotifyDetailPojo;
import com.orem.bashhub.databinding.ActivityBashDetailBinding;
import com.orem.bashhub.databinding.OrderRowBinding;
import com.orem.bashhub.databinding.TableRowBinding;
import com.orem.bashhub.databinding.TicketRowBinding;
import com.orem.bashhub.utils.Const;
import com.orem.bashhub.utils.OnSwipeListener;
import com.orem.bashhub.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class BashDetailsWebFragment extends BaseFragment {
    static Context mContext;
    static ArrayList<HashMap<String, String>> drinkList;
    static ArrayList<HashMap<String, String>> bottleList;
    static ArrayList<HashMap<String, String>> tableList;
    static ArrayList<HashMap<String, String>> sectionList;
    static ArrayList<HashMap<String, String>> serviceList;
    private static ActivityBashDetailBinding binding;
    private static BashDetailsPOJO data;
    private static SectionAdapter mSectionAdapter;
    private static TicketAdapter mTicketAdapter;
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

    public static void setDrinksData(BashDetailsPOJO datas, String from) {
        data = datas;
//        Toast.makeText(mContext, "clicked", Toast.LENGTH_SHORT).show();
        if (from.equalsIgnoreCase("drink")) {
            ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
            drinkList = new ArrayList<>();
            for (int i = 0; i < data.getDrinks().size(); i++) {
                HashMap<String, String> map = new HashMap<>();
                if (data.getDrinks().get(i).isChecked != null)
                    if (data.getDrinks().get(i).isChecked.equalsIgnoreCase("1")) {
                        map.put("id", data.getDrinks().get(i).getId());
                        map.put("price", data.getDrinks().get(i).getPrice());
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
                        map.put("id", data.getBottles().get(i).getId());
                        map.put("price", data.getBottles().get(i).getAmount());
                        map.put("name", data.getBottles().get(i).getName());
                        map.put("qty", data.getBottles().get(i).qty);
                        map.put("quantity", data.getBottles().get(i).getQuantity());
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
                        map.put("id", data.getTables().get(i).getId());
                        map.put("price", data.getTables().get(i).getPrice());
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
                        map.put("id", data.getSections().get(i).getId());
                        map.put("price", data.getSections().get(i).getPrice());
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
                        map.put("price", data.getProducts().get(i).getPrice());
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

    @Override
    public void onResume() {
        super.onResume();
        if (ticketList != null) {
            if (ticketList.size() > 0) {
                mTicketAdapter.notifyDataSetChanged();
            }
        }
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
        binding = DataBindingUtil.inflate(inflater, R.layout.activity_bash_detail, container, false);
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
                fragment.setData(data, "bottle", "detail");
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
                fragment.setData(data, "drink", "detail");
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
                fragment.setData(data, "service", "detail");
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
                fragment.setData(data, "table", "detail");
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
                fragment.setData(data, "section", "detail");
                Utils.goToFragment(mContext, fragment, R.id.fragment_container);
            }
        });
        binding.mTicketType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ticketType(binding.mTicketType);
            }
        });

        binding.setData(data);
        binding.executePendingBindings();
        binding.ivShare.setOnClickListener(v -> Utils.shareContent(mContext, data.getShareData(mContext)));
        binding.ivNavigation.setOnClickListener(v -> Utils.intentToMap(mContext, data.lat, data.lng));

        Log.e("event_image", Const.IMAGE_BASE_EVENT + data.eventImage);
        Utils.loadImage(mContext, Const.IMAGE_BASE_EVENT + data.eventImage, binding.mEventImage, R.drawable.placeholder);
        if (data.getHideNationalFact() == 1) {
            binding.mNationalFact.setVisibility(View.GONE);
        }
        if (data.spotifyImages != null)
            if (data.spotifyImages.equalsIgnoreCase("")) {
                binding.mImagesLayout.setVisibility(View.GONE);
            } else {
                spotifyLink = data.spotifyLink;
                if (data.spotifyUserName != null)
                    if (!data.spotifyUserName.equalsIgnoreCase(""))
                        binding.mSpotifyUserName.setText("@" + data.spotifyUserName);
                String spotifyImages = data.spotifyImages;
                SpotifyDetailPojo pojo = new Gson().fromJson(spotifyImages, SpotifyDetailPojo.class);
                binding.spotifyRecyclerView.setAdapter(new SpotifyDetailAdapter(mContext, pojo.getImages(), "detail"));

            }
        else binding.mImagesLayout.setVisibility(View.GONE);

        binding.rvHosts.setAdapter(new HostsAdapter(mContext, data.hosts, "web"));
        if (data.getVenue() != null)
            if (data.getVenue().getId() != null) {
                if (data.getDrinks() != null)
                    if (data.getDrinks().size() == 0)
                        binding.mDrinkLayout.setVisibility(View.GONE);
                    else binding.mDrinkLayout.setVisibility(View.VISIBLE);
                else binding.mDrinkLayout.setVisibility(View.GONE);

                if (data.getBottles() != null)
                    if (data.getBottles().size() > 0)
                        binding.mBottleLayout.setVisibility(View.VISIBLE);
                    else
                        binding.mBottleLayout.setVisibility(View.GONE);
                else binding.mBottleLayout.setVisibility(View.GONE);

                if (data.getTables() != null)
                    if (data.getTables().size() > 0)
                        binding.mTableLayout.setVisibility(View.VISIBLE);
                    else
                        binding.mTableLayout.setVisibility(View.GONE);
                else binding.mTableLayout.setVisibility(View.GONE);

                if (data.getSections() != null)
                    if (data.getSections().size() > 0)
                        binding.mSectionLayout.setVisibility(View.VISIBLE);
                    else
                        binding.mSectionLayout.setVisibility(View.GONE);
                else binding.mSectionLayout.setVisibility(View.GONE);
                if (data.getProducts() != null)
                    if (data.getProducts().size() > 0)
                        binding.mOtherServicesLayout.setVisibility(View.VISIBLE);
                    else
                        binding.mOtherServicesLayout.setVisibility(View.GONE);
                else binding.mOtherServicesLayout.setVisibility(View.GONE);

            } else {
                binding.mVenueMenuLayout.setVisibility(View.GONE);
            }
        else {
            binding.mVenueMenuLayout.setVisibility(View.GONE);
        }
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
            if (data.isIamHost(Const.getLoggedInUserID(mContext))) {
//                if (ticketList == null) {
//                    Toast.makeText(mContext, "Please select entry ticket", Toast.LENGTH_SHORT).show();
//                } else if (ticketList.size() == 0) {
//                    Toast.makeText(mContext, "Please select entry ticket", Toast.LENGTH_SHORT).show();
//                } else {
//                    ConfirmOrderFragment fragment = new ConfirmOrderFragment();
//                    fragment.setData(ticketId + "", binding.mTicketType.getText().toString(), "", ticketprice + "", drinkList, bottleList, tableList, sectionList);
//                    Utils.goToFragment(mContext, fragment, R.id.fragment_container);
//                    ((MainActivity) mContext).binding.fragmentContainer.setVisibility(View.VISIBLE);
//                }
                Utils.goToFragment(mContext, new MyBashFragment(false), R.id.fragment_container);
                ((MainActivity) mContext).binding.fragmentContainer.setVisibility(View.VISIBLE);
            } else {
                if (ticketList == null && drinkList == null && bottleList == null && tableList == null && sectionList == null && serviceList == null) {
                    Toast.makeText(mContext, "Please select any service to buy", Toast.LENGTH_SHORT).show();
                } else if (ticketList.size() == 0 && drinkList.size() == 0 && bottleList.size() == 0 && tableList.size() == 0 && sectionList.size() == 0 && serviceList.size() == 0) {
                    Toast.makeText(mContext, "Please select any service to buy", Toast.LENGTH_SHORT).show();
                } else {
                    ConfirmOrderFragment fragment = new ConfirmOrderFragment();
                    fragment.setData(data, ticketList, drinkList, bottleList, tableList, sectionList, serviceList, "1");
                    Utils.goToFragment(mContext, fragment, R.id.fragment_container);
                    ((MainActivity) mContext).binding.fragmentContainer.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    public void ticketType(AppCompatTextView mType) {
        final PopupMenu popup = new PopupMenu(mContext, mType);
        ArrayList rating = new ArrayList();
        for (int i = 0; i < data.getEventTickets().size(); i++) {
            if (data.getEventTickets().get(i).getQuantity() > 0)
                if (data.getEventTickets().get(i).getVisibility() == 1)
                    if (data.getEventTickets().get(i).getIsImmediatelySale() == 1)
                        rating.add(data.getEventTickets().get(i).getName());
                    else {
                        if (data.getEventTickets().get(i).getIsSaleEnd() == 1) {
                            rating.add(data.getEventTickets().get(i).getName());
                        } else if (Utils.isStartDate(data.getEventTickets().get(i).getSaleStartDate())) {
                            rating.add(data.getEventTickets().get(i).getName());
                        } else {
                        }
                    }
        }
        if (rating.size() > 0)
            for (int i = 0; i < rating.size(); i++) {
                popup.getMenu().add(Menu.NONE, (i + 1), Menu.NONE, rating.get(i).toString());
            }
        else {
            Toast.makeText(mContext, "No Ticket Found for This Event", Toast.LENGTH_SHORT).show();
        }
        popup.setOnMenuItemClickListener(item -> {
            mType.setText(item.getTitle());
            for (int i = 0; i < data.getEventTickets().size(); i++) {
                if (data.getEventTickets().get(i).getName().equalsIgnoreCase(mType.getText().toString())) {

                    if (ticketList == null)
                        ticketList = new ArrayList<>();
                    boolean isFind = false;
                    for (int j = 0; j < ticketList.size(); j++) {
                        if (data.getEventTickets().get(i).getId().toString().equalsIgnoreCase(ticketList.get(j).get("id"))) {
                            isFind = true;
                        }
                    }
                    if (isFind) {

                    } else {
                        HashMap<String, String> map = new HashMap<>();
                        map.put("is_advance", data.getEventTickets().get(i).getIsAdvance() + "");
                        map.put("is_demography", data.getEventTickets().get(i).getIsDemography() + "");
                        map.put("is_price_by_day", data.getEventTickets().get(i).getIsPriceByDay() + "");

                        map.put("before_male_time", data.getEventTickets().get(i).getBeforeMaleTime() + "");
                        map.put("before_male_date", data.getEventTickets().get(i).getBeforeMaleDate() + "");
                        map.put("before_male_price", data.getEventTickets().get(i).getBeforeMalePrice() + "");
                        map.put("after_male_time", data.getEventTickets().get(i).getAfterMaleTime() + "");
                        map.put("after_male_date", data.getEventTickets().get(i).getAfterMaleDate() + "");
                        map.put("after_male_price", data.getEventTickets().get(i).getAfterMalePrice() + "");

                        map.put("before_female_time", data.getEventTickets().get(i).getBeforeFemaleTime() + "");
                        map.put("before_female_date", data.getEventTickets().get(i).getBeforeFemaleDate() + "");
                        map.put("before_female_price", data.getEventTickets().get(i).getBeforeFemalePrice() + "");
                        map.put("after_female_time", data.getEventTickets().get(i).getAfterFemaleTime() + "");
                        map.put("after_female_date", data.getEventTickets().get(i).getAfterFemaleDate() + "");
                        map.put("after_female_price", data.getEventTickets().get(i).getAfterFemalePrice() + "");
                        map.put("selected_price", "0");

                        map.put("id", data.getEventTickets().get(i).getId() + "");
                        map.put("name", data.getEventTickets().get(i).getName());
                        map.put("total_qty", data.getEventTickets().get(i).getQuantity() + "");
                        if (data.getEventTickets().get(i).getQuantity() < data.getEventTickets().get(i).getPreOrderMax())
                            map.put("qty", data.getEventTickets().get(i).getQuantity() + "");
                        else
                            map.put("qty", data.getEventTickets().get(i).getPreOrderMax() + "");
                        map.put("min", data.getEventTickets().get(i).getPreOrderMin() + "");
                        map.put("select_qty", data.getEventTickets().get(i).getPreOrderMin() + "");
                        if (data.getEventTickets().get(i).getPrice() != null)
                            map.put("price", data.getEventTickets().get(i).getPrice() + "");
                        else map.put("price", "0");
                        if (data.getEventTickets().get(i).getIsDescription() == 0)
                            map.put("des", data.getEventTickets().get(i).getDescription() + "");
                        else
                            map.put("des", "");

                        map.put("male_price", data.getEventTickets().get(i).getPriceMale() + "");
                        map.put("female_price", data.getEventTickets().get(i).getPriceFemale() + "");

                        ticketList.add(map);
                        mTicketAdapter = new TicketAdapter(mContext);
                        binding.mTicketRV.setAdapter(mTicketAdapter);
                    }
                }
            }
            return false;
        });
        popup.show();
    }

    public void setData(BashDetailsPOJO data, boolean isDetailShow) {
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
            return new Holder(OrderRowBinding.inflate(LayoutInflater.from(mContext), parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            Holder h = (Holder) holder;
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
            return new Holder(OrderRowBinding.inflate(LayoutInflater.from(mContext), parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            Holder h = (Holder) holder;
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
            return new Holder(OrderRowBinding.inflate(LayoutInflater.from(mContext), parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            Holder h = (Holder) holder;
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
            return new Holder(TableRowBinding.inflate(LayoutInflater.from(mContext), parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            Holder h = (Holder) holder;
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
            return new Holder(TableRowBinding.inflate(LayoutInflater.from(mContext), parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            Holder h = (Holder) holder;
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

    private class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.ViewHolder> {
        Context mContext;

        public TicketAdapter(Context mContext) {
            this.mContext = mContext;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(TicketRowBinding.inflate(LayoutInflater.from(mContext), parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.binding.mDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ticketList.remove(position);
                    notifyItemRemoved(position);
                    notifyDataSetChanged();
                }
            });
            holder.binding.mTicketQuantity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ticketQuantity(holder.binding.mRemainingTickets, position, Integer.parseInt(ticketList.get(position).get("min")), Double.parseDouble(ticketList.get(position).get("price")), Integer.parseInt(ticketList.get(position).get("qty")), holder.binding.mTicketQuantity, holder.binding.tvCharge);
                }
            });
            holder.binding.mTicketName.setText(ticketList.get(position).get("name"));
            if (!ticketList.get(position).get("des").equalsIgnoreCase(""))
                holder.binding.mDescription.setText(ticketList.get(position).get("des"));
            else
                holder.binding.mDescription.setVisibility(View.GONE);
            holder.binding.mTicketQuantity.setText(ticketList.get(position).get("min"));
            holder.binding.mRemainingTickets.setText("Tickets Remaining: " + ticketList.get(position).get("total_qty"));
            if (ticketList.get(position).get("is_advance") != null)
                if (ticketList.get(position).get("is_advance").equalsIgnoreCase("1")) {
                    String gender = Const.getLoggedInUser(mContext).getGenderText(mContext);
                    if (ticketList.get(position).get("is_demography").equalsIgnoreCase("1") && ticketList.get(position).get("is_price_by_day").equalsIgnoreCase("1")) {
                        if (gender.equalsIgnoreCase("") || gender.equalsIgnoreCase(Const.MALE) || gender.equalsIgnoreCase(Const.NOT_SPECIFIED)) {
                            if (Utils.isValidDOB(ticketList.get(position).get("before_male_date"))) {
                                Double pp = Double.parseDouble(ticketList.get(position).get("min")) * Double.parseDouble(ticketList.get(position).get("before_male_price"));
                                String cost = Const.getCost(pp + "");
                                holder.binding.tvCharge.setText(cost);
                            } else {
                                Double pp = Double.parseDouble(ticketList.get(position).get("min")) * Double.parseDouble(ticketList.get(position).get("after_male_price"));
                                String cost = Const.getCost(pp + "");
                                holder.binding.tvCharge.setText(cost);
                            }

                        } else {
                            if (Utils.isValidDOB(ticketList.get(position).get("before_female_date"))) {
                                Double pp = Double.parseDouble(ticketList.get(position).get("min")) * Double.parseDouble(ticketList.get(position).get("before_female_price"));
                                String cost = Const.getCost(pp + "");
                                holder.binding.tvCharge.setText(cost);

                            } else {
                                Double pp = Double.parseDouble(ticketList.get(position).get("min")) * Double.parseDouble(ticketList.get(position).get("after_female_price"));
                                String cost = Const.getCost(pp + "");
                                holder.binding.tvCharge.setText(cost);

                            }
                        }
                    } else if (ticketList.get(position).get("is_demography").equalsIgnoreCase("1")) {

                        if (gender.equalsIgnoreCase("") || gender.equalsIgnoreCase(Const.MALE) || gender.equalsIgnoreCase(Const.NOT_SPECIFIED)) {
                            Double pp = Double.parseDouble(ticketList.get(position).get("min")) * Double.parseDouble(ticketList.get(position).get("male_price"));
                            String cost = Const.getCost(pp + "");
                            holder.binding.tvCharge.setText(cost);
                        } else {
                            Double pp = Double.parseDouble(ticketList.get(position).get("min")) * Double.parseDouble(ticketList.get(position).get("female_price"));
                            String cost = Const.getCost(pp + "");
                            holder.binding.tvCharge.setText(cost);
                        }
                    } else if (ticketList.get(position).get("is_price_by_day").equalsIgnoreCase("1")) {
                        if (gender.equalsIgnoreCase("") || gender.equalsIgnoreCase(Const.MALE) || gender.equalsIgnoreCase(Const.NOT_SPECIFIED)) {
                            if (Utils.isValidDOB(ticketList.get(position).get("before_male_date"))) {
                                Double pp = Double.parseDouble(ticketList.get(position).get("min")) * Double.parseDouble(ticketList.get(position).get("before_male_price"));

                                String cost = Const.getCost(pp + "");
                                holder.binding.tvCharge.setText(cost);

                            } else {
                                Double pp = Double.parseDouble(ticketList.get(position).get("min")) * Double.parseDouble(ticketList.get(position).get("after_male_price"));
                                String cost = Const.getCost(pp + "");
                                holder.binding.tvCharge.setText(cost);
                            }

                        } else {
                            if (Utils.isValidDOB(ticketList.get(position).get("before_female_date"))) {
                                Double pp = Double.parseDouble(ticketList.get(position).get("min")) * Double.parseDouble(ticketList.get(position).get("before_female_price"));
                                String cost = Const.getCost(pp + "");
                                holder.binding.tvCharge.setText(cost);

                            } else {
                                Double pp = Double.parseDouble(ticketList.get(position).get("min")) * Double.parseDouble(ticketList.get(position).get("after_female_price"));
                                String cost = Const.getCost(pp + "");
                                holder.binding.tvCharge.setText(cost);

                            }
                        }
                    } else {
                        Double pp = Double.parseDouble(ticketList.get(position).get("min")) * Double.parseDouble(ticketList.get(position).get("price"));
                        String cost = Const.getCost(pp + "");
                        holder.binding.tvCharge.setText(cost);
                    }
                } else {
                    Double pp = Double.parseDouble(ticketList.get(position).get("min")) * Double.parseDouble(ticketList.get(position).get("price"));
                    String cost = Const.getCost(pp + "");
                    holder.binding.tvCharge.setText(cost);
                }
            else {
                Double pp = Double.parseDouble(ticketList.get(position).get("min")) * Double.parseDouble(ticketList.get(position).get("price"));
                String cost = Const.getCost(pp + "");
                holder.binding.tvCharge.setText(cost);
            }
            HashMap<String, String> map = new HashMap<>();
            map.put("is_advance", ticketList.get(position).get("is_advance") + "");
            map.put("is_demography", ticketList.get(position).get("is_demography"));
            map.put("is_price_by_day", ticketList.get(position).get("is_price_by_day") + "");

            map.put("before_male_time", ticketList.get(position).get("before_male_time") + "");
            map.put("before_male_date", ticketList.get(position).get("before_male_date") + "");
            map.put("before_male_price", ticketList.get(position).get("before_male_price") + "");
            map.put("after_male_time", ticketList.get(position).get("after_male_time") + "");
            map.put("after_male_date", ticketList.get(position).get("after_male_date") + "");
            map.put("after_male_price", ticketList.get(position).get("after_male_price") + "");

            map.put("before_female_time", ticketList.get(position).get("before_female_time") + "");
            map.put("before_female_date", ticketList.get(position).get("before_female_date") + "");
            map.put("before_female_price", ticketList.get(position).get("before_female_price") + "");
            map.put("after_female_time", ticketList.get(position).get("after_female_time") + "");
            map.put("after_female_date", ticketList.get(position).get("after_female_date") + "");
            map.put("after_female_price", ticketList.get(position).get("after_female_price") + "");

            map.put("id", ticketList.get(position).get("id") + "");
            map.put("name", ticketList.get(position).get("name"));
            map.put("qty", ticketList.get(position).get("qty") + "");
            map.put("min", ticketList.get(position).get("min") + "");
            map.put("select_qty", ticketList.get(position).get("select_qty") + "");
            map.put("price", ticketList.get(position).get("price") + "");
            map.put("total_qty", ticketList.get(position).get("total_qty") + "");
            map.put("selected_price", ticketList.get(position).get("selected_price") + "");
            map.put("des", ticketList.get(position).get("des") + "");

            map.put("male_price", ticketList.get(position).get("male_price") + "");
            map.put("female_price", ticketList.get(position).get("female_price") + "");
            ticketList.set(position, map);
        }

        public void ticketQuantity(TextView mRemainingTickets, int position, int min, Double ticketprice, int ticketQTY, AppCompatTextView mType, TextView tvCharge) {
            final PopupMenu popup = new PopupMenu(mContext, mType);
            ArrayList rating = new ArrayList();
            for (int i = 0; i < ticketQTY; i++) {
                if ((i + 1) >= min)
                    rating.add((i + 1) + "");
            }

            for (int i = 0; i < rating.size(); i++) {
                popup.getMenu().add(Menu.NONE, (i + 1), Menu.NONE, rating.get(i).toString());
            }
            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    mType.setText(item.getTitle());
                    Double pp = ticketprice * Double.parseDouble(mType.getText().toString());
                    String cost = Const.getCost(pp + "");
                    tvCharge.setText(cost);
//                    int remain= Integer.parseInt( ticketList.get(position).get("qty"))-Integer.parseInt(mType.getText().toString());
//                    mRemainingTickets.setText("Per Order Max Tickets: "+remain);
                    HashMap<String, String> map = new HashMap<>();
                    map.put("is_advance", ticketList.get(position).get("is_advance") + "");
                    map.put("is_demography", ticketList.get(position).get("is_demography"));
                    map.put("is_price_by_day", ticketList.get(position).get("is_price_by_day") + "");

                    map.put("before_male_time", ticketList.get(position).get("before_male_time") + "");
                    map.put("before_male_date", ticketList.get(position).get("before_male_date") + "");
                    map.put("before_male_price", ticketList.get(position).get("before_male_price") + "");
                    map.put("after_male_time", ticketList.get(position).get("after_male_time") + "");
                    map.put("after_male_date", ticketList.get(position).get("after_male_date") + "");
                    map.put("after_male_price", ticketList.get(position).get("after_male_price") + "");

                    map.put("before_female_time", ticketList.get(position).get("before_female_time") + "");
                    map.put("before_female_date", ticketList.get(position).get("before_female_date") + "");
                    map.put("before_female_price", ticketList.get(position).get("before_female_price") + "");
                    map.put("after_female_time", ticketList.get(position).get("after_female_time") + "");
                    map.put("after_female_date", ticketList.get(position).get("after_female_date") + "");
                    map.put("after_female_price", ticketList.get(position).get("after_female_price") + "");

                    map.put("id", ticketList.get(position).get("id") + "");
                    map.put("name", ticketList.get(position).get("name"));
                    map.put("qty", ticketList.get(position).get("qty") + "");
                    map.put("min", ticketList.get(position).get("min") + "");
                    map.put("select_qty", mType.getText().toString());
                    map.put("price", ticketList.get(position).get("price") + "");
                    map.put("total_qty", ticketList.get(position).get("total_qty") + "");
                    map.put("selected_price", tvCharge.getText().toString().replace("$", ""));
                    map.put("des", ticketList.get(position).get("des") + "");

                    map.put("male_price", ticketList.get(position).get("male_price") + "");
                    map.put("female_price", ticketList.get(position).get("female_price") + "");
                    ticketList.set(position, map);
                    return false;
                }
            });
            popup.show();
        }


        @Override
        public int getItemCount() {
            return ticketList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TicketRowBinding binding;

            public ViewHolder(@NonNull TicketRowBinding itemView) {
                super(itemView.getRoot());
                binding = itemView;
            }
        }
    }
}