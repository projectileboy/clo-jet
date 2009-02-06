package com.bitbakery.clojet.repl;

import com.bitbakery.clojet.CloJetIcons;
import static com.bitbakery.clojet.CloJetStrings.message;
import com.intellij.execution.ExecutionException;
import com.intellij.execution.filters.TextConsoleBuilder;
import com.intellij.execution.filters.TextConsoleBuilderFactory;
import com.intellij.execution.process.ProcessHandler;
import com.intellij.execution.process.ProcessTerminatedListener;
import com.intellij.execution.ui.ConsoleView;
import com.intellij.execution.ui.ConsoleViewContentType;
import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.ScrollType;
import com.intellij.openapi.editor.impl.EditorImpl;
import com.intellij.openapi.editor.impl.EditorComponentImpl;
import com.intellij.openapi.editor.ex.EditorEx;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowAnchor;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.util.Icons;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReplToolWindow implements ProjectComponent {

    private Project myProject;
    private List<Repl> replList = new ArrayList<Repl>();
    private JTabbedPane tabbedPane;
    private ToolWindow toolWindow;
    private static final String REPL_TOOL_WINDOW_ID = "repl.toolWindow";


    public ReplToolWindow(Project project) {
        myProject = project;
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                disposeComponent();
            }
        });
    }

    public void requestFocus() {
        toolWindow.activate(null, true);
        if (tabbedPane.getSelectedIndex() > -1) {
            Repl repl = replList.get(tabbedPane.getSelectedIndex());
            repl.view.getPreferredFocusableComponent().requestFocusInWindow();
        }
    }

    public void writeToCurrentRepl(String s) {
        if (tabbedPane.getSelectedIndex() > -1) {
            requestFocus();
            Repl repl = replList.get(tabbedPane.getSelectedIndex());
            repl.view.print(s + "\r\n", ConsoleViewContentType.USER_INPUT);
        }
    }

    public void projectOpened() {
        try {
            initToolWindow();
        } catch (Exception e) {
            // TODO - Handle me for real...
            e.printStackTrace();  // TODO - Some sort of real error handling
        }
    }

    public void projectClosed() {
        // ??? unregisterToolWindow();
    }

    public void initComponent() {
        // Empty

        // TODO - We need a way to open and close the REPL tool window, not just individual REPLs
        // toolWindow.setAvailable(true);
    }

    public void disposeComponent() {
        ToolWindowManager.getInstance(myProject).unregisterToolWindow(message("repl.toolWindowName"));
        for (Repl repl : replList) {
            repl.close();
        }
        tabbedPane.removeAll();

        // TODO - Any other cleanup??
    }

    @NotNull
    public String getComponentName() {
        return REPL_TOOL_WINDOW_ID;
    }


    private void initToolWindow() throws ExecutionException, IOException {
        if (myProject != null) {
            tabbedPane = new JTabbedPane(JTabbedPane.BOTTOM);
            tabbedPane.setComponentPopupMenu(createPopupMenu());

            JPanel panel = new JPanel(new BorderLayout());
            panel.add(tabbedPane, BorderLayout.CENTER);
            panel.add(createButtonPanel(), BorderLayout.WEST);

            Repl repl = createRepl();
            tabbedPane.addTab(message("repl.title"), repl.view.getComponent());

            // TODO - Use a non-deprecated version...
            toolWindow = ToolWindowManager.getInstance(myProject).registerToolWindow(message("repl.toolWindowName"), panel, ToolWindowAnchor.BOTTOM);

            toolWindow.setAnchor(ToolWindowAnchor.BOTTOM, null);
            toolWindow.setIcon(CloJetIcons.CLOJURE_REPL_ICON);
            toolWindow.setToHideOnEmptyContent(true);
        }
    }

    private JPanel createButtonPanel() {
        // TODO - Modify the buttons to match look and feel of other open/close tab buttons - see "Add" and "Cancel" icons in the IntelliJ resource bundle...
        // TODO - See other plugins for how we can use the built-in tool window toolbars...
        JButton addButton = new JButton(Icons.ADD_ICON);
        addButton.setToolTipText(message("repl.open"));
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    tabbedPane.addTab(message("repl.title"), createRepl().view.getComponent());
                    tabbedPane.setSelectedIndex(tabbedPane.getTabCount() - 1);
                } catch (IOException e) {
                    e.printStackTrace();  // TODO - Some meaningful error handling
                }
            }
        });

        // TODO - We should also close the tab and kill the process if the user enters the right Lisp command (usually, (quit), or some such)
        JButton removeButton = new JButton(Icons.DELETE_ICON);
        removeButton.setToolTipText(message("repl.close"));
        removeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                int i = tabbedPane.getSelectedIndex();
                if (i > -1) {
                    replList.remove(i).close();
                    tabbedPane.removeTabAt(i);
                }
            }
        });

        JPanel buttonPanel = new JPanel(new GridLayout(5, 1));
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        return buttonPanel;
    }

    private JPopupMenu createPopupMenu() {
        JPopupMenu menu = new JPopupMenu();
        JMenuItem item = new JMenuItem(message("repl.rename"));
        item.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                int tabIndex = tabbedPane.getSelectedIndex();
                if (tabIndex > -1) {
                    String oldTitle = tabbedPane.getTitleAt(tabIndex);
                    String newTitle = (String) JOptionPane.showInputDialog(
                            (Component) actionEvent.getSource(), message("repl.newName"), message("repl.rename"),
                            JOptionPane.PLAIN_MESSAGE, null, null, oldTitle);
                    if (newTitle != null) {
                        tabbedPane.setTitleAt(tabIndex, newTitle);
                    }
                }
            }
        });
        menu.add(item);
        return menu;
    }

    private Repl createRepl() throws IOException {
        Repl repl = new Repl();
        replList.add(repl);
        return repl;
    }


    private class Repl {
        public ConsoleView view;
        private ProcessHandler processHandler;

        public Repl() throws IOException {
            TextConsoleBuilder builder = TextConsoleBuilderFactory.getInstance().createBuilder(myProject);
            view = builder.getConsole();

            // TODO - What does the "help ID" give us??
            // view.setHelpId("Kurt's Help ID");

            processHandler = new ClojureProcessHandler(myProject);
            ProcessTerminatedListener.attach(processHandler);
            processHandler.startNotify();
            view.attachToProcess(processHandler);

            tabbedPane.addTab(message("repl.title"), view.getComponent());

            final EditorEx ed = getEditor();
            ed.getContentComponent().addKeyListener(new KeyAdapter() {
                public void keyTyped(KeyEvent event) {
                    // TODO - This is probably wrong, actually, but it's a start...
                    ed.getCaretModel().moveToOffset(view.getContentSize());
                    ed.getScrollingModel().scrollToCaret(ScrollType.MAKE_VISIBLE);
                }
            });
            ed.getContentComponent().addFocusListener(new FocusAdapter() {
                public void focusGained(FocusEvent event) {
                    // TODO - This is probably wrong, actually, but it's a start...
                    ed.getCaretModel().moveToOffset(view.getContentSize());
                    ed.getScrollingModel().scrollToCaret(ScrollType.MAKE_VISIBLE);
                }
            });

            // TODO - Experimental... Play around with what widgetry we'd like to see in the REPL
            ed.getSettings().setSmartHome(true);
            ed.getSettings().setVariableInplaceRenameEnabled(true);
            ed.getSettings().setAnimatedScrolling(true);
            ed.getSettings().setFoldingOutlineShown(true);

/*
            // TODO - Register TransferHandler with the *code* editor; we *never* want to move, only copy. Also, we'd like a custom icon.
            e.getContentComponent().setTransferHandler(new TransferHandler() {

                public Icon getVisualRepresentation(Transferable transferable) {
                    return ArcIcons.ARC_LARGE_ICON;
                }
            });
*/

/*
            ed.getContentComponent().setDropTarget(new DropTarget() {
                public synchronized void drop(DropTargetDropEvent event) {
                    try {
                        Transferable transferable = event.getTransferable();
                        String s = (String) transferable.getTransferData(DataFlavor.stringFlavor);
                        view.print(s.trim(), ConsoleViewContentType.USER_INPUT);
                        event.dropComplete(true);

                    } catch (UnsupportedFlavorException e1) {
                        e1.printStackTrace();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            });
*/

            //e.getSettings().setLineNumbersShown(true);

            // This is how we can add add'l tooling to our REPL window!
            // e.setHeaderComponent(new JLabel("Only a test"));

            // TODO - Ctrl-v (paste) should trim string before dropping
            // TODO - Drag-n-drop text isn't working from editor to console... why not?
            // Unfortunately, the following two lines do nothing to make either of the above things happen...
            //e.getSettings().setDndEnabled(true);
            //e.getContentComponent().getDropTarget().setActive(true);

        }

        /**
         * A bit of a hack, admittedly...
         */
        public EditorEx getEditor() {
            EditorComponentImpl eci = (EditorComponentImpl) view.getPreferredFocusableComponent();
            return eci.getEditor();
        }

        public void close() {
            if (processHandler != null) {
                processHandler.destroyProcess();
            }
        }
    }
}
