## jts空间分析工具包

### 1. 说明

这个包貌似是用于几何计算的

### 2. 常用类

+ com.vividsolutions.jts.geom 

  **Class Polygon**

  ```
  Represents a polygon with linear edges, which may include holes. The outer boundary (shell) and inner boundaries (holes) of the polygon are represented by LinearRings. The boundary rings of the polygon may have any orientation. Polygons are closed, simple geometries by definition.
  表示多边形，可能有洞，所以有外边界，内边界，总之，它是一个闭合曲线。
  ```

+ com.vividsolutions.jts.geom 

  **Class Geometry**

  ```
  A representation of a planar, linear vector geometry. 表示平面、线的几何向量,即空间对象。
  ```

+ com.vividsolutions.jts.io

  **Class WKTReader**

  ```
  Converts a geometry in Well-Known Text format to a Geometry.
  
  WKTReader supports extracting Geometry objects from either Readers or Strings. This allows it to function as a parser to read Geometry objects from text blocks embedded in other data formats (e.g. XML).
  
  A WKTReader is parameterized by a GeometryFactory, to allow it to create Geometry objects of the appropriate implementation. In particular, the GeometryFactory determines the PrecisionModel and SRID that is used.
  
  The WKTReader converts all input numbers to the precise internal representation.
  
  将文本格式的空间对象表示转换为Geometry空间对象
  ```

  



### 参考文档

1. <http://javadox.com/com.vividsolutions/jts/1.13/com/vividsolutions/jts/geom/package-summary.html>

   

