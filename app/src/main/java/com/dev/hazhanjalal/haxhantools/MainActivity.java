package com.dev.hazhanjalal.haxhantools;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        
        
        /*
        new Utils(this);
        ArrayList itms = new ArrayList();
        itms.add("Suli");
        itms.add("Erbil");
        itms.add("Karkuk");
        
        new HxListOfItems(this)
                .setTitle("Select city")
                .setSearchHint("Search city")
                .setDialogBackgroundColor(Utils.getColor(R.color.colorWhiteDark))
                .setItemBackgroundColor(Utils.getColor(R.color.colorWhiteLight))
                .setItems(itms)
                .setSelectedItemImage(R.drawable.ic_check, Utils.getColor(android.R.color.transparent))
                .setHintBackgroundColor(Utils.getColor(R.color.white))
                .setItemTextColor(Utils.getColor(R.color.black))
                .setSelectedItemIndex(0)
                .onSingleItemClickListener(new OnHxItemClickListener() {
                    @Override
                    public void onItemClicked() {
                        Logger.v("=" + itemText);
                        Logger.v("=" + selectedItem);
                    }
                })
                .show();*/
        
    }
}
