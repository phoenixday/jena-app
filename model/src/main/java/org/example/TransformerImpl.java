package org.example;

import tr.com.srdc.ontmalizer.XML2OWLMapper;
import tr.com.srdc.ontmalizer.XSD2OWLMapper;

import java.io.File;
import java.io.FileOutputStream;

/**
 * An implementation of Transformer interface.
 * The code is taken from there:
 * <a href="https://github.com/srdc/ontmalizer">https://github.com/srdc/ontmalizer</a>
 */
public class TransformerImpl implements Transformer{

    @Override
    public void convertXMLToRDF(String inputXSD, String inputXML, String outputRDF) {
        // This part converts XML schema to OWL ontology.
        XSD2OWLMapper mapping = new XSD2OWLMapper(new File(inputXSD));
        mapping.setObjectPropPrefix("");
        mapping.setDataTypePropPrefix("");
        mapping.convertXSD2OWL();

        // This part converts XML instance to RDF data model.
        XML2OWLMapper generator = new XML2OWLMapper(
                new File(inputXML), mapping);
        generator.convertXML2OWL();

        // This part prints the RDF data model to the specified file.
        try{
            File f = new File(outputRDF);
            f.getParentFile().mkdirs();
            FileOutputStream fout = new FileOutputStream(f);
            generator.writeModel(fout, "RDF/XML");
            fout.close();

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void convertXSDToRDF(String inputXSD, String outputRDF) {
        // This part converts XML schema to OWL ontology.
        XSD2OWLMapper mapping = new XSD2OWLMapper(new File(inputXSD));
        mapping.setObjectPropPrefix("");
        mapping.setDataTypePropPrefix("");
        mapping.convertXSD2OWL();

        // This part prints the ontology to the specified file.
        FileOutputStream ont;
        try {
            File f = new File(outputRDF);
            f.getParentFile().mkdirs();
            ont = new FileOutputStream(f);
            mapping.writeOntology(ont, "RDF/XML");
            ont.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
