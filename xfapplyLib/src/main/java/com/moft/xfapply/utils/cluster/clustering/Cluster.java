/*
 * Copyright (C) 2015 Baidu, Inc. All Rights Reserved.
 */

package com.moft.xfapply.utils.cluster.clustering;


import com.baidu.mapapi.model.LatLng;

import java.util.Collection;

/**
 * A collection of ClusterItems that are nearby each other.
 */
public interface Cluster<T extends com.moft.xfapply.utils.cluster.clustering.ClusterItem> {
    public LatLng getPosition();

    Collection<T> getItems();
    void resetPosition();

    int getSize();
}