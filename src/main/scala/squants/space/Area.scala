/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2014, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.space

import squants._
import squants.motion.Newtons
import squants.electro.{ MagneticFlux, Webers, MagneticFluxDensity }
import squants.photo._
import squants.energy.Watts
import squants.radio._
import squants.Candelas
import squants.radio.Irradiance
import squants.photo.Luminance
import squants.motion.Pressure
import squants.radio.Radiance
import squants.mass.AreaDensity

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 * @param value value in [[squants.space.SquareMeters]]
 */
final class Area private (val value: Double)
    extends Quantity[Area] {

  def valueUnit = Area.valueUnit

  def *(that: Length): Volume = CubicMeters(toSquareMeters * that.toMeters)
  def *(that: AreaDensity): Mass = Kilograms(toSquareMeters * that.toKilogramsPerSquareMeter)
  def *(that: Pressure): Force = Newtons(toSquareMeters * that.toPascals)
  def *(that: Illuminance): LuminousFlux = Lumens(toSquareMeters * that.toLux)
  def *(that: Luminance): LuminousIntensity = Candelas(toSquareMeters * that.toCandelasPerSquareMeters)
  def *(that: MagneticFluxDensity): MagneticFlux = Webers(toSquareMeters * that.toTeslas)
  def *(that: Irradiance): Power = Watts(toSquareMeters * that.toWattsPerSquareMeter)
  def *(that: Radiance): RadiantIntensity = WattsPerSteradian(toSquareMeters * that.toWattsPerSteradianPerSquareMeter)
  def /(that: Length): Length = Meters(toSquareMeters / that.toMeters)

  def toSquareMeters = to(SquareMeters)
  def toSquareCentimeters = to(SquareCentimeters)
  def toSquareKilometers = to(SquareKilometers)
  def toSquareUsMiles = to(SquareUsMiles)
  def toSquareYards = to(SquareYards)
  def toSquareFeet = to(SquareFeet)
  def toSquareInches = to(SquareInches)
  def toHectares = to(Hectares)
  def toAcres = to(Acres)
  def toBarnes = to(Barnes)
}

object Area extends QuantityCompanion[Area] {
  private[space] def apply[A](n: A)(implicit num: Numeric[A]) = new Area(num.toDouble(n))
  def apply(length1: Length, length2: Length): Area = apply(length1.toMeters * length2.toMeters)
  def apply = parseString _
  def name = "Area"
  def valueUnit = SquareMeters
  def units = Set(SquareMeters, SquareCentimeters, SquareKilometers,
    SquareUsMiles, SquareYards, SquareFeet, SquareInches,
    Hectares, Acres, Barnes)
}

trait AreaUnit extends UnitOfMeasure[Area] with UnitConverter {
  def apply[A](n: A)(implicit num: Numeric[A]) = Area(convertFrom(n))
}

object SquareMeters extends AreaUnit with ValueUnit {
  val symbol = "m²"
}

object SquareCentimeters extends AreaUnit {
  val symbol = "cm²"
  val conversionFactor = MetricSystem.Centi * MetricSystem.Centi
}

object SquareKilometers extends AreaUnit {
  val symbol = "km²"
  val conversionFactor = MetricSystem.Kilo * MetricSystem.Kilo
}

object SquareUsMiles extends AreaUnit {
  val symbol = "mi²"
  val conversionFactor = 2.589988110336 * SquareKilometers.conversionFactor
}

object SquareYards extends AreaUnit {
  val symbol = "yd²"
  val conversionFactor = 0.83612736
}

object SquareFeet extends AreaUnit {
  val symbol = "ft²"
  val conversionFactor = .09290304
}

object SquareInches extends AreaUnit {
  val symbol = "in²"
  val conversionFactor = 6.4516 * SquareCentimeters.conversionFactor
}

object Hectares extends AreaUnit {
  val symbol = "ha"
  val conversionFactor = 10000D
}

object Acres extends AreaUnit {
  val symbol = "acre"
  val conversionFactor = 43560 * SquareFeet.conversionFactor
}

object Barnes extends AreaUnit {
  val symbol = "b"
  val conversionFactor = scala.math.pow(10, -28)
}

object AreaConversions {
  lazy val squareMeter = SquareMeters(1)
  lazy val squareCentimeter = SquareCentimeters(1)
  lazy val squareKilometer = SquareKilometers(1)
  lazy val squareMile = SquareUsMiles(1)
  lazy val squareYard = SquareYards(1)
  lazy val squareFoot = SquareFeet(1)
  lazy val squareInch = SquareInches(1)
  lazy val hectare = Hectares(1)
  lazy val acre = Acres(1)
  lazy val barne = Barnes(1)

  implicit class AreaConversions[A](n: A)(implicit num: Numeric[A]) {
    def squareMeters = SquareMeters(n)
    def squareCentimeters = SquareCentimeters(n)
    def squareKilometers = SquareKilometers(n)
    def squareMiles = SquareUsMiles(n)
    def squareYards = SquareYards(n)
    def squareFeet = SquareFeet(n)
    def squareInches = SquareInches(n)
    def hectares = Hectares(n)
    def acres = Acres(n)
    def barnes = Barnes(n)
  }

  implicit object AreaNumeric extends AbstractQuantityNumeric[Area](Area.valueUnit)
}
