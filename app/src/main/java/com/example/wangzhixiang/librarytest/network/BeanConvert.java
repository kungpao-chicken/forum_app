package com.example.wangzhixiang.librarytest.network;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lzy.okgo.convert.Converter;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import okhttp3.Response;
import okhttp3.ResponseBody;

public class BeanConvert<T> implements Converter<T> {
    private Class<T> mType;

    public BeanConvert(Class<T> mType) {
        this.mType = mType;
    }

    @Override
    public T convertResponse(Response response) throws Throwable {
        ResponseBody body = response.body();

        if (body == null) return null;
        JSONObject jsonResponse = JSON.parseObject(body.string());
        return JSON.parseObject(jsonResponse.get("data").toString(), mType);
    }
//    public class JsonResponse{
//        public int code;
//        public String msg;
//        public CustomMap data;
//    }
//    public class CustomMap implements Map{
//        public final Map<String, Object> map;
//        public CustomMap(){
//            this(16, false);
//        }
//        public CustomMap(Map<String, Object> map) {
//            this.map = map;
//        }
//        public CustomMap(int initialCapacity, boolean ordered){
//            if (ordered) {
//                map = new LinkedHashMap<String, Object>(initialCapacity);
//            } else {
//                map = new HashMap<String, Object>(initialCapacity);
//            }
//        }
//
//        @Override
//        public int size() {
//            return map.size();
//        }
//
//        @Override
//        public boolean isEmpty() {
//            return map.isEmpty();
//        }
//
//        @Override
//        public boolean containsKey(Object key) {
//            return map.containsKey(key);
//        }
//
//        @Override
//        public boolean containsValue(Object value) {
//            return map.containsValue(value);
//        }
//
//        @Override
//        public Object get( Object key) {
//            return map.get(key);
//        }
//
//        @Override
//        public Object put( Object key, Object value) {
//            return map.put(String.valueOf(key), value);
//        }
//
//        @Override
//        public Object remove(Object key) {
//            return map.remove(key);
//        }
//
//        @Override
//        public void putAll(Map m) {
//            map.putAll(m);
//
//        }
//
//        @Override
//        public void clear() {
//            map.clear();
//        }
//
//        @Override
//        public Set keySet() {
//            return map.keySet();
//        }
//
//        @Override
//        public Collection values() {
//            return map.values();
//        }
//
//        @Override
//        public Set<Map.Entry<String, Object>> entrySet() {
//            return map.entrySet();
//        }
//        //需要测试
//        @Override
//        public String toString() {
//            StringBuilder sb = new StringBuilder("{");
//            for (String key:map.keySet()){
//                sb.append("\"" + key +"\""+ ":");
//                sb.append(map.get(key));
//            }
//            sb.append("}");
//            return sb.toString();
//        }
//    }
}
