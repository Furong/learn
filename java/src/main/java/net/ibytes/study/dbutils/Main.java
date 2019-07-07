package net.ibytes.study.dbutils;

import net.ibytes.study.dbutils.dao.CityDao;
import net.ibytes.study.dbutils.dao.CityDaoImpl;
import net.ibytes.study.dbutils.domain.City;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author dingfurong
 * @date 2019/4/8
 * @description
 */
public class Main {
    public static void main(String[] args) throws SQLException {

        CityDao cityDao = new CityDaoImpl();
        List<City> list = cityDao.findAll();
        List<City> cities = getList();
        cityDao.batchUpdate(cities);

        System.out.println(list);
        System.out.println("------执行完毕-------");
    }

    public static List<City> getList() {
        List<City> list = new ArrayList<>();
        City city = new City();
        city.setId(2);
        city.setName("上海副本");
        list.add(city);

        city = new City();
        city.setId(3);
        city.setName("杭州副本");
        list.add(city);
        return list;
    }


}
