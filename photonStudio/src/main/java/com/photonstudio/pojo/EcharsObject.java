package com.photonstudio.pojo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class EcharsObject {
	private String name;
	private String value;
	private String alarmtypelevel;
}
