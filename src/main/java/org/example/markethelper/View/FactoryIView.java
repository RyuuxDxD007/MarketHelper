package org.example.markethelper.View;

import java.security.InvalidParameterException;

public class FactoryIView {
    public static IView createView(String view, IView app) {
        switch (view) {
            case "start":
                return new Start();
            case "view-identification":
                return (IView) new Identification(app);
            case "view-mainPage":
                return (IView) new MainPage(app);
            case "view-newUser":
                return (IView) new NewUser(app);
            case "view-relics":
                return (IView) new VRelics(app);
            case "view-primeSets":
                return (IView) new VPrimeSets(app);
            case "view-newItem":
                return (IView) new VNewItem(app);
            default :
                throw new InvalidParameterException("unknown view : " + view);
        }
    }
}
