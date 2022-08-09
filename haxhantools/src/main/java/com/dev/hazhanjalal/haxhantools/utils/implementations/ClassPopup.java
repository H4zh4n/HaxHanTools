package com.dev.hazhanjalal.haxhantools.utils.implementations;

import com.dev.hazhanjalal.haxhantools.R;
import com.dev.hazhanjalal.haxhantools.utils.utils.Utils;

public class ClassPopup {
    
    public long popupTime = 0;
    public String popupText = "";
    public String popupImageURL = "";
    public String popupVisitURL = "";
    public String popupDuration = "";
    public boolean popupTextIsAyah = false;
    public boolean popupCanTapImage = false;
    public int colorBackground = Utils.getColor(R.color.colorDialog);
    public int colorText = Utils.getColor(R.color.white);
}
