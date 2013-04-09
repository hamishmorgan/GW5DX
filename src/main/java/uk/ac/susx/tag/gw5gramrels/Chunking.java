package uk.ac.susx.tag.gw5gramrels;

import com.beust.jcommander.internal.Lists;
import com.google.common.base.CharMatcher;
import com.google.common.collect.Sets;
import edu.jhu.agiga.AgigaConstants;
import edu.jhu.agiga.AgigaSentence;
import edu.jhu.agiga.AgigaToken;
import edu.jhu.agiga.AgigaTypedDependency;
import org.jgrapht.alg.util.UnionFind;

import java.util.List;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;

/**
* Created with IntelliJ IDEA.
* User: hiam20
* Date: 09/04/2013
* Time: 14:45
* To change this template use File | Settings | File Templates.
*/
public class Chunking {
    private final List<AgigaToken> tokens;
    private final UnionFind<AgigaToken> chunkSet;
    private final List<List<AgigaToken>> chunks;

    private Chunking(List<AgigaToken> tokens, UnionFind<AgigaToken> chunkSet, List<List<AgigaToken>> chunks) {
        this.tokens = checkNotNull(tokens, "tokens");
        this.chunkSet = checkNotNull(chunkSet, "chunkSet");
        this.chunks = checkNotNull(chunks, "chunks");
    }

    public static Chunking newInstance(AgigaSentence sent, AgigaConstants.DependencyForm depForm, Set<String> chunkRelations) {
        final List<AgigaToken> tokens = sent.getTokens();

        // Build a union-find set of all compounded tokens
        final UnionFind<AgigaToken> chunkSets = new UnionFind<AgigaToken>(Sets.newHashSet(tokens));
        for (final AgigaTypedDependency rel : sent.getAgigaDeps(depForm)) {
            if (chunkRelations.contains(rel.getType())) {
                chunkSets.union(
                        tokens.get(rel.getDepIdx()),
                        tokens.get(rel.getGovIdx()));
            }
        }
        // Create a list of chunks of the same length as the token list. Each chunk is a list of tokens.
        final List<List<AgigaToken>> chunks = Lists.newArrayList();
        for (AgigaToken token : tokens) {
            final int id = chunkSets.find(token).getTokIdx();
            while (chunks.size() <= id)
                chunks.add(Lists.<AgigaToken>newArrayList());
            chunks.get(id).add(token);
        }

        return new Chunking(tokens, chunkSets, chunks);
    }

    public List<AgigaToken> chunk(int tokenId) {
        return chunks.get(chunkSet.find(tokens.get(tokenId)).getTokIdx());
    }

    public String surface(int tokenId) {
        StringBuilder surface = new StringBuilder();
        for (AgigaToken token : chunk(tokenId)) {
            if (surface.length() > 0)
                surface.append(" ");
            surface.append(token.getWord());
        }
        return CharMatcher.WHITESPACE.replaceFrom(surface,' ');
    }

}
