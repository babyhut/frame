package com.frame.project.util;


import com.frame.project.modle.ResponseModle;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;


/**
 * 编解码JSON
 * 
 * @author
 * 
 */
public class JsonUtil {
	
	private Gson getGson(){
		return new GsonBuilder().
                registerTypeAdapter(Map.class,new FixedMapTypeAdapter()).create();
    }
	
	private class FixedMapTypeAdapter implements JsonDeserializer<Map<String,Object>> {
		@Override
		public Map<String,Object> deserialize(JsonElement json, Type typeOfT,
                                              JsonDeserializationContext context) throws JsonParseException {
			if (json.isJsonNull()) {
				return null;
			}
			if ("java.util.Map<java.lang.String, com.fanya.zjd.model.message.Pagination>".equalsIgnoreCase(typeOfT.toString())) {
				return handleObject(json.getAsJsonObject(), context);
			} else {
				return handlePrimitive(json.getAsJsonObject(), context);
			}
		}
		
		

		private Map<String,Object> handleObject(JsonObject json, JsonDeserializationContext context) {
			Map<String, Object> map = new HashMap<String, Object>();
			for (Map.Entry<String, JsonElement> entry : json.entrySet()) {
				if (entry.getValue().isJsonNull()) {
					map.put(entry.getKey(), null);
				} else {
					ResponseModle p = context.deserialize(entry.getValue(), ResponseModle.class);
					map.put(entry.getKey(), p);
				}
			}
			return map;
		}
		
		private Map<String, Object> handlePrimitive(JsonObject json,JsonDeserializationContext context) {
			Map<String, Object> map = new HashMap<String, Object>();
			for (Map.Entry<String, JsonElement> entry : json.entrySet()) {
				if (entry.getValue().isJsonNull()) {
					map.put(entry.getKey(), null);
				} else {
					JsonPrimitive jp = entry.getValue().getAsJsonPrimitive();
					if (jp.isBoolean()) {
						map.put(entry.getKey(), jp.getAsBoolean());
					} else if (jp.isString()) {
						map.put(entry.getKey(), jp.getAsString());
					} 
					else if (jp.isNumber()) {
						BigDecimal bigDec = jp.getAsBigDecimal();
						if (bigDec.longValue() == jp.getAsDouble()) {//long
							map.put(entry.getKey(), jp.getAsLong());
						}  else {
							map.put(entry.getKey(), jp.getAsDouble());
						}
					}
				}
			}
			return map;
		}
		

	}
	
	public String formatBean(Object jsonObject) {
		Gson gson = new Gson();
		return gson.toJson(jsonObject);
	}

	public <T> T parseJson(String jsonStr, Class<T> converClass) {
		Gson gson = getGson();
		return gson.fromJson(jsonStr, converClass);
	}
	
	

	 

}
