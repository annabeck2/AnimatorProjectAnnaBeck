package cs3500.animator;

import cs3500.animator.controller.ControllerFactory;
import cs3500.animator.controller.IAnimatorController;
import cs3500.animator.model.AnimatorModel;
import cs3500.animator.model.IAnimatorModel;
import cs3500.animator.model.shape.IModelShape;
import cs3500.animator.model.shape.ShapeType;
import cs3500.animator.view.IAnimatorView;

import cs3500.animator.view.ViewFactory;
import cs3500.animator.view.panel.DrawingPanelPlus;

import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Random;

/**
 * <p>A class to contain a main method for creating and testing animations.</p>
 */
public class CustomAnimationCreator {

  /**
   * <p>The main method.</p>
   *
   * @param args command line arguments
   */
  public static void main(String[] args) throws IOException {
    BufferedWriter out;
    final double tempo = 1;
    final String viewType = "text";
//    out = new BufferedWriter(new OutputStreamWriter(System.out));
    out = new BufferedWriter(new FileWriter("./resources/rainbow-plus.txt", true));

    final IAnimatorModel<IModelShape> model = getModel();
    final IAnimatorView view = ViewFactory.create(viewType, model, out, tempo);
    final IAnimatorController controller = ControllerFactory.create(view, model, out, tempo);

    controller.start();
    out.close();
  }

  private static IAnimatorModel<IModelShape> getModel() {
    IAnimatorModel<IModelShape> model = new AnimatorModel();
    model.setBounds(50, 20, 800, 600);

    model.addShape("plus", ShapeType.PLUS);
    model.addKeyFrame(0, "plus", 50, 50, 100, 100, Color.RED);
    model.addKeyFrame(10, "plus", 60, 60, 150, 150, Color.ORANGE);
    model.addKeyFrame(20, "plus", 70, 70, 200, 200, Color.YELLOW);
    model.addKeyFrame(30, "plus", 80, 80, 250, 250, Color.GREEN);
    model.addKeyFrame(40, "plus", 90, 90, 300, 300, Color.BLUE);
    model.addKeyFrame(50, "plus", 100, 100, 350, 350, Color.MAGENTA);

    return model;
  }
}
