package com.test.sate;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;

/**
 * <p>
 * class desc:
 * 使用安卓提供的工具SharedPreferences可以方便的存储参数
 * 可存储数据类型:long 、boolean、int、float、String
 * 用于存储参数
 * 要使用的权限
 * <p/>
 * 定外的知识点:或取数据
 * Map<String ,Object> getAll();	查询所有数据
 * contains(String key);	查看是否包含某个值
 * getXX(String key,XX defValue);	查找某个数据（如果没有对应值，返回默认值）:
 * clear();	清空所有数据
 * remove(String key);	删除数据
 * BaseApply();	提交更新
 * <p/>
 * </p>
 */
public class SpUtil {


    private static final int MODE = App.getAppContext().MODE_PRIVATE;

    /**
     * 用于保存单个数据
     *
     * @param
     * @param fileName 首选参数的文件名  不需要后缀
     * @param
     */
    public static void saveSingleStr(String fileName, String dataName, String dataValue) {
        //或取首选参数的实例
        SharedPreferences spf = App.getAppContext().getSharedPreferences(fileName, MODE);
        //使用editor操作参数
        Editor editor = spf.edit();
        editor.putString(dataName, dataValue);
        editor.commit();
    }

    /**
     * 用于读取单个首选参数
     *
     * @param
     * @param fileName
     * @param dataName
     * @return
     */
    public static String readSingleStr(String fileName, String dataName) {//
        SharedPreferences sp = App.getAppContext().getSharedPreferences(fileName, MODE);
        // 没有name，默认是空字符串
        String username = sp.getString(dataName, "");
        return username;
    }

    /***
     * 保存蓝牙名称
     */
     public static void saveBluetoothName(String fileName, String dataName, String dataValue){
         //或取首选参数的实例
         SharedPreferences spf = App.getAppContext().getSharedPreferences(fileName, MODE);
         //使用editor操作参数
         Editor editor = spf.edit();
         editor.putString(dataName, dataValue);
         editor.commit();
     }


    public static String readBluetoothName(String fileName, String dataName) {//
        SharedPreferences sp = App.getAppContext().getSharedPreferences(fileName, MODE);
        // 没有name，默认是空字符串
        String username = sp.getString(dataName, "");
        return username;
    }

    /***
     *
     * @param fileName 文件夹名
     * @param dataName  保存对象的key
     * @param dataValue 保存整形值
     */
    public static void saveInt(String fileName,String dataName,int dataValue){
        SharedPreferences sp = App.getAppContext().getSharedPreferences(fileName,MODE);
        Editor editor = sp.edit();
        editor.putInt(dataName,dataValue);
        editor.commit();
    }


    public static int readInt(String fileName,String dataName){
        SharedPreferences sp = App.getAppContext().getSharedPreferences(fileName,MODE);
        int data = sp.getInt(dataName,50);
        return data;
    }


    /**
     * 保存对象
     *
     * @param
     * @param obj           要保存的对象，只能保存实现了serializable的对象
     * @param objectNameKey 要保存的对象的key
     * @param fileName      保存到的文件名
     */
    public static void saveObject(Object obj, String objectNameKey, String fileName) {
        try {
            Log.e("", "保存成功");
            Editor sharedata = App.getAppContext().getSharedPreferences(fileName, MODE).edit();
            //先将序列化结果写到byte缓存中，其实就分配一个内存空间
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream os = new ObjectOutputStream(bos);
            //将对象序列化写入byte缓存
            os.writeObject(obj);
            //将序列化的数据转为16进制保存
            String bytesToHexString = bytesToHexString(bos.toByteArray());
            //保存该16进制数组
            sharedata.putString(objectNameKey, bytesToHexString);
            sharedata.commit();

        } catch (IOException e) {
            e.printStackTrace();
            Log.e("", "保存obj失败");
        }
    }

    /**
     * 将数组转为16进制
     *
     * @param bArray
     * @return
     */
    private static String bytesToHexString(byte[] bArray) {
        if (bArray == null) {
            return null;
        }
        if (bArray.length == 0) {
            return "";
        }
        StringBuffer sb = new StringBuffer(bArray.length);
        String sTemp;
        for (int i = 0; i < bArray.length; i++) {
            sTemp = Integer.toHexString(0xFF & bArray[i]);
            if (sTemp.length() < 2)
                sb.append(0);
            sb.append(sTemp.toUpperCase());
        }
        return sb.toString();
    }


    /**
     * 获取保存的Object对象
     *
     * @param
     * @param objectNameKey
     * @param fileName
     * @return
     */
    public static Object readObject(String objectNameKey, String fileName) {
        try {
            SharedPreferences sharedata = App.getAppContext().getSharedPreferences(fileName, MODE);
            if (sharedata.contains(objectNameKey)) {
                String string = sharedata.getString(objectNameKey, "");
                if (TextUtils.isEmpty(string)) {
                    return null;
                } else {
                    //将16进制的数据转为数组，准备反序列化
                    byte[] stringToBytes = stringToBytes(string);
                    ByteArrayInputStream bis = new ByteArrayInputStream(stringToBytes);
                    ObjectInputStream is = new ObjectInputStream(bis);
                    //返回反序列化得到的对象
                    Object readObject = is.readObject();
                    return readObject;
                }
            }
        } catch (StreamCorruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //所有异常返回null
        return null;

    }

    /**
     * 将16进制的数据转为数组
     *
     * @param data
     * @return
     */
    private static byte[] stringToBytes(String data) {
        String hexString = data.toUpperCase().trim();
        if (hexString.length() % 2 != 0) {
            return null;
        }
        byte[] retData = new byte[hexString.length() / 2];
        for (int i = 0; i < hexString.length(); i++) {
            int int_ch;  // 两位16进制数转化后的10进制数
            char hex_char1 = hexString.charAt(i); ////两位16进制数中的第一位(高位*16)
            int int_ch3;
            if (hex_char1 >= '0' && hex_char1 <= '9')
                int_ch3 = (hex_char1 - 48) * 16;   //// 0 的Ascll - 48
            else if (hex_char1 >= 'A' && hex_char1 <= 'F')
                int_ch3 = (hex_char1 - 55) * 16; //// A 的Ascll - 65
            else
                return null;
            i++;
            char hex_char2 = hexString.charAt(i); ///两位16进制数中的第二位(低位)
            int int_ch4;
            if (hex_char2 >= '0' && hex_char2 <= '9')
                int_ch4 = (hex_char2 - 48); //// 0 的Ascll - 48
            else if (hex_char2 >= 'A' && hex_char2 <= 'F')
                int_ch4 = hex_char2 - 55; //// A 的Ascll - 65
            else
                return null;
            int_ch = int_ch3 + int_ch4;
            retData[i / 2] = (byte) int_ch;//将转化后的数放入Byte里
        }
        return retData;
    }


    /**
     * 用于保存多个基本数据类型数据
     *
     * @param
     * @param fileName  首选参数的文件名  不需要后缀
     *                  MODE_PRIVATE
     *                  MODE_WORLD_READABLE
     *                  MODE_WORLD_WRITEABLE
     *                  MODE_MULTI_PROCESS
     * @param fromKeys  键       	String fromKeys[] = {"name","gender","age"};
     * @param toValues  值	String toValues[] = {name,gender,age};
     * @param dataTypes 类型   String dataTypes[] = {"String","String","int"};
     * @return 返回1代表成功 、否则失败
     */
    public static int saveMultiData(String fileName, String[] fromKeys, String[] toValues, String[] dataTypes) {
        //或取首选参数的实例
        SharedPreferences spf = App.getAppContext().getSharedPreferences(fileName, MODE);
        //使用editor操作参数
        Editor editor = spf.edit();
        for (int i = 0; i < dataTypes.length; i++) {
            if ("String".equals(dataTypes[i])) {
                editor.putString(fromKeys[i], toValues[i]);
            } else if ("int".equals(dataTypes[i])) {
                editor.putInt(fromKeys[i], Integer.parseInt(toValues[i]));
            } else if ("float".equals(dataTypes[i])) {
                editor.putFloat(fromKeys[i], Float.parseFloat(toValues[i]));
            } else if ("long".equals(dataTypes[i])) {
                editor.putLong(fromKeys[i], Long.parseLong(toValues[i]));
            } else if ("boolean".equals(dataTypes[i])) {
                editor.putBoolean(fromKeys[i], Boolean.parseBoolean(toValues[i]));
            }

        }
        editor.commit();
        return 1;
    }

    public static void saveBoolean(String fileName, String dataName, boolean dataValue){
        SharedPreferences sp= App.getAppContext().getSharedPreferences(fileName,MODE);
        Editor editor=sp.edit();
        editor.putBoolean(dataName,dataValue);
        editor.commit();
    }

    public static boolean readBoolen(String fileName, String dataName){
        SharedPreferences sp= App.getAppContext().getSharedPreferences(fileName,MODE);
        boolean result=sp.getBoolean(dataName,false);
        return result;
    }





}
