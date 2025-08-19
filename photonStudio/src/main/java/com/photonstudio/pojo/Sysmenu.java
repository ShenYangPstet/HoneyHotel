package com.photonstudio.pojo;




import java.util.List;

import lombok.Data;

@Data

public class Sysmenu {
	
     private Integer id;
     private Integer parentId;
     private String menuName;
     private String menuPath;
     private Integer menuLevel;
     private Integer apptypeid;
     private List<Sysmenu> children;
}
