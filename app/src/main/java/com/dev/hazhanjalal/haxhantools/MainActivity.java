package com.dev.hazhanjalal.haxhantools;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        /*new Utils(this);
        ArrayList<Item> itms = new ArrayList();
        itms.add(new Item(1, "Sulaymani"));
        itms.add(new Item(2, "Erbil"));
        itms.add(new Item(3, "Karkuk"));
        
        new HxListOfItems<Item>(this)
                .setItems(itms)
                .setItemTextProvider(new ItemTextProvider() {
                    @NonNull
                    @Override
                    public CharSequence getText(int position) {
                        return itms.get(position).name;
                    }
                })
                .onSingleItemClickListener(new OnHxItemClickListener<Item>() {
                    @Override
                    public void onItemClicked() {
                        Logger.v("=" + itemText);
                        Logger.v("=" + selectedItem);
                    }
                })
                .show();*/
        
    }
}

class Item {
    public String name;
    int id;
    
    public Item(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
