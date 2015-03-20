package com.yz.snews.util;

import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Maps;

public class SearchFilter2 {
	public enum Operator {
		EQ, LIKE, GT, LT, GTE, LTE, IN, NE, RANGEDATE
	}

	public String fieldName;
	public Object value;
	public Operator operator;

	public SearchFilter2(String fieldName, Operator operator, Object value) {
		this.fieldName = fieldName;
		this.value = value;
		this.operator = operator;
	}

	/**
	 * searchParams中key的格式为OPERATOR_FIELDNAME
	 */
	public static Map<String, SearchFilter2> parse(Map<String, Object> searchParams) {
		Map<String, SearchFilter2> filters = Maps.newHashMap();

		for (Entry<String, Object> entry : searchParams.entrySet()) {
			// 过滤掉空值
			String key = entry.getKey();
			Object value = entry.getValue();
			if (StringUtils.isBlank((String) value)) {
				continue;
			}

			// 拆分operator与filedAttribute
			String[] names = StringUtils.split(key, "_");
			if (names.length != 2) {
				throw new IllegalArgumentException(key + " is not a valid search filter name");
			}
			String filedName = names[1];
			Operator operator = Operator.valueOf(names[0]);

			// 创建searchFilter
			SearchFilter2 filter = new SearchFilter2(filedName, operator, value);
			filters.put(key, filter);
		}

		return filters;
	}
}
