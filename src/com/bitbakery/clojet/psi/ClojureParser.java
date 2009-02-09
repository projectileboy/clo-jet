package com.bitbakery.clojet.psi;

import com.intellij.lang.ASTNode;
import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiParser;
import com.intellij.psi.tree.IElementType;
import com.bitbakery.clojet.lexer.ClojureTokenTypes;
import org.jetbrains.annotations.NotNull;

/**
 * Walk through the token stream of a Clojure source file and generate the appropriate PSI tree.
 */
public class ClojureParser implements PsiParser {

    @NotNull
    public ASTNode parse(IElementType root, PsiBuilder builder) {
        final PsiBuilder.Marker rootMarker = builder.mark();
        while (!builder.eof()) {
            builder.advanceLexer();
/*
            if (ClojureTokenTypes.LEFT_PAREN == builder.getTokenType()) {
                PsiBuilder.Marker m = builder.mark();
                while (ClojureTokenTypes.RIGHT_PAREN != builder.getTokenType()) {

                }
                m.done(ClojureTokenTypes.r
            }
*/
        }
        rootMarker.done(root);
        return builder.getTreeBuilt();
    }
}