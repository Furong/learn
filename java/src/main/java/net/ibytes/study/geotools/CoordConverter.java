package net.ibytes.study.geotools;

import org.geotools.geometry.jts.JTS;
import org.geotools.referencing.CRS;
import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.opengis.geometry.MismatchedDimensionException;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.opengis.referencing.operation.MathTransform;
import org.opengis.referencing.operation.TransformException;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;

/**
 * Hello world!
 *
 */
public class CoordConverter 
{
    public static double[] convert(double lon, double lat) 
            throws FactoryException, MismatchedDimensionException, TransformException {
        // 传入原始的经纬度坐标
        Coordinate sourceCoord = new Coordinate(lon, lat);
        GeometryFactory geoFactory = new GeometryFactory();
        Point sourcePoint = geoFactory.createPoint(sourceCoord);

        // 这里是以OGC WKT形式定义的是World Mercator投影，网页地图一般使用该投影
        final String strWKTMercator = "PROJCS[\"World_Mercator\","
                + "GEOGCS[\"GCS_WGS_1984\","
                + "DATUM[\"WGS_1984\","
                + "SPHEROID[\"WGS_1984\",6378137,298.257223563]],"
                + "PRIMEM[\"Greenwich\",0],"
                + "UNIT[\"Degree\",0.017453292519943295]],"
                + "PROJECTION[\"Mercator_1SP\"],"
                + "PARAMETER[\"False_Easting\",0],"
                + "PARAMETER[\"False_Northing\",0],"
                + "PARAMETER[\"Central_Meridian\",0],"
                + "PARAMETER[\"latitude_of_origin\",0],"
                + "UNIT[\"Meter\",1]]";
        CoordinateReferenceSystem mercatroCRS = CRS.parseWKT(strWKTMercator);
        // 做投影转换，将WCG84坐标转换成世界墨卡托投影转
        MathTransform transform = CRS.findMathTransform(DefaultGeographicCRS.WGS84, mercatroCRS);
        Point targetPoint = (Point) JTS.transform(sourcePoint, transform);

        // 返回转换以后的X和Y坐标
        double[] targetCoord = {targetPoint.getX(), targetPoint.getY()};
        return targetCoord;
    }

    // 将目标投影坐标系作为参数输入，其实和第一个程序类似，我懒得提取公共部分再抽取函数了
    public static double[] convert(double lon, double lat, String strWKT) 
            throws FactoryException, MismatchedDimensionException, TransformException {
        Coordinate sourceCoord = new Coordinate(lon, lat);
        GeometryFactory geoFactory = new GeometryFactory();
        Point sourcePoint = geoFactory.createPoint(sourceCoord);

        CoordinateReferenceSystem mercatroCRS = CRS.parseWKT(strWKT);
        MathTransform transform = CRS.findMathTransform(DefaultGeographicCRS.WGS84, mercatroCRS);
        Point targetPoint = (Point) JTS.transform(sourcePoint, transform);

        double[] targetCoord = {targetPoint.getX(), targetPoint.getY()};
        return targetCoord;
    }

    // main函数进行验证
    public static void main( String[] args ) throws Exception
    {
        double longitude = 0;
        double latitude = 0;
        double[] coordinate = convert(longitude, latitude);

        System.out.println("X: " + coordinate[0] + ", Y: " + coordinate[1]);
    }
}
