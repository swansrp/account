package com.srct.service.account.dao.repository;

import com.srct.service.account.dao.entity.Permit;
import com.srct.service.account.dao.mapper.PermitDao;
import com.srct.service.frame.BaseRepository;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

/**
 * Title: ${NAME}.java
 * Description: Copyright: Copyright (c) 2019 Company: BHFAE
 *
 * @author Sharp
 * @date 2019-7-26 17:44
 * @description Project Name: Grote
 * @Package: ${PACKAGE_NAME}
 */
@Service
public class PermitService extends BaseRepository<PermitDao, Permit> {

    @Resource
    private PermitDao permitDao;


    public int updateBatch(List<Permit> list) {
        return permitDao.updateBatch(list);
    }


    public int batchInsert(List<Permit> list) {
        return permitDao.batchInsert(list);
    }


    public int insertOrUpdate(Permit record) {
        return permitDao.insertOrUpdate(record);
    }


    public int insertOrUpdateSelective(Permit record) {
        return permitDao.insertOrUpdateSelective(record);
    }

    public Permit getPermitById(Integer id) {
        return permitDao.selectByPrimaryKey(id);
    }

    public List<Permit> getPermitListByParentId(Integer id) {
        Example example = new Example(Permit.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("parentPermitId", id);
        return permitDao.selectByExample(example);
    }

    @Override
    protected PermitDao getDao() {
        return permitDao;
    }
}



