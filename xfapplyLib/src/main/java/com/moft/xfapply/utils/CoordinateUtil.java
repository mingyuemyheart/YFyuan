package com.moft.xfapply.utils;

import com.baidu.mapapi.model.LatLng;
import com.moft.xfapply.model.common.Coordinate;

import java.util.ArrayList;
import java.util.List;

/*
 * 坐标
 */
public class CoordinateUtil {

	public static CoordinateUtil mInstance;

	public static CoordinateUtil getInstance() {
		if (mInstance == null) {
			mInstance = new CoordinateUtil();
		}
		return mInstance;
	}

	public Coordinate WGS84_Convert_BD09(Coordinate c) {
		return (new WGS84_BD09()).w2b(c);
	}

	public Coordinate BD09_Convert_WGS84(Coordinate c) {
		return (new WGS84_BD09()).b2w(c);
	}

    public Coordinate BD09_Convert_GCJ02(Coordinate c) {
        return (new GCJ02_BD09()).b2g(c);
    }
    
    public double getDistance(ArrayList<LatLng> points) {
        double totalDis = 0;
        if (points.size() < 2) {
            return totalDis;
        }

        LLPoint[] pts = new LLPoint[points.size()];
        for (int i = 0; i < pts.length; i++) {
            LLPoint pt = new LLPoint();
            pt.lng = points.get(i).longitude;
            pt.lat = points.get(i).latitude;
            pts[i] = pt;
        }
        
        for (int i = 0; i < pts.length; i++) {
            totalDis += getDisTwo(pts[i].lng, pts[i].lat, pts[i+1].lng, pts[i+1].lat);
            if ((i+1) == pts.length-1) {
                break;
            }
        }

        return totalDis;
    }
    
    public double getDisTwo(double longitude1, double latitude1,
            double longitude2, double latitude2) {
        double s = 0;
        double EARTH_RADIUS = 6370986.0;  // 为了精度 将赤道半径 6378137 改为地球平均半径 6370986.0
        double Lat1 = rad(latitude1);
        double Lat2 = rad(latitude2);
        double a = Lat1 - Lat2;
        double b = rad(longitude1) - rad(longitude2);
        s = 2 * Math.asin(
                Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(Lat1)
                        * Math.cos(Lat2) * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        
        return s;
    }

    public double rad(double d) {
        return d * Math.PI / 180.0;
    }

    // 使用openlayers 提供的算法计算面积  2019-8-29 start
    public double getAreaBL(ArrayList<LatLng> points) {
        double totalArea = 0;// 初始化总面积

        if (points.size() < 3) {
            return totalArea;
        }

        double Radius = 6370986.0;   // 为了精度 将赤道半径 6378137 改为地球平均半径 6370986.0
        List<Coordinate> wgs84s = new ArrayList<Coordinate>();
        Coordinate c = new Coordinate();

        for (LatLng ll : points) {
            c.setLat(ll.latitude);
            c.setLon(ll.longitude);
            wgs84s.add(BD09_Convert_WGS84(c));
        }

        int r = wgs84s.size();
        double e = wgs84s.get(r-1).getLon();
        double o = wgs84s.get(r-1).getLat();
        double s = 0;
        for (int i = 0; i < r; i++) {
            double a = wgs84s.get(i).getLon();
            double h = wgs84s.get(i).getLat();
            s += St(a - e) * (2 + Math.sin(St(o)) + Math.sin(St(h)));
            e = a;
            o = h;
        }

        totalArea = Math.abs(s * Radius * Radius / 2);
        return totalArea;
    }

    private double St(double t) {
        return t * Math.PI / 180;
    }
    // 使用openlayers 提供的算法计算面积  2019-8-29 end

	private class GCJ02_BD09 {
		/*
		 * 国测局坐标转为百度坐标
		 */
		public Coordinate g2b(Coordinate gg) {
			Coordinate bd = new Coordinate();
			double x = gg.getLon(), y = gg.getLat();
			double z = Math.sqrt(x * x + y * y) + 0.00002
					* Math.sin(y * Coordinate.x_pi);
			double theta = Math.atan2(y, x) + 0.000003
					* Math.cos(x * Coordinate.x_pi);
			double longitude = z * Math.cos(theta) + 0.0065;
			double latitude = z * Math.sin(theta) + 0.006;

			bd.setLon(longitude);
			bd.setLat(latitude);

			return bd;
		}

		/*
		 * 百度坐标转为国测局坐标
		 */
		public Coordinate b2g(Coordinate bd) {
			Coordinate gg = new Coordinate();
			double x = bd.getLon() - 0.0065, y = bd.getLat() - 0.006;
			double z = Math.sqrt(x * x + y * y) - 0.00002
					* Math.sin(y * Coordinate.x_pi);
			double theta = Math.atan2(y, x) - 0.000003
					* Math.cos(x * Coordinate.x_pi);
			double gg_lon = z * Math.cos(theta);
			double gg_lat = z * Math.sin(theta);

			gg.setLon(gg_lon);
			gg.setLat(gg_lat);

			return gg;
		}
	}

	private class WGS84_BD09 {
		private WGS84_GCJ02 wg = new WGS84_GCJ02();
		private GCJ02_BD09 gb = new GCJ02_BD09();

		/*
		 * 地球84坐标转为百度坐标
		 */
		public Coordinate w2b(Coordinate ww) {
			Coordinate bb = new Coordinate();

			bb = gb.g2b(wg.w2g(ww));

			return bb;
		}

		/*
		 * 百度坐标转为地球84坐标
		 */
		public Coordinate b2w(Coordinate bb) {
			Coordinate ww = new Coordinate();

			ww = wg.g2w(gb.b2g(bb));

			return ww;
		}

	}

	private class WGS84_GCJ02 {
		public final static double a = 6378245.0;
		public final static double ee = 0.00669342162296594323;

		/*
		 * 地球84坐标转为火星（国测局）坐标
		 */
		public Coordinate w2g(Coordinate wg) {
			Coordinate mg = new Coordinate();
			double wgLat = wg.getLat();
			double wgLon = wg.getLon();

			double mgLat, mgLon;
			if (outOfChina(wgLat, wgLon)) {
				mgLat = wgLat;
				mgLon = wgLon;
				mg.setLat(mgLat);
				mg.setLon(mgLon);

				return mg;
			}
			double dLat = transformLat(wgLon - 105.0, wgLat - 35.0);
			double dLon = transformLon(wgLon - 105.0, wgLat - 35.0);
			double radLat = wgLat / 180.0 * Coordinate.pi;
			double magic = Math.sin(radLat);
			magic = 1 - ee * magic * magic;
			double sqrtMagic = Math.sqrt(magic);
			dLat = (dLat * 180.0)
					/ ((a * (1 - ee)) / (magic * sqrtMagic) * Coordinate.pi);
			dLon = (dLon * 180.0)
					/ (a / sqrtMagic * Math.cos(radLat) * Coordinate.pi);
			mgLat = wgLat + dLat;
			mgLon = wgLon + dLon;

			mg.setLat(mgLat);
			mg.setLon(mgLon);

			return mg;
		}

		/*
		 * 火星（国测局）坐标转换为地球84坐标 粗略计算
		 */
		public Coordinate g2w(Coordinate mg) {
			Coordinate wg = new Coordinate();

			double mgLat = mg.getLat();
			double mgLon = mg.getLon();

			double wgLat, wgLon;
			double dLat, dLon;
			wg = w2g(mg);
			dLat = wg.getLat() - mgLat;
			dLon = wg.getLon() - mgLon;
			wgLat = mgLat - dLat;
			wgLon = mgLon - dLon;
			wg.setLat(wgLat);
			wg.setLon(wgLon);

			return wg;
		}

		private boolean outOfChina(double lat, double lon) {
			if (lon < 72.004 || lon > 137.8347)
				return true;
			if (lat < 0.8293 || lat > 55.8271)
				return true;
			return false;
		}

		private double transformLat(double x, double y) {
			double ret = -100.0 + 2.0 * x + 3.0 * y + 0.2 * y * y + 0.1 * x * y
					+ 0.2 * Math.sqrt(Math.abs(x));
			ret += (20.0 * Math.sin(6.0 * x * Coordinate.pi) + 20.0 * Math
					.sin(2.0 * x * Coordinate.pi)) * 2.0 / 3.0;
			ret += (20.0 * Math.sin(y * Coordinate.pi) + 40.0 * Math.sin(y
					/ 3.0 * Coordinate.pi)) * 2.0 / 3.0;
			ret += (160.0 * Math.sin(y / 12.0 * Coordinate.pi) + 320 * Math
					.sin(y * Coordinate.pi / 30.0)) * 2.0 / 3.0;
			return ret;
		}

		private double transformLon(double x, double y) {
			double ret = 300.0 + x + 2.0 * y + 0.1 * x * x + 0.1 * x * y + 0.1
					* Math.sqrt(Math.abs(x));
			ret += (20.0 * Math.sin(6.0 * x * Coordinate.pi) + 20.0 * Math
					.sin(2.0 * x * Coordinate.pi)) * 2.0 / 3.0;
			ret += (20.0 * Math.sin(x * Coordinate.pi) + 40.0 * Math.sin(x
					/ 3.0 * Coordinate.pi)) * 2.0 / 3.0;
			ret += (150.0 * Math.sin(x / 12.0 * Coordinate.pi) + 300.0 * Math
					.sin(x / 30.0 * Coordinate.pi)) * 2.0 / 3.0;
			return ret;
		}
	}

    private static class LLPoint {
        public double lng;
        public double lat;

        public LLPoint() {
            lng = 0;
            lat = 0;
        }
    }
    
    public double getLatSub() {
        return 1/(2*Math.PI*6356.9088/360);
    }
    
    public double getLngSub(double lat) {
        return 1/((2*Math.PI*6377.83/360)*Math.cos(Math.PI*(lat)/180));
    }

    public static double[] calLocation(double angle, double startLong, double startLat, double distance){
        double[] result = new double[2];
        double bb = 111319.5555;

        double lat = startLat + (distance * Math.cos(angle* Math.PI / 180)) / bb;
        double lon = startLong + (distance * Math.sin(angle* Math.PI / 180)) / (bb * Math.cos(startLat * Math.PI / 180));

        result[0] = lat;
        result[1] = lon;
        return result;
    }
}
