package cs3500.animator;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import cs3500.animator.model.SimpleAnimation;
import cs3500.animator.view.AbstractView;

import static junit.framework.Assert.assertEquals;


public class AnimationViewTests {
  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
  private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

  @Before
  public void setUpStreams() {
    System.setOut(new PrintStream(outContent));
    System.setErr(new PrintStream(errContent));
  }

  @After
  public void restoreStreams() {
    System.setOut(System.out);
    System.setErr(System.err);
  }

  @Test
  public void testText() throws IOException {
    String[] args = {"-if", "smalldemo.txt", "-iv", "text", "-o", "out", "-speed", "2"};
    EasyAnimator.main(args);
    assertEquals("Shapes:\n" +
            "Name: R\n" +
            "Type: rectangle\n" +
            "Min-corner: (200.000000, 200.000000), Width: 50.0, Height: 100.0, Color: (1.000000," +
            " 0.000000, 0.000000)\n" +
            "Appears at t=0.5\n" +
            "Disappears at t=50.0\n" +
            "\n" +
            "Name: C\n" +
            "Type: oval\n" +
            "Center: (500.000000, 100.000000), X radius: 60.0, Y radius: 30.0, Color: (0.000000," +
            " 0.000000, 1.000000)\n" +
            "Appears at t=3.0\n" +
            "Disappears at t=50.0\n" +
            "\n" +
            "Shape R moves from (200.000000, 200.000000) to (300.000000, 300.000000) from t=5" +
            ".0 to t=25.0\n" +
            "Shape C moves from (500.000000, 100.000000) to (500.000000, 400.000000) from t=1" +
            "0.0 to t=35.0\n" +
            "Shape C changes color from (0.000000, 0.000000, 1.000000) to (0.000000, 1.000000" +
            ", 0.000000) from t=25.0 to t=40.0\n" +
            "Shape R scales from Width: 50.0, Height: 100.0 to Width: 25.0, Height: 100.0 fro" +
            "m t=25.5 to t=35.0\n" +
            "Shape R moves from (300.000000, 300.000000) to (200.000000, 200.000000) from t=3" +
            "5.0 to t=50.0", outContent.toString());
  }

  @Test
  public void testText2() throws IOException {
    String[] args = {"-if", "toh-3.txt", "-iv", "text", "-o", "out", "-speed", "20"};
    EasyAnimator.main(args);
    assertEquals("Shapes:\n" +
            "Name: disk3\n" +
            "Type: rectangle\n" +
            "Min-corner: (145.000000, 240.000000), Width: 110.0, Height: 30.0, Color: (0.044487," +
            " 0.179378, 0.687518)\n" +
            "Appears at t=0.05\n" +
            "Disappears at t=15.1\n" +
            "\n" +
            "Name: disk2\n" +
            "Type: rectangle\n" +
            "Min-corner: (167.500000, 210.000000), Width: 65.0, Height: 30.0, Color: (0.025823, " +
            "0.969198, 0.163931)\n" +
            "Appears at t=0.05\n" +
            "Disappears at t=15.1\n" +
            "\n" +
            "Name: disk1\n" +
            "Type: rectangle\n" +
            "Min-corner: (190.000000, 180.000000), Width: 20.0, Height: 30.0, Color: (0.001856," +
            " 0.195475, 0.354671)\n" +
            "Appears at t=0.05\n" +
            "Disappears at t=15.1\n" +
            "\n" +
            "Shape disk1 moves from (190.000000, 180.000000) to (190.000000, 50.000000) " +
            "from t=1.25 to t=1.75\n" +
            "Shape disk1 moves from (190.000000, 50.000000) to (490.000000, 50.000000) f" +
            "rom t=1.8 to t=2.3\n" +
            "Shape disk1 moves from (490.000000, 50.000000) to (490.000000, 240.000000) " +
            "from t=2.35 to t=2.85\n" +
            "Shape disk2 moves from (167.500000, 210.000000) to (167.500000, 50.000000) " +
            "from t=2.85 to t=3.35\n" +
            "Shape disk2 moves from (167.500000, 50.000000) to (317.500000, 50.000000) f" +
            "rom t=3.4 to t=3.9\n" +
            "Shape disk2 moves from (317.500000, 50.000000) to (317.500000, 240.000000) " +
            "from t=3.95 to t=4.45\n" +
            "Shape disk1 moves from (490.000000, 240.000000) to (490.000000, 50.000000) " +
            "from t=4.45 to t=4.95\n" +
            "Shape disk1 moves from (490.000000, 50.000000) to (340.000000, 50.000000) f" +
            "rom t=5.0 to t=5.5\n" +
            "Shape disk1 moves from (340.000000, 50.000000) to (340.000000, 210.000000) " +
            "from t=5.55 to t=6.05\n" +
            "Shape disk3 moves from (145.000000, 240.000000) to (145.000000, 50.000000) " +
            "from t=6.05 to t=6.55\n" +
            "Shape disk3 moves from (145.000000, 50.000000) to (445.000000, 50.000000) f" +
            "rom t=6.6 to t=7.1\n" +
            "Shape disk3 moves from (445.000000, 50.000000) to (445.000000, 240.000000) " +
            "from t=7.15 to t=7.65\n" +
            "Shape disk1 moves from (340.000000, 210.000000) to (340.000000, 50.000000) " +
            "from t=7.65 to t=8.15\n" +
            "Shape disk3 changes color from (0.044487, 0.179378, 0.687518) to (0.000000," +
            " 1.000000, 0.000000) from t=7.65 to t=8.05\n" +
            "Shape disk1 moves from (340.000000, 50.000000) to (190.000000, 50.000000) f" +
            "rom t=8.2 to t=8.7\n" +
            "Shape disk1 moves from (190.000000, 50.000000) to (190.000000, 240.000000) " +
            "from t=8.75 to t=9.25\n" +
            "Shape disk2 moves from (317.500000, 240.000000) to (317.500000, 50.000000) " +
            "from t=9.25 to t=9.75\n" +
            "Shape disk2 moves from (317.500000, 50.000000) to (467.500000, 50.000000) f" +
            "rom t=9.8 to t=10.3\n" +
            "Shape disk2 moves from (467.500000, 50.000000) to (467.500000, 210.000000) " +
            "from t=10.35 to t=10.85\n" +
            "Shape disk1 moves from (190.000000, 240.000000) to (190.000000, 50.000000) " +
            "from t=10.85 to t=11.35\n" +
            "Shape disk2 changes color from (0.025823, 0.969198, 0.163931) to (0.000000," +
            " 1.000000, 0.000000) from t=10.85 to t=11.25\n" +
            "Shape disk1 moves from (190.000000, 50.000000) to (490.000000, 50.000000) f" +
            "rom t=11.4 to t=11.9\n" +
            "Shape disk1 moves from (490.000000, 50.000000) to (490.000000, 180.000000) " +
            "from t=11.95 to t=12.45\n" +
            "Shape disk1 changes color from (0.001856, 0.195475, 0.354671) to (0.000000," +
            " 1.000000, 0.000000) from t=12.45 to t=12.85", outContent.toString());
  }

  @Test
  public void testSVG() throws IOException {
    String[] args = {"-if", "smalldemo.txt", "-iv", "svg", "-o", "out", "-speed", "20"};
    EasyAnimator.main(args);
    assertEquals("<!DOCTYPE html>\n" +
            "<html>\n" +
            "<body>\n" +
            "<svg xmlns=\"http://www.w3.org/2000/svg\" version=\"1.1\" width=\"1000\" height=\"" +
            "1000\">\n" +
            "<rect id=\"R\" x=\"200.0\" y=\"200.0\" width=\"50.0\" height=\"100.0\" style=\"fill" +
            ":RGB(255, 0, 0)\">\n" +
            "<animateMotion path=\"M 0.0 0.0 L 100.0 100.0\" begin=\"0.5s\" dur=\"2.0s\" fill=\"" +
            "freeze\" />\n" +
            "<animate attributeName=\"width\" attributeType=\"XML\" begin=\"2.55s\" " +
            "dur=\"0.95s\" fill=\"freeze\" from=\"50.0\" to=\"25.0\" /> \n" +
            "<animate attributeName=\"height\" attributeType=\"XML\" begin=\"2.55s\" " +
            "dur=\"0.95s\" fill=\"freeze\" from=\"100.0\" to=\"100.0\" />\n" +
            "<animateMotion path=\"M 100.0 100.0 L 0.0 0.0\" begin=\"3.5s\" dur=\"1.5s\"" +
            " fill=\"freeze\" /></rect>\n" +
            "<ellipse cx=\"500.0\" cy=\"100.0\" rx=\"60.0\" ry=\"30.0\" style=\"" +
            "fill:RGB(0, 0, 255)\">\n" +
            "<animateMotion path=\"M 0.0 0.0 L 0.0 300.0\" begin=\"1.0s\" dur=\"2.5s\" " +
            "fill=\"freeze\" />\n" +
            "<animate attributeName=\"fill\" attributeType=\"CSS\" from=\"RGB(0, 0, 255)\"" +
            " to=\"RGB(0, 255, 0)\" begin=\"2.5s\" dur=\"1.5s\" fill=\"freeze\" /></ellipse>\n" +
            "</svg>\n" +
            "</body>\n" +
            "</html>\n", outContent.toString());
  }

  @Test
  public void testSVG2() throws IOException {
    String[] args = {"-if", "toh-3.txt", "-iv", "svg", "-o", "out", "-speed", "20"};
    EasyAnimator.main(args);
    assertEquals("<!DOCTYPE html>\n" +
            "<html>\n" +
            "<body>\n" +
            "<svg xmlns=\"http://www.w3.org/2000/svg\" version=\"1.1\" width=\"1000\"" +
            " height=\"1000\">\n" +
            "<rect id=\"disk3\" x=\"145.0\" y=\"240.0\" width=\"110.0\" height=\"30.0\" style=\"" +
            "fill:RGB(11, 45, 175)\">\n" +
            "<animateMotion path=\"M 0.0 0.0 L 0.0 -190.0\" begin=\"6.05s\" dur=\"0.5s\" fill=\"" +
            "freeze\" />\n" +
            "<animateMotion path=\"M 0.0 -190.0 L 300.0 -190.0\" begin=\"6.6s\" dur=\"0.5s\" " +
            "fill=\"freeze\" />\n" +
            "<animateMotion path=\"M 300.0 -190.0 L 300.0 0.0\" begin=\"7.15s\" dur=\"0.5s\" " +
            "fill=\"freeze\" />\n" +
            "<animate attributeName=\"fill\" attributeType=\"CSS\" from=\"RGB(11, 45, 175)\" " +
            "to=\"RGB(0, 255, 0)\" begin=\"7.65s\" dur=\"0.4s\" fill=\"freeze\" /></rect>\n" +
            "<rect id=\"disk2\" x=\"167.5\" y=\"210.0\" width=\"65.0\" height=\"30.0\" style=\"" +
            "fill:RGB(6, 247, 41)\">\n" +
            "<animateMotion path=\"M 0.0 0.0 L 0.0 -160.0\" begin=\"2.85s\" dur=\"0.5s\" fill=\"" +
            "freeze\" />\n" +
            "<animateMotion path=\"M 0.0 -160.0 L 150.0 -160.0\" begin=\"3.4s\" dur=\"0.5s\" fil" +
            "l=\"freeze\" />\n" +
            "<animateMotion path=\"M 150.0 -160.0 L 150.0 30.0\" begin=\"3.95s\" dur=\"0.5s\" fi" +
            "ll=\"freeze\" />\n" +
            "<animateMotion path=\"M 150.0 30.0 L 150.0 -160.0\" begin=\"9.25s\" dur=\"0.5s\" fi" +
            "ll=\"freeze\" />\n" +
            "<animateMotion path=\"M 150.0 -160.0 L 300.0 -160.0\" begin=\"9.8s\" dur=\"0.5s\" f" +
            "ill=\"freeze\" />\n" +
            "<animateMotion path=\"M 300.0 -160.0 L 300.0 0.0\" begin=\"10.35s\" dur=\"0.5s\" fi" +
            "ll=\"freeze\" />\n" +
            "<animate attributeName=\"fill\" attributeType=\"CSS\" from=\"RGB(6, 247, 41)\" to=\"" +
            "RGB(0, 255, 0)\" begin=\"10.85s\" dur=\"0.4s\" fill=\"freeze\" /></rect>\n" +
            "<rect id=\"disk1\" x=\"190.0\" y=\"180.0\" width=\"20.0\" height=\"30.0\" style=\"f" +
            "ill:RGB(0, 49, 90)\">\n" +
            "<animateMotion path=\"M 0.0 0.0 L 0.0 -130.0\" begin=\"1.25s\" dur=\"0.5s\" fill=\"" +
            "freeze\" />\n" +
            "<animateMotion path=\"M 0.0 -130.0 L 300.0 -130.0\" begin=\"1.8s\" dur=\"0.5s\" fil" +
            "l=\"freeze\" />\n" +
            "<animateMotion path=\"M 300.0 -130.0 L 300.0 60.0\" begin=\"2.35s\" dur=\"0.5s\" fi" +
            "ll=\"freeze\" />\n" +
            "<animateMotion path=\"M 300.0 60.0 L 300.0 -130.0\" begin=\"4.45s\" dur=\"0.5s\" fi" +
            "ll=\"freeze\" />\n" +
            "<animateMotion path=\"M 300.0 -130.0 L 150.0 -130.0\" begin=\"5.0s\" dur=\"0.5s\" f" +
            "ill=\"freeze\" />\n" +
            "<animateMotion path=\"M 150.0 -130.0 L 150.0 30.0\" begin=\"5.55s\" dur=\"0.5s\" fi" +
            "ll=\"freeze\" />\n" +
            "<animateMotion path=\"M 150.0 30.0 L 150.0 -130.0\" begin=\"7.65s\" dur=\"0.5s\" fi" +
            "ll=\"freeze\" />\n" +
            "<animateMotion path=\"M 150.0 -130.0 L 0.0 -130.0\" begin=\"8.2s\" dur=\"0.5s\" fil" +
            "l=\"freeze\" />\n" +
            "<animateMotion path=\"M 0.0 -130.0 L 0.0 60.0\" begin=\"8.75s\" dur=\"0.5s\" fill=\"" +
            "freeze\" />\n" +
            "<animateMotion path=\"M 0.0 60.0 L 0.0 -130.0\" begin=\"10.85s\" dur=\"0.5s\" fill=" +
            "\"freeze\" />\n" +
            "<animateMotion path=\"M 0.0 -130.0 L 300.0 -130.0\" begin=\"11.4s\" dur=\"0.5s\" fi" +
            "ll=\"freeze\" />\n" +
            "<animateMotion path=\"M 300.0 -130.0 L 300.0 0.0\" begin=\"11.95s\" dur=\"0.5s\" fi" +
            "ll=\"freeze\" />\n" +
            "<animate attributeName=\"fill\" attributeType=\"CSS\" from=\"RGB(0, 49, 90)\" to=\"" +
            "RGB(0, 255, 0)\" begin=\"12.45s\" dur=\"0.4s\" fill=\"freeze\" /></rect>\n" +
            "</svg>\n" +
            "</body>\n" +
            "</html>\n", outContent.toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAbstractExceptionZero() {
    AbstractView av = new AbstractView(new SimpleAnimation(), 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAbstractExceptionNeg() {
    AbstractView av = new AbstractView(new SimpleAnimation(), -1);
  }

  // Wrong shape type in text file.
  @Test(expected = IllegalStateException.class)
  public void testIllegalStatetException() throws IOException {
    String[] args = {"-if", "invalid-shape.txt", "-iv", "svg", "-o", "out", "-speed", "20"};
    EasyAnimator.main(args);
  }

  @Test
  public void testVisual() throws IOException, InterruptedException {
    String[] args = {"-if", "smalldemo.txt", "-iv", "visual", "-o", "out", "-speed", "10"};
    EasyAnimator.main(args);
    Thread.sleep(30000);
  }
}
