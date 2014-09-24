package nl.gmt.jsdo.query.parser;

import org.antlr.runtime.*;

public abstract class Parser extends org.antlr.runtime.Parser {
    private String fileName;

    protected Parser(TokenStream input, RecognizerSharedState state) {
        super(input, state);
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public Object recoverFromMismatchedSet(IntStream input, RecognitionException e, BitSet follow) throws RecognitionException {
        throw e;
    }

    @Override
    public void reportError(RecognitionException e) {
        throw new RuntimeException(e);
    }

    protected String parseIdentifier(String identifier) {
        if (identifier.length() >= 2 && identifier.charAt(0) == '"') {
            assert identifier.charAt(identifier.length() - 1) == '"';
            return identifier.substring(1, identifier.length() - 1);
        }

        return identifier;
    }
}
