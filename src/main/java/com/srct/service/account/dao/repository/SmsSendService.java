package com.srct.service.account.dao.repository;

import com.srct.service.account.dao.entity.SmsSend;
import com.srct.service.account.dao.mapper.SmsSendDao;
import com.srct.service.frame.BaseRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Title: ${NAME}.java
 * Description: Copyright: Copyright (c) 2019 Company: BHFAE
 *
 * @author Sharp
 * @date 2019-8-7 11:36
 * @description Project Name: Grote
 * @Package: ${PACKAGE_NAME}
 */
@Service
public class SmsSendService extends BaseRepository<SmsSendDao, SmsSend> {

    @Resource
    private SmsSendDao smsSendDao;


    public int updateBatch(List<SmsSend> list) {
        return smsSendDao.updateBatch(list);
    }


    public int batchInsert(List<SmsSend> list) {
        return smsSendDao.batchInsert(list);
    }


    public int insertOrUpdate(SmsSend record) {
        return smsSendDao.insertOrUpdate(record);
    }


    public int insertOrUpdateSelective(SmsSend record) {
        return smsSendDao.insertOrUpdateSelective(record);
    }

    @Override
    protected SmsSendDao getDao() {
        return smsSendDao;
    }
}
