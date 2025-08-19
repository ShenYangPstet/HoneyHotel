package com.photonstudio.converter;

import com.photonstudio.pojo.Board;
import com.photonstudio.pojo.BoardTemplate;
import com.photonstudio.pojo.vo.req.BoardSaveReq;
import com.photonstudio.pojo.vo.resp.BoardResp;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;

/**
 * 看板
 *
 * @author bingo
 */
@Mapper(componentModel = ComponentModel.SPRING)
public interface BoardConverter {

  @Mapping(target = "id", source = "board.id")
  @Mapping(target = "name", source = "board.name")
  @Mapping(target = "description", source = "board.description")
  BoardResp toResp(Board board, BoardTemplate boardTemplate);

  Board forSaveReq(BoardSaveReq boardSaveReq);
}
