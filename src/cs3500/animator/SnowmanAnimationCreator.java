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
import java.util.Random;

/**
 * <p>A class to contain a main method for creating a snowman animation. Saves the animation to a
 * file for loading into Excellence at a future time.</p>
 */
public class SnowmanAnimationCreator {

  /**
   * <p>The main method.</p>
   *
   * @param args command line arguments
   */
  public static void main(String[] args) throws IOException {
    final double tempo = 1;
    final String viewType = "text";
    BufferedWriter out;
    out = new BufferedWriter(new FileWriter("snowman.txt", true));

    final IAnimatorModel<IModelShape> model = getModel();
    final IAnimatorView view = ViewFactory.create(viewType, model, out, tempo);
    final IAnimatorController controller = ControllerFactory.create(view, model, out, tempo);

    controller.start();
    out.close();
  }

  private static IAnimatorModel<IModelShape> getModel() {
    IAnimatorModel<IModelShape> model = new AnimatorModel();
    Random rand = new Random();
    model.setBounds(50, 20, 600, 600);

    model.addShape("sky", ShapeType.RECTANGLE);
    model.addShape("bottomSnowManBody", ShapeType.ELLIPSE);
    model.addShape("middleSnowManBody", ShapeType.ELLIPSE);
    model.addShape("topSnowManBody", ShapeType.ELLIPSE);

    model.addKeyFrame(0, "sky", 0, 0, 1000, 400, Color.DARK_GRAY);
    model.addKeyFrame(200, "sky", 0, 0, 1000, 400, Color.DARK_GRAY);

    model.addShape("roof", ShapeType.RECTANGLE);
    model.addKeyFrame(10, "roof", 375, 150, 250, 50, Color.BLACK);
    model.addKeyFrame(200, "roof", 375, 150, 250, 50, Color.BLACK);

    model.addShape("walls", ShapeType.RECTANGLE);
    model.addKeyFrame(10, "walls", 400, 200, 200, 200, Color.ORANGE);
    model.addKeyFrame(200, "walls", 400, 200, 200, 200, Color.ORANGE);

    model.addShape("door", ShapeType.RECTANGLE);
    model.addKeyFrame(10, "door", 465, 300, 75, 100, Color.DARK_GRAY);
    model.addKeyFrame(200, "door", 465, 300, 75, 100, Color.DARK_GRAY);

    model.addShape("doorknob", ShapeType.ELLIPSE);
    model.addKeyFrame(10, "doorknob", 515, 350, 10, 10, Color.BLACK);
    model.addKeyFrame(200, "doorknob", 515, 350, 10, 10, Color.BLACK);

    model.addShape("ground", ShapeType.RECTANGLE);

    model.addKeyFrame(0, "ground", 0, 400, 1000, 300, Color.LIGHT_GRAY);
    model.addKeyFrame(200, "ground", 0, 400, 1000, 300, Color.LIGHT_GRAY);

    model.addKeyFrame(0, "bottomSnowManBody", 50, 300, 100, 100, Color.GRAY);
    model.addKeyFrame(10, "bottomSnowManBody", 100, 300, 100, 100, Color.GRAY);
    model.addKeyFrame(20, "bottomSnowManBody", 150, 300, 100, 100, Color.GRAY);
    model.addKeyFrame(30, "bottomSnowManBody", 200, 300, 100, 100, Color.GRAY);
    model.addKeyFrame(200, "bottomSnowManBody", 200, 300, 100, 100, Color.GRAY);

    model.addKeyFrame(0, "middleSnowManBody", 60, 230, 80, 80, Color.GRAY);
    model.addKeyFrame(10, "middleSnowManBody", 110, 230, 80, 80, Color.GRAY);
    model.addKeyFrame(20, "middleSnowManBody", 160, 230, 80, 80, Color.GRAY);
    model.addKeyFrame(30, "middleSnowManBody", 210, 230, 80, 80, Color.GRAY);
    model.addKeyFrame(200, "middleSnowManBody", 210, 230, 80, 80, Color.GRAY);

    model.addKeyFrame(0, "topSnowManBody", 75, 185, 50, 50, Color.GRAY);
    model.addKeyFrame(10, "topSnowManBody", 125, 185, 50, 50, Color.GRAY);
    model.addKeyFrame(20, "topSnowManBody", 175, 185, 50, 50, Color.GRAY);
    model.addKeyFrame(30, "topSnowManBody", 225, 185, 50, 50, Color.GRAY);
    model.addKeyFrame(200, "topSnowManBody", 225, 185, 50, 50, Color.GRAY);

    model.addShape("hatBottom", ShapeType.RECTANGLE);
    model.addKeyFrame(30, "hatBottom", 220, 180, 60, 10, Color.BLACK);
    model.addKeyFrame(200, "hatBottom", 220, 180, 60, 10, Color.BLACK);

    model.addShape("hatTop", ShapeType.RECTANGLE);
    model.addKeyFrame(30, "hatTop", 235, 160, 30, 30, Color.BLACK);
    model.addKeyFrame(200, "hatTop", 235, 160, 30, 30, Color.BLACK);

    model.addShape("bottomButton", ShapeType.ELLIPSE);
    model.addShape("middleButton", ShapeType.ELLIPSE);
    model.addShape("topButton", ShapeType.ELLIPSE);
    model.addKeyFrame(30, "bottomButton", 245, 285, 1, 1, Color.BLACK);
    model.addKeyFrame(40, "bottomButton", 245, 285, 10, 10, Color.BLACK);
    model.addKeyFrame(200, "bottomButton", 245, 285, 10, 10, Color.BLACK);

    model.addKeyFrame(30, "middleButton", 245, 265, 1, 1, Color.BLACK);
    model.addKeyFrame(40, "middleButton", 245, 265, 10, 10, Color.BLACK);
    model.addKeyFrame(200, "middleButton", 245, 265, 10, 10, Color.BLACK);

    model.addKeyFrame(30, "topButton", 245, 245, 1, 1, Color.BLACK);
    model.addKeyFrame(40, "topButton", 245, 245, 10, 10, Color.BLACK);
    model.addKeyFrame(200, "topButton", 245, 245, 10, 10, Color.BLACK);

    model.addShape("rightEye", ShapeType.ELLIPSE);
    model.addShape("leftEye", ShapeType.ELLIPSE);

    model.addKeyFrame(30, "rightEye", 255, 195, 1, 1, Color.BLACK);
    model.addKeyFrame(40, "rightEye", 255, 195, 8, 8, Color.BLACK);
    model.addKeyFrame(200, "rightEye", 255, 195, 8, 8, Color.BLACK);

    model.addKeyFrame(30, "leftEye", 236, 195, 1, 1, Color.BLACK);
    model.addKeyFrame(40, "leftEye", 236, 195, 8, 8, Color.BLACK);
    model.addKeyFrame(200, "leftEye", 236, 195, 8, 8, Color.BLACK);

    model.addShape("nose", ShapeType.RECTANGLE);
    model.addKeyFrame(30, "nose", 248, 209, 1, 1, Color.ORANGE);
    model.addKeyFrame(40, "nose", 248, 209, 17, 5, Color.ORANGE);
    model.addKeyFrame(200, "nose", 248, 209, 17, 5, Color.ORANGE);

    model.addShape("rightArm", ShapeType.RECTANGLE);
    model.addKeyFrame(40, "rightArm", 290, 270, 40, 5, Color.BLACK);
    model.addKeyFrame(200, "rightArm", 290, 270, 40, 5, Color.BLACK);

    model.addShape("rightArmLegs", ShapeType.RECTANGLE);
    model.addKeyFrame(40, "rightArmLegs", 320, 263, 5, 20, Color.BLACK);
    model.addKeyFrame(200, "rightArmLegs", 320, 263, 5, 20, Color.BLACK);

    model.addShape("leftArm", ShapeType.RECTANGLE);
    model.addKeyFrame(40, "leftArm", 172, 270, 40, 5, Color.BLACK);
    model.addKeyFrame(200, "leftArm", 172, 270, 40, 5, Color.BLACK);

    model.addShape("leftArmLegs", ShapeType.RECTANGLE);
    model.addKeyFrame(40, "leftArmLegs", 178, 263, 5, 20, Color.BLACK);
    model.addKeyFrame(200, "leftArmLegs", 178, 263, 5, 20, Color.BLACK);

    for (int i = 0; i < 1000; i++) {
      // random snow effects each time animation is run
      model.addShape("snow" + i, ShapeType.ELLIPSE);
      Random randTick = new Random();
      model.addKeyFrame(randTick.nextInt(200), "snow" + i, rand.nextInt(800), 0, 5, 5, Color.WHITE);
      model.addKeyFrame(200, "snow" + i, rand.nextInt(800), 1000, 5, 5, Color.WHITE);
    }

    return model;
  }

}
