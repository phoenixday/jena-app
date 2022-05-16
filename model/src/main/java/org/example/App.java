package org.example;

import org.apache.jena.query.*;
import org.apache.jena.rdf.model.*;
import org.apache.jena.riot.RDFDataMgr;

import javax.xml.transform.Source;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;

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
//        try {
//            transformESEToRDF();
//        } catch (TransformerException e) {
//            throw new RuntimeException(e);
//        }
//        Model model = RDFDataMgr.loadModel("model/assets/publikace_predmetu_ese.rdf");
//        selectAll(model);
//        transformESEToRDFOntmalizer();
//        Model model = RDFDataMgr.loadModel("model/assets/publikace_predmetu_ese.rdf");
//        selectAll(model);
//        transformAXMPRToRDF();
//        Model model = RDFDataMgr.loadModel("model/assets/axmpr/publikace_predmetu_axmpr.rdf");
//        writeModel(model);
//        selectAll(model);

        InfModel model = ModelFactory.createRDFSModel(RDFDataMgr.loadModel("/home/phoenixday/Documents/data/city_country_continent.rdf"));
        //Model model = RDFDataMgr.loadModel("/home/phoenixday/Documents/data/city_country_continent.rdf");
        selectAll(model);
        selectClassesOfPraha(model);
        selectRegionsOfPraha(model);
    }

    /**
     * Transfrom using XSLT
     */
    static void transformESEToRDF() throws TransformerException {
        TransformerFactory factory = TransformerFactory.newInstance();
        Source xslt = new StreamSource(new File("model/assets/transform.xslt"));
        javax.xml.transform.Transformer transformer = factory.newTransformer(xslt);

        Source text = new StreamSource(new File("model/assets/esbirky_ese_MMP_publikacePredmetu_01.xml"));
        transformer.transform(text, new StreamResult(new File("model/assets/publikace_predmetu_ese.rdf")));
    }

    static void myTransform() throws TransformerException {
        TransformerFactory factory = TransformerFactory.newInstance();
        Source xslt = new StreamSource(new File("model/assets/testest/try_axmpr.xslt"));
        javax.xml.transform.Transformer transformer = factory.newTransformer(xslt);

        Source text = new StreamSource(new File("model/assets/testest/try_axmpr.xml"));
        transformer.transform(text, new StreamResult(new File("model/assets/testest/try_axmpr.rdf")));
    }

    /**
     * Transform using Ontmalizer
     */
    static void transformAXMPRToRDF() {
        Transformer transformer = new TransformerImpl();
        transformer.convertXMLToRDF("model/assets/axmpr/axmpr_complete.xsd",
                "model/assets/axmpr/esbirky_axmpr_MMP_publikacePredmetu_02.xml",
                "model/assets/axmpr/publikace_predmetu_axmpr.rdf");
    }

    static void transformESEToRDFOntmalizer() {
        Transformer transformer = new TransformerImpl();
        transformer.convertXMLToRDF("model/assets/testest/eseOntmalizer/ese_complete.xsd",
                "/home/phoenixday/Documents/data/try_ese.xml",
                "model/assets/publikace_predmetu_ese.rdf");
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

    static void selectAll(Model model) {
        System.out.println("\n======= Select all names =======");
        String queryString = "" +
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" +
                "PREFIX dc: <http://purl.org/dc/elements/1.1/>" +
                "SELECT * WHERE {" +
                "   ?subject ?predicate ?object" +
                "}";
        Query query = QueryFactory.create(queryString);
        try (QueryExecution qexec = QueryExecutionFactory.create(query, model)) {
            ResultSet results = qexec.execSelect();
            ResultSetFormatter.out(results);
            //System.out.println(results.getResultVars());
            //ResultSetFormatter.outputAsJSON(results);
            //System.out.println(results.);
            //System.out.println(ResultSetFormatter.toList(results));
            //List<QuerySolution> resultsAsList = ResultSetFormatter.toList(results);
            //System.out.println(results.getResultVars());
            //System.out.println(resultsAsList.get(0));
            //QuerySolution querySolution = resultsAsList.get(0);
            //QuerySolutionMap querySolutionMap = new QuerySolutionMap();
            //querySolutionMap.addAll(querySolution);
            //System.out.println(querySolutionMap.get());
            //System.out.println(resultsAsList.get(0).getResource(results.getResultVars().get(0)));
        }
    }

    static void selectClassesOfPraha(Model model) {
        System.out.println("\n======= Select location of Praha =======");
        String queryString = "" +
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" +
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>" +
                "PREFIX dc: <http://purl.org/dc/elements/1.1/>" +
                "PREFIX dcterms: <http://purl.org/dc/terms/>" +
                "PREFIX : <http://myloc.org/>" +
                "SELECT * WHERE {" +
                "   :Praha rdf:type ?object" +
                "}";
        Query query = QueryFactory.create(queryString);
        try (QueryExecution qexec = QueryExecutionFactory.create(query, model)) {
            ResultSet results = qexec.execSelect();
            ResultSetFormatter.out(results);
        }
    }

    static void selectRegionsOfPraha(Model model) {
        System.out.println("\n======= Select regions of Praha =======");
        String queryString = "" +
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" +
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>" +
                "PREFIX dc: <http://purl.org/dc/elements/1.1/>" +
                "PREFIX dcterms: <http://purl.org/dc/terms/>" +
                "PREFIX : <http://myloc.org/>" +
                "SELECT * WHERE {" +
                "   :Praha dcterms:isPartOf+ ?object" +
                "}";
        Query query = QueryFactory.create(queryString);
        try (QueryExecution qexec = QueryExecutionFactory.create(query, model)) {
            ResultSet results = qexec.execSelect();
            ResultSetFormatter.out(results);
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
