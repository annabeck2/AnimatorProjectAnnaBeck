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
import java.io.IOException;

public class DiscreteAnimation {

  /**
   * <p>The main method.</p>
   *
   * @param args command line arguments
   */
  public static void main(String[] args) throws IOException {
    final double tempo = 1;
    final String viewType = "interactive";
    Appendable out = System.out;

    final IAnimatorModel<IModelShape> model = new AnimatorModel();
    model.setBounds(50, 20, 600, 600);

    final IAnimatorView view = ViewFactory.create(viewType, model, out, tempo);
    final IAnimatorController controller = ControllerFactory.create(view, model, out, tempo);

    model.addShape("Square1", ShapeType.RECTANGLE);
    model.addKeyFrame(0, "Square1", 200, 200, 200, 200, Color.RED);
    model.addKeyFrame(10, "Square1", 200, 200, 200, 200, Color.ORANGE);
    model.addKeyFrame(20, "Square1", 200, 200, 200, 200, Color.YELLOW);
    model.addKeyFrame(30, "Square1", 200, 200, 200, 200, Color.GREEN);
    model.addKeyFrame(40, "Square1", 200, 200, 200, 200, Color.BLUE);
    model.addKeyFrame(50, "Square1", 200, 200, 200, 200, Color.MAGENTA);

    controller.start();
  }
}
