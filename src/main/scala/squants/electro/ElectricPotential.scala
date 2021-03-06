/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2014, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.electro

import squants._
import squants.energy.{ Joules, Watts }
import squants.time.{ Seconds, TimeDerivative }

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 * @param value value in [[squants.electro.Volts]]
 */
final class ElectricPotential private (val value: Double) extends Quantity[ElectricPotential]
    with TimeDerivative[MagneticFlux] {

  def valueUnit = ElectricPotential.valueUnit
  protected def timeIntegrated = Webers(toVolts)
  protected[squants] def time = Seconds(1)

  def *(that: ElectricCurrent): Power = Watts(toVolts * that.toAmperes)
  def *(that: Capacitance): ElectricCharge = Coulombs(toVolts * that.toFarads)
  def *(that: ElectricCharge): Energy = Joules(toVolts * that.toCoulombs)

  def /(that: ElectricCurrent): ElectricalResistance = Ohms(toVolts / that.toAmperes)
  def /(that: ElectricalResistance): ElectricCurrent = Amperes(toVolts / that.toOhms)
  def /(that: Length) = ??? // returns ElectricFieldStrength

  def toVolts = to(Volts)
  def toMircovolts = to(Microvolts)
  def toMillivolts = to(Millivolts)
  def toKilovolts = to(Kilovolts)
  def toMegavolts = to(Megavolts)
}

object ElectricPotential extends QuantityCompanion[ElectricPotential] {
  private[electro] def apply[A](n: A)(implicit num: Numeric[A]) = new ElectricPotential(num.toDouble(n))
  def apply = parseString _
  def name = "ElectricPotential"
  def valueUnit = Volts
  def units = Set(Volts, Microvolts, Millivolts, Kilovolts, Megavolts)
}

trait ElectricPotentialUnit extends UnitOfMeasure[ElectricPotential] with UnitConverter {
  def apply[A](n: A)(implicit num: Numeric[A]) = ElectricPotential(convertFrom(n))
}

object Volts extends ElectricPotentialUnit with ValueUnit {
  val symbol = "V"
}

object Microvolts extends ElectricPotentialUnit {
  val symbol = "μV"
  val conversionFactor = MetricSystem.Micro
}

object Millivolts extends ElectricPotentialUnit {
  val symbol = "mV"
  val conversionFactor = MetricSystem.Milli
}

object Kilovolts extends ElectricPotentialUnit {
  val symbol = "kV"
  val conversionFactor = MetricSystem.Kilo
}

object Megavolts extends ElectricPotentialUnit {
  val symbol = "MV"
  val conversionFactor = MetricSystem.Mega
}

object ElectricPotentialConversions {
  lazy val volt = Volts(1)
  lazy val microvolt = Microvolts(1)
  lazy val millivolt = Millivolts(1)
  lazy val kilovolt = Kilovolts(1)
  lazy val megavolt = Megavolts(1)

  implicit class ElectricPotentialConversions[A](n: A)(implicit num: Numeric[A]) {
    def V = Volts(n)
    def volts = Volts(n)
    def microvolts = Microvolts(n)
    def millivolts = Millivolts(n)
    def kilovolts = Kilovolts(n)
    def megavolts = Megavolts(n)
  }

  implicit object ElectricPotentialNumeric extends AbstractQuantityNumeric[ElectricPotential](ElectricPotential.valueUnit)
}

