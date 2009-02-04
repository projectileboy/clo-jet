package com.bitbakery.clojet.lexer;

import com.intellij.lexer.FlexAdapter;

import java.io.Reader;

public class ClojureLexer extends FlexAdapter {
    public ClojureLexer() {
        super(new _ClojureLexer((Reader) null));
    }
}
