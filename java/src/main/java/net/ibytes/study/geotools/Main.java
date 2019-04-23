package net.ibytes.study.geotools;

import com.vividsolutions.jts.geom.Coordinate;
import org.geotools.geometry.jts.JTS;
import org.geotools.referencing.CRS;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.opengis.referencing.operation.MathTransform;

/**
 * @author dingfurong
 * @date 2019/4/22
 * @description
 */
public class Main {
    public static void main(String[] args)  throws Exception {
        CoordinateReferenceSystem targetCRS = CRS.decode("EPSG:4326");
        CoordinateReferenceSystem sourceCRS = CRS.decode("DefaultGeographicCRS.WGS84");
        double coordinateX = 1307285;
        double coordinateY = 2229260;
        Coordinate in = new Coordinate(coordinateX, coordinateY);
        Coordinate out = in;

        MathTransform transform = CRS.findMathTransform(sourceCRS, targetCRS);
        Coordinate result = JTS.transform(in, out, transform);
        result.toString();
        System.out.println(result);
    }

}
