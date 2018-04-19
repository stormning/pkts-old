package io.pkts.sdp;

import com.google.common.collect.Lists;
import gov.nist.javax.sdp.SessionDescriptionImpl;
import gov.nist.javax.sdp.fields.SDPField;
import gov.nist.javax.sdp.parser.ParserFactory;
import gov.nist.javax.sdp.parser.SDPAnnounceParser;
import gov.nist.javax.sdp.parser.SDPParser;

import javax.sdp.SdpException;
import javax.sdp.SessionDescription;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

/**
 * .
 *
 * @author stormning 2017/9/6
 * @since 1.3.0
 */
public class SDPAnnounceParserEx extends SDPAnnounceParser {

    static {
        String[] unknownFields = new String[]{"d", "f", "g", "h", "j", "l", "n", "q", "w", "x", "y"};
        try {
            Field parserTableField = ParserFactory.class.getDeclaredField("parserTable");
            parserTableField.setAccessible(true);
            Hashtable parserTable = (Hashtable) parserTableField.get(null);
            for (String f : unknownFields) {
                parserTable.put(f, UnknownParser.class);
            }
            parserTable.put("m", MediaFieldParserEx.class);

        } catch (Exception e) {
            //ignore
        }
    }

    public SDPAnnounceParserEx(Vector sdpMessage) {
        super(sdpMessage);
    }

    public SDPAnnounceParserEx(String message) {
        super(message);
    }

    @Override
    public SessionDescriptionImpl parse() throws ParseException {
        SessionDescriptionImpl retval = new SessionDescriptionImplEx();
        for (int i = 0; i < sdpMessage.size(); i++) {
            String field = (String) sdpMessage.elementAt(i);
            SDPParser sdpParser = ParserFactory.createParser(field);
            SDPField sdpField = null;
            if (sdpParser != null) {
                sdpField = sdpParser.parse();
            }
            retval.addField(sdpField);
        }
        return retval;
    }

    public class SessionDescriptionImplEx extends SessionDescriptionImpl {

        private List<UnknownField> unknownFields = Lists.newArrayList();

        public SessionDescriptionImplEx() {
        }

        public SessionDescriptionImplEx(SessionDescription otherSessionDescription) throws SdpException {
            super(otherSessionDescription);
        }

        @Override
        public void addField(SDPField sdpField) throws ParseException {
            if (sdpField instanceof UnknownField) {
                unknownFields.add((UnknownField) sdpField);
            } else {
                super.addField(sdpField);
            }
        }

        @Override
        public String toString() {
            StringBuilder encBuff = new StringBuilder();

            // Encode single attributes
            encBuff.append(getVersion() == null ? "" : getVersion().toString());
            encBuff.append(getOrigin() == null ? "" : getOrigin().toString());
            encBuff.append(getSessionName() == null ? "" : getSessionName()
                    .toString());
            encBuff.append(getInfo() == null ? "" : getInfo().toString());

            // Encode attribute vectors
            try {
                encBuff.append(getURI() == null ? "" : getURI().toString());
                encBuff.append(getEmails(false) == null ? ""
                        : encodeVector(getEmails(false)));
                encBuff.append(getPhones(false) == null ? ""
                        : encodeVector(getPhones(false)));
                encBuff.append(getConnection() == null ? "" : getConnection()
                        .toString());
                encBuff.append(getBandwidths(false) == null ? ""
                        : encodeVector(getBandwidths(false)));
                encBuff.append(getTimeDescriptions(false) == null ? ""
                        : encodeVector(getTimeDescriptions(false)));
                encBuff.append(getZoneAdjustments(false) == null ? ""
                        : encodeVector(getZoneAdjustments(false)));
                encBuff.append(getKey() == null ? "" : getKey().toString());
                encBuff.append(getAttributes(false) == null ? ""
                        : encodeVector(getAttributes(false)));
                encBuff.append(getMediaDescriptions(false) == null ? ""
                        : encodeVector(getMediaDescriptions(false)));
                for (UnknownField unknownField : unknownFields) {
                    encBuff.append(unknownField.toString());
                }
                // adds the final crlf
            } catch (SdpException exc) {
                // add exception handling if necessary
            }
            return encBuff.toString();
        }

        private String encodeVector(Vector vector) {
            StringBuilder encBuff = new StringBuilder();

            for (int i = 0; i < vector.size(); i++)
                encBuff.append(vector.elementAt(i));

            return encBuff.toString();
        }
    }
}
