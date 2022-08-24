package com.dev.hazhanjalal.haxhantools.utils.custom_auto_complete;

import android.os.Parcelable;

import com.dev.hazhanjalal.haxhantools.R;
import com.dev.hazhanjalal.haxhantools.utils.utils.Utils;
import com.frogobox.recycler.widget.FrogoRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ClassFrogoData {
    public String searchFor;
    public Parcelable state;
    public boolean isLinear = false;
    int backgroundColor = Utils.getColor(R.color.colorGreenChosen);
    FrogoRecyclerView frg;
    List<String> dataIncluded;
    List<String> autoCompleteList;
    
    public ClassFrogoData(FrogoRecyclerView frg, List<String> dataIncluded, ArrayList<String> autoCompleteList) {
        this.frg = frg;
        this.dataIncluded = dataIncluded;
        this.autoCompleteList = autoCompleteList;
    }
    
    public ClassFrogoData(FrogoRecyclerView frg, List<String> dataIncluded, ArrayList<String> autoCompleteList, int backgroundColor) {
        this.frg = frg;
        this.autoCompleteList = autoCompleteList;
        this.dataIncluded = dataIncluded;
        this.backgroundColor = backgroundColor;
    }
    
    
    public ClassFrogoData(FrogoRecyclerView frg, List<String> dataIncluded, ArrayList<String> autoCompleteList, boolean isLinear) {
        this.frg = frg;
        this.autoCompleteList = autoCompleteList;
        this.dataIncluded = dataIncluded;
        this.isLinear = isLinear;
    }
    
    public ClassFrogoData(FrogoRecyclerView frg, List<String> dataIncluded, ArrayList<String> autoCompleteList, int backgroundColor, boolean isLinear) {
        this.frg = frg;
        this.autoCompleteList = autoCompleteList;
        this.dataIncluded = dataIncluded;
        this.backgroundColor = backgroundColor;
        this.isLinear = isLinear;
    }
    
}
