package cs3500.animator.controller;

import java.awt.event.ActionEvent;

import javax.swing.*;

import cs3500.animator.model.SimpleAnimation;
import cs3500.animator.model.SimpleAnimationModel;
import cs3500.animator.view.ViewInterface;

public class Controller implements IController {
  private SimpleAnimationModel model;
  private ViewInterface view;
  String out;

  public Controller(SimpleAnimationModel m, ViewInterface v) {
    model = m;
    view = v;
    view.setListener(this);
  }

  public Controller(SimpleAnimationModel m, ViewInterface v, String out) {
    model = m;
    view = v;
    this.out = out;
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
      case "SVG animation":
        if (out == null) {
          out = JOptionPane.showInputDialog("Please enter a file name ending in .xml or " +
                  " .txt to save the SVG view to.");
        }
        view.svgAnimation(out);
        view.createMessageDialog("SVG view was saved.");
        break;
      case "add shape to subset":
        if (e.getSource() instanceof JComboBox) {
          JComboBox<String> box = (JComboBox<String>) e.getSource();
          String item = (String) box.getSelectedItem();
          view.addToSubset(item);
        }
        break;
      case "view subset":
        view.showSubsetList();
        break;
      case "play subset":
        view.playSubset(0);
        break;
      case "SVG subset":
        if (out == null) {
          out = JOptionPane.showInputDialog("Please enter a file name ending in .xml or " +
                  " .txt to save the SVG view to.");
        }
        view.svgSubset(out);
        view.createMessageDialog("SVG view of the subset was saved.");
        break;
      default:
        throw new IllegalArgumentException("Invalid action command");
    }
  }
}
