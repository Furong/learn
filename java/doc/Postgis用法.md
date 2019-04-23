## PostGIS的使用

### 1. 介绍

PostGIS是PostgreSQL的一个插件，是对PostgreSQL空间数据的扩展，它增加了对地理对象的支持。

PostGIS是PostgreSQL的一个扩展，使用时，需要加载PostGIS扩展。

```sql
CREATE EXTENTION postgis;
```

### 2. 几何对象

PostGIS支持很多集合类型：点、线、多变形、复合几何体等，并提供了大量实用的相关函数。

### 3. 几何对象的输入

PostGIS支持多种空间对象的创建方式，大体上可以分为几类：

- 众所周知的文本格式(WKT，Well-Konown-Text)
- 众所周知的二进制格式(WKB，Well-Konown-Binary)
- GeoJSON等编码
- 返回几何类型的函数

例如，创建几何点(1,2)，可以通过以下四种方式，得到的结果都是一样的：

```sql
select 'Point(1 2)'::geometry AS wkt,'0101000000000000000000F03F0000000000000040'::geometry as wkb,st_geomfromgeojson('{"type":"Point","coordinates":[1,2]}') as geon_json,st_point(1, 2) AS func;
```

::geometry的作用是把格式强制转化为geometry

### 4. 几何对象的存储

在实际使用中，通常PostGIS的空间数据类型使用统一的Geometry类型，无论是点、折线还是多边形，都可以放入Geometry类型字段中。

### 5. 几何对象的输出

与几何对象的输入类似，几何对象也可以以多种方式输出，最常见的是WKT与GeoJSON

```sql
SELECT ST_AsText(geom) AS wkt, ST_AsGeoJSON(geom) AS json From city;
```

函数ST_AsText输出WKT，函数ST_AsGeoJSON输出JSON

### 6. 几何对象的运算

PostGIS提供了多种多样的关系判断与几何运算函数，功能非常强大。

#### (1) wkt和geometry的互换

函数st_astext(geom)实现geometry到wkt的转换，通过st_geomfromtext(wkt，wkid)实现wkt到geometry的转换

#### (2)通过函数st_x(geom)和st_y(geom)函数获取点对象的x和y坐标值

例如，存储了经纬度的geom数据，可以通过st_x(geom)和st_y(geom)得到对应的经度、纬度

```sql
select st_x(geom) as longitude,st_y(geom) as latitude from t_tocc_gps_current_his_20190409
```

#### (3)距离计算

通过函数st_distance(geom,geom)或者st_distance(wkt,wkt)函数计算两点的距离

两个几何点的坐标计算相对容易，但两个地理坐标之间的距离就相当复杂了，需要计算一个不规则球体上的球面距离。例如，地点A的经纬度为：(116.321367，39.966956)，地点B的经纬度为：(116.315346,39.997398)

在GIS中的坐标系有两种：一种是球坐标(地理坐标)，另一种是平面坐标(投影坐标)。投影坐标系（Projected coordinate system）是在大地坐标系（Geodectic coordinate system）的基础上，经过数学运算，把大地坐标系的曲面坐标映射到平面上产生的一种平面坐标系。

EPSG:4326，一个地理坐标系（也叫大地坐标系），用于描述WGS84坐标系，其是从1984年开始在GPS中使用的全球地理坐标系统。

**方法1：使用球坐标**，通过st_distancespheroid计算两个geometry类型的球面距离

对于geometry类型，可以使用st_distancespheroid直接用球坐标计算，在计算时会自动设置这个椭球特性（SPHEROID["Krasovsky_1940",6378245.000000,298.299997264589] ）

```sql
SELECT st_distancespheroid(ST_GeomFromText('POINT(116.321367 39.966956)', 4326),ST_GeomFromText('POINT(116.315346 39.997398)', 4326), 'SPHEROID["WGS84",6378137,298.257223563]');  
```

**方法2：使用平面坐标**，通过st_distance计算两个geometry类型的平面投影后的距离

EPSG:4527，是投影坐标系，基于国家2000大地坐标系做的高斯-克吕格3度带投影中的第39带坐标系。经度范围115.5 22.6到118.5。如果geometry值的SRID不是（高精度）目标坐标系，可以使用ST_Transform函数进行转换，转换为目标投影坐标系，再计算距离。

```sql
SELECT st_distance(st_transform(st_geometryfromtext('POINT(116.321367 39.966956)',4326),4527),st_transform(st_geometryfromtext('POINT(116.315346 39.997398)',4326),4527));
```

**方法3：使用球坐标，通过st_distance计算两个geography类型的球面距离**

```sql
SELECT st_distance(ST_GeogFromText('SRID=4326;POINT(116.321367 39.966956)'), ST_GeogFromText('SRID=4326;POINT(116.315346 39.997398)'), true);
```

#### (4) 面积计算

st_area(geometry,srid)：该函数用于计算在某个srid（某个坐标系下）一个几何对象的面积。

```sql
SELECT st_area(ST_Transform(st_geometryfromtext('POLYGON((116.4679312706 39.9482801227,116.4677961543 39.9486461337,116.4680989087 39.9486998528,116.4682182670 39.9483181633,116.4679312706 39.9482801227))',4326),4527));
```

#### (5) 几何关系

判断A是否覆盖 B ST_Covers(geometry A, geometry B)

返回两个几何对象的合并结果 ST_Union(geometry, geometry)

返回一系列几何对象的合并结果 ST_Union(geometry set)



### 参考资料

1. 《PostgreSQL实战》机械工业出版社 谭峰 张文升 编著

2. <https://www.jianshu.com/p/be5049ad8884>
3. <https://yq.aliyun.com/articles/228281>
4. <https://www.cnblogs.com/kaituorensheng/p/4647901.html>

