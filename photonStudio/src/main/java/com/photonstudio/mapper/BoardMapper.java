package com.photonstudio.mapper;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.google.common.collect.Lists;
import com.photonstudio.pojo.Board;
import org.apache.commons.collections4.CollectionUtils;


import java.util.List;
import java.util.stream.Collectors;

/**
 * 看板
 *
 * @author bingo
 */
public interface BoardMapper extends BaseMapper<Board> {

  default void deleteByTemplateIds(List<Integer> templateIds) {
    //delete(Board::getBoardTemplateId, templateIds);
    if (CollectionUtils.isEmpty(templateIds)) {
    delete(new LambdaQueryWrapper<Board>().in(Board::getBoardTemplateId, templateIds));
    }
  }

  default List<Board> listByNameAndTemplateIds(String name, List<Integer> templateIds) {
    if (CollectionUtils.isEmpty(templateIds)) {
      return Lists.newArrayList();
    }
    return selectList(
            new LambdaQueryWrapper<Board>()
            .like(StrUtil.isNotEmpty(name),Board::getName, name)
            .in(Board::getBoardTemplateId, templateIds));
  }

  default List<Integer> listIdByTemplateIds(List<Integer> templateIds) {
    //return selectColumnList(Board::getId, Board::getBoardTemplateId, templateIds);
    if (CollectionUtils.isEmpty(templateIds)) {
      return Lists.newArrayList();
    }
    return selectList(new LambdaQueryWrapper<Board>().select(Board::getId).in(Board::getBoardTemplateId, templateIds)).stream()
            .map(Board::getId)
            .collect(Collectors.toList());
  }
}
