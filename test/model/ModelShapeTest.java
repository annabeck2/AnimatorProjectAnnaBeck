package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotSame;

import cs3500.animator.model.shape.ShapeType;
import java.awt.Color;

import cs3500.animator.model.shape.IModelShape;
import cs3500.animator.model.shape.ModelShape;
import org.junit.Test;

/**
 * Tests for {@link ModelShape}s.
 */
public abstract class ModelShapeTest {

  /**
   * Factory method for creating {@link ModelShape}s.
   *
   * @param x      the x coordinate of the shape
   * @param y      the y coordinate of the shape
   * @param width  the width of the shape
   * @param height the height of the shape
   * @param color  the color of the shape
   * @return a new {@link ModelShape}
   */
  public abstract IModelShape createShape(double x, double y, double width, double height,
      Color color);

  /**
   * Tests for shapes of type ELLIPSE.
   */
  public static class EllipseTest extends ModelShapeTest {

    ShapeType type = ShapeType.ELLIPSE;

    @Override
    public IModelShape createShape(double x, double y, double width, double height, Color color) {
      return new ModelShape(type, x, y, width, height, color);
    }
  }

  /**
   * Tests for shapes of type RECTANGLE.
   */
  public static class RectangleTest extends ModelShapeTest {

    ShapeType type = ShapeType.RECTANGLE;

    @Override
    public IModelShape createShape(double x, double y, double width, double height, Color color) {
      return new ModelShape(type, x, y, width, height, color);
    }
  }


  /**
   * Tests for shapes of type PLUS.
   */
  public static class PlusTest extends ModelShapeTest {

    ShapeType type = ShapeType.PLUS;

    @Override
    public IModelShape createShape(double x, double y, double width, double height, Color color) {
      return new ModelShape(type, x, y, width, height, color);
    }
  }

  @Test(expected = NullPointerException.class)
  public void testNullColor() {
    this.createShape(5, 5, 10, 10, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNegativeWidth() {
    this.createShape(0, 0, -5, 10, Color.BLACK);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNegativeHeight() {
    this.createShape(0, 0, 10, -5, Color.BLACK);
  }

  @Test
  public void testGetTypeRect() {
    IModelShape rect = new ModelShape(ShapeType.RECTANGLE, 0, 0, 50, 50, Color.BLACK);
    assertEquals(ShapeType.RECTANGLE, rect.getType());
  }

  @Test
  public void testGetTypeEllipse() {
    IModelShape ellipse = new ModelShape(ShapeType.ELLIPSE, 0, 0, 50, 50, Color.BLACK);
    assertEquals(ShapeType.ELLIPSE, ellipse.getType());
  }

  @Test
  public void testGetX() {
    IModelShape shape1 = this.createShape(0, 3, 10, 20, Color.BLACK);
    assertEquals(0, (int) shape1.getX(), 0.001);
  }

  @Test
  public void testGetY() {
    IModelShape shape1 = this.createShape(0, 3, 10, 20, Color.BLACK);
    assertEquals(3, shape1.getY(), 0.001);
  }

  @Test
  public void testGetWidth() {
    IModelShape shape1 = this.createShape(0, 0, 10, 20, Color.BLACK);
    assertEquals(10, shape1.getWidth(), 0.001);
  }

  @Test
  public void testGetHeight() {
    IModelShape shape1 = this.createShape(0, 0, 10, 20, Color.BLACK);
    assertEquals(20, shape1.getHeight(), 0.001);
  }

  @Test
  public void testGetColor() {
    IModelShape shape1 = this.createShape(0, 0, 10, 20, Color.BLACK);
    assertEquals(Color.BLACK, shape1.getColor());
  }

  @Test
  public void testEqualsSameType() {
    IModelShape shape1 = this.createShape(0, 0, 10, 20, Color.BLACK);
    IModelShape shape2 = this.createShape(0, 0, 10, 20, Color.BLACK);
    assertNotSame(shape1, shape2);
    assertEquals(shape1, shape2);
  }

  @Test
  public void testNotEqualsDifferentType() {
    IModelShape shape1 = new ModelShape(ShapeType.RECTANGLE, 0, 0, 10, 20, Color.BLACK);
    IModelShape shape2 = new ModelShape(ShapeType.ELLIPSE, 0, 0, 10, 20, Color.BLACK);

    assertNotSame(shape1, shape2);
    assertNotEquals(shape1, shape2);
  }

  @Test
  public void testPrintState() {
    String expected = "0 0 10 20 255 255 255";
    IModelShape shape1 = this.createShape(0, 0, 10, 20, Color.WHITE);
    assertEquals(expected, shape1.printState());
  }

  @Test
  public void testToString() {
    String expected = "rectangle 0 0 10 20 255 255 255";
    IModelShape shape1 = new ModelShape(ShapeType.RECTANGLE, 0, 0, 10, 20, Color.WHITE);
    assertEquals(expected, shape1.toString());
  }
}
