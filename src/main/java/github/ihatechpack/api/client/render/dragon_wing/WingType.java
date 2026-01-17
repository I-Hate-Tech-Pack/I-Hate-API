package github.ihatechpack.api.client.render.dragon_wing;

/**
 * @description: TODO
 * @author: HowXu
 * @date: 2026/1/17 15:58
 */
public enum WingType {

    ENDER_DRAGON("textures/render/ender_dragon_wings.png", "Ender Dragon Wings"),

    DRAGON("textures/render/dragon_wings.png", "Dragon Wings");

    private final String texturePath;
    private final String displayName;

    WingType(String texturePath, String displayName) {
        this.texturePath = texturePath;
        this.displayName = displayName;
    }

    public String getTexturePath() {
        return texturePath;
    }

    public String getDisplayName() {
        return displayName;
    }
}
