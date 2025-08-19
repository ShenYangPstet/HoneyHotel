package com.photonstudio.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.photonstudio.mapper.CanvasMapper;
import com.photonstudio.pojo.Canvas;
import com.photonstudio.service.CanvasService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CanvasServiceImpl extends ServiceImpl<CanvasMapper, Canvas> implements CanvasService {

    private final CanvasMapper canvasMapper;

    @Override
    public Canvas getByPicId(Integer picId) {
        return lambdaQuery().eq(Canvas::getPicId, picId).one();
    }
    @Override
    public void insertOrUpdate(Canvas canvas) {
        Canvas dbCanvas = getByPicId(canvas.getPicId());
        if (dbCanvas == null) {
            save(canvas);
        } else {
            lambdaUpdate().eq(Canvas::getPicId, canvas.getPicId()).update(canvas);
        }
    }

    @Override
    public void deleteByPicId(Integer picId) {
        lambdaUpdate().eq(Canvas::getPicId, picId).remove();
    }
}
