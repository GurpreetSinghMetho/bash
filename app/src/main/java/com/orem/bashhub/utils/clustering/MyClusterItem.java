/*
 * Copyright 2013 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.orem.bashhub.utils.clustering;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;
import com.orem.bashhub.data.BashDetailsPOJO;
import com.orem.bashhub.data.VenueListPOJO;

public class MyClusterItem implements ClusterItem {
    private final LatLng mPosition;
    private String mTitle;
    private String mSnippet;
    private BitmapDescriptor mBitmap;
    private BashDetailsPOJO bashdetail;
    private VenueListPOJO.Venue venue;

    public MyClusterItem(double lat, double lng, BitmapDescriptor mBitmap, BashDetailsPOJO bashdetail) {
        mPosition = new LatLng(lat, lng);
        this.mBitmap = mBitmap;
        this.bashdetail = bashdetail;
        mTitle = null;
        mSnippet = null;
    }

    public MyClusterItem(double lat, double lng, BitmapDescriptor mBitmap, VenueListPOJO.Venue bashdetail) {
        mPosition = new LatLng(lat, lng);
        this.mBitmap = mBitmap;
        this.venue = bashdetail;
        mTitle = null;
        mSnippet = null;
    }

    public MyClusterItem(double lat, double lng, String title, String snippet) {
        mPosition = new LatLng(lat, lng);
        mTitle = title;
        mSnippet = snippet;
    }

    @Override
    public LatLng getPosition() {
        return mPosition;
    }

    @Override
    public String getTitle() {
        return mTitle;
    }

    /**
     * Set the title of the marker
     *
     * @param title string to be set as title
     */
    public void setTitle(String title) {
        mTitle = title;
    }

    @Override
    public String getSnippet() {
        return mSnippet;
    }

    /**
     * Set the description of the marker
     *
     * @param snippet string to be set as snippet
     */
    public void setSnippet(String snippet) {
        mSnippet = snippet;
    }

    public BitmapDescriptor getmBitmap() {
        return mBitmap;
    }

    public BashDetailsPOJO getBashdetail() {
        return bashdetail;
    }

    public VenueListPOJO.Venue getVenudetail() {
        return venue;
    }
}
