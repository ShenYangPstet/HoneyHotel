package com.photonstudio.pojo;

import java.util.Map;

import lombok.Data;

@Data
public class AppenergyData {
	
	private Map<String,String> beforSevenData;
	private Map<Integer,String> appEnergyData;
	private String apptotalEffEnergy;
	private String apptotalDrCost;
	private String apptotalPeopleCost;
	private String apptotalCarbon;
	private String apptotalTree;
	private String apptotalCost;

}
