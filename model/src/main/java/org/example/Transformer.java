package org.example;

/**
 * A common interface for transformers.
 */
public interface Transformer {
    /**
     * A method to convert XML to ontology in RDF/XML. Uses third-party project ontmalizer.
     * <a href="https://github.com/srdc/ontmalizer">https://github.com/srdc/ontmalizer</a>
     * @param inputXSD path to XML schema (exactly one schema)
     * @param inputXML path to XML itself
     * @param outputRDF path to result RDF
     */
    void convertXMLToRDF(String inputXSD, String inputXML, String outputRDF);

    /**
     * A method to convert XSD to ontology in RDF/XML. Uses third-party project ontmalizer.
     * <a href="https://github.com/srdc/ontmalizer">https://github.com/srdc/ontmalizer</a>
     * @param inputXSD path to XML schema (exactly one schema)
     * @param outputRDF path to result RDF
     */
    void convertXSDToRDF(String inputXSD, String outputRDF);
}
