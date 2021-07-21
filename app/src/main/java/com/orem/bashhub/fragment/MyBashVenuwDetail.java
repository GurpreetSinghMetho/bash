package com.orem.bashhub.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.bumptech.glide.Glide;
import com.orem.bashhub.R;
import com.orem.bashhub.data.VenueListPOJO;
import com.orem.bashhub.databinding.MyBashVenueDetailFragmentBinding;
import com.orem.bashhub.databinding.QuickLinkRowBinding;
import com.orem.bashhub.databinding.TicketListRowBinding;
import com.orem.bashhub.utils.Const;
import com.orem.bashhub.utils.OnSwipeListener;
import com.orem.bashhub.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class MyBashVenuwDetail extends BaseFragment {
    static Context mContext;
    private static MyBashVenueDetailFragmentBinding binding;
    private static VenueListPOJO.Venue data;
    private GestureDetector gestureDetector;
    private boolean isDetailShow = false;
    private ArrayList<HashMap<String, String>> qrList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.my_bash_venue_detail_fragment, container, false);
        if (data != null) init();
        else Objects.requireNonNull(getActivity()).onBackPressed();
        return binding.getRoot();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void init() {
        mContext = getActivity();

        binding.setData(data);
        binding.executePendingBindings();
        Utils.loadImage(mContext, Const.IMAGE_BASE_EVENT + data.getImage(), binding.mVenuImage, R.drawable.placeholder);
        binding.ivShare.setOnClickListener(v -> Utils.shareContent(mContext, data.getShareData(mContext)));
        binding.ivNavigation.setOnClickListener(v -> Utils.intentToMap(mContext, data.getLat(), data.getLng()));

        binding.fullLL.setVisibility(isDetailShow ? View.VISIBLE : View.GONE);

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
//        if (data.getProducts().size() > 0) {
//            binding.mServiceLink.setVisibility(View.VISIBLE);
//        } else binding.mServiceLink.setVisibility(View.GONE);
        if (data.getDrinks().size() > 0) {
            binding.mOrderDrinkLink.setVisibility(View.VISIBLE);
        } else binding.mOrderDrinkLink.setVisibility(View.GONE);
        if (data.getBottles().size() > 0) {
            binding.mOrderBottleLink.setVisibility(View.VISIBLE);
        } else binding.mOrderBottleLink.setVisibility(View.GONE);
        if (data.getTables().size() > 0) {
            binding.mOrderTableLink.setVisibility(View.VISIBLE);
        } else binding.mOrderTableLink.setVisibility(View.GONE);
        if (data.getSections().size() > 0) {
            binding.mOrderSectionLink.setVisibility(View.VISIBLE);
        } else binding.mOrderSectionLink.setVisibility(View.GONE);
        if (data.getItemSections().size() > 0) {
            binding.mItemSectionRV.setAdapter(new IteSectionAdapter(data.getItemSections()));
        } else binding.mOrderSectionLink.setVisibility(View.GONE);

        qrList = new ArrayList<>();


        if (data.getQrProduct() != null)
            if (data.getQrProduct().size() > 0) {
                binding.mServiceLayout.setBackground(mContext.getDrawable(R.drawable.purple_button));
                binding.mServiceTXT.setTextColor(mContext.getColor(R.color.white));
                HashMap map = new HashMap();
                map.put("title", "Product");
                map.put("image", "");
                qrList.add(map);
            }
        if (data.getQrDrink() != null)
            if (data.getQrDrink().size() > 0) {
                binding.mDrinkLayout.setBackground(mContext.getDrawable(R.drawable.purple_button));
                binding.mDrinkTXT.setTextColor(mContext.getColor(R.color.white));
                HashMap map = new HashMap();
                map.put("title", "drink");
                map.put("image", "");
                qrList.add(map);

            }
        if (data.getQrBottle() != null)
            if (data.getQrBottle().size() > 0) {
                binding.mBottleLayout.setBackground(mContext.getDrawable(R.drawable.purple_button));
                binding.mBottleTXT.setTextColor(mContext.getColor(R.color.white));
                HashMap map = new HashMap();
                map.put("title", "bottle");
                map.put("image", "");
                qrList.add(map);

            }

        if (data.getQrTable() != null)
            if (data.getQrTable().size() > 0) {
                binding.mTableLayout.setBackground(mContext.getDrawable(R.drawable.purple_button));
                binding.mTableTXT.setTextColor(mContext.getColor(R.color.white));
                HashMap map = new HashMap();
                map.put("title", "table");
                map.put("image", "");
                qrList.add(map);

            }
        if (data.getQrSection() != null)
            if (data.getQrSection().size() > 0) {
                binding.mSectionLayout.setBackground(mContext.getDrawable(R.drawable.purple_button));
                binding.mSectionTXT.setTextColor(mContext.getColor(R.color.white));
                HashMap map = new HashMap();
                map.put("title", "section");
                map.put("image", "");
                qrList.add(map);
            }
        for (int i = 0; i < data.getItemSections().size(); i++) {
            if (data.getItemSections().get(i).getQr().size() > 0) {
                HashMap map = new HashMap();
                map.put("title", data.getItemSections().get(i).getName());
                map.put("image", data.getItemSections().get(i).getImage());
                map.put("id", data.getItemSections().get(i).getId().toString());
                qrList.add(map);
            }
        }
        if (qrList.size() > 0) {
            binding.mTicketsRV.setAdapter(new TicketListAdapter(qrList));
        }
        binding.mOrderSectionLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for (int i = 0; i < data.getSections().size(); i++) {
                    data.getSections().get(i).isChecked = "0";
                }

                TableSectionListFragment fragment = new TableSectionListFragment();
                fragment.setVenueData(data, "section", "quick");
                Utils.goToFragment(mContext, fragment, R.id.fragment_container);
            }
        });

        binding.mOrderTableLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < data.getTables().size(); i++) {
                    data.getTables().get(i).isChecked = "0";
                }

                TableSectionListFragment fragment = new TableSectionListFragment();
                fragment.setVenueData(data, "table", "quick");
                Utils.goToFragment(mContext, fragment, R.id.fragment_container);
            }
        });

        binding.mOrderBottleLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < data.getBottles().size(); i++) {
                    data.getBottles().get(i).qty = "1";
                    data.getBottles().get(i).isChecked = "0";
                }

                DrinkBottleListFragment fragment = new DrinkBottleListFragment();
                fragment.setVenueData(data, "bottle", "quick", 0);
                Utils.goToFragment(mContext, fragment, R.id.fragment_container);
            }
        });
        binding.mServiceLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < data.getProducts().size(); i++) {
                    data.getProducts().get(i).qty = "1";
                    data.getProducts().get(i).isChecked = "0";
                }
                DrinkBottleListFragment fragment = new DrinkBottleListFragment();
                fragment.setVenueData(data, "service", "quick", 0);
                Utils.goToFragment(mContext, fragment, R.id.fragment_container);
            }
        });

        binding.mOrderDrinkLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < data.getDrinks().size(); i++) {
                    data.getDrinks().get(i).qty = "1";
                    data.getDrinks().get(i).isChecked = "0";
                }
                DrinkBottleListFragment fragment = new DrinkBottleListFragment();
                fragment.setVenueData(data, "drink", "quick", 0);
                Utils.goToFragment(mContext, fragment, R.id.fragment_container);
            }
        });
     /*
        binding.mDrinkLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (data.getQrDrink() != null)
                    if (data.getQrDrink().size() > 0) {
                        TicketsFragment fragment = new TicketsFragment();
                        fragment.setData(data, "drink");
                        Utils.goToFragment(mContext, fragment, R.id.fragment_container);
                    }
            }
        });
        binding.mServiceLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (data.getQrDrink() != null)
                    if (data.getQrDrink().size() > 0) {
                        TicketsFragment fragment = new TicketsFragment();
                        fragment.setData(data, "service");
                        Utils.goToFragment(mContext, fragment, R.id.fragment_container);
                    }
            }
        });
        binding.mBottleLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (data.getQrBottle() != null)
                    if (data.getQrBottle().size() > 0) {
                        TicketsFragment fragment = new TicketsFragment();
                        fragment.setData(data, "bottle");
                        Utils.goToFragment(mContext, fragment, R.id.fragment_container);
                    }
            }
        });
        binding.mTableLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (data.getQrTable() != null)
                    if (data.getQrTable().size() > 0) {
                        TicketsFragment fragment = new TicketsFragment();
                        fragment.setData(data, "table");
                        Utils.goToFragment(mContext, fragment, R.id.fragment_container);
                    }
            }
        });
        binding.mSectionLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (data.getQrSection() != null)
                    if (data.getQrSection().size() > 0) {
                        TicketsFragment fragment = new TicketsFragment();
                        fragment.setData(data, "section");
                        Utils.goToFragment(mContext, fragment, R.id.fragment_container);
                    }
            }
        });*/

     /*   binding.llCrashBash.setOnClickListener(v -> {
            if (data.isIamHost(Const.getLoggedInUserID(mContext))) {
                Utils.goToFragment(mContext, new MyBashFragment(), R.id.fragment_container);
                ((MainActivity) mContext).binding.fragmentContainer.setVisibility(View.VISIBLE);
            } else {
                if (ticketList == null) {
                    Toast.makeText(mContext, "Please select entry ticket", Toast.LENGTH_SHORT).show();
                } else if (ticketList.size() == 0) {
                    Toast.makeText(mContext, "Please select entry ticket", Toast.LENGTH_SHORT).show();
                } else {
                    ConfirmOrderFragment fragment = new ConfirmOrderFragment();
                    fragment.setData(data, ticketList, drinkList, bottleList, tableList, sectionList);
                    Utils.goToFragment(mContext, fragment, R.id.fragment_container);
                    ((MainActivity) mContext).binding.fragmentContainer.setVisibility(View.VISIBLE);
                }
            }
        });*/
    }


    public void setData(VenueListPOJO.Venue data, boolean isDetailShow) {
        this.data = data;
        this.isDetailShow = isDetailShow;
    }


    private class TicketListAdapter extends RecyclerView.Adapter<TicketListAdapter.ViewHolder> {
        ArrayList<HashMap<String, String>> qrList;

        public TicketListAdapter(ArrayList<HashMap<String, String>> qrList) {
            this.qrList = qrList;
        }

        @NonNull
        @Override
        public TicketListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new TicketListAdapter.ViewHolder(TicketListRowBinding.inflate(LayoutInflater.from(mContext), parent, false));

        }

        @Override
        public void onBindViewHolder(@NonNull TicketListAdapter.ViewHolder holder, int position) {
            if (qrList.get(position).get("title").toString().equalsIgnoreCase("product")) {
                holder.binding.mTicketImage.setImageDrawable(mContext.getDrawable(R.drawable.other_services));
                holder.binding.mTicketTXT.setText("Other Items");
            } else if (qrList.get(position).get("title").toString().equalsIgnoreCase("ticket")) {
                holder.binding.mTicketImage.setImageDrawable(mContext.getDrawable(R.drawable.ic_tickets_white));
                holder.binding.mTicketTXT.setText("Entry Tickets");
            } else if (qrList.get(position).get("title").toString().equalsIgnoreCase("drink")) {
                holder.binding.mTicketImage.setImageDrawable(mContext.getDrawable(R.drawable.ic_drinks));
                holder.binding.mTicketTXT.setText("Drink Order");
            } else if (qrList.get(position).get("title").toString().equalsIgnoreCase("bottle")) {
                holder.binding.mTicketImage.setImageDrawable(mContext.getDrawable(R.drawable.ic_bottle));
                holder.binding.mTicketTXT.setText("Bottle Service");
            } else if (qrList.get(position).get("title").toString().equalsIgnoreCase("table")) {
                holder.binding.mTicketImage.setImageDrawable(mContext.getDrawable(R.drawable.ic_table));
                holder.binding.mTicketTXT.setText("Table Service");
            } else if (qrList.get(position).get("title").toString().equalsIgnoreCase("section")) {
                holder.binding.mTicketImage.setImageDrawable(mContext.getDrawable(R.drawable.ic_section));
                holder.binding.mTicketTXT.setText("Sections");
            } else {
                Glide.with(mContext).load(Const.IMAGE_BASE_EVENT + qrList.get(position).get("image")).into(holder.binding.mTicketImage);
                holder.binding.mTicketTXT.setText(qrList.get(position).get("title"));
            }
            holder.binding.mTicketLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (qrList.get(position).get("title").toString().equalsIgnoreCase("product")) {
                        TicketsFragment fragment = new TicketsFragment();
                        fragment.setVenueData(data, "service");
                        Utils.goToFragment(mContext, fragment, R.id.fragment_container);
                    } else if (qrList.get(position).get("title").toString().equalsIgnoreCase("ticket")) {
                        TicketsFragment fragment = new TicketsFragment();
                        fragment.setVenueData(data, "ticket");
                        Utils.goToFragment(mContext, fragment, R.id.fragment_container);
                    } else if (qrList.get(position).get("title").toString().equalsIgnoreCase("drink")) {
                        TicketsFragment fragment = new TicketsFragment();
                        fragment.setVenueData(data, "drink");
                        Utils.goToFragment(mContext, fragment, R.id.fragment_container);
                    } else if (qrList.get(position).get("title").toString().equalsIgnoreCase("bottle")) {
                        TicketsFragment fragment = new TicketsFragment();
                        fragment.setVenueData(data, "bottle");
                        Utils.goToFragment(mContext, fragment, R.id.fragment_container);
                    } else if (qrList.get(position).get("title").toString().equalsIgnoreCase("table")) {
                        TicketsFragment fragment = new TicketsFragment();
                        fragment.setVenueData(data, "table");
                        Utils.goToFragment(mContext, fragment, R.id.fragment_container);
                    } else if (qrList.get(position).get("title").toString().equalsIgnoreCase("section")) {
                        TicketsFragment fragment = new TicketsFragment();
                        fragment.setVenueData(data, "section");
                        Utils.goToFragment(mContext, fragment, R.id.fragment_container);
                    } else {
                        TicketsFragment fragment = new TicketsFragment();
                        fragment.setVenueData(data, qrList.get(position).get("id"));
                        Utils.goToFragment(mContext, fragment, R.id.fragment_container);
                    }
                }
            });

        }

        @Override
        public int getItemCount() {
            return qrList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TicketListRowBinding binding;

            public ViewHolder(@NonNull TicketListRowBinding itemView) {
                super(itemView.getRoot());
                binding = itemView;
            }
        }
    }

    private class IteSectionAdapter extends RecyclerView.Adapter<IteSectionAdapter.ViewHolder> {
        List<VenueListPOJO.ItemSection> itemSections;

        public IteSectionAdapter(List<VenueListPOJO.ItemSection> itemSections) {
            this.itemSections = itemSections;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(QuickLinkRowBinding.inflate(LayoutInflater.from(mContext), parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.binding.mName.setText(itemSections.get(position).getName());
            holder.binding.mServiceLink.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DrinkBottleListFragment fragment = new DrinkBottleListFragment();
                    fragment.setVenueData(data, "itemSection", "quick", position);
                    Utils.goToFragment(mContext, fragment, R.id.fragment_container);
                }
            });
        }

        @Override
        public int getItemCount() {
            return itemSections.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            QuickLinkRowBinding binding;

            public ViewHolder(@NonNull QuickLinkRowBinding itemView) {
                super(itemView.getRoot());
                binding = itemView;
            }
        }
    }
}