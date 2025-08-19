package com.photonstudio.pojo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.function.Function;

@Data
@Accessors(chain = true)
public class Icon {
    private Integer iconid;
    private String iconname;
    private String icontype;
    private String iconsuffix;//后缀  png,git

    /**
     * 图标类型名称转成图标路径
     *
     * @param staticAccessPath 静态资源上传路径
     * @return 图标类型名称转成图标路径函数
     */
    public static Function<Icon, Icon> type2Url(String staticAccessPath) {
        return (icon) -> icon.setIcontype(staticAccessPath + "images/icon/" + icon.getIcontype() + "/type.png");
    }
}
