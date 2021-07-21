package com.orem.bashhub.data;

import android.content.Context;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.orem.bashhub.R;
import com.orem.bashhub.utils.Const;

import java.util.List;

public class VenueListPOJO {
    @SerializedName("data")
    @Expose
    private Data data;
    @SerializedName("mesg")
    @Expose
    private String mesg;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public String getMesg() {
        return mesg;
    }

    public void setMesg(String mesg) {
        this.mesg = mesg;
    }

    public class Bottle {
        @SerializedName("isChecked")

        public String isChecked;
        @SerializedName("qty")

        public String qty;
        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("user_id")
        @Expose
        private Integer userId;
        @SerializedName("venue_id")
        @Expose
        private Integer venueId;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("slug")
        @Expose
        private String slug;
        @SerializedName("amount")
        @Expose
        private String amount;
        @SerializedName("quantity")
        @Expose
        private Integer quantity;
        @SerializedName("volume")
        @Expose
        private String volume;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("price_request")
        @Expose
        private Integer priceRequest;
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

    public class Data {

        @SerializedName("venues")
        @Expose
        private List<Venue> venues = null;

        public List<Venue> getVenues() {
            return venues;
        }

        public void setVenues(List<Venue> venues) {
            this.venues = venues;
        }

    }

    public class Drink {
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
        @SerializedName("bottle_id")
        @Expose
        private Integer bottleId;
        @SerializedName("drink_type")
        @Expose
        private Integer drinkType;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("slug")
        @Expose
        private String slug;
        @SerializedName("price")
        @Expose
        private String price;
        @SerializedName("volume")
        @Expose
        private Integer volume;
        @SerializedName("recipe")
        @Expose
        private String recipe;
        @SerializedName("additional_info")
        @Expose
        private String additionalInfo;
        @SerializedName("status")
        @Expose
        private Integer status;
        @SerializedName("type")
        @Expose
        private Integer type;
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

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
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

    public class Friday {

        @SerializedName("is_open")
        @Expose
        private Integer isOpen;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("start_time")
        @Expose
        private String startTime;
        @SerializedName("end_time")
        @Expose
        private String endTime;

        public Integer getIsOpen() {
            return isOpen;
        }

        public void setIsOpen(Integer isOpen) {
            this.isOpen = isOpen;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

    }

    public class Monday {

        @SerializedName("is_open")
        @Expose
        private Integer isOpen;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("start_time")
        @Expose
        private String startTime;
        @SerializedName("end_time")
        @Expose
        private String endTime;

        public Integer getIsOpen() {
            return isOpen;
        }

        public void setIsOpen(Integer isOpen) {
            this.isOpen = isOpen;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
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
        private String slug;
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

    public class Recipe {

        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("volume")
        @Expose
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

    public class Roster {

        @SerializedName("Sunday")
        @Expose
        private Sunday sunday;
        @SerializedName("Monday")
        @Expose
        private Monday monday;
        @SerializedName("Tuesday")
        @Expose
        private Tuesday tuesday;
        @SerializedName("Wednesday")
        @Expose
        private Wednesday wednesday;
        @SerializedName("Thursday")
        @Expose
        private Thursday thursday;
        @SerializedName("Friday")
        @Expose
        private Friday friday;
        @SerializedName("Saturday")
        @Expose
        private Saturday saturday;

        public Sunday getSunday() {
            return sunday;
        }

        public void setSunday(Sunday sunday) {
            this.sunday = sunday;
        }

        public Monday getMonday() {
            return monday;
        }

        public void setMonday(Monday monday) {
            this.monday = monday;
        }

        public Tuesday getTuesday() {
            return tuesday;
        }

        public void setTuesday(Tuesday tuesday) {
            this.tuesday = tuesday;
        }

        public Wednesday getWednesday() {
            return wednesday;
        }

        public void setWednesday(Wednesday wednesday) {
            this.wednesday = wednesday;
        }

        public Thursday getThursday() {
            return thursday;
        }

        public void setThursday(Thursday thursday) {
            this.thursday = thursday;
        }

        public Friday getFriday() {
            return friday;
        }

        public void setFriday(Friday friday) {
            this.friday = friday;
        }

        public Saturday getSaturday() {
            return saturday;
        }

        public void setSaturday(Saturday saturday) {
            this.saturday = saturday;
        }

    }

    public class Saturday {

        @SerializedName("is_open")
        @Expose
        private Integer isOpen;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("start_time")
        @Expose
        private String startTime;
        @SerializedName("end_time")
        @Expose
        private String endTime;

        public Integer getIsOpen() {
            return isOpen;
        }

        public void setIsOpen(Integer isOpen) {
            this.isOpen = isOpen;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

    }

    public class Section {
        @SerializedName("price_request")

        public String price_request;
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
        @SerializedName("guest")
        @Expose
        private Integer guest;
        @SerializedName("floor_type")
        @Expose
        private String floorType;
        @SerializedName("status")
        @Expose
        private Integer status;
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

    public class Sunday {

        @SerializedName("is_open")
        @Expose
        private Integer isOpen;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("start_time")
        @Expose
        private String startTime;
        @SerializedName("end_time")
        @Expose
        private String endTime;

        public Integer getIsOpen() {
            return isOpen;
        }

        public void setIsOpen(Integer isOpen) {
            this.isOpen = isOpen;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

    }

    public class Table {
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
        private String slug;
        @SerializedName("number")
        @Expose
        private String number;
        @SerializedName("designation_id")
        @Expose
        private String designationId;
        @SerializedName("guest_per_table")
        @Expose
        private Integer guestPerTable;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("price")
        @Expose
        private String price;
        @SerializedName("price_request")
        @Expose
        private Integer priceRequest;
        @SerializedName("type")
        @Expose
        private Integer type;
        @SerializedName("status")
        @Expose
        private Integer status;
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

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
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


    public class Thursday {

        @SerializedName("is_open")
        @Expose
        private Integer isOpen;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("start_time")
        @Expose
        private String startTime;
        @SerializedName("end_time")
        @Expose
        private String endTime;

        public Integer getIsOpen() {
            return isOpen;
        }

        public void setIsOpen(Integer isOpen) {
            this.isOpen = isOpen;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

    }

    public class Tuesday {

        @SerializedName("is_open")
        @Expose
        private Integer isOpen;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("start_time")
        @Expose
        private String startTime;
        @SerializedName("end_time")
        @Expose
        private String endTime;

        public Integer getIsOpen() {
            return isOpen;
        }

        public void setIsOpen(Integer isOpen) {
            this.isOpen = isOpen;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

    }

    public class Item {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("venue_id")
        @Expose
        private Integer venueId;
        @SerializedName("drink_type")
        @Expose
        private Integer drinkType;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("slug")
        @Expose
        private String slug;
        @SerializedName("price")
        @Expose
        private String price;
        @SerializedName("volume")
        @Expose
        private Integer volume;
        @SerializedName("status")
        @Expose
        private Integer status;
        @SerializedName("type")
        @Expose
        private Integer type;
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

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public Integer getVolume() {
            return volume;
        }

        public void setVolume(Integer volume) {
            this.volume = volume;
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

    public class Item_ {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("user_id")
        @Expose
        private Integer userId;
        @SerializedName("venue_id")
        @Expose
        private Integer venueId;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("slug")
        @Expose
        private String slug;
        @SerializedName("amount")
        @Expose
        private String amount;
        @SerializedName("quantity")
        @Expose
        private Integer quantity;
        @SerializedName("volume")
        @Expose
        private String volume;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("price_request")
        @Expose
        private Integer priceRequest;
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

    public class Item__ {

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
        private String slug;
        @SerializedName("number")
        @Expose
        private String number;
        @SerializedName("designation_id")
        @Expose
        private String designationId;
        @SerializedName("guest_per_table")
        @Expose
        private Integer guestPerTable;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("price")
        @Expose
        private String price;
        @SerializedName("price_request")
        @Expose
        private Integer priceRequest;
        @SerializedName("type")
        @Expose
        private Integer type;
        @SerializedName("status")
        @Expose
        private Integer status;
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

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
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

    public class Item___ {

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
        private String slug;
        @SerializedName("number")
        @Expose
        private String number;
        @SerializedName("designation_id")
        @Expose
        private String designationId;
        @SerializedName("guest_per_table")
        @Expose
        private Integer guestPerTable;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("price")
        @Expose
        private String price;
        @SerializedName("price_request")
        @Expose
        private Integer priceRequest;
        @SerializedName("type")
        @Expose
        private Integer type;
        @SerializedName("status")
        @Expose
        private Integer status;
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

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
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

    public class Item____ {

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
        @SerializedName("total")
        @Expose
        private String total;
        @SerializedName("grand_total")
        @Expose
        private String grandTotal;
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
        @SerializedName("total")
        @Expose
        private String total;
        @SerializedName("grand_total")
        @Expose
        private String grandTotal;
        @SerializedName("item_type")
        @Expose
        private String itemType;
        @SerializedName("item")
        @Expose
        private Item_ item;

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

        public String getItemType() {
            return itemType;
        }

        public void setItemType(String itemType) {
            this.itemType = itemType;
        }

        public Item_ getItem() {
            return item;
        }

        public void setItem(Item_ item) {
            this.item = item;
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
        @SerializedName("item_type")
        @Expose
        private String itemType;
        @SerializedName("item")
        @Expose
        private Item__ item;

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

        public String getItemType() {
            return itemType;
        }

        public void setItemType(String itemType) {
            this.itemType = itemType;
        }

        public Item__ getItem() {
            return item;
        }

        public void setItem(Item__ item) {
            this.item = item;
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
        @SerializedName("item_type")
        @Expose
        private String itemType;
        @SerializedName("item")
        @Expose
        private Item___ item;

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

        public String getItemType() {
            return itemType;
        }

        public void setItemType(String itemType) {
            this.itemType = itemType;
        }

        public Item___ getItem() {
            return item;
        }

        public void setItem(Item___ item) {
            this.item = item;
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
        @SerializedName("item_type")
        @Expose
        private String itemType;
        @SerializedName("image")
        @Expose
        private String image;
        @SerializedName("item")
        @Expose
        private Item____ item;

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

        public String getItemType() {
            return itemType;
        }

        public void setItemType(String itemType) {
            this.itemType = itemType;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public Item____ getItem() {
            return item;
        }

        public void setItem(Item____ item) {
            this.item = item;
        }

    }

    public class QrBottle {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("code")
        @Expose
        private String code;
        @SerializedName("ticket_id")
        @Expose
        private Integer ticketId;
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
        @SerializedName("purchase_type")
        @Expose
        private Integer purchaseType;
        @SerializedName("venue_id")
        @Expose
        private Integer venueId;
        @SerializedName("order_item")
        @Expose
        private List<OrderItem_> orderItem = null;

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
            return "Order No : " + ticketId;
        }

        public Integer getTicketId() {
            return ticketId;
        }

        public void setTicketId(Integer ticketId) {
            this.ticketId = ticketId;
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

        public Integer getPurchaseType() {
            return purchaseType;
        }

        public void setPurchaseType(Integer purchaseType) {
            this.purchaseType = purchaseType;
        }

        public Integer getVenueId() {
            return venueId;
        }

        public void setVenueId(Integer venueId) {
            this.venueId = venueId;
        }

        public List<OrderItem_> getOrderItem() {
            return orderItem;
        }

        public void setOrderItem(List<OrderItem_> orderItem) {
            this.orderItem = orderItem;
        }

    }

    public class QrDrink {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("code")
        @Expose
        private String code;
        @SerializedName("ticket_id")
        @Expose
        private Integer ticketId;
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
        @SerializedName("purchase_type")
        @Expose
        private Integer purchaseType;
        @SerializedName("venue_id")
        @Expose
        private Integer venueId;
        @SerializedName("order_item")
        @Expose
        private List<OrderItem> orderItem = null;

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
            return "Order No : " + ticketId;
        }

        public Integer getTicketId() {
            return ticketId;
        }

        public void setTicketId(Integer ticketId) {
            this.ticketId = ticketId;
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

        public Integer getPurchaseType() {
            return purchaseType;
        }

        public void setPurchaseType(Integer purchaseType) {
            this.purchaseType = purchaseType;
        }

        public Integer getVenueId() {
            return venueId;
        }

        public void setVenueId(Integer venueId) {
            this.venueId = venueId;
        }

        public List<OrderItem> getOrderItem() {
            return orderItem;
        }

        public void setOrderItem(List<OrderItem> orderItem) {
            this.orderItem = orderItem;
        }

    }

    public class QrProduct {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("code")
        @Expose
        private String code;
        @SerializedName("ticket_id")
        @Expose
        private Integer ticketId;
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
        @SerializedName("purchase_type")
        @Expose
        private Integer purchaseType;
        @SerializedName("venue_id")
        @Expose
        private Integer venueId;
        @SerializedName("order_item")
        @Expose
        private List<OrderItem____> orderItem = null;

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
            return "Order No : " + ticketId;
        }

        public Integer getTicketId() {
            return ticketId;
        }

        public void setTicketId(Integer ticketId) {
            this.ticketId = ticketId;
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

        public Integer getPurchaseType() {
            return purchaseType;
        }

        public void setPurchaseType(Integer purchaseType) {
            this.purchaseType = purchaseType;
        }

        public Integer getVenueId() {
            return venueId;
        }

        public void setVenueId(Integer venueId) {
            this.venueId = venueId;
        }

        public List<OrderItem____> getOrderItem() {
            return orderItem;
        }

        public void setOrderItem(List<OrderItem____> orderItem) {
            this.orderItem = orderItem;
        }

    }

    public class QrSection {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("code")
        @Expose
        private String code;
        @SerializedName("ticket_id")
        @Expose
        private Integer ticketId;
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
        @SerializedName("purchase_type")
        @Expose
        private Integer purchaseType;
        @SerializedName("venue_id")
        @Expose
        private Integer venueId;
        @SerializedName("order_item")
        @Expose
        private List<OrderItem___> orderItem = null;

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
            return "Order No : " + ticketId;
        }

        public Integer getTicketId() {
            return ticketId;
        }

        public void setTicketId(Integer ticketId) {
            this.ticketId = ticketId;
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

        public Integer getPurchaseType() {
            return purchaseType;
        }

        public void setPurchaseType(Integer purchaseType) {
            this.purchaseType = purchaseType;
        }

        public Integer getVenueId() {
            return venueId;
        }

        public void setVenueId(Integer venueId) {
            this.venueId = venueId;
        }

        public List<OrderItem___> getOrderItem() {
            return orderItem;
        }

        public void setOrderItem(List<OrderItem___> orderItem) {
            this.orderItem = orderItem;
        }

    }

    public class QrTable {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("code")
        @Expose
        private String code;
        @SerializedName("ticket_id")
        @Expose
        private Integer ticketId;
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
        @SerializedName("purchase_type")
        @Expose
        private Integer purchaseType;
        @SerializedName("venue_id")
        @Expose
        private Integer venueId;
        @SerializedName("order_item")
        @Expose
        private List<OrderItem__> orderItem = null;

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
            return "Order No : " + ticketId;
        }

        public Integer getTicketId() {
            return ticketId;
        }

        public void setTicketId(Integer ticketId) {
            this.ticketId = ticketId;
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

        public Integer getPurchaseType() {
            return purchaseType;
        }

        public void setPurchaseType(Integer purchaseType) {
            this.purchaseType = purchaseType;
        }

        public Integer getVenueId() {
            return venueId;
        }

        public void setVenueId(Integer venueId) {
            this.venueId = venueId;
        }

        public List<OrderItem__> getOrderItem() {
            return orderItem;
        }

        public void setOrderItem(List<OrderItem__> orderItem) {
            this.orderItem = orderItem;
        }

    }

    public class Item_____ {

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
        @SerializedName("item_type")
        @Expose
        private String itemType;

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

        public String getItemType() {
            return itemType;
        }

        public void setItemType(String itemType) {
            this.itemType = itemType;
        }

    }

    public class Qr {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("code")
        @Expose
        private String code;
        @SerializedName("ticket_id")
        @Expose
        private Integer ticketId;
        @SerializedName("bash_id")
        @Expose
        private Object bashId;
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
        @SerializedName("purchase_type")
        @Expose
        private Integer purchaseType;
        @SerializedName("venue_id")
        @Expose
        private Integer venueId;
        @SerializedName("item_section_id")
        @Expose
        private Integer itemSectionId;
        @SerializedName("items")
        @Expose
        private List<Item_____> items = null;

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
            return "Order No : " + ticketId;
        }

        public Integer getTicketId() {
            return ticketId;
        }

        public void setTicketId(Integer ticketId) {
            this.ticketId = ticketId;
        }

        public Object getBashId() {
            return bashId;
        }

        public void setBashId(Object bashId) {
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

        public Integer getPurchaseType() {
            return purchaseType;
        }

        public void setPurchaseType(Integer purchaseType) {
            this.purchaseType = purchaseType;
        }

        public Integer getVenueId() {
            return venueId;
        }

        public void setVenueId(Integer venueId) {
            this.venueId = venueId;
        }

        public Integer getItemSectionId() {
            return itemSectionId;
        }

        public void setItemSectionId(Integer itemSectionId) {
            this.itemSectionId = itemSectionId;
        }

        public List<Item_____> getItems() {
            return items;
        }

        public void setItems(List<Item_____> items) {
            this.items = items;
        }

    }

    public class ItemSection {

        public String qty;
        public String isChecked;
        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("user_id")
        @Expose
        private Integer userId;
        @SerializedName("venue_id")
        @Expose
        private Integer venueId;
        @SerializedName("name")
        @Expose
        private String name;
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
        @SerializedName("items")
        @Expose
        private List<Items> items = null;
        @SerializedName("qr")
        @Expose
        private List<Qr> qr = null;

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

        public List<Items> getItems() {
            return items;
        }

        public void setItems(List<Items> items) {
            this.items = items;
        }

        public List<Qr> getQr() {
            return qr;
        }

        public void setQr(List<Qr> qr) {
            this.qr = qr;
        }
    }

    public class Items {
        @SerializedName("isChecked")

        public String isChecked;
        @SerializedName("qty")

        public String qty;
        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("item_section_id")
        @Expose
        private Integer itemSectionId;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("slug")
        @Expose
        private String slug;
        @SerializedName("price")
        @Expose
        private String price;
        @SerializedName("tax")
        @Expose
        private Integer tax;
        @SerializedName("quantity")
        @Expose
        private Integer quantity;
        @SerializedName("image")
        @Expose
        private String image;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("manage_stock")
        @Expose
        private Integer manageStock;
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

        public Integer getItemSectionId() {
            return itemSectionId;
        }

        public void setItemSectionId(Integer itemSectionId) {
            this.itemSectionId = itemSectionId;
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

        public Integer getTax() {
            return tax;
        }

        public void setTax(Integer tax) {
            this.tax = tax;
        }

        public Integer getQuantity() {
            return quantity;
        }

        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
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

        public Integer getManageStock() {
            return manageStock;
        }

        public void setManageStock(Integer manageStock) {
            this.manageStock = manageStock;
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

    public class BusinessType {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("image")
        @Expose
        private String image;
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

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
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
        @SerializedName("item_sections")
        @Expose
        private List<ItemSection> itemSections = null;
        @SerializedName("business_type")
        @Expose
        private BusinessType businessType;
        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("image")
        @Expose
        private String image;
        @SerializedName("user_id")
        @Expose
        private Integer userId;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("slug")
        @Expose
        private String slug;
        @SerializedName("address")
        @Expose
        private String address;
        @SerializedName("street_address")
        @Expose
        private String streetAddress;
        @SerializedName("city")
        @Expose
        private String city;
        @SerializedName("state")
        @Expose
        private String state;
        @SerializedName("zipcode")
        @Expose
        private String zipcode;
        @SerializedName("country")
        @Expose
        private String country;
        @SerializedName("lat")
        @Expose
        private String lat;
        @SerializedName("lng")
        @Expose
        private String lng;
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("phone")
        @Expose
        private String phone;
        @SerializedName("attachment")
        @Expose
        private String attachment;
        @SerializedName("is_approve")
        @Expose
        private Integer isApprove;
        @SerializedName("is_draft")
        @Expose
        private Integer isDraft;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;
        @SerializedName("roster")
        @Expose
        private Roster roster;
        @SerializedName("section_plan_image")
        @Expose
        private List<String> sectionPlanImage = null;
        @SerializedName("table_plan_image")
        @Expose
        private List<String> tablePlanImage = null;
        @SerializedName("bottles")
        @Expose
        private List<Bottle> bottles = null;
        @SerializedName("drinks")
        @Expose
        private List<Drink> drinks = null;
        @SerializedName("sections")
        @Expose
        private List<Section> sections = null;
        @SerializedName("tables")
        @Expose
        private List<Table> tables = null;
        @SerializedName("products")
        @Expose
        private List<Product> products = null;
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
        @SerializedName("qr_product")
        @Expose
        private List<QrProduct> qrProduct = null;

        public List<ItemSection> getItemSections() {
            return itemSections;
        }

        public void setItemSections(List<ItemSection> itemSections) {
            this.itemSections = itemSections;
        }

        public BusinessType getBusinessType() {
            return businessType;
        }

        public void setBusinessType(BusinessType businessType) {
            this.businessType = businessType;
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

        public List<QrProduct> getQrProduct() {
            return qrProduct;
        }

        public void setQrProduct(List<QrProduct> qrProduct) {
            this.qrProduct = qrProduct;
        }

        public String getShareData(Context mContext) {
            return String.format(mContext.getString(R.string.venue_share_text), name, address,
                    Const.getvenueShareLink(mContext, id.toString()));
        }


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

        public String getSlug() {
            return slug;
        }

        public void setSlug(String slug) {
            this.slug = slug;
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

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
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

        public String getAttachment() {
            return attachment;
        }

        public void setAttachment(String attachment) {
            this.attachment = attachment;
        }

        public Integer getIsApprove() {
            return isApprove;
        }

        public void setIsApprove(Integer isApprove) {
            this.isApprove = isApprove;
        }

        public Integer getIsDraft() {
            return isDraft;
        }

        public void setIsDraft(Integer isDraft) {
            this.isDraft = isDraft;
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

        public Roster getRoster() {
            return roster;
        }

        public void setRoster(Roster roster) {
            this.roster = roster;
        }

        public List<String> getSectionPlanImage() {
            return sectionPlanImage;
        }

        public void setSectionPlanImage(List<String> sectionPlanImage) {
            this.sectionPlanImage = sectionPlanImage;
        }

        public List<String> getTablePlanImage() {
            return tablePlanImage;
        }

        public void setTablePlanImage(List<String> tablePlanImage) {
            this.tablePlanImage = tablePlanImage;
        }

        public List<Bottle> getBottles() {
            return bottles;
        }

        public void setBottles(List<Bottle> bottles) {
            this.bottles = bottles;
        }

        public List<Drink> getDrinks() {
            return drinks;
        }

        public void setDrinks(List<Drink> drinks) {
            this.drinks = drinks;
        }

        public List<Section> getSections() {
            return sections;
        }

        public void setSections(List<Section> sections) {
            this.sections = sections;
        }

        public List<Table> getTables() {
            return tables;
        }

        public void setTables(List<Table> tables) {
            this.tables = tables;
        }

        public List<Product> getProducts() {
            return products;
        }

        public void setProducts(List<Product> products) {
            this.products = products;
        }

    }

    public class Wednesday {

        @SerializedName("is_open")
        @Expose
        private Integer isOpen;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("start_time")
        @Expose
        private String startTime;
        @SerializedName("end_time")
        @Expose
        private String endTime;

        public Integer getIsOpen() {
            return isOpen;
        }

        public void setIsOpen(Integer isOpen) {
            this.isOpen = isOpen;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

    }
}
