package org.lrima.laop.controller;

import org.lrima.laop.utils.Action;

import java.io.OutputStream;
import java.io.PrintStream;

/**
 * Classe utilisée pour intercepter les System.out.print et leurs attribuer une autre action. Par exemple, elle est utilisée dans pour afficher les résultats dans la console.
 * @author Léonard
 */
public class PrintInterseptor extends PrintStream {
    Action<String> action;

    public PrintInterseptor(OutputStream out, Action<String> stringAction) {
        super(out, true);
        this.action = stringAction;
    }

    @Override
    public void print(String s) {
        this.action.handle(s);
        super.print(s);
    }
}
