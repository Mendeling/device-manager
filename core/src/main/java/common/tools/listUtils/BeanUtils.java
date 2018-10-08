package common.tools.listUtils;

import java.util.List;
import java.util.Optional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class BeanUtils<T> {

	public static <T> T Bean2Bean(Object obj,Class<T> clazz) {  
        if(!Optional.ofNullable(obj).isPresent()){
        	return null;
        }
        JSONObject json = (JSONObject) JSONObject.toJSON(obj); 
        T result = json.toJavaObject(clazz);
        return result; 
    } 
	
	public static  <T, K> List<T> list2List(List<K> obj,Class<T> clazz) {  
        if(!Optional.ofNullable(obj).isPresent()){
        	return null;
        }
        String json = JSONArray.toJSONString(obj);
        List<T> result = JSONArray.parseArray(json, clazz);
        return result; 
    }
	
}
