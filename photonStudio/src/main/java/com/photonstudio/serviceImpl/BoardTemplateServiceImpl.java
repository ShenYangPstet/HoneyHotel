package com.photonstudio.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.photonstudio.converter.BoardTemplateConverter;
import com.photonstudio.mapper.AppsysuserMapper;
import com.photonstudio.mapper.BoardMapper;
import com.photonstudio.mapper.BoardTemplateMapper;
import com.photonstudio.pojo.Appsysuser;
import com.photonstudio.pojo.BoardTemplate;
import com.photonstudio.pojo.Usertoken;
import com.photonstudio.pojo.vo.query.BoardTemplatePageQuery;
import com.photonstudio.pojo.vo.req.BoardTemplateSaveReq;
import com.photonstudio.pojo.vo.resp.BoardTemplateResp;
import com.photonstudio.service.BoardTemplateService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * 看板模板
 *
 * @author bingo
 */
@Service
@RequiredArgsConstructor
public class BoardTemplateServiceImpl implements BoardTemplateService {
  private final BoardTemplateMapper boardTemplateMapper;
  private final BoardTemplateConverter boardTemplateConverter;
  private final BoardMapper boardMapper;
  private final AppsysuserMapper appsysuserMapper;
  private Appsysuser getAppsysuser(){
    ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder
            .getRequestAttributes();
    String token = requestAttributes.getRequest().getHeader("Zsqy_test");
    Usertoken tokenDB = appsysuserMapper.findUsertokenByToken(token);
    if (tokenDB == null) throw new RuntimeException("请重新登录");
    Appsysuser user = appsysuserMapper.findAppsysuserByUsername(tokenDB.getUsername());
    return user;
  }
  @Override
  public IPage<BoardTemplateResp> page(BoardTemplatePageQuery pageQuery) {
    BoardTemplate boardTemplate = boardTemplateConverter.fromPageQuery(pageQuery);
    return boardTemplateMapper
        .selectPage(pageQuery.toPage(), new QueryWrapper<>(boardTemplate).orderByDesc("id"))
        .convert(boardTemplateConverter::toResp);
  }

  @Override
  public void save(BoardTemplateSaveReq saveReq) {
    BoardTemplate boardTemplate = boardTemplateConverter.fromSaveReq(saveReq);
    //if(boardTemplateMapper.exists(boardTemplate, BoardTemplate::getCode))throw new ServiceException("看板模板编码已存在");
    //throwIf(boardTemplateMapper.exists(boardTemplate, BoardTemplate::getCode), "看板模板编码已存在");
    boardTemplateMapper.insertOrUpdate(boardTemplate);

  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void deleteByIds(List<Integer> ids) {
    List<Integer> deleteIds =new ArrayList<>();
    if (!CollectionUtils.isEmpty(ids)){
      LambdaQueryWrapper<BoardTemplate> lambdaQueryWrapper = new LambdaQueryWrapper<>();
      deleteIds=boardTemplateMapper.selectList(lambdaQueryWrapper.select(BoardTemplate::getId).in(BoardTemplate::getId, ids)).stream()
              .map(BoardTemplate::getId)
              .collect(Collectors.toList());
    }
    //if (ObjectUtil.isEmpty(deleteIds))throw new ServiceException("看板模板不存在");
    boardTemplateMapper.deleteBatchIds(deleteIds);
    // 删除看板模板生成的看板
    boardMapper.deleteByTemplateIds(deleteIds);
  }
}
