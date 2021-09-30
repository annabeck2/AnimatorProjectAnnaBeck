package cs3500.animator.model.shape;

import java.awt.Color;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * <p>Represents the state of a {@code shape}. Includes information about the shape's type, center
 * position, dimensions, rotation, color, and visibility.</p>
 */
public class ModelShape implements IModelShape {

  private final ShapeType type;
  private final double x;
  private final double y;
  // x and y are the (X,Y) coordinates of the shape in 2D space
  private final double width;
  private final double height;
  // width and height correspond to the dimensions of the smallest possible bounding box
  // needed to fully enclose the shape
  private final Color color;

  /**
   * Constructs a {@code Shape} object.
   *
   * @param type   the type of shape
   * @param x      the x coordinate of the shape
   * @param y      the y coordinate of the shape
   * @param width  the width of the shape
   * @param height the height of the shape
   * @param color  the color of the shape
   * @throws NullPointerException if any of the parameters are null
   */
  public ModelShape(ShapeType type, double x, double y, double width, double height,
                    Color color) throws IllegalArgumentException, NullPointerException {
    this.type = Objects.requireNonNull(type);
    this.color = Objects.requireNonNull(color);
    if (width < 0 || height < 0) {
      throw new IllegalArgumentException("Dimensions cannot be negative!");
    }
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
  }

  @Override
  public ShapeType getType() {
    return this.type;
  }

  @Override
  public double getX() {
    return this.x;
  }

  @Override
  public double getY() {
    return this.y;
  }

  @Override
  public double getWidth() {
    return this.width;
  }

  @Override
  public double getHeight() {
    return this.height;
  }

  @Override
  public Color getColor() {
    return this.color;
  }

  @Override
  public List<Double> getAttributes() {
    return new ArrayList<>(Arrays.asList(
        this.x,
        this.y,
        this.width,
        this.height,
        (double) this.color.getRed(),
        (double) this.color.getGreen(),
        (double) this.color.getBlue()));
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.type, this.x, this.y, this.width, this.height, this.color);
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof ModelShape)) {
      return false;
    }
    ModelShape that = (ModelShape) obj;
    return that.type.equals(this.type)
        && that.x == this.x
        && that.y == this.y
        && that.width == this.width
        && that.height == this.height
        && that.color.equals(this.color);
  }

  @Override
  public String printState() {
    DecimalFormat format = new DecimalFormat("0.#"); // to remove trailing zeroes
    String x = format.format(this.x);
    String y = format.format(this.y);
    String width = format.format(this.width);
    String height = format.format(this.height);

    return String.format("%s %s %s %s %d %d %d",
        x,
        y,
        width,
        height,
        this.color.getRed(), this.color.getGreen(), this.color.getBlue());
  }

  @Override
  public String toString() {
    return this.type.toString().toLowerCase() + " " + this.printState();
  }
}
