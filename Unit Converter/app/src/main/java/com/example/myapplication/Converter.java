package com.example.myapplication;

public class Converter {
  public enum Unit {
    MILLIMETER,
    CENTIMETER,
    METER,
    KILOMETER,
    INCH,
    FOOT,
    YARD,
    MILE;

    public static Unit fromString(String text) {
      if (text != null) {
        for (Unit unit : Unit.values()) {
          if (text.equalsIgnoreCase(unit.toString())) {
            return unit;
          }
        }
      }

      throw new IllegalArgumentException("Cannot find a value for " + text);
    }
  }

  private final double multiplier;

  public Converter(Unit from, Unit to) {

    multiplier = getRate(from, to);
  }

  public double getRate(Unit from, Unit to) {
    double constant = 1;

    switch (from) {
      case MILLIMETER:
        constant = (1 / getRate(Unit.KILOMETER, Unit.MILLIMETER)) * getRate(Unit.KILOMETER, to);
        break;
      case CENTIMETER:
        constant = (1 / getRate(Unit.KILOMETER, Unit.CENTIMETER)) * getRate(Unit.KILOMETER, to);
        break;
      case METER:
        constant = (1 / getRate(Unit.KILOMETER, Unit.METER)) * getRate(Unit.KILOMETER, to);
        break;
      case KILOMETER:
        if (to == Unit.MILLIMETER) {
          constant = 1000000;
        } else if (to == Unit.CENTIMETER) {
          constant = 100000;
        } else if (to == Unit.METER) {
          constant = 1000;
        } else if (to == Unit.INCH) {
          constant = 39370.079;
        } else if (to == Unit.FOOT) {
          constant = 3280.84;
        } else if (to == Unit.YARD) {
          constant = 1093.6133;
        } else if (to == Unit.MILE) {
          constant = 0.6213712;
        }
        break;
      case INCH:
        constant = (1 / getRate(Unit.MILE, Unit.INCH)) * getRate(Unit.MILE, to);
        break;
      case FOOT:
        constant = (1 / getRate(Unit.MILE, Unit.FOOT)) * getRate(Unit.MILE, to);
        break;
      case YARD:
        constant = (1 / getRate(Unit.MILE, Unit.YARD)) * getRate(Unit.MILE, to);
        break;
      case MILE:
        if (to == Unit.MILLIMETER) {
          constant = 1609344;
        } else if (to == Unit.CENTIMETER) {
          constant = 160934.4;
        } else if (to == Unit.METER) {
          constant = 1609.344;
        } else if (to == Unit.KILOMETER) {
          constant = 1.609344;
        } else if (to == Unit.INCH) {
          constant = 63360;
        } else if (to == Unit.FOOT) {
          constant = 5280;
        } else if (to == Unit.YARD) {
          constant = 1760;
        }
        break;
    }

    return constant;
  }

  public double convert(double input) {
    return input * multiplier;
  }
}
