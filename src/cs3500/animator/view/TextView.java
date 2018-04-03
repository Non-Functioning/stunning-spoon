package cs3500.animator.view;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

import cs3500.animator.controller.IController;
import cs3500.animator.model.AnimatedShape;
import cs3500.animator.model.Animations;
import cs3500.animator.model.SimpleAnimationModel;

/**
 * This class represents a textual view of an animation.
 * It takes in and reads a SimpleAnimation model and using the information
 * from a model it prints out a list of shapes, animations, and characteristics
 * of each.
 */
public class TextView extends AbstractView {
  private String viewText;

  /**
   * Constructor for a text view.
   *
   * @param animationModel The model to present in a text view.
   * @param tempo          Tempo, representing the desired ticks per second.
   * @param outputFile     The output text file's name.
   */
  public TextView(SimpleAnimationModel animationModel, double tempo, String outputFile) {
    super(animationModel, tempo);
    viewText = printAnimation();

    try (PrintWriter out = new PrintWriter(outputFile)) {
      out.println(viewText);
    } catch (FileNotFoundException e) {
      // Nothing here
    }
  }

  /**
   * Constructor for a text view, without an output file.
   *
   * @param animationModel The model to present in a text view.
   * @param tempo          Tempo, representing the desired ticks per second.
   */
  public TextView(SimpleAnimationModel animationModel, double tempo) {
    super(animationModel, tempo);
    viewText = printAnimation();

    System.out.print(viewText);
  }

  /**
   * Returns the String representation of the entire animation including
   * all shapes and Animations.
   *
   * @return animation as a String
   */
  private String printAnimation() {
    StringBuilder shapesString = new StringBuilder("Shapes:\n");
    StringBuilder animationString = new StringBuilder();
    for (int i = 0; i < shapes.size(); i++) {
      shapesString.append(shapeToString(i));
      shapesString.append("\n\n");
    }

    for (int i = 0; i < animations.size(); i++) {
      animationString.append(animationToString(i));
      if (i != (animations.size() - 1)) {
        animationString.append("\n");
      }
    }
    return shapesString.append(animationString).toString();
  }

  /**
   * This method converts a shape into a String representation.
   *
   * @param index index of a shape
   * @return shape as a String
   */
  private String shapeToString(int index) {
    AnimatedShape currentShape = shapes.get(index);
    return "Name: " + currentShape.getShapeName() + "\nType: " + currentShape.getShapeType()
            .getStringValue() + "\n"
            + currentShape.getRefPoint().getStringValue() + ": " + currentShape.getInitialPosition()
            .toString() + ", "
            + currentShape.sizeParamsToString(currentShape.getInitialSize()) + ", Color: "
            + currentShape.getInitialColor().toString()
            + "\nAppears at t=" + String.valueOf(currentShape.getAppearTime() / tempo)
            + "\nDisappears at t="
            + String.valueOf(currentShape.getDisappearTime() / tempo);
  }

  /**
   * This method converts an animation into s String representation.
   *
   * @param index index of an animation
   * @return animation as a String
   */
  private String animationToString(int index) {
    Animations currentAnimations = animations.get(index);

    StringBuilder newString = new StringBuilder("Shape " + currentAnimations.getChangedShape()
            .getShapeName());
    switch (currentAnimations.getType()) {
      case MOVE:
        newString.append(" moves from ");
        newString.append(currentAnimations.getPosition1().toString());
        newString.append(" to ");
        newString.append(currentAnimations.getPosition2().toString());
        newString.append(" from t=");
        newString.append(currentAnimations.getTime1() / tempo);
        newString.append(" to t=");
        newString.append(currentAnimations.getTime2() / tempo);
        break;
      case CHANGECOLOR:
        newString.append(" changes color from ");
        newString.append(currentAnimations.getColor1().toString());
        newString.append(" to ");
        newString.append(currentAnimations.getColor2().toString());
        newString.append(" from t=");
        newString.append(currentAnimations.getTime1() / tempo);
        newString.append(" to t=");
        newString.append(currentAnimations.getTime2() / tempo);
        break;
      case CHANGESIZE:
        newString.append(" scales from ");
        newString.append(currentAnimations.getChangedShape().sizeParamsToString(currentAnimations
                .getSizeParams1()));
        newString.append(" to ");
        newString.append(currentAnimations.getChangedShape().sizeParamsToString(currentAnimations
                .getSizeParams2()));
        newString.append(" from t=");
        newString.append(currentAnimations.getTime1() / tempo);
        newString.append(" to t=");
        newString.append(currentAnimations.getTime2() / tempo);
        break;
      case APPEAR:
        newString.append(" appears at t=");
        newString.append(currentAnimations.getTime1() / tempo);
        break;
      default:
        newString.append(" disappears at t=");
        newString.append(currentAnimations.getTime1() / tempo);
        break;
    }
    return newString.toString();
  }

  @Override
  public void setListener(IController listener) {
    throw new UnsupportedOperationException("This view does not support listeners.");
  }

  @Override
  public void startAnimation() {
    throw new UnsupportedOperationException("This view does not support visual views.");
  }

  @Override
  public void loopAnimation() {
    throw new UnsupportedOperationException("This view does not support animation looping.");
  }

  @Override
  public void restartAnimation() {
    throw new UnsupportedOperationException("This view does not support animation restart.");
  }

  @Override
  public void pauseAnimation() {
    throw new UnsupportedOperationException("This view does not support animation pausing.");
  }
}
