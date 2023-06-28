//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package me.stevemmmmm.thepitremake.managers.enchants;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.ChatColor;

public class LoreBuilder {
    private final ArrayList<String> description = new ArrayList();
    private final List<String[]> parameters = new ArrayList();
    private boolean condition = true;
    private int lineIndex;
    private ChatColor color;

    public LoreBuilder() {
        this.color = ChatColor.GRAY;
        this.description.add("");
    }

    public LoreBuilder declareVariable(String... values) {
        this.parameters.add(values);
        return this;
    }

    public LoreBuilder writeVariable(int id, int level) {
        if (this.parameters.get(id) != null) {
            try {
                if (this.condition) {
                    this.description.set(this.lineIndex, (String)this.description.get(this.lineIndex) + this.color.toString() + ((String[])this.parameters.get(id))[level - 1]);
                }
            } catch (IndexOutOfBoundsException | NullPointerException var4) {
            }
        }

        return this;
    }

    public LoreBuilder writeVariable(ChatColor color, int id, int level) {
        if (this.parameters.get(id) != null && this.condition) {
            this.description.set(this.lineIndex, (String)this.description.get(this.lineIndex) + color.toString() + ((String[])this.parameters.get(id))[level - 1]);
        }

        return this;
    }

    public LoreBuilder next() {
        if (this.condition) {
            ++this.lineIndex;
            this.description.add("");
        }

        return this;
    }

    public LoreBuilder write(String value) {
        if (this.condition) {
            this.description.set(this.lineIndex, (String)this.description.get(this.lineIndex) + this.color.toString() + value);
        }

        return this;
    }

    public LoreBuilder write(ChatColor color, String value) {
        if (this.condition) {
            this.description.set(this.lineIndex, (String)this.description.get(this.lineIndex) + color.toString() + value);
        }

        return this;
    }

    public LoreBuilder setColor(ChatColor color) {
        this.color = color;
        if (this.condition) {
            this.description.set(this.lineIndex, (String)this.description.get(this.lineIndex) + color.toString());
        }

        return this;
    }

    public LoreBuilder resetColor() {
        this.color = ChatColor.GRAY;
        return this;
    }

    public LoreBuilder writeOnlyIf(boolean condition, String value) {
        if (condition) {
            this.description.set(this.lineIndex, (String)this.description.get(this.lineIndex) + this.color.toString() + value);
        }

        return this;
    }

    public LoreBuilder setWriteCondition(boolean condition) {
        this.condition = condition;
        return this;
    }

    public LoreBuilder resetCondition() {
        this.condition = true;
        return this;
    }

    public ArrayList<String> build() {
        return this.description;
    }
}
