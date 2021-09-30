package cs3500.animator.controller.command;

import cs3500.animator.view.IAnimatorView;

import java.awt.*;

public class DrawPlus extends DrawCommand {

  /**
   * <p>Constructs a {@code DrawCommand}.</p>
   *
   * @param view the view on which to execute the draw command
   */
  public DrawPlus(IAnimatorView view) {
    super(view);
  }

  @Override
  public void execute(int x, int y, int width, int height, Color color)
      throws UnsupportedOperationException {
    view.drawPlus(x, y, width, height, color);
  }

}
