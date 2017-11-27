package io.pkts.sdp;

import gov.nist.core.Separators;
import gov.nist.javax.sdp.fields.SDPField;

/**
 * .
 *
 * @author stormning 2017/9/6
 * @since 1.3.0
 */
public class UnknownField extends SDPField {

    public UnknownField(String fieldName) {
        super(fieldName);
    }

    @Override
    public String encode() {
        return fieldName + Separators.NEWLINE;
    }
}
