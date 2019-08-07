package com.srct.service.account.dao.repository;

import com.srct.service.account.dao.entity.User;
import com.srct.service.account.dao.mapper.UserDao;
import com.srct.service.frame.BaseRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Title: ${NAME}.java
 * Description: Copyright: Copyright (c) 2019 Company: BHFAE
 *
 * @author Sharp
 * @date 2019-7-26 17:55
 * @description Project Name: Grote
 * @Package: ${PACKAGE_NAME}
 */
@Service
public class UserService extends BaseRepository<UserDao, User> {

    @Resource
    private UserDao userDao;

    public int batchInsert(List<User> list) {
        return userDao.batchInsert(list);
    }

    public User getUserByEmail(String email) {
        Map<String, Object> propertyMap = new HashMap<>(1);
        propertyMap.put("email", email);
        return super.selectOneByPropertyMap(propertyMap);
    }

    public User getUserByPhoneNumber(String phoneNumber) {
        Map<String, Object> propertyMap = new HashMap<>(1);
        propertyMap.put("phone", phoneNumber);
        return super.selectOneByPropertyMap(propertyMap);
    }

    public User getUserByUserId(String userId) {
        Map<String, Object> propertyMap = new HashMap<>(1);
        propertyMap.put("userId", userId);
        return super.selectOneByPropertyMap(propertyMap);
    }

    public int insertOrUpdate(User record) {
        return userDao.insertOrUpdate(record);
    }

    public int insertOrUpdateSelective(User record) {
        return userDao.insertOrUpdateSelective(record);
    }

    public int updateBatch(List<User> list) {
        return userDao.updateBatch(list);
    }

    @Override
    protected UserDao getDao() {
        return userDao;
    }
}




