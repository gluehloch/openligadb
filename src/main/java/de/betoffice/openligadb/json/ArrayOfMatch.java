package de.betoffice.openligadb.json;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArrayOfMatch {

    private List<Match> matches = null;
}
