# Apache Camel™ IEC 60870 Component [![Build status](https://api.travis-ci.org/ctron/de.dentrassi.camel.iec60870.svg "Travis Build Status")](https://travis-ci.org/ctron/de.dentrassi.camel.iec60870) [![Maven Central](https://img.shields.io/maven-central/v/de.dentrassi.camel.iec60870/camel-iec60870.svg)](https://search.maven.org/#search|ga|1|g%3A%22de.dentrassi.camel.iec60870%22%20AND%20a%3A%22camel-iec60870%22)

This is an Apache Camel component for providing IEC 60870 client and server functionality based on Eclipse NeoSCADA™.

## Install into Karaf

To install `camel-iec60870` into a Karaf 4+ container run the following commands inside the Karaf shell:

    feature:repo-add mvn:org.apache.camel.karaf/apache-camel/2.18.0/xml/features
    feature:repo-add mvn:de.dentrassi.camel.iec60870/feature/0.1.1/xml/features
    feature:install camel-iec60870

If you want to install use a different version of Camel, this is possible by selecting a different Camel
feature version:

    feature:repo-add mvn:org.apache.camel.karaf/apache-camel/2.17.0/xml/features

## Build your own

If you want to re-compile the component yourself try the following:

    git clone https://github.com/ctron/de.dentrassi.camel.iec60870.git
    cd de.dentrassi.camel.iec60870
    mvn clean install
