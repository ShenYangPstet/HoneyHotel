package com.photonstudio.mapper;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.google.common.collect.Maps;
import com.photonstudio.pojo.BoardTemplate;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 看板模板
 *
 * @author bingo
 */
public interface BoardTemplateMapper extends BaseMapper<BoardTemplate> {

  default Map<Integer, BoardTemplate> mapId2Template(List<Integer> templateIds) {
    //   return selectEntityMap(BoardTemplate::getId, templateIds);
    if (CollectionUtils.isEmpty(templateIds)) {
      return Maps.newHashMap();
    }
    return selectList(new LambdaQueryWrapper<BoardTemplate>().in(BoardTemplate::getId, templateIds)).stream()
            .collect(Collectors.toMap(BoardTemplate::getId, Function.identity(), (v1, v2) -> v2));
  }

  default List<Integer> listIdByCodeAndArea(String code, String area) {
    return selectList(
            new LambdaQueryWrapper<BoardTemplate>()
                .eq(StrUtil.isNotEmpty(code),BoardTemplate::getCode, code)
                .eq(StrUtil.isEmpty(area),BoardTemplate::getArea, area))
        .stream()
        .map(BoardTemplate::getId)
        .collect(Collectors.toList());
  }

  default List<Integer> listIdByArea(String area) {
    //return selectColumnList(BoardTemplate::getId, BoardTemplate::getArea, area);
    return selectList(new LambdaQueryWrapper<BoardTemplate>().select(BoardTemplate::getId).eq(BoardTemplate::getArea, area)).stream()
            .map(BoardTemplate::getId)
            .collect(Collectors.toList());
  }

  default int insertOrUpdate(BoardTemplate boardTemplate){
    TableInfo tableInfo = TableInfoHelper.getTableInfo(boardTemplate.getClass());
    Assert.notNull(
            tableInfo, "error: can not execute. because can not find cache of TableInfo for entity!");
    String keyProperty = tableInfo.getKeyProperty();
    Assert.notEmpty(
            keyProperty, "error: can not execute. because can not find column for id from entity!");
    Object keyValue = tableInfo.getPropertyValue(boardTemplate, keyProperty);
    return ObjectUtils.isEmpty(keyValue) || Objects.isNull(selectById((Serializable) keyValue))
            ? insert(boardTemplate)
            : updateById(boardTemplate);
  }
}
