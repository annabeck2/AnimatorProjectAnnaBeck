package cs3500.animator.model.shape;

import java.awt.Color;
import java.util.List;

/**
 * <p>Represents a shape with attributes: type, x/y position, size, and color.</p>
 */
public interface IModelShape {

  /**
   * Return the type of this shape.
   * @return the type of this shape
   */
  ShapeType getType();

  /**
   * Return the x position of this shape.
   * @return the x position of this shape
   */
  double getX();

  /**
   * Return the y position of this shape.
   * @return the y position of this shape
   */
  double getY();

  /**
   * Returns the width of the shape.
   *
   * @return the width of the shape
   */
  double getWidth();

  /**
   * Returns the height of the shape.
   *
   * @return the height of the shape
   */
  double getHeight();

  /**
   * Return the color of the shape.
   *
   * @return the color of the shape
   */
  Color getColor();

  /**
   * <p>Return a list of this shape's attributes in order: x position, y position, width, height,
   * red value, green value, blue value. Useful for interpolation.</p>
   *
   * @return a list of this shape's attributes
   */
  List<Double> getAttributes();

  /**
   * Get a string representation of this shape's numerical attributes.
   *
   * @return a string representation of this shape's attributes
   */
  String printState();

  @Override
  int hashCode();

  @Override
  boolean equals(Object obj);

}
