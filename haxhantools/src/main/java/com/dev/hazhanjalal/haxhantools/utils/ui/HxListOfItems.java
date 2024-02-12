package com.dev.hazhanjalal.haxhantools.utils.ui;

import static com.dev.hazhanjalal.haxhantools.utils.print.Logger.e;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Parcelable;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.dev.hazhanjalal.haxhantools.R;
import com.dev.hazhanjalal.haxhantools.utils.implementations.CustomAction;
import com.dev.hazhanjalal.haxhantools.utils.implementations.OnHxItemClickListener;
import com.dev.hazhanjalal.haxhantools.utils.ui.list_related.ItemTextProvider;
import com.dev.hazhanjalal.haxhantools.utils.utils.Utils;
import com.frogobox.recycler.core.FrogoRecyclerNotifyListener;
import com.frogobox.recycler.core.IFrogoViewAdapter;
import com.frogobox.recycler.widget.FrogoRecyclerView;

import java.util.ArrayList;
import java.util.Arrays;

public class HxListOfItems<T> {
    Context context;
    ArrayList<T> arItems = new ArrayList<>();
    ArrayList<T> arItemsAllItems = new ArrayList<>();
    Dialog dialog;
    IFrogoViewAdapter adptItems;
    String searchText;
    int colorFoundTextInSearch;
    View btnClearSearchText;
    ItemTextProvider itemTextProvider;
    OnHxItemClickListener onItemClickListener;
    
    boolean closeDialogOnItemClick = true;
    boolean closeDialogOnItemLongClick = false;
    
    
    CustomAction actionDoAfterShown = null;
    boolean ignoreCaseInSearch = true;
    Bitmap bmpItemIcon;
    Bitmap bmpSelectedItemIcon;
    
    /*CustomAction actionRightButton;
    CustomAction actionLeftButton;
    
    boolean showRightButton = false;
    boolean showLeftButton = false;
    
    int rightButtonImageResource = 0;
    int leftButtonImageResource = 0;
    
    int rightButtonBackgroundColor = Utils.getColor(R.color.colorRedChosen);
    int leftButtonBackgroundColor = Utils.getColor(R.color.colorGreenChosen);*/
    int itemBackgroundColor, itemTextColor;
    int selectedItemBackgroundColor, selectedItemTextColor;
    
    boolean scrollToSelectedItemWhenDialogOpens = true;
    
    FrogoRecyclerView frgList;
    LinearLayout fullDialogLayout;
    Parcelable stateScroll;
    int selectedIndex = -1;
    String selectedItemText = null;
    boolean checkSelectedItemThatContains = false;
    
    int itemImageBackgroundColor, selectedItemImageBackgroundColor;
    
    public HxListOfItems(Context ctx) {
        context = ctx;
        
        dialog = new Dialog(context, R.style.alert);
        dialog.setContentView(R.layout.hx_show_item_list);
        
        View btnClose = dialog.findViewById(R.id.btnClose);
        frgList = dialog.findViewById(R.id.frgItemList);
        
        fullDialogLayout = dialog.findViewById(R.id.fullDialogLayout);
        
        EditText etBookSearch = dialog.findViewById(R.id.etBookSearch);
        btnClearSearchText = dialog.findViewById(R.id.btnClearSearchText);
        btnClearSearchText.setVisibility(View.INVISIBLE);
        
        btnClearSearchText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etBookSearch.setText("");
            }
        });
        
        frgList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                //Frogo Scroll
                stateScroll = recyclerView.getLayoutManager().onSaveInstanceState();
            }
        });
        
        //onItemClickListener(null);
        onItemClickListener = null;
        
        enableSearch();
        
        colorFoundTextInSearch = Utils.getColor(context, R.color.colorRedChosen);
        itemBackgroundColor = Utils.getColor(context, R.color.colorBlackTransparent);
        selectedItemBackgroundColor = Utils.getColor(context, R.color.colorBlueChosen);
        
        itemImageBackgroundColor = Utils.getColor(context, R.color.colorBlackDark);
        selectedItemImageBackgroundColor = Utils.getColor(context, R.color.colorBlackDark);
        
        selectedItemTextColor = Utils.getColor(context, R.color.white);
        itemTextColor = Utils.getColor(context, R.color.white);
        
        scrollToSelectedItemWhenDialogOpens = true;
        checkSelectedItemThatContains = false;
        
        selectedItemText = null;
        selectedIndex = -1;
        
        closeDialogOnItemClick = true;
        closeDialogOnItemLongClick = false;
        
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }
    
    
    public HxListOfItems setDialogBackgroundColor(int color) {
        if (fullDialogLayout != null) {
            fullDialogLayout.setBackgroundColor(color);
        }
        
        frgList.setBackgroundColor(color);
        dialog.findViewById(R.id.loSearch).setBackgroundColor(color);
        return this;
    }
    
    
    public HxListOfItems setHintBackgroundColor(int color) {
        Utils.setBackgroundTint(context, color, false, dialog.findViewById(R.id.loHint));
        return this;
    }
    
    
    public HxListOfItems setItemTextProvider(ItemTextProvider itemTextProvider) {
        this.itemTextProvider = itemTextProvider;
        
        return this;
    }
    
    public HxListOfItems setOnItemClickListener(OnHxItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
        return this;
    }
    
    public HxListOfItems setHintTextColor(int color) {
        ((EditText) dialog.findViewById(R.id.etBookSearch)).setTextColor(color);
        return this;
    }
    
    public HxListOfItems doAfterDialogShown(CustomAction action) {
        actionDoAfterShown = action;
        return this;
    }
    
    public HxListOfItems setItems(ArrayList<T> items) {
        arItems.addAll(items);
        arItemsAllItems.addAll(arItems);
        return this;
    }
    
    
    public HxListOfItems setItems(T[] items) {
        arItems.addAll(Arrays.asList(items));
        arItemsAllItems.addAll(arItems);
        return this;
    }
    
    
    public HxListOfItems showCloseButton(boolean show) {
        View btnClose = dialog.findViewById(R.id.btnClose);
        
        if (show) {
            btnClose.setVisibility(View.VISIBLE);
        } else {
            btnClose.setVisibility(View.GONE);
        }
        
        return this;
    }
    
    public HxListOfItems setTitle(String title) {
        TextView tvTitle = dialog.findViewById(R.id.tvTitle);
        tvTitle.setText(title);
        return this;
    }
    
    public HxListOfItems setTitleBarBackgroundColor(int backgroundColor) {
        LinearLayout loTitleBar = dialog.findViewById(R.id.loTitleBar);
        Utils.setBackgroundTintForce(backgroundColor, loTitleBar);
        return this;
    }
    
    public HxListOfItems setCloseDialogOnItemClick(boolean closeDialogOnItemClick) {
        this.closeDialogOnItemClick = closeDialogOnItemClick;
        return this;
    }
    
    public HxListOfItems setCloseDialogOnItemLongClick(boolean closeDialogOnItemLongClick) {
        this.closeDialogOnItemLongClick = closeDialogOnItemLongClick;
        return this;
    }
    
    public HxListOfItems setItemImage(Bitmap bitmapItemIcon, int backgroundColor) {
        this.bmpItemIcon = bitmapItemIcon;
        this.itemImageBackgroundColor = backgroundColor;
        return this;
    }
    
    public HxListOfItems setItemImage(int idItemIcon, int backgroundColor) {
        
        try {
            this.bmpItemIcon = Utils.drawableToBitmap(Utils.getDrawable(idItemIcon));
        } catch (Exception e) {
            e(e);
        }
        
        this.itemImageBackgroundColor = backgroundColor;
        return this;
    }
    
    public HxListOfItems setSelectedItemImage(Bitmap bitmapSelectedItemIcon, int backgroundColor) {
        this.bmpSelectedItemIcon = bitmapSelectedItemIcon;
        this.selectedItemImageBackgroundColor = backgroundColor;
        return this;
    }
    
    
    public HxListOfItems setSelectedItemImage(int idSelectedItemIcon, int backgroundColor) {
        
        try {
            this.bmpSelectedItemIcon = Utils.drawableToBitmap(Utils.getDrawable(idSelectedItemIcon));
        } catch (Exception e) {
            e(e);
        }
        
        this.selectedItemImageBackgroundColor = backgroundColor;
        return this;
    }
    
    public HxListOfItems setTitleBarTextColor(int textColor) {
        TextView tvTitle = dialog.findViewById(R.id.tvTitle);
        tvTitle.setTextColor(textColor);
        return this;
    }
    
    
    public HxListOfItems setCloseButtonColor(int color) {
        ImageView btnClose = dialog.findViewById(R.id.btnClose);
        btnClose.setImageDrawable(Utils.setDrawableTint(btnClose.getDrawable(), color));
        return this;
    }
    
    public HxListOfItems onListCloseListener(CustomAction action) {
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                if (action != null) {
                    action.positiveButtonPressed();
                }
            }
        });
        return this;
    }
    
    public HxListOfItems setSelectedItemIndex(int index) {
        selectedIndex = index;
        return this;
    }
    
    public HxListOfItems setSelectedItem(String itemText) {
        selectedItemText = itemText;
        return this;
    }
    
    
    public HxListOfItems setSelectFirstItemThatContains(String itemText) {
        checkSelectedItemThatContains = true;
        return setSelectedItem(itemText);
    }
    
    public HxListOfItems scrollToSelectedItemWhenDialogOpens(boolean scrollToSelectedItem) {
        scrollToSelectedItemWhenDialogOpens = scrollToSelectedItem;
        return this;
    }
    
    public HxListOfItems setFoundPortionTextColor(int color) {
        colorFoundTextInSearch = color;
        return this;
    }
    
    public HxListOfItems setSelectedItemBackgroundColor(int backgroundColor) {
        selectedItemBackgroundColor = backgroundColor;
        return this;
    }
    
    
    public HxListOfItems setSelectedItemTextColor(int textColor) {
        selectedItemTextColor = textColor;
        return this;
    }
    
    
    public HxListOfItems setItemBackgroundColor(int backgroundColor) {
        itemBackgroundColor = backgroundColor;
        return this;
    }
    
    public HxListOfItems setItemTextColor(int textColor) {
        itemTextColor = textColor;
        return this;
    }
    
    public HxListOfItems enableSearch() {
        return enableSearch(true);
    }
    
    public HxListOfItems enableSearch(boolean enableSearch) {
        return enableSearch(enableSearch, true);
    }
    
    public HxListOfItems enableSearch(boolean enableSearch, boolean ignoreCaseInSearch) {
        this.ignoreCaseInSearch = ignoreCaseInSearch;
        
        ViewGroup loSearch = dialog.findViewById(R.id.loSearch);
        EditText etBookSearch = dialog.findViewById(R.id.etBookSearch);
        
        if (enableSearch) {
            loSearch.setVisibility(View.VISIBLE);
            
            etBookSearch.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                
                }
                
                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    searchText = etBookSearch.getText().toString();
                    
                    arItems.clear();
                    arItems.addAll(arItemsAllItems);
                    
                    if (searchText.isEmpty()) {
                        btnClearSearchText.setVisibility(View.INVISIBLE);
                    } else {
                        btnClearSearchText.setVisibility(View.VISIBLE);
                        
                        for (int j = arItems.size() - 1; j >= 0; j--) {
                            if (ignoreCaseInSearch) {
                                
                                try {
                                    String text = "";
                                    
                                    if (itemTextProvider != null) {
                                        text = itemTextProvider.getText(j).toString(); // text
                                    } else {
                                        text = ""; // text
                                    }
                                    
                                    if (!text.toString().toLowerCase().contains(searchText.toLowerCase())) {
                                        arItems.remove(j);
                                    }
                                } catch (Exception e) {
                                    e(e);
                                }
                                
                            } else {
                                
                                try {
                                    
                                    String text = "";
                                    
                                    if (itemTextProvider != null) {
                                        text = itemTextProvider.getText(j).toString(); // text
                                    } else {
                                        text = ""; // text
                                    }
                                    
                                    if (!text.toString().contains(searchText)) {
                                        arItems.remove(j);
                                    }
                                } catch (Exception e) {
                                
                                }
                            }
                            
                        }
                    }
                    
                    if (frgList != null) {
                        frgList.injector()
                                .addData(arItems)
                                .addCallback(adptItems)
                                .createLayoutLinearVertical(false)
                                .addCustomView(R.layout.hx_layout_item_list)
                                .addEmptyView(R.layout.hx_no_data_found)
                                .build();
                        
                        /*if (stateScroll != null) {
                            frgList.getLayoutManager().onRestoreInstanceState(stateScroll);
                        }*/
                    }
                }
                
                @Override
                public void afterTextChanged(Editable editable) {
                
                }
            });
        } else {
            loSearch.setVisibility(View.GONE);
            searchText = null;
            etBookSearch.addTextChangedListener(null);
        }
        
        return this;
    }
    
    
    public HxListOfItems setSearchTextColor(int textColor) {
        EditText etBookSearch = dialog.findViewById(R.id.etBookSearch);
        etBookSearch.setTextColor(textColor);
        return this;
    }
    
    
    public HxListOfItems setSearchHint(String hint) {
        EditText etBookSearch = dialog.findViewById(R.id.etBookSearch);
        etBookSearch.setHint(hint);
        return this;
    }
    
    public void show() {
        adptItems = new IFrogoViewAdapter() {
            @Override
            public void setupInitComponent(@NonNull View view, Object o, int index, @NonNull FrogoRecyclerNotifyListener frogoRecyclerNotifyListener) {
                CardView topLayout = view.findViewById(R.id.topLayout);
                
                topLayout.setCardBackgroundColor(itemBackgroundColor);
                
                topLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (onItemClickListener != null) {
                            onItemClickListener.itemPosition = index; // index
                            
                            onItemClickListener.thisHxDialogObject = HxListOfItems.this; // Current Object
                            
                            try {
                                if (itemTextProvider != null) {
                                    onItemClickListener.itemText = itemTextProvider.getText(index).toString(); // text
                                } else {
                                    onItemClickListener.itemText = ""; // text
                                }
                            } catch (Exception e) {
                                e(e);
                            }
                            
                            onItemClickListener.itemObject = o;
                            
                            onItemClickListener.onItemClicked(index);
                        }
                        
                        if (closeDialogOnItemClick) {
                            dialog.dismiss();
                        }
                    }
                });
                
                topLayout.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        if (onItemClickListener != null) {
                            onItemClickListener.itemPosition = index; // index
                            
                            onItemClickListener.thisHxDialogObject = HxListOfItems.this; // Current Object
                            
                            if (itemTextProvider != null) {
                                onItemClickListener.itemText = itemTextProvider.getText(index).toString(); // text
                            } else {
                                onItemClickListener.itemText = ""; // text
                            }
                            
                            onItemClickListener.itemObject = o;
                            
                            onItemClickListener.onItemLongClicked(index);
                        }
                        
                        if (closeDialogOnItemLongClick) {
                            dialog.dismiss();
                        }
                        
                        return true;
                    }
                });
                
                TextView tvItem = view.findViewById(R.id.tvItem);
                
                // grab the variable from O
                if (itemTextProvider != null) {
                    tvItem.setText(itemTextProvider.getText(index));
                } else {
                    tvItem.setText("* be sure to call [setItemTextProvider] to provide text for items.");
                }
                
                tvItem.setTextColor(itemTextColor);
                
                if (searchText != null && !searchText.isEmpty()) {
                    if (o.toString().contains(searchText)) {
                        String colordText = o.toString().replaceAll(searchText, "<font color=\"" +
                                Utils.colorIntToHex(colorFoundTextInSearch)
                                + "\">" + searchText + "</font>");
                        tvItem.setText(Html.fromHtml(colordText));
                    }
                }
                
                tvItem.setTypeface(tvItem.getTypeface(), Typeface.NORMAL);
                
                //for index, searchText has to be null or empty to change background of item.
                //because user can say highlight index 10, and then search for something,
                //in this example in both cases regardless of the item data index 10 will be highlighted.
                //hence the second condition after [index == selectedIndex] is added to prevent that.
                // for selectedItemText it's ok and no condition is needed since even when searching for an item
                //it's still ok to highlight it if they match.
                ImageView btnLeft = view.findViewById(R.id.btnLeft);
                btnLeft.setImageBitmap(null);
                
                //if it's requested to have an image to left of the data [whether selected or not]
                if (bmpItemIcon != null || bmpSelectedItemIcon != null) {
                    //change the image visibility
                    btnLeft.setVisibility(View.VISIBLE);
                    
                    if (bmpItemIcon != null) {
                        btnLeft.setImageBitmap(bmpItemIcon);
                        Utils.setBackgroundTintForce(itemImageBackgroundColor, btnLeft);
                    } else {
                        btnLeft.setVisibility(View.INVISIBLE);
                    }
                }
                
                if ((index == selectedIndex && (searchText == null || searchText.isEmpty()))
                        || (selectedItemText != null && !selectedItemText.isEmpty() && o.toString().equals(selectedItemText))
                        || (selectedItemText != null && !selectedItemText.isEmpty() && o.toString().contains(selectedItemText) && checkSelectedItemThatContains)) {
                    
                    checkSelectedItemThatContains = false;
                    
                    topLayout.setCardBackgroundColor(selectedItemBackgroundColor);
                    tvItem.setTextColor(selectedItemTextColor);
                    tvItem.setTypeface(tvItem.getTypeface(), Typeface.BOLD);
                    
                    if (bmpSelectedItemIcon != null) {
                        btnLeft.setVisibility(View.VISIBLE);
                        Utils.setBackgroundTintForce(selectedItemImageBackgroundColor, btnLeft);
                        btnLeft.setImageBitmap(bmpSelectedItemIcon);
                    } else if (bmpItemIcon != null) {
                        btnLeft.setVisibility(View.VISIBLE);
                        btnLeft.setImageBitmap(bmpItemIcon);
                    } else {
                        btnLeft.setVisibility(View.GONE);
                    }
                }
              
                
                /*ImageView btnLeft = view.findViewById(R.id.btnLeft);
                ImageView btnRight = view.findViewById(R.id.btnRight);
                
                if (showRightButton) {
                    btnRight.setVisibility(View.VISIBLE);
                    
                    try {
                        if (rightButtonImageResource != 0) {
                            btnRight.setImageResource(rightButtonImageResource);
                        } else {
                            btnRight.setImageResource(R.drawable.ic_delete);
                        }
                    } catch (Exception e) {
                    
                    }
                    
                    if (actionRightButton != null) {
                        btnRight.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                actionRightButton.positiveButtonPressed();
                            }
                        });
                    }
                } else {
                    btnRight.setVisibility(View.GONE);
                }*/
            }
            
            @Override
            public void onItemClicked(@NonNull View view, Object o, int i, @NonNull FrogoRecyclerNotifyListener frogoRecyclerNotifyListener) {
            
            }
            
            @Override
            public void onItemLongClicked(@NonNull View view, Object o, int i, @NonNull FrogoRecyclerNotifyListener frogoRecyclerNotifyListener) {
            
            }
        };
        
        frgList.injector()
                .addData(arItems)
                .addCallback(adptItems)
                .createLayoutLinearVertical(false)
                .addCustomView(R.layout.hx_layout_item_list)
                .addEmptyView(R.layout.hx_no_data_found)
                .build();
        
        if (scrollToSelectedItemWhenDialogOpens) {
            if (selectedItemText != null && !selectedItemText.isEmpty()) {
                for (int i = 0; i < arItems.size(); i++) {
                    //if current item is equal to requested text
                    // OR has contain set as true and current item contains requested text
                    
                    try {
                        String text = "";
                        
                        if (itemTextProvider != null) {
                            text = itemTextProvider.getText(i).toString(); // text
                        } else {
                            text = ""; // text
                        }
                        
                        if (text.equals(selectedItemText)
                                || (checkSelectedItemThatContains && text.contains(selectedItemText))) {
                            // selectedIndex = i;
                            frgList.scrollToPosition(i);
                            break;
                        }
                    } catch (Exception e) {
                    
                    }
                }
            }
            
            if (selectedIndex >= 0) {
                frgList.scrollToPosition(selectedIndex);
            }
            
            // selectedItemText = arItems.get(selectedIndex);
        }
        
        dialog.show();
        
        if (actionDoAfterShown != null) {
            actionDoAfterShown.positiveButtonPressed();
        }
    }
    
    
    private boolean checkDefaultTypes(Object o) {
        
        if (
                o instanceof String
                        || o instanceof Integer
                        || o instanceof Float
                        || o instanceof Double
                        || o instanceof Character
                        || o instanceof Boolean
        ) {
            return true;
        }
        
        return false;
    }
    
    public void closeDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}

