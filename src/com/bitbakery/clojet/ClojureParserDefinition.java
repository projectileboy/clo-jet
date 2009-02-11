package com.bitbakery.clojet;

import com.bitbakery.clojet.lexer.ClojureLexer;
import com.bitbakery.clojet.lexer.ClojureTokenTypes;
import com.bitbakery.clojet.psi.*;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.lang.ParserDefinition;
import com.intellij.lang.PsiParser;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.project.Project;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.IFileElementType;
import com.intellij.psi.tree.TokenSet;
import org.jetbrains.annotations.NotNull;

/**
 * Defines the implementation of our Clojure file parser. Note that the real parsing guts are in ClojureParser.
 */
public class ClojureParserDefinition implements ParserDefinition {
    @NotNull
    public Lexer createLexer(Project project) {
        return new ClojureLexer();
    }

    public PsiParser createParser(Project project) {
        return new ClojureParser();
    }

    public IFileElementType getFileNodeType() {
        return ClojureElementTypes.FILE;
    }

    @NotNull
    public TokenSet getWhitespaceTokens() {
        return TokenSet.create(ClojureTokenTypes.WHITESPACE, ClojureTokenTypes.EOL, ClojureTokenTypes.EOF);
    }

    @NotNull
    public TokenSet getCommentTokens() {
        return ClojureTokenTypes.COMMENTS;
    }

    @NotNull
    public TokenSet getStringLiteralElements() {
        return TokenSet.create(ClojureTokenTypes.STRING_LITERAL); // TODO - Not sure if this is complete
    }

    public SpaceRequirements spaceExistanceTypeBetweenTokens(ASTNode left, ASTNode right) {
        // TODO - Is this right? Are there spacing requirements in Lisp/Scheme/Arc that I'm not aware of?
        if (left.getElementType() == ClojureTokenTypes.COMMA
                || left.getElementType() == ClojureTokenTypes.COMMA_AT
                || left.getElementType() == ClojureTokenTypes.QUOTE
                || left.getElementType() == ClojureTokenTypes.BACKQUOTE
                || left.getElementType() == ClojureTokenTypes.COMPOSER
                || right.getElementType() == ClojureTokenTypes.COMPOSER) {

            return SpaceRequirements.MUST_NOT;

        } else if (left.getElementType() == ClojureTokenTypes.LEFT_PAREN
                || right.getElementType() == ClojureTokenTypes.RIGHT_PAREN
                || left.getElementType() == ClojureTokenTypes.RIGHT_PAREN
                || right.getElementType() == ClojureTokenTypes.LEFT_PAREN

                || left.getElementType() == ClojureTokenTypes.LEFT_SQUARE
                || right.getElementType() == ClojureTokenTypes.RIGHT_SQUARE
                || left.getElementType() == ClojureTokenTypes.RIGHT_SQUARE
                || right.getElementType() == ClojureTokenTypes.LEFT_SQUARE) {

            return SpaceRequirements.MAY;
        }
        return SpaceRequirements.MUST;
    }

    public PsiFile createFile(FileViewProvider viewProvider) {
        return new ClojureFile(viewProvider);
    }

    @NotNull
    public PsiElement createElement(ASTNode node) {
        final IElementType type = node.getElementType();

        if (type == ClojureElementTypes.FUNCTION_DEFINITION) {
            return new Defn(node);
        } else if (type == ClojureElementTypes.ANONYMOUS_FUNCTION_DEFINITION) {
            return new Fn(node);
        } else if (type == ClojureElementTypes.DEFINITION) {
            return new Def(node);
        } else if (type == ClojureElementTypes.MACRO_DEFINITION) {
            return new Defmacro(node);
        } else if (type == ClojureElementTypes.EXPRESSION) {
            return new Expression(node);
        } /* else if (type == ArcElementTypes.VARIABLE_ASSIGNMENT) {
            return new VariableAssignment(node);
        } else if (type == ArcElementTypes.OPTIONAL_PARAMETER) {
            return new OptionalParameter(node);
        } else if (type == ArcElementTypes.REST_PARAMETER) {
            return new RestParameter(node);
        } else if (type == ArcElementTypes.PARAMETER) {
            return new Parameter(node);
        } else if (type == ArcElementTypes.VARIABLE_DEFINITION) {
            return new VariableDefinition(node);
        } else if (type == ArcElementTypes.VARIABLE_REFERENCE) {
            return new VariableReference(node);
        } else if (type == ArcElementTypes.LET_BLOCK) {
            return new Let(node);
        } else if (type == ArcElementTypes.WITH_BLOCK) {
            return new With(node);
        } else if (type == ArcElementTypes.PARAMETER_LIST) {
            return new ParameterList(node);
        } else if (type == ArcElementTypes.DOCSTRING) {
            return new Docstring(node);
        }
*/

        return new ASTWrapperPsiElement(node);
    }
}
