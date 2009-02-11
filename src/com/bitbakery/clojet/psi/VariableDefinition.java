package com.bitbakery.clojet.psi;

import org.jetbrains.annotations.NotNull;
import com.intellij.lang.ASTNode;

/**
 * Created by IntelliJ IDEA.
 * User: kurtc
 * Date: Feb 9, 2009
 * Time: 2:45:05 PM
 * To change this template use File | Settings | File Templates.
 */
public class VariableDefinition extends ClojureElement {
    public VariableDefinition(@NotNull final ASTNode node) {
        super(node);
    }
}