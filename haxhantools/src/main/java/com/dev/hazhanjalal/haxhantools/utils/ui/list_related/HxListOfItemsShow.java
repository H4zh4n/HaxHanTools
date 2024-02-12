package com.dev.hazhanjalal.haxhantools.utils.ui.list_related;

import com.dev.hazhanjalal.haxhantools.utils.ui.HxListOfItems;

public class HxListOfItemsShow {
    
    private HxListOfItems items;
    
    public HxListOfItemsShow(HxListOfItems items) {
        this.items = items;
    }
    
    public void show() {
        if (items != null) {
            items.show();
        }
    }
}