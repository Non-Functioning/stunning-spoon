package cs3500.animator.view;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

import cs3500.animator.model.AnimatedShape;
import cs3500.animator.model.Animations;
import cs3500.animator.model.SimpleAnimationModel;

/**
 * A SVG view produces a XML output that represents the SVG view. It parses the model to the SVG
 * format.
 */
public class SVGView extends AbstractView {
  private String viewText;

  /**
   * Constructor for a SVG view.
   *
   * @param animationModel The model to take in to parse.
   * @param tempo          Tempo, representing the desired ticks per second.
   * @param output         The output file name string to output.
   */
  public SVGView(SimpleAnimationModel animationModel, double tempo, String output) {
    super(animationModel, tempo);
    makeSVG();

    try (PrintWriter out = new PrintWriter(output)) {
      out.println(viewText);
    } catch (FileNotFoundException e) {
      // Nothing here
    }
  }

  /**
   * Second constructor to write to the console.
   *
   * @param animationModel The model to take in to parse.
   * @param tempo          Tempo, representing the desired ticks per second.
   */
  public SVGView(SimpleAnimationModel animationModel, double tempo) {
    super(animationModel, tempo);
    makeSVG();

    System.out.print(viewText);
  }

  /**
   * Constructs a SVG view and saves it in the viewText field. This will call the shapeCommand
   * method to fill in the details for each shape.
   */
  private void makeSVG() {
    StringBuilder sb = new StringBuilder();
    sb.append("<!DOCTYPE html>\n");
    sb.append("<html>\n");
    sb.append("<body>\n");
    sb.append("<svg xmlns=\"http://www.w3.org/2000/svg\" version=\"1.1\" width=\"1000\" " +
            "height=\"1000\">\n");
    for (int i = 0; i < shapes.size(); i++) {
      sb.append(shapeCommand(shapes.get(i)) + "\n");
    }
    sb.append("</svg>\n");
    sb.append("</body>\n");
    sb.append("</html>\n");

    viewText = sb.toString();
  }

  /**
   * Constructs the animation of a shape.
   *
   * @param ani The animation of a shape to parse.
   * @return The string that represents a single animation that belongs to a shape.
   */
  private String animate(Animations ani) {
    switch (ani.getType()) {
      case MOVE:
        return "<animateMotion path=\"M " + (ani.getPosition1().getX()
                - ani.getChangedShape().getInitialPosition().getX()) + " "
                + (ani.getPosition1().getY() - ani.getChangedShape().getInitialPosition().getY())
                + " L " + (ani.getPosition2().getX() - ani.getChangedShape().getInitialPosition()
                .getX()) + " "
                + (ani.getPosition2().getY() - ani.getChangedShape().getInitialPosition().getY())
                + "\" begin=\""
                + ani.getTime1() / tempo + "s\" dur=\""
                + (ani.getTime2() - ani.getTime1()) / tempo + "s\" fill=\"freeze\" />";

      case CHANGECOLOR:
        return "<animate attributeName=\"fill\" attributeType=\"CSS\" from=\"RGB"
                + ani.getColor1().toStringSVG() + "\" to=\"RGB" + ani.getColor2().toStringSVG()
                + "\" begin=\"" + ani.getTime1() / tempo + "s\" dur=\""
                + (ani.getTime2() - ani.getTime1()) / tempo
                + "s\" fill=\"freeze\" />";

      case CHANGESIZE:
        return changeSize(ani);

      case APPEAR:
        return "<set attributeName=\"visibility\" attributeType=\"CSS\" to=\"visible\" begin=\""
                + ani.getTime1() / tempo + "s\" dur=\"" + (ani.getTime2() - ani.getTime1()) / tempo
                + "s\" fill=\"freeze\" />";

      case DISAPPEAR:
        return "<set attributeName=\"visibility\" attributeType=\"CSS\" to=\"hidden\" begin=\""
                + ani.getTime1() / tempo + "s\" dur=\"" + (ani.getTime2() - ani.getTime1()) / tempo
                + "s\" fill=\"freeze\" />";

      default:
        throw new IllegalArgumentException("Animation type not found.");
    }
  }

  /**
   * Constructs a shape to initialise and all animations that belong to this shape.
   *
   * @param shape The shape to be described in the SVG commands.
   * @return A string that contains the definition and all animations of a single shape.
   */
  private String shapeCommand(AnimatedShape shape) {
    String animateCommands = "";
    for (int i = 0; i < animations.size(); i++) {
      if (animations.get(i).getChangedShape().getShapeName().equals(shape.getShapeName())
              && animations.get(i).getChangedShape().getShapeType().equals(shape.getShapeType())) {
        animateCommands = animateCommands + "\n" + animate(animations.get(i));
      }
    }
    switch (shape.getShapeType()) {
      case RECTANGLE:
        return "<rect id=\"" + shape.getShapeName() + "\" x=\"" + shape.getInitialPosition().getX()
                + "\" y=\"" + shape.getInitialPosition().getY() + "\" width=\""
                + shape.getInitialSize().get(0) + "\" height=\"" + shape.getInitialSize().get(1)
                + "\" style=\"fill:RGB" + shape.getInitialColor().toStringSVG() + "\">"
                + animateCommands
                + "</rect>";
      case CIRCLE:
        return "<circle id=\"" + shape.getShapeName() + "\" cx=\""
                + shape.getInitialPosition().getX()
                + "\" cy=\"" + shape.getInitialPosition().getY() + "\" r=\""
                + shape.getInitialSize().get(0) + "\" stroke=RGB\""
                + shape.getInitialColor().toStringSVG()
                + "\" />"
                + animateCommands
                + "</circle>";
      case SQUARE:
        return "<rect id=\"" + shape.getShapeName() + "\" x=\"" + shape.getInitialPosition().getX()
                + "\" y=\"" + shape.getInitialPosition().getY() + "\" width=\""
                + shape.getInitialSize().get(0) + "\" height=\"" + shape.getInitialSize().get(0)
                + "\" style=\"fill:RGB" + shape.getInitialColor().toStringSVG() + "\">"
                + animateCommands
                + "</rect>";
      case OVAL:
        return "<ellipse cx=\"" + shape.getInitialPosition().getX() + "\" cy=\""
                + shape.getInitialPosition().getY() + "\" rx=\"" + shape.getInitialSize().get(0)
                + "\" ry=\"" + shape.getInitialSize().get(1) + "\" style=\"fill:RGB"
                + shape.getInitialColor().toStringSVG() + "\">"
                + animateCommands
                + "</ellipse>";

      default:
        throw new IllegalArgumentException("Shape type not found");
    }
  }

  /**
   * Exclusively changes the size of a shape since changing size for every shape demands different
   * commands.
   *
   * @param ani The animation object that contains the details of changing a shape's size.
   * @return The string that describes the size change of a shape in SVG format.
   */
  private String changeSize(Animations ani) {
    AnimatedShape shape = ani.getChangedShape();
    switch (shape.getShapeType()) {
      case RECTANGLE:
        return "<animate attributeName=\"width\" attributeType=\"XML\" begin=\""
                + ani.getTime1() / tempo
                + "s\" dur=\"" + (ani.getTime2() - ani.getTime1()) / tempo
                + "s\" fill=\"freeze\" from=\""
                + ani.getSizeParams1().get(0) + "\" to=\""
                + ani.getSizeParams2().get(0) + "\" /> \n" +
                "<animate attributeName=\"height\" attributeType=\"XML\" begin=\""
                + ani.getTime1() / tempo + "s\" dur=\"" + (ani.getTime2() - ani.getTime1()) / tempo
                + "s\" fill=\"freeze\" from=\"" + ani.getSizeParams1().get(1) + "\" to=\""
                + ani.getSizeParams2().get(1) + "\" />";
      case CIRCLE:
        return "<animate attributeName=\"r\" attributeType=\"XML\" begin=\""
                + ani.getTime1() / tempo
                + "s\" dur=\"" + (ani.getTime2() - ani.getTime1()) / tempo
                + "s\" fill=\"freeze\" from=\""
                + ani.getSizeParams1().get(0) + "\" to=\""
                + ani.getSizeParams2().get(0) + "\" />";
      case SQUARE:
        return "<animate attributeName=\"width\" attributeType=\"XML\" begin=\""
                + ani.getTime1() / tempo
                + "s\" dur=\"" + (ani.getTime2() - ani.getTime1()) / tempo
                + "s\" fill=\"freeze\" from=\""
                + ani.getSizeParams1().get(0) + "\" to=\""
                + ani.getSizeParams2().get(0) + "\" /> \n" +
                "<animate attributeName=\"height\" attributeType=\"XML\" begin=\""
                + ani.getTime1() / tempo + "s\" dur=\"" + (ani.getTime2() - ani.getTime1()) / tempo
                + "s\" fill=\"freeze\" from=\"" + ani.getSizeParams1().get(0) + "\" to=\""
                + ani.getSizeParams2().get(0) + "\" />";
      case OVAL:
        return "<animate attributeName=\"rx\" attributeType=\"XML\" begin=\""
                + ani.getTime1() / tempo
                + "s\" dur=\"" + (ani.getTime2() - ani.getTime1()) / tempo
                + "s\" fill=\"freeze\" from=\""
                + ani.getSizeParams1().get(0) + "\" to=\""
                + ani.getSizeParams2().get(0) + "\" /> \n" +
                "<animate attributeName=\"ry\" attributeType=\"XML\" begin=\""
                + ani.getTime1() / tempo + "s\" dur=\"" + (ani.getTime2() - ani.getTime1()) / tempo
                + "s\" fill=\"freeze\" from=\"" + ani.getSizeParams1().get(1) + "\" to=\""
                + ani.getSizeParams2().get(1) + "\" />";

      default:
        throw new IllegalArgumentException("Shape type not found");
    }
  }
}