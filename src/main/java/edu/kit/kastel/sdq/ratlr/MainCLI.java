package edu.kit.kastel.sdq.ratlr;

import java.nio.file.Path;

import edu.kit.kastel.sdq.ratlr.command.EvaluateCommand;
import picocli.CommandLine;

@CommandLine.Command(subcommands = {EvaluateCommand.class})
public final class MainCLI {

    private MainCLI() {}

    public static void main(String[] args) {
        new CommandLine(new MainCLI()).registerConverter(Path.class, Path::of).execute(args);
    }
}
