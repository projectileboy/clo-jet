package com.bitbakery.clojet.psi;

import org.jetbrains.annotations.NotNull;
import com.intellij.lang.ASTNode;

/**
 * Created by IntelliJ IDEA.
 * User: kurtc
 * Date: Feb 10, 2009
 * Time: 1:25:18 AM
 * To change this template use File | Settings | File Templates.
 */
public class ClojureMap extends ClojureElement {
    public ClojureMap(@NotNull final ASTNode node) {
        super(node);
    }
}
