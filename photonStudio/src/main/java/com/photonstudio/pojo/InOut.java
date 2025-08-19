package com.photonstudio.pojo;

import lombok.Data;

@Data
public class InOut {
	private Integer id;
	private String itemId;
	private String parkName;
	private String parkCode;
	private String idno;
	private String cardType;
	private String mediaType;
	private String equipName;
	private String equipCode;
	private String equipType;
	private String openTime;
	private String openType;
	private String recordType;
	private String ownerName;
	private String ownerPhone;
	private String visitorName;
	private String visitorPhone;
	private String inoutPhoto;
	private Integer visitorFlag;
	private String attach;
}
