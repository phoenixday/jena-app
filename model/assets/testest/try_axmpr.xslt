<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
                xmlns:oai="http://www.openarchives.org/OAI/2.0/"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:axmpr="https://api.museion.cz/schema/axmpr">
    <xsl:template match="/">
        <OAI-PMH>
            <xsl:variable name="xmlns" select="'http://www.openarchives.org/OAI/2.0/'"/>
            <!-- The variable xmlns can be used for further processing.  -->
            <xsl:attribute name="xmlns">
                <xsl:value-of select="$xmlns"/>
            </xsl:attribute>
            <ListRecords>
                <xsl:for-each select="/oai:OAI-PMH/oai:ListRecords/oai:record">
                    <record>
                        <metadata>
                            <axmpr>
                                <xsl:variable name="xmlns" select="'https://api.museion.cz/schema/axmpr'"/>
                                <!-- The variable xmlns can be used for further processing.  -->
                                <xsl:attribute name="xmlns">
                                    <xsl:value-of select="$xmlns"/>
                                </xsl:attribute>
                                <PublikacePredmetu>
                                    <xsl:variable name="xmlns" select="''"/>
                                    <!-- The variable xmlns can be used for further processing.  -->
                                    <xsl:attribute name="xmlns">
                                        <xsl:value-of select="$xmlns"/>
                                    </xsl:attribute>
                                    <id>
                                        <xsl:value-of   select="./oai:metadata/axmpr:axmpr/axmpr:PublikacePredmetu/axmpr:id"/>
                                    </id>
                                    <inventarniCislo>
                                        <xsl:value-of   select="./oai:metadata/axmpr:axmpr/axmpr:PublikacePredmetu/axmpr:inventarniCislo"/>
                                    </inventarniCislo>
                                    <nazev>
                                        <xsl:value-of   select="./oai:metadata/axmpr:axmpr/axmpr:PublikacePredmetu/axmpr:nazev"/>
                                    </nazev>
                                    <popis>
                                        <xsl:value-of   select="./oai:metadata/axmpr:axmpr/axmpr:PublikacePredmetu/axmpr:popis"/>
                                    </popis>
                                    <rozmer>
                                        <xsl:value-of   select="./oai:metadata/axmpr:axmpr/axmpr:PublikacePredmetu/axmpr:rozmer"/>
                                    </rozmer>
                                    <konvDatace>
                                        <xsl:value-of   select="./oai:metadata/axmpr:axmpr/axmpr:PublikacePredmetu/axmpr:konvDatace"/>
                                    </konvDatace>
                                    <konvLokalita>
                                        <xsl:value-of   select="./oai:metadata/axmpr:axmpr/axmpr:PublikacePredmetu/axmpr:konvLokalita"/>
                                    </konvLokalita>
                                    <konvMaterial>
                                        <xsl:value-of   select="./oai:metadata/axmpr:axmpr/axmpr:PublikacePredmetu/axmpr:konvMaterial"/>
                                    </konvMaterial>
                                    <poznamka>
                                        <xsl:value-of   select="./oai:metadata/axmpr:axmpr/axmpr:PublikacePredmetu/axmpr:poznamka"/>
                                    </poznamka>
                                    <autorskaPrava>
                                        <xsl:value-of   select="./oai:metadata/axmpr:axmpr/axmpr:PublikacePredmetu/axmpr:autorskaPrava"/>
                                    </autorskaPrava>
                                    <datumPublikace>
                                        <xsl:value-of   select="./oai:metadata/axmpr:axmpr/axmpr:PublikacePredmetu/axmpr:datumPublikace"/>
                                    </datumPublikace>
                                    <datumModifikace>
                                        <xsl:value-of   select="./metadata/axmpr/PublikacePredmetu/datumModifikace"/>
                                    </datumModifikace>
                                    <Datace>
                                        <start>
                                            <xsl:value-of   select="./metadata/axmpr/PublikacePredmetu/Datace/start"/>
                                        </start>
                                        <end>
                                            <xsl:value-of   select="./metadata/axmpr/PublikacePredmetu/Datace/end"/>
                                        </end>
                                        <snad>
                                            <xsl:value-of   select="./metadata/axmpr/PublikacePredmetu/Datace/snad"/>
                                        </snad>
                                        <snad2>
                                            <xsl:value-of   select="./metadata/axmpr/PublikacePredmetu/Datace/snad2"/>
                                        </snad2>
                                        <dataceNazvemPublic>
                                            <xsl:variable name="id" select="./metadata/axmpr/PublikacePredmetu/Datace/dataceNazvemPublic/@id"/>
                                            <!-- The variable id can be used for further processing.  -->
                                            <xsl:attribute name="id">
                                                <xsl:value-of select="$id"/>
                                            </xsl:attribute>
                                            <xsl:attribute name="label">
                                                <xsl:value-of select="./metadata/axmpr/PublikacePredmetu/Datace/dataceNazvemPublic/@label"/>
                                            </xsl:attribute>
                                            <xsl:attribute name="oaiId">
                                                <xsl:value-of select="./metadata/axmpr/PublikacePredmetu/Datace/dataceNazvemPublic/@oaiId"/>
                                            </xsl:attribute>
                                            <xsl:attribute name="setSpec">
                                                <xsl:value-of select="./metadata/axmpr/PublikacePredmetu/Datace/dataceNazvemPublic/@setSpec"/>
                                            </xsl:attribute>
                                        </dataceNazvemPublic>
                                    </Datace>
                                    <typSbirkyPublic>
                                        <xsl:variable name="id" select="./metadata/axmpr/PublikacePredmetu/typSbirkyPublic/@id"/>
                                        <!-- The variable id can be used for further processing.  -->
                                        <xsl:attribute name="id">
                                            <xsl:value-of select="$id"/>
                                        </xsl:attribute>
                                        <xsl:attribute name="label">
                                            <xsl:value-of select="./metadata/axmpr/PublikacePredmetu/typSbirkyPublic/@label"/>
                                        </xsl:attribute>
                                        <xsl:attribute name="oaiId">
                                            <xsl:value-of select="./metadata/axmpr/PublikacePredmetu/typSbirkyPublic/@oaiId"/>
                                        </xsl:attribute>
                                        <xsl:attribute name="setSpec">
                                            <xsl:value-of select="./metadata/axmpr/PublikacePredmetu/typSbirkyPublic/@setSpec"/>
                                        </xsl:attribute>
                                    </typSbirkyPublic>
                                    <materialPublic>
                                        <xsl:variable name="id" select="./metadata/axmpr/PublikacePredmetu/materialPublic/@id"/>
                                        <!-- The variable id can be used for further processing.  -->
                                        <xsl:attribute name="id">
                                            <xsl:value-of select="$id"/>
                                        </xsl:attribute>
                                        <xsl:attribute name="label">
                                            <xsl:value-of select="./metadata/axmpr/PublikacePredmetu/materialPublic/@label"/>
                                        </xsl:attribute>
                                        <xsl:attribute name="oaiId">
                                            <xsl:value-of select="./metadata/axmpr/PublikacePredmetu/materialPublic/@oaiId"/>
                                        </xsl:attribute>
                                        <xsl:attribute name="setSpec">
                                            <xsl:value-of select="./metadata/axmpr/PublikacePredmetu/materialPublic/@setSpec"/>
                                        </xsl:attribute>
                                    </materialPublic>
                                    <lokalitaPublic>
                                        <xsl:variable name="id" select="./metadata/axmpr/PublikacePredmetu/lokalitaPublic/@id"/>
                                        <!-- The variable id can be used for further processing.  -->
                                        <xsl:attribute name="id">
                                            <xsl:value-of select="$id"/>
                                        </xsl:attribute>
                                        <xsl:attribute name="label">
                                            <xsl:value-of select="./metadata/axmpr/PublikacePredmetu/lokalitaPublic/@label"/>
                                        </xsl:attribute>
                                        <xsl:attribute name="oaiId">
                                            <xsl:value-of select="./metadata/axmpr/PublikacePredmetu/lokalitaPublic/@oaiId"/>
                                        </xsl:attribute>
                                        <xsl:attribute name="setSpec">
                                            <xsl:value-of select="./metadata/axmpr/PublikacePredmetu/lokalitaPublic/@setSpec"/>
                                        </xsl:attribute>
                                    </lokalitaPublic>
                                    <typPredmetu>
                                        <xsl:value-of   select="./metadata/axmpr/PublikacePredmetu/typPredmetu"/>
                                    </typPredmetu>
                                    <KontextovyDokument>
                                        <id>
                                            <xsl:value-of   select="./metadata/axmpr/PublikacePredmetu/KontextovyDokument/id"/>
                                        </id>
                                        <nahled>
                                            <xsl:value-of   select="./metadata/axmpr/PublikacePredmetu/KontextovyDokument/nahled"/>
                                        </nahled>
                                        <url>
                                            <xsl:value-of   select="./metadata/axmpr/PublikacePredmetu/KontextovyDokument/url"/>
                                        </url>
                                        <poznamka>
                                            <xsl:value-of   select="./metadata/axmpr/PublikacePredmetu/KontextovyDokument/poznamka"/>
                                        </poznamka>
                                        <licenceCc>
                                            <xsl:value-of   select="./metadata/axmpr/PublikacePredmetu/KontextovyDokument/licenceCc"/>
                                        </licenceCc>
                                        <typPrilohy>
                                            <xsl:value-of   select="./metadata/axmpr/PublikacePredmetu/KontextovyDokument/typPrilohy"/>
                                        </typPrilohy>
                                    </KontextovyDokument>
                                    <sbirka>
                                        <xsl:variable name="id" select="./metadata/axmpr/PublikacePredmetu/sbirka/@id"/>
                                        <!-- The variable id can be used for further processing.  -->
                                        <xsl:attribute name="id">
                                            <xsl:value-of select="$id"/>
                                        </xsl:attribute>
                                        <xsl:attribute name="label">
                                            <xsl:value-of select="./metadata/axmpr/PublikacePredmetu/sbirka/@label"/>
                                        </xsl:attribute>
                                        <xsl:attribute name="oaiId">
                                            <xsl:value-of select="./metadata/axmpr/PublikacePredmetu/sbirka/@oaiId"/>
                                        </xsl:attribute>
                                        <xsl:attribute name="setSpec">
                                            <xsl:value-of select="./metadata/axmpr/PublikacePredmetu/sbirka/@setSpec"/>
                                        </xsl:attribute>
                                    </sbirka>
                                    <spravceSbirky>
                                        <xsl:variable name="id" select="./metadata/axmpr/PublikacePredmetu/spravceSbirky/@id"/>
                                        <!-- The variable id can be used for further processing.  -->
                                        <xsl:attribute name="id">
                                            <xsl:value-of select="$id"/>
                                        </xsl:attribute>
                                        <xsl:attribute name="label">
                                            <xsl:value-of select="./metadata/axmpr/PublikacePredmetu/spravceSbirky/@label"/>
                                        </xsl:attribute>
                                        <xsl:attribute name="oaiId">
                                            <xsl:value-of select="./metadata/axmpr/PublikacePredmetu/spravceSbirky/@oaiId"/>
                                        </xsl:attribute>
                                        <xsl:attribute name="setSpec">
                                            <xsl:value-of select="./metadata/axmpr/PublikacePredmetu/spravceSbirky/@setSpec"/>
                                        </xsl:attribute>
                                    </spravceSbirky>
                                    <podsbirka>
                                        <xsl:variable name="id" select="./metadata/axmpr/PublikacePredmetu/podsbirka/@id"/>
                                        <!-- The variable id can be used for further processing.  -->
                                        <xsl:attribute name="id">
                                            <xsl:value-of select="$id"/>
                                        </xsl:attribute>
                                        <xsl:attribute name="label">
                                            <xsl:value-of select="./metadata/axmpr/PublikacePredmetu/podsbirka/@label"/>
                                        </xsl:attribute>
                                        <xsl:attribute name="setSpec">
                                            <xsl:value-of select="./metadata/axmpr/PublikacePredmetu/podsbirka/@setSpec"/>
                                        </xsl:attribute>
                                    </podsbirka>
                                    <fond>
                                        <xsl:variable name="id" select="./metadata/axmpr/PublikacePredmetu/fond/@id"/>
                                        <!-- The variable id can be used for further processing.  -->
                                        <xsl:attribute name="id">
                                            <xsl:value-of select="$id"/>
                                        </xsl:attribute>
                                        <xsl:attribute name="label">
                                            <xsl:value-of select="./metadata/axmpr/PublikacePredmetu/fond/@label"/>
                                        </xsl:attribute>
                                        <xsl:attribute name="setSpec">
                                            <xsl:value-of select="./metadata/axmpr/PublikacePredmetu/fond/@setSpec"/>
                                        </xsl:attribute>
                                    </fond>
                                    <skupina>
                                        <xsl:variable name="id" select="./metadata/axmpr/PublikacePredmetu/skupina/@id"/>
                                        <!-- The variable id can be used for further processing.  -->
                                        <xsl:attribute name="id">
                                            <xsl:value-of select="$id"/>
                                        </xsl:attribute>
                                        <xsl:attribute name="label">
                                            <xsl:value-of select="./metadata/axmpr/PublikacePredmetu/skupina/@label"/>
                                        </xsl:attribute>
                                        <xsl:attribute name="setSpec">
                                            <xsl:value-of select="./metadata/axmpr/PublikacePredmetu/skupina/@setSpec"/>
                                        </xsl:attribute>
                                    </skupina>
                                </PublikacePredmetu>
                            </axmpr>
                        </metadata>
                    </record>
                </xsl:for-each>
            </ListRecords>
        </OAI-PMH>
    </xsl:template>
</xsl:stylesheet>