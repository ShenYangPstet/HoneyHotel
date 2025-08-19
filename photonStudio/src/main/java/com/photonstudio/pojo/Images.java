package com.photonstudio.pojo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Images {
	private Integer imgid;
	private String imgname;
	private String imgtype;
	private String imgurl;

}
