package com.example.lab5;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class DataParser {
    public static String getRateFromECB(InputStream stream, String currencyCode) throws IOException {
        String result = "";
        try {
            DocumentBuilderFactory xmlDocFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder xmlDocBuilder = xmlDocFactory.newDocumentBuilder();
            Document doc = xmlDocBuilder.parse(stream);

            NodeList rateNodes = doc.getElementsByTagName("Cube");
            for (int i = 0; i < rateNodes.getLength(); i++) {
                Element cube = (Element) rateNodes.item(i);
                if(cube.hasAttribute("currency")){
                    String currencyName = cube.getAttribute("currency");
                    if(currencyName.equals(currencyCode))
                    {
                        result = cube.getAttribute("rate");
                        break;
                    }
                }
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
        return result;
    }


    public List<String> getCurrencies(InputStream stream) throws IOException {
        List<String> result = new ArrayList<String>();
        try {
            DocumentBuilderFactory xmlDocFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder xmlDocBuilder = xmlDocFactory.newDocumentBuilder();
            Document doc = xmlDocBuilder.parse(stream);

            NodeList rateNodes = doc.getElementsByTagName("Cube");
            for (int i = 0; i < rateNodes.getLength(); i++) {
                Element cube = (Element) rateNodes.item(i);
                if (cube.hasAttribute("currency")) {
                    result.add(cube.getAttribute("currency"));
                }
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
        return result;
    }




}
