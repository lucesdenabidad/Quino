package datta.core.paper.listeners;

import lombok.Getter;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class GameCallEvents {
    public static class GameCancelEvent extends Event {
        @Getter
        private static final HandlerList handlers = new HandlerList();
        @Getter private final String gameName;


        public GameCancelEvent(String gameName) {
            this.gameName = gameName;
        }

        @Override
        public @NotNull HandlerList getHandlers() {
            return handlers;
        }
    }
    public static class GameStartEvent extends Event {
        @Getter
        private static final HandlerList handlers = new HandlerList();
        @Getter private final String gameName;


        public GameStartEvent(String gameName) {
            this.gameName = gameName;
        }

        @Override
        public @NotNull HandlerList getHandlers() {
            return handlers;
        }
    }
    public static class GameStopEvent extends Event {
        @Getter
        private static final HandlerList handlers = new HandlerList();
        @Getter private final String gameName;


        public GameStopEvent(String gameName) {
            this.gameName = gameName;
        }

        @Override
        public @NotNull HandlerList getHandlers() {
            return handlers;
        }
    }
}
