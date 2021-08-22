package org.syssort;

import org.syssort.config.Config;
import org.syssort.config.ConfigFromXML;
import org.syssort.exceptions.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws InvalidTagException, EmptyBodyException,
            ParserConfigurationException, IOException, EmptyPathException, SAXException,
            RequiredDirectory, EmptyWorkingDirectory {
        if(args.length > 1) {
            ArrayList<String> extraArgs = new ArrayList<>(Arrays.asList(args).subList(1, args.length));
            throw new IllegalArgumentException(extraArgs.toString() + ' ' +
                    ((extraArgs.size() > 1) ? "are":"is") +
                    " extra. the only accepted argument is the location of a XML file that contains configurations for this program.");
        }
        Config[] configs = new ConfigFromXML((args.length < 1) ? "default.xml" /*default configurations*/ : args[0]).getConfigs();
        System.out.println("Running... (this may take from seconds to months depends on your files)");
        for (int i = 0 ; i < configs.length ; i ++) {
            System.out.println("Sorting File by Configuration " + i + 1);
            MoveFile.traverseWorkingDir(configs[i], configs[i].getWorkingDir());
        }
    }
}
