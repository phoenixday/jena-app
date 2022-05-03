package org.example;

import org.apache.jena.query.*;
import org.apache.jena.rdf.model.*;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;

import java.io.InputStream;

public class Main {
    static final String inputFileName  = "vc-db-1.rdf";
    static final String myInputFile  = "rdf.rdf";
    public static void main(String[] args) {
        // create an empty model
        Model model = ModelFactory.createDefaultModel();
        // use the RDFDataMgr to find the input file
        InputStream in = RDFDataMgr.open(myInputFile);
        // read the RDF/XML file
        model.read(in, null);

        iterate(model);
        //write the model in a pretty form
        //RDFDataMgr.write(System.out, model, Lang.RDFJSON);
        //model.write(System.out);

        System.out.println("\n======= Select all resources for books =======");
        queryModel(model);
    }

    /**
     * Writes the triples(statements) stored in the model
     * @param model RDF data already stored in triplstore
     */
    static void iterate(Model model) {
        // list the statements in the Model
        StmtIterator iter = model.listStatements();
        // print out the predicate, subject and object of each statement
        while (iter.hasNext()) {
            System.out.println("=======");
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
        }
    }

    /**
     * Example SPARQL query
     * @param model RDF data already stored in triplstore
     */
    static void queryModel(Model model) {
        String queryString = "" +
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" +
                "PREFIX dc: <http://purl.org/dc/elements/1.1/>" +
                "SELECT * WHERE {" +
                "   ?book dc:creator ?author" +
                "}";
        Query query = QueryFactory.create(queryString);
        try (QueryExecution qexec = QueryExecutionFactory.create(query, model)) {
            ResultSet results = qexec.execSelect();
            while (results.hasNext()) {
                QuerySolution soln = results.nextSolution();
                //Literal author = soln.getLiteral("author");
                //System.out.println(author);
                Resource book = soln.getResource("book");
                System.out.println(book);
            }
        }
    }
}