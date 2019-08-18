package kaptainwutax.techuhc;

import net.minecraft.text.LiteralText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class MessageUtils {

    private static final Style CHAT_PREFIX_STYLE = new Style().setBold(true).setColor(Formatting.DARK_PURPLE);
    private static final Style CHAT_STYLE = new Style().setBold(false).setColor(Formatting.AQUA);

    public static Text formatMessage(String message) {
        return new LiteralText("[UHC] ").setStyle(CHAT_PREFIX_STYLE)
                .append(new LiteralText(message).setStyle(CHAT_STYLE));
    }

}
