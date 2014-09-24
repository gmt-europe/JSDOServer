package nl.gmt.jsdo.query.parser;

import org.antlr.runtime.*;

public abstract class Lexer extends org.antlr.runtime.Lexer {
    private String fileName;

    protected Lexer() {
    }

    protected Lexer(CharStream input, RecognizerSharedState state) {
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
}
