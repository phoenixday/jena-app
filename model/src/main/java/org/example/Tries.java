package org.example;

import org.apache.jena.query.*;
import org.apache.jena.rdf.model.*;
import org.apache.jena.riot.RDFDataMgr;

//TODO: delete
public class Tries {

    static void tryToTransformESEToRDFThroughOntmalizer() {
        Transformer transformer = new TransformerImpl();
        transformer.convertXMLToRDF("model/assets/ese_complete.xsd",
                "model/assets/esbirky_ese_MMP_publikacePredmetu_01.xml",
                "model/assets/publikace_predmetu_ese.rdf");
    }

    static void transformBookToRDF() {
        Transformer transformer = new TransformerImpl();
        transformer.convertXSDToRDF("model/assets/testest/complete.xsd",
                "model/assets/testest/books.rdf");
        transformer.convertXMLToRDF("model/assets/testest/complete.xsd",
        "model/assets/testest/xml.xml",
        "model/assets/testest/books.rdf");
        Model model = RDFDataMgr.loadModel("model/assets/testest/books.rdf");
        writeModel(model);
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

    static void firstTryOnMyBookSchema() {
        //        transformer.convertXMLToRDF("model/assets/testest/axmpr_complete.xsd",
//                "model/assets/testest/xml.xml",
//                "model/assets/testest/books.rdf");

        Model model = RDFDataMgr.loadModel("model/assets/testest/data.rdf");
        //writeModel(model);
        //write the model in a pretty form
        //RDFDataMgr.write(System.out, model, Lang.RDFJSON);
        //model.write(System.out);

        selectAllResourcesForBooks(model);
        selectAllDistinctAuthors(model);
        selectBooksWrittenAfter2015(model);
        selectBooksWithLanguageIfExists(model);

        //examples with inference
        Model schema = RDFDataMgr.loadModel("model/assets/testest/my_schema.rdf");
        InfModel infModel = ModelFactory.createRDFSModel(schema, model);
        selectBooksWithDescription(infModel);
        selectBooksFromMyLibrary(infModel);
    }

    /**
     * I just was trying to understand how inference works.
     */
    static void test() {
        InfModel model = ModelFactory.createRDFSModel(RDFDataMgr.loadModel("assets/testest/test.rdf"));

        String queryString = "" +
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" +
                "PREFIX eg: <http://example.org/>" +
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>" +
                "SELECT * WHERE {" +
                "   eg:colin ?relation eg:rosy" +
                "}";
        Query query = QueryFactory.create(queryString);
        try (QueryExecution qexec = QueryExecutionFactory.create(query, model)) {
            ResultSet results = qexec.execSelect();
            ResultSetFormatter.out(results);
        }
    }

    /**
     * @param model RDF data already stored in triplestore
     */
    static void selectAllResourcesForBooks(Model model) {
        System.out.println("\n======= Select all resources for books =======");
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
                Resource book = soln.getResource("book");
                System.out.println(book);
            }
        }
    }

    static void selectAllDistinctAuthors(Model model) {
        System.out.println("\n======= Select all distinct authors =======");
        String queryString = "" +
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" +
                "PREFIX dc: <http://purl.org/dc/elements/1.1/>" +
                "SELECT DISTINCT ?author WHERE {" +
                "   { ?book dc:creator ?bag ." +
                "   ?bag ?prop ?author ." +
                "   FILTER (strstarts(str(?prop), str(rdf:_))) }" +
                "   UNION " +
                "   { ?book dc:creator ?author ." +
                "   FILTER (isLiteral(?author)) }" +
                "}";
        Query query = QueryFactory.create(queryString);
        try (QueryExecution qexec = QueryExecutionFactory.create(query, model)) {
            ResultSet results = qexec.execSelect();
            while (results.hasNext()) {
                QuerySolution soln = results.nextSolution();
                Literal author = soln.getLiteral("author");
                System.out.println(author);
            }
        }
    }

    static void selectBooksWrittenAfter2015(Model model) {
        System.out.println("\n======= Select all books writen after >= 2015 =======");
        String queryString = "" +
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" +
                "PREFIX dc: <http://purl.org/dc/elements/1.1/>" +
                "SELECT * WHERE {" +
                "   ?book dc:date ?year ." +
                "   FILTER (?year >= 2015)" +
                "}";
        Query query = QueryFactory.create(queryString);
        try (QueryExecution qexec = QueryExecutionFactory.create(query, model)) {
            ResultSet results = qexec.execSelect();
            while (results.hasNext()) {
                QuerySolution soln = results.nextSolution();
                Resource book = soln.getResource("book");
                Literal year = soln.getLiteral("year");
                System.out.println("Book: " + book + ", year: " + year.getInt());
            }
        }
    }

    static void selectBooksWithLanguageIfExists(Model model) {
        System.out.println("\n======= Select all books with language if exists =======");
        String queryString = "" +
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" +
                "PREFIX dc: <http://purl.org/dc/elements/1.1/>" +
                "SELECT * WHERE {" +
                "   OPTIONAL {?book dc:language ?language}" +
                "}";
        Query query = QueryFactory.create(queryString);
        try (QueryExecution qexec = QueryExecutionFactory.create(query, model)) {
            ResultSet results = qexec.execSelect();
            while (results.hasNext()) {
                QuerySolution soln = results.nextSolution();
                Resource book = soln.getResource("book");
                Literal language = soln.getLiteral("language");
                System.out.println("Book: " + book + ", language: " + language);
            }
        }
    }

    static void selectBooksWithDescription(Model model) {
        System.out.println("\n======= Select all books with description =======");
        String queryString = "" +
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" +
                "PREFIX dc: <http://purl.org/dc/elements/1.1/>" +
                "PREFIX dcterms: <http://purl.org/dc/terms/>" +
                "SELECT * WHERE {" +
                "   ?book dc:title \"A history of China\" ." +
                "   ?book dc:description ?description" +
                "}";
        Query query = QueryFactory.create(queryString);
        try (QueryExecution qexec = QueryExecutionFactory.create(query, model)) {
            ResultSet results = qexec.execSelect();
            while (results.hasNext()) {
                QuerySolution soln = results.nextSolution();
                Resource book = soln.getResource("book");
                Literal description = soln.getLiteral("description");
                System.out.println("Book: " + book + ", description: " + description);
            }
        }
    }

    static void selectBooksFromMyLibrary(Model model) {
        System.out.println("\n======= Select all books from my library, that have abstract class book =======");
        String queryString = "" +
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" +
                "PREFIX dc: <http://purl.org/dc/elements/1.1/>" +
                "PREFIX dcterms: <http://purl.org/dc/terms/>" +
                "PREFIX myroom: <https://www.my-room.com/>" +
                "SELECT DISTINCT ?title ?type WHERE {" +
                "   ?book a myroom:book ." +
                "   ?book dcterms:isPartOf* ?library ." +
                "   ?book dc:title ?title ." +
                "   ?book rdf:type ?type" +
                "}";
        Query query = QueryFactory.create(queryString);
        try (QueryExecution qexec = QueryExecutionFactory.create(query, model)) {
            ResultSet results = qexec.execSelect();
            while (results.hasNext()) {
                QuerySolution soln = results.nextSolution();
                Literal title = soln.getLiteral("title");
                Resource type = soln.getResource("type");
                System.out.println("Book title: " + title + ", class: " + type);
            }
        }
    }
}
