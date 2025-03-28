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
            case "view-newSet":
                return (IView) new VNewSet(app);
            case "view-newRelic":
                return (IView) new VNewRelic(app);
            default :
                throw new InvalidParameterException("unknown view : " + view);
        }
    }
    public static IView createView(String view, IView app, String[] item) {
        switch (view) {
            case "view-modifyItem":
                return (IView) new VNewItem(app, item);
            case "view-modifySet":
                return (IView) new VNewSet(app, item);
            case "view-modifyRelic":
                return (IView) new VNewRelic(app, item);
            default :
                throw new InvalidParameterException("unknown view : " + view);
        }
    }
}
