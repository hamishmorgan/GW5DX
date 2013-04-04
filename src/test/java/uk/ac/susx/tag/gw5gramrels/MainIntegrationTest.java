package uk.ac.susx.tag.gw5gramrels;

import com.google.common.collect.ImmutableList;
import com.google.common.io.Resources;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * Created with IntelliJ IDEA.
 * User: hiam20
 * Date: 03/04/2013
 * Time: 13:14
 * To change this template use File | Settings | File Templates.
 */
public class MainIntegrationTest {

    /**
     *
     */
    private static final File OUTPUT_DIR = new File("target" + File.separator + "testout");
    /**
     *
     */
    private static final String[] INPUT_FILE_NAMES = {
            "nyt_eng_201012_sample.xml.gz",
            "afp_eng_200412_sample.xml.gz",
            "cna_eng_200803_sample.xml.gz",
            "apw_eng_200806_sample.xml.gz",
            "ltw_eng_200902_sample.xml.gz",
            "xin_eng_200804_sample.xml.gz",
            "wpb_eng_201001_sample.xml.gz"};

    @BeforeClass
    public static void createOutputDir() throws IOException {
        if (!OUTPUT_DIR.exists()) {
            if (!OUTPUT_DIR.mkdirs())
                throw new IOException("Output directory does not exist and could not be created: " + OUTPUT_DIR);
        } else if (!OUTPUT_DIR.isDirectory())
            throw new IOException("Output directory already exists, but is not a directory:: " + OUTPUT_DIR);
    }

    private static File asFile(URL url) {
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

    @Test
    public void singleFile() throws Throwable {
        final URL url = Resources.getResource(this.getClass(), "nyt_eng_201012_sample.xml.gz");

        Assert.assertTrue(url.getProtocol().equalsIgnoreCase("file"));
        System.out.println("URL = " + url);
        System.out.println("File = " + asFile(url));

        File inputFile = asFile(url);

        File outputFile = new File(OUTPUT_DIR, inputFile.getName() + ".out");
        final String[] args = {
                "--output", outputFile.toString(),
                inputFile.toString()
        };
        Main.main(args);
    }

    @Test
    public void multipleFiles() throws Throwable {

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

    @Test
    public void testPrintUsage() throws Throwable {
        final String[] args = {"--help"};
        Main.main(args);
    }
}
