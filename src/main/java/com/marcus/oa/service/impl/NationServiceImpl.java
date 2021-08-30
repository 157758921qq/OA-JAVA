package com.marcus.oa.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.marcus.oa.mapper.NationMapper;
import com.marcus.oa.pojo.Nation;
import com.marcus.oa.service.INationService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Marcus
 * @since 2021-08-23
 */
@Service
public class NationServiceImpl extends ServiceImpl<NationMapper, Nation> implements INationService {

}
