package com.dev.hazhanjalal.haxhantools;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        /*new Utils(this);
        ArrayList<Warehouses> itms = new ArrayList();
        itms.add(new Warehouses(1, "Sulaymani"));
        itms.add(new Warehouses(2, "Erbil"));
        itms.add(new Warehouses(3, "Karkuk"));
        
        new HxListOfItems<Warehouses>(this)
                .setItems(itms)
                .setItemTextProvider(new ItemTextProvider() {
                    @NonNull
                    @Override
                    public CharSequence getText(int position) {
                        return itms.get(position).name;
                    }
                })
                .setOnItemClickListener(new OnHxItemClickListener<Warehouses>() {
                    @Override
                    public void onItemClicked(int position) {
                        Logger.v("=" + itemText);
                        Logger.v("=" + itemObject);
                    }
                    
                })
                .show();*/
        
    }
}

