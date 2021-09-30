package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import cs3500.animator.model.AnimatorModel;
import cs3500.animator.model.IAnimatorModel;
import cs3500.animator.model.shape.IModelShape;
import cs3500.animator.model.shape.ModelShape;
import cs3500.animator.model.shape.ShapeType;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;
import java.util.List;

/**
 * Tests for {@link AnimatorModel}.
 */
public class AnimatorModelTest {

  /**
   * Factory method for creating blank {@link AnimatorModel}s.
   *
   * @return a new model
   */
  public IAnimatorModel<IModelShape> getModel() {
    return new AnimatorModel();
  }

  /**
   * <p>Factory method. Generates a cs3500.animator.model of the example simple animation.</p>
   *
   * @return a new simple animation cs3500.animator.model
   */
  public IAnimatorModel<IModelShape> getSimpleModel() {
    //# declares a rectangle shape named R
    //#                  start                           end
    //#        --------------------------    ----------------------------
    //#        t  x   y   w  h   r   g  b    t   x   y   w  h   rt  r   g  b alpha

    //shape R rectangle
    //motion R 1  200 200 50 100 255 0  0    10  200 200 50 100 255 0  0
    //motion R 10 200 200 50 100 255 0  0    50  300 300 50 100 255 0  0
    //motion R 50 300 300 50 100 255 0  0    51  300 300 50 100 255 0  0
    //motion R 51 300 300 50 100 255 0  0    70  300 300 25 100 255 0  0
    //motion R 70 300 300 25 100 255 0  0    100 200 200 25 100 255 0  0
    //
    //shape C ellipse
    //motion C 6  440 70 120 60 0 0 255      20  440 70  120 60 0 0 255
    //motion C 20 440 70 120 60 0 0 255      50  440 250 120 60 0 0 255
    //motion C 50 440 250 120 60 0 0 255     70  440 370 120 60 0 170 85
    //motion C 70 440 370 120 60 0 170 85    80  440 370 120 60 0 255  0
    //motion C 80 440 370 120 60 0 255  0    100 440 370 120 60 0 255  0

    IAnimatorModel<IModelShape> model = this.getModel();

    model.addShape("R", ShapeType.RECTANGLE);
    model.addKeyFrame(1, "R", 200, 200, 50, 100, Color.RED); // in place at 1
    model.addKeyFrame(10, "R", 200, 200, 50, 100, Color.RED);
    model.addKeyFrame(50, "R", 300, 300, 50, 100, Color.RED);
    model.addKeyFrame(51, "R", 300, 300, 50, 100, Color.RED);
    model.addKeyFrame(70, "R", 300, 300, 25, 100, Color.RED);
    model.addKeyFrame(100, "R", 200, 200, 25, 100, Color.RED);

    model.addShape("C", ShapeType.ELLIPSE);
    model.addKeyFrame(6, "C", 440, 70, 120, 60, Color.BLUE);
    model.addKeyFrame(20, "C", 440, 70, 120, 60, Color.BLUE);
    model.addKeyFrame(50, "C", 440, 250, 120, 60, Color.BLUE);
    model.addKeyFrame(70, "C", 440, 370, 120, 60, new Color(0, 170, 85));
    model.addKeyFrame(80, "C", 440, 370, 120, 60, new Color(0, 255, 0));
    model.addKeyFrame(100, "C", 440, 370, 120, 60, new Color(0, 255, 0));

    return model;
  }

  @Test
  public void getFrameNoShapes() {
    assertEquals(this.getSimpleModel().getFrame(0), new ArrayList<>());
  }

  @Test(expected = IllegalArgumentException.class)
  public void getFrameInvalidTickTooLarge() {
    this.getSimpleModel().getFrame(500);
  }

  @Test(expected = IllegalArgumentException.class)
  public void getFrameInvalidTickNegative() {
    this.getSimpleModel().getFrame(-1);
  }

  @Test
  public void getFrameAtStartTickOfTransition_SingleShape() {
    IModelShape r1 = new ModelShape(ShapeType.RECTANGLE, 200, 200, 50, 100, Color.RED);
    List<IModelShape> expected = new ArrayList<>(Collections.singletonList(r1));
    assertEquals(expected, this.getSimpleModel().getFrame(1));
  }

  @Test
  public void getFrameAtStartTickOfTransition_MultipleShapes1() {
    IModelShape r1 = new ModelShape(ShapeType.RECTANGLE, 200, 200, 50, 100, Color.RED);
    IModelShape e1 = new ModelShape(ShapeType.ELLIPSE, 440, 70, 120, 60, Color.BLUE);
    List<IModelShape> expected = new ArrayList<>(Arrays.asList(r1, e1));
    assertEquals(expected, this.getSimpleModel().getFrame(6));
  }

  @Test
  public void getFrameAtStartTickOfTransition_MultipleShapes2() {
    IModelShape r1 = new ModelShape(ShapeType.RECTANGLE, 300, 300, 50, 100, Color.RED);
    IModelShape e1 = new ModelShape(ShapeType.ELLIPSE, 440, 250, 120, 60, Color.BLUE);
    List<IModelShape> expected = new ArrayList<>(Arrays.asList(r1, e1));
    assertEquals(expected, this.getSimpleModel().getFrame(50));
  }

  @Test
  public void getFrameAtEndTickOfTransition_MultipleShapes() {
    IModelShape r1 = new ModelShape(ShapeType.RECTANGLE, 200, 200, 25, 100, Color.RED);
    IModelShape e1 = new ModelShape(ShapeType.ELLIPSE, 440, 370, 120, 60, Color.GREEN);
    List<IModelShape> expected = new ArrayList<>(Arrays.asList(r1, e1));
    assertEquals(expected, this.getSimpleModel().getFrame(100));
  }

  @Test
  public void getNextFrame() {
    IAnimatorModel<IModelShape> model = this.getSimpleModel();
    for (int f = 1; f < 51; f++) {
      model.getNextFrame();
    }
    IModelShape r1 = new ModelShape(ShapeType.RECTANGLE, 300, 300, 50, 100, Color.RED);
    IModelShape e1 = new ModelShape(ShapeType.ELLIPSE, 440, 250, 120, 60, Color.BLUE);
    List<IModelShape> expected = new ArrayList<>(Arrays.asList(r1, e1));
    assertEquals(expected, model.getNextFrame());
  }

  @Test(expected = NullPointerException.class)
  public void getKeyFramesNullId() {
    this.getSimpleModel().getKeyFrames(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void getKeyFramesInvalidId() {
    this.getSimpleModel().getKeyFrames("A");
  }

  @Test
  public void getKeyFramesEmpty() {
    IAnimatorModel<IModelShape> model = this.getModel();
    model.addShape("R", ShapeType.RECTANGLE);
    Map<Integer, IModelShape> actual = model.getKeyFrames("R");
    assertEquals(0, actual.values().size());
  }

  @Test
  public void getKeyFramesNonEmpty() {
    Map<Integer, IModelShape> actual = this.getSimpleModel().getKeyFrames("R");
    assertEquals(6, actual.values().size());
  }

  @Test(expected = NullPointerException.class)
  public void getShapeTypeNullId() {
    this.getSimpleModel().getShapeType(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void getShapeTypeInvalidId() {
    this.getSimpleModel().getShapeType("A");
  }

  @Test
  public void getShapeTypeRect() {
    assertEquals(ShapeType.RECTANGLE, this.getSimpleModel().getShapeType("R"));
  }

  @Test
  public void getShapeTypeEllipse() {
    assertEquals(ShapeType.ELLIPSE, this.getSimpleModel().getShapeType("C"));
  }

  @Test
  public void testInterpolateHalfway() {
    IAnimatorModel<IModelShape> model = getModel();
    model.addShape("R", ShapeType.RECTANGLE);
    model.addShape("C", ShapeType.ELLIPSE);
    model.addKeyFrame(0, "R", 100, 100, 50, 50, Color.BLUE);
    model.addKeyFrame(2, "R", 200, 200, 60, 60, Color.BLUE);
    IModelShape r1 = new ModelShape(ShapeType.RECTANGLE, 150, 150, 55, 55, Color.BLUE);

    List<IModelShape> expected = new ArrayList<>(Arrays.asList(r1));
    assertEquals(expected, model.getFrame(1));
  }

  @Test
  public void testInterpolate2() {
    IAnimatorModel<IModelShape> model = getModel();
    model.addShape("R", ShapeType.RECTANGLE);
    model.addShape("R2", ShapeType.RECTANGLE);
    model.addKeyFrame(0, "R", 100, 100, 50, 50, Color.BLUE);
    model.addKeyFrame(2, "R", 200, 200, 60, 60, Color.BLUE);
    IModelShape r1 = new ModelShape(ShapeType.RECTANGLE, 150, 150, 55, 55, Color.BLUE);

    model.addKeyFrame(4, "R2", 100, 100, 50, 50, Color.BLUE);
    model.addKeyFrame(9, "R2", 200, 200, 60, 60, Color.BLUE);
    IModelShape r2 = new ModelShape(ShapeType.RECTANGLE, 150, 150, 55, 55, Color.BLUE);

    List<IModelShape> expected = new ArrayList<>(Arrays.asList(r1));
    assertEquals(expected, model.getFrame(1));
  }

  @Test
  public void testInterpolate3() {
    IAnimatorModel<IModelShape> model = getModel();
    model.addShape("R", ShapeType.RECTANGLE);
    model.addShape("R2", ShapeType.RECTANGLE);
    model.addKeyFrame(0, "R", 100, 100, 50, 50, Color.BLUE);
    model.addKeyFrame(2, "R", 200, 200, 60, 60, Color.BLUE);
    IModelShape r1 = new ModelShape(ShapeType.RECTANGLE, 150, 150, 55, 55, Color.BLUE);

    model.addKeyFrame(4, "R2", 100, 100, 50, 50, Color.BLUE);
    model.addKeyFrame(9, "R2", 200, 200, 60, 60, Color.BLUE);
    IModelShape r2 = new ModelShape(ShapeType.RECTANGLE, 100, 100, 50, 50, Color.BLUE);

    List<IModelShape> expected = new ArrayList<>(Arrays.asList(r2));
    assertEquals(expected, model.getFrame(4));
  }

  @Test(expected = IllegalStateException.class)
  public void getNextFrameNoMoreFrames() {
    IAnimatorModel<IModelShape> model = this.getSimpleModel();
    while (model.hasNextFrame()) {
      model.getNextFrame();
    }
    model.getNextFrame();
  }

  @Test
  public void hasNextFrameTrue_StartOfAnimation() {
    IAnimatorModel<IModelShape> model = this.getSimpleModel();
    assertTrue(model.hasNextFrame());
  }

  @Test
  public void hasNextFrameFalse_StartOfAnimation() {
    IAnimatorModel<IModelShape> model = this.getModel();
    assertFalse(model.hasNextFrame());
  }

  @Test
  public void hasNextFrameTrue_MiddleOfAnimation() {
    IAnimatorModel<IModelShape> model = this.getSimpleModel();
    for (int f = 1; f < 25; f++) {
      model.getNextFrame();
    }
    assertTrue(model.hasNextFrame());
  }

  @Test
  public void hasNextFrameFalse() {
    IAnimatorModel<IModelShape> model = this.getSimpleModel();
    while (model.hasNextFrame()) {
      model.getNextFrame();
    }
    assertFalse(model.hasNextFrame());
  }

  @Test
  public void getStartTick() {
    IAnimatorModel<IModelShape> model = this.getSimpleModel();
    assertEquals(0, model.getStartTick());
  }

  @Test
  public void getStartTickNoKeyFrames() {
    IAnimatorModel<IModelShape> model = this.getModel();
    assertEquals(0, model.getStartTick());
  }

  @Test
  public void getEndTick() {
    IAnimatorModel<IModelShape> model = this.getSimpleModel();
    assertEquals(100, model.getEndTick());
  }

  @Test
  public void getEndTickNoKeyFrames() {
    IAnimatorModel<IModelShape> model = this.getModel();
    assertEquals(-1, model.getEndTick());
  }

  @Test
  public void getShapesNoShapes() {
    assertEquals(new ArrayList<>(), this.getModel().getShapeIds());
  }

  @Test
  public void testGetShapes() {
    IAnimatorModel<IModelShape> model = this.getSimpleModel();
    List<String> expected = new ArrayList<>(Arrays.asList("R", "C"));
    assertEquals(expected, model.getShapeIds());
  }

  @Test
  public void bringToFront1() {
    IAnimatorModel<IModelShape> model = this.getSimpleModel();
    model.bringToFront("C");
    List<String> expected = new ArrayList<>(Arrays.asList("R", "C"));
    assertEquals(expected, model.getShapeIds());
  }

  @Test
  public void bringToFront2() {
    IAnimatorModel<IModelShape> model = this.getSimpleModel();
    model.bringToFront("R");
    List<String> expected = new ArrayList<>(Arrays.asList("C", "R"));
    assertEquals(expected, model.getShapeIds());
  }

  @Test(expected = IllegalArgumentException.class)
  public void bringToFrontInvalidId() {
    IModelShape r1 = new ModelShape(ShapeType.RECTANGLE, 200, 200, 50, 100, Color.RED);
    IModelShape e1 = new ModelShape(ShapeType.ELLIPSE, 440, 70, 120, 60, Color.BLUE);
    IAnimatorModel<IModelShape> model = this.getSimpleModel();
    model.bringToFront("T");
  }

  @Test(expected = IllegalArgumentException.class)
  public void bringToFrontNoShapes() {
    IAnimatorModel<IModelShape> model = this.getModel();
    model.bringToFront("T");
  }

  @Test
  public void sendToBack1() {
    IAnimatorModel<IModelShape> model = this.getSimpleModel();
    model.sendToBack("C");
    List<String> expected = new ArrayList<>(Arrays.asList("C", "R"));
    assertEquals(expected, model.getShapeIds());
  }

  @Test
  public void sendToBack2() {
    IAnimatorModel<IModelShape> model = this.getSimpleModel();
    model.sendToBack("R");
    List<String> expected = new ArrayList<>(Arrays.asList("R", "C"));
    assertEquals(expected, model.getShapeIds());
  }

  @Test(expected = IllegalArgumentException.class)
  public void sendToBackInvalidId() {
    IModelShape r1 = new ModelShape(ShapeType.RECTANGLE, 200, 200, 50, 100, Color.RED);
    IModelShape e1 = new ModelShape(ShapeType.ELLIPSE, 440, 70, 120, 60, Color.BLUE);
    IAnimatorModel<IModelShape> model = this.getSimpleModel();
    model.sendToBack("T");
  }

  @Test(expected = IllegalArgumentException.class)
  public void sendToBackNoShapes() {
    IAnimatorModel<IModelShape> model = this.getModel();
    model.sendToBack("T");
  }

  @Test
  public void removeOneShape() {
    IAnimatorModel<IModelShape> model = this.getSimpleModel();
    model.removeShape("R");
    List<String> expected = new ArrayList<>(Arrays.asList("C"));
    assertEquals(expected, model.getShapeIds());
  }

  @Test
  public void removeAllShapes() {
    IAnimatorModel<IModelShape> model = this.getSimpleModel();
    List<String> shapes = new ArrayList<>();
    model.removeShape("R");
    model.removeShape("C");
    assertEquals(shapes, model.getShapeIds());
  }

  @Test(expected = IllegalArgumentException.class)
  public void removeShapeNoShapes() {
    IAnimatorModel<IModelShape> model = this.getModel();
    model.removeShape("R");
  }

  @Test(expected = IllegalArgumentException.class)
  public void removeShapeInvalidId() {
    IAnimatorModel<IModelShape> model = this.getSimpleModel();
    model.removeShape("pizza");
  }

  @Test
  public void addSingleShape() {
    IAnimatorModel<IModelShape> model = this.getModel();
    ArrayList<String> expectedShapeIds = new ArrayList<>();
    Map<String, ShapeType> expectedShapes = new HashMap<>();

    assertEquals(expectedShapeIds, model.getShapeIds());
    assertEquals(expectedShapes, model.getShapes());

    model.addShape("R", ShapeType.RECTANGLE);

    expectedShapeIds.add("R");
    expectedShapes.put("R", ShapeType.RECTANGLE);
    assertEquals(expectedShapeIds, model.getShapeIds());
    assertEquals(expectedShapes, model.getShapes());
  }

  @Test
  public void addMultipleShapes() {
    IAnimatorModel<IModelShape> model = this.getModel();
    ArrayList<String> expectedShapeIds = new ArrayList<>();
    Map<String, ShapeType> expectedShapes = new HashMap<>();

    assertEquals(expectedShapeIds, model.getShapeIds());
    assertEquals(expectedShapes, model.getShapes());

    model.addShape("R", ShapeType.RECTANGLE);
    model.addShape("C", ShapeType.ELLIPSE);

    expectedShapeIds.add("R");
    expectedShapeIds.add("C");
    expectedShapes.put("R", ShapeType.RECTANGLE);
    expectedShapes.put("C", ShapeType.ELLIPSE);
    assertEquals(expectedShapeIds, model.getShapeIds());
    assertEquals(expectedShapes, model.getShapes());
  }

  @Test(expected = IllegalArgumentException.class)
  public void addShapeIdConflicts() {
    IAnimatorModel<IModelShape> model = this.getModel();
    ArrayList<String> expectedShapeIds = new ArrayList<>();
    Map<String, ShapeType> expectedShapes = new HashMap<>();

    assertEquals(expectedShapeIds, model.getShapeIds());
    assertEquals(expectedShapes, model.getShapes());

    model.addShape("R", ShapeType.RECTANGLE);
    model.addShape("R", ShapeType.ELLIPSE);
  }

  @Test(expected = NullPointerException.class)
  public void addShapeNullId() {
    IAnimatorModel<IModelShape> model = this.getModel();
    ArrayList<String> expectedShapeIds = new ArrayList<>();
    Map<String, ShapeType> expectedShapes = new HashMap<>();

    assertEquals(expectedShapeIds, model.getShapeIds());
    assertEquals(expectedShapes, model.getShapes());

    model.addShape(null, ShapeType.RECTANGLE);
  }

  @Test(expected = NullPointerException.class)
  public void addShapeNullType() {
    IAnimatorModel<IModelShape> model = this.getModel();
    ArrayList<String> expectedShapeIds = new ArrayList<>();
    Map<String, ShapeType> expectedShapes = new HashMap<>();

    assertEquals(expectedShapeIds, model.getShapeIds());
    assertEquals(expectedShapes, model.getShapes());

    model.addShape("R", null);
  }

  @Test
  public void addKeyFrame() {
    IAnimatorModel<IModelShape> model = this.getModel();
    model.addShape("R", ShapeType.RECTANGLE);
    model.addKeyFrame(10, "R", 250, 250,
        25, 100, Color.RED);
    List<IModelShape> expectedFrame = new ArrayList<>();
    expectedFrame.add(new ModelShape(ShapeType.RECTANGLE, 250, 250,
        25, 100, Color.RED));
    assertEquals(expectedFrame, model.getFrame(10));
  }

  @Test(expected = IllegalArgumentException.class)
  public void addKeyFrameIdDoesNotExist() {
    IAnimatorModel<IModelShape> model = this.getModel();
    model.addKeyFrame(-2, "Q", 200, 200,
        25, 100, Color.RED);
  }

  @Test(expected = IllegalArgumentException.class)
  public void addKeyFrameInvalidTickNegative() {
    IAnimatorModel<IModelShape> model = this.getSimpleModel();
    model.addKeyFrame(-2, "R", 200, 200, 25, 100, Color.RED);
  }

  @Test(expected = IllegalArgumentException.class)
  public void addKeyFrameConflicts() {
    IAnimatorModel<IModelShape> model = this.getSimpleModel();
    model.addKeyFrame(70, "C", 400, 300, 120, 60, Color.CYAN);
  }

  @Test(expected = NullPointerException.class)
  public void addKeyFrameNullId() {
    IAnimatorModel<IModelShape> model = this.getModel();
    model.addKeyFrame(100, null, 200, 200,
        25, 100, Color.RED);
  }

  @Test
  public void removeKeyFrame() {
    IAnimatorModel<IModelShape> model = this.getSimpleModel();
    model.removeKeyFrame(20, "C");
    List<IModelShape> expected = new ArrayList<>();
    expected.add(new ModelShape(ShapeType.RECTANGLE, 300, 300,
        50, 100, Color.RED));
    expected.add(new ModelShape(ShapeType.ELLIPSE, 440, 250,
        120, 60, Color.BLUE));
    assertEquals(expected, model.getFrame(50));
  }

  @Test(expected = IllegalArgumentException.class)
  public void removeKeyFrameAlreadyRemoved() {
    IAnimatorModel<IModelShape> model = this.getSimpleModel();
    model.removeKeyFrame(10, "C");
    model.removeKeyFrame(10, "C");

  }

  @Test(expected = IllegalArgumentException.class)
  public void removeKeyFrameInvalidTick() {
    IAnimatorModel<IModelShape> model = this.getSimpleModel();
    model.removeKeyFrame(102, "C");
  }

  @Test(expected = IllegalArgumentException.class)
  public void removeKeyFrameInvalidId() {
    IAnimatorModel<IModelShape> model = this.getSimpleModel();
    model.removeKeyFrame(70, "A");
  }

  @Test(expected = NullPointerException.class)
  public void removeKeyFrameNullId() {
    IAnimatorModel<IModelShape> model = this.getSimpleModel();
    model.removeKeyFrame(70, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void findPrevKeyFrameInvalidId() {
    IAnimatorModel<IModelShape> model = this.getSimpleModel();
    model.findPrevKeyFrame(1, "A");
  }

  @Test(expected = NullPointerException.class)
  public void findPrevKeyFrameNullId() {
    IAnimatorModel<IModelShape> model = this.getSimpleModel();
    model.findPrevKeyFrame(1, null);
  }

  @Test
  public void findPrevKeyFrameNoPrev() {
    IAnimatorModel<IModelShape> model = this.getSimpleModel();
    assertEquals(-1, model.findPrevKeyFrame(6, "C"));
  }

  @Test
  public void findPrevKeyFrame() {
    IAnimatorModel<IModelShape> model = this.getSimpleModel();
    assertEquals(1, model.findPrevKeyFrame(10, "R"));
  }

  @Test
  public void findPrevKeyFrame2() {
    IAnimatorModel<IModelShape> model = this.getSimpleModel();
    assertEquals(70, model.findPrevKeyFrame(80, "C"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void findNextKeyFrameInvalidId() {
    IAnimatorModel<IModelShape> model = this.getSimpleModel();
    model.findNextKeyFrame(1, "A");
  }

  @Test(expected = NullPointerException.class)
  public void findNextKeyFrameNullId() {
    IAnimatorModel<IModelShape> model = this.getSimpleModel();
    model.findNextKeyFrame(1, null);
  }

  @Test
  public void findNextKeyFrameNoNext() {
    IAnimatorModel<IModelShape> model = this.getSimpleModel();
    assertEquals(-1, model.findNextKeyFrame(100, "C"));
  }

  @Test
  public void findNextKeyFrame() {
    IAnimatorModel<IModelShape> model = this.getSimpleModel();
    assertEquals(10, model.findNextKeyFrame(1, "R"));
  }

  @Test
  public void findNextKeyFrame2() {
    IAnimatorModel<IModelShape> model = this.getSimpleModel();
    assertEquals(80, model.findNextKeyFrame(70, "C"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void setBoundsZeroWidth() {
    this.getSimpleModel().setBounds(0, 0, 0, 600);
  }

  @Test(expected = IllegalArgumentException.class)
  public void setBoundsZeroHeight() {
    this.getSimpleModel().setBounds(0, 0, 800, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void setBoundsNegativeWidth() {
    this.getSimpleModel().setBounds(0, 10, -50, 600);
  }

  @Test(expected = IllegalArgumentException.class)
  public void setBoundsNegativeHeight() {
    this.getSimpleModel().setBounds(10, 0, 800, -600);
  }

  @Test(expected = IllegalArgumentException.class)
  public void setBoundsNegativeX() {
    this.getSimpleModel().setBounds(-1, 10, 50, 600);
  }

  @Test(expected = IllegalArgumentException.class)
  public void setBoundsNegativeY() {
    this.getSimpleModel().setBounds(10, -10, 800, 600);
  }

  @Test
  public void testDefaultBounds() {
    IAnimatorModel<IModelShape> model = this.getSimpleModel();

    assertEquals(0, model.getX());
    assertEquals(0, model.getY());
    assertEquals(800, model.getWidth());
    assertEquals(600, model.getHeight());
  }

  @Test
  public void setBounds() {
    IAnimatorModel<IModelShape> model = this.getSimpleModel();
    model.setBounds(5, 0, 100, 200);

    assertEquals(5, model.getX());
    assertEquals(0, model.getY());
    assertEquals(100, model.getWidth());
    assertEquals(200, model.getHeight());
  }

  @Test
  public void testToString() {
    String expected =
        "shape R rectangle\n"
            + "motion R 1 200 200 50 100 255 0 0 1 200 200 50 100 255 0 0\n"
            + "motion R 1 200 200 50 100 255 0 0 10 200 200 50 100 255 0 0\n"
            + "motion R 10 200 200 50 100 255 0 0 50 300 300 50 100 255 0 0\n"
            + "motion R 50 300 300 50 100 255 0 0 51 300 300 50 100 255 0 0\n"
            + "motion R 51 300 300 50 100 255 0 0 70 300 300 25 100 255 0 0\n"
            + "motion R 70 300 300 25 100 255 0 0 100 200 200 25 100 255 0 0\n"
            + "\n"
            + "shape C ellipse\n"
            + "motion C 6 440 70 120 60 0 0 255 6 440 70 120 60 0 0 255\n"
            + "motion C 6 440 70 120 60 0 0 255 20 440 70 120 60 0 0 255\n"
            + "motion C 20 440 70 120 60 0 0 255 50 440 250 120 60 0 0 255\n"
            + "motion C 50 440 250 120 60 0 0 255 70 440 370 120 60 0 170 85\n"
            + "motion C 70 440 370 120 60 0 170 85 80 440 370 120 60 0 255 0\n"
            + "motion C 80 440 370 120 60 0 255 0 100 440 370 120 60 0 255 0\n"
            + "\n";

    assertEquals(expected, this.getSimpleModel().toString());
  }

}