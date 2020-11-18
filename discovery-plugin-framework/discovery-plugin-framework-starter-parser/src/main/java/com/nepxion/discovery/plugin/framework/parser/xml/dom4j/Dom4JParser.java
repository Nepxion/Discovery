package com.nepxion.discovery.plugin.framework.parser.xml.dom4j;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;

public abstract class Dom4JParser {
    public void parse(String text) throws DocumentException {
        if (StringUtils.isEmpty(text)) {
            throw new IllegalArgumentException("The text is empty");
        }

        Document document = Dom4JReader.getDocument(text);

        parse(document);
    }

    public void parseFormat(String text) throws DocumentException, UnsupportedEncodingException {
        if (StringUtils.isEmpty(text)) {
            throw new IllegalArgumentException("The text is empty");
        }

        Document document = Dom4JReader.getFormatDocument(text);

        parse(document);
    }

    public void parse(File file) throws DocumentException, IOException, UnsupportedEncodingException {
        if (file == null) {
            throw new IllegalArgumentException("The file is null");
        }

        Document document = Dom4JReader.getDocument(file);

        parse(document);
    }

    public void parseFormat(File file) throws DocumentException, IOException, UnsupportedEncodingException {
        if (file == null) {
            throw new IllegalArgumentException("The file is null");
        }

        Document document = Dom4JReader.getFormatDocument(file);

        parse(document);
    }

    public void parse(InputStream inputStream) throws DocumentException, IOException {
        if (inputStream == null) {
            throw new IllegalArgumentException("The input stream is null");
        }

        Document document = Dom4JReader.getDocument(inputStream);

        parse(document);
    }

    public void parseFormat(InputStream inputStream) throws DocumentException, IOException, UnsupportedEncodingException {
        if (inputStream == null) {
            throw new IllegalArgumentException("The input stream is null");
        }

        Document document = Dom4JReader.getFormatDocument(inputStream);

        parse(document);
    }

    public void parse(Document document) {
        Element rootElement = document.getRootElement();

        parseRoot(rootElement);
    }

    protected abstract void parseRoot(Element element);
}