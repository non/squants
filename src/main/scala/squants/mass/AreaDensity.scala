/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2014, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.mass

import squants._

/**
 * @author  garyKeorkunian
 * @since   0.2.3
 *
 * @param value Double
 */
final class AreaDensity private (val value: Double) extends Quantity[AreaDensity] {

  def valueUnit = AreaDensity.valueUnit

  def *(that: Area): Mass = Kilograms(value * that.toSquareMeters)

  def toKilogramsPerSquareMeter = to(KilogramsPerSquareMeter)
}

/**
 * Factory singleton for [[squants.mass.AreaDensity]] values
 */
object AreaDensity extends QuantityCompanion[AreaDensity] {
  private[mass] def apply[A](n: A)(implicit num: Numeric[A]) = new AreaDensity(num.toDouble(n))
  def apply(mass: Mass, area: Area): AreaDensity = KilogramsPerSquareMeter(mass.toKilograms / area.toSquareMeters)
  def apply = parseString _
  def name = "AreaDensity"
  def valueUnit = KilogramsPerSquareMeter
  def units = Set(KilogramsPerSquareMeter)
}

trait AreaDensityUnit extends UnitOfMeasure[AreaDensity] {
  def apply[A](n: A)(implicit num: Numeric[A]) = AreaDensity(convertFrom(n))
}

object KilogramsPerSquareMeter extends AreaDensityUnit with ValueUnit {
  val symbol = "kg/m²"
}

object AreaDensityConversions {
  lazy val kilogramPerSquareMeter = KilogramsPerSquareMeter(1)

  implicit class AreaDensityConversions[A](n: A)(implicit num: Numeric[A]) {
    def kilogramsPerSquareMeter = KilogramsPerSquareMeter(n)
  }

  implicit object AreaDensityNumeric extends AbstractQuantityNumeric[AreaDensity](AreaDensity.valueUnit)
}