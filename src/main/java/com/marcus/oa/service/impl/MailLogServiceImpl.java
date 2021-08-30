package com.marcus.oa.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.marcus.oa.mapper.MailLogMapper;
import com.marcus.oa.pojo.MailLog;
import com.marcus.oa.service.IMailLogService;
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
public class MailLogServiceImpl extends ServiceImpl<MailLogMapper, MailLog> implements IMailLogService {

}
