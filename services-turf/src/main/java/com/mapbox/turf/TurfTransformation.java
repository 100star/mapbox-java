package com.mapbox.turf;

import android.support.annotation.NonNull;
import com.mapbox.geojson.Point;
import com.mapbox.geojson.Polygon;
import com.mapbox.turf.TurfConstants.TurfUnitCriteria;

import java.util.ArrayList;
import java.util.List;

/**
 * Methods in this class consume one GeoJSON object and output a new object with the defined
 * parameters provided.
 *
 * @since 3.0.0
 */
public final class TurfTransformation {

  private static final int DEFAULT_STEPS = 64;

  private TurfTransformation() {
    // Empty constructor to prevent class initialization
  }

  /**
   * Takes a {@link Point} and calculates the circle polygon given a radius in degrees, radians,
   * miles, or kilometers; and steps for precision. This uses the {@link #DEFAULT_STEPS} and
   * {@link TurfConstants#UNIT_DEFAULT} values.
   *
   * @param center a {@link Point} which the circle will center around
   * @param radius the radius of the circle
   * @return a {@link Polygon} which represents the newly created circle
   * @since 3.0.0
   */
  public static Polygon circle(@NonNull Point center, double radius) {
    return circle(center, radius, 64, TurfConstants.UNIT_DEFAULT);
  }

  /**
   * Takes a {@link Point} and calculates the circle polygon given a radius in degrees, radians,
   * miles, or kilometers; and steps for precision. This uses the {@link #DEFAULT_STEPS}.
   *
   * @param center a {@link Point} which the circle will center around
   * @param radius the radius of the circle
   * @param units  one of the units found inside {@link TurfUnitCriteria}
   * @return a {@link Polygon} which represents the newly created circle
   * @since 3.0.0
   */
  public static Polygon circle(@NonNull Point center, double radius,
                               @TurfUnitCriteria String units) {
    return circle(center, radius, DEFAULT_STEPS, units);
  }

  /**
   * Takes a {@link Point} and calculates the circle polygon given a radius in degrees, radians,
   * miles, or kilometers; and steps for precision.
   *
   * @param center a {@link Point} which the circle will center around
   * @param radius the radius of the circle
   * @param steps  number of steps which make up the circle parameter
   * @param units  one of the units found inside {@link TurfUnitCriteria}
   * @return a {@link Polygon} which represents the newly created circle
   * @since 3.0.0
   */
  public static Polygon circle(@NonNull Point center, double radius, int steps,
                               @TurfUnitCriteria String units) {
    List<Point> coordinates = new ArrayList<>();
    for (int i = 0; i < steps; i++) {
      coordinates.add(TurfMeasurement.destination(center, radius, i * 360 / steps, units));
    }

    List<List<Point>> coordinate = new ArrayList<>();
    coordinate.add(coordinates);
    return Polygon.fromLngLats(coordinate);
  }
}
