package org.herojohn.adrobot.device.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Map;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

/**
 * Copyright @ 2012 sohu.com Inc.
 * All right reserved.
 * <p>
 * 
 * </p>
 * @author liuchong
 * @since 2012-12-5
 */
public class JsonUtils {
	private static final ObjectMapper mapper;
	
	static {
		mapper = new ObjectMapper();
		mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
	}
	
	public static <T> T toBean(String json, Class<T> clazz) throws Exception{
		try {
			return mapper.readValue(json, clazz);
		} catch (JsonParseException e) {
			throw new Exception(e.getMessage());
		} catch (JsonMappingException e) {
			throw new Exception(e.getMessage());
		} catch (IOException e) {
			throw new Exception(e.getMessage());
		}
	}
	
	public static <T> T toBean(String json, TypeReference<T> valueTypeRef) throws IOException {
		try {
			return mapper.readValue(json, valueTypeRef);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static <T> T toBean(File src, TypeReference<T> valueTypeRef) throws IOException {
		try {
			return mapper.readValue(src, valueTypeRef);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String formBean(Object obj) {
		try {
			return mapper.writeValueAsString(obj);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void writeBean(File src,Object obj) throws IOException {
		try {
			mapper.writeValue(src, obj);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} 
	}
	
	@SuppressWarnings("unchecked")
	public static Map<String, Object> toMap(String json) throws Exception {
		return toBean(json, Map.class);
	}
}
