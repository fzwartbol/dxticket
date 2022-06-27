package training.standardfeatures.answer._04;
/*
 * Copyright 2020 Bloomreach B.V. (http://www.bloomreach.com)
 * Usage is prohibited except for people attending a training given or authorised by Bloomreach B.V., and only for that purpose.
 */


import com.fasterxml.jackson.databind.util.StdConverter;

public class ComponentNameConverter extends StdConverter<String, String> {
    @Override
    public String convert(final String value) {
        //todo: implement this feature, strip away numeric characters in string
        return value.replaceAll("\\d", "");
    }
}
