package net.ibytes.study.dbutils.dao;

import net.ibytes.study.dbutils.domain.City;

import java.sql.SQLException;
import java.util.List;

/**
 * @author dingfurong
 * @date 2019/4/8
 * @description
 */
public interface CityDao {
    /**
     * 新增
     * @param city
     * @throws SQLException
     */
    void add(City city) throws SQLException;

    /**
     * 更新
     * @param city
     * @throws SQLException
     */
    void update(City city) throws SQLException;

    /**
     * 删除
     * @param id
     * @throws SQLException
     */
    void delete(Integer id) throws SQLException;

    /**
     * 查找
     * @param id
     * @return
     * @throws SQLException
     */
    City findBy(Integer id) throws SQLException;

    /**
     * 查找全部
     * @return
     * @throws SQLException
     */
    List<City> findAll() throws SQLException;

    /**
     * 统计总数
     * @return
     * @throws SQLException
     */
    long cityCount() throws SQLException;

    /**
     * 批量插入
     * @param city
     * @throws SQLException
     */
    void batchInsert(List<City> city)throws SQLException;

    /**
     * 批量更新
     * @param city
     * @throws SQLException
     */
    void batchUpdate(List<City> city)throws SQLException;
}
