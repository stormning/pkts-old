package io.pkts.sdp;

import gov.nist.javax.sdp.fields.SDPField;
import gov.nist.javax.sdp.parser.SDPParser;

import java.text.ParseException;

/**
 * .
 *
 * @author stormning 2017/9/4
 * @since 1.3.0
 */
public class UnknownParser extends SDPParser {

    private final String field;

    public UnknownParser(String field) {
        this.field = field;
    }

    @Override
    public SDPField parse() throws ParseException {
        return new UnknownField(field);
    }
}
