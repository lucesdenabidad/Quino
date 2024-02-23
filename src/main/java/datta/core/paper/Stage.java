package datta.core.paper;

public enum Stage {
    Waiting("&7Esperando..."),
    Game("&8> &aEn juego");

    private final String showString;

    Stage(String showString) {
        this.showString = showString;
    }

    public String getShowString() {
        return showString;
    }
}
