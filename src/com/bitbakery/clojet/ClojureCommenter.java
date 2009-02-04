package com.bitbakery.clojet;

import com.intellij.lang.Commenter;

/**
 * Defines the support for "Comment with Line Comment" and "Comment with Block Comment" actions for Clojure source files.
 */
public class ClojureCommenter implements Commenter {
    public String getLineCommentPrefix() {
        return ";";
    }

    public String getBlockCommentPrefix() {
        return null;
    }

    public String getBlockCommentSuffix() {
        return null;
    }
}
