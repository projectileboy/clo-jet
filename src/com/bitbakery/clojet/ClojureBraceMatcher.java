package com.bitbakery.clojet;

import static com.bitbakery.clojet.lexer.ClojureTokenTypes.*;
import com.intellij.lang.BracePair;
import com.intellij.lang.PairedBraceMatcher;
import com.intellij.psi.PsiFile;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Defines the brace matching support required for Clojure.
 */
public class ClojureBraceMatcher implements PairedBraceMatcher {
    private static final BracePair[] PAIRS = new BracePair[]{
            new BracePair(LEFT_PAREN, RIGHT_PAREN, true),
            new BracePair(LEFT_SQUARE, RIGHT_SQUARE, false)
    };

    public BracePair[] getPairs() {
        return PAIRS;
    }

    public boolean isPairedBracesAllowedBeforeType(@NotNull final IElementType lbraceType, @Nullable final IElementType tokenType) {
        return true;
    }

    public int getCodeConstructStart(PsiFile file, int openingBraceOffset) {
        return openingBraceOffset;
    }
}
