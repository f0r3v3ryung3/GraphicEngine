import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

public class RayTracing {
    public static void main(String[] args) throws IOException {
        FileOutputStream out = new FileOutputStream("output.bmp");
        Scanner in = new Scanner(System.in);
        Scene scene = new Scene();
//        scene.addObject(new Cone(new Vector(-1, 1, 1), new Point(-1, 0, 5), 0.3, 2, new Color(0, 255, 255), 100, 0.5));
        int width = in.nextInt();
        int height = in.nextInt();
        int n_light = in.nextInt();
        for (int i = 0; i < n_light; i++) {
            String type_light = in.next();
            switch (type_light) {
                case "light_dir" -> {
                    Vector vec = new Vector(in.nextDouble(), in.nextDouble(), in.nextDouble());
                    double imp = in.nextDouble();
                    scene.addLight(new DirectLight(vec, imp));
                    break;
                }
                case "light_amb" -> {
//                    System.out.println(type_light);
                    double imp = in.nextDouble();
//                    System.out.println(imp);
                    scene.addLight(new AmbientLight(imp));
                    break;
                }
                case "light_point" -> {
                    Point point = new Point(in.nextDouble(), in.nextDouble(), in.nextDouble());
                    double imp = in.nextDouble();
                    scene.addLight(new PointLight(point, imp));
                    break;
                }
            }
        }

        int n_obj = in.nextInt();
        for (int i = 0; i < n_obj; i++) {
            String type_obj = in.next();
            switch (type_obj) {
                case "sphere" -> {
                    Point center = new Point(in.nextDouble(), in.nextDouble(), in.nextDouble());
                    double r = in.nextDouble();
                    Color color = new Color(in.nextInt(), in.nextInt(), in.nextInt());
                    double specular = in.nextDouble();
                    double reflective = in.nextDouble();
                    scene.addObject(new Sphere(center, r, color, specular, reflective));
                    break;
                }
                case "plane" -> {
                    Vector normal = new Vector(in.nextDouble(), in.nextDouble(), in.nextDouble());
                    Point center = new Point(in.nextDouble(), in.nextDouble(), in.nextDouble());
                    Color color = new Color(in.nextInt(), in.nextInt(), in.nextInt());
                    double specular = in.nextDouble();
                    double reflective = in.nextDouble();
                    scene.addObject(new Plane(normal, center, color, specular, reflective));
                    break;
                }
                case "square" -> {
                    double length = in.nextDouble();
                    Point center = new Point(in.nextDouble(), in.nextDouble(), in.nextDouble());
                    Vector vec_A = new Vector(in.nextDouble(), in.nextDouble(), in.nextDouble());
                    Vector vec_B = new Vector(in.nextDouble(), in.nextDouble(), in.nextDouble());
                    Color color = new Color(in.nextInt(), in.nextInt(), in.nextInt());
                    double specular = in.nextDouble();
                    double reflective = in.nextDouble();
                    scene.addObject(new Square(length, center, vec_A, vec_B, color, specular, reflective));
                    break;
                }
                case "circle" -> {
                    Vector normal = new Vector(in.nextDouble(), in.nextDouble(), in.nextDouble());
                    Point center = new Point(in.nextDouble(), in.nextDouble(), in.nextDouble());
                    double r = in.nextDouble();
                    Color color = new Color(in.nextInt(), in.nextInt(), in.nextInt());
                    double specular = in.nextDouble();
                    double reflective = in.nextDouble();
                    scene.addObject(new Circle(normal, center, r, color, specular, reflective));
                    break;
                }
                case "cone" -> {
                    Vector cone_height = new Vector(in.nextDouble(), in.nextDouble(), in.nextDouble());
                    Point center = new Point(in.nextDouble(), in.nextDouble(), in.nextDouble());
                    double angle = in.nextDouble();
                    double length = in.nextDouble();
                    Color color = new Color(in.nextInt(), in.nextInt(), in.nextInt());
                    double specular = in.nextDouble();
                    double reflective = in.nextDouble();
                    scene.addObject(new Cone(cone_height, center, angle, length, color, specular, reflective));
                    break;
                }
                case "cube" -> {
                    double length = in.nextDouble();
                    Point center = new Point(in.nextDouble(), in.nextDouble(), in.nextDouble());
                    Vector vec_A = new Vector(in.nextDouble(), in.nextDouble(), in.nextDouble());
                    Vector vec_B = new Vector(in.nextDouble(), in.nextDouble(), in.nextDouble());
                    Color color = new Color(in.nextInt(), in.nextInt(), in.nextInt());
                    double specular = in.nextDouble();
                    double reflective = in.nextDouble();
                    addCube(scene, length, center, vec_A, vec_B, color, specular, reflective);
                    break;
                }
            }
        }

        long start = System.currentTimeMillis();
        Canvas canvas = new Canvas(width, height);
        canvas.render(scene);
        long end = System.currentTimeMillis();
        float sec = (end - start) / 1000F;
        System.out.println(sec + " seconds for render");

        start = System.currentTimeMillis();
        BMP bmp = new BMP(canvas.wsc, canvas.hsc, canvas);
        bmp.save(out);
        end = System.currentTimeMillis();
        sec = (end - start) / 1000F;
        System.out.println(sec + " seconds for bmp");
        out.close();
    }
    public static void addCube(Scene scene, double length, Point center, Vector v1, Vector v2, Color color, double specular, double reflective) {
        Vector v3 = v1.mulVector(v2);
        v1.div();
        v2.div();
        v3.div();
        scene.addObject(new Square(length, new Point(center.x + v3.x * length / 2, center.y + v3.y * length / 2, center.z + v3.z * length / 2), v1, v2, color, specular, reflective));
        scene.addObject(new Square(length, new Point(center.x - v3.x * length / 2, center.y - v3.y * length / 2, center.z - v3.z * length / 2), v1, v2, color, specular, reflective));
        scene.addObject(new Square(length, new Point(center.x + v2.x * length / 2, center.y + v2.y * length / 2, center.z + v2.z * length / 2), v1, v3, color, specular, reflective));
        scene.addObject(new Square(length, new Point(center.x - v2.x * length / 2, center.y - v2.y * length / 2, center.z - v2.z * length / 2), v1, v3, color, specular, reflective));
        scene.addObject(new Square(length, new Point(center.x + v1.x * length / 2, center.y + v1.y * length / 2, center.z + v1.z * length / 2), v3, v2, color, specular, reflective));
        scene.addObject(new Square(length, new Point(center.x - v1.x * length / 2, center.y - v1.y * length / 2, center.z - v1.z * length / 2), v3, v2, color, specular, reflective));
    }
}
abstract class Object {
    abstract double dist(Vector vector, Point point);
    abstract Color Color();
    abstract double getS();
    abstract double getRF();
    abstract Vector getNormal(Point point, Vector vector);
    abstract Point intersection(Vector vector, Point point);
    public abstract String toString();
}
abstract class Light {
    abstract double intensity(Object obj, Point point, Scene scene, Vector vector);
}
class DirectLight extends Light {
    double imp, specular;
    Vector vector;
    DirectLight (Vector vector, double imp) {
        this.imp = imp;
        this.vector = vector;
    }
    double intensity(Object obj, Point point, Scene scene, Vector v) {
        Vector kek = new Vector(-vector.x, -vector.y, -vector.z);
        kek.div();
        double dist;
        for (Object f: scene.Objects) {
            if (f != null) {
                dist = f.dist(kek, point);
                if (dist > 0) return 0;
            }
        }
        Vector N = obj.getNormal(point, v);
        Vector L = new Vector(-vector.x, -vector.y, -vector.z);
        N.div();
        Vector R = L.ReflectRay(N);
        Vector V = new Vector(point, new Point(0, 0, 0));
        double Is = imp * Math.pow(Math.max(0, R.cosAngle(V)), obj.getS());
        double Ir = imp * -vector.cosAngle(obj.getNormal(point, v));
        if (R.scalar(V) >= 0) return Math.max(0, Math.max(0, Ir) + Is);
        return Math.max(0, Ir);
    }

}
class AmbientLight extends Light {
    double imp;
    AmbientLight (double imp) {
        this.imp = imp;
    }
    double intensity(Object obj, Point point,  Scene scene, Vector vector) {
        return imp;
    }

}
class PointLight extends Light {
    double imp;
    Point center;
    PointLight (Point center, double imp) {
        this.imp = imp;
        this.center = center;
    }
    double intensity(Object obj, Point point,  Scene scene, Vector v) {
        Vector kek = new Vector(point, center);
        kek.div();
        double dist;
        for (Object f: scene.Objects) {
            if (f != null) {
                dist = f.dist(kek, point);
                if (dist != -1 && dist < point.dist(center) && dist > 0) return 0;
            }
        }
        Vector N = obj.getNormal(point, v);
        Vector L = new Vector(point, center);
        N.div();
        Vector R = L.ReflectRay(N);
        Vector V = new Vector(point, new Point(0, 0, 0));
        double Is = imp * Math.pow(Math.max(0, R.cosAngle(V)), obj.getS());
        double Ir = imp * new Vector(point, center).cosAngle(obj.getNormal(point, v));
        if (R.scalar(V) > 0) return Math.max(0, Math.max(Ir, 0) + Is);
        return Math.max(0, Ir);
    }
}
class Point {
    double x, y, z;
    Point (double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    public String toString(){
        return x + " " + y + " " + z;
    }
    public double dist(Point p) {
        return Math.pow(Math.pow(x - p.x, 2) + Math.pow(y - p.y, 2) + Math.pow(z - p.z, 2), 0.5);
    }
}
class Vector {
    double x, y;
    double z = 1;
    Vector (double x, double y, double wsc, double hsc) {
        this.x = (x / wsc) - 0.5;
        this.y = (0.5 - y / hsc);
    }
    Vector (double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    Vector (double x, double y, double z, double x1, double y1, double z1) {
        this.x = x1 - x;
        this.y = y1 - y;
        this.z = z1 - z;
    }
    Vector (Point v1, Point v2) {
        this.x = v2.x - v1.x;
        this.y = v2.y - v1.y;
        this.z = v2.z - v1.z;
    }
    public double scalar(Vector vector) {
        return x * vector.x + y * vector.y + z * vector.z;
    }
    public double cosAngle(Vector vector) {
        return scalar(vector) / (dist() * vector.dist());
    }

    public double sinAngle(Vector vector) {
        return Math.pow(1 - Math.pow(cosAngle(vector), 2), 0.5);
    }
    public double dist(){
        return Math.pow(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2), 0.5);
    }
    public void div() {
        double dist = dist();
        x /= dist;
        y /= dist;
        z /= dist;
    }
    public Vector ReflectRay (Vector N) {
        return new Vector(2 * N.x * scalar(N) - x, 2 * N.y * scalar(N) - y, 2 * N.z * scalar(N) - z);
    }
    public String toString() {
        return x + " " + y + " " + z;
    }
    public Vector mulVector(Vector vector) {
        return new Vector(y * vector.z - z * vector.y, z * vector.x - x * vector.z, x * vector.y - y * vector.x);
    }
}
class Ray {
    Vector vector;
    Point point;
    Ray(Vector vector, Point point) {
        this.point = point;
        this.vector = vector;
    }
    public Color takeColor(double depth, Scene scene) {
        if (depth > 5) return null;
        Color color = null;
        double intensity;
        double intensityRes = 0;
        double dist = 2 << 10;
        Object lastObject = null;
        for (Object Object: scene.Objects) {
            intensity = 0;
            if (Object != null) {
                if (Object.dist(vector, point) >= 0) {
                    if (Object.dist(vector, point) < dist) {
                        Point pointInt = Object.intersection(vector, point);
                        for (Light light: scene.lights) {
                            if (light != null) {
                                intensity += light.intensity(Object, pointInt, scene, vector);
                            }
                        }
                        dist = Object.dist(vector, point);
                        color = new Color(Object.Color());
                        intensityRes = intensity;
                        lastObject = Object;
                    }
                }
            }
        }
        if (color == null) return null;
        Vector N = lastObject.getNormal(lastObject.intersection(vector, point), vector);
        Vector L = new Vector(-vector.x, -vector.y, -vector.z);
        N.div();
        Vector R = L.ReflectRay(N);
        color.intensity(intensityRes * (1 - lastObject.getRF()));
        Color color1 = new Ray(R, lastObject.intersection(vector, point)).takeColor(depth + 1, scene);
        if (color1 == null || lastObject.getRF() <= 0) return color;
        color1.intensity(lastObject.getRF());
        return color.sumColor(color1);
    }
}
class Sphere extends Object {
    double r, specular, xc, yc, zc, reflective;
    Point center;
    Color Color;
    Sphere (Point center, double r, Color Color, double specular, double reflective){
        this.r = r;
        this.reflective = reflective;
        this.specular = specular;
        this.center = center;
        this.Color = Color;
        xc = center.x;
        yc = center.y;
        this.zc = center.z;
    }
    public double dist(Vector vector, Point point) {
        double x1 = xc - point.x, y1 = yc - point.y, z1 = zc - point.z;
        double a = Math.pow(vector.x, 2) + Math.pow(vector.y, 2) + Math.pow(vector.z, 2);
        double b = -2 * (x1 * vector.x + y1 * vector.y + z1 * vector.z);
        double c = Math.pow(x1, 2) + Math.pow(y1, 2) + Math.pow(z1, 2) - Math.pow(r, 2);
        double D = Math.pow(b, 2) - 4 * a * c;
        if (D < 0) {
            return -1;
        }
        return vector.dist() * (-b - Math.pow(D, 0.5)) / (2 * a);
    }
    public Point intersection(Vector vector, Point point) {
        double dist = dist(vector, point);
        double dist1 = vector.dist();
        return new Point(vector.x * dist / dist1 + point.x, vector.y * dist / dist1 + point.y, vector.z * dist / dist1 + point.z);
    }
    public Color Color() {
        return Color;
    }
    public Vector getNormal(Point point, Vector vector) {
        return new Vector(center, point);
    }
    public double getS() {
        return specular;
    }
    public double getRF() {
        return reflective;
    }
    public String toString() {
        return Color + " ";
    }
}
class Plane extends Object {
    double specular, reflective;
    Vector A, B, N;
    Point center;
    Color color;
    Plane(Vector A, Vector B, Point center, Color color, double specular, double reflective) {
        this.A = A;
        this.B = B;
        this.center = center;
        this.specular = specular;
        this.reflective = reflective;
        this.color = color;
        A.div();
        B.div();
        N = A.mulVector(B);
    }
    Plane(Vector N, Point center, Color color, double specular, double reflective) {
        this.center = center;
        this.specular = specular;
        this.reflective = reflective;
        this.color = color;
        this.N = N;
        this.A = new Vector(center, new Point(center.x + 1 + N.x * new Vector(1, 0, 0).sinAngle(N), center. y + N.y * new Vector(1, 0, 0).sinAngle(N), center.z + N.z * new Vector(1, 0, 0).sinAngle(N)));
        this.B = new Vector(center, new Point(center.x + N.x * new Vector(0, 1, 0).sinAngle(N), center. y + 1 + N.y * new Vector(0, 1, 0).sinAngle(N), center.z + N.z * new Vector(0, 1, 0).sinAngle(N)));
    }
    public double dist(Vector vector, Point point) {
        double t = -((point.x - center.x) * N.x + (point.y - center.y) * N.y + (point.z - center.z) * N.z) / (vector.x * N.x + vector.y * N.y + vector.z * N.z);
        if (t <= Math.pow(10, -6)) return -1;
        return t * vector.dist();
    }
    public Color Color() {
        return color;
    }
    public double getS() {
        return specular;
    }
    public double getRF() {
        return reflective;
    }
    public String toString() {
        return A + "; " + B + "; " + center;
    }
    public Point intersection(Vector vector, Point point) {
        double dist = this.dist(vector, point);
        double dist1 = vector.dist();
        return new Point(vector.x * dist / dist1 + point.x, vector.y * dist / dist1 + point.y, vector.z * dist / dist1 + point.z);
    }
    public Vector getNormal(Point point, Vector vector) {
        if (N.scalar(vector) > 0) return new Vector(-N.x, -N.y, -N.z);
        return N;
    }
}
class Square extends Object {
    double specular, reflective;
    Point A, B, C, D;
    Plane plane;
    Color color;
    Square(Point A, Point B, Point C, Point D, Color color, double specular, double reflective) {
        this.color = color;
        this.specular = specular;
        this.reflective = reflective;
        this.A = A;
        this.B = B;
        this.C = C;
        this.D = D;
        plane = new Plane(new Vector(A, B), new Vector(A, C), A, color, specular, reflective);
    }
    Square(double length, Point center, Vector v1, Vector v2, Color color, double specular, double reflective) {
        plane = new Plane(v1, v2, center, color, specular, reflective);
        Vector vector1 = new Vector(v1.x + v2.x, v1.y + v2.y, v1.z + v2.z);
        Vector vector2 = new Vector(v1.x - v2.x, v1.y - v2.y, v1.z - v2.z);
        vector1.div();
        vector2.div();
        this.color = color;
        this.specular = specular;
        this.reflective = reflective;
        A = new Point(center.x + vector1.x * length / Math.pow(2, 0.5), center.y + vector1.y * length / Math.pow(2, 0.5), center.z + vector1.z * length / Math.pow(2, 0.5));
        B = new Point(center.x + vector2.x * length / Math.pow(2, 0.5), center.y + vector2.y * length / Math.pow(2, 0.5), center.z + vector2.z * length / Math.pow(2, 0.5));
        C = new Point(center.x - vector1.x * length / Math.pow(2, 0.5), center.y - vector1.y * length / Math.pow(2, 0.5), center.z - vector1.z * length / Math.pow(2, 0.5));
        D = new Point(center.x - vector2.x * length / Math.pow(2, 0.5), center.y - vector2.y * length / Math.pow(2, 0.5), center.z - vector2.z * length / Math.pow(2, 0.5));
    }
    double dist(Vector vector, Point point) {
        Point P = intersection(vector, point);
        if (new Vector(A, B).scalar(new Vector(A, P)) >= 0 && new Vector(B, C).scalar(new Vector(B, P)) >= 0 && new Vector(C, D).scalar(new Vector(C, P)) >= 0 && new Vector(D, A).scalar(new Vector(D, P)) >= 0) {
            return plane.dist(vector, point);
        }
        return -1;
    }
    Color Color() {
        return color;
    }
    double getS() {
        return specular;
    }
    double getRF() {
        return reflective;
    }
    public String toString() {
        return A + "; " + B + "; " + C + "; " + D;
    }
    public Point intersection(Vector vector, Point point) {
        return plane.intersection(vector, point);
    }
    public Vector getNormal(Point point, Vector vector) {
        return plane.getNormal(point, vector);
    }
}
class Circle extends Plane {
    double r;
    Circle(Vector A, Vector B, Point center, double r, Color color, double specular, double reflective) {
        super(A, B, center, color, specular, reflective);
        this.r = r;
    }
    Circle(Vector N, Point center, double r, Color color, double specular, double reflective) {
        super(N, center, color, specular, reflective);
        this.r = r;
    }
    public double dist(Vector vector, Point point) {
        if (dist1(vector, point) == -1) return -1;
        Point inter = intersection(vector, point);
        if (inter.dist(center) <= r)
            return inter.dist(point);
        return -1;
    }
    public double dist1(Vector vector, Point point) {
        double t = -((point.x - center.x) * N.x + (point.y - center.y) * N.y + (point.z - center.z) * N.z) / (vector.x * N.x + vector.y * N.y + vector.z * N.z);
        if (t <= Math.pow(10, -6)) return -1;
        return t * vector.dist();
    }
    public Point intersection(Vector vector, Point point) {
        double dist = this.dist1(vector, point);
        double dist1 = vector.dist();
        return new Point(vector.x * dist / dist1 + point.x, vector.y * dist / dist1 + point.y, vector.z * dist / dist1 + point.z);
    }
}
class Cone extends Object {
    double specular, reflective, angle, len;
    Point center;
    Vector V;
    Color color;
    Cone (Vector V, Point center, double angle, double len, Color color, double specular, double reflective) {
        this.V = V;
        this.center = center;
        this.specular = specular;
        this.reflective = reflective;
        this.len = len;
        this.angle = angle;
        this.color = color;
        V.div();
    }
    double dist(Vector vector, Point point) {
        vector.div();
        Vector CO = new Vector(center, point);
        double a = Math.pow(vector.scalar(V), 2) - Math.pow(Math.cos(angle), 2);
        double b = 2 * (vector.scalar(V) * CO.scalar(V) - vector.scalar(CO) * Math.pow(Math.cos(angle), 2));
        double c = Math.pow(CO.scalar(V), 2) - CO.scalar(CO) * Math.pow(Math.cos(angle), 2);
        double D = Math.pow(b, 2) - 4 * a * c;
        double t1 = vector.dist() * (-b - Math.pow(D, 0.5)) / (2 * a);
        double t2 = vector.dist() * (-b + Math.pow(D, 0.5)) / (2 * a);
        double t = Math.min(t1, t2);
        if (D < 0 || t1 < Math.pow(10, -6) || t2 < Math.pow(10, -6)) {
            return -1;
        }
        double dist1 = vector.dist();
        Point p = new Point(vector.x * t / dist1 + point.x, vector.y * t / dist1 + point.y, vector.z * t / dist1 + point.z);
        if (new Vector(center, p).scalar(V) < 0 || center.dist(p) > len) {
            return -1;
        }
        return t * vector.dist();
    }
    Color Color() {return color;}
    double getS() {return specular;}
    double getRF() {return reflective;}
    public String toString() {return null;}
    public Point intersection(Vector vector, Point point) {
        double dist = dist(vector, point);
        double dist1 = vector.dist();
        return new Point(vector.x * dist / dist1 + point.x, vector.y * dist / dist1 + point.y, vector.z * dist / dist1 + point.z);
    }
    public Vector getNormal(Point point, Vector vector) {
        double dist = center.dist(point) / Math.cos(angle);
        return new Vector(new Point(center.x + V.x * dist, center.y + V.y * dist, center.z + V.z * dist), point);
    }
}
class Color {
    int x1;
    int x2;
    int x3;
    Color (int x1, int x2, int x3) {
        this.x1 = x1;
        this.x2 = x2;
        this.x3 = x3;
    }
    Color (Color Color) {
        this.x1 = Color.x1;
        this.x2 = Color.x2;
        this.x3 = Color.x3;
    }
    void intensity (double i) {
        x1 = (int) Math.min(i * x1, 255);
        x2 = (int) Math.min(i * x2, 255);
        x3 = (int) Math.min(i * x3, 255);
    }
    Color sumColor(Color Color) {
        return new Color(Math.min(Color.x1 + x1, 255), Math.min(Color.x2 + x2, 255), Math.min(Color.x3 + x3, 255));
    }

    public String toString() {
        return x1 + " " + x2 + " " + x3;
    }
}
class BMP {
    int size;
    byte[] bmp;
    BMP(int b, int a, Canvas canvas) {
        this.size = 54 + (3 * b + (4 - (3 * b) % 4) % 4) * a;
        this.bmp = new byte[this.size];

        bmp[0] = 66;
        bmp[1] = 77;
        redact(this.size, bmp, 2);
        bmp[10] = 54;
        bmp[14] = 40;
        redact(b, bmp, 18);
        redact(a, bmp, 22);
        bmp[26] = 1;
        bmp[28] = 24;

        int pos = 54;
        for (int i = 0; i < a; i++) {
            for (int j = 0; j < b; j++) {
                bmp[pos] = (byte) canvas.canvas[a - 1 - i][j][0];
                bmp[pos + 1] = (byte) canvas.canvas[a - 1 - i][j][1];
                bmp[pos + 2] = (byte) canvas.canvas[a - 1 - i][j][2];
                pos += 3;
            }
            pos += (4 - (3 * b) % 4) % 4;
        }
    }
    public static void redact(int v, byte[] bmp, int pos) {
        for (int i = 0; i < 4; i++) {
            bmp[pos + i] = (byte) (v % 256);
            v /= 256;
        }
    }

    public void save(FileOutputStream out) throws IOException {
        out.write(bmp);
    }
}
class Scene {
    Object[] Objects = new Object[100];
    Light[] lights = new Light[100];
    int i = 0, j = 0;
    void addObject(Object Object) {
        if (i < 100) {
            Objects[i] = Object;
            i++;
        } else System.out.println("Too much Objects");
    }
    void addLight(Light light) {
        if (j < 100) {
            lights[j] = light;
            j++;
        } else System.out.println("Too much Lights");
    }
}
class Canvas {
    int[][][] canvas;
    int wsc, hsc;
    Canvas(int a, int b) {
        this.wsc = a;
        this.hsc = b;
        canvas = new int[b][a][3];
        for (int i = 0; i < canvas.length; i++) {
            for (int j = 0; j < canvas[i].length; j++) {
                for (int k = 0; k < 3; k++) {
                    canvas[i][j][k] = 0;
                }
            }
        }
    }
    public void render (Scene scene) {
       for (int x = 0; x < wsc; x++) {
             for (int y = 0; y < hsc; y++) {
                Color color = new Ray(new Vector(x, y, wsc, hsc), new Point(0, 0, 0)).takeColor(2, scene);
                if (color == null) color = new Color(0, 0, 0);
                draw_point(x, y, color);
            }
        }
    }
    public void draw_point(int x, int y, Color Color) {
        canvas[y][x][0] = Math.min(255, Color.x3);
        canvas[y][x][1] = Math.min(255, Color.x2);
        canvas[y][x][2] = Math.min(255, Color.x1);
    }
}
