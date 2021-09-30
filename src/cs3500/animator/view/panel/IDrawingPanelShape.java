package cs3500.animator.view.panel;

import java.awt.Graphics;

/**
 * A drawing panel interface for drawing ModelShapes.
 */
public interface IDrawingPanelShape {

  /**
   * <p>Place an image of this shape onto the given {@code Graphics} object.</p>
   *
   * @param g       a graphics object
   * @param outline <p>whether to draw this shape as an outline (if true, draws outline of shape. if
   *                false, fills shape)</p>
   */
  void draw(Graphics g, boolean outline);

}
