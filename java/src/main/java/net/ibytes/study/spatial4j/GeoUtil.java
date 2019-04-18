package net.ibytes.study.spatial4j;

import org.locationtech.jts.geom.Geometry;
import org.locationtech.spatial4j.context.jts.JtsSpatialContext;
import org.locationtech.spatial4j.distance.DistanceUtils;
import org.locationtech.spatial4j.shape.Circle;

/**
 * @author dingfurong
 * @date 2019/4/18
 * @description Spatial4j工具类
 */
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
