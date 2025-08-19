package com.photonstudio.serviceImpl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.photonstudio.common.SessionUtil;
import com.photonstudio.mapper.TypeLayoutMapper;
import com.photonstudio.pojo.Drtypeinfo;
import com.photonstudio.pojo.FileUploadProperteis;
import com.photonstudio.pojo.Icon;
import com.photonstudio.pojo.TypeLayout;
import com.photonstudio.pojo.vo.TypeLayoutVO;
import com.photonstudio.service.IconService;
import com.photonstudio.service.TypeLayoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TypeLayoutServiceImpl extends ServiceImpl<TypeLayoutMapper, TypeLayout> implements TypeLayoutService {
    private final DrtypeinfoServiceImpl drtypeinfoService;
    private final IconService iconService;
    private final FileUploadProperteis fileUploadProperties;

    @Override
    public List<TypeLayoutVO> listTypeWithLayout(Integer userId) {
        List<TypeLayout> typeLayoutList = lambdaQuery().eq(TypeLayout::getUserId, userId).list();
        List<Integer> typeIdList = typeLayoutList.stream().map(TypeLayout::getTypeId).collect(Collectors.toList());
        if (CollUtil.isEmpty(typeIdList)) {
            typeIdList = drtypeinfoService.findObjectByParentId(SessionUtil.getDatabase(), 0).stream()
                    .map(Drtypeinfo::getDrtypeid)
                    .collect(Collectors.toList());
        }
        Map<Integer, String> iconId2Url = iconService.findObject().stream()
                .map(Icon.type2Url(fileUploadProperties.getStaticAccessPath()))
                .collect(Collectors.toMap(Icon::getIconid, Icon::getIcontype, selectNewMerger()));
        Map<Integer, Integer> typeId2TypeIndexMap = typeLayoutList.stream()
                .collect(Collectors.toMap(TypeLayout::getTypeId, TypeLayout::getTypeIndex, selectNewMerger()));
        return drtypeinfoService.findAllStatusVos(SessionUtil.getDatabase(), typeIdList).stream()
                .filter(Drtypeinfo::isShow)
                .map(Drtypeinfo.fillIconUrl(iconId2Url))
                .map(TypeLayoutVO::drTypeInfo2TypeLayoutVO)
                .map(TypeLayoutVO.fillTypeIndex(typeId2TypeIndexMap))
                .collect(Collectors.toList());
    }

    @Override
    public void saveByUserId(List<TypeLayout> typeLayoutList, Integer userId) {
        lambdaUpdate().eq(TypeLayout::getUserId, userId).remove();
        for (TypeLayout typeLayout : typeLayoutList) {
            typeLayout.setUserId(userId);
        }
        saveBatch(typeLayoutList);
    }

    /**
     * stream流map转换合并函数
     * map新旧key相同时选择新key对应的值
     *
     * @return 合并函数
     */
    private static <T> BinaryOperator<T> selectNewMerger() {
        return (oldValue, newValue) -> newValue;
    }
}
