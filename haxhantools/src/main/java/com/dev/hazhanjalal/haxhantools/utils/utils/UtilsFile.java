package com.dev.hazhanjalal.haxhantools.utils.utils;


import android.content.Context;
import android.os.Environment;

import com.dev.hazhanjalal.haxhantools.utils.ui.HxProgress;

import java.io.File;

public class UtilsFile {
    
    public static String localFolderPath = "";
    
    
    public static void deleteFile(String subFolder, String fileName) {
        try {
            File dir = getFileFullPath(subFolder, fileName);
            dir.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void deleteFolder(String subFolder, String folderName) {
        try {
            File dir = getFolderFullPath(subFolder + "/" + folderName);
            if (dir.isDirectory()) {
                final String[] children = dir.list();
                for (int i = 0; i < children.length; i++) {
                    new File(dir, children[i]).delete();
                    
                    final int finalI = i;
                    
                    Utils.getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            HxProgress.setProgressText(finalI + "/" + children.length);
                        }
                    });
                }
                
                dir.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static boolean fileExist(String subfolder, String fileName) {
        return getFileFullPath(subfolder, fileName).exists();
    }
    
    public static boolean fileExistsInRaw(String fileName) {
        try {
            
            if (fileName.contains(".")) {
                fileName = fileName.substring(0, fileName.indexOf("."));
            }
            
            int id = Utils.getResourceIdByName("raw", fileName);
            
            if (id != 0) {
                return true;
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    
        return false;
    }
    
    
    public static boolean folderExists(String subfolder) {
        File folder = getFileFullPath(subfolder, "");
        return folder.exists();
    }
    
    public static File getFolderFullPath(String subfolder) {
        return getFolderFullPath(Utils.activeContext, subfolder);
    }
    
    public static File getFolderFullPath(Context context, String subfolder) {
        File folder = getFileFullPath(context, subfolder, "");
        if (!folder.exists()) {
            folder.mkdirs();
        }
        
        return folder;
    }
    
    public static File getFileFullPath(String subfolder, String fileName) {
        return getFileFullPath(Utils.activeContext, subfolder, fileName);
    }
    
    public static File getFileFullPath(Context context, String subfolder, String fileName) {
        return new File(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).getPath() + "/" + localFolderPath + "/" + Utils.replaceEasternNumbers(subfolder) + "/" + Utils.replaceEasternNumbers(fileName));
        /*if (Build.VERSION.SDK_INT >= 28) {
            //Stores in Android/data/{app_package}
            //  return new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath() + "/" + localFolderPath + "/" + subfolder + "/" + fileName);
        } else {
            //Stores in Direct Storage, example : 0/TafseeriNoor/
            //But this is deprecated, try to use above method always
            return new File(Environment.getExternalStorageDirectory().getPath() + "/" + localFolderPath + "/" + subfolder + "/" + fileName);
        }*/
    }
    
}
