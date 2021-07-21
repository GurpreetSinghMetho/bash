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

import com.orem.bashhub.R;
import com.orem.bashhub.adapter.HostsAdapter;
import com.orem.bashhub.data.BashDetailsPOJO;
import com.orem.bashhub.databinding.FragmentMyBashDetailBinding;
import com.orem.bashhub.databinding.TicketListRowBinding;
import com.orem.bashhub.utils.OnSwipeListener;
import com.orem.bashhub.utils.Utils;

import java.util.ArrayList;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class MyBashWebDetailFragment extends BaseFragment {
    static Context mContext;
    private static FragmentMyBashDetailBinding binding;
    private static BashDetailsPOJO data;
    private GestureDetector gestureDetector;
    private boolean isDetailShow = false;
    private ArrayList<String> qrList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_bash_detail, container, false);
        if (data != null) init();
        else Objects.requireNonNull(getActivity()).onBackPressed();
        return binding.getRoot();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void init() {
        mContext = getActivity();

        binding.setData(data);
        binding.executePendingBindings();
        binding.ivShare.setOnClickListener(v -> Utils.shareContent(mContext, data.getShareData(mContext)));
        binding.ivNavigation.setOnClickListener(v -> Utils.intentToMap(mContext, data.lat, data.lng));
        binding.ivRSVP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventUsersFragment fragment = new EventUsersFragment();
                fragment.setData(data);
                Utils.goToFragment(mContext, fragment, R.id.fragment_container);
            }
        });
        binding.rvHosts.setAdapter(new HostsAdapter(mContext, data.hosts, "web"));

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
        if (data.getProducts().size() > 0) {
            binding.mServiceLink.setVisibility(View.VISIBLE);
        } else binding.mServiceLink.setVisibility(View.GONE);
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
        qrList = new ArrayList<>();



        if (data.getQrTicket() != null)
            if (data.getQrTicket().size() > 0) {
                binding.mTicketLayout.setBackground(mContext.getDrawable(R.drawable.purple_button));
                binding.mTicketTXT.setTextColor(mContext.getColor(R.color.white));
                binding.mTicketImage.setImageDrawable(mContext.getDrawable(R.drawable.ic_tickets_white));
                qrList.add("ticket");

            }
        if (data.getQrProduct() != null)
            if (data.getQrProduct().size() > 0) {
                binding.mServiceLayout.setBackground(mContext.getDrawable(R.drawable.purple_button));
                binding.mServiceTXT.setTextColor(mContext.getColor(R.color.white));
                qrList.add("product");
            }
        if (data.getQrDrink() != null)
            if (data.getQrDrink().size() > 0) {
                binding.mDrinkLayout.setBackground(mContext.getDrawable(R.drawable.purple_button));
                binding.mDrinkTXT.setTextColor(mContext.getColor(R.color.white));
                qrList.add("drink");

            }
        if (data.getQrBottle() != null)
            if (data.getQrBottle().size() > 0) {
                binding.mBottleLayout.setBackground(mContext.getDrawable(R.drawable.purple_button));
                binding.mBottleTXT.setTextColor(mContext.getColor(R.color.white));
                qrList.add("bottle");

            }

        if (data.getQrTable() != null)
            if (data.getQrTable().size() > 0) {
                binding.mTableLayout.setBackground(mContext.getDrawable(R.drawable.purple_button));
                binding.mTableTXT.setTextColor(mContext.getColor(R.color.white));
                qrList.add("table");

            }
        if (data.getQrSection() != null)
            if (data.getQrSection().size() > 0) {
                binding.mSectionLayout.setBackground(mContext.getDrawable(R.drawable.purple_button));
                binding.mSectionTXT.setTextColor(mContext.getColor(R.color.white));
                qrList.add("section");

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
                fragment.setData(data, "section", "quick");
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
                fragment.setData(data, "table", "quick");
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
                fragment.setData(data, "bottle", "quick");
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
                fragment.setData(data, "service", "quick");
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
                fragment.setData(data, "drink", "quick");
                Utils.goToFragment(mContext, fragment, R.id.fragment_container);
            }
        });

        binding.mTicketLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (data.getQrTicket() != null)
                    if (data.getQrTicket().size() > 0) {
                        TicketsFragment fragment = new TicketsFragment();
                        fragment.setData(data, "ticket");
                        Utils.goToFragment(mContext, fragment, R.id.fragment_container);
                    }
            }
        });
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
        });

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


    public void setData(BashDetailsPOJO data, boolean isDetailShow) {
        this.data = data;
        this.isDetailShow = isDetailShow;
    }


    private class TicketListAdapter extends RecyclerView.Adapter<TicketListAdapter.ViewHolder> {
        ArrayList<String> qrList;

        public TicketListAdapter(ArrayList<String> qrList) {
            this.qrList = qrList;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(TicketListRowBinding.inflate(LayoutInflater.from(mContext), parent, false));

        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            if (qrList.get(position).toString().equalsIgnoreCase("product")) {
                holder.binding.mTicketImage.setImageDrawable(mContext.getDrawable(R.drawable.other_services));
              holder.  binding.mTicketTXT.setText("Other Items");
            } else if (qrList.get(position).toString().equalsIgnoreCase("ticket")) {
                holder.binding.mTicketImage.setImageDrawable(mContext.getDrawable(R.drawable.ic_tickets_white));
                holder.  binding.mTicketTXT.setText("Entry Tickets");
            } else if (qrList.get(position).toString().equalsIgnoreCase("drink")) {
                holder.binding.mTicketImage.setImageDrawable(mContext.getDrawable(R.drawable.ic_drinks));
                holder.  binding.mTicketTXT.setText("Drink Order");
            } else if (qrList.get(position).toString().equalsIgnoreCase("bottle")) {
                holder.binding.mTicketImage.setImageDrawable(mContext.getDrawable(R.drawable.ic_bottle));
                holder.  binding.mTicketTXT.setText("Bottle Service");
            } else if (qrList.get(position).toString().equalsIgnoreCase("table")) {
                holder.binding.mTicketImage.setImageDrawable(mContext.getDrawable(R.drawable.ic_table));
                holder.  binding.mTicketTXT.setText("Table Service");
            } else if (qrList.get(position).toString().equalsIgnoreCase("section")) {
                holder.binding.mTicketImage.setImageDrawable(mContext.getDrawable(R.drawable.ic_section));
                holder.  binding.mTicketTXT.setText("Sections");
            }
            holder.binding.mTicketLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (qrList.get(position).toString().equalsIgnoreCase("product")) {
                        TicketsFragment fragment = new TicketsFragment();
                        fragment.setData(data, "service");
                        Utils.goToFragment(mContext, fragment, R.id.fragment_container);
                        } else if (qrList.get(position).toString().equalsIgnoreCase("ticket")) {
                        TicketsFragment fragment = new TicketsFragment();
                        fragment.setData(data, "ticket");
                        Utils.goToFragment(mContext, fragment, R.id.fragment_container);
                         } else if (qrList.get(position).toString().equalsIgnoreCase("drink")) {
                        TicketsFragment fragment = new TicketsFragment();
                        fragment.setData(data, "drink");
                        Utils.goToFragment(mContext, fragment, R.id.fragment_container);
                       } else if (qrList.get(position).toString().equalsIgnoreCase("bottle")) {
                        TicketsFragment fragment = new TicketsFragment();
                        fragment.setData(data, "bottle");
                        Utils.goToFragment(mContext, fragment, R.id.fragment_container);
                       } else if (qrList.get(position).toString().equalsIgnoreCase("table")) {
                        TicketsFragment fragment = new TicketsFragment();
                        fragment.setData(data, "table");
                        Utils.goToFragment(mContext, fragment, R.id.fragment_container);
                        } else if (qrList.get(position).toString().equalsIgnoreCase("section")) {
                        TicketsFragment fragment = new TicketsFragment();
                        fragment.setData(data, "section");
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
}