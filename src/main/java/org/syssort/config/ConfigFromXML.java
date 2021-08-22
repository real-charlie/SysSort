package org.syssort.config;

import org.syssort.exceptions.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;

/*
 * Getting configurations from the target xml file.
 * */
public class ConfigFromXML {
    String xmlConfigFile;

    public ConfigFromXML(String configFile) {
        xmlConfigFile = configFile;
    }

    /*
     * returns the configurations from the XML file as a `Config` type and handling errors.
     * */
    public Config[] getConfigs() throws ParserConfigurationException, IOException, SAXException, InvalidTagException, EmptyBodyException, EmptyPathException, RequiredDirectory, EmptyWorkingDirectory {
        Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(xmlConfigFile);
        doc.getDocumentElement().normalize();
        Element rootElement = doc.getDocumentElement();
        NodeList subElements = rootElement.getChildNodes();
        String rootElementTag = rootElement.getTagName();
        ArrayList<Config> configs = new ArrayList<>();
        if (!rootElementTag.equals(XMLTags.ROOT_ELEMENT)) {
            throw new InvalidTagException(rootElementTag);
        }

        /*
         * Each <config> tag in root tag or <main-conf>
         * */
        for (int i = 0; i < subElements.getLength(); i++) {
            Node configNode = subElements.item(i);
            if (configNode.getNodeType() == Node.ELEMENT_NODE) {
                Element configElement = (Element) configNode;
                String configElementTag = configElement.getTagName();
                if(!configElementTag.equals(XMLTags.CONFIG_ELEMENT)){
                    throw new InvalidTagException(configElementTag);
                }
                NodeList configChildren = configElement.getChildNodes(); // <config> children
                String workingDir = Path.of(configElement.getAttribute(XMLAttributes.WORKING_DIR)).toString();

                if (workingDir.isEmpty()) {
                    throw new EmptyWorkingDirectory(workingDir);
                } else if (!new File(workingDir).isDirectory()) {
                    throw new RequiredDirectory(XMLAttributes.WORKING_DIR, workingDir);
                } else if (!new File(workingDir).exists()) {
                    throw new IOException(workingDir + " not exists. working directory must be exist.");
                }

                Config config = new Config();
                ArrayList<DirectoryConfig> d_configs = new ArrayList<>();
                ArrayList<String> ignores = new ArrayList<>();

                /*
                 * Each tag in <config>
                 * */
                for (int j = 0; j < configChildren.getLength(); j++) {
                    Node subNode = configChildren.item(j);
                    if (subNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element subElement = (Element) subNode;
                        String elementTag = subElement.getNodeName();
                        String path = Path.of(subElement.getAttribute(XMLAttributes.PATH)).toString();
                        String textContent = subElement.getTextContent();
                        if (elementTag.equals(XMLTags.DIRECTORY_CONF)) {
                            if (textContent.isEmpty())
                                throw new EmptyBodyException(elementTag);
                            else if (path.isEmpty())
                                throw new EmptyPathException(elementTag);
                            File pathAsFile = new File(path);
                            if (!pathAsFile.isDirectory() && pathAsFile.exists())
                                throw new RequiredDirectory(elementTag, path);
                            d_configs.add(new DirectoryConfig(path, textContent.trim().split(Shared.FILE_SPECIFICATION_SEPARATOR)));
                        } else if (elementTag.equals(XMLTags.IGNORE_PATH)) {
                            if (path.isEmpty())
                                throw new EmptyPathException(elementTag);
                            ignores.add(path);
                        } else {
                            throw new InvalidTagException(elementTag);
                        }
                    }
                }
                config.setDirectoryConfigs(d_configs.toArray(new DirectoryConfig[]{}));
                config.setIgnores(ignores.toArray(new String[0]));
                config.setHiddenFiles(Boolean.parseBoolean(configElement.getAttribute(XMLAttributes.INCLUDING_HIDDEN_FILES)));
                config.setDeleteEmptyDirs(Boolean.parseBoolean(configElement.getAttribute(XMLAttributes.DELETING_EMPTY_DIRS)));
                config.setWorkingDir(workingDir);

                configs.add(config);
            }
        }
        return configs.toArray(new Config[0]);
    }
}