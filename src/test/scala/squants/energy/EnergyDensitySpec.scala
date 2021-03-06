/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2014, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.energy

import org.scalatest.{ Matchers, FlatSpec }
import scala.language.postfixOps
import squants.space.CubicMeters
import squants.QuantityStringParseException

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
class EnergyDensitySpec extends FlatSpec with Matchers {

  behavior of "EnergyDensity and its Units of Measure"

  it should "create values using UOM factories" in {
    JoulesPerCubicMeter(1).toJoulesPerCubicMeter should be(1)
  }

  it should "create values from properly formatted Strings" in {
    EnergyDensity("10.22 j/m³").get should be(JoulesPerCubicMeter(10.22))
    EnergyDensity("10.22 zz").failed.get should be(QuantityStringParseException("Unable to parse EnergyDensity", "10.22 zz"))
    EnergyDensity("ZZ j/m³").failed.get should be(QuantityStringParseException("Unable to parse EnergyDensity", "ZZ j/m³"))
  }

  it should "properly convert to all supported Units of Measure" in {
    val x = JoulesPerCubicMeter(1)

    x.toJoulesPerCubicMeter should be(1)
  }

  it should "return properly formatted strings for all supported Units of Measure" in {
    JoulesPerCubicMeter(1).toString(JoulesPerCubicMeter) should be("1.0 j/m³")
  }

  it should "return Energy when multiplied by Volume" in {
    JoulesPerCubicMeter(1) * CubicMeters(10) should be(Joules(10))
  }

  behavior of "EnergyDensityConversions"

  it should "provide aliases for single unit values" in {
    import EnergyDensityConversions._

    joulePerCubicMeter should be(JoulesPerCubicMeter(1))
  }

  it should "provide implicit conversion from Double" in {
    import EnergyDensityConversions._

    val d = 10.22d
    d.joulesPerCubicMeter should be(JoulesPerCubicMeter(d))
  }

  it should "provide Numeric support" in {
    import EnergyDensityConversions.EnergyDensityNumeric

    val eds = List(JoulesPerCubicMeter(10), JoulesPerCubicMeter(100))
    eds.sum should be(JoulesPerCubicMeter(110))
  }
}
