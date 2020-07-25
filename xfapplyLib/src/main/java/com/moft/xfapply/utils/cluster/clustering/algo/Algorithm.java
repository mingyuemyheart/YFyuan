/*
 * Copyright (C) 2015 Baidu, Inc. All Rights Reserved.
 */

package com.moft.xfapply.utils.cluster.clustering.algo;


import com.moft.xfapply.utils.cluster.clustering.Cluster;
import com.moft.xfapply.utils.cluster.clustering.ClusterItem;

import java.util.Collection;
import java.util.Set;

/**
 * Logic for computing clusters
 */
public interface Algorithm<T extends ClusterItem> {
    void addItem(T item);

    void addItems(Collection<T> items);

    void clearItems();

    void removeItem(T item);

    Set<? extends Cluster<T>> getClusters(double zoom);

    Collection<T> getItems();
}