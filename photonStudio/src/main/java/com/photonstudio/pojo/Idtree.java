package com.photonstudio.pojo;

import java.util.List;

import lombok.Data;

@Data
public class Idtree {
	private Integer id;
	private String name;
	private List<Idtree> child;
	private Integer type;
}
