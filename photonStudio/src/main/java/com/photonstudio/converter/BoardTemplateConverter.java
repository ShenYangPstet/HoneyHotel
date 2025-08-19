package com.photonstudio.converter;

import com.photonstudio.pojo.BoardTemplate;
import com.photonstudio.pojo.vo.query.BoardTemplatePageQuery;
import com.photonstudio.pojo.vo.req.BoardTemplateSaveReq;
import com.photonstudio.pojo.vo.resp.BoardTemplateResp;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;

/**
 * 看板模板转换器
 *
 * @author bingo
 */
@Mapper(componentModel = ComponentModel.SPRING)
public interface BoardTemplateConverter {

  BoardTemplate fromPageQuery(BoardTemplatePageQuery pageQuery);

  BoardTemplateResp toResp(BoardTemplate boardTemplate);

  BoardTemplate fromSaveReq(BoardTemplateSaveReq saveReq);
}
