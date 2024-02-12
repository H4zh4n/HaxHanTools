package com.dev.hazhanjalal.haxhantools.utils.implementations;

import com.dev.hazhanjalal.haxhantools.utils.ui.HxListOfItems;

public abstract class OnHxItemClickListener<T> {
    
    public String itemText = "";
    public HxListOfItems thisHxDialogObject = null;
    public int itemPosition = -1;
    
    public T itemObject;
    
    public abstract void onItemClicked(int position);
    
    
    public void onItemLongClicked(int position) {
    
    }
    
}
