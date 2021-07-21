package com.orem.bashhub.utils.clustering;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;

import androidx.core.content.ContextCompat;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
import com.google.maps.android.ui.IconGenerator;
import com.orem.bashhub.R;
import com.orem.bashhub.activity.BaseActivity;

import java.util.Objects;

public class CustomClusterRenderer extends DefaultClusterRenderer<MyClusterItem> {

    private final Context mContext;
    private final IconGenerator mClusterIconGenerator;
    private GoogleMap map;
    private double zoom = 0;

    public CustomClusterRenderer(Context context, GoogleMap map, ClusterManager<MyClusterItem> clusterManager) {
        super(context, map, clusterManager);
        mContext = context;
        mClusterIconGenerator = new IconGenerator(mContext.getApplicationContext());
        this.map = map;
    }

    @Override
    protected void onBeforeClusterItemRendered(MyClusterItem item, MarkerOptions markerOptions) {
        markerOptions.icon(item.getmBitmap());
    }

    @Override
    protected void onBeforeClusterRendered(Cluster<MyClusterItem> cluster, MarkerOptions markerOptions) {
        if (cluster.getSize() < 21) {
            mClusterIconGenerator.setBackground(ContextCompat.getDrawable(mContext, R.drawable.custom_cluster_bg_1));
        } else if (cluster.getSize() > 20 && cluster.getSize() < 50) {
            mClusterIconGenerator.setBackground(ContextCompat.getDrawable(mContext, R.drawable.custom_cluster_bg_2));
        } else {
            mClusterIconGenerator.setBackground(ContextCompat.getDrawable(mContext, R.drawable.custom_cluster_bg_3));
        }
        LayoutInflater myInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams")
        View clusterView = Objects.requireNonNull(myInflater).inflate(R.layout.cluster_view, null, false);
        mClusterIconGenerator.setContentView(clusterView);
        final Bitmap icon = mClusterIconGenerator.makeIcon(String.valueOf(cluster.getSize()));
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon));
    }

    @Override
    protected boolean shouldRenderAsCluster(Cluster<MyClusterItem> cluster) {
        ((BaseActivity) mContext).runOnUiThread(() -> zoom = map.getCameraPosition().zoom);
        //return cluster.getSize() > 1;
        return cluster.getSize() > 1 && zoom < 19;
    }
}
