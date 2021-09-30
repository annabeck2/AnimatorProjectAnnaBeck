package cs3500.animator.view;

import cs3500.animator.view.panel.DrawingPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.Objects;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

/**
 * <p>A view for displaying and playing animations graphically.</p>
 */
public class VisualAnimationView extends JFrame implements IAnimatorView {

  protected final DrawingPanel drawingPanel;

  /**
   * <p>Constructs a visual view for displaying the model.</p>
   *
   * @param x      x-position on screen to place the frame
   * @param y      y-position on screen to place the frame
   * @param width  width of the canvas
   * @param height height of the canvas
   * @throws NullPointerException     if the model is {@code null}.
   * @throws IllegalArgumentException if width or height are <= zero
   */
  public VisualAnimationView(int x, int y, int width, int height)
      throws NullPointerException, IllegalArgumentException {
    super();
    if (width <= 0 || height <= 0) {
      throw new IllegalArgumentException("Width and height must be positive!");
    }
    setTitle("Excellence v4.0 (c) 2021 Jonah and Anna");
    setLocation(x, y);
    this.drawingPanel = new DrawingPanel(width, height);
    this.drawingPanel.setBorder(BorderFactory.createTitledBorder("Animation"));

    initGui();

    setSize(new Dimension(800, 600));
    setDefaultCloseOperation(EXIT_ON_CLOSE);
  }

  protected void initGui() {
    this.setLayout(new BorderLayout());
    add(new JScrollPane(this.drawingPanel));
  }

  @Override
  public void render() {
    this.setVisible(true);
  }

  @Override
  public void drawRectangle(int x, int y, int w, int h, Color color)
      throws UnsupportedOperationException, NullPointerException, IllegalArgumentException {
    Objects.requireNonNull(color);
    if (w < 0 || h < 0) {
      throw new IllegalArgumentException("Must have non-negative values for width and height!");
    }
    this.drawingPanel.drawRectangle(x, y, w, h, color);
  }

  @Override
  public void drawEllipse(int x, int y, int w, int h, Color color)
      throws UnsupportedOperationException, NullPointerException, IllegalArgumentException {
    Objects.requireNonNull(color);
    if (w < 0 || h < 0) {
      throw new IllegalArgumentException("Must have non-negative values for width and height!");
    }
    this.drawingPanel.drawEllipse(x, y, w, h, color);
  }

  @Override
  public void drawPlus(int x, int y, int w, int h, Color color)
      throws UnsupportedOperationException, NullPointerException, IllegalArgumentException {
    Objects.requireNonNull(color);
    if (w < 0 || h < 0) {
      throw new IllegalArgumentException("Must have non-negative values for width and height!");
    }
    this.drawingPanel.drawPlus(x, y, w, h, color);
  }

  @Override
  public void refresh() throws UnsupportedOperationException {
    this.drawingPanel.repaint();
  }

  @Override
  public void setTrails(boolean enabled) throws UnsupportedOperationException {
    this.drawingPanel.setTrails(enabled);
  }

}