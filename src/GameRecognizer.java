//import javax.lang.model.type.ArrayType;
//import java.awt.geom.Point2D;
//import java.util.LinkedList;
//import java.util.ArrayList;
//
//
//public class GameRecognizer {
//    private static DollarRecognizer dollarRecognizer;
//    private static SpaceShip spaceShip;
//
//    public GameRecognizer(DollarRecognizer dollar, SpaceShip space) {
//        dollarRecognizer = dollar;
//        spaceShip = space;
//    }
//
//    public String stroke(ArrayList<Point2D> p) {
//        Result result = dollarRecognizer.recognize(p);
//        String shape = result.getName();
//
//        if (result.getName().equals("star")) {
//            spaceShip.invincible = true;
//            spaceShip.changeCraft();
//            Board.destroyAllAliens();
//        } else if (result.getName().equals("triangle")) {
//
//        }
//    }
//
//}
