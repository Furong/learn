package net.ibytes.study.dbutils.dao;

import net.ibytes.study.dbutils.DBUtils;
import net.ibytes.study.dbutils.domain.City;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.List;

/**
 * @author dingfurong
 * @date 2019/4/8
 * @description
 */
public class CityDaoImpl  implements CityDao{

    private QueryRunner queryRunner = null;
    public CityDaoImpl(){
        queryRunner = new QueryRunner();
    }

    @Override
    public void add(City city) throws SQLException {
        String sql = "insert into city(id,name) values (?,?)";
        queryRunner.update(DBUtils.getConnection(),sql,city.getId(),city.getName());
    }

    @Override
    public void update(City city) throws SQLException {
        String sql = "update city set name = ? where id = ?";
        queryRunner.update(DBUtils.getConnection(),sql,city.getName(),city.getId());
    }

    @Override
    public void delete(Integer id) throws SQLException {
        String sql = "delete city where id = ?";
        queryRunner.update(DBUtils.getConnection(),sql,id);
    }

    @Override
    public City findBy(Integer id) throws SQLException {
        String sql = "select id,name from city where id = ?";
        City city = queryRunner.query(DBUtils.getConnection(),sql,new BeanHandler<>(City.class),id);
        return city;
    }

    @Override
    public List<City> findAll() throws SQLException {
        String sql = "select id,name,rank from city";
        List<City> list = queryRunner.query(DBUtils.getConnection(),sql,new BeanListHandler<>(City.class));
        return list;
    }

    @Override
    public long cityCount() throws SQLException {
        String sql = "select count(0) from city";
        return queryRunner.query(DBUtils.getConnection(),sql,new ScalarHandler<Long>());
    }

    @Override
    public void batchInsert(List<City> list) throws SQLException {
        String sql = "insert into city(id,name) values (?,?)";
        Object[][] param= new Object[list.size()][];
        for(int i = 0; i < list.size(); i++){
            param[i] = new Object[2];
            param[i][0] = list.get(i).getId();
            param[i][1] = list.get(i).getName();
        }
        queryRunner.batch(DBUtils.getConnection(),sql,param);
    }

    @Override
    public void batchUpdate(List<City> list) throws SQLException {
        String sql = "update city set name = ? where id = ?";
        Object[][] param= new Object[list.size()][];
        for(int i = 0; i < list.size(); i++){
            param[i] = new Object[2];
            param[i][0] = list.get(i).getName();
            param[i][1] = list.get(i).getId();
        }
        queryRunner.batch(DBUtils.getConnection(),sql,param);
    }
}
