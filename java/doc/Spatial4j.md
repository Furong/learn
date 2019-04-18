## Spatial4j介绍

### 1. 介绍

Spatial4j是一个通用的空间和地理空间数据处理的开源Java库，它属于LocationTech组织，使用**ASL**开源许可协议。

它的三个核心功能是：

+ 提供常见的地理空间图形

+ 提供距离以及其他数学计算

+ 读取和写入文本格式的图形

#### 特征

Shape类具有地理空间特征

+ 包括Point，Rectangle，Circle，Polygon(via JTS)
+ 图形相交逻辑，以及相离、包含、重叠等
+ 图形的边界
+ 面积计算

距离计算

+ 球面的：球面余弦公式，Haversine公式，Vincenty公式

支持WKT和GeoJSON的图形创建和输出，并且与Jackson-databind集成；

与JTS集成，使JTS中的图形适配Spatial4j的接口；

#### 为什么不使用JTS？

JTS是最常用的Java空间数据处理库。JTS功能强大，但是它只支持欧几里得几何图形，不支持地理空间集合对象，而且没有圆形。Spatial4j有地理的圆实现，而且用jts的gemotery封装。

### 2. 实践

SpatialContext提供了访问Spatial4j的接口。它充当所有图形的工厂类，并且包含一些方便使用的方法。例如，如果想计算距离，可以得到DistanceCalcutor类。

为了得到SpatialContext，需要使用全局单例SpatialContext.GEO或者JtsSpatialContext.GEO，它们都使用地理的球面来计算，Jts支持图形。

例子如下：以某个经纬度点为圆心，500米为半径画圆形，并计算圆形的面积

```java
public class GeoUtil {
    /**
     * 获取Context
     * @return
     */
    private static JtsSpatialContext getJtsContext(){
        return JtsSpatialContext.GEO;
    }
    
    /**
     * 根据经度、纬度，半径画圆形
     * @param lon
     * @param lat
     * @param circleRadius
     * @return
     */
    public static Geometry createCircle(double lon,double lat,double circleRadius){
        Circle circle = getJtsContext().getShapeFactory()
                .circle(lon, lat, DistanceUtils.dist2Degrees(circleRadius / 1000, DistanceUtils.EARTH_MEAN_RADIUS_KM));
        return  getJtsContext().getShapeFactory().getGeometryFrom(circle);
    }

    public static void main(String[] args){
        //经纬度位置
        double lon = 122.106398879331;
        double lat = 30.1212294078562;
        double radis = 500.0;
        Geometry circle = createCircle(lon,lat,radis);
        //求面积
        double area = circle.getArea();
    }
}

```

pom.xml需要引入的依赖

```xml
<!-- https://mvnrepository.com/artifact/org.locationtech.jts/jts-core -->
<dependency>
    <groupId>org.locationtech.jts</groupId>
    <artifactId>jts-core</artifactId>
    <version>1.16.0</version>
</dependency>
        
<!-- https://mvnrepository.com/artifact/org.locationtech.spatial4j/spatial4j -->
<dependency>
    <groupId>org.locationtech.spatial4j</groupId>
    <artifactId>spatial4j</artifactId>
    <version>0.7</version>
</dependency>
```

### 3. 其他实践

**lucene**使用了spatial4j工具包来计算地理空间距离；


### 参考链接

<https://projects.eclipse.org/projects/locationtech.spatial4j>

<https://github.com/locationtech/spatial4j>

