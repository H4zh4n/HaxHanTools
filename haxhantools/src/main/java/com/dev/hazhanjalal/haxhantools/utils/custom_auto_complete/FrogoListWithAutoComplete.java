package com.dev.hazhanjalal.haxhantools.utils.custom_auto_complete;


import static com.dev.hazhanjalal.haxhantools.utils.print.Logger.e;

import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import com.dev.hazhanjalal.haxhantools.R;
import com.dev.hazhanjalal.haxhantools.utils.implementations.CustomAction;
import com.dev.hazhanjalal.haxhantools.utils.ui.HxToast;
import com.dev.hazhanjalal.haxhantools.utils.utils.Utils;
import com.frogobox.recycler.core.FrogoRecyclerNotifyListener;
import com.frogobox.recycler.core.IFrogoViewAdapter;

import java.util.List;

public class FrogoListWithAutoComplete {
    
    //static AutoCompleteTextView lastItem;
    
    public static String formatToSingleLine(List<String> ar) {
        String result = "";
        
        for (int i = 0; i < ar.size(); i++) {
            if (ar.get(i).trim().isEmpty()) {
                continue;
            }
            
            result += ar.get(i).trim();
            result += " | ";
            
        }
        if (result.endsWith(" | ")) {
            result = result.substring(0, result.lastIndexOf(" | "));
        }
        return result;
    }
    
    public static void formatInitFrogoArray(List<String> arList, String bk) {
        String auth[] = bk.split("\\|");
        
        for (int i = 0; i < auth.length; i++) {
            
            /*if (arList.contains(auth[i].trim())) {
                continue;
            }*/
            
            arList.add(auth[i].trim());
        }
        
        //arList.add("");
    }
    
    public static void requetLastItemFocus(ClassFrogoData dt) {
        requestItemFocus(dt, dt.dataIncluded.size() - 1);
    }
    
    public static void requestItemFocus(ClassFrogoData dt, int index) {
        
        try {
            int atIndex = index;
            dt.frg.scrollToPosition(atIndex);
            
            Utils.whenViewIsVisible(dt.frg,
                                    new CustomAction() {
                                        @Override
                                        public void positiveButtonPressed() {
                                            View viewItem = dt.frg.getLayoutManager().findViewByPosition(atIndex);
                                            AutoCompleteTextView cd = viewItem.findViewById(R.id.txtData);
                    
                                            Utils.showKeyboard(cd);
                                        }
                                    });
        } catch (Exception e) {
        
        }
    }
    
    public static void setListForAutoComplete(AutoCompleteTextView et, List arValues) {
        AutoSuggestAdapter adapter = new AutoSuggestAdapter(Utils.activeContext,
                                                            R.layout.custom_simple_list,
                                                            arValues);
        et.setAdapter(adapter);
    }
    
    public IFrogoViewAdapter initFrogo(ClassFrogoData dt) {
        IFrogoViewAdapter adpt = new IFrogoViewAdapter<String>() {
            @Override
            public void onItemClicked(@NonNull View view, String s, int i, @NonNull FrogoRecyclerNotifyListener<String> frogoRecyclerNotifyListener) {
            
            }
            
            @Override
            public void onItemLongClicked(@NonNull View view, String s, int i, @NonNull FrogoRecyclerNotifyListener<String> frogoRecyclerNotifyListener) {
            
            }
            
            @Override
            public void setupInitComponent(@NonNull View view, String data, int index, @NonNull FrogoRecyclerNotifyListener<String> frogoRecyclerNotifyListener) {
                AutoCompleteTextView txtData = view.findViewById(R.id.txtData);
                ImageView imgClose = view.findViewById(R.id.imgClose);
                CardView mainLayout = view.findViewById(R.id.mainLayout);
                
                ViewGroup.LayoutParams mainParams = mainLayout.getLayoutParams();
                ViewGroup.LayoutParams dataParams = txtData.getLayoutParams();
                
                mainLayout.setCardBackgroundColor(dt.backgroundColor);
                
                if (dt.isLinear) {
                    mainParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
                    dataParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
                } else {
                    mainParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                    dataParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                }
                
                txtData.setText(data);
                
                if (txtData.getText().toString().trim().isEmpty()) {
                    txtData.setFocusable(true);
                    imgClose.setVisibility(View.GONE);
                    //  txtData.requestFocus();
                    
                    //lastItem = txtData;
                    
                    setListForAutoComplete(txtData,
                                           dt.autoCompleteList);
                    
                    /*ArrayAdapter<String> adapter = new ArrayAdapter<String>(Utils.activeContext,
                                                                            R.layout.custom_simple_list,
                                                                            dt.autoCompleteList);
                    txtData.setAdapter(adapter);*/
                } else {
                    txtData.setFocusable(false);
                    imgClose.setVisibility(View.VISIBLE);
                    txtData.setAdapter(null);
                }
                
                /** focus on last item.
                 mainLayout.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View view) {
                dt.frg.scrollToPosition(dt.dataIncluded.size() - 1);
                
                if (txtData.getText().toString().trim().isEmpty()) {
                //  txtData.requestFocus();
                }
                }
                });*/
                
                /** focus on edittext
                 * */
                txtData.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        requetLastItemFocus(dt);
                    }
                });
                
                txtData.setOnKeyListener(new View.OnKeyListener() {
                    public boolean onKey(View v, int keyCode, KeyEvent event) {
                        
                        try {
                            // If the event is a key-down event on the "enter" button
                            if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                                    (keyCode == KeyEvent.KEYCODE_ENTER)) {
                                
                                String text = txtData.getText().toString().trim();
                                
                                if (text.isEmpty()) {
                                    txtData.setText("");
                                    return false;
                                }
                                
                                // Perform action on key press
                                dt.dataIncluded.add(text);
                                
                                //Data was previously added with below line, don't know why and I don't think it's necessary.
                                //dt.dataIncluded.set(dt.dataIncluded.size() - 1, text);
                                
                                //  ShowToast.showToastSuccess("[" + text + "] زیادكرا ");
                                
                                setData(dt, true);
                                return true;
                            }
                        } catch (Exception e) {
                            e(e);
                        }
                        return false;
                    }
                });
                
                //insert data to array when this field looses focus and at least 1 letter is inserted
                txtData.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        try {
                            if (!hasFocus) {
                                String text = txtData.getText().toString().trim();
                                
                                if (text.isEmpty()) {
                                    txtData.setText("");
                                    return;
                                }
                                
                                // Perform action on key press
                                if (!dt.dataIncluded.contains(text)) {
                                    dt.dataIncluded.add(text);
                                    setData(dt, false);
                                }
                                
                                //Data was previously added with below line, don't know why and I don't think it's necessary.
                                //dt.dataIncluded.set(dt.dataIncluded.size() - 1, text);
                                
                                //  ShowToast.showToastSuccess("[" + text + "] زیادكرا ");
                                
                            }
                        } catch (Exception e) {
                            e(e);
                        }
                    }
                });
                
                imgClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        HxToast.showToastWarning("[" + dt.dataIncluded.get(index) + "] لادرا ");
                        dt.dataIncluded.remove(index);
                        setData(dt);
                        
                        Utils.getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Utils.whenViewIsVisible(dt.frg, new CustomAction() {
                                    @Override
                                    public void positiveButtonPressed() {
                                        try {
                                            dt.frg.scrollToPosition(index - 1);
                                        } catch (Exception e) {
                                        
                                        }
                                    }
                                });
                            }
                        });
                    }
                });
            }
        };
        
        return adpt;
    }
    
    public void setData(ClassFrogoData dt) {
        setData(dt, false);
    }
    
    public void setData(ClassFrogoData dt, boolean isLastItemFocusRequested) {
        /*if (dt.state == null) {
            dt.frg.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    dt.state = recyclerView.getLayoutManager().onSaveInstanceState();
                }
            });
        }*/
    
        /*
        if (!dt.dataIncluded.get(dt.dataIncluded.size() - 1).trim().isEmpty()) //if last item is not empty, add an empty item, which allows writing new tag.
          */
        
        //if list is empty add an empty item, which allows writing new tag.
        if (dt.dataIncluded.size() == 0) {
            dt.dataIncluded.add("");
        } else {
            //clean all empty cells.
            for (int i = dt.dataIncluded.size() - 1; i >= 0; i--) {
                if (dt.dataIncluded.get(i).trim().isEmpty()) {
                    dt.dataIncluded.remove(i);
                }
            }
            //add single empty at the end.
            dt.dataIncluded.add("");
        }
        
        dt.frg.setItemViewCacheSize(100);
        
        if (dt.isLinear) {
            dt.frg.injector()
                    .addCustomView(R.layout.hx_layout_auto_complete)
                    .createLayoutLinearVertical(false)
                    .addCallback(initFrogo(dt))
                    .addEmptyView(null)
                    .addData(dt.dataIncluded)
                    .build();
        } else {
            dt.frg.injector()
                    .addCustomView(R.layout.hx_layout_auto_complete)
                    .createLayoutLinearHorizontal(false)
                    .addCallback(initFrogo(dt))
                    .addEmptyView(null)
                    .addData(dt.dataIncluded)
                    .build();
        }
        
       /* if (dt.state != null) {
            dt.frg.getLayoutManager().onRestoreInstanceState(dt.state);
        }*/
        try {
            if (isLastItemFocusRequested) {
                requetLastItemFocus(dt);
            } else {
                dt.frg.scrollToPosition(dt.dataIncluded.size() - 1);
            }
        } catch (Exception e) {
            e(e);
        }
    }
    
}


