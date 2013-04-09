package uk.ac.susx.tag.lib;

import javax.annotation.Nonnull;
import java.math.BigInteger;
import java.util.EnumSet;

/**
 * Created with IntelliJ IDEA.
 * User: hiam20
 * Date: 04/04/2013
 * Time: 17:30
 * To change this template use File | Settings | File Templates.
 * <p/>
 * <p/>
 * TODO: valueOf from names and abbreviation
 * TODO: floating point approximations of results
 */
public enum DataUnit {

    BIT("bit", "b") {
        @Override
        public BigInteger from(BigInteger sourceSize, DataUnit sourceUnit) {
            return sourceUnit.toBits(sourceSize);
        }

        @Override
        public BigInteger toBits(BigInteger size) {
            return size;
        }
    },

    NIBBLE("nibble", "n") {
        @Override
        public BigInteger from(BigInteger sourceSize, DataUnit sourceUnit) {
            return sourceUnit.toBits(sourceSize).divide(BigInteger.valueOf(BITS_PER_NIBBLE));
        }

        @Override
        public BigInteger toBits(BigInteger size) {
            return size.multiply(BigInteger.valueOf(BITS_PER_NIBBLE));
        }
    },

    BYTE("Byte", "B") {
        @Override
        public BigInteger from(BigInteger sourceSize, DataUnit sourceUnit) {
            return sourceUnit.toBits(sourceSize).divide(BigInteger.valueOf(BITS_PER_BYTE));
        }

        @Override
        public BigInteger toBits(BigInteger size) {
            return size.multiply(BigInteger.valueOf(BITS_PER_BYTE));
        }

        @Override
        public BigInteger toBytes(BigInteger size) {
            return size;
        }
    },

    WORD16("Word16", "W16") {
        @Override
        public BigInteger from(BigInteger sourceSize, DataUnit sourceUnit) {
            return sourceUnit.toBytes(sourceSize).divide(BigInteger.valueOf(BYTES_PER_WORD16));
        }

        @Override
        public BigInteger toBits(BigInteger size) {
            return size.multiply(BigInteger.valueOf(BITS_PER_BYTE * BYTES_PER_WORD16));
        }
    },

    WORD32("Word32", "W32") {
        @Override
        public BigInteger from(BigInteger sourceSize, DataUnit sourceUnit) {
            return sourceUnit.toBytes(sourceSize).divide(BigInteger.valueOf(BYTES_PER_WORD32));
        }

        @Override
        public BigInteger toBits(BigInteger size) {
            return size.multiply(BigInteger.valueOf(BITS_PER_BYTE * BYTES_PER_WORD32));
        }
    },

    WORD64("Word64", "W64") {
        @Override
        public BigInteger from(BigInteger sourceSize, DataUnit sourceUnit) {
            return sourceUnit.toBytes(sourceSize).divide(BigInteger.valueOf(BYTES_PER_WORD64));
        }

        @Override
        public BigInteger toBits(BigInteger size) {
            return size.multiply(BigInteger.valueOf(BITS_PER_BYTE * BYTES_PER_WORD64));
        }


    },

    WORD128("Word128", "W128") {
        @Override
        public BigInteger from(BigInteger sourceSize, DataUnit sourceUnit) {
            return sourceUnit.toBytes(sourceSize).divide(BigInteger.valueOf(BYTES_PER_WORD128));
        }

        @Override
        public BigInteger toBits(BigInteger size) {
            return size.multiply(BigInteger.valueOf(BITS_PER_BYTE * BYTES_PER_WORD128));
        }

    },

    KILOBYTE("kilobyte", "kB") {
        @Override
        public BigInteger from(BigInteger sourceSize, DataUnit sourceUnit) {
            return sourceUnit.toBytes(sourceSize).divide(KILO);
        }

        @Override
        public BigInteger toBits(BigInteger size) {
            return size.multiply(KILO).multiply(BigInteger.valueOf(BITS_PER_BYTE));
        }
    },

    MEGABYTE("megabyte", "MB") {
        @Override
        public BigInteger from(BigInteger sourceSize, DataUnit sourceUnit) {
            return sourceUnit.toBytes(sourceSize).divide(MEGA);
        }

        @Override
        public BigInteger toBits(BigInteger size) {
            return size.multiply(MEGA).multiply(BigInteger.valueOf(BITS_PER_BYTE));
        }

    },

    GIGABYTE("gigabyte", "GB") {
        @Override
        public BigInteger from(BigInteger sourceSize, DataUnit sourceUnit) {
            return sourceUnit.toBytes(sourceSize).divide(GIGA);
        }

        @Override
        public BigInteger toBits(BigInteger size) {
            return size.multiply(GIGA).multiply(BigInteger.valueOf(BITS_PER_BYTE));
        }

    },

    TERABYTE("terabyte", "TB") {
        @Override
        public BigInteger from(BigInteger sourceSize, DataUnit sourceUnit) {
            return sourceUnit.toBytes(sourceSize).divide(TERA);
        }

        @Override
        public BigInteger toBits(BigInteger size) {
            return size.multiply(TERA).multiply(BigInteger.valueOf(BITS_PER_BYTE));
        }

    },

    PETABYTE("petabyte", "PB") {
        @Override
        public BigInteger from(BigInteger sourceSize, DataUnit sourceUnit) {
            return sourceUnit.toBytes(sourceSize).divide(PETA);
        }

        @Override
        public BigInteger toBits(BigInteger size) {
            return size.multiply(PETA).multiply(BigInteger.valueOf(BITS_PER_BYTE));
        }

    },

    EXABYTE("exabyte", "EB") {
        @Override
        public BigInteger from(BigInteger sourceSize, DataUnit sourceUnit) {
            return sourceUnit.toBytes(sourceSize).divide(EXA);
        }

        @Override
        public BigInteger toBits(BigInteger size) {
            return size.multiply(EXA).multiply(BigInteger.valueOf(BITS_PER_BYTE));
        }

    },

    ZETTABYTE("zettabyte", "ZB") {
        @Override
        public BigInteger from(BigInteger sourceSize, DataUnit sourceUnit) {
            return sourceUnit.toBytes(sourceSize).divide(ZETTA);
        }

        @Override
        public BigInteger toBits(BigInteger size) {
            return size.multiply(ZETTA).multiply(BigInteger.valueOf(BITS_PER_BYTE));
        }

    },

    YOTTABYTE("yottabyte", "YB") {
        @Override
        public BigInteger from(BigInteger sourceSize, DataUnit sourceUnit) {
            return sourceUnit.toBytes(sourceSize).divide(YOTTA);
        }

        @Override
        public BigInteger toBits(BigInteger size) {
            return size.multiply(YOTTA).multiply(BigInteger.valueOf(BITS_PER_BYTE));
        }

    },

    KIBIBYTE("kibibyte", "KiB") {
        @Override
        public BigInteger from(BigInteger sourceSize, DataUnit sourceUnit) {
            return sourceUnit.toBytes(sourceSize).divide(KIBI);
        }

        @Override
        public BigInteger toBits(BigInteger size) {
            return size.multiply(KIBI).multiply(BigInteger.valueOf(BITS_PER_BYTE));
        }

    },

    MEBIBYTE("mebibyte", "MiB") {
        @Override
        public BigInteger from(BigInteger sourceSize, DataUnit sourceUnit) {
            return sourceUnit.toBytes(sourceSize).divide(MEBI);
        }

        @Override
        public BigInteger toBits(BigInteger size) {
            return size.multiply(MEBI).multiply(BigInteger.valueOf(BITS_PER_BYTE));
        }


    },

    GIBIBYTE("gibibyte", "GiB") {
        @Override
        public BigInteger from(BigInteger sourceSize, DataUnit sourceUnit) {
            return sourceUnit.toBytes(sourceSize).divide(GIBI);
        }

        @Override
        public BigInteger toBits(BigInteger size) {
            return size.multiply(GIBI).multiply(BigInteger.valueOf(BITS_PER_BYTE));
        }

    },

    TEBIBYTE("tebibyte", "TiB") {
        @Override
        public BigInteger from(BigInteger sourceSize, DataUnit sourceUnit) {
            return sourceUnit.toBytes(sourceSize).divide(TEBI);
        }

        @Override
        public BigInteger toBits(BigInteger size) {
            return size.multiply(TEBI).multiply(BigInteger.valueOf(BITS_PER_BYTE));
        }


    },

    PEBIBYTE("pebibyte", "PiB") {
        @Override
        public BigInteger from(BigInteger sourceSize, DataUnit sourceUnit) {
            return sourceUnit.toBytes(sourceSize).divide(PEBI);
        }

        @Override
        public BigInteger toBits(BigInteger size) {
            return size.multiply(PEBI).multiply(BigInteger.valueOf(BITS_PER_BYTE));
        }

    },

    EXBIBYTE("exbibyte", "EiB") {
        @Override
        public BigInteger from(BigInteger sourceSize, DataUnit sourceUnit) {
            return sourceUnit.toBytes(sourceSize).divide(EXBI);
        }

        @Override
        public BigInteger toBits(BigInteger size) {
            return size.multiply(EXBI).multiply(BigInteger.valueOf(BITS_PER_BYTE));
        }

    },

    ZEBIBYTE("zebibyte", "ZiB") {
        @Override
        public BigInteger from(BigInteger sourceSize, DataUnit sourceUnit) {
            return sourceUnit.toBytes(sourceSize).divide(ZEBI);
        }

        @Override
        public BigInteger toBits(BigInteger size) {
            return size.multiply(ZEBI).multiply(BigInteger.valueOf(BITS_PER_BYTE));
        }


    },

    YOBIBYTE("yobibyte", "YiB") {
        @Override
        public BigInteger from(BigInteger sourceSize, DataUnit sourceUnit) {
            return sourceUnit.toBytes(sourceSize).divide(YOBI);
        }

        @Override
        public BigInteger toBits(BigInteger size) {
            return size.multiply(YOBI).multiply(BigInteger.valueOf(BITS_PER_BYTE));
        }
    },

    KILOBIT("kilobit", "kb") {
        @Override
        public BigInteger from(BigInteger sourceSize, DataUnit sourceUnit) {
            return sourceUnit.toBits(sourceSize).divide(KILO);
        }

        @Override
        public BigInteger toBits(BigInteger size) {
            return size.multiply(KILO);
        }
    },

    MEGABIT("megabit", "Mb") {
        @Override
        public BigInteger from(BigInteger sourceSize, DataUnit sourceUnit) {
            return sourceUnit.toBits(sourceSize).divide(MEGA);
        }

        @Override
        public BigInteger toBits(BigInteger size) {
            return size.multiply(MEGA);
        }
    },

    GIGABIT("gigabit", "Gb") {
        @Override
        public BigInteger from(BigInteger sourceSize, DataUnit sourceUnit) {
            return sourceUnit.toBits(sourceSize).divide(GIGA);
        }

        @Override
        public BigInteger toBits(BigInteger size) {
            return size.multiply(GIGA);
        }
    },

    TERABIT("terabit", "Tb") {
        @Override
        public BigInteger from(BigInteger sourceSize, DataUnit sourceUnit) {
            return sourceUnit.toBits(sourceSize).divide(TERA);
        }

        @Override
        public BigInteger toBits(BigInteger size) {
            return size.multiply(TERA);
        }
    },

    PETABIT("petabit", "Pb") {
        @Override
        public BigInteger from(BigInteger sourceSize, DataUnit sourceUnit) {
            return sourceUnit.toBits(sourceSize).divide(PETA);
        }

        @Override
        public BigInteger toBits(BigInteger size) {
            return size.multiply(PETA);
        }
    },

    EXABIT("exabit", "Eb") {
        @Override
        public BigInteger from(BigInteger sourceSize, DataUnit sourceUnit) {
            return sourceUnit.toBits(sourceSize).divide(EXA);
        }

        @Override
        public BigInteger toBits(BigInteger size) {
            return size.multiply(EXA);
        }
    },

    ZETTABIT("zetabit", "Zb") {
        @Override
        public BigInteger from(BigInteger sourceSize, DataUnit sourceUnit) {
            return sourceUnit.toBits(sourceSize).divide(ZETTA);
        }

        @Override
        public BigInteger toBits(BigInteger size) {
            return size.multiply(ZETTA);
        }
    },

    YOTTABIT("yottabit", "Yb") {
        @Override
        public BigInteger from(BigInteger sourceSize, DataUnit sourceUnit) {
            return sourceUnit.toBits(sourceSize).divide(YOTTA);
        }

        @Override
        public BigInteger toBits(BigInteger size) {
            return size.multiply(YOTTA);
        }
    },

    KIBIBIT("kibibit", "Kib") {
        @Override
        public BigInteger from(BigInteger sourceSize, DataUnit sourceUnit) {
            return sourceUnit.toBits(sourceSize).divide(KIBI);
        }

        @Override
        public BigInteger toBits(BigInteger size) {
            return size.multiply(KIBI);
        }
    },

    MEBIBIT("mebibit", "Mib") {
        @Override
        public BigInteger from(BigInteger sourceSize, DataUnit sourceUnit) {
            return sourceUnit.toBits(sourceSize).divide(MEBI);
        }

        @Override
        public BigInteger toBits(BigInteger size) {
            return size.multiply(MEBI);
        }
    },

    GIBIBIT("gibibit", "Gib") {
        @Override
        public BigInteger from(BigInteger sourceSize, DataUnit sourceUnit) {
            return sourceUnit.toBits(sourceSize).divide(GIBI);
        }

        @Override
        public BigInteger toBits(BigInteger size) {
            return size.multiply(GIBI);
        }
    },

    TEBIBIT("tebibit", "Tib") {
        @Override
        public BigInteger from(BigInteger sourceSize, DataUnit sourceUnit) {
            return sourceUnit.toBits(sourceSize).divide(TEBI);
        }

        @Override
        public BigInteger toBits(BigInteger size) {
            return size.multiply(TEBI);
        }
    },

    PEBIBIT("pebibit", "Pib") {
        @Override
        public BigInteger from(BigInteger sourceSize, DataUnit sourceUnit) {
            return sourceUnit.toBits(sourceSize).divide(PEBI);
        }

        @Override
        public BigInteger toBits(BigInteger size) {
            return size.multiply(PEBI);
        }
    },

    EXBIBIT("exbibit", "Eib") {
        @Override
        public BigInteger from(BigInteger sourceSize, DataUnit sourceUnit) {
            return sourceUnit.toBits(sourceSize).divide(EXBI);
        }

        @Override
        public BigInteger toBits(BigInteger size) {
            return size.multiply(EXBI);
        }
    },

    ZEBIBIT("zebibit", "Zib") {
        @Override
        public BigInteger from(BigInteger sourceSize, DataUnit sourceUnit) {
            return sourceUnit.toBits(sourceSize).divide(ZEBI);
        }

        @Override
        public BigInteger toBits(BigInteger size) {
            return size.multiply(ZEBI);
        }
    },

    YOBIBIT("yobibit", "Yib") {
        @Override
        public BigInteger from(BigInteger sourceSize, DataUnit sourceUnit) {
            return sourceUnit.toBits(sourceSize).divide(YOBI);
        }

        @Override
        public BigInteger toBits(BigInteger size) {
            return size.multiply(YOBI);
        }
    };
    // bases
    private static final int SI_BASE = 10;
    private static final int BI_BASE = 2;
    //
    private static final int BITS_PER_BYTE = 8;
    private static final int BITS_PER_NIBBLE = 4;
    private static final int BYTES_PER_WORD16 = 2;
    private static final int BYTES_PER_WORD32 = 4;
    private static final int BYTES_PER_WORD64 = 8;
    private static final int BYTES_PER_WORD128 = 16;
    // si exponents
    private static final int KILO_EXP = 3;
    private static final int MEGA_EXP = 6;
    private static final int GIGA_EXP = 9;
    private static final int TERA_EXP = 12;
    private static final int PETA_EXP = 15;
    private static final int EXA_EXP = 18;
    private static final int ZETTA_EXP = 21;
    private static final int YOTTA_EXP = 24;
    // binary exponents
    private static final int KIBI_EXP = 10;
    private static final int MEBI_EXP = 20;
    private static final int GIBI_EXP = 30;
    private static final int TEBI_EXP = 40;
    private static final int PEBI_EXP = 50;
    private static final int EXBI_EXP = 60;
    private static final int ZEBI_EXP = 70;
    private static final int YOBI_EXP = 80;
    // si byte units
    private static final BigInteger KILO = BigInteger.valueOf(SI_BASE).pow(KILO_EXP);
    private static final BigInteger MEGA = BigInteger.valueOf(SI_BASE).pow(MEGA_EXP);
    private static final BigInteger GIGA = BigInteger.valueOf(SI_BASE).pow(GIGA_EXP);
    private static final BigInteger TERA = BigInteger.valueOf(SI_BASE).pow(TERA_EXP);
    private static final BigInteger PETA = BigInteger.valueOf(SI_BASE).pow(PETA_EXP);
    private static final BigInteger EXA = BigInteger.valueOf(SI_BASE).pow(EXA_EXP);
    private static final BigInteger ZETTA = BigInteger.valueOf(SI_BASE).pow(ZETTA_EXP);
    private static final BigInteger YOTTA = BigInteger.valueOf(SI_BASE).pow(YOTTA_EXP);
    // binary byte units
    private static final BigInteger KIBI = BigInteger.valueOf(BI_BASE).pow(KIBI_EXP);
    private static final BigInteger MEBI = BigInteger.valueOf(BI_BASE).pow(MEBI_EXP);
    private static final BigInteger GIBI = BigInteger.valueOf(BI_BASE).pow(GIBI_EXP);
    private static final BigInteger TEBI = BigInteger.valueOf(BI_BASE).pow(TEBI_EXP);
    private static final BigInteger PEBI = BigInteger.valueOf(BI_BASE).pow(PEBI_EXP);
    private static final BigInteger EXBI = BigInteger.valueOf(BI_BASE).pow(EXBI_EXP);
    private static final BigInteger ZEBI = BigInteger.valueOf(BI_BASE).pow(ZEBI_EXP);
    private static final BigInteger YOBI = BigInteger.valueOf(BI_BASE).pow(YOBI_EXP);
    //
    private static final EnumSet<DataUnit> BYTE_UNITS_SI = EnumSet.of(BYTE, KILOBYTE, MEGABYTE, GIGABYTE, TERABYTE,
            PETABYTE, EXABYTE, ZETTABYTE, YOTTABYTE);
    private static final EnumSet<DataUnit> BYTE_UNITS_BIN = EnumSet.of(BYTE, KIBIBYTE, MEBIBYTE, GIBIBYTE, TEBIBYTE,
            PEBIBYTE, EXBIBYTE, ZEBIBYTE, YOBIBYTE);
    @Nonnull
    private final String name;
    @Nonnull
    private final String abbreviation;

    private DataUnit(@Nonnull String name, @Nonnull String abbreviation) {
        this.name = name;
        this.abbreviation = abbreviation;
    }

    @Nonnull
    public String getName() {
        return name;
    }

    @Nonnull
    public String getAbbreviation() {
        return abbreviation;
    }

    public abstract BigInteger from(BigInteger sourceSize, DataUnit sourceUnit);

    public abstract BigInteger toBits(BigInteger size);

    public BigInteger toBytes(BigInteger size) {
        return toBits(size).divide(BigInteger.valueOf(BITS_PER_BYTE));
    }

    public final BigInteger from(long sourceSize, DataUnit sourceUnit) {
        return from(BigInteger.valueOf(sourceSize), sourceUnit);
    }

    public final BigInteger toBits(long size) {
        return toBits(BigInteger.valueOf(size));
    }

    public final BigInteger toBytes(long size) {
        return toBytes(BigInteger.valueOf(size));
    }

}
