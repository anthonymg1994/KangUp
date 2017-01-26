package com.mx.bridgestudio.kangup.Adapters;

/**
 * Created by Isaac on 25/01/2017.
 */

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

public class RouteTouchHelper extends ItemTouchHelper.SimpleCallback{

    private AdapterRoutes mRouteAdapter;

    public RouteTouchHelper(AdapterRoutes routeAdapter){
        super(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        this.mRouteAdapter = routeAdapter;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        mRouteAdapter.swap(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        mRouteAdapter.remove(viewHolder.getAdapterPosition());
    }
}
