package com.dev.hazhanjalal.haxhantools.utils.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
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
import com.dev.hazhanjalal.haxhantools.utils.utils.Utils;
import com.frogobox.recycler.core.FrogoRecyclerNotifyListener;
import com.frogobox.recycler.core.IFrogoViewAdapter;
import com.frogobox.recycler.widget.FrogoRecyclerView;

import java.util.ArrayList;

public class HxListOfItems {
    Context context;
    ArrayList<String> arItems = new ArrayList<>();
    ArrayList<String> arItemsAllItems = new ArrayList<>();
    Dialog dialog;
    IFrogoViewAdapter adptItems;
    String searchText;
    int colorFoundTextInSearch;
    View btnClearSearchText;
    
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
    Parcelable stateScroll;
    int selectedIndex = -1;
    String selectedItemText = null;
    boolean checkSelectedItemThatContains = false;
    
    public HxListOfItems(Context ctx) {
        context = ctx;
        
        dialog = new Dialog(context, R.style.alert);
        dialog.setContentView(R.layout.hx_show_item_list);
        
        View btnClose = dialog.findViewById(R.id.btnClose);
        frgList = dialog.findViewById(R.id.frgItemList);
        
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
        
        onItemClickListener(null);
        enableSearch(true);
        
        colorFoundTextInSearch = Utils.getColor(context, R.color.colorRedChosen);
        itemBackgroundColor = Utils.getColor(context, R.color.colorBlackTransparent);
        selectedItemBackgroundColor = Utils.getColor(context, R.color.colorBlueChosen);
        
        selectedItemTextColor = Utils.getColor(context, R.color.white);
        itemTextColor = Utils.getColor(context, R.color.white);
        
        scrollToSelectedItemWhenDialogOpens = true;
        checkSelectedItemThatContains = false;
        
        selectedItemText = null;
        selectedIndex = -1;
        
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }
    
    public HxListOfItems onItemClickListener(CustomAction action) {
        adptItems = new IFrogoViewAdapter() {
            @Override
            public void setupInitComponent(@NonNull View view, Object o, int index, @NonNull FrogoRecyclerNotifyListener frogoRecyclerNotifyListener) {
                CardView topLayout = view.findViewById(R.id.topLayout);
                
                topLayout.setCardBackgroundColor(itemBackgroundColor);
                
                topLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (action != null) {
                            action.object = index; // index
                            action.inputText = o.toString(); // text
                            action.positiveButtonPressed();
                        }
                        
                        dialog.dismiss();
                    }
                });
                
                TextView tvItem = view.findViewById(R.id.tvItem);
                tvItem.setText(o.toString());
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
                
                if ((index == selectedIndex && (searchText == null || searchText.isEmpty()))
                        || (selectedItemText != null && o.toString().equals(selectedItemText))
                        || (selectedItemText != null && o.toString().contains(selectedItemText) && checkSelectedItemThatContains)) {
                    
                    checkSelectedItemThatContains = false;
                    
                    topLayout.setCardBackgroundColor(selectedItemBackgroundColor);
                    tvItem.setTextColor(selectedItemTextColor);
                    tvItem.setTypeface(tvItem.getTypeface(), Typeface.BOLD);
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
        
        return this;
    }
    
    public HxListOfItems setItems(ArrayList<String> items) {
        arItems.addAll(items);
        arItemsAllItems.addAll(arItems);
        return this;
    }
    
    public HxListOfItems setItems(String[] items) {
        for (int i = 0; i < items.length; i++) {
            arItems.add(items[i]);
        }
        
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
    
    public HxListOfItems enableSearch(boolean enableSearch) {
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
                            if (!arItems.get(j).contains(searchText)) {
                                arItems.remove(j);
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
        //        FrogoRecyclerView frgQariList = dialog.findViewById(R.id.frgQariList);
        //        ArrayList<ClassQariAudio> arQari = MyDatabase.getAllQari();
        //        EditText etBookSearch = dialog.findViewById(R.id.etBookSearch);
        //
        //        TextView tvAllAudioSize = dialog.findViewById(R.id.tvAllAudioSize);
        //        TextView tvTopInfoSelectAudio = dialog.findViewById(R.id.tvTopInfoSelectAudio);
        //        tvTopInfoSelectAudio.append("ی قورئان خوێن");
        //
        //        IFrogoViewAdapter adptQari = new IFrogoViewAdapter<ClassQariAudio>() {
        //            @Override
        //            public void setupInitComponent(@NonNull View view, ClassQariAudio o, int i, @NonNull FrogoRecyclerNotifyListener frogoRecyclerNotifyListener) {
        //                TextView tvQari = view.findViewById(R.id.tvQari);
        //                ImageView btnDelete = view.findViewById(R.id.btnDelete);
        //                ImageView btnDownload = view.findViewById(R.id.btnDownload);
        //                CardView topLayout = view.findViewById(R.id.topLayout);
        //                String nArabic = o.nameArabic;
        //
        //                if (nArabic.contains("بێ بچڕان")) {
        //                    nArabic = nArabic.replaceAll("بێ بچڕان", "<font color=\"#f1c40f\">بێ بچڕان</font>");
        //                    /*if (!o.nameArabic.equals(MyDatabase.getSelectedQari())) {
        //                        nArabic = nArabic.replaceAll("بێ بچڕان", "<font color=\"#e74c3c\">بێ بچڕان</font>");
        //                    } else {
        //                    }*/
        //                } else {
        //                    nArabic += "<font color=\"#1abc9c\"> (ئایەت ئایەت) </font>";
        //                }
        //
        //                if (MyDatabase.getSelectedQari().equals(o.nameArabic)) {
        //                    topLayout.setCardBackgroundColor(Utils.getColor(R.color.colorBlueChosen));
        //                    tvQari.setTextColor(Utils.getColor(android.R.color.white));
        //                } else {
        //                    topLayout.setCardBackgroundColor(Utils.getColor(R.color.modeBackgroundColorLighter));
        //                    tvQari.setTextColor(Utils.getColor(R.color.modeTextColor));
        //                }
        //
        //                if (!etBookSearch.getText().toString().isEmpty()) {
        //                    nArabic = nArabic.replaceAll(etBookSearch.getText().toString(), "<font color=\"#2ecc71\">" + etBookSearch.getText().toString() + "</font>");
        //                }
        //
        //                tvQari.setText(Html.fromHtml(nArabic));
        //
        //                btnDownload.setVisibility(View.GONE);
        //
        //                ViewGroup mainLayout = view.findViewById(R.id.mainLayout);
        //                View.OnClickListener onClickDownload = new View.OnClickListener() {
        //                    @Override
        //                    public void onClick(View v) {
        //                        try {
        //                            if (!o.nameArabic.equals(MyDatabase.getSelectedQari())) {
        //
        //                                //audio_player.pause();
        //                                Utils.getActivity().runOnUiThread(new Runnable() {
        //                                    @Override
        //                                    public void run() {
        //                                        // if (audio_player.isPlaying()) {
        //                                        audio_player.pause();
        //                                        audio_player.kill();
        //                                        //  }
        //                                    }
        //                                });
        //
        //                                Utils.put("selected_qari", o.nameArabic);
        //                                tvSelectedQari.setText(MyDatabase.getSelectedQari());
        //
        //                                new Thread(new Runnable() {
        //                                    @Override
        //                                    public void run() {
        //                                        refreshAudio();
        //                                        checkAudioAvailable();
        //                                    }
        //                                }).start();
        //                            }
        //
        //                            dialog.dismiss();
        //
        //                        } catch (Exception e) {
        //                            e(e);
        //                        }
        //                    }
        //                };
        //
        //                mainLayout.setOnClickListener(onClickDownload);
        //
        //                /*
        //                btnDelete.setBackground(null);
        //                btnDelete.setBackgroundColor();
        //                */
        //
        //                Utils.setBackgroundTintForce(Utils.getColor(R.color.colorGreenChosen), btnDelete);
        //                btnDelete.setImageResource(R.drawable.ic_check);
        //                // btnDelete.setImageDrawable(Utils.setDrawableTint(Utils.getDrawable(R.drawable.ic_check), Utils.getColor(R.color.modeTextColor)));
        //
        //                if (!AudioPlayer.isAudioAvailableForSurahOfQari(o.nameArabic, selectedSura)) {
        //                    btnDelete.setVisibility(View.VISIBLE);
        //                    btnDelete.setImageResource(R.drawable.ic_no_audio);
        //                    Utils.setBackgroundTintForce(Utils.getColor(R.color.colorRedChosen), btnDelete);
        //                } else {
        //                    //checking if file for this surah exists or not.
        //                    if (MyDatabase.isGapless(o.nameArabic)) {
        //                        if (UtilsFile.fileExist("mp3/" + o.nameEnglish, Utils.replaceEasternNumbers(String.format("%03d.mp3", selectedSura)))) {
        //                            btnDelete.setVisibility(View.VISIBLE);
        //                        } else {
        //                            btnDelete.setVisibility(View.INVISIBLE);
        //                        }
        //                    } else {
        //                        //sample three ayahs, first middle last, if they all exists, consider surah downloaded.
        //                        if (UtilsFile.fileExist("mp3/" + o.nameEnglish, Utils.replaceEasternNumbers(String.format("%03d%03d.mp3", selectedSura, 1)))
        //                                && UtilsFile.fileExist("mp3/" + o.nameEnglish, Utils.replaceEasternNumbers(String.format("%03d%03d.mp3", selectedSura, totalAyah / 2)))
        //                                && UtilsFile.fileExist("mp3/" + o.nameEnglish, Utils.replaceEasternNumbers(String.format("%03d%03d.mp3", selectedSura, totalAyah)))) {
        //                            btnDelete.setVisibility(View.VISIBLE);
        //                        } else {
        //                            btnDelete.setVisibility(View.INVISIBLE);
        //                        }
        //                    }
        //                }
        //
        //
        //                /*if (UtilsFile.folderExists("mp3/" + o.nameEnglish)) {
        //                    btnDelete.setVisibility(View.VISIBLE);
        //                } else {
        //                    btnDelete.setVisibility(View.INVISIBLE);
        //                }*/
        //
        //                btnDelete.setOnClickListener(new View.OnClickListener() {
        //                    @Override
        //                    public void onClick(View v) {
        //                        if (!AudioPlayer.isAudioAvailableForSurahOfQari(o.nameArabic, selectedSura)) {
        //                            ShowToast.showToast(String.format(" ئەم سورەتە بە دەنگی %s بەردەست نییە ", o.nameArabic.replaceAll("\\(" + "بێ بچڕان" + "\\)" + "|" + "\\(" + " بێ بچڕان " + "\\)", "").trim()),
        //                                                Utils.getDrawable(R.drawable.ic_no_audio),
        //                                                Utils.getColor(R.color.colorRedChosen));
        //                        } else {
        //                            ShowToast.showToastSuccess("ئەم سورەتە بە دەنگی " + o.nameArabic.replaceAll("\\(" + "بێ بچڕان" + "\\)" + "|" + "\\(" + " بێ بچڕان " + "\\)", "").trim() + " داگیراوە ");
        //                        }
        //
        //                    }
        //                });
        //            }
        //
        //            @Override
        //            public void onItemClicked(@NonNull View view, ClassQariAudio o, int i, @NonNull FrogoRecyclerNotifyListener frogoRecyclerNotifyListener) {
        //
        //            }
        //
        //            @Override
        //            public void onItemLongClicked(@NonNull View view, ClassQariAudio o, int i, @NonNull FrogoRecyclerNotifyListener frogoRecyclerNotifyListener) {
        //
        //            }
        //        };
        //
        //        int selectedQari = 0;
        //        for (int i = 0; i < arQari.size(); i++) {
        //            if (MyDatabase.getSelectedQari().equals(arQari.get(i).nameArabic)) {
        //                selectedQari = i;
        //                break;
        //            }
        //        }
        //
        //        tvAllAudioSize.setText("كۆی دەنگەكان : " + arQari.size());
        //
        //        frgQariList.injector()
        //                .addData(arQari)
        //                .createLayoutLinearVertical(false)
        //                .addCustomView(R.layout.layout_qari_download)
        //                .addCallback(adptQari)
        //                .addEmptyView(R.layout.layout_empty)
        //                .build();
        //
        //        frgQariList.scrollToPosition(selectedQari);
        //
        //        etBookSearch.addTextChangedListener(new TextWatcher() {
        //            @Override
        //            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        //
        //            }
        //
        //            @Override
        //            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        //                ArrayList<ClassQariAudio> arQari = MyDatabase.getAllQari();
        //
        //                int currentSelectedQari = 0;
        //                //v("text=" + charSequence.toString());
        //                if (!etBookSearch.getText().toString().isEmpty()) {
        //                    for (int x = arQari.size() - 1; x >= 0; x--) {
        //                        if (!arQari.get(x).nameArabic.contains(charSequence.toString())) {
        //                            arQari.remove(x);
        //                            continue;
        //                        }
        //
        //                        if (MyDatabase.getSelectedQari().equals(arQari.get(x).nameArabic)) {
        //                            currentSelectedQari = x;
        //                        }
        //                    }
        //                }
        //
        //                frgQariList.injector()
        //                        .addData(arQari)
        //                        .createLayoutLinearVertical(false)
        //                        .addCustomView(R.layout.layout_qari_download)
        //                        .addCallback(adptQari)
        //                        .addEmptyView(R.layout.layout_empty)
        //                        .build();
        //
        //                frgQariList.scrollToPosition(currentSelectedQari);
        //
        //            }
        //
        //            @Override
        //            public void afterTextChanged(Editable editable) {
        //
        //            }
        //        });
        
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
                    if (arItems.get(i).equals(selectedItemText)
                            || (checkSelectedItemThatContains && arItems.get(i).contains(selectedItemText))) {
                        // selectedIndex = i;
                        frgList.scrollToPosition(i);
                        break;
                    }
                    
                }
            }
            
            if (selectedIndex >= 0) {
                frgList.scrollToPosition(selectedIndex);
            }
            
            // selectedItemText = arItems.get(selectedIndex);
        }
        
        dialog.show();
    }
    
}
