package com.photonstudio.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.photonstudio.mapper.LowcodeThemeMapper;
import com.photonstudio.pojo.LowcodeTheme;
import com.photonstudio.service.LowcodeThemeSerice;
import org.springframework.stereotype.Service;

/**
 * 页面主题表service实现类
 *
 * @author hx
 * @date 2022/6/9 16:27
 */
@Service
public class LowcodeThemeSercieImpl extends ServiceImpl<LowcodeThemeMapper, LowcodeTheme> implements LowcodeThemeSerice {
    @Override
    public void insertOrUpdate(LowcodeTheme lowcodeTheme) {
        if (lowcodeTheme.getId() == null) {
            save(lowcodeTheme);
        } else {
            lambdaUpdate().eq(LowcodeTheme::getId, lowcodeTheme.getId()).update(lowcodeTheme);
        }
    }

    @Override
    public LowcodeTheme getById(Integer id) {
        return lambdaQuery().eq(LowcodeTheme::getId,id).one();
    }

    @Override
    public void deleteById(Integer id) {
        lambdaUpdate().eq(LowcodeTheme::getId,id).remove();
    }
}
