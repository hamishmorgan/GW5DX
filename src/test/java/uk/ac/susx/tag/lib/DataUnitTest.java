package uk.ac.susx.tag.lib;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Suite;

import java.math.BigInteger;
import java.util.Iterator;

import static java.text.MessageFormat.format;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: hiam20
 * Date: 05/04/2013
 * Time: 10:51
 * To change this template use File | Settings | File Templates.
 */
@RunWith(org.junit.runners.Suite.class)
@Suite.SuiteClasses({
        DataUnitTest.AllPairs.class
})
public class DataUnitTest {

    public static final Logger LOG = Logger.getLogger(DataUnit.class);
    @Rule
    public final TestName testName = new TestName();

    @Before
    public final void printTestName() {
        LOG.info(format("Runnning test: {0}#{1}", this.getClass().getName(), testName.getMethodName()));
    }

    @RunWith(Parameterized.class)
    public static class AllPairs extends DataUnitTest {
        private static final long[] values = {0L, 1L, 2L, 10L, Long.MAX_VALUE};
        private final DataUnit unitA;
        private final DataUnit unitB;
        private final Long value;

        public AllPairs(DataUnit unitA, DataUnit unitB, Long value) {
            this.unitA = unitA;
            this.unitB = unitB;
            this.value = value;
        }

        @Parameterized.Parameters(name = "{index}: {0} <=> {1}")
        public static Iterable<Object[]> data() {
            return new Iterable<Object[]>() {
                @Override
                public Iterator<Object[]> iterator() {
                    return new Iterator<Object[]>() {
                        private final DataUnit[] units = DataUnit.values();
                        private final int n = units.length;
                        private int i = 0;
                        private int j = 0;
                        private int k = 0;

                        @Override
                        public boolean hasNext() {
                            return i < n && j < n && k < values.length;
                        }

                        @Override
                        public Object[] next() {
                            final Object[] result = new Object[]{units[i], units[j], values[k]};
                            ++i;
                            if (i == n) {
                                i = 0;
                                ++j;
                                if (j == n) {
                                    j = 0;
                                    ++k;
                                }
                            }
                            return result;
                        }

                        @Override
                        public void remove() {
                            throw new UnsupportedOperationException("Remove is not supported on this iterator.");
                        }
                    };
                }
            };
        }

        @Test
        public void test() {


            unitA.from(value, unitB);
            unitB.from(value, unitA);

        }
    }

    @RunWith(Parameterized.class)
    public static class AllDataUnits extends DataUnitTest {
        private final DataUnit unit;

        public AllDataUnits(DataUnit unit) {
            this.unit = unit;
        }

        @Parameterized.Parameters(name = "{index}: {0}")
        public static Iterable<Object[]> data() {
            return new Iterable<Object[]>() {
                @Override
                public Iterator<Object[]> iterator() {
                    return new Iterator<Object[]>() {
                        private final DataUnit[] units = DataUnit.values();
                        private int i = 0;

                        @Override
                        public boolean hasNext() {
                            return i < units.length;
                        }

                        @Override
                        public Object[] next() {
                            final Object[] result = new Object[]{units[i]};
                            ++i;
                            return result;
                        }

                        @Override
                        public void remove() {
                            throw new UnsupportedOperationException("Remove is not supported on this iterator.");
                        }
                    };
                }
            };
        }

        @Test
        public void testZeroToBits() {
            final long value = 0L;

            final BigInteger result1 = unit.toBits(value);
            LOG.debug(format("{0} {1} ~ {2} b", value, unit.getAbbreviation(), result1));
            assertEquals(BigInteger.ZERO, result1);

            // Check the other method is equivalent
            final BigInteger result2 = DataUnit.BIT.from(value, unit);
            assertEquals(result1, result2);
        }

        @Test
        public void testZeroToBytes() {
            final long value = 0L;

            final BigInteger result1 = unit.toBytes(value);
            LOG.debug(format("{0} {1} ~ {2} B", value, unit.getAbbreviation(), result1));
            assertEquals(BigInteger.ZERO, result1);

            // Check the other method is equivalent
            final BigInteger result2 = DataUnit.BYTE.from(value, unit);
            assertEquals(result1, result2);
        }

        @Test
        public void testOneToBits() {
            final long value = 1L;

            final BigInteger result1 = unit.toBits(value);
            LOG.debug(format("{0} {1} ~ {2} b", value, unit.getAbbreviation(), result1));
            assertTrue(result1.compareTo(BigInteger.ONE) >= 0);

            // Check the other method is equivalent
            final BigInteger result2 = DataUnit.BIT.from(value, unit);
            assertEquals(result1, result2);

        }

        @Test
        public void testOneToBytes() {
            final long value = 1L;

            final BigInteger result1 = unit.toBytes(value);
            LOG.debug(format("{0} {1} ~ {2} B", value, unit.getAbbreviation(), result1));

            // Check the other method is equivalent
            final BigInteger result2 = DataUnit.BYTE.from(value, unit);
            assertEquals(result1, result2);
        }

        @Test
        public void testLongMaxToBits() {
            final long value = Long.MAX_VALUE;

            final BigInteger result1 = unit.toBits(value);
            LOG.debug(format("{0} {1} ~ {2} b", value, unit.getAbbreviation(), result1));
            assertTrue(result1.compareTo(BigInteger.ONE) >= 0);

            // Check the other method is equivalent
            final BigInteger result2 = DataUnit.BIT.from(value, unit);
            assertEquals(result1, result2);
        }

        @Test
        public void testLongMaxToBytes() {
            final long value = Long.MAX_VALUE;

            final BigInteger result1 = unit.toBytes(value);
            LOG.debug(format("{0} {1} ~ {2} B", value, unit.getAbbreviation(), result1));

            // Check the other method is equivalent
            final BigInteger result2 = DataUnit.BYTE.from(value, unit);
            assertEquals(result1, result2);
        }
    }


}
