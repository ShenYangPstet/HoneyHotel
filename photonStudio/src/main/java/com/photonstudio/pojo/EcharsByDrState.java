package com.photonstudio.pojo;

import lombok.Data;

@Data
public class EcharsByDrState {
	private Integer runState;
	private Integer stopState;
	private Integer alarmState;
	private Integer errorState;
	private Integer drCount;
}
