package uk.ac.susx.tag.gw5gramrels;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.google.common.base.Stopwatch;
import com.google.common.io.Closer;
import edu.jhu.agiga.*;
import org.apache.log4j.Logger;
import uk.ac.susx.tag.lib.MiscUtil;

import java.io.*;
import java.util.List;

import static java.text.MessageFormat.format;

/**
 * @author Hamish Morgan &lt;hamish.morgan@ussex.ac.uk&gt;
 * @since 25/03/2013 16:02
 */
public class Main {
    private static final Logger LOG = Logger.getLogger(Main.class);
    /**
     * List of input files to parse.
     */
    @Parameter(required = true, variableArity = true, description = "FILE [FILE [ FILE [...]]]")
    private List<File> inputFiles = null;
    /**
     * Output file to write gramrels to.
     */
    @Parameter(names = {"-o", "--output"}, required = true,
            description = "Output file to write gramrels to.")
    private File outputFile = null;
    /**
     *
     */
    @Parameter(names = {"-d", "--depForm"},
            description = "Dependency form; out of {BASIC_DEPS,COL_DEPS,COL_CCPROC_DEPS}")
    private AgigaConstants.DependencyForm depForm = AgigaConstants.DependencyForm.COL_CCPROC_DEPS;
    /**
     *
     */
    @Parameter(names = {"-h", "--help"}, help = true,
            description = "Display this message")
    private boolean usageReqested = false;

    public Main() {
    }

    public static void main(String[] args) throws IOException {

        final Main instance = new Main();

        final JCommander jc = new JCommander();
        jc.addObject(instance);
        jc.setProgramName("uk/ac/susx/tag/gw5gramrels");
        jc.parse(args);

        if (instance.usageReqested) {
            jc.usage();
        } else {
            instance.run();
        }
    }

    private static File checkOutputFile(final File outputFile)
            throws NullPointerException, IllegalArgumentException, IOException {
        if (!outputFile.exists()) {
            if (!outputFile.createNewFile())
                throw new IllegalArgumentException("Output file could not be created:" + outputFile);
        } else {
            if (outputFile.isDirectory())
                throw new IllegalArgumentException("Output file already exists, but is a directory:" + outputFile);
            else if (!outputFile.isFile())
                throw new IllegalArgumentException("Output file already exists, but is not a regular file:" + outputFile);
            else if (!outputFile.canWrite())
                throw new IllegalArgumentException("Output file already exists, but is not writeable:" + outputFile);
            else
                LOG.warn("Over-writing output file: " + outputFile);
        }
        return outputFile;
    }

    private static File checkInputFile(final File inputFile)
            throws NullPointerException, IllegalArgumentException {
        if (!inputFile.exists())
            throw new IllegalArgumentException("Input file does not exist: " + inputFile);
        else if (inputFile.isDirectory())
            throw new IllegalArgumentException("Input file is a directory: " + inputFile);
        else if (!inputFile.isFile())
            throw new IllegalArgumentException("Input file is not a regular file: " + inputFile);
        else if (!inputFile.canRead())
            throw new IllegalArgumentException("Input file is not readable: " + inputFile);

        return inputFile;
    }

    public void run() throws IOException {

        checkOutputFile(outputFile);
        final Closer outputCloser = Closer.create();

        try {
            final Stopwatch sw = new Stopwatch();
            sw.start();
            if (LOG.isInfoEnabled())
                LOG.info(format("Beginning gram-relation extraction from {0} file{1}.",
                        inputFiles.size(), inputFiles.size() == 1 ? "" : "s"));

            final AgigaPrefs agigaPreferences = new AgigaPrefs();
            final Writer writer = outputCloser.register(new BufferedWriter(
                    outputCloser.register(new FileWriter(outputFile))));

            for (final File inputFile : inputFiles) {
                if (LOG.isInfoEnabled())
                    LOG.info(format("Processing file `{0}`.", inputFile));

                checkInputFile(inputFile);

                final Closer inputCloser = Closer.create();
                try {
                    final StreamingDocumentReader instance = inputCloser.register(
                            new StreamingDocumentReader(inputFile.toString(), agigaPreferences));

                    extractGramRels(instance, writer);
                } catch (Throwable t) {
                    LOG.error("Rethrowing caught exception.", t);
                    throw inputCloser.rethrow(t);
                } finally {
                    inputCloser.close();
                }
            }

            sw.stop();
            if (LOG.isInfoEnabled())
                LOG.info(format("Completed gram-relation extraction from {0} file{1}. (Elapsed time: {2})",
                        inputFiles.size(), inputFiles.size() == 1 ? "" : "s", sw.toString()));

        } catch (Throwable t) {
            LOG.error("Rethrowing caught exception.", t);
            throw outputCloser.rethrow(t);
        } finally {
            outputCloser.close();
        }
    }

    public void extractGramRels(final StreamingDocumentReader reader, final Writer writer) throws IOException {
        final Stopwatch sw = new Stopwatch();
        sw.start();

        int documentCount = 0;
        for (final AgigaDocument doc : reader) {
            ++documentCount;

            if (LOG.isDebugEnabled()) {
                LOG.debug(format("Reading document {2}; id={0}, type={1}",
                        doc.getDocId(), doc.getType(), documentCount));
            }

            for (AgigaSentence sent : doc.getSents()) {
                final List<AgigaToken> tokens = sent.getTokens();

                for (AgigaTypedDependency rel : sent.getAgigaDeps(depForm)) {
                    final int depIdx = rel.getDepIdx();
                    final int govIdx = rel.getGovIdx();
                    final String type = rel.getType();
                    final String dep = tokens.get(depIdx).getWord();
                    final String gov = govIdx == -1 ? "<NIL>" : tokens.get(govIdx).getWord();

                    writer.write(String.format("%s\t%s:%s%n", gov, type, dep));
                }
            }
        }
        sw.stop();

        if (LOG.isInfoEnabled()) {
            LOG.info(format("Completed processing {0} documents. (Elapsed time: {1})",
                    reader.getNumDocs(),
                    sw.toString()));
            LOG.info(MiscUtil.memoryInfoString());
        }
    }

}
