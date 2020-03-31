package liao.utils;

import liao.parse.table.model.Column;
import liao.parse.table.model.Table;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by ao on 2017/10/25.
 */
public class CommonUtils {
    public static <T extends EnumValue> T getEnumByValue(Integer value, T[] values){
        if(value == null){
            return null;
        }
        for(T enumValue : values){
            if(enumValue.getValue() == value){
                return enumValue;
            }
        }
        return null;
    }

    public static  Map<Integer,String> convertEnumToMap(EnumValue[] values,boolean hasAll){
        LinkedHashMap<Integer,String> map = new LinkedHashMap<>();

        for(EnumValue enumValue : values){
            map.put(enumValue.getValue(),enumValue.getDesc());
        }
        return map;
    }

    public static  Map<Integer,String> convertEnumToMap(EnumValue[] values){
        LinkedHashMap<Integer,String> map = new LinkedHashMap<>();
        for(EnumValue enumValue : values){
            map.put(enumValue.getValue(),enumValue.getDesc());
        }
        return map;
    }

    public static String createNewJavaObject(String javaType, int index) {
        if (javaType.equals("Long")) {
            return ""+index+"l * num";
        } else if (javaType.equals("Integer")) {
            return index+" * num";
        } else if (javaType.equals("String")) {
            return "\"x" + index+"\" + num";
        } else if (javaType.equals("Date")) {
            return "new Date()";
        } else if (javaType.equals("BigDecimal")) {
            return "new BigDecimal(\""+index + "\")";
        }
        throw new IllegalArgumentException("不支持的java类型：" + javaType);
    }

    public static<K,V  extends IListToMap<K>> Map<K,V> convertListToMap(List<V> valueList, K k){
        if(valueList == null){
            return null;
        }
        Map<K, V > resultMap = new HashMap<K, V>();
        for(V value : valueList){
            K key = value.getKey();
            resultMap.put(key,value);
        }
        return resultMap;
    }

    public static<K,V  extends IListToMap<K>> Map<K,List<V>> convertListToMapList(List<V> valueList){
        if(valueList == null){
            return null;
        }
        Map<K, List<V>> resultMap = new HashMap<K, List<V>>();
        for(V value : valueList){
            K key = value.getKey();
            List<V> vList = resultMap.get(key);
            if(vList == null){
                vList = new ArrayList<V>();
                resultMap.put(key,vList);
            }
            vList.add(value);

        }
        return resultMap;
    }

    public static<K,V extends IListToMap<K>> String contactIListToMapWithToken(Collection<V> list,String token){
        if(list == null){
            return null;
        }
        List<K> keyList = new ArrayList<K>(list.size());
        for(IListToMap<K> listToMap : list){
            keyList.add(listToMap.getKey());
        }
        return contactCollectionWithToken(keyList,token);
    }

    public static boolean isEqual(Object obj1,Object obj2){
        if(obj1 == obj2){
            return true;
        }
        if(obj1 == null || obj2 == null){
            return false;
        }
        return obj1.equals(obj2);
    }
    public static String contactCollectionWithToken(Collection<?> list, String token) {
        if (list == null) {
            return null;
        }
        StringBuilder strB = new StringBuilder();
        for (Object item : list) {
            strB.append(item.toString());
            strB.append(token);
        }
        String result = strB.toString();
        if (result.length() > 0) {
            result = result.substring(0, result.length() - token.length());
        }
        return result;
    }

    public static boolean isEmpty(String string){
        return string == null || string.trim().length() == 0;
    }
}
