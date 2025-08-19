package com.photonstudio.common.vo;

import lombok.Data;

@Data
public class JsResult {
	private Integer resultCode;
	private String message;
	private Object dataItems;

	public JsResult() {

	}

	public	JsResult(Integer resultCode, String message, Object dataItems) {
		this.resultCode = resultCode;
		this.message = message;
		this.dataItems = dataItems;
	}

	public JsResult(Object dataItems) {
		this.resultCode = 0;
		this.message = "处理成功";
		this.dataItems = dataItems;
	}

	public static JsResult build(Integer resultCode, String message) {
		return new JsResult(resultCode, message, null);
	}

	public static JsResult build(Integer resultCode, String message, Object dataItems) {
		return new JsResult(resultCode, message, dataItems);
	}

	public static JsResult oK(Object dataItems) {
		return new JsResult(dataItems);
	}

	public static JsResult oK() {
		return new JsResult(null);
	}
}
