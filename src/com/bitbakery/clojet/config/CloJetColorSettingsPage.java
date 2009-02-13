package com.bitbakery.clojet.config;

import com.intellij.openapi.options.colors.ColorSettingsPage;
import com.intellij.openapi.options.colors.AttributesDescriptor;
import com.intellij.openapi.options.colors.ColorDescriptor;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighter;
import com.bitbakery.clojet.ClojureSyntaxHighlighter;
import com.bitbakery.clojet.CloJetIcons;
import static com.bitbakery.clojet.CloJetStrings.*;
import static com.bitbakery.clojet.lexer.ClojureTokenTypes.*;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.util.Map;

/**
 *
 */
public class CloJetColorSettingsPage implements ColorSettingsPage {

    private static final String DEMO_TEXT =
            ";  Sample comment\n\n" +

                    "(= v \"Some global variable\")\n\n" +

                    "(= one-var-function [+ 1 _])\n\n" +

                    "(def function-name (param . rest)\n" +
                    "   \"Documentation string\"\n" +
                    "   (function-body param rest))\n\n\n" +

                    "(def another-function rest\n" +
                    "   (function-body rest))\n\n\n" +

                    "(mac macro-name (param . rest)\n" +
                    "   `(,param ,@rest))\n";

    private static final AttributesDescriptor[] ATTRS = new AttributesDescriptor[]{
            new AttributesDescriptor(message("color.settings.docstring"), ClojureSyntaxHighlighter.keys.get(SYMBOL)),
            new AttributesDescriptor(message("color.settings.comment"), ClojureSyntaxHighlighter.keys.get(LINE_COMMENT)),
            new AttributesDescriptor(message("color.settings.number"), ClojureSyntaxHighlighter.keys.get(NUMERIC_LITERAL)),
            new AttributesDescriptor(message("color.settings.string"), ClojureSyntaxHighlighter.keys.get(STRING_LITERAL)),
            new AttributesDescriptor(message("color.settings.bad_character"), ClojureSyntaxHighlighter.keys.get(BAD_CHARACTER)),
            new AttributesDescriptor(message("color.settings.defn"), ClojureSyntaxHighlighter.keys.get(DEFN)),
            new AttributesDescriptor(message("color.settings.defmacro"), ClojureSyntaxHighlighter.keys.get(DEFMACRO)),
            new AttributesDescriptor(message("color.settings.backquote"), ClojureSyntaxHighlighter.keys.get(BACKQUOTE)),
            new AttributesDescriptor(message("color.settings.comma"), ClojureSyntaxHighlighter.keys.get(COMMA)),
            new AttributesDescriptor(message("color.settings.comma_at"), ClojureSyntaxHighlighter.keys.get(COMMA_AT)),
    };


    private static final ColorDescriptor[] COLORS = new ColorDescriptor[0];

    @Nullable
    public Map<String, TextAttributesKey> getAdditionalHighlightingTagToDescriptorMap() {
        return null;
    }

    @NotNull
    public String getDisplayName() {
        return message("color.settings.name");
    }

    @NotNull
    public Icon getIcon() {
        return CloJetIcons.CLOJURE_FILE_ICON;
    }

    @NotNull
    public AttributesDescriptor[] getAttributeDescriptors() {
        return ATTRS;
    }

    @NotNull
    public ColorDescriptor[] getColorDescriptors() {
        return COLORS;
    }

    @NotNull
    public SyntaxHighlighter getHighlighter() {
        return new ClojureSyntaxHighlighter();
    }

    @NotNull
    public String getDemoText() {
        return DEMO_TEXT;
    }
}
