package com.photonstudio.serviceImpl;


import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.photonstudio.converter.BoardConverter;
import com.photonstudio.mapper.BoardMapper;
import com.photonstudio.mapper.BoardTemplateMapper;
import com.photonstudio.pojo.Board;
import com.photonstudio.pojo.BoardTemplate;
import com.photonstudio.pojo.vo.query.BoardQuery;
import com.photonstudio.pojo.vo.req.BoardBatchSaveReq;
import com.photonstudio.pojo.vo.req.BoardSaveReq;
import com.photonstudio.pojo.vo.resp.BoardResp;
import com.photonstudio.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;


/**
 * 看板
 *
 * @author bingo
 */
@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {
    private final BoardMapper boardMapper;
    private final BoardConverter boardConverter;
    private final BoardTemplateMapper boardTemplateMapper;

    @Override
    public List<BoardResp> list(BoardQuery boardQuery) {
        List<Integer> templateIds =
                boardTemplateMapper.listIdByCodeAndArea(boardQuery.getCode(), boardQuery.getArea());
        List<Board> boardList = boardMapper.listByNameAndTemplateIds(boardQuery.getName(), templateIds);
        Map<Integer, BoardTemplate> id2Template = boardTemplateMapper.mapId2Template(templateIds);
        return boardList.stream()
                .map(board -> boardConverter.toResp(board, id2Template.get(board.getBoardTemplateId())))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchSave(BoardBatchSaveReq saveReq) {
        List<BoardSaveReq> boardList = saveReq.getBoardList();
        for (BoardSaveReq boardSaveReq : boardList) {
            BoardTemplate boardTemplate =
                    boardTemplateMapper.selectById(boardSaveReq.getBoardTemplateId());
            //if (ObjectUtil.isEmpty(boardTemplate))throw new ServiceException("看板模板不存在");
            //throwIfEmpty(boardTemplate, "看板模板不存在");
            //if (!saveReq.getArea().equals(boardTemplate.getArea()))throw new ServiceException("只允许批量保存同一个使用区域的看板");
            //throwIf(!saveReq.getArea().equals(boardTemplate.getArea()), "只允许批量保存同一个使用区域的看板");
        }
        List<Integer> templateIds = boardTemplateMapper.listIdByArea(saveReq.getArea());
        // 数据库看板id集合
        List<Integer> dbBoardIds = boardMapper.listIdByTemplateIds(templateIds);
        // 保存看板id集合
        List<Integer> saveIds = boardList.stream().map(BoardSaveReq::getId).collect(Collectors.toList());
        // 删除看板id集合（数据库中存在，但是保存的id集合中不存在）
        List<Integer> deleteIds =
                dbBoardIds.stream().filter(id -> !saveIds.contains(id)).collect(Collectors.toList());
        if (!deleteIds.isEmpty()) {
            boardMapper.deleteBatchIds(deleteIds);
        }
        boardList.forEach(
                boardSaveReq -> {

                    Board board = boardConverter.forSaveReq(boardSaveReq);
                    TableInfo tableInfo = TableInfoHelper.getTableInfo(board.getClass());
                    Assert.notNull(
                            tableInfo, "error: can not execute. because can not find cache of TableInfo for entity!");
                    String keyProperty = tableInfo.getKeyProperty();
                    Assert.notEmpty(
                            keyProperty, "error: can not execute. because can not find column for id from entity!");
                    Object keyValue = tableInfo.getPropertyValue(board, keyProperty);
                    if (ObjectUtils.isEmpty(keyValue) || Objects.isNull(boardMapper.selectById((Serializable) keyValue))) {
                        boardMapper.insert(board);
                    } else {
                        boardMapper.updateById(board);
                    }

                    //boardMapper.insertOrUpdate(boardConverter.forSaveReq(boardSaveReq));
                });
    }
}
