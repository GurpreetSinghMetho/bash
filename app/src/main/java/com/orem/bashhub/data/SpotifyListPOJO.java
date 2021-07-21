package com.orem.bashhub.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SpotifyListPOJO {
    @SerializedName("items")
    @Expose
    public List<Item> items = null;
    @SerializedName("limit")
    @Expose
    public Integer limit;
    @SerializedName("offset")
    @Expose
    public Integer offset;
    @SerializedName("total")
    @Expose
    public Integer total;
    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("href")
    @Expose
    public String href;

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @SerializedName("display_name")
    @Expose
    public String displayName;
    @SerializedName("external_urls")
    @Expose
    private ExternalUrls_ externalUrls;

    public ExternalUrls_ getExternalUrls() {
        return externalUrls;
    }

    public void setExternalUrls(ExternalUrls_ externalUrls) {
        this.externalUrls = externalUrls;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }


    public static class Image {

        @SerializedName("height")
        @Expose
        public Integer height;
        @SerializedName("url")
        @Expose
        public String url;
        @SerializedName("width")
        @Expose
        public Integer width;

        public Integer getHeight() {
            return height;
        }

        public void setHeight(Integer height) {
            this.height = height;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public Integer getWidth() {
            return width;
        }

        public void setWidth(Integer width) {
            this.width = width;
        }

    }

    public static class Item {
        @SerializedName("href")
        @Expose
        public String href;
        @SerializedName("selected")
        @Expose
        public boolean selected;
        @SerializedName("id")
        @Expose
        public String id;
        @SerializedName("images")
        @Expose
        public List<Image> images = null;
        @SerializedName("name")
        @Expose
        public String name;
        @SerializedName("owner")
        @Expose
        public Owner owner;
        @SerializedName("external_urls")
        @Expose
        private ExternalUrls externalUrls;

        public ExternalUrls getExternalUrls() {
            return externalUrls;
        }

        public void setExternalUrls(ExternalUrls externalUrls) {
            this.externalUrls = externalUrls;
        }

        public boolean isSelected() {
            return selected;
        }

        public void setSelected(boolean selected) {
            this.selected = selected;
        }

        public String getHref() {
            return href;
        }

        public void setHref(String href) {
            this.href = href;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public List<Image> getImages() {
            return images;
        }

        public void setImages(List<Image> images) {
            this.images = images;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Owner getOwner() {
            return owner;
        }

        public void setOwner(Owner owner) {
            this.owner = owner;
        }

    }

    public static class ExternalUrls {

        @SerializedName("spotify")
        @Expose
        private String spotify;

        public String getSpotify() {
            return spotify;
        }

        public void setSpotify(String spotify) {
            this.spotify = spotify;
        }

    }

    public class ExternalUrls_ {

        @SerializedName("spotify")
        @Expose
        private String spotify;

        public String getSpotify() {
            return spotify;
        }

        public void setSpotify(String spotify) {
            this.spotify = spotify;
        }

    }

    public class Owner {

        @SerializedName("display_name")
        @Expose
        public String displayName;
        @SerializedName("href")
        @Expose
        public String href;
        @SerializedName("id")
        @Expose
        public String id;

        public String getDisplayName() {
            return displayName;
        }

        public void setDisplayName(String displayName) {
            this.displayName = displayName;
        }

        public String getHref() {
            return href;
        }

        public void setHref(String href) {
            this.href = href;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

    }
}
