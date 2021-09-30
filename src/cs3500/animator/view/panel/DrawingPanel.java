package cs3500.animator.view.panel;

import cs3500.animator.view.IViewEventListener;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.swing.JPanel;

/**
 * <p>A canvas on which to draw and animate shapes.</p>
 */
public class DrawingPanel extends JPanel implements IViewEventListener {

  private final List<IDrawingPanelShape> shapes;
  private boolean trailsEnabled;
  private boolean outlineMode;

  /**
   * Creates a drawing panel of the given width and height.
   *
   * @param width  the width of the drawing panel
   * @param height the height of the drawing panel
   * @throws IllegalArgumentException if the width or height is a non-positive number.
   */
  public DrawingPanel(int width, int height) throws IllegalArgumentException {
    super();
    if (width <= 0 || height <= 0) {
      throw new IllegalArgumentException("Width and height must be positive!");
    }
    this.setPreferredSize(new Dimension(width, height));
    this.setBackground(Color.WHITE);
    this.shapes = new ArrayList<>(); // ordered according to layering on canvas
    this.trailsEnabled = false;
    this.outlineMode = false;
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    for (IDrawingPanelShape shape : shapes) {
      shape.draw(g, this.outlineMode);
    }

    if (!this.trailsEnabled) {
      this.shapes.clear();
    }
  }

  /**
   * Draws an ellipse at coordinate (x,y), with width w, height h and Color color.
   *
   * @param x     the x coordinate of the ellipse
   * @param y     the y coordinate of the ellipse
   * @param w     the width of the ellipse
   * @param h     the height of the ellipse
   * @param color the color of the ellipse
   * @throws NullPointerException     if the Color is null
   * @throws IllegalArgumentException if the width or height is negative
   */
  public void drawEllipse(int x, int y, int w, int h, Color color)
      throws IllegalArgumentException, NullPointerException {
    Objects.requireNonNull(color);
    if (w < 0 || h < 0) {
      throw new IllegalArgumentException("Cannot have negative height or width!");
    }
    this.shapes.add(new DrawingPanelEllipse(x, y, w, h, color));
  }

  /**
   * Draws a rectangle at coordinate (x,y), with width w, height h and Color color.
   *
   * @param x     the x coordinate of the rectangle
   * @param y     the y coordinate of the rectangle
   * @param w     the width of the rectangle
   * @param h     the height of the rectangle
   * @param color the color of the rectangle
   * @throws NullPointerException     if the Color is null
   * @throws IllegalArgumentException if the width or height is negative
   */
  public void drawRectangle(int x, int y, int w, int h, Color color)
      throws IllegalArgumentException, NullPointerException {
    Objects.requireNonNull(color);
    if (w < 0 || h < 0) {
      throw new IllegalArgumentException("Cannot have negative height or width!");
    }
    this.shapes.add(new DrawingPanelRectangle(x, y, w, h, color));
  }

  public void drawPlus(int x, int y, int w, int h, Color color)
      throws IllegalArgumentException, NullPointerException {
    Objects.requireNonNull(color);
    if (w < 0 || h < 0) {
      throw new IllegalArgumentException("Cannot have negative height or width!");
    }
    this.shapes.add(new DrawingPanelPlus(x, y, w, h, color));
  }

  /**
   * <p>Sets the "trailing" function of our view. Setting trails will allow for shapes to be drawn
   * with a trailing option, the view will not be refreshed each view and the shapes will layer on
   * top of each other during their transformation.</p>
   *
   * @param enabled true if want to enable trails, false otherwise.
   */
  public void setTrails(boolean enabled) {
    this.trailsEnabled = enabled;
  }

  @Override
  public void playbackToggled(boolean play) {

  }

  @Override
  public void playbackRestarted() {

  }

  @Override
  public void speedIncreased() {

  }

  @Override
  public void speedDecreased() {

  }

  @Override
  public void loopingToggled(boolean enabled) {

  }

  @Override
  public void discreteToggled(boolean enabled) {

  }

  @Override
  public void outlineToggled(boolean enabled) {
    this.outlineMode = enabled;
  }
}
