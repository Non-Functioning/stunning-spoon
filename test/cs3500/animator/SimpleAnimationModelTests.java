package cs3500.animator;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import cs3500.animator.model.AnimatedShape;
import cs3500.animator.model.Animations;
import cs3500.animator.model.Position2D;
import cs3500.animator.model.RGB;
import cs3500.animator.model.SimpleAnimation;

import static org.junit.Assert.assertEquals;

public class SimpleAnimationModelTests {
  @Test
  public void animatedShapeToStringTest() {
    RGB color1 = new RGB(0.0,1.0,0.0);
    Position2D initial = new Position2D(10.0, 4.0);
    List<Double> params = new ArrayList<>();
    params.add(6.0);
    params.add(8.0);
    AnimatedShape shape1 = new AnimatedShape("Shape1", AnimatedShape.ShapeType.RECTANGLE, color1,
            initial, params, 10, 100);
    assertEquals("Name: Shape1\nType: rectangle\nLower-left corner: (10.000000, 4.000000), Width: "
            + "6.0, Height: 8.0, Color: (0.000000, 1.000000, 0.000000)\nAppears at t=10\n"
            + "Disappears at t=100", shape1.toString());
  }

  @Test
  public void createShapeTest() {
    SimpleAnimation anime = new SimpleAnimation();
    anime.createShape("My Shape", AnimatedShape.ShapeType.CIRCLE, new RGB(0.0,0.0,1.0),
            new Position2D(573.04, 42.2493), Collections.singletonList(5.0), 0, 50);
    assertEquals("Name: My Shape\nType: circle\nCenter: (573.040000, 42.249300), Radius: "
            + "5.0, Color: (0.000000, 0.000000, 1.000000)\nAppears at t=0\n"
            + "Disappears at t=50", anime.getShapeStatus(0));
  }

  @Test
  public void moveShapeTest() {
    SimpleAnimation anime = new SimpleAnimation();
    anime.createShape("R", AnimatedShape.ShapeType.SQUARE, new RGB(0.0,0.0,1.0),
            new Position2D(573.04, 42.2493), Collections.singletonList(5.0), 0, 50);
    anime.moveShape(anime.getShape(0), new Position2D(10.0, 20.0), 10, 20);
    assertEquals("Shapes:\nName: R\nType: square\nLower-left corner: (573.040000, 42.249300),"
            + " Side length: 5.0, Color: (0.000000, 0.000000, 1.000000)\nAppears at t=0\n"
            + "Disappears at t=50\n\nShape R moves from (573.040000, 42.249300) to (10.000000, "
            + "20.000000) from t=10 to t=20", anime.printAnimation());
  }

  @Test
  public void changeShapeSizeTest() {
    SimpleAnimation anime = new SimpleAnimation();
    List<Double> size = new ArrayList<>();
    size.add(4.0);
    size.add(9.0);
    List<Double> newSize = new ArrayList<>();
    newSize.add(10.0);
    newSize.add(15.0);
    anime.createShape("R", AnimatedShape.ShapeType.OVAL, new RGB(1.0,0.0,0.0),
            new Position2D(573.04, 42.2493), size, 0, 50);
    anime.changeShapeSize(anime.getShape(0), newSize, 10, 25);
    assertEquals("Shapes:\nName: R\nType: oval\nCenter: (573.040000, 42.249300), X radius: 4.0, "
            + "Y radius: 9.0, Color: (1.000000, 0.000000, 0.000000)\nAppears at t=0\nDisappears "
            + "at t=50\n\nShape R scales from X radius: 4.0, Y radius: 9.0 to X radius: 10.0, "
            + "Y radius: 15.0 from t=10 to t=25", anime.printAnimation());
  }

  @Test
  public void changeShapeColorTest() {
    SimpleAnimation anime = new SimpleAnimation();
    List<Double> size = new ArrayList<>();
    size.add(100.0048);
    size.add(492.2);
    size.add(400.99);
    anime.createShape("B", AnimatedShape.ShapeType.POLYGON, new RGB(0.5, 0.5, 0.5),
            new Position2D(1000, 5000), size, 150, 250);
    anime.changeShapeColor(anime.getShape(0), new RGB(0.75, 0.0, 0.5), 200, 201);
    assertEquals("Shapes:\nName: B\nType: polygon\nCenter: (1000.000000, 5000.000000), Side 1"
            + " length: 100.0048, Side 2 length: 492.2, Side 3 length: 400.99, Color: (0.500000,"
            + " 0.500000, 0.500000)\nAppears at t=150\nDisappears at t=250\n\nShape B changes "
            + "color from (0.500000, 0.500000, 0.500000) to (0.750000, 0.000000, 0.500000) from "
            + "t=200 to t=201", anime.printAnimation());
  }

  @Test
  public void createMultipleShapesNoAnimationsTest() {
    SimpleAnimation anime = new SimpleAnimation();
    RGB color1 = new RGB(0.0,1.0,0.0);
    Position2D initial = new Position2D(10.0, 4.0);
    List<Double> params = new ArrayList<>();
    params.add(6.0);
    params.add(8.0);
    List<Double> size1 = new ArrayList<>();
    size1.add(50.0);
    size1.add(50.0);
    size1.add(75.0);
    List<Double> size2 = new ArrayList<>();
    size2.add(100.0);
    size2.add(50.0);
    size2.add(25.0);
    size2.add(30.0);
    anime.createShape("Shape1", AnimatedShape.ShapeType.RECTANGLE, color1, initial, params,10,100);
    anime.createShape("B", AnimatedShape.ShapeType.POLYGON, new RGB(0.5, 0.5, 0.5),
            new Position2D(1000, 5000), size1, 0, 70);
    anime.createShape("My Shape", AnimatedShape.ShapeType.CIRCLE, new RGB(0.0,0.0,1.0),
            new Position2D(573.04, 42.2493), Collections.singletonList(5.0), 0, 50);
    anime.createShape("Hi", AnimatedShape.ShapeType.POLYGON, new RGB(0.5, 0.0, 0.5),
            new Position2D(10, 19), size2, 5, 110);
    assertEquals("Shapes:\nName: My Shape\nType: circle\nCenter: (573.040000, 42.249300), Radius: "
            + "5.0, Color: (0.000000, 0.000000, 1.000000)\nAppears at t=0\nDisappears at t=50\n\n"
            + "Name: B\nType: polygon\nCenter: (1000.000000, 5000.000000), Side 1 length: 50.0, "
            + "Side 2 length: 50.0, Side 3 length: 75.0, Color: (0.500000, 0.500000, 0.500000)\n"
            + "Appears at t=0\nDisappears at t=70\n\nName: Hi\nType: polygon\nCenter: (10.000000, "
            + "19.000000), Side 1 length: 100.0, Side 2 length: 50.0, Side 3 length: 25.0, Side 4 "
            + "length: 30.0, Color: (0.500000, 0.000000, 0.500000)\nAppears at t=5\nDisappears "
            + "at t=110\n\nName: Shape1\nType: rectangle\nLower-left corner: (10.000000, 4.000000)"
            + ", Width: 6.0, Height: 8.0, Color: (0.000000, 1.000000, 0.000000)\nAppears at "
            + "t=10\nDisappears at t=100\n\n", anime.printAnimation());
  }

  @Test
  public void create1ShapeMultipleAnimationsTest() {
    SimpleAnimation anime = new SimpleAnimation();
    List<Double> size = new ArrayList<>();
    List<Double> newSize = new ArrayList<>();
    for (int a = 0; a < 100; a++) {
      size.add(10.0);
      newSize.add(15.0);
    }
    anime.createShape("B", AnimatedShape.ShapeType.POLYGON, new RGB(0.5, 0.5, 0.5),
            new Position2D(1000, 5000), size, 150, 250);
    anime.changeShapeSize(anime.getShape(0), newSize, 240, 250);
    anime.moveShape(anime.getShape(0), new Position2D(0, 0), 215, 230);
    anime.changeShapeColor(anime.getShape(0), new RGB(1.0, 0.5, 0.0), 200, 220);
    anime.moveShape(anime.getShape(0), new Position2D(10, 20), 160, 210);
    String testString1 = "Shape B moves from";
    String testString2 = "Shape B changes color from";
    String testString3 = "Shape B scales from";
    String testString4 = anime.printAnimation();
    assertEquals(true, (testString4.contains(testString1) && testString4.contains(testString2)
            && testString4.contains(testString3)));
  }

  @Test
  public void createMultipleShapesMultipleAnimationsTest() {
    SimpleAnimation anime = new SimpleAnimation();
    RGB color1 = new RGB(0.0,1.0,0.0);
    Position2D initial = new Position2D(10.0, 4.0);
    List<Double> params = new ArrayList<>();
    params.add(6.0);
    params.add(8.0);
    List<Double> size1 = new ArrayList<>();
    size1.add(50.0);
    size1.add(50.0);
    size1.add(75.0);

    anime.createShape("Shape1", AnimatedShape.ShapeType.RECTANGLE, color1, initial, params,10,100);
    anime.createShape("My Shape", AnimatedShape.ShapeType.CIRCLE, new RGB(0.0,0.0,1.0),
            new Position2D(573.04, 42.2493), Collections.singletonList(5.0), 0, 50);
    anime.createShape("B", AnimatedShape.ShapeType.POLYGON, new RGB(0.5, 0.5, 0.5),
            new Position2D(1000, 5000), size1, 5, 70);

    anime.changeShapeColor(anime.getShape(1), new RGB(1.0, 0.5, 0.0), 10, 50);
    anime.moveShape(anime.getShape(1), new Position2D(10, 20), 60, 70);
    anime.moveShape(anime.getShape(2), new Position2D(-10, -15), 25, 90);
    anime.changeShapeColor(anime.getShape(2), new RGB(1.0, 0.5, 0.0), 45, 75);
    anime.changeShapeSize(anime.getShape(0), Collections.singletonList(10.0), 10, 40);
    anime.moveShape(anime.getShape(0), new Position2D(0, 0), 0, 25);

    String testString1 = "Shape B moves from";
    String testString2 = "Shape B changes color from";
    String testString3 = "Shape My Shape scales from";
    String testString4 = "Shape My Shape moves from";
    String testString5 = "Shape Shape1 moves from";
    String testString6 = "Shape Shape1 changes color from";
    String testString7 = anime.printAnimation();
    assertEquals(true, (testString7.contains(testString1) && testString7.contains(testString2)
            && testString7.contains(testString3) && testString7.contains(testString4)
            && testString7.contains(testString5) && testString7.contains(testString6)));
  }

  @Test
  public void removeShapeTest() {
    SimpleAnimation anime = new SimpleAnimation();
    RGB color1 = new RGB(0.0,1.0,0.0);
    Position2D initial = new Position2D(10.0, 4.0);
    List<Double> params = new ArrayList<>();
    params.add(6.0);
    params.add(8.0);
    anime.createShape("Shape1", AnimatedShape.ShapeType.RECTANGLE, color1, initial, params,10,100);
    anime.createShape("My Shape", AnimatedShape.ShapeType.CIRCLE, new RGB(0.0,0.0,1.0),
            new Position2D(573.04, 42.2493), Collections.singletonList(5.0), 0, 50);
    anime.removeShape(1);
    assertEquals("Name: My Shape\nType: circle\nCenter: (573.040000, 42.249300), Radius: "
            + "5.0, Color: (0.000000, 0.000000, 1.000000)\nAppears at t=0\n"
            + "Disappears at t=50", anime.getShape(0).toString());
  }

  @Test
  public void removeAnimationTest() {
    SimpleAnimation anime = new SimpleAnimation();
    RGB color1 = new RGB(0.0,1.0,0.0);
    Position2D initial = new Position2D(10.0, 4.0);
    List<Double> params = new ArrayList<>();
    params.add(6.0);
    params.add(8.0);
    List<Double> size1 = new ArrayList<>();
    size1.add(50.0);
    size1.add(50.0);
    size1.add(75.0);

    anime.createShape("Shape1", AnimatedShape.ShapeType.RECTANGLE, color1, initial, params,10,100);
    anime.createShape("My Shape", AnimatedShape.ShapeType.CIRCLE, new RGB(0.0,0.0,1.0),
            new Position2D(573.04, 42.2493), Collections.singletonList(5.0), 0, 50);
    anime.createShape("B", AnimatedShape.ShapeType.POLYGON, new RGB(0.5, 0.5, 0.5),
            new Position2D(1000, 5000), size1, 5, 70);

    anime.changeShapeColor(anime.getShape(1), new RGB(1.0, 0.5, 0.0), 10, 50);
    anime.moveShape(anime.getShape(1), new Position2D(10, 20), 60, 70);
    anime.removeAnimation(anime.getShape(1), Animations.AnimateTypes.MOVE, 65);
    String testString1 = "Shape B moves from";
    String testString2 = anime.printAnimation();
    assertEquals(false, testString2.contains(testString1));
  }

  @Test (expected = IllegalArgumentException.class)
  public void invalidPolygonTest() {
    SimpleAnimation anime = new SimpleAnimation();
    List<Double> size = new ArrayList<>();
    size.add(100.0);
    size.add(50.0);
    size.add(15.0);
    size.add(5.0);
    anime.createShape("Hi", AnimatedShape.ShapeType.POLYGON, new RGB(0.5, 0.0, 0.5),
            new Position2D(10, 19), size, 0, 10);
  }

  @Test (expected = IllegalArgumentException.class)
  public void invalidAnimationTimesTest() {
    SimpleAnimation anime = new SimpleAnimation();
    List<Double> size = new ArrayList<>();
    List<Double> newSize = new ArrayList<>();
    for (int a = 0; a < 10; a++) {
      size.add(10.0);
      newSize.add(15.0);
    }
    anime.createShape("B", AnimatedShape.ShapeType.POLYGON, new RGB(0.5, 0.5, 0.5),
            new Position2D(1000, 5000), size, 150, 250);
    anime.changeShapeSize(anime.getShape(0), newSize, 240, 260);
  }

  @Test (expected = IllegalArgumentException.class)
  public void overlappingAnimationsTest() {
    SimpleAnimation anime = new SimpleAnimation();
    List<Double> size = new ArrayList<>();
    List<Double> newSize = new ArrayList<>();
    for (int a = 0; a < 10; a++) {
      size.add(10.0);
      newSize.add(15.0);
    }
    anime.createShape("B", AnimatedShape.ShapeType.POLYGON, new RGB(0.5, 0.5, 0.5),
            new Position2D(1000, 5000), size, 150, 250);
    anime.changeShapeSize(anime.getShape(0), newSize, 240, 250);
    anime.moveShape(anime.getShape(0), new Position2D(0, 0), 215, 230);
    anime.moveShape(anime.getShape(0), new Position2D(5, 5), 195, 220);
  }
}
