package org.example;

import tr.com.srdc.ontmalizer.XML2OWLMapper;
import tr.com.srdc.ontmalizer.XSD2OWLMapper;

import java.io.File;
import java.io.FileOutputStream;

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
}
