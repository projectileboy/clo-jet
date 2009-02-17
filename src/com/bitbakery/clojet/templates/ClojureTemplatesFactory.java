package com.bitbakery.clojet.templates;

import com.intellij.ide.fileTemplates.*;
import com.intellij.openapi.fileTypes.FileTypeManager;
import com.bitbakery.clojet.CloJetStrings;
import com.bitbakery.clojet.CloJetIcons;
import org.jetbrains.annotations.NonNls;

import java.util.ArrayList;

/*
 * Copyright (c) Kurt Christensen, 2009
 *
 * Licensed under the Artistic License, Version 2.0 (the "License"); you may not use this
 * file except in compliance with the License. You may obtain a copy of the License at:
 *
 * http://www.opensource.org/licenses/artistic-license-2.0.php
 *
 * Unless required by applicable law or agreed to in writing, software distributed under
 * the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS
 * OF ANY KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */

/**
 * Enables users to create Clojure files from a template, which is both
 * a time-saver and a way for beginners to learn the language.
 */
public class ClojureTemplatesFactory implements FileTemplateGroupDescriptorFactory {
    @NonNls
    public static final String[] TEMPLATES = {"ClojureFile.clj"};

    public void registerCustomTemplates(String... templates) {
        for (String template : templates) {
            customTemplates.add(template);
        }
    }

    private static ClojureTemplatesFactory instance = null;

    public static ClojureTemplatesFactory getInstance() {
        if (instance == null) {
            instance = new ClojureTemplatesFactory();
        }
        return instance;
    }

    private ArrayList<String> customTemplates = new ArrayList<String>();

    //public static final String GSP_TEMPLATE = "GroovyServerPage.gsp";
    @NonNls
//    static final String NAME_TEMPLATE_PROPERTY = "NAME";
//    static final String LOW_CASE_NAME_TEMPLATE_PROPERTY = "lowCaseName";

    public FileTemplateGroupDescriptor getFileTemplatesDescriptor() {
        final FileTemplateGroupDescriptor group =
                new FileTemplateGroupDescriptor(CloJetStrings.message("file.template.group.title"), CloJetIcons.CLOJURE_FILE_ICON);
        final FileTypeManager fileTypeManager = FileTypeManager.getInstance();
        for (String template : TEMPLATES) {
            group.addTemplate(new FileTemplateDescriptor(template, fileTypeManager.getFileTypeByFileName(template).getIcon()));
        }
        //add GSP Template
        //group.addTemplate(new FileTemplateDescriptor(GSP_TEMPLATE, fileTypeManager.getFileTypeByFileName(GSP_TEMPLATE).getIcon()));

        // register custom templates
        for (String template : getInstance().getCustomTemplates()) {
            group.addTemplate(new FileTemplateDescriptor(template, fileTypeManager.getFileTypeByFileName(template).getIcon()));
        }
        return group;
    }


/*
    public static PsiFile createFromTemplate(final PsiDirectory directory,
                                             final String name,
                                             String fileName,
                                             String templateName,
                                             @NonNls String... parameters) throws IncorrectOperationException {

        final FileTemplate template = FileTemplateManager.getInstance().getInternalTemplate(templateName);

        Properties properties = new Properties(FileTemplateManager.getInstance().getDefaultProperties());
        JavaTemplateUtil.setPackageNameAttribute(properties, directory);
        properties.setProperty(NAME_TEMPLATE_PROPERTY, name);
        properties.setProperty(LOW_CASE_NAME_TEMPLATE_PROPERTY, name.substring(0, 1).toLowerCase() + name.substring(1));
        for (int i = 0; i < parameters.length; i += 2) {
            properties.setProperty(parameters[i], parameters[i + 1]);
        }
        String text;
        try {
            text = template.getText(properties);
        }
        catch (Exception e) {
            throw new RuntimeException("Unable to load template for " + FileTemplateManager.getInstance().internalTemplateToSubject(templateName),
                    e);
        }

        final PsiFileFactory factory = PsiFileFactory.getInstance(directory.getProject());
        final PsiFile file = factory.createFileFromText(fileName, text);

        return (PsiFile) directory.add(file);
    }
*/

    public String[] getCustomTemplates() {
        return customTemplates.toArray(new String[customTemplates.size()]);
    }
}

