package cs3500.animator.view.panel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Objects;

/**
 * A rectangle object for use with the canvas of the easy animator.
 */
public class DrawingPanelRectangle extends Rectangle implements IDrawingPanelShape {

  private final Color color;

  /**
   * <p>Constructs a {@code DrawingPanelRectangle}.</p>
   *
   * @param x      the x coordinate of the rectangle
   * @param y      the y coordinate of the rectangle
   * @param width  the width of the rectangle
   * @param height the height of the rectangle
   * @param color  the Color of the rectangle
   * @throws NullPointerException     if the Color is null
   * @throws IllegalArgumentException if the width or height are not positive values
   */
  public DrawingPanelRectangle(int x, int y, int width, int height, Color color)
      throws NullPointerException, IllegalArgumentException {
    super(x, y, width, height);
    if (width <= 0 || height <= 0) {
      throw new IllegalArgumentException("Width and height must be positive values!");
    }
    this.color = Objects.requireNonNull(color);
  }

  @Override
  public void draw(Graphics g, boolean outline) {
    Objects.requireNonNull(g);
    g.setColor(this.color);
    if (outline) {
      g.drawRect(x, y, width, height);
    } else {
      g.fillRect(x, y, width, height);
    }
  }
}