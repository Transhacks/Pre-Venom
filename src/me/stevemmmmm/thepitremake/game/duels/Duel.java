//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package me.stevemmmmm.thepitremake.game.duels;

import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class Duel {
    private final Player playerA;
    private final Player playerB;
    private Vector playerAPos;
    private Vector playerBPos;

    public Duel(Player playerA, Player playerB) {
        this.playerA = playerA;
        this.playerB = playerB;
    }

    public Duel(Player playerA, Player playerB, Vector playerAPos, Vector playerBPos) {
        this.playerA = playerA;
        this.playerB = playerB;
        this.playerAPos = playerAPos;
        this.playerBPos = playerBPos;
    }

    public Player getPlayerA() {
        return this.playerA;
    }

    public Player getPlayerB() {
        return this.playerB;
    }

    public void setPlayerAPos(Vector playerAPos) {
        this.playerAPos = playerAPos;
    }

    public void setPlayerBPos(Vector playerBPos) {
        this.playerBPos = playerBPos;
    }

    public Vector getPlayerAPos() {
        return this.playerAPos;
    }

    public Vector getPlayerBPos() {
        return this.playerBPos;
    }
}
