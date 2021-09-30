package cs3500.animator;

import cs3500.animator.controller.ControllerFactory;
import cs3500.animator.controller.IAnimatorController;
import cs3500.animator.model.AnimatorModel;
import cs3500.animator.model.IAnimatorModel;
import cs3500.animator.model.shape.IModelShape;
import cs3500.animator.model.shape.ShapeType;
import cs3500.animator.view.IAnimatorView;

import cs3500.animator.view.ViewFactory;

import java.awt.Color;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * <p>A class to contain a main method for creating a ping pong animation. Saves the animation to a
 * file for loading into Excellence at a future time.</p>
 */
public class PongAnimationCreator {

  /**
   * <p>The main method.</p>
   *
   * @param args command line arguments
   */
  public static void main(String[] args) throws IOException {
    final double tempo = 5;
    final String viewType = "text";
    BufferedWriter out;
//    out = new BufferedWriter(new OutputStreamWriter(System.out));
    out = new BufferedWriter(new FileWriter("pong.txt", true));

    final IAnimatorModel<IModelShape> model = getModel();
    final IAnimatorView view = ViewFactory.create(viewType, model, out, tempo);
    final IAnimatorController controller = ControllerFactory.create(view, model, out, tempo);

    controller.start();
    out.close();
  }

  private static IAnimatorModel<IModelShape> getModel() {
    IAnimatorModel<IModelShape> model = new AnimatorModel();
    model.setBounds(50, 20, 600, 600);

    model.addShape("pingpongtable", ShapeType.RECTANGLE);
    model.addKeyFrame(0, "pingpongtable", 100, 300, 500, 50, new Color(0, 255, 0));
    model.addKeyFrame(19, "pingpongtable", 100, 300, 500, 50, new Color(0, 255, 0));

    model.addShape("net", ShapeType.RECTANGLE);
    model.addKeyFrame(0, "net", 350, 300, 10, 50, Color.BLACK);
    model.addKeyFrame(19, "net", 350, 300, 10, 50, Color.BLACK);

    model.addShape("paddle1Top", ShapeType.ELLIPSE);
    model.addShape("paddle1Bottom", ShapeType.RECTANGLE);

    model.addShape("paddle2Top", ShapeType.ELLIPSE);
    model.addKeyFrame(0, "paddle2Top", 610, 260, 20, 40, Color.RED);

    model.addShape("paddle2Bottom", ShapeType.RECTANGLE);
    model.addKeyFrame(0, "paddle2Bottom", 617, 297, 5, 20, Color.BLACK);

    model.addShape("ball", ShapeType.ELLIPSE);
    model.addKeyFrame(0, "ball", 110, 250, 10, 10, Color.ORANGE);
    model.addKeyFrame(0, "paddle1Top", 100, 250, 20, 40, Color.BLUE);
    model.addKeyFrame(0, "paddle1Bottom", 107, 287, 5, 20, Color.BLACK);
    model.addKeyFrame(5, "ball", 500, 320, 10, 10, Color.ORANGE);
    model.addKeyFrame(10, "paddle1Top", 80, 260, 20, 40, Color.BLUE);
    model.addKeyFrame(10, "paddle1Bottom", 87, 297, 5, 20, Color.BLACK);
    model.addKeyFrame(19, "paddle1Top", 100, 250, 20, 40, Color.BLUE);
    model.addKeyFrame(19, "paddle1Bottom", 107, 287, 5, 20, Color.BLACK);
    model.addKeyFrame(13, "ball", 590, 250, 10, 10, Color.ORANGE);
    model.addKeyFrame(13, "paddle2Top", 590, 250, 20, 40, Color.RED);
    model.addKeyFrame(13, "paddle2Bottom", 597, 287, 5, 20, Color.BLACK);
    model.addKeyFrame(19, "paddle2Top", 610, 260, 20, 40, Color.RED);
    model.addKeyFrame(19, "paddle2Bottom", 617, 297, 5, 20, Color.BLACK);
    model.addKeyFrame(17, "ball", 200, 320, 10, 10, Color.ORANGE);
    model.addKeyFrame(19, "ball", 110, 250, 10, 10, Color.ORANGE);

    return model;
  }
}
