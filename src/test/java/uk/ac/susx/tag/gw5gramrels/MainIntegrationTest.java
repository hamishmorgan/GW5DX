package uk.ac.susx.tag.gw5gramrels;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.io.Resources;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.junit.*;
import org.junit.rules.TestName;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.MessageFormat;

/**
 * Integrations tests for gram-relation extraction, that call the main method.
 *
 * @author Hamish Morgan &lt;hamish.morgan@sussex.ac.uk&gt;
 */
public class MainIntegrationTest {

    /**
     * Logger
     */
    private static final Logger LOG = Logger.getLogger(MainIntegrationTest.class);
    /**
     * All data will be written to the given directory.
     */
    private static final File OUTPUT_DIR = new File("target" + File.separator + "testout");
    /**
     * List of input sample files
     */
    private static final String[] INPUT_FILE_NAMES = {
            "nyt_eng_201012_sample.xml.gz",
            "afp_eng_200412_sample.xml.gz",
            "cna_eng_200803_sample.xml.gz",
            "apw_eng_200806_sample.xml.gz",
            "ltw_eng_200902_sample.xml.gz",
            "xin_eng_200804_sample.xml.gz",
            "wpb_eng_201001_sample.xml.gz",
            "ltw_eng_19971118.0013.xml"};
    @Rule
    public final TestName testName = new TestName();

    @BeforeClass
    public static void createAndEmptyOutputDir() throws IOException {

        if (OUTPUT_DIR.exists())
            deleteRecursive(OUTPUT_DIR);

        if (!OUTPUT_DIR.exists()) {
            if (!OUTPUT_DIR.mkdirs())
                throw new IOException("Output directory does not exist and could not be created: " + OUTPUT_DIR);
        } else if (!OUTPUT_DIR.isDirectory())
            throw new IOException("Output directory already exists, but is not a directory:: " + OUTPUT_DIR);


    }

    @NotNull
    private static File asFile(@NotNull final URL url) {
        if (!url.getProtocol().equalsIgnoreCase("file"))
            throw new IllegalArgumentException("URL protocol must be `file`, but found `" + url.getProtocol() + "`.");
        File file = (url.getPath().startsWith("/"))
                ? new File(File.separator)
                : new File(".");
        for (String part : url.getPath().split("/")) {
            if (!part.isEmpty())
                file = new File(file, part);
        }
        return file;
    }

    private static void deleteRecursive(File file) throws IOException {
        Preconditions.checkNotNull(file, "file");
        if (file.isDirectory())
            for (File child : file.listFiles())
                deleteRecursive(child);
        if (file.exists() && !file.delete())
            throw new IOException("Failed to delete file " + file);
    }

    @Before
    public final void printTestName() {
        LOG.info(MessageFormat.format("Running test: {0}#{1}", this.getClass().getName(), testName.getMethodName()));
    }

    @Test
    public void testSingleFile() throws Throwable {
        final URL url = Resources.getResource(this.getClass(), "nyt_eng_201012_sample.xml.gz");
        Assert.assertTrue(url.getProtocol().equalsIgnoreCase("file"));
        final File inputFile = asFile(url);

        File outputFile = new File(OUTPUT_DIR, inputFile.getName() + ".out");
        final String[] args = {
                "--output", outputFile.toString(),
                inputFile.toString()
        };
        Main.main(args);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNonExistantInputFile() throws Throwable {
        final File inputFile = new File("I dont exist");

        File outputFile = new File(OUTPUT_DIR, inputFile.getName() + ".out");
        final String[] args = {
                "--output", outputFile.toString(),
                inputFile.toString()
        };
        Main.main(args);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInputFileIsDirectory() throws Throwable {
        final File inputFile = new File(".");

        File outputFile = new File(OUTPUT_DIR, inputFile.getName() + ".out");
        final String[] args = {
                "--output", outputFile.toString(),
                inputFile.toString()
        };
        Main.main(args);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testOutputNotCreatable() throws Throwable {
        final URL url = Resources.getResource(this.getClass(), "nyt_eng_201012_sample.xml.gz");
        Assert.assertTrue(url.getProtocol().equalsIgnoreCase("file"));
        final File inputFile = asFile(url);

        try {
            OUTPUT_DIR.setWritable(false);
            OUTPUT_DIR.setExecutable(false);
            File outputFile = new File(OUTPUT_DIR, inputFile.getName() + ".out");
            final String[] args = {
                    "--output", outputFile.toString(),
                    inputFile.toString()
            };
            Main.main(args);
        } finally {
            OUTPUT_DIR.setWritable(true);
            OUTPUT_DIR.setExecutable(true);
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testOutputNotWritable() throws Throwable {
        final URL url = Resources.getResource(this.getClass(), "nyt_eng_201012_sample.xml.gz");
        Assert.assertTrue(url.getProtocol().equalsIgnoreCase("file"));
        final File inputFile = asFile(url);
        final File outputFile = new File(OUTPUT_DIR, inputFile.getName() + ".out");
        outputFile.createNewFile();

        try {
            outputFile.setWritable(false);

            final String[] args = {
                    "--output", outputFile.toString(),
                    inputFile.toString()
            };
            Main.main(args);
        } finally {
            outputFile.setWritable(true);
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInputNotReadable() throws Throwable {
        final URL url = Resources.getResource(this.getClass(), "nyt_eng_201012_sample.xml.gz");
        Assert.assertTrue(url.getProtocol().equalsIgnoreCase("file"));
        final File inputFile = asFile(url);
        final File outputFile = new File(OUTPUT_DIR, inputFile.getName() + ".out");

        try {
            inputFile.setReadable(false);

            final String[] args = {
                    "--output", outputFile.toString(),
                    inputFile.toString()
            };
            Main.main(args);
        } finally {
            inputFile.setReadable(true);
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testOutputFileIsDirectory() throws Throwable {
        final URL url = Resources.getResource(this.getClass(), "nyt_eng_201012_sample.xml.gz");
        Assert.assertTrue(url.getProtocol().equalsIgnoreCase("file"));
        final File inputFile = asFile(url);

        File outputFile = OUTPUT_DIR;
        final String[] args = {
                "--output", outputFile.toString(),
                inputFile.toString()
        };
        Main.main(args);
    }

    @Test
    public void testMultipleFiles() throws Throwable {

        final String[] inputPaths = new String[INPUT_FILE_NAMES.length];
        for (int i = 0; i < INPUT_FILE_NAMES.length; i++) {
            final String fileName = INPUT_FILE_NAMES[i];
            final URL url = Resources.getResource(this.getClass(), fileName);
            Assert.assertTrue(url.getProtocol().equalsIgnoreCase("file"));
            System.out.println("URL = " + url);
            System.out.println("File = " + asFile(url));
            inputPaths[i] = asFile(url).toString();
        }

        File outputFile = new File(OUTPUT_DIR, "xxx.out");

        ImmutableList.Builder<String> args = ImmutableList.builder();

        args.add("--output", outputFile.toString());
        args.add(inputPaths);

        Main.main(args.build().toArray(new String[0]));
    }

    /**
     * GigaWord 5 annotated corpus occasionally omits the NER tag newInstance tokens (e.g in "ltw_eng_19971118.0013".) This
     * causes the reader to throw an unhelpful IllegalStateException. There is currently no way to recover a
     * AgigaSentenceReader to skip the document, so the simplest solution for this use case is to disable NER tags in
     * the prefs.
     *
     * @throws Throwable
     */
    @Test
    public void testMissingNERTag() throws Throwable {
        final URL url = Resources.getResource(this.getClass(), "ltw_eng_19971118.0013.xml");
        Assert.assertTrue(url.getProtocol().equalsIgnoreCase("file"));
        final File inputFile = asFile(url);

        final File outputFile = new File(OUTPUT_DIR, inputFile.getName() + ".out");
        final String[] args = {
                "--output", outputFile.toString(),
                inputFile.toString()
        };
        Main.main(args);
    }

    /**
     * Print the help usage and exit.
     * <p/>
     * Not exception should be thrown and system to exit should not be called.
     *
     * @throws Throwable
     */
    @Test
    public void testPrintUsage() throws Throwable {
        Main.main(new String[]{"--help"});
    }
}
