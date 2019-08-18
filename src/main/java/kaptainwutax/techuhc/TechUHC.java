package kaptainwutax.techuhc;

import kaptainwutax.techuhc.command.CommandRule;
import kaptainwutax.techuhc.init.Commands;
import net.fabricmc.api.ModInitializer;

@SuppressWarnings("unused")
public class TechUHC implements ModInitializer {

    @Override
    public void onInitialize() {
        Commands.registerCommand(CommandRule.getCommand());
    }

}
