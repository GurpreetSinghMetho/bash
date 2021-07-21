package com.orem.bashhub.fragment;


import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.orem.bashhub.R;
import com.orem.bashhub.data.BashDetailsPOJO;
import com.orem.bashhub.data.VenueListPOJO;
import com.orem.bashhub.databinding.FragmentTicketsBinding;
import com.orem.bashhub.databinding.ItemBottleQrcodeBinding;
import com.orem.bashhub.databinding.ItemDrinkQrcodeBinding;
import com.orem.bashhub.databinding.ItemQrcodeBinding;
import com.orem.bashhub.databinding.ItemSectionQrBinding;
import com.orem.bashhub.databinding.ItemSectionQrcodeBinding;
import com.orem.bashhub.databinding.ItemServiceQrcodeBinding;
import com.orem.bashhub.databinding.ItemTableQrcodeBinding;
import com.orem.bashhub.databinding.VenueItemBottleQrcodeBinding;
import com.orem.bashhub.databinding.VenueItemDrinkQrcodeBinding;
import com.orem.bashhub.databinding.VenueItemSectionQrcodeBinding;
import com.orem.bashhub.databinding.VenueItemServiceQrcodeBinding;
import com.orem.bashhub.databinding.VenueItemTableQrcodeBinding;
import com.orem.bashhub.utils.Const;
import com.orem.bashhub.utils.MyPagerIndicator;
import com.orem.bashhub.utils.Utils;

import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

/**
 * A simple {@link Fragment} subclass.
 */
public class TicketsFragment extends BaseFragment {

    String from;
    private FragmentTicketsBinding binding;
    private BashDetailsPOJO data;
    private VenueListPOJO.Venue venueData;

    public TicketsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_tickets, container, false);
        ini();
        return binding.getRoot();
    }

    private void ini() {
        Utils.loadImage(mContext, R.drawable.ic_tickets_bg, binding.ivBG, 0);
//        binding.setData(data);
        binding.ivBack.setOnClickListener(v -> Objects.requireNonNull(getActivity()).onBackPressed());
        if (data != null) {
            if (from.equalsIgnoreCase("ticket")) {
                setTicketData();
            } else if (from.equalsIgnoreCase("drink")) {
                binding.mTitle.setText("Drinks");
                binding.ivTitle.setImageDrawable(mContext.getDrawable(R.drawable.ic_drinks));
                setDrinkData();
            } else if (from.equalsIgnoreCase("service")) {
                binding.mTitle.setText("Other Items");
                binding.ivTitle.setImageDrawable(mContext.getDrawable(R.drawable.other_services));
                setServiceData();
            } else if (from.equalsIgnoreCase("bottle")) {
                binding.ivTitle.setImageDrawable(mContext.getDrawable(R.drawable.ic_bottle));
                binding.mTitle.setText("Bottles");
                setBottleData();
            } else if (from.equalsIgnoreCase("table")) {
                binding.ivTitle.setImageDrawable(mContext.getDrawable(R.drawable.ic_table));
                setTableData();
                binding.mTitle.setText("Tables");
            } else if (from.equalsIgnoreCase("section")) {
                binding.ivTitle.setImageDrawable(mContext.getDrawable(R.drawable.ic_section));
                binding.mTitle.setText("Sections");
                setSectionData();
            }
        } else {
            if (from.equalsIgnoreCase("drink")) {
                binding.mTitle.setText("Drinks");
                binding.ivTitle.setImageDrawable(mContext.getDrawable(R.drawable.ic_drinks));
                setVenueDrinkData();
            } else if (from.equalsIgnoreCase("service")) {
                binding.mTitle.setText("Other Items");
                binding.ivTitle.setImageDrawable(mContext.getDrawable(R.drawable.other_services));
                setVenueServiceData();
            } else if (from.equalsIgnoreCase("bottle")) {
                binding.ivTitle.setImageDrawable(mContext.getDrawable(R.drawable.ic_bottle));
                binding.mTitle.setText("Bottles");
                setVenueBottleData();
            } else if (from.equalsIgnoreCase("table")) {
                binding.ivTitle.setImageDrawable(mContext.getDrawable(R.drawable.ic_table));
                setVenueTableData();
                binding.mTitle.setText("Tables");
            } else if (from.equalsIgnoreCase("section")) {
                binding.ivTitle.setImageDrawable(mContext.getDrawable(R.drawable.ic_section));
                binding.mTitle.setText("Sections");
                setVenueSectionData();
            } else {
                for (int i = 0; i < venueData.getItemSections().size(); i++) {
                    if (venueData.getItemSections().get(i).getId().toString().equalsIgnoreCase(from)) {
                        Glide.with(mContext).load(Const.IMAGE_BASE_EVENT + venueData.getItemSections().get(i).getImage()).into(binding.ivTitle);
                        binding.mTitle.setText(venueData.getItemSections().get(i).getName());
                        setVenueItemSectionData(venueData.getItemSections().get(i).getQr(), venueData.getItemSections().get(i).getName(), venueData.getItemSections().get(i).getId().toString());
                    }
                }
            }
        }
    }

    private void setVenueItemSectionData(List<VenueListPOJO.Qr> qr, String name, String id) {
        binding.ivShare.setOnClickListener(v -> Utils.shareContent(mContext, String.format(mContext.getString(R.string.ticket_venue_share_text), venueData.getName(), venueData.getName(), venueData.getAddress(), "", qr.get(binding.viewPager.getCurrentItem()).getCode(),
                "https://chart.googleapis.com/chart?chs=300x300&cht=qr&chl=BASH_" + qr.get(binding.viewPager.getCurrentItem()).getCode() + "&choe=UTF-8")));

        binding.viewPager.setAdapter(new VenueItemSectionQrCodeAdapter(mContext, qr, id));
        new MyPagerIndicator(mContext, binding.viewPager, qr.size(), binding.llPagerIndicator).setPagerIndicator();
        binding.tvTickets.setText(String.format(getString(R.string.tickets_count), Const.ONE, "" + qr.size()));

        binding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                binding.tvTickets.setText(String.format(getString(R.string.tickets_count), "" + (position + 1), "" + qr.size()));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void setBottleData() {
        binding.ivShare.setOnClickListener(v -> Utils.shareContent(mContext, String.format(mContext.getString(R.string.ticket_share_text1), data.name, data.name, data.getDate(), data.getTime(), data.location, data.getQrBottle().get(binding.viewPager.getCurrentItem()).getCode(),
                "https://chart.googleapis.com/chart?chs=300x300&cht=qr&chl=BASH_" + data.getQrBottle().get(binding.viewPager.getCurrentItem()).getCode() + "&choe=UTF-8")));

        binding.viewPager.setAdapter(new BottleQrCodeAdapter(mContext, data.getQrBottle()));
        new MyPagerIndicator(mContext, binding.viewPager, data.getQrBottle().size(), binding.llPagerIndicator).setPagerIndicator();
        binding.tvTickets.setText(String.format(getString(R.string.tickets_count), Const.ONE, "" + data.getQrBottle().size()));

        binding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                binding.tvTickets.setText(String.format(getString(R.string.tickets_count), "" + (position + 1), "" + data.getQrBottle().size()));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void setVenueBottleData() {
        binding.ivShare.setOnClickListener(v -> Utils.shareContent(mContext, String.format(mContext.getString(R.string.ticket_venue_share_text), venueData.getName(), venueData.getName(), venueData.getAddress(), "", venueData.getQrBottle().get(binding.viewPager.getCurrentItem()).getCode(),
                "https://chart.googleapis.com/chart?chs=300x300&cht=qr&chl=BASH_" + venueData.getQrBottle().get(binding.viewPager.getCurrentItem()).getCode() + "&choe=UTF-8")));

        binding.viewPager.setAdapter(new VenueBottleQrCodeAdapter(mContext, venueData.getQrBottle()));
        new MyPagerIndicator(mContext, binding.viewPager, venueData.getQrBottle().size(), binding.llPagerIndicator).setPagerIndicator();
        binding.tvTickets.setText(String.format(getString(R.string.tickets_count), Const.ONE, "" + venueData.getQrBottle().size()));

        binding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                binding.tvTickets.setText(String.format(getString(R.string.tickets_count), "" + (position + 1), "" + venueData.getQrBottle().size()));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void setTableData() {
        binding.ivShare.setOnClickListener(v -> Utils.shareContent(mContext, String.format(mContext.getString(R.string.ticket_share_text1), data.name, data.name, data.getDate(), data.getTime(), data.location, data.getQrTable().get(binding.viewPager.getCurrentItem()).getCode(),
                "https://chart.googleapis.com/chart?chs=300x300&cht=qr&chl=BASH_" + data.getQrTable().get(binding.viewPager.getCurrentItem()).getCode() + "&choe=UTF-8")));

        binding.viewPager.setAdapter(new TableQrCodeAdapter(mContext, data.getQrTable()));
        new MyPagerIndicator(mContext, binding.viewPager, data.getQrTable().size(), binding.llPagerIndicator).setPagerIndicator();
        binding.tvTickets.setText(String.format(getString(R.string.tickets_count), Const.ONE, "" + data.getQrTable().size()));

        binding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                binding.tvTickets.setText(String.format(getString(R.string.tickets_count), "" + (position + 1), "" + data.getQrTable().size()));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void setVenueTableData() {
        binding.ivShare.setOnClickListener(v -> Utils.shareContent(mContext, String.format(mContext.getString(R.string.ticket_venue_share_text), venueData.getName(), venueData.getName(), venueData.getAddress(), "", venueData.getQrTable().get(binding.viewPager.getCurrentItem()).getCode(),
                "https://chart.googleapis.com/chart?chs=300x300&cht=qr&chl=BASH_" + venueData.getQrTable().get(binding.viewPager.getCurrentItem()).getCode() + "&choe=UTF-8")));

        binding.viewPager.setAdapter(new VenueTableQrCodeAdapter(mContext, venueData.getQrTable()));
        new MyPagerIndicator(mContext, binding.viewPager, venueData.getQrTable().size(), binding.llPagerIndicator).setPagerIndicator();
        binding.tvTickets.setText(String.format(getString(R.string.tickets_count), Const.ONE, "" + venueData.getQrTable().size()));

        binding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                binding.tvTickets.setText(String.format(getString(R.string.tickets_count), "" + (position + 1), "" + venueData.getQrTable().size()));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void setSectionData() {
        binding.ivShare.setOnClickListener(v -> Utils.shareContent(mContext, String.format(mContext.getString(R.string.ticket_share_text1), data.name, data.name, data.getDate(), data.getTime(), data.location, data.getQrSection().get(binding.viewPager.getCurrentItem()).getCode(),
                "https://chart.googleapis.com/chart?chs=300x300&cht=qr&chl=BASH_" + data.getQrSection().get(binding.viewPager.getCurrentItem()).getCode() + "&choe=UTF-8")));

        binding.viewPager.setAdapter(new SectionQrCodeAdapter(mContext, data.getQrSection()));
        new MyPagerIndicator(mContext, binding.viewPager, data.getQrSection().size(), binding.llPagerIndicator).setPagerIndicator();
        binding.tvTickets.setText(String.format(getString(R.string.tickets_count), Const.ONE, "" + data.getQrSection().size()));

        binding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                binding.tvTickets.setText(String.format(getString(R.string.tickets_count), "" + (position + 1), "" + data.getQrSection().size()));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void setVenueSectionData() {
        binding.ivShare.setOnClickListener(v -> Utils.shareContent(mContext, String.format(mContext.getString(R.string.ticket_venue_share_text), venueData.getName(), venueData.getName(), venueData.getAddress(), "", venueData.getQrSection().get(binding.viewPager.getCurrentItem()).getCode(),
                "https://chart.googleapis.com/chart?chs=300x300&cht=qr&chl=BASH_" + venueData.getQrSection().get(binding.viewPager.getCurrentItem()).getCode() + "&choe=UTF-8")));

        binding.viewPager.setAdapter(new VenueSectionQrCodeAdapter(mContext, venueData.getQrSection()));
        new MyPagerIndicator(mContext, binding.viewPager, venueData.getQrSection().size(), binding.llPagerIndicator).setPagerIndicator();
        binding.tvTickets.setText(String.format(getString(R.string.tickets_count), Const.ONE, "" + venueData.getQrSection().size()));

        binding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                binding.tvTickets.setText(String.format(getString(R.string.tickets_count), "" + (position + 1), "" + venueData.getQrSection().size()));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void setDrinkData() {
        binding.ivShare.setOnClickListener(v -> Utils.shareContent(mContext, String.format(mContext.getString(R.string.ticket_share_text1), data.name, data.name, data.getDate(), data.getTime(), data.location, data.getQrDrink().get(binding.viewPager.getCurrentItem()).getCode(),
                "https://chart.googleapis.com/chart?chs=300x300&cht=qr&chl=BASH_" + data.getQrDrink().get(binding.viewPager.getCurrentItem()).getCode() + "&choe=UTF-8")));

        binding.viewPager.setAdapter(new DrinkQrCodeAdapter(mContext, data.getQrDrink()));
        new MyPagerIndicator(mContext, binding.viewPager, data.getQrDrink().size(), binding.llPagerIndicator).setPagerIndicator();
        binding.tvTickets.setText(String.format(getString(R.string.tickets_count), Const.ONE, "" + data.getQrDrink().size()));

        binding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                binding.tvTickets.setText(String.format(getString(R.string.tickets_count), "" + (position + 1), "" + data.getQrDrink().size()));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void setVenueDrinkData() {
        binding.ivShare.setOnClickListener(v -> Utils.shareContent(mContext, String.format(mContext.getString(R.string.ticket_venue_share_text), venueData.getName(), venueData.getName(), venueData.getAddress(), "", venueData.getQrDrink().get(binding.viewPager.getCurrentItem()).getCode(),
                "https://chart.googleapis.com/chart?chs=300x300&cht=qr&chl=BASH_" + venueData.getQrDrink().get(binding.viewPager.getCurrentItem()).getCode() + "&choe=UTF-8")));

        binding.viewPager.setAdapter(new VenueDrinkQrCodeAdapter(mContext, venueData.getQrDrink()));
        new MyPagerIndicator(mContext, binding.viewPager, venueData.getQrDrink().size(), binding.llPagerIndicator).setPagerIndicator();
        binding.tvTickets.setText(String.format(getString(R.string.tickets_count), Const.ONE, "" + venueData.getQrDrink().size()));

        binding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                binding.tvTickets.setText(String.format(getString(R.string.tickets_count), "" + (position + 1), "" + venueData.getQrDrink().size()));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void setServiceData() {
        binding.ivShare.setOnClickListener(v -> Utils.shareContent(mContext, String.format(mContext.getString(R.string.ticket_share_text1), data.name, data.name, data.getDate(), data.getTime(), data.location, data.getQrProduct().get(binding.viewPager.getCurrentItem()).getCode(),
                "https://chart.googleapis.com/chart?chs=300x300&cht=qr&chl=BASH_" + data.getQrProduct().get(binding.viewPager.getCurrentItem()).getCode() + "&choe=UTF-8")));

        binding.viewPager.setAdapter(new ServiceQrCodeAdapter(mContext, data.getQrProduct()));
        new MyPagerIndicator(mContext, binding.viewPager, data.getQrProduct().size(), binding.llPagerIndicator).setPagerIndicator();
        binding.tvTickets.setText(String.format(getString(R.string.tickets_count), Const.ONE, "" + data.getQrProduct().size()));

        binding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                binding.tvTickets.setText(String.format(getString(R.string.tickets_count), "" + (position + 1), "" + data.getQrProduct().size()));
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private void setVenueServiceData() {
        binding.ivShare.setOnClickListener(v -> Utils.shareContent(mContext, String.format(mContext.getString(R.string.ticket_venue_share_text), venueData.getName(), venueData.getName(), venueData.getAddress(), "", venueData.getQrProduct().get(binding.viewPager.getCurrentItem()).getCode(),
                "https://chart.googleapis.com/chart?chs=300x300&cht=qr&chl=BASH_" + venueData.getQrProduct().get(binding.viewPager.getCurrentItem()).getCode() + "&choe=UTF-8")));

        binding.viewPager.setAdapter(new VenueServiceQrCodeAdapter(mContext, venueData.getQrProduct()));
        new MyPagerIndicator(mContext, binding.viewPager, venueData.getQrProduct().size(), binding.llPagerIndicator).setPagerIndicator();
        binding.tvTickets.setText(String.format(getString(R.string.tickets_count), Const.ONE, "" + venueData.getQrProduct().size()));

        binding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                binding.tvTickets.setText(String.format(getString(R.string.tickets_count), "" + (position + 1), "" + venueData.getQrProduct().size()));
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private void setTicketData() {
        binding.ivShare.setOnClickListener(v -> Utils.shareContent(mContext, String.format(mContext.getString(R.string.ticket_share_text), data.name, data.name, data.getDate(), data.getTime(), data.location, data.getQrTicket().get(binding.viewPager.getCurrentItem()).getItem().getPrice() + "", data.getQrTicket().get(binding.viewPager.getCurrentItem()).getCode(),
                "https://chart.googleapis.com/chart?chs=300x300&cht=qr&chl=BASH_" + data.getQrTicket().get(binding.viewPager.getCurrentItem()).getCode() + "&choe=UTF-8")));

        binding.viewPager.setAdapter(new QrCodeAdapter(mContext, data.getQrTicket()));
        new MyPagerIndicator(mContext, binding.viewPager, data.getQrTicket().size(), binding.llPagerIndicator).setPagerIndicator();
        binding.tvTickets.setText(String.format(getString(R.string.tickets_count), Const.ONE, "" + data.getQrTicket().size()));

        binding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                binding.tvTickets.setText(String.format(getString(R.string.tickets_count), "" + (position + 1), "" + data.getQrTicket().size()));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void setData(BashDetailsPOJO data, String from) {
        this.data = data;
        this.from = from;
    }

    public void setVenueData(VenueListPOJO.Venue data, String from) {
        this.venueData = data;
        this.from = from;
    }

    public class QrCodeAdapter extends PagerAdapter {

        private Context mContext;
        private List<BashDetailsPOJO.QrTicket> list;

        public QrCodeAdapter(Context mContext, List<BashDetailsPOJO.QrTicket> list) {
            this.mContext = mContext;
            this.list = list;
        }

        public int getCount() {
            return list.size();
        }

        @NonNull
        public Object instantiateItem(@NonNull ViewGroup collection, final int position) {
            ItemQrcodeBinding itemBinding =
                    ItemQrcodeBinding.inflate(LayoutInflater.from(mContext), collection, false);
            itemBinding.setData(list.get(position));

            itemBinding.executePendingBindings();
            collection.addView(itemBinding.getRoot(), 0);
            return itemBinding.getRoot();
        }

        @Override
        public void destroyItem(@NonNull ViewGroup arg0, int arg1, @NonNull Object arg2) {
            arg0.removeView((View) arg2);
        }

        @Override
        public boolean isViewFromObject(@NonNull View arg0, @NonNull Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public Parcelable saveState() {
            return null;
        }
    }

    public class BottleQrCodeAdapter extends PagerAdapter {

        private Context mContext;
        private List<BashDetailsPOJO.QrBottle> list;

        public BottleQrCodeAdapter(Context mContext, List<BashDetailsPOJO.QrBottle> list) {
            this.mContext = mContext;
            this.list = list;
        }

        public int getCount() {
            return list.size();
        }

        @NonNull
        public Object instantiateItem(@NonNull ViewGroup collection, final int position) {
            ItemBottleQrcodeBinding itemBinding =
                    ItemBottleQrcodeBinding.inflate(LayoutInflater.from(mContext), collection, false);
            itemBinding.setData(list.get(position));
            itemBinding.executePendingBindings();

            collection.addView(itemBinding.getRoot(), 0);
            if (list.get(position).orderItem.size() == 0) {
                itemBinding.mViewDetail.setVisibility(View.GONE);
            }
            itemBinding.mViewDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    OrderDetailFragment fragment = new OrderDetailFragment();
                    fragment.setData(data, "bottle", position);
                    Utils.goToFragment(mContext, fragment, R.id.fragment_container);
                }
            });
            return itemBinding.getRoot();
        }

        @Override
        public void destroyItem(@NonNull ViewGroup arg0, int arg1, @NonNull Object arg2) {
            arg0.removeView((View) arg2);
        }

        @Override
        public boolean isViewFromObject(@NonNull View arg0, @NonNull Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public Parcelable saveState() {
            return null;
        }
    }

    public class VenueBottleQrCodeAdapter extends PagerAdapter {

        private Context mContext;
        private List<VenueListPOJO.QrBottle> list;


        public VenueBottleQrCodeAdapter(Context mContext, List<VenueListPOJO.QrBottle> list) {
            this.mContext = mContext;
            this.list = list;
        }

        public int getCount() {
            return list.size();
        }

        @NonNull
        public Object instantiateItem(@NonNull ViewGroup collection, final int position) {
            VenueItemBottleQrcodeBinding itemBinding =
                    VenueItemBottleQrcodeBinding.inflate(LayoutInflater.from(mContext), collection, false);
            itemBinding.setData(list.get(position));
            itemBinding.executePendingBindings();

            collection.addView(itemBinding.getRoot(), 0);
            if (list.get(position).getOrderItem().size() == 0) {
                itemBinding.mViewDetail.setVisibility(View.GONE);
            }
            itemBinding.mViewDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    OrderDetailFragment fragment = new OrderDetailFragment();
                    fragment.setVenueData(venueData, "bottle", position);
                    Utils.goToFragment(mContext, fragment, R.id.fragment_container);
                }
            });
            return itemBinding.getRoot();
        }

        @Override
        public void destroyItem(@NonNull ViewGroup arg0, int arg1, @NonNull Object arg2) {
            arg0.removeView((View) arg2);
        }

        @Override
        public boolean isViewFromObject(@NonNull View arg0, @NonNull Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public Parcelable saveState() {
            return null;
        }
    }

    public class DrinkQrCodeAdapter extends PagerAdapter {

        private Context mContext;
        private List<BashDetailsPOJO.QrDrink> list;

        public DrinkQrCodeAdapter(Context mContext, List<BashDetailsPOJO.QrDrink> list) {
            this.mContext = mContext;
            this.list = list;
        }

        public int getCount() {
            return list.size();
        }

        @NonNull
        public Object instantiateItem(@NonNull ViewGroup collection, final int position) {
            ItemDrinkQrcodeBinding itemBinding =
                    ItemDrinkQrcodeBinding.inflate(LayoutInflater.from(mContext), collection, false);
            itemBinding.setData(list.get(position));
            itemBinding.executePendingBindings();
            collection.addView(itemBinding.getRoot(), 0);
            if (list.get(position).orderItem.size() == 0) {
                itemBinding.mViewDetail.setVisibility(View.GONE);
            }
            itemBinding.mViewDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    OrderDetailFragment fragment = new OrderDetailFragment();
                    fragment.setData(data, "drink", position);
                    Utils.goToFragment(mContext, fragment, R.id.fragment_container);
                }
            });
            return itemBinding.getRoot();
        }

        @Override
        public void destroyItem(@NonNull ViewGroup arg0, int arg1, @NonNull Object arg2) {
            arg0.removeView((View) arg2);
        }

        @Override
        public boolean isViewFromObject(@NonNull View arg0, @NonNull Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public Parcelable saveState() {
            return null;
        }
    }

    public class VenueDrinkQrCodeAdapter extends PagerAdapter {

        private Context mContext;
        private List<VenueListPOJO.QrDrink> list;


        public VenueDrinkQrCodeAdapter(Context mContext, List<VenueListPOJO.QrDrink> list) {
            this.mContext = mContext;
            this.list = list;
        }

        public int getCount() {
            return list.size();
        }

        @NonNull
        public Object instantiateItem(@NonNull ViewGroup collection, final int position) {
            VenueItemDrinkQrcodeBinding itemBinding =
                    VenueItemDrinkQrcodeBinding.inflate(LayoutInflater.from(mContext), collection, false);
            itemBinding.setData(list.get(position));
            itemBinding.executePendingBindings();
            collection.addView(itemBinding.getRoot(), 0);
            if (list.get(position).getOrderItem().size() == 0) {
                itemBinding.mViewDetail.setVisibility(View.GONE);
            }
            itemBinding.mViewDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    OrderDetailFragment fragment = new OrderDetailFragment();
                    fragment.setVenueData(venueData, "drink", position);
                    Utils.goToFragment(mContext, fragment, R.id.fragment_container);
                }
            });
            return itemBinding.getRoot();
        }

        @Override
        public void destroyItem(@NonNull ViewGroup arg0, int arg1, @NonNull Object arg2) {
            arg0.removeView((View) arg2);
        }

        @Override
        public boolean isViewFromObject(@NonNull View arg0, @NonNull Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public Parcelable saveState() {
            return null;
        }
    }

    public class ServiceQrCodeAdapter extends PagerAdapter {

        private Context mContext;
        private List<BashDetailsPOJO.QrProduct> list;

        public ServiceQrCodeAdapter(Context mContext, List<BashDetailsPOJO.QrProduct> list) {
            this.mContext = mContext;
            this.list = list;
        }

        public int getCount() {
            return list.size();
        }

        @NonNull
        public Object instantiateItem(@NonNull ViewGroup collection, final int position) {
            ItemServiceQrcodeBinding itemBinding =
                    ItemServiceQrcodeBinding.inflate(LayoutInflater.from(mContext), collection, false);
            itemBinding.setData(list.get(position));
            itemBinding.executePendingBindings();
            collection.addView(itemBinding.getRoot(), 0);
            if (list.get(position).getOrderItem().size() == 0) {
                itemBinding.mViewDetail.setVisibility(View.GONE);
            }
            itemBinding.mViewDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    OrderDetailFragment fragment = new OrderDetailFragment();
                    fragment.setData(data, "service", position);
                    Utils.goToFragment(mContext, fragment, R.id.fragment_container);
                }
            });
            return itemBinding.getRoot();
        }

        @Override
        public void destroyItem(@NonNull ViewGroup arg0, int arg1, @NonNull Object arg2) {
            arg0.removeView((View) arg2);
        }

        @Override
        public boolean isViewFromObject(@NonNull View arg0, @NonNull Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public Parcelable saveState() {
            return null;
        }
    }

    public class VenueServiceQrCodeAdapter extends PagerAdapter {

        private Context mContext;
        private List<VenueListPOJO.QrProduct> list;


        public VenueServiceQrCodeAdapter(Context mContext, List<VenueListPOJO.QrProduct> list) {
            this.mContext = mContext;
            this.list = list;
        }

        public int getCount() {
            return list.size();
        }

        @NonNull
        public Object instantiateItem(@NonNull ViewGroup collection, final int position) {
            VenueItemServiceQrcodeBinding itemBinding =
                    VenueItemServiceQrcodeBinding.inflate(LayoutInflater.from(mContext), collection, false);
            itemBinding.setData(list.get(position));
            itemBinding.executePendingBindings();
            collection.addView(itemBinding.getRoot(), 0);
            if (list.get(position).getOrderItem().size() == 0) {
                itemBinding.mViewDetail.setVisibility(View.GONE);
            }
            itemBinding.mViewDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    OrderDetailFragment fragment = new OrderDetailFragment();
                    fragment.setVenueData(venueData, "service", position);
                    Utils.goToFragment(mContext, fragment, R.id.fragment_container);
                }
            });
            return itemBinding.getRoot();
        }

        @Override
        public void destroyItem(@NonNull ViewGroup arg0, int arg1, @NonNull Object arg2) {
            arg0.removeView((View) arg2);
        }

        @Override
        public boolean isViewFromObject(@NonNull View arg0, @NonNull Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public Parcelable saveState() {
            return null;
        }
    }

    public class TableQrCodeAdapter extends PagerAdapter {

        private Context mContext;
        private List<BashDetailsPOJO.QrTable> list;

        public TableQrCodeAdapter(Context mContext, List<BashDetailsPOJO.QrTable> list) {
            this.mContext = mContext;
            this.list = list;
        }

        public int getCount() {
            return list.size();
        }

        @NonNull
        public Object instantiateItem(@NonNull ViewGroup collection, final int position) {
            ItemTableQrcodeBinding itemBinding =
                    ItemTableQrcodeBinding.inflate(LayoutInflater.from(mContext), collection, false);
            itemBinding.setData(list.get(position));
            itemBinding.executePendingBindings();
            collection.addView(itemBinding.getRoot(), 0);
            if (list.get(position).orderItem.size() == 0) {
                itemBinding.mViewDetail.setVisibility(View.GONE);
            }
            itemBinding.mViewDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    OrderDetailFragment fragment = new OrderDetailFragment();
                    fragment.setData(data, "table", position);
                    Utils.goToFragment(mContext, fragment, R.id.fragment_container);
                }
            });
            return itemBinding.getRoot();
        }

        @Override
        public void destroyItem(@NonNull ViewGroup arg0, int arg1, @NonNull Object arg2) {
            arg0.removeView((View) arg2);
        }

        @Override
        public boolean isViewFromObject(@NonNull View arg0, @NonNull Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public Parcelable saveState() {
            return null;
        }
    }

    public class VenueTableQrCodeAdapter extends PagerAdapter {

        private Context mContext;
        private List<VenueListPOJO.QrTable> list;


        public VenueTableQrCodeAdapter(Context mContext, List<VenueListPOJO.QrTable> list) {
            this.mContext = mContext;
            this.list = list;
        }

        public int getCount() {
            return list.size();
        }

        @NonNull
        public Object instantiateItem(@NonNull ViewGroup collection, final int position) {
            VenueItemTableQrcodeBinding itemBinding =
                    VenueItemTableQrcodeBinding.inflate(LayoutInflater.from(mContext), collection, false);
            itemBinding.setData(list.get(position));
            itemBinding.executePendingBindings();
            collection.addView(itemBinding.getRoot(), 0);
            if (list.get(position).getOrderItem().size() == 0) {
                itemBinding.mViewDetail.setVisibility(View.GONE);
            }
            itemBinding.mViewDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    OrderDetailFragment fragment = new OrderDetailFragment();
                    fragment.setVenueData(venueData, "table", position);
                    Utils.goToFragment(mContext, fragment, R.id.fragment_container);
                }
            });
            return itemBinding.getRoot();
        }

        @Override
        public void destroyItem(@NonNull ViewGroup arg0, int arg1, @NonNull Object arg2) {
            arg0.removeView((View) arg2);
        }

        @Override
        public boolean isViewFromObject(@NonNull View arg0, @NonNull Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public Parcelable saveState() {
            return null;
        }
    }

    public class SectionQrCodeAdapter extends PagerAdapter {

        private Context mContext;
        private List<BashDetailsPOJO.QrSection> list;

        public SectionQrCodeAdapter(Context mContext, List<BashDetailsPOJO.QrSection> list) {
            this.mContext = mContext;
            this.list = list;
        }

        public int getCount() {
            return list.size();
        }

        @NonNull
        public Object instantiateItem(@NonNull ViewGroup collection, final int position) {
            ItemSectionQrcodeBinding itemBinding =
                    ItemSectionQrcodeBinding.inflate(LayoutInflater.from(mContext), collection, false);
            itemBinding.setData(list.get(position));
            itemBinding.executePendingBindings();
            if (list.get(position).orderItem.size() == 0) {
                itemBinding.mViewDetail.setVisibility(View.GONE);
            }
            itemBinding.mViewDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    OrderDetailFragment fragment = new OrderDetailFragment();
                    fragment.setData(data, "section", position);
                    Utils.goToFragment(mContext, fragment, R.id.fragment_container);
                }
            });
            collection.addView(itemBinding.getRoot(), 0);
            return itemBinding.getRoot();
        }

        @Override
        public void destroyItem(@NonNull ViewGroup arg0, int arg1, @NonNull Object arg2) {
            arg0.removeView((View) arg2);
        }

        @Override
        public boolean isViewFromObject(@NonNull View arg0, @NonNull Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public Parcelable saveState() {
            return null;
        }
    }

    public class VenueSectionQrCodeAdapter extends PagerAdapter {

        private Context mContext;
        private List<VenueListPOJO.QrSection> list;


        public VenueSectionQrCodeAdapter(Context mContext, List<VenueListPOJO.QrSection> list) {
            this.mContext = mContext;
            this.list = list;
        }

        public int getCount() {
            return list.size();
        }

        @NonNull
        public Object instantiateItem(@NonNull ViewGroup collection, final int position) {
            VenueItemSectionQrcodeBinding itemBinding =
                    VenueItemSectionQrcodeBinding.inflate(LayoutInflater.from(mContext), collection, false);
            itemBinding.setData(list.get(position));
            itemBinding.executePendingBindings();
            if (list.get(position).getOrderItem().size() == 0) {
                itemBinding.mViewDetail.setVisibility(View.GONE);
            }
            itemBinding.mViewDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    OrderDetailFragment fragment = new OrderDetailFragment();
                    fragment.setVenueData(venueData, "section", position);
                    Utils.goToFragment(mContext, fragment, R.id.fragment_container);
                }
            });
            collection.addView(itemBinding.getRoot(), 0);
            return itemBinding.getRoot();
        }

        @Override
        public void destroyItem(@NonNull ViewGroup arg0, int arg1, @NonNull Object arg2) {
            arg0.removeView((View) arg2);
        }

        @Override
        public boolean isViewFromObject(@NonNull View arg0, @NonNull Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public Parcelable saveState() {
            return null;
        }
    }

    private class VenueItemSectionQrCodeAdapter extends PagerAdapter {
        String id;
        private Context mContext;
        private List<VenueListPOJO.Qr> list;

        public VenueItemSectionQrCodeAdapter(Context mContext, List<VenueListPOJO.Qr> qr, String id) {
            this.mContext = mContext;
            this.list = qr;
            this.id = id;
        }


        public int getCount() {
            return list.size();
        }

        @NonNull
        public Object instantiateItem(@NonNull ViewGroup collection, final int position) {
            ItemSectionQrBinding itemBinding =
                    ItemSectionQrBinding.inflate(LayoutInflater.from(mContext), collection, false);
            itemBinding.setData(list.get(position));
            itemBinding.executePendingBindings();
            if (!id.equalsIgnoreCase(list.get(position).getItemSectionId().toString())) {
                itemBinding.getRoot().setLayoutParams(new ViewGroup.LayoutParams(0, 0));
            } else {
                if (list.get(position).getItems().size() == 0) {
                    itemBinding.mViewDetail.setVisibility(View.GONE);
                }
                itemBinding.mViewDetail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        OrderDetailFragment fragment = new OrderDetailFragment();
                        fragment.setItemSectionData(list.get(position).getItems(), "itemSection", position);
                        Utils.goToFragment(mContext, fragment, R.id.fragment_container);
                    }
                });
            }

            collection.addView(itemBinding.getRoot(), 0);
            return itemBinding.getRoot();
        }

        @Override
        public void destroyItem(@NonNull ViewGroup arg0, int arg1, @NonNull Object arg2) {
            arg0.removeView((View) arg2);
        }

        @Override
        public boolean isViewFromObject(@NonNull View arg0, @NonNull Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public Parcelable saveState() {
            return null;
        }
    }
}