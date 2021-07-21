package com.orem.bashhub.data;

import android.content.Context;
import android.text.Html;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.orem.bashhub.R;
import com.orem.bashhub.utils.Const;
import com.orem.bashhub.utils.Utils;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

public class BashDetailsPOJO extends BaseObservable {
    @SerializedName("event_image")
    public String eventImage;
    @SerializedName("user_data")
    public User_data user_data;
    @SerializedName("ticket_data")
    public Ticket_data ticket_data;
    @SerializedName("hosts")
    public List<Hosts> hosts;
    @SerializedName("delete")
    public String delete;
    @SerializedName("repeat_end_date")
    public String repeatEndDate;
    @SerializedName("category")
    public String category;
    @SerializedName("description")
    public String description;
    @SerializedName("is_crash")
    public String is_crash;
    @SerializedName("end_date")
    public String end_date;
    @SerializedName("distance")
    public String distance;
    @SerializedName("ratings")
    public String ratings;
    @SerializedName("not_specified")
    public String not_specified;
    @SerializedName("fe_male")
    public String fe_male;
    @SerializedName("male")
    public String male;
    @SerializedName("is_private")
    public String is_private;
    @SerializedName("session_id")
    public String session_id;
    @SerializedName("spotify_images")
    public String spotifyImages;
    @SerializedName("spotify_link")
    public String spotifyLink;
    @SerializedName("spotify_user_name")
    public String spotifyUserName;
    @SerializedName("hide_national_fact")
    public Integer hideNationalFact;
    @SerializedName("insta_link")
    public String instaLink;
    @SerializedName("insta_images")
    public String instaImages;
    @SerializedName("created_at")
    public String created_at;
    @SerializedName("updated_at")
    public String updated_at;
    @SerializedName("age_max")
    public String age_max;
    @SerializedName("age")
    public String age;
    @SerializedName("charge")
    public String charge;
    @SerializedName("lng")
    public String lng;
    @SerializedName("lat")
    public String lat;
    @SerializedName("location")
    public String location;
    @SerializedName("end_time")
    public String end_time;
    @SerializedName("start_time")
    public String start_time;
    @SerializedName("start_date")
    public String start_date;
    @SerializedName("name_of_host")
    public String name_of_host;
    @SerializedName("name")
    public String name;
    @SerializedName("bash_type")
    public String bash_type;
    @SerializedName("user_id")
    public String user_id;
    @SerializedName("id")
    public String id;
    @SerializedName("event_tickets")
    private List<EventTicket> eventTickets = null;
    @SerializedName("venue_id")

    private String venueId;
    @SerializedName("slug")

    private String slug;
    @SerializedName("venue_name")

    private String venueName;
    @SerializedName("street_address")

    private String streetAddress;
    @SerializedName("city")

    private String city;
    @SerializedName("state")

    private String state;
    @SerializedName("zipcode")

    private String zipcode;
    @SerializedName("country")

    private String country;
    @SerializedName("is_advance")

    private String isAdvance;
    @SerializedName("refund_policy")

    private String refundPolicy;
    @SerializedName("is_remain_ticket")

    private String isRemainTicket;
    @SerializedName("no_venue")

    private String noVenue;
    @SerializedName("repeat_type")

    private String repeatType;
    @SerializedName("category_id")

    private String categoryId;
    @SerializedName("is_spotify")

    private String isSpotify;
    @SerializedName("status")

    private String status;
    @SerializedName("attendees")

    private String attendees;
    @SerializedName("type")

    private String type;
    @SerializedName("min_budget")

    private String minBudget;
    @SerializedName("max_budget")

    private String maxBudget;
    @SerializedName("miles")

    private String miles;
    @SerializedName("venue_description")

    private String venueDescription;
    @SerializedName("bid_confirm")

    private String bidConfirm;
    @SerializedName("created_from")

    private Integer createdFrom;
    @SerializedName("bottles")

    private List<Bottle> bottles = null;
    @SerializedName("tables")

    private List<Table> tables = null;
    @SerializedName("sections")

    private List<Section> sections = null;
    @SerializedName("drinks")

    private List<Drink> drinks = null;
    @SerializedName("products")
    @Expose
    private List<Product> products = null;
    @SerializedName("complement")

    private String complement;
    @SerializedName("complement1")

    private String complement1;
    @SerializedName("complement2")

    private String complement2;
    @SerializedName("complement3")

    private String complement3;
    @SerializedName("complement4")

    private String complement4;
    @SerializedName("complement5")

    private String complement5;
    @SerializedName("venue")

    private Venue venue;
    @SerializedName("qr_ticket")
    @Expose
    private List<QrTicket> qrTicket = null;
    @SerializedName("qr_product")
    @Expose
    private List<QrProduct> qrProduct = null;
    @SerializedName("qr_drink")
    @Expose
    private List<QrDrink> qrDrink = null;
    @SerializedName("qr_bottle")
    @Expose
    private List<QrBottle> qrBottle = null;
    @SerializedName("qr_table")
    @Expose
    private List<QrTable> qrTable = null;
    @SerializedName("qr_section")
    @Expose
    private List<QrSection> qrSection = null;
    private String tickets = Const.ONE;

    public List<QrProduct> getQrProduct() {
        return qrProduct;
    }

    public void setQrProduct(List<QrProduct> qrProduct) {
        this.qrProduct = qrProduct;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<QrTicket> getQrTicket() {
        return qrTicket;
    }

    public void setQrTicket(List<QrTicket> qrTicket) {
        this.qrTicket = qrTicket;
    }

    public List<QrDrink> getQrDrink() {
        return qrDrink;
    }

    public void setQrDrink(List<QrDrink> qrDrink) {
        this.qrDrink = qrDrink;
    }

    public List<QrBottle> getQrBottle() {
        return qrBottle;
    }

    public void setQrBottle(List<QrBottle> qrBottle) {
        this.qrBottle = qrBottle;
    }

    public List<QrTable> getQrTable() {
        return qrTable;
    }

    public void setQrTable(List<QrTable> qrTable) {
        this.qrTable = qrTable;
    }

    public List<QrSection> getQrSection() {
        return qrSection;
    }

    public void setQrSection(List<QrSection> qrSection) {
        this.qrSection = qrSection;
    }

    public String getEventImages() {
        return Const.IMAGE_BASE_EVENT + eventImage;
    }

    public List<EventTicket> getEventTickets() {
        return eventTickets;
    }

    public void setEventTickets(List<EventTicket> eventTickets) {
        this.eventTickets = eventTickets;
    }

    public Venue getVenue() {
        return venue;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }

    public String getVenueId() {
        return venueId;
    }

    public void setVenueId(String venueId) {
        this.venueId = venueId;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getVenueName() {
        return venueName;
    }

    public void setVenueName(String venueName) {
        this.venueName = venueName;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setEventImage(String eventImage) {
        this.eventImage = eventImage;
    }

    public String getIsAdvance() {
        return isAdvance;
    }

    public void setIsAdvance(String isAdvance) {
        this.isAdvance = isAdvance;
    }

    public String getRefundPolicy() {
        return refundPolicy;
    }

    public void setRefundPolicy(String refundPolicy) {
        this.refundPolicy = refundPolicy;
    }

    public String getIsRemainTicket() {
        return isRemainTicket;
    }

    public void setIsRemainTicket(String isRemainTicket) {
        this.isRemainTicket = isRemainTicket;
    }

    public String getNoVenue() {
        return noVenue;
    }

    public void setNoVenue(String noVenue) {
        this.noVenue = noVenue;
    }

    public String getRepeatType() {
        return repeatType;
    }

    public void setRepeatType(String repeatType) {
        this.repeatType = repeatType;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getIsSpotify() {
        return isSpotify;
    }

    public void setIsSpotify(String isSpotify) {
        this.isSpotify = isSpotify;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAttendees() {
        return attendees;
    }

    public void setAttendees(String attendees) {
        this.attendees = attendees;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMinBudget() {
        return minBudget;
    }

    public void setMinBudget(String minBudget) {
        this.minBudget = minBudget;
    }

    public String getMaxBudget() {
        return maxBudget;
    }

    public void setMaxBudget(String maxBudget) {
        this.maxBudget = maxBudget;
    }

    public String getMiles() {
        return miles;
    }

    public void setMiles(String miles) {
        this.miles = miles;
    }

    public String getVenueDescription() {
        return venueDescription;
    }

    public void setVenueDescription(String venueDescription) {
        this.venueDescription = venueDescription;
    }

    public String getStartDate() {
        return Utils.changeDateFormat(start_date);
    }

    public String getEndDate() {
        return Utils.changeDateFormat(end_date);
    }

    public String getEndTime() {
        return Utils.changeTimeFormat(end_time);
    }

    public String getStartTime() {
        return Utils.changeTimeFormat(start_time);
    }

    public String getBidConfirm() {
        return bidConfirm;
    }

    public void setBidConfirm(String bidConfirm) {
        this.bidConfirm = bidConfirm;
    }

    public Integer getCreatedFrom() {
        return createdFrom;
    }

    public void setCreatedFrom(Integer createdFrom) {
        this.createdFrom = createdFrom;
    }

    public List<Bottle> getBottles() {
        return bottles;
    }

    public void setBottles(List<Bottle> bottles) {
        this.bottles = bottles;
    }

    public List<Table> getTables() {
        return tables;
    }

    public void setTables(List<Table> tables) {
        this.tables = tables;
    }

    public List<Section> getSections() {
        return sections;
    }

    public void setSections(List<Section> sections) {
        this.sections = sections;
    }

    public List<Drink> getDrinks() {
        return drinks;
    }

    public void setDrinks(List<Drink> drinks) {
        this.drinks = drinks;
    }

    public String getComplement() {
        return complement;
    }

    public void setComplement(String complement) {
        this.complement = complement;
    }

    public String getComplement1() {
        return complement1;
    }

    public void setComplement1(String complement1) {
        this.complement1 = complement1;
    }

    public String getComplement2() {
        return complement2;
    }

    public void setComplement2(String complement2) {
        this.complement2 = complement2;
    }

    public String getComplement3() {
        return complement3;
    }

    public void setComplement3(String complement3) {
        this.complement3 = complement3;
    }

    public String getComplement4() {
        return complement4;
    }

    public void setComplement4(String complement4) {
        this.complement4 = complement4;
    }

    public String getComplement5() {
        return complement5;
    }

    public void setComplement5(String complement5) {
        this.complement5 = complement5;
    }

    public Integer getHideNationalFact() {
        return hideNationalFact;
    }

    public void setHideNationalFact(Integer hideNationalFact) {
        this.hideNationalFact = hideNationalFact;
    }

    public String getDescription() {
        if (description != null)
            return Html.fromHtml(description).toString();
        else return "";
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Bindable
    public String getTickets() {
        return tickets;
    }

    public void setTickets(String tickets) {
        this.tickets = tickets;
        notifyChange();
    }

    public String getChargeText() {
        return getCharge() + " x " + getTickets();
    }

    public double getTotalCost() {
        return Double.parseDouble(charge) * Integer.parseInt(tickets);
    }

    public String getTotalCostText() {
        return Const.getCost("" + getTotalCost());
    }

    public String getChargeTextFull(Context mContext) {
        return mContext.getString(R.string.cover) + " " + getChargeText() + " = " + getTotalCostText();
    }

    public String getTicketQty() {
        if (ticket_data.qty == null) {
            return Const.ZERO;
        } else if (Integer.parseInt(ticket_data.qty) <= 1)
            return ticket_data.qty;
        else
            return ticket_data.qty;
    }

    public boolean isIamHost(String id) {
        boolean isHost = false;
        for (Hosts item : hosts) {
            if (item.id.equals(id)) {
                isHost = true;
                break;
            }
        }
        return isHost;
    }

    public int getEventIcon() {
        return bash_type.equals(Const.EVENT_RESTAURANT) ? R.drawable.ic_restaurant_selected : (bash_type.equals(Const.EVENT_CLUB) ? R.drawable.ic_club_selected : R.drawable.ic_bar_selected);
    }

    public int getEventCategoryIcon() {
        if (category.equalsIgnoreCase("")) {
            return R.drawable.transparent;
        } else
            return category.equals(Const.VIRAL) ? R.drawable.viral : category.equals(Const.POOL_PARTY) ? R.drawable.bikini : category.equals(Const.QUEER_FRIENDLY) ? R.drawable.gay : category.equals(Const.DESI_PARTY) ? R.drawable.flag : category.equals(Const.GONE_COUNTRY) ? R.drawable.cowboy : category.equals(Const.KARAOKE_TIME) ? R.drawable.mic : R.drawable.transparent;
    }

    public String getTime() {
        return Utils.changeTimeFormat(start_time) + " - " + Utils.changeTimeFormat(end_time);
    }

    public String getCharge() {
        return Const.getCost(charge);
    }

    public String getTotalMember() {
        return "" + (Integer.parseInt(male) + Integer.parseInt(fe_male) + Integer.parseInt(not_specified));
    }

    public String getDistanceText(Context mContext) {
        return Utils.getTwoDigitsAfterDecimal(distance) + mContext.getString(R.string.miles);
    }

    public String getDate() {
        return Utils.changeDateFormat(start_date) + " - " + Utils.changeDateFormat(end_date);
    }

    public String getDateHost() {
        return Utils.changeDateFormatHost(end_date);
    }

    public String getDetailDateTime() {
        return getDate() + "\n" + getTime();
    }

    public String getHostName() {
        String hostNames = "";
        for (Hosts item : hosts)
            hostNames = hostNames.isEmpty() ? item.username : hostNames + "," + item.username;
        return hostNames;
    }

    public String getDateRange() {
        return start_date + " - " + end_date;
    }

    @Bindable
    public String getIs_crash() {
        return is_crash;
    }

    public void setIs_crash(String is_crash) {
        this.is_crash = is_crash;
        notifyChange();
    }

    public String getShareData(Context mContext) {
        return String.format(mContext.getString(R.string.bash_share_text), name, getDate(), location,
                Const.getShareLink(mContext, id));
    }

    public boolean isPlusVisible(String id) {
//        if (isIamHost(id)==true) {
//            return false;
//        } else
        return getIs_crash().equals(Const.ZERO) && !isIamHost(id);
    }

    public boolean isTickVisible(String id) {
       /* if (isIamHost(id)==false) {
            return true;
        } else*/
        return getIs_crash().equals(Const.ONE) || isIamHost(id);
    }

    public boolean isTicketVisible() {
        return qrTicket != null && qrTicket.size() > 0;
    }

    public String getTicketSize() {
        return qrTicket.size() + "";
    }

    public boolean isCountDropVisible() {
        if (charge != null)
            return Double.parseDouble(charge) > 0;
        else return false;
    }

    public boolean isDesVisible() {
        return description != null && !description.isEmpty();
    }

    public void navigationClick(Context mContext) {
        Utils.intentToMap(mContext, lat, lng);
    }

    public void shareClick(Context mContext) {
        Utils.shareContent(mContext, String.format(mContext.getString(R.string.bash_share_text), name, getDate(), location,
                Const.getShareLink(mContext, id)));
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return obj instanceof BashDetailsPOJO && ((BashDetailsPOJO) obj).id.equalsIgnoreCase(id);
    }

    public static class User_data {
        @SerializedName("image")
        public String image;
        @SerializedName("username")
        public String username;
        @SerializedName("lname")
        public String lname;
        @SerializedName("fname")
        public String fname;
        @SerializedName("id")
        public int id;

        public String getFullName() {
            return fname + " " + lname;
        }
    }

    public static class Ticket_data {
        @SerializedName("barcodedata")
        public List<Barcodedata> barcodedata;
        @SerializedName("amount")
        public String amount;
        @SerializedName("updated_at")
        public String updated_at;
        @SerializedName("created_at")
        public String created_at;
        @SerializedName("qty")
        public String qty;
        @SerializedName("bash_id")
        public int bash_id;
        @SerializedName("user_id")
        public int user_id;
        @SerializedName("id")
        public String id;
    }

    public static class Barcodedata {
        @SerializedName("used")
        public String used;
        @SerializedName("updated_at")
        public String updated_at;
        @SerializedName("created_at")
        public String created_at;
        @SerializedName("bash_id")
        public int bash_id;
        @SerializedName("ticket_id")
        public String ticket_id;
        @SerializedName("code")
        public String code;
        @SerializedName("id")
        public int id;


        public String getTicketCode(Context mContext) {
            return mContext.getString(R.string.ticket_id) + " : " + code;
        }
    }

    public static class Hosts {
        @SerializedName("username")
        public String username;
        @SerializedName("lname")
        public String lname;
        @SerializedName("fname")
        public String fname;
        @SerializedName("id")
        public String id;
    }

    public class QrProduct {

        @SerializedName("ticket_id")
        @Expose
        Integer ticketId;
        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("code")
        @Expose
        private String code;
        @SerializedName("bash_id")
        @Expose
        private Integer bashId;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;
        @SerializedName("status")
        @Expose
        private Integer status;
        @SerializedName("user_id")
        @Expose
        private Integer userId;
        @SerializedName("item_id")
        @Expose
        private Object itemId;
        @SerializedName("type")
        @Expose
        private Integer type;
        @SerializedName("item_type")
        @Expose
        private String itemType;
        @SerializedName("order_item")
        @Expose
        private List<OrderItem_____> orderItem = null;

        public String getTicketCode(Context mContext) {
            return "Order No : " + ticketId;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public Integer getTicketId() {
            return ticketId;
        }

        public void setTicketId(Integer ticketId) {
            this.ticketId = ticketId;
        }

        public Integer getBashId() {
            return bashId;
        }

        public void setBashId(Integer bashId) {
            this.bashId = bashId;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }

        public Object getItemId() {
            return itemId;
        }

        public void setItemId(Object itemId) {
            this.itemId = itemId;
        }

        public Integer getType() {
            return type;
        }

        public void setType(Integer type) {
            this.type = type;
        }

        public String getItemType() {
            return itemType;
        }

        public void setItemType(String itemType) {
            this.itemType = itemType;
        }

        public List<OrderItem_____> getOrderItem() {
            return orderItem;
        }

        public void setOrderItem(List<OrderItem_____> orderItem) {
            this.orderItem = orderItem;
        }

    }

    public class OrderItem_____ {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("order_id")
        @Expose
        private Integer orderId;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("image")
        @Expose
        private String image;
        @SerializedName("quantity")
        @Expose
        private Integer quantity;
        @SerializedName("price")
        @Expose
        private String price;
        @SerializedName("tax")
        @Expose
        private Integer tax;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("is_completed")
        @Expose
        private Integer isCompleted;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;
        @SerializedName("type")
        @Expose
        private Integer type;
        @SerializedName("item_id")
        @Expose
        private Integer itemId;
        @SerializedName("volume")
        @Expose
        private Integer volume;
        @SerializedName("recipe")
        @Expose
        private String recipe;
        @SerializedName("total")
        @Expose
        private String total;
        @SerializedName("grand_total")
        @Expose
        private String grandTotal;

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getOrderId() {
            return orderId;
        }

        public void setOrderId(Integer orderId) {
            this.orderId = orderId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getQuantity() {
            return quantity;
        }

        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public Integer getTax() {
            return tax;
        }

        public void setTax(Integer tax) {
            this.tax = tax;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public Integer getIsCompleted() {
            return isCompleted;
        }

        public void setIsCompleted(Integer isCompleted) {
            this.isCompleted = isCompleted;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public Integer getType() {
            return type;
        }

        public void setType(Integer type) {
            this.type = type;
        }

        public Integer getItemId() {
            return itemId;
        }

        public void setItemId(Integer itemId) {
            this.itemId = itemId;
        }

        public Integer getVolume() {
            return volume;
        }

        public void setVolume(Integer volume) {
            this.volume = volume;
        }

        public String getRecipe() {
            return recipe;
        }

        public void setRecipe(String recipe) {
            this.recipe = recipe;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public String getGrandTotal() {
            return grandTotal;
        }

        public void setGrandTotal(String grandTotal) {
            this.grandTotal = grandTotal;
        }

    }

    public class Product {
        @SerializedName("isChecked")

        public String isChecked;
        @SerializedName("qty")

        public String qty;
        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("venue_id")
        @Expose
        private Integer venueId;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("slug")
        @Expose
        private Object slug;
        @SerializedName("price")
        @Expose
        private String price;
        @SerializedName("tax")
        @Expose
        private Integer tax;
        @SerializedName("image")
        @Expose
        private String image;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getVenueId() {
            return venueId;
        }

        public void setVenueId(Integer venueId) {
            this.venueId = venueId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Object getSlug() {
            return slug;
        }

        public void setSlug(Object slug) {
            this.slug = slug;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public Integer getTax() {
            return tax;
        }

        public void setTax(Integer tax) {
            this.tax = tax;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

    }

    public class OrderItem {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("order_id")
        @Expose
        private Integer orderId;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("quantity")
        @Expose
        private Integer quantity;
        @SerializedName("price")
        @Expose
        private String price;
        @SerializedName("tax")
        @Expose
        private Integer tax;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("is_completed")
        @Expose
        private Integer isCompleted;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;
        @SerializedName("type")
        @Expose
        private Integer type;
        @SerializedName("item_id")
        @Expose
        private Integer itemId;
        @SerializedName("volume")
        @Expose
        private Integer volume;
        @SerializedName("recipe")
        @Expose
        private Object recipe;
        @SerializedName("total")
        @Expose
        private String total;
        @SerializedName("grand_total")
        @Expose
        private String grandTotal;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getOrderId() {
            return orderId;
        }

        public void setOrderId(Integer orderId) {
            this.orderId = orderId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getQuantity() {
            return quantity;
        }

        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public Integer getTax() {
            return tax;
        }

        public void setTax(Integer tax) {
            this.tax = tax;
        }

        public String getDescription(String id, List<OrderItem> list) {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).itemId.toString().equalsIgnoreCase(id))
                    return list.get(i).description;
            }
            return "";
        }

        public String getName(String id, List<OrderItem> list) {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).itemId.toString().equalsIgnoreCase(id))
                    return list.get(i).name;
            }
            return "";
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public Integer getIsCompleted() {
            return isCompleted;
        }

        public void setIsCompleted(Integer isCompleted) {
            this.isCompleted = isCompleted;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public Integer getType() {
            return type;
        }

        public void setType(Integer type) {
            this.type = type;
        }

        public Integer getItemId() {
            return itemId;
        }

        public void setItemId(Integer itemId) {
            this.itemId = itemId;
        }

        public Integer getVolume() {
            return volume;
        }

        public void setVolume(Integer volume) {
            this.volume = volume;
        }

        public Object getRecipe() {
            return recipe;
        }

        public void setRecipe(Object recipe) {
            this.recipe = recipe;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public String getGrandTotal() {
            return grandTotal;
        }

        public void setGrandTotal(String grandTotal) {
            this.grandTotal = grandTotal;
        }

    }

    public class OrderItem_ {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("order_id")
        @Expose
        private Integer orderId;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("quantity")
        @Expose
        private Integer quantity;
        @SerializedName("price")
        @Expose
        private String price;
        @SerializedName("tax")
        @Expose
        private Integer tax;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("is_completed")
        @Expose
        private Integer isCompleted;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;
        @SerializedName("type")
        @Expose
        private Integer type;
        @SerializedName("item_id")
        @Expose
        private Integer itemId;
        @SerializedName("volume")
        @Expose
        private Integer volume;
        @SerializedName("recipe")
        @Expose
        private String recipe;
        @SerializedName("total")
        @Expose
        private String total;
        @SerializedName("grand_total")
        @Expose
        private String grandTotal;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getOrderId() {
            return orderId;
        }

        public void setOrderId(Integer orderId) {
            this.orderId = orderId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getQuantity() {
            return quantity;
        }

        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public Integer getTax() {
            return tax;
        }

        public void setTax(Integer tax) {
            this.tax = tax;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public Integer getIsCompleted() {
            return isCompleted;
        }

        public void setIsCompleted(Integer isCompleted) {
            this.isCompleted = isCompleted;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public Integer getType() {
            return type;
        }

        public void setType(Integer type) {
            this.type = type;
        }

        public Integer getItemId() {
            return itemId;
        }

        public void setItemId(Integer itemId) {
            this.itemId = itemId;
        }

        public Integer getVolume() {
            return volume;
        }

        public void setVolume(Integer volume) {
            this.volume = volume;
        }

        public String getRecipe() {
            return recipe;
        }

        public void setRecipe(String recipe) {
            this.recipe = recipe;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public String getGrandTotal() {
            return grandTotal;
        }

        public void setGrandTotal(String grandTotal) {
            this.grandTotal = grandTotal;
        }

    }

    public class OrderItem__ {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("order_id")
        @Expose
        private Integer orderId;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("quantity")
        @Expose
        private Integer quantity;
        @SerializedName("price")
        @Expose
        private String price;
        @SerializedName("tax")
        @Expose
        private Integer tax;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("is_completed")
        @Expose
        private Integer isCompleted;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;
        @SerializedName("type")
        @Expose
        private Integer type;
        @SerializedName("item_id")
        @Expose
        private Integer itemId;
        @SerializedName("volume")
        @Expose
        private Integer volume;
        @SerializedName("recipe")
        @Expose
        private String recipe;
        @SerializedName("total")
        @Expose
        private String total;
        @SerializedName("grand_total")
        @Expose
        private String grandTotal;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getOrderId() {
            return orderId;
        }

        public void setOrderId(Integer orderId) {
            this.orderId = orderId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getQuantity() {
            return quantity;
        }

        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public Integer getTax() {
            return tax;
        }

        public void setTax(Integer tax) {
            this.tax = tax;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public Integer getIsCompleted() {
            return isCompleted;
        }

        public void setIsCompleted(Integer isCompleted) {
            this.isCompleted = isCompleted;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public Integer getType() {
            return type;
        }

        public void setType(Integer type) {
            this.type = type;
        }

        public Integer getItemId() {
            return itemId;
        }

        public void setItemId(Integer itemId) {
            this.itemId = itemId;
        }

        public Integer getVolume() {
            return volume;
        }

        public void setVolume(Integer volume) {
            this.volume = volume;
        }

        public String getRecipe() {
            return recipe;
        }

        public void setRecipe(String recipe) {
            this.recipe = recipe;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public String getGrandTotal() {
            return grandTotal;
        }

        public void setGrandTotal(String grandTotal) {
            this.grandTotal = grandTotal;
        }

    }

    public class OrderItem___ {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("order_id")
        @Expose
        private Integer orderId;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("quantity")
        @Expose
        private Integer quantity;
        @SerializedName("price")
        @Expose
        private String price;
        @SerializedName("tax")
        @Expose
        private Integer tax;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("is_completed")
        @Expose
        private Integer isCompleted;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;
        @SerializedName("type")
        @Expose
        private Integer type;
        @SerializedName("item_id")
        @Expose
        private Integer itemId;
        @SerializedName("volume")
        @Expose
        private Integer volume;
        @SerializedName("recipe")
        @Expose
        private String recipe;
        @SerializedName("total")
        @Expose
        private String total;
        @SerializedName("grand_total")
        @Expose
        private String grandTotal;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getOrderId() {
            return orderId;
        }

        public void setOrderId(Integer orderId) {
            this.orderId = orderId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getQuantity() {
            return quantity;
        }

        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public Integer getTax() {
            return tax;
        }

        public void setTax(Integer tax) {
            this.tax = tax;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public Integer getIsCompleted() {
            return isCompleted;
        }

        public void setIsCompleted(Integer isCompleted) {
            this.isCompleted = isCompleted;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public Integer getType() {
            return type;
        }

        public void setType(Integer type) {
            this.type = type;
        }

        public Integer getItemId() {
            return itemId;
        }

        public void setItemId(Integer itemId) {
            this.itemId = itemId;
        }

        public Integer getVolume() {
            return volume;
        }

        public void setVolume(Integer volume) {
            this.volume = volume;
        }

        public String getRecipe() {
            return recipe;
        }

        public void setRecipe(String recipe) {
            this.recipe = recipe;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public String getGrandTotal() {
            return grandTotal;
        }

        public void setGrandTotal(String grandTotal) {
            this.grandTotal = grandTotal;
        }

    }

    public class OrderItem____ {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("order_id")
        @Expose
        private Integer orderId;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("quantity")
        @Expose
        private Integer quantity;
        @SerializedName("price")
        @Expose
        private String price;
        @SerializedName("tax")
        @Expose
        private Integer tax;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("is_completed")
        @Expose
        private Integer isCompleted;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;
        @SerializedName("type")
        @Expose
        private Integer type;
        @SerializedName("item_id")
        @Expose
        private Integer itemId;
        @SerializedName("volume")
        @Expose
        private Integer volume;
        @SerializedName("recipe")
        @Expose
        private String recipe;
        @SerializedName("total")
        @Expose
        private String total;
        @SerializedName("grand_total")
        @Expose
        private String grandTotal;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getOrderId() {
            return orderId;
        }

        public void setOrderId(Integer orderId) {
            this.orderId = orderId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getQuantity() {
            return quantity;
        }

        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public Integer getTax() {
            return tax;
        }

        public void setTax(Integer tax) {
            this.tax = tax;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public Integer getIsCompleted() {
            return isCompleted;
        }

        public void setIsCompleted(Integer isCompleted) {
            this.isCompleted = isCompleted;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public Integer getType() {
            return type;
        }

        public void setType(Integer type) {
            this.type = type;
        }

        public Integer getItemId() {
            return itemId;
        }

        public void setItemId(Integer itemId) {
            this.itemId = itemId;
        }

        public Integer getVolume() {
            return volume;
        }

        public void setVolume(Integer volume) {
            this.volume = volume;
        }

        public String getRecipe() {
            return recipe;
        }

        public void setRecipe(String recipe) {
            this.recipe = recipe;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public String getGrandTotal() {
            return grandTotal;
        }

        public void setGrandTotal(String grandTotal) {
            this.grandTotal = grandTotal;
        }

    }

    public class Item {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("event_id")
        @Expose
        private Integer eventId;
        @SerializedName("status")
        @Expose
        private Integer status;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("slug")
        @Expose
        private String slug;
        @SerializedName("quantity")
        @Expose
        private Integer quantity;
        @SerializedName("price")
        @Expose
        private String price;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("is_description")
        @Expose
        private Integer isDescription;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getEventId() {
            return eventId;
        }

        public void setEventId(Integer eventId) {
            this.eventId = eventId;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSlug() {
            return slug;
        }

        public void setSlug(String slug) {
            this.slug = slug;
        }

        public Integer getQuantity() {
            return quantity;
        }

        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public Integer getIsDescription() {
            return isDescription;
        }

        public void setIsDescription(Integer isDescription) {
            this.isDescription = isDescription;
        }

    }

    public class QrBottle {

        @SerializedName("order_item")
        @Expose
        public List<OrderItem__> orderItem = null;
        @SerializedName("ticket_id")
        @Expose
        public Integer ticketId;
        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("code")
        @Expose
        private String code;
        @SerializedName("bash_id")
        @Expose
        private Integer bashId;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;
        @SerializedName("status")
        @Expose
        private Integer status;
        @SerializedName("user_id")
        @Expose
        private Integer userId;
        @SerializedName("type")
        @Expose
        private Integer type;
        @SerializedName("item_type")
        @Expose
        private String itemType;

        public String getTicketCode(Context mContext) {
            return "Order No : " + ticketId;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public Integer getBashId() {
            return bashId;
        }

        public void setBashId(Integer bashId) {
            this.bashId = bashId;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }

        public Integer getType() {
            return type;
        }

        public void setType(Integer type) {
            this.type = type;
        }

        public String getItemType() {
            return itemType;
        }

        public void setItemType(String itemType) {
            this.itemType = itemType;
        }

    }

    public class QrDrink {

        @SerializedName("order_item")
        @Expose
        public List<OrderItem_> orderItem = null;
        @SerializedName("ticket_id")
        @Expose
        public Integer ticketId;
        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("code")
        @Expose
        private String code;
        @SerializedName("bash_id")
        @Expose
        private Integer bashId;
        @SerializedName("status")
        @Expose
        private Integer status;
        @SerializedName("user_id")
        @Expose
        private Integer userId;
        @SerializedName("type")
        @Expose
        private Integer type;
        @SerializedName("item_type")
        @Expose
        private String itemType;

        public String getTicketCode(Context mContext) {
            return "Order No : " + ticketId;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public Integer getBashId() {
            return bashId;
        }

        public void setBashId(Integer bashId) {
            this.bashId = bashId;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }

        public Integer getType() {
            return type;
        }

        public void setType(Integer type) {
            this.type = type;
        }

        public String getItemType() {
            return itemType;
        }

        public void setItemType(String itemType) {
            this.itemType = itemType;
        }

    }

    public class QrSection {

        @SerializedName("order_item")
        @Expose
        public List<OrderItem____> orderItem = null;
        @SerializedName("ticket_id")
        @Expose
        public Integer ticketId;
        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("code")
        @Expose
        private String code;
        @SerializedName("bash_id")
        @Expose
        private Integer bashId;
        @SerializedName("status")
        @Expose
        private Integer status;
        @SerializedName("user_id")
        @Expose
        private Integer userId;
        @SerializedName("type")
        @Expose
        private Integer type;
        @SerializedName("item_type")
        @Expose
        private String itemType;

        public String getTicketCode(Context mContext) {
            return "Order No : " + ticketId;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public Integer getBashId() {
            return bashId;
        }

        public void setBashId(Integer bashId) {
            this.bashId = bashId;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }

        public Integer getType() {
            return type;
        }

        public void setType(Integer type) {
            this.type = type;
        }

        public String getItemType() {
            return itemType;
        }

        public void setItemType(String itemType) {
            this.itemType = itemType;
        }

    }

    public class QrTable {

        @SerializedName("order_item")
        @Expose
        public List<OrderItem___> orderItem = null;
        @SerializedName("ticket_id")
        @Expose
        public Integer ticketId;
        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("code")
        @Expose
        private String code;
        @SerializedName("bash_id")
        @Expose
        private Integer bashId;
        @SerializedName("status")
        @Expose
        private Integer status;
        @SerializedName("user_id")
        @Expose
        private Integer userId;
        @SerializedName("type")
        @Expose
        private Integer type;
        @SerializedName("item_type")
        @Expose
        private String itemType;

        public String getTicketCode(Context mContext) {
            return "Order No : " + ticketId;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public Integer getBashId() {
            return bashId;
        }

        public void setBashId(Integer bashId) {
            this.bashId = bashId;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }

        public Integer getType() {
            return type;
        }

        public void setType(Integer type) {
            this.type = type;
        }

        public String getItemType() {
            return itemType;
        }

        public void setItemType(String itemType) {
            this.itemType = itemType;
        }

    }

    public class QrTicket {

        @SerializedName("order_item")
        @Expose
        public List<OrderItem> orderItem = null;
        @SerializedName("ticket_id")
        @Expose
        Integer ticketId;
        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("code")
        @Expose
        private String code;
        @SerializedName("bash_id")
        @Expose
        private Integer bashId;
        @SerializedName("status")
        @Expose
        private Integer status;
        @SerializedName("user_id")
        @Expose
        private Integer userId;
        @SerializedName("item_id")
        @Expose
        private String itemId;
        @SerializedName("type")
        @Expose
        private Integer type;
        @SerializedName("item_type")
        @Expose
        private String itemType;
        @SerializedName("item")
        @Expose
        private Item item;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getTicketCode(Context mContext) {
            return " Order No: " + ticketId;
        }

        public Integer getTicketId() {
            return ticketId;
        }

        public void setTicketId(Integer ticketId) {
            this.ticketId = ticketId;
        }

        public Integer getBashId() {
            return bashId;
        }

        public void setBashId(Integer bashId) {
            this.bashId = bashId;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }

        public String getItemId() {
            return itemId;
        }

        public void setItemId(String itemId) {
            this.itemId = itemId;
        }

        public Integer getType() {
            return type;
        }

        public void setType(Integer type) {
            this.type = type;
        }

        public String getItemType() {
            return itemType;
        }

        public void setItemType(String itemType) {
            this.itemType = itemType;
        }

        public Item getItem() {
            return item;
        }

        public void setItem(Item item) {
            this.item = item;
        }

    }

    public class Bartender {

        @SerializedName("id")

        private String id;
        @SerializedName("venue_id")

        private String venueId;
        @SerializedName("image")

        private String image;
        @SerializedName("name")

        private String name;
        @SerializedName("job_title")

        private String jobTitle;
        @SerializedName("user_name")

        private String userName;
        @SerializedName("status")

        private String status;
        @SerializedName("created_at")

        private String createdAt;
        @SerializedName("updated_at")

        private String updatedAt;
        @SerializedName("user_id")

        private String userId;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getVenueId() {
            return venueId;
        }

        public void setVenueId(String venueId) {
            this.venueId = venueId;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getJobTitle() {
            return jobTitle;
        }

        public void setJobTitle(String jobTitle) {
            this.jobTitle = jobTitle;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

    }

    public class Bottle {
        @SerializedName("isChecked")

        public String isChecked;
        @SerializedName("qty")

        public String qty;

        @SerializedName("id")

        private String id;
        @SerializedName("user_id")

        private String userId;
        @SerializedName("venue_id")

        private String venueId;
        @SerializedName("name")

        private String name;
        @SerializedName("slug")

        private String slug;
        @SerializedName("amount")

        private String amount;
        @SerializedName("quantity")

        private String quantity;
        @SerializedName("volume")

        private String volume;
        @SerializedName("description")

        private String description;
        @SerializedName("price_request")

        private String priceRequest;
        @SerializedName("created_at")

        private String createdAt;
        @SerializedName("updated_at")

        private String updatedAt;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getVenueId() {
            return venueId;
        }

        public void setVenueId(String venueId) {
            this.venueId = venueId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSlug() {
            return slug;
        }

        public void setSlug(String slug) {
            this.slug = slug;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getQuantity() {
            return quantity;
        }

        public void setQuantity(String quantity) {
            this.quantity = quantity;
        }

        public String getVolume() {
            return volume;
        }

        public void setVolume(String volume) {
            this.volume = volume;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getPriceRequest() {
            return priceRequest;
        }

        public void setPriceRequest(String priceRequest) {
            this.priceRequest = priceRequest;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

    }

    public class Drink {
        @SerializedName("isChecked")

        public String isChecked;
        @SerializedName("qty")

        public String qty;
        @SerializedName("id")

        private String id;
        @SerializedName("venue_id")

        private String venueId;

        @SerializedName("drink_type")

        private String drinkType;
        @SerializedName("name")

        private String name;
        @SerializedName("slug")

        private String slug;
        @SerializedName("price")

        private String price;
        @SerializedName("volume")

        private String volume;
        @SerializedName("recipe")

        private String recipe;
        @SerializedName("additional_info")

        private String additionalInfo;
        @SerializedName("status")

        private String status;

        @SerializedName("created_at")

        private String createdAt;
        @SerializedName("updated_at")

        private String updatedAt;
//        @SerializedName("recipes")
//
//        private Recipes recipes;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getVenueId() {
            return venueId;
        }

        public void setVenueId(String venueId) {
            this.venueId = venueId;
        }


        public String getDrinkType() {
            return drinkType;
        }

        public void setDrinkType(String drinkType) {
            this.drinkType = drinkType;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSlug() {
            return slug;
        }

        public void setSlug(String slug) {
            this.slug = slug;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getVolume() {
            return volume;
        }

        public void setVolume(String volume) {
            this.volume = volume;
        }

        public String getRecipe() {
            return recipe;
        }

        public void setRecipe(String recipe) {
            this.recipe = recipe;
        }

        public String getAdditionalInfo() {
            return additionalInfo;
        }

        public void setAdditionalInfo(String additionalInfo) {
            this.additionalInfo = additionalInfo;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }


        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

//        public Recipes getRecipes() {
//            return recipes;
//        }
//
//        public void setRecipes(Recipes recipes) {
//            this.recipes = recipes;
//        }

    }

    public class Recipes {

        @SerializedName("name")

        private String name;
        @SerializedName("volume")

        private String volume;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getVolume() {
            return volume;
        }

        public void setVolume(String volume) {
            this.volume = volume;
        }

    }

    public class Section {
        @SerializedName("isChecked")

        public String isChecked;
        @SerializedName("price_request")

        public String price_request;
        @SerializedName("description")

        public String description;
        @SerializedName("designation_id")

        public String designation_id;
        @SerializedName("id")

        private String id;
        @SerializedName("venue_id")

        private String venueId;
        @SerializedName("name")

        private String name;
        @SerializedName("slug")

        private String slug;
        @SerializedName("price")

        private String price;
        @SerializedName("guest_per_table")

        private String guest;
        @SerializedName("floor_type")

        private String floorType;
        @SerializedName("status")

        private String status;
        @SerializedName("created_at")

        private String createdAt;
        @SerializedName("updated_at")

        private String updatedAt;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getVenueId() {
            return venueId;
        }

        public void setVenueId(String venueId) {
            this.venueId = venueId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSlug() {
            return slug;
        }

        public void setSlug(String slug) {
            this.slug = slug;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getGuest() {
            return guest;
        }

        public void setGuest(String guest) {
            this.guest = guest;
        }

        public String getFloorType() {
            return floorType;
        }

        public void setFloorType(String floorType) {
            this.floorType = floorType;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

    }

    public class Table {
        @SerializedName("isChecked")

        public String isChecked;
        @SerializedName("id")

        private String id;
        @SerializedName("venue_id")

        private String venueId;
        @SerializedName("name")

        private String name;
        @SerializedName("slug")

        private String slug;
        @SerializedName("number")

        private String number;
        @SerializedName("designation_id")

        private String designationId;
        @SerializedName("guest_per_table")

        private String guestPerTable;
        @SerializedName("description")

        private String description;
        @SerializedName("price")

        private String price;
        @SerializedName("price_request")

        private String priceRequest;
        @SerializedName("type")

        private String type;
        @SerializedName("status")

        private String status;
        @SerializedName("created_at")

        private String createdAt;
        @SerializedName("updated_at")

        private String updatedAt;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getVenueId() {
            return venueId;
        }

        public void setVenueId(String venueId) {
            this.venueId = venueId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSlug() {
            return slug;
        }

        public void setSlug(String slug) {
            this.slug = slug;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getDesignationId() {
            return designationId;
        }

        public void setDesignationId(String designationId) {
            this.designationId = designationId;
        }

        public String getGuestPerTable() {
            return guestPerTable;
        }

        public void setGuestPerTable(String guestPerTable) {
            this.guestPerTable = guestPerTable;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getPriceRequest() {
            return priceRequest;
        }

        public void setPriceRequest(String priceRequest) {
            this.priceRequest = priceRequest;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

    }

    public class Bottle_ {

        @SerializedName("id")

        private Integer id;
        @SerializedName("user_id")

        private Integer userId;
        @SerializedName("venue_id")

        private Integer venueId;
        @SerializedName("name")

        private String name;
        @SerializedName("slug")

        private Object slug;
        @SerializedName("amount")

        private String amount;
        @SerializedName("quantity")

        private Integer quantity;
        @SerializedName("volume")

        private String volume;
        @SerializedName("description")

        private String description;
        @SerializedName("price_request")

        private Integer priceRequest;
        @SerializedName("created_at")

        private String createdAt;
        @SerializedName("updated_at")

        private String updatedAt;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }

        public Integer getVenueId() {
            return venueId;
        }

        public void setVenueId(Integer venueId) {
            this.venueId = venueId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Object getSlug() {
            return slug;
        }

        public void setSlug(Object slug) {
            this.slug = slug;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public Integer getQuantity() {
            return quantity;
        }

        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
        }

        public String getVolume() {
            return volume;
        }

        public void setVolume(String volume) {
            this.volume = volume;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public Integer getPriceRequest() {
            return priceRequest;
        }

        public void setPriceRequest(Integer priceRequest) {
            this.priceRequest = priceRequest;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

    }

    public class Drink_ {

        @SerializedName("id")

        private Integer id;
        @SerializedName("venue_id")

        private Integer venueId;
        @SerializedName("bottle_id")

        private Integer bottleId;
        @SerializedName("drink_type")

        private Integer drinkType;
        @SerializedName("name")

        private String name;
        @SerializedName("slug")

        private String slug;
        @SerializedName("price")

        private Double price;
        @SerializedName("volume")

        private Integer volume;
        @SerializedName("recipe")

        private String recipe;
        @SerializedName("additional_info")

        private String additionalInfo;
        @SerializedName("status")

        private Integer status;
        @SerializedName("type")

        private Integer type;
        @SerializedName("created_at")

        private String createdAt;
        @SerializedName("updated_at")

        private String updatedAt;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getVenueId() {
            return venueId;
        }

        public void setVenueId(Integer venueId) {
            this.venueId = venueId;
        }

        public Integer getBottleId() {
            return bottleId;
        }

        public void setBottleId(Integer bottleId) {
            this.bottleId = bottleId;
        }

        public Integer getDrinkType() {
            return drinkType;
        }

        public void setDrinkType(Integer drinkType) {
            this.drinkType = drinkType;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSlug() {
            return slug;
        }

        public void setSlug(String slug) {
            this.slug = slug;
        }

        public Double getPrice() {
            return price;
        }

        public void setPrice(Double price) {
            this.price = price;
        }

        public Integer getVolume() {
            return volume;
        }

        public void setVolume(Integer volume) {
            this.volume = volume;
        }

        public String getRecipe() {
            return recipe;
        }

        public void setRecipe(String recipe) {
            this.recipe = recipe;
        }

        public String getAdditionalInfo() {
            return additionalInfo;
        }

        public void setAdditionalInfo(String additionalInfo) {
            this.additionalInfo = additionalInfo;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public Integer getType() {
            return type;
        }

        public void setType(Integer type) {
            this.type = type;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

    }

    public class SectionFloorPlan {

        @SerializedName("id")

        private Integer id;
        @SerializedName("venue_id")

        private Integer venueId;
        @SerializedName("type")

        private Integer type;
        @SerializedName("media")

        private String media;
        @SerializedName("created_at")

        private String createdAt;
        @SerializedName("updated_at")

        private String updatedAt;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getVenueId() {
            return venueId;
        }

        public void setVenueId(Integer venueId) {
            this.venueId = venueId;
        }

        public Integer getType() {
            return type;
        }

        public void setType(Integer type) {
            this.type = type;
        }

        public String getMedia() {
            return media;
        }

        public void setMedia(String media) {
            this.media = media;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

    }

    public class TableFloorPlan {

        @SerializedName("id")

        private Integer id;
        @SerializedName("venue_id")

        private Integer venueId;
        @SerializedName("type")

        private Integer type;
        @SerializedName("media")

        private String media;
        @SerializedName("created_at")

        private String createdAt;
        @SerializedName("updated_at")

        private String updatedAt;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getVenueId() {
            return venueId;
        }

        public void setVenueId(Integer venueId) {
            this.venueId = venueId;
        }

        public Integer getType() {
            return type;
        }

        public void setType(Integer type) {
            this.type = type;
        }

        public String getMedia() {
            return media;
        }

        public void setMedia(String media) {
            this.media = media;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

    }

    public class Table_ {

        @SerializedName("id")

        private Integer id;
        @SerializedName("venue_id")

        private Integer venueId;
        @SerializedName("name")

        private String name;
        @SerializedName("slug")

        private Object slug;
        @SerializedName("number")

        private String number;
        @SerializedName("designation_id")

        private String designationId;
        @SerializedName("guest_per_table")

        private Integer guestPerTable;
        @SerializedName("description")

        private String description;
        @SerializedName("price")

        private Double price;
        @SerializedName("price_request")

        private Integer priceRequest;
        @SerializedName("type")

        private Integer type;
        @SerializedName("status")

        private Integer status;
        @SerializedName("created_at")

        private String createdAt;
        @SerializedName("updated_at")

        private String updatedAt;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getVenueId() {
            return venueId;
        }

        public void setVenueId(Integer venueId) {
            this.venueId = venueId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Object getSlug() {
            return slug;
        }

        public void setSlug(Object slug) {
            this.slug = slug;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getDesignationId() {
            return designationId;
        }

        public void setDesignationId(String designationId) {
            this.designationId = designationId;
        }

        public Integer getGuestPerTable() {
            return guestPerTable;
        }

        public void setGuestPerTable(Integer guestPerTable) {
            this.guestPerTable = guestPerTable;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public Double getPrice() {
            return price;
        }

        public void setPrice(Double price) {
            this.price = price;
        }

        public Integer getPriceRequest() {
            return priceRequest;
        }

        public void setPriceRequest(Integer priceRequest) {
            this.priceRequest = priceRequest;
        }

        public Integer getType() {
            return type;
        }

        public void setType(Integer type) {
            this.type = type;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

    }

    public class Venue {

        @SerializedName("section_plan_image")

        public List<String> section_plan_image;
        @SerializedName("table_plan_image")
        public List<String> table_plan_image = null;
        @SerializedName("id")

        private Integer id;
        @SerializedName("image")

        private String image;
        @SerializedName("user_id")

        private Integer userId;
        @SerializedName("name")

        private String name;
        @SerializedName("address")

        private String address;
        @SerializedName("street_address")

        private String streetAddress;
        @SerializedName("city")

        private String city;
        @SerializedName("state")

        private String state;
        @SerializedName("zipcode")

        private String zipcode;
        @SerializedName("country")

        private String country;
        @SerializedName("lat")

        private String lat;
        @SerializedName("lng")

        private String lng;
        @SerializedName("email")

        private String email;
        @SerializedName("phone")

        private String phone;
        @SerializedName("title")

        private String title;
        @SerializedName("attachment")

        private String attachment;
        @SerializedName("created_at")

        private String createdAt;
        @SerializedName("updated_at")

        private String updatedAt;
        @SerializedName("bottles")

        private List<Bottle_> bottles = null;
        @SerializedName("drinks")

        private List<Drink_> drinks = null;
        @SerializedName("sections")

        private List<Section_> sections = null;
        @SerializedName("bartender")

        private List<Bartender> bartender = null;
        @SerializedName("tables")

        private List<Table_> tables = null;

        public List<String> getTablePlanImage() {
            return table_plan_image;
        }

        public void setTablePlanImage(List<String> tablePlanImage) {
            this.table_plan_image = tablePlanImage;
        }
//        @SerializedName("table_floor_plan")
//
//        private TableFloorPlan tableFloorPlan;
//        @SerializedName("section_floor_plan")
//
//        private SectionFloorPlan sectionFloorPlan;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getStreetAddress() {
            return streetAddress;
        }

        public void setStreetAddress(String streetAddress) {
            this.streetAddress = streetAddress;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getZipcode() {
            return zipcode;
        }

        public void setZipcode(String zipcode) {
            this.zipcode = zipcode;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getLng() {
            return lng;
        }

        public void setLng(String lng) {
            this.lng = lng;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getAttachment() {
            return attachment;
        }

        public void setAttachment(String attachment) {
            this.attachment = attachment;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public List<Bottle_> getBottles() {
            return bottles;
        }

        public void setBottles(List<Bottle_> bottles) {
            this.bottles = bottles;
        }

        public List<Drink_> getDrinks() {
            return drinks;
        }

        public void setDrinks(List<Drink_> drinks) {
            this.drinks = drinks;
        }

        public List<Section_> getSections() {
            return sections;
        }

        public void setSections(List<Section_> sections) {
            this.sections = sections;
        }

        public List<Bartender> getBartender() {
            return bartender;
        }

        public void setBartender(List<Bartender> bartender) {
            this.bartender = bartender;
        }

        public List<Table_> getTables() {
            return tables;
        }

        public void setTables(List<Table_> tables) {
            this.tables = tables;
        }

//        public TableFloorPlan getTableFloorPlan() {
//            return tableFloorPlan;
//        }
//
//        public void setTableFloorPlan(TableFloorPlan tableFloorPlan) {
//            this.tableFloorPlan = tableFloorPlan;
//        }
//
//        public SectionFloorPlan getSectionFloorPlan() {
//            return sectionFloorPlan;
//        }
//
//        public void setSectionFloorPlan(SectionFloorPlan sectionFloorPlan) {
//            this.sectionFloorPlan = sectionFloorPlan;
//        }

    }

    public class Section_ {

        @SerializedName("id")

        private Integer id;
        @SerializedName("venue_id")

        private Integer venueId;
        @SerializedName("name")

        private String name;
        @SerializedName("slug")

        private Object slug;
        @SerializedName("price")

        private Double price;
        @SerializedName("guest")

        private Integer guest;
        @SerializedName("floor_type")

        private String floorType;
        @SerializedName("status")

        private Integer status;
        @SerializedName("created_at")

        private String createdAt;
        @SerializedName("updated_at")

        private String updatedAt;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getVenueId() {
            return venueId;
        }

        public void setVenueId(Integer venueId) {
            this.venueId = venueId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Object getSlug() {
            return slug;
        }

        public void setSlug(Object slug) {
            this.slug = slug;
        }

        public Double getPrice() {
            return price;
        }

        public void setPrice(Double price) {
            this.price = price;
        }

        public Integer getGuest() {
            return guest;
        }

        public void setGuest(Integer guest) {
            this.guest = guest;
        }

        public String getFloorType() {
            return floorType;
        }

        public void setFloorType(String floorType) {
            this.floorType = floorType;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

    }

    public class EventTicket {

        @SerializedName("id")

        private Integer id;
        @SerializedName("event_id")

        private Integer eventId;
        @SerializedName("status")

        private Integer status;
        @SerializedName("created_at")

        private String createdAt;
        @SerializedName("updated_at")

        private String updatedAt;
        @SerializedName("name")

        private String name;
        @SerializedName("slug")

        private String slug;
        @SerializedName("quantity")

        private Integer quantity;
        @SerializedName("price")

        private Double price;
        @SerializedName("description")

        private String description;
        @SerializedName("is_description")

        private Integer isDescription;
        @SerializedName("is_demography")

        private Integer isDemography;
        @SerializedName("price_male")

        private String priceMale;
        @SerializedName("price_female")

        private String priceFemale;
        @SerializedName("is_price_by_day")

        private Integer isPriceByDay;
        @SerializedName("before_male_time")

        private String beforeMaleTime;
        @SerializedName("before_male_date")

        private String beforeMaleDate;
        @SerializedName("before_male_price")

        private String beforeMalePrice;
        @SerializedName("after_male_time")

        private String afterMaleTime;
        @SerializedName("after_male_date")

        private String afterMaleDate;
        @SerializedName("after_male_price")

        private String afterMalePrice;
        @SerializedName("before_female_time")

        private String beforeFemaleTime;
        @SerializedName("before_female_date")

        private String beforeFemaleDate;
        @SerializedName("before_female_price")

        private String beforeFemalePrice;
        @SerializedName("after_female_time")

        private String afterFemaleTime;
        @SerializedName("after_female_date")

        private String afterFemaleDate;
        @SerializedName("after_female_price")

        private String afterFemalePrice;
        @SerializedName("is_immediately_sale")

        private Integer isImmediatelySale;
        @SerializedName("sale_start_date")

        private String saleStartDate;
        @SerializedName("sale_start_time")

        private String saleStartTime;
        @SerializedName("is_sale_end")

        private Integer isSaleEnd;
        @SerializedName("end_date")

        private String endDate;
        @SerializedName("end_time")

        private String endTime;
        @SerializedName("pre_order_min")

        private Integer preOrderMin;
        @SerializedName("pre_order_max")

        private Integer preOrderMax;
        @SerializedName("visibility")

        private Integer visibility;
        @SerializedName("ticket_type")

        private String ticketType;
        @SerializedName("is_advance")

        private String isAdvance;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getEventId() {
            return eventId;
        }

        public void setEventId(Integer eventId) {
            this.eventId = eventId;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSlug() {
            return slug;
        }

        public void setSlug(String slug) {
            this.slug = slug;
        }

        public Integer getQuantity() {
            return quantity;
        }

        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
        }

        public Double getPrice() {
            return price;
        }

        public void setPrice(Double price) {
            this.price = price;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public Integer getIsDescription() {
            return isDescription;
        }

        public void setIsDescription(Integer isDescription) {
            this.isDescription = isDescription;
        }

        public Integer getIsDemography() {
            return isDemography;
        }

        public void setIsDemography(Integer isDemography) {
            this.isDemography = isDemography;
        }

        public String getPriceMale() {
            return priceMale;
        }

        public void setPriceMale(String priceMale) {
            this.priceMale = priceMale;
        }

        public String getPriceFemale() {
            return priceFemale;
        }

        public void setPriceFemale(String priceFemale) {
            this.priceFemale = priceFemale;
        }

        public Integer getIsPriceByDay() {
            return isPriceByDay;
        }

        public void setIsPriceByDay(Integer isPriceByDay) {
            this.isPriceByDay = isPriceByDay;
        }

        public String getBeforeMaleTime() {
            return beforeMaleTime;
        }

        public void setBeforeMaleTime(String beforeMaleTime) {
            this.beforeMaleTime = beforeMaleTime;
        }

        public String getBeforeMaleDate() {
            return beforeMaleDate;
        }

        public void setBeforeMaleDate(String beforeMaleDate) {
            this.beforeMaleDate = beforeMaleDate;
        }

        public String getBeforeMalePrice() {
            return beforeMalePrice;
        }

        public void setBeforeMalePrice(String beforeMalePrice) {
            this.beforeMalePrice = beforeMalePrice;
        }

        public String getAfterMaleTime() {
            return afterMaleTime;
        }

        public void setAfterMaleTime(String afterMaleTime) {
            this.afterMaleTime = afterMaleTime;
        }

        public String getAfterMaleDate() {
            return afterMaleDate;
        }

        public void setAfterMaleDate(String afterMaleDate) {
            this.afterMaleDate = afterMaleDate;
        }

        public String getAfterMalePrice() {
            return afterMalePrice;
        }

        public void setAfterMalePrice(String afterMalePrice) {
            this.afterMalePrice = afterMalePrice;
        }

        public String getBeforeFemaleTime() {
            return beforeFemaleTime;
        }

        public void setBeforeFemaleTime(String beforeFemaleTime) {
            this.beforeFemaleTime = beforeFemaleTime;
        }

        public String getBeforeFemaleDate() {
            return beforeFemaleDate;
        }

        public void setBeforeFemaleDate(String beforeFemaleDate) {
            this.beforeFemaleDate = beforeFemaleDate;
        }

        public String getBeforeFemalePrice() {
            return beforeFemalePrice;
        }

        public void setBeforeFemalePrice(String beforeFemalePrice) {
            this.beforeFemalePrice = beforeFemalePrice;
        }

        public String getAfterFemaleTime() {
            return afterFemaleTime;
        }

        public void setAfterFemaleTime(String afterFemaleTime) {
            this.afterFemaleTime = afterFemaleTime;
        }

        public String getAfterFemaleDate() {
            return afterFemaleDate;
        }

        public void setAfterFemaleDate(String afterFemaleDate) {
            this.afterFemaleDate = afterFemaleDate;
        }

        public String getAfterFemalePrice() {
            return afterFemalePrice;
        }

        public void setAfterFemalePrice(String afterFemalePrice) {
            this.afterFemalePrice = afterFemalePrice;
        }

        public Integer getIsImmediatelySale() {
            return isImmediatelySale;
        }

        public void setIsImmediatelySale(Integer isImmediatelySale) {
            this.isImmediatelySale = isImmediatelySale;
        }

        public String getSaleStartDate() {
            return saleStartDate;
        }

        public void setSaleStartDate(String saleStartDate) {
            this.saleStartDate = saleStartDate;
        }

        public String getSaleStartTime() {
            return saleStartTime;
        }

        public void setSaleStartTime(String saleStartTime) {
            this.saleStartTime = saleStartTime;
        }

        public Integer getIsSaleEnd() {
            return isSaleEnd;
        }

        public void setIsSaleEnd(Integer isSaleEnd) {
            this.isSaleEnd = isSaleEnd;
        }

        public String getEndDate() {
            return endDate;
        }

        public void setEndDate(String endDate) {
            this.endDate = endDate;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public Integer getPreOrderMin() {
            return preOrderMin;
        }

        public void setPreOrderMin(Integer preOrderMin) {
            this.preOrderMin = preOrderMin;
        }

        public Integer getPreOrderMax() {
            return preOrderMax;
        }

        public void setPreOrderMax(Integer preOrderMax) {
            this.preOrderMax = preOrderMax;
        }

        public Integer getVisibility() {
            return visibility;
        }

        public void setVisibility(Integer visibility) {
            this.visibility = visibility;
        }

        public String getTicketType() {
            return ticketType;
        }

        public void setTicketType(String ticketType) {
            this.ticketType = ticketType;
        }

        public String getIsAdvance() {
            return isAdvance;
        }

        public void setIsAdvance(String isAdvance) {
            this.isAdvance = isAdvance;
        }

    }
}
