package cs3500.animator.view;

import java.awt.Color;
import java.io.IOException;

/**
 * <p>A view interface for the easy animator. There are three different types of views that will
 * implement this interface, one which produces a textual output (TextualAnimationView), one which
 * produces a visual view (VisualAnimationView) and one which produces an SVG output
 * (SVGAnimationView).</p>
 */
public interface IAnimatorView {

  /**
   * Render the animation according to the format of this view.
   *
   * @throws IOException if rendering fails for some reason.
   */
  void render() throws IOException;

  /**
   * Draws a rectangle at coordinate (x,y), with width w, height h and Color c.
   *
   * @param x     the x coordinate of the rectangle
   * @param y     the y coordinate of the rectangle
   * @param w     the width of the rectangle
   * @param h     the height of the rectangle
   * @param color the color of the rectangle
   * @throws UnsupportedOperationException if the rectangle cannot be drawn in this view
   * @throws NullPointerException          if the Color is null
   * @throws IllegalArgumentException      if the width or height is negative
   */
  void drawRectangle(int x, int y, int w, int h, Color color)
      throws UnsupportedOperationException, NullPointerException, IllegalArgumentException;

  /**
   * Draws an ellipse at coordinate (x,y), with width w, height h and Color c.
   *
   * @param x     the x coordinate of the ellipse
   * @param y     the y coordinate of the ellipse
   * @param w     the width of the ellipse
   * @param h     the height of the ellipse
   * @param color the color of the ellipse
   * @throws UnsupportedOperationException if the ellipse cannot be drawn in this view
   * @throws NullPointerException          if the Color is null
   * @throws IllegalArgumentException      if the width or height is negative
   */
  void drawEllipse(int x, int y, int w, int h, Color color)
      throws UnsupportedOperationException, NullPointerException, IllegalArgumentException;

  /**
   * <p>Draws a plus sign of the given width and height. The top left corner of the bounding box
   * surrounding the plus is located at (x,y).</p>
   *
   * @param x     x coordinate of top left corner of the plus
   * @param y     y coordinate of top left corner of the plus
   * @param w     width of the plus
   * @param h     height of the plus
   * @param color color of the plus
   * @throws UnsupportedOperationException if cannot draw shapes in the view
   * @throws NullPointerException          if the color is {@code null}
   * @throws IllegalArgumentException      if width or height are negative
   */
  void drawPlus(int x, int y, int w, int h, Color color)
      throws UnsupportedOperationException, NullPointerException, IllegalArgumentException;

  /**
   * Used to update the view after each frame.
   *
   * @throws UnsupportedOperationException if the canvas cannot be refreshed in this view.
   */
  void refresh() throws UnsupportedOperationException;

  /**
   * <p>Sets the "trailing" function of our view. Setting trails will allow for shapes to be drawn
   * with a trailing option, the view will not be refreshed each view and the shapes will layer on
   * top of each other during their transformation.</p>
   *
   * @param enabled true if want to enable trails, false otherwise.
   * @throws UnsupportedOperationException if the trails cannot be drawn in this view.
   */
  void setTrails(boolean enabled) throws UnsupportedOperationException;

}
