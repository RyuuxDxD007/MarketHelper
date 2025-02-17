package org.example.markethelper.View;

import java.security.InvalidParameterException;

public class FactoryIView {
    public static IView createView(String view, IView app) {
        switch (view) {
            case "start":
                return new Start();
            case "identification":
                return (IView) new Identification(app);
            case "mainPage":
                return (IView) new MainPage(app);
            case "new-user":
                return (IView) new NewUser(app);
            default :
                throw new InvalidParameterException("unknown view : " + view);
        }
    }
}
