package com.bitbakery.clojet.psi;

import com.bitbakery.clojet.ClojureFileType;
import com.intellij.lang.PsiBuilder;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NonNls;

/**
 * Simple wrapper for IElementType which enables Clojure PSI elements to own theirown parsing
 */
public class ClojureElementType extends IElementType {
    public ClojureElementType(@NonNls String debugName) {
        super(debugName, ClojureFileType.CLOJURE);
    }

    @SuppressWarnings({"HardCodedStringLiteral"})
    public String toString() {
        return "Clojure:" + super.toString();
    }

    /**
     * Concrete subclasses tell ClojureParser how they should be broken down into PSI elements.
     */
    public PsiBuilder.Marker parse(PsiBuilder builder) {
        // By default, we do nothing...
        advanceLexer(builder);
        return null;
    }

    /**
     * Advances the lexer if we haven't fallen off the end of the token stream
     */
    protected void advanceLexer(PsiBuilder builder) {
        if (builder.getTokenType() != null) {
            builder.advanceLexer();
        }
    }
}


