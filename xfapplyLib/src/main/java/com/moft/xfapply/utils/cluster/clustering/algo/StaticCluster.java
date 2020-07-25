/*
 * Copyright (C) 2015 Baidu, Inc. All Rights Reserved.
 */

package com.moft.xfapply.utils.cluster.clustering.algo;

import com.moft.xfapply.utils.cluster.clustering.Cluster;
import com.moft.xfapply.utils.cluster.clustering.ClusterItem;
import com.baidu.mapapi.model.LatLng;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * A cluster whose center is determined upon creation.
 */
public class StaticCluster<T extends ClusterItem> implements Cluster<T> {
    private LatLng mCenter;
    private final List<T> mItems = new ArrayList<T>();

    public StaticCluster(LatLng center) {
        mCenter = center;
    }



    public boolean add(T t) {
        return mItems.add(t);
    }

    @Override
    public LatLng getPosition() {
        return mCenter;
    }

    @Override
    public void resetPosition() {
        double lat = 0;
        double lng = 0;
        for (T t : mItems) {
            lat += t.getPosition().latitude;
            lng += t.getPosition().longitude;
        }
        lat = lat / getSize();
        lng = lng / getSize();
        mCenter = new LatLng(lat, lng);
    }

    public boolean remove(T t) {
        return mItems.remove(t);
    }

    @Override
    public Collection<T> getItems() {
        return mItems;
    }

    @Override
    public int getSize() {
        return mItems.size();
    }

    @Override
    public String toString() {
        return "StaticCluster{"
                + "mCenter=" + mCenter
                + ", mItems.size=" + mItems.size()
                + '}';
    }
}