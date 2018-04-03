package cs3500.animator.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.*;

import cs3500.animator.controller.IController;
import cs3500.animator.model.Animations;
import cs3500.animator.model.SimpleAnimationModel;

public class InteractiveView extends AbstractVisualView {
  private JPanel buttonPane;
  private JPanel mainButtonPanel;
  private JPanel subsetPanel;
  private JButton start;
  private JButton restart;
  private JButton upTempo;
  private JButton downTempo;
  private JRadioButton loop;
  private JLabel tempoDisplay;
  private JLabel dropdownDisplay;
  private JComboBox<String> dropdown;

  /**
   * Constructor for an abstract view.
   *
   * @param animationModel The model to transfer to a view.
   * @param tempo          Speed of the animation, as ticks per second.
   */
  public InteractiveView(SimpleAnimationModel animationModel, double tempo) {
    super(animationModel, tempo);

    mainButtonPanel = new JPanel();
    mainButtonPanel.setLayout(new FlowLayout());

    buttonPane = new JPanel();
    buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));
    buttonPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));

    start = new JButton("Play");
    start.setActionCommand("start");
    buttonPane.add(start);
    restart = new JButton("Restart");
    restart.setActionCommand("restart");
    buttonPane.add(restart);
    upTempo = new JButton("Increase Tempo");
    upTempo.setActionCommand("increase tempo");
    buttonPane.add(upTempo);
    downTempo = new JButton("Decrease Tempo");
    downTempo.setActionCommand("decrease tempo");
    buttonPane.add(downTempo);

    mainButtonPanel.add(buttonPane, FlowLayout.LEFT);

    tempoDisplay = new JLabel("Tempo: " + tempo);
    mainButtonPanel.add(tempoDisplay, FlowLayout.CENTER);

    JPanel radioPanel = new JPanel();
    radioPanel.setLayout(new BoxLayout(radioPanel, BoxLayout.PAGE_AXIS));

    loop = new JRadioButton("Enable Looping");
    loop.setActionCommand("loop");
    radioPanel.add(loop);

    subsetPanel = new JPanel();
    subsetPanel.setLayout(new FlowLayout());
    frame.add(subsetPanel, BorderLayout.SOUTH);

    dropdownDisplay = new JLabel("Add shapes to new animation");
    subsetPanel.add(dropdownDisplay);
    dropdown = new JComboBox<>();
    dropdown.setActionCommand("add shape to subset");
    createShapeDropdown();
    subsetPanel.add(dropdown);

    mainButtonPanel.add(radioPanel, FlowLayout.RIGHT);
    frame.add(mainButtonPanel, BorderLayout.NORTH);

    frame.pack();
    frame.setVisible(true);
  }

  private void createShapeDropdown() {
    for (int i = 0; i < shapes.size(); i++) {
      dropdown.addItem("Shape " + shapes.get(i).getShapeName() + " ("
              + shapes.get(i).getShapeType() + ")");
    }
  }

  public void togglePlayOrPause() {
    if (isPaused) {
      startAnimation(currTick);
      start.setText("Pause");
      isPaused = false;
    }
    else {
      pauseAnimation();
      start.setText("Play");
      isPaused = true;
    }
  }

  @Override
  public void updateTempo(double newTempo) {
    tempo = newTempo;
    tempoDisplay.setText("Tempo: " + tempo);
    timer.cancel();
    timer = new Timer();
    startAnimation(currTick);
  }

  @Override
  public void loopAnimation() {
    isLooped = !isLooped;
    timer.cancel();
    timer = new Timer();
    startAnimation(currTick);
  }

  @Override
  public void restartAnimation() {
    timer.cancel();
    timer = new Timer();
    start.setText("Pause");
    isPaused = false;
    startAnimation(0);
  }

  @Override
  public void addToSubset(ActionEvent arg0, SimpleAnimationModel subset) {
    if (arg0.getSource() instanceof JComboBox) {
      JComboBox<String> box = (JComboBox<String>) arg0.getSource();
      String item = (String) box.getSelectedItem();
      if (subset.getShapeByName(item.substring(6)) == null) {
        subset.copyShape(model.getShapeByName(item.substring(6)));
        for (int i = 0; i < animations.size(); i++) {
          if (animations.get(i).getChangedShape().getShapeName().equals(item.substring(6))) {
            subset.copyAnimation(animations.get(i));
          }
        }
        dropdownDisplay.setText("Added to Subset: " + item);
      }
      else {
        subset.removeShapeByName(item.substring(6));
        dropdownDisplay.setText("Removed from Subset: " + item);
      }
    }
  }

  @Override
  public void setListener(IController listener) {
    ActionListener listen = new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        listener.action(e);
      }
    };
    start.addActionListener(listen);
    restart.addActionListener(listen);
    upTempo.addActionListener(listen);
    downTempo.addActionListener(listen);
    loop.addActionListener(listen);
    dropdown.addActionListener(listen);
  }
}
