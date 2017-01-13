package com.es.demo;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;
/**
 * 
 * <br>
 * 标题: map转成bean<br>
 * 描述: <br>
 * 公司: www.tydic.com<br>
 * @autho wesley
 * @time 2016年10月31日 下午5:03:08
 */
public class ConverterUtil {

	/** 转换为驼峰（大写）
	 * 
	 * @param underscoreName
	 * @return */
	public static String camelCaseName(String underscoreName) {
		StringBuilder result = new StringBuilder();
		if (underscoreName != null && underscoreName.length() > 0) {
			boolean flag = false;
			for (int i = 0; i < underscoreName.length(); i++) {
				char ch = underscoreName.charAt(i);
				if ("_".charAt(0) == ch) {
					flag = true;
				}
				else {
					if (flag) {
						result.append(Character.toUpperCase(ch));
						flag = false;
					}
					else {
						result.append(Character.toLowerCase(ch));
					}
				}
			}
		}
		return result.toString();
	}

	/** 转换为下划线(大写)
	 * 
	 * @param camelCaseName
	 * @return */
	public static String underscoreName(String camelCaseName) {
		StringBuilder result = new StringBuilder();
		int len = camelCaseName.length();
		if (camelCaseName != null && len > 0) {
			result.append(camelCaseName.substring(0, 1).toUpperCase());
			for (int i = 1; i < len; i++) {
				char ch = camelCaseName.charAt(i);
				if (Character.isUpperCase(ch)) {
					result.append("_");
					result.append(ch);
				}
				else {
					result.append(Character.toUpperCase(ch));
				}
			}
		}
		return result.toString();
	}

	/** 把Map<String,Object>处理成实体类
	 * 
	 * @param clazz
	 *        想要的实体类
	 * @param map
	 *        包含信息的Map对象
	 * @return */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Object mapToObject(Class clazz, Map<String, Object> map) {
		if (null == map) {
			return null;
		}
		Field[] fields = clazz.getDeclaredFields(); // 取到类下所有的属性，也就是变量名
		Field field;
		Object o = null;
		try {
			o = clazz.newInstance();
		}
		catch (InstantiationException e1) {
			e1.printStackTrace();
		}
		catch (IllegalAccessException e1) {
			e1.printStackTrace();
		}
		for (int i = 0; i < fields.length; i++) {
			field = fields[i];
			String fieldName = field.getName();
			// 把属性的第一个字母处理成大写
			String stringLetter = fieldName.substring(0, 1).toUpperCase();
			// 取得set方法名，比如setBbzt
			String setterName = "set" + stringLetter + fieldName.substring(1);
			// 真正取得set方法。
			Method setMethod = null;
			Class fieldClass = field.getType();
			try {
				if (isHaveSuchMethod(clazz, setterName)) {
					if (fieldClass == String.class) {
						setMethod = clazz.getMethod(setterName, fieldClass);
						if (null != map.get(fieldName) && !("").equals(map.get(fieldName))) {
							setMethod.invoke(o, String.valueOf(map.get(fieldName)));// 为其赋值
						}
					}
					else if (fieldClass == Integer.class || fieldClass == int.class) {
						setMethod = clazz.getMethod(setterName, fieldClass);
						if (null != map.get(fieldName) && !("").equals(map.get(fieldName))) {
							setMethod.invoke(o, Integer.parseInt(String.valueOf(map.get(fieldName))));// 为其赋值
						}
						
					}
					else if (fieldClass == Boolean.class || fieldClass == boolean.class) {
						setMethod = clazz.getMethod(setterName, fieldClass);
						if (null != map.get(fieldName) && !("").equals(map.get(fieldName))) {
							setMethod.invoke(o, Boolean.getBoolean(String.valueOf(map.get(fieldName))));// 为其赋值
						}
						
					}
					else if (fieldClass == Short.class || fieldClass == short.class) {
						setMethod = clazz.getMethod(setterName, fieldClass);
						if (null != map.get(fieldName) && !("").equals(map.get(fieldName))) {
							setMethod.invoke(o, Short.parseShort(String.valueOf(map.get(fieldName))));// 为其赋值
						}
						
					}
					else if (fieldClass == Long.class || fieldClass == long.class) {
						setMethod = clazz.getMethod(setterName, fieldClass);
						if (null != map.get(fieldName) && !("").equals(map.get(fieldName))) {
							setMethod.invoke(o, Long.parseLong(String.valueOf(map.get(fieldName))));// 为其赋值
						}
						
					}
					else if (fieldClass == Double.class || fieldClass == double.class) {
						setMethod = clazz.getMethod(setterName, fieldClass);
						if (null != map.get(fieldName) && !("").equals(map.get(fieldName))) {
							setMethod.invoke(o, Double.parseDouble(String.valueOf(map.get(fieldName))));// 为其赋值
						}
						
					}
					else if (fieldClass == Float.class || fieldClass == float.class) {
						setMethod = clazz.getMethod(setterName, fieldClass);
						if (null != map.get(fieldName) && !("").equals(map.get(fieldName))) {
							setMethod.invoke(o, Float.parseFloat(String.valueOf(map.get(fieldName))));// 为其赋值
						}
						
					}
					else if (fieldClass == BigInteger.class) {
						setMethod = clazz.getMethod(setterName, fieldClass);
						if (null != map.get(fieldName) && !("").equals(map.get(fieldName))) {
							setMethod.invoke(o, BigInteger.valueOf(Long.parseLong(String.valueOf(map.get(fieldName)))));// 为其赋值
						}
						
					}
					else if (fieldClass == BigDecimal.class) {
						setMethod = clazz.getMethod(setterName, fieldClass);
						if (null != map.get(fieldName) && !("").equals(map.get(fieldName))) {
							setMethod.invoke(o, BigDecimal.valueOf(Long.parseLong(String.valueOf(map.get(fieldName)))));// 为其赋值
						}
						
					}
					else if (fieldClass == Date.class) {
						setMethod = clazz.getMethod(setterName, fieldClass);
						if (null != map.get(fieldName) && !("").equals(map.get(fieldName))) {
							DateTime date = ISODateTimeFormat.dateTimeParser().parseDateTime((String) map.get(fieldName));
							setMethod.invoke(o, new Date(date.getMillis()));// 为期赋值
						}
						/*
						 * if (map.get(fieldName).getClass() == java.sql.Date.class) { setMethod.invoke(o, new Date(((java.sql.Date)
						 * map.get(fieldName)).getTime()));// 为其赋值 } else if (map.get(fieldName).getClass() ==
						 * java.sql.Time.class) { setMethod.invoke(o, new Date(((java.sql.Time) map.get(fieldName)).getTime()));// 为其赋值 } else
						 * if (map.get(fieldName).getClass() == java.sql.Timestamp.class) { setMethod.invoke(o, new Date(((java.sql.Timestamp)
						 * map.get(fieldName)).getTime()));// 为其赋值 }else if(map.get(fieldName).getClass() ==
						 * org.joda.time.format.ISODateTimeFormat.class){ DateTime date =
						 * ISODateTimeFormat.dateTimeParser().parseDateTime((String)map.get(fieldName)); setMethod.invoke(o, new
						 * Date(date.getMillis())); }
						 */
					}
				}
			}
			catch (SecurityException e) {
				e.printStackTrace();
			}
			catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
			catch (IllegalArgumentException e) {
				e.printStackTrace();
			}
			catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			catch (InvocationTargetException e) {
				e.printStackTrace();
			}

		}
		return o;
	}

	/** 判断某个类里是否有某个方法
	 * 
	 * @param clazz
	 * @param methodName
	 * @return */
	public static boolean isHaveSuchMethod(Class<?> clazz, String methodName) {
		Method[] methodArray = clazz.getMethods();
		boolean result = false;
		if (null != methodArray) {
			for (int i = 0; i < methodArray.length; i++) {
				if (methodArray[i].getName().equals(methodName)) {
					result = true;
					break;
				}
			}
		}
		return result;
	}

	public static void main(String[] args) {
		System.err.println(underscoreName("name_full"));
		System.err.println(camelCaseName("NAME_FILL"));
		System.err.println(camelCaseName("nameFill"));
		System.err.println(underscoreName("nameFill"));
	}
}
