#!/usr/bin/env bash

cd ontology_generation/apache-jena-fuseki-3.6.0
./fuseki-server --config=assembler.ttl --port=8080