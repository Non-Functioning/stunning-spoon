package cs3500.animator.controller;

import java.awt.event.ActionEvent;

import cs3500.animator.model.SimpleAnimation;
import cs3500.animator.model.SimpleAnimationModel;
import cs3500.animator.view.ViewInterface;

public class Controller implements IController {
  private SimpleAnimationModel model;
  private SimpleAnimationModel subsetModel;
  private ViewInterface view;

  public Controller(SimpleAnimationModel m, ViewInterface v) {
    model = m;
    view = v;
    subsetModel = new SimpleAnimation();
    view.setListener(this);
  }

  @Override
  public void action(ActionEvent e) {
    switch (e.getActionCommand()) {
      case "start":
        view.togglePlayOrPause();
        break;
      case "restart":
        view.restartAnimation();
        break;
      case "increase tempo":
        view.updateTempo(view.getTempo() + 1);
        break;
      case "decrease tempo":
        view.updateTempo(view.getTempo() - 1);
        break;
      case "loop":
        view.loopAnimation();
        break;
      case "add shape to subset":
        view.addToSubset(e, subsetModel);
        break;
      default:
        throw new IllegalArgumentException("Invalid action command");
    }
  }
}
