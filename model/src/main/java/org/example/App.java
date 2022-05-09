package org.example;

import org.apache.jena.query.*;
import org.apache.jena.rdf.model.*;
import org.apache.jena.riot.RDFDataMgr;

//TODO: prevod ese do dublin core a nejaky reasoning
//TODO: prevod axmpr do nove ontologie a nejaky reasoning
//TODO: nejaky view
//TODO: better import for ontmalizer
//TODO: try not to change xsd


/**
 * <a href="https://www.euclid-project.eu/modules/chapter2.html">https://www.euclid-project.eu/modules/chapter2.html</a>
 * <a href="https://jena.apache.org/documentation/inference/index.html">https://jena.apache.org/documentation/inference/index.html</a>
 * <a href="https://www.dublincore.org/specifications/dublin-core/dcmi-terms/">https://www.dublincore.org/specifications/dublin-core/dcmi-terms/</a>
 */
public class App 
{
    public static void main( String[] args )
    {
        Transformer transformer = new TransformerImpl();
//        transformer.convertXMLToRDF("model/assets/testest/complete.xsd",
//        "model/assets/testest/xml.xml",
//        "model/assets/testest/books.rdf");
//        Model model = RDFDataMgr.loadModel("model/assets/testest/books.rdf");
//        writeModel(model);
        transformer.convertXMLToRDF("model/assets/ese_complete.xsd",
                "model/assets/esbirky_ese_MMP_publikacePredmetu_01.xml",
                "model/assets/publikace_predmetu_ese.rdf");
//        transformer.convertXMLToRDF("model/assets/axmpr_axmpr_complete.xsd",
//                "model/assets/esbirky_axmpr_MMP_publikacePredmetu_02.xml",
//                "model/assets/publikace_predmetu_axmpr.rdf");
        //Model model = RDFDataMgr.loadModel("model/assets/publikace_predmetu_axmpr.rdf");
        //writeModel(model);
        //selectAllNames(model);
        //selectAllLocalities(model);
    }

    /**
     * Writes the triples(statements) stored in the model
     * @param model RDF data already stored in triplestore
     */
    static void writeModel(Model model) {
        System.out.println("======= Write all triples stored from RDF (data.rdf) =======");
        // list the statements in the Model
        StmtIterator iter = model.listStatements();
        // print out the predicate, subject and object of each statement
        while (iter.hasNext()) {
            Statement stmt = iter.nextStatement();  // get next statement
            Resource subject = stmt.getSubject();     // get the subject
            Property predicate = stmt.getPredicate();   // get the predicate
            RDFNode object = stmt.getObject();      // get the object

            System.out.println("subject: " + subject.toString());
            System.out.println("predicate: " + predicate.toString());
            System.out.print("object: ");
            if (object instanceof Resource) {
                System.out.println(object);
            } else {
                // object is a literal
                System.out.println(object.toString() + " (literal)");
            }
            System.out.println();
        }
    }

    static void selectAllNames(Model model) {
        System.out.println("\n======= Select all names =======");
        String queryString = "" +
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" +
                "PREFIX : <http://www.openarchives.org/OAI/2.0/#>" +
                "SELECT * WHERE {" +
                "   ?predmet :nazev ?nazev" +
                "}";
        Query query = QueryFactory.create(queryString);
        try (QueryExecution qexec = QueryExecutionFactory.create(query, model)) {
            ResultSet results = qexec.execSelect();
            ResultSetFormatter.out(results);
        }
    }

    static void selectAllLocalities(Model model) {
        System.out.println("\n======= Select all localities =======");
        String queryString = "" +
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" +
                "PREFIX : <http://www.openarchives.org/OAI/2.0/#>" +
                "SELECT * WHERE {" +
                "   ?predmet :lokalitaPublic ?lokalita" +
                "}";
        Query query = QueryFactory.create(queryString);
        try (QueryExecution qexec = QueryExecutionFactory.create(query, model)) {
            ResultSet results = qexec.execSelect();
            ResultSetFormatter.out(results);
        }
    }
}
